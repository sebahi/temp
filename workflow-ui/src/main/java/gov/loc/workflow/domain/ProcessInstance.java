package gov.loc.workflow.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProcessInstance {
	private String processInstanceId;
	private String processId;
	private String status;
	private String processInstanceName;
	private String initiator;
	private Date startDate;
	
	public ProcessInstance() { }
	
	public ProcessInstance(String processInstanceId, String processId, String status, String processInstanceName,
			String initiator, Date startDate) {
		this.processInstanceId = processInstanceId;
		this.processId = processId;
		this.status = status;
		this.processInstanceName = processInstanceName;
		this.initiator = initiator;
		this.startDate = startDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
