package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import logic.CancellationDetail;
import logic.ReportDetail;

public class ReportController {
	private boolean gotResponse = true;
	private Map<LocalDate, int[]> visitorStatisticData;
	private ArrayList<CancellationDetail> cancellationReportData;
	private boolean reportCheck;
	private ArrayList<ReportDetail> reportList;
	
	
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
	public void setAddReportCheck(boolean reportCheck) {
		this.reportCheck = reportCheck;
		this.gotResponse = false;
	}

	public boolean isReportCheck() {
		return reportCheck;
	}



	public void setReportList(ArrayList<ReportDetail> reportList) {
		this.reportList = reportList;
		this.gotResponse = false;
	}



	public ArrayList<ReportDetail> getReportList() {
		return reportList;
	}

}
