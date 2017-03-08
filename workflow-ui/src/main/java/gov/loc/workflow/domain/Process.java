package gov.loc.workflow.domain;

import org.springframework.stereotype.Component;

@Component
public class Process {
	
	private String deploymentId;
	private String processDefId;
	private String processName;


	public Process() {}

	public Process(String deploymentId, String processDefId, String processName) {
		this.deploymentId = deploymentId;
		this.processDefId = processDefId;
		this.processName = processName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
