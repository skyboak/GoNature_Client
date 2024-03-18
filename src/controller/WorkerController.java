package controller;

public class WorkerController {
	
	private String workerID;

	private boolean gotResponse = false;
	

	public String getWorkerID() {
		return workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}
	
	private boolean AverageParkStayTime,OnlineBookingCapacity,ParkCapacity;

	public boolean getAverageParkStayTime() {
		return AverageParkStayTime;
	}

	public void setAverageParkStayTime(Boolean averageParkStayTimeResult) {
		AverageParkStayTime = averageParkStayTimeResult;
		gotResponse = true;
	}

	public boolean getOnlineBookingCapacity() {
		return OnlineBookingCapacity;
	}

	public void setOnlineBookingCapacity(boolean onlineBookingCapacity) {
		OnlineBookingCapacity = onlineBookingCapacity;
		gotResponse = true;
	}

	public boolean getParkCapacity() {
		return ParkCapacity;
	}

	public void setParkCapacity(boolean parkCapacity) {
		ParkCapacity = parkCapacity;
		gotResponse = true;
	}

	public boolean isGotResponse() {
		return gotResponse;
	}
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}
	
	
	
	
}
