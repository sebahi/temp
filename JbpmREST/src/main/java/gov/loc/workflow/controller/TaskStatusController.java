package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.Task;
import gov.loc.workflow.domain.TaskStatus;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class TaskStatusController {
	private Logger logger = Logger.getLogger(TaskStatusController.class);

	@Autowired
	User user;
	@Autowired
	TaskStatus taskStatus;
	@Autowired
	Task task;
	@Autowired
	Env environment;
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	@Autowired
	RestTemplate restTemplate;

	private ArrayList<TaskStatus> tsk;

	@RequestMapping(value = "/task/status", method = RequestMethod.GET)
	public ModelAndView getAllProcessesDefinitions(Model model) {

		ModelAndView mav = new ModelAndView("taskStatus");
		try {
			String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/task/query";

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
					connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword()),
					String.class);

			String res = response.getBody();
			JSONObject json = new JSONObject(res);
			List<String> list = new ArrayList<String>();
			JSONArray array = json.getJSONArray("taskSummaryList");
			tsk = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsn = array.getJSONObject(i);
				@SuppressWarnings("unchecked")
				Iterator<String> keys = jsn.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					list.add(jsn.get(key).toString());

					taskStatus.setTask(jsn.get("name").toString().trim());
					taskStatus.setStatus(jsn.get("status").toString().trim());
					taskStatus.setPriority(jsn.get("priority").toString().trim());

					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");

					String createdOn = jsn.get("created-on").toString().trim();
					Timestamp createdOnStamp = new Timestamp(Long.valueOf(createdOn));
					Date cDate = new Date(createdOnStamp.getTime());
					Date createdDate = format.parse(format.format(cDate));
					taskStatus.setCreatedOn(createdDate);

				}
				tsk.add(new TaskStatus(taskStatus.getTask(), taskStatus.getPriority(), taskStatus.getStatus(),
						taskStatus.getCreatedOn(), null));
			}

		} catch (Exception ex) {
			logger.debug(ex);
		}
		mav.addObject("task", tsk);
		return mav;
	}
}
