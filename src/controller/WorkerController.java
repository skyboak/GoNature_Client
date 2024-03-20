package controller;

import logic.WorkerDetail;

public class WorkerController {
	
	private boolean gotResponse = false;
	private WorkerDetail workerDetail;


	
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

	public WorkerDetail getWorkerDetail() {
		return workerDetail;
	}

	public void setWorkerDetail(WorkerDetail workerDetail) {
		this.workerDetail = workerDetail;
	}


	
	
	
	
}
