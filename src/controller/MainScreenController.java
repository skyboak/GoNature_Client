package controller;

import logic.WorkerDetail;

public class MainScreenController {
	
	private WorkerDetail workerLoginValid;
	private boolean visitorLoginValid;
	private boolean GroupGuideLoginValid;
	private boolean gotResponse = true;
	
	
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
