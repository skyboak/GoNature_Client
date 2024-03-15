package controller;

public class MainScreenController {
	
	private boolean workerLoginValid;
	private boolean visitorLoginValid;
	private boolean gotResponse = true;
	

	public boolean isWorkerLoginValid() {
		return workerLoginValid;
	}

	public boolean isGotResponse() {
		return gotResponse;
	}

	public void setWorkerLoginValid(boolean workerLoginValid) {
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
	

	
	
	
	
	

}
