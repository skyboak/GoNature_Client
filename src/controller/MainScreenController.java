package controller;

import logic.WorkerDetail;
/**
 * The MainScreenController class manages login validations and responses for different types of users.
 */
public class MainScreenController {
	

	private WorkerDetail workerLoginValid;
	private boolean visitorLoginValid;
	private boolean GroupGuideLoginValid;
	private boolean gotResponse = true;
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}
	
	public WorkerDetail getWorkerLoginValid() {
		return workerLoginValid;
	}

	public boolean isGotResponse() {
		return gotResponse;
	}

	public void setWorkerLoginValid(WorkerDetail workerLoginValid) {
		this.workerLoginValid = workerLoginValid;
		this.gotResponse  = false;
	}
	
	
	public boolean isVisitorLoginValid() {
		return visitorLoginValid;
	}
	
	public void setVisitorLoginValid(boolean visitorLoginValid) {
		this.visitorLoginValid = visitorLoginValid;
		this.gotResponse  = false;
	}
	
	
	
	public boolean isGroupGuideLoginValid() {
		return GroupGuideLoginValid;
	}
	
	public void setGroupGuideValid(boolean GroupGuideLoginValide) {
		this.GroupGuideLoginValid = GroupGuideLoginValide;
		this.gotResponse  = false;
	}
	

	
	
	
	
	
	

}
