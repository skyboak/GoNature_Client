package logic;

import java.io.Serializable;

public class WorkerDetail implements Serializable{
	
	private int workerId;
	private String role;
	private String parkName;

	public WorkerDetail(int workerId,String role) {
		this.workerId = workerId;
		this.role = role;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public int getWorkerId() {
		return workerId;
	}
	public String getRole() {
		return role;
	}
	

}
