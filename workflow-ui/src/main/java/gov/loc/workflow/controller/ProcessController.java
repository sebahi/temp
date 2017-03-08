package gov.loc.workflow.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import gov.loc.workflow.domain.Bag;
import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.Process;
import gov.loc.workflow.domain.Task;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class ProcessController {
	
	private Logger logger = Logger.getLogger(ProcessController.class);
	
	@Autowired
	User user;
	@Autowired
	Bag bag;
	@Autowired
	Process process;
	@Autowired
	Task task;
	@Autowired
	Env environment;
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	@Autowired
	RestTemplate restTemplate;
	
	
	private String deploymentId;
	private String processDefId;
	
	
	
	@RequestMapping(value = "/process/management", method = RequestMethod.GET)
	public ModelAndView getAllProcessesDefinitions(Model model) {

		List<Process> processList = new ArrayList<>();
		ModelAndView mav = new ModelAndView("processes");
		String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/deployment/processes";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword()), String.class);

		String res = response.getBody();
		JSONObject json = new JSONObject(res);
		List<String> list = new ArrayList<String>();
		Set<String> nameSet = new HashSet<>();
		Set<String> deploymentIdSet = new HashSet<>();
		Set<String> definitionIdSet = new HashSet<>();
		JSONArray array = json.getJSONArray("processDefinitionList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				list.add(jsn.get(key).toString());

			}
		}

		for (String string : list) {
			org.json.JSONObject jsonObject = new org.json.JSONObject(string);
			process.setDeploymentId(jsonObject.get("deployment-id").toString().trim());
			process.setProcessDefId(jsonObject.get("id").toString().trim());
			process.setProcessName(jsonObject.get("name").toString().trim());
			processList.add(new Process(process.getDeploymentId(), process.getProcessDefId(), process.getProcessName()));
		}
		
		logger.debug("Retrieved Process Names in process management : "+nameSet+", Definition Id : "+definitionIdSet+", Deployment Id : "+deploymentIdSet);

		mav.addObject("process", processList);
		return mav;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/process/start", method = RequestMethod.GET)
	public ModelAndView startProcess(@ModelAttribute("processName") String processName, ModelMap model) {

		String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/deployment/processes";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword()), String.class);

		String res = response.getBody();
		JSONObject json = new JSONObject(res);
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<>();
		JSONArray array = json.getJSONArray("processDefinitionList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				list.add(jsn.get(key).toString());
				if (jsn.get(key).toString().contains(processName)) {
					for (String string : list) {
						org.json.JSONObject jsonObject = new org.json.JSONObject(string);
						deploymentId = jsonObject.get("deployment-id").toString().trim();
						processDefId = jsonObject.get("id").toString().trim();
					}
				}
			}
		}
		
		process.setDeploymentId(deploymentId);
		process.setProcessDefId(processDefId);
		
		model.addAttribute("bag", new Bag());
		model.addAttribute(process);
		model.addAttribute("server", environment.getEnvironment());
		ModelAndView mav = new ModelAndView("processForm");
		mav.addObject("process", process);
		mav.setViewName("processForm");

		return mav;
	}

}
