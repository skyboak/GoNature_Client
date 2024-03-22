package controller;

import java.time.LocalDate;
import java.util.Map;

public class ReportController {
	private boolean gotResponse = true;
	private Map<LocalDate, int[]> visitorStatisticData;
	
	public boolean isGotResponse() {
		return gotResponse;
	}
	
	
	
	public Map<LocalDate, int[]> getVisitorStatisticData() {
		return visitorStatisticData;
	}

	public void setvisitorStatisticData(Map<LocalDate, int[]> visitorStatisticData) {
		this.visitorStatisticData = visitorStatisticData;
		System.out.println("rpcont"+ visitorStatisticData );
		this.gotResponse = false;
	}

}
