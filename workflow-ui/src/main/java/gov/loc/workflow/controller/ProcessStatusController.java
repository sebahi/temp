package gov.loc.workflow.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
import gov.loc.workflow.domain.ProcessStatus;
import gov.loc.workflow.domain.Task;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class ProcessStatusController {

	private Logger logger = Logger.getLogger(ProcessStatusController.class);

	@Autowired
	User user;
	@Autowired
	ProcessStatus processStatus;
	@Autowired
	Task task;
	@Autowired
	Env environment;
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	@Autowired
	RestTemplate restTemplate;

	private ArrayList<ProcessStatus> inst;
	private ArrayList<String> list;

	@RequestMapping(value = "/process/status", method = RequestMethod.GET)
	public ModelAndView getAllProcessesDefinitions(Model model) {

		ModelAndView mav = new ModelAndView("processStatus");
		try {
			String url = "http://" + environment.getEnvironment() + "/jbpm-console/rest/history/instances";

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
					connectionEstablishement.getConnectionRequest(user.getUserName(), user.getPassword()),
					String.class);

			String res = response.getBody();
			JSONObject json = new JSONObject(res);
			list = new ArrayList<String>();
			JSONArray array = json.getJSONArray("historyLogList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsn = array.getJSONObject(i);
				@SuppressWarnings("unchecked")
				Iterator<String> keys = jsn.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					list.add(jsn.get(key).toString());

				}
			}

			inst = new ArrayList<>();
			for (String string : list) {
				org.json.JSONObject jsonObject = new org.json.JSONObject(string);
				processStatus.setProcessInstanceName(jsonObject.get("process-name").toString().trim());
				processStatus.setInitiator(jsonObject.get("identity").toString().trim());
				String state = null;
				switch (jsonObject.get("status").toString().trim()) {
				case "1":
					state = "Active";
					break;
				case "2":
					state = "Completed";
					break;
				case "3":
					state = "Aborted";
					break;
				}
				processStatus.setStatus(state);
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss ");

				String start = jsonObject.get("start").toString().trim();
				Timestamp startStamp = new Timestamp(Long.valueOf(start));
				Date sDate = new Date(startStamp.getTime());
				Date startDate = format.parse(format.format(sDate));
				processStatus.setStartDate(startDate);

				inst.add(new ProcessStatus(null, null, processStatus.getStatus(), processStatus.getProcessInstanceName(),
						processStatus.getInitiator(), processStatus.getStartDate()));
			}

		} catch (Exception ex) {
			logger.debug(ex);
		}
		mav.addObject("process", inst);
		return mav;
	}
}
