package logic;

public class CancellationDetail {
	
	
	private String orderNumber;
	private String visitTime;
	private int numVisitors;
	private String visitType;
	private String visitorID;
	   
	   
	public CancellationDetail(String orderNumber, String visitTime, int numVisitors, String visitType,
			String visitorID) {
		this.orderNumber = orderNumber;
		this.visitTime = visitTime;
		this.numVisitors = numVisitors;
		this.visitType = visitType;
		this.visitorID = visitorID;
	}
	   
	   
	public String getOrderNumber() {
		return orderNumber;
	}
	
	
	public String getVisitTime() {
		return visitTime;
	}
	
	
	public int getNumVisitors() {
		return numVisitors;
	}
	
	
	public String getVisitType() {
		return visitType;
	}
	
	
	public String getVisitorID() {
		return visitorID;
	}
}
