package controller;

import java.util.ArrayList;

import logic.BookingDetail;

public class BookingController {
	private boolean isAvailable;
	private String ID;
	private ArrayList<BookingDetail> myBookingList;

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
	}

	public ArrayList<BookingDetail> getMyBookingList() {
		return myBookingList;
	}
	
	/*public void CreateNewBooking(String vistorID   ) {
		
	}
	
	
	
	public void checkAvialablity(boolean Condition) {
		return Condition;
	}
	
	
	*/
	

}
