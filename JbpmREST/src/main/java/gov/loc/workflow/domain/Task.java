package gov.loc.workflow.domain;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Task {

	private List<String> taskIdList;
	private String taskId;
	private String resubmit;

	public Task() {
	}

	public Task(List<String> taskIdList, String resubmit) {
		this.taskIdList = taskIdList;
		this.resubmit = resubmit;
	}

	public List<String> getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}

	public String getResubmit() {
		return resubmit;
	}

	public void setResubmit(String resubmit) {
		this.resubmit = resubmit;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


}
