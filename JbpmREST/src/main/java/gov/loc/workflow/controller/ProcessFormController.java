package gov.loc.workflow.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import gov.loc.workflow.domain.Bag;
import gov.loc.workflow.domain.Env;
import gov.loc.workflow.domain.Process;
import gov.loc.workflow.domain.User;
import gov.loc.workflow.util.ConnectionEstablishement;

@Controller
public class ProcessFormController {

	private Logger logger = Logger.getLogger(ProcessFormController.class);
	
    @Autowired
    Bag bag;
    @Autowired
    Process process;
    @Autowired
    Env environment;
    @Autowired
    User user;
	@Autowired
	ConnectionEstablishement connectionEstablishement;
	@Autowired
	RestTemplate restTemplate;
    
	private String bagId;
	private boolean doInventory;
	private boolean doMalwareScan;
	private boolean doBagInPlace;
	private boolean doExport;
	private boolean doVerify;
	private boolean doCopy;
	private boolean doWriteBagInfo;
	private boolean doDeleteFromStaging;
	private String numberOfCopies;
    
	@RequestMapping(value = "/process/submit", method = RequestMethod.GET)
	public String submitProcess(@ModelAttribute("processForm")Bag processForm,  Model model){
	
		bagId = processForm.getBagId();
		doInventory =  processForm.isDoInventory();
		doMalwareScan = processForm.isDoMalwareScan();
		doBagInPlace = processForm.isDoBagInPlace();
		doExport = processForm.isDoExport();
		doVerify =  processForm.isDoVerify();
		doCopy =  processForm.isDoCopy();
		doWriteBagInfo = processForm.isDoWriteBagInfo();
		doDeleteFromStaging =  processForm.isDoDeleteFromStaging();
		numberOfCopies = processForm.getNumberOfCopies();
		
		String url = "http://"+environment.getEnvironment()+"/jbpm-console/rest/runtime/"+ process.getDeploymentId() +"/withvars/process/"+ process.getProcessDefId() +"/start";
		
		logger.debug("Process submit url: "+url);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("map_bagId", "\""+bagId+"\"");
		map.add("map_numberOfCopies", "\""+numberOfCopies+"\"");
		map.add("map_doInventory", String.valueOf(doInventory).trim());
		map.add("map_doMalwareScan", String.valueOf(doMalwareScan).trim());
		map.add("map_doBagInPlace", String.valueOf(doBagInPlace).trim());
		map.add("map_doVerify", String.valueOf(doVerify).trim());
		map.add("map_doWriteBagInfo", String.valueOf(doWriteBagInfo).trim());
		map.add("map_doCopy", String.valueOf(doCopy).trim());
		map.add("map_doExport", String.valueOf(doExport).trim());
		map.add("map_doDeleteFromStaging", String.valueOf(doDeleteFromStaging).trim());
		map.add("map_hostServer", environment.getWebServer());
		restTemplate.exchange(url, HttpMethod.POST, connectionEstablishement.getConnectionRequest(user.getUserName(),user.getPassword(),map), String.class);
	
		return "redirect:/process/management";
	}
}
