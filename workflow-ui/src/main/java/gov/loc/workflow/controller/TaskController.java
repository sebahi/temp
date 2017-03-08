package gov.loc.workflow.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.Task;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class TaskController {

	private Logger logger = Logger.getLogger(TaskController.class);
	
	@Autowired
	Task task;
	@Autowired
	Env environment;
	@Autowired
	User user;
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	@Autowired
	RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/task/management", method = RequestMethod.GET)
	public ModelAndView getAllTasks(Model model) {
		
		ModelAndView mav = new ModelAndView("tasks");
		String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/task/query?status=Ready";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword()), String.class);

		String body = response.getBody();

		JSONObject json = new JSONObject(body);
		List<String> list = new ArrayList<String>();

		JSONArray arrayTask = json.getJSONArray("taskSummaryList");
		for (int i = 0; i < arrayTask.length(); i++) {
			JSONObject jsn = arrayTask.getJSONObject(i);
			Iterator<String> keys = jsn.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("id")) {
					list.add(jsn.get(key).toString());
				}
			}
		}

		task.setTaskIdList(list);
		model.addAttribute(task);
		mav.addObject("task", task);
		return mav;
	}
	
	@RequestMapping(value = "/task/start", method = RequestMethod.GET)
	public String claim(@RequestParam("taskId") String taskId, Model model) {
		task.setTaskId(taskId);
		return "taskForm";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/task/claim", method = RequestMethod.GET)
	public String claimProcess(@ModelAttribute("resubmit")String resubmit, Model model) {
		String responseStartBody = claimStart(task.getTaskId());
		if(resubmit.equalsIgnoreCase("on") && resubmit!=null){
			String responseCompleteBody = claimComplete(task.getTaskId(), "true");
		}else {
			String responseCompleteBody = claimComplete(task.getTaskId(), "false");
		}
		return "redirect:/task/management";
	}
	
	public String claimStart(String taskId){

		String url="http://"+environment.getEnvironment()+"/jbpm-console/rest/task/"+taskId+"/start";
		logger.debug("Task submit url: "+url);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword()), String.class);
		
		String body = response.getBody();
		return body;
	}
	
	public String claimComplete(String taskId, String confirm){

		String url="http://"+environment.getEnvironment()+"/jbpm-console/rest/task/"+taskId+"/complete";
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("map_resubmit_out", confirm);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword(),map), String.class);
		String body = response.getBody();
		return body;
	}
}
