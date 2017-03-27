package gov.loc.workflow.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import gov.loc.workflow.domain.Bag;
import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.ProcessDefinition;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class ProcessController {
	
	private Logger logger = Logger.getLogger(ProcessController.class);
	
	//TODO: Consider other ways to pass around login info. E.g. store login info in session scope. 
	@Autowired
	User user;

	@Autowired
	Env environment;
	
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@RequestMapping(value = "/process/management", method = RequestMethod.GET)
	public String getAllProcessesDefinitions(Model model) {
		
		List<ProcessDefinition> processDefinitionList = this.getProcessDefinitionList();
		model.addAttribute(processDefinitionList);

		return "processesInstances";
	}

	@RequestMapping(value = "/process/start", method = RequestMethod.GET)
	public String startProcess(@RequestParam String processName, ModelMap model) {
		logger.debug("Selected process definition name: " + processName);
		
		List<ProcessDefinition> processDefinitionList = this.getProcessDefinitionList();

		ProcessDefinition selectedProcessDefinition = new ProcessDefinition();
		for(ProcessDefinition processDefinition: processDefinitionList){			
			if(processDefinition.getName().equalsIgnoreCase(processName)){
				selectedProcessDefinition.setName(processName);
				selectedProcessDefinition.setId(processDefinition.getId());
				selectedProcessDefinition.setDeploymentId(processDefinition.getDeploymentId());
			}
		}
		
		model.addAttribute("bag", new Bag());
		model.addAttribute("processDefinition", selectedProcessDefinition);
		model.addAttribute("server", environment.getEnvironment());

		return "processForm";
	}

	//TODO: cache the result
	private List<ProcessDefinition> getProcessDefinitionList(){
		String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/deployment/processes";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword()), String.class);

		String res = response.getBody();
		JSONObject json = new JSONObject(res);
		List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
		
		JSONArray array = json.getJSONArray("processDefinitionList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			JSONObject pdJson = jsn.getJSONObject("process-definition");
			ProcessDefinition processDefiniton = new ProcessDefinition();
			processDefiniton.setId((String)pdJson.get("id"));
			processDefiniton.setName((String)pdJson.get("name"));
			processDefiniton.setDeploymentId((String)pdJson.get("deployment-id"));
			processDefinitionList.add(processDefiniton);
		}

		return processDefinitionList;
	}

}
