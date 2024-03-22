package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import logic.CancellationDetail;

public class ReportController {
	private boolean gotResponse = true;
	private Map<LocalDate, int[]> visitorStatisticData;
	private ArrayList<CancellationDetail> cancellationReportData;
	public boolean isGotResponse() {
		return gotResponse;
	}
	
	
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}



	public Map<LocalDate, int[]> getVisitorStatisticData() {
		return visitorStatisticData;
	}

	public void setvisitorStatisticData(Map<LocalDate, int[]> visitorStatisticData) {
		this.visitorStatisticData = visitorStatisticData;
		this.gotResponse = false;
	}



	public void setCancellationReportData(ArrayList<CancellationDetail> cancellationReportData) {
		this.cancellationReportData = cancellationReportData;
		this.gotResponse = false;
		
	}

}
