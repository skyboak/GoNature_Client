package controller;

import java.util.ArrayList;

import logic.BookingDetail;

public class BookingController {
	private boolean isAvailable;
	private String ID;
	private ArrayList<BookingDetail> myBookingList;
	private boolean isGotResponse = false;
	private boolean isCanceled = false;;

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public void setMyBookingTableList(ArrayList<BookingDetail> myBookingList) {
		this.myBookingList = myBookingList;
		this.isGotResponse = true;
	}

	public ArrayList<BookingDetail> getMyBookingList() {
		return myBookingList;
	}
	
	public void setCancelBooking(boolean result) {
		isCanceled = result;
		this.isGotResponse = true;
	}
	
	public boolean getCancelBooking() {
		return isCanceled;	
	}
	
	public void setGotResponse() {
		isGotResponse = false;
	}

	public boolean isGotResponse() {
		return isGotResponse;
	}
	
	/*public void CreateNewBooking(String vistorID   ) {
		
	}
	
	
	
	public void checkAvialablity(boolean Condition) {
		return Condition;
	}
	
	
	*/
	

}
