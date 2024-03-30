package controller;
//Client
import java.util.ArrayList;

import logic.BookingDetail;

public class BookingController {
	private boolean isAvailable;
	private String ID;
	private ArrayList<BookingDetail> myBookingList;
	private boolean isGotResponse = false;
	private boolean isCanceled = false;
	private String existOrder;
	private boolean bookingAvailable = false;
	private BookingDetail newBooking;
	private ArrayList<String> SixSlots;
	

	public BookingDetail getNewBooking() {
		return newBooking;
	}

	public void setNewBooking(BookingDetail newBooking) {
		this.newBooking = newBooking;
	}

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
	
	public String getCheckIfExistBooking() {
		return existOrder;	
	}
	
	public void setCheckIfExistBooking(String result) {
		existOrder = result;
		this.isGotResponse = true;
	}
	
	public boolean getCheckIfBookingAvailable() {
		return bookingAvailable;	
	}
	
	public void setCheckIfBookingAvailable(boolean result) {
		bookingAvailable = result;
		this.isGotResponse = true;
	}

	public ArrayList<String> getSixSlots() {
		return SixSlots;
	}

	public void setSixSlots(ArrayList<String> sixSlots) {
		this.isGotResponse = true;
		SixSlots = sixSlots;
	}

	public void setCancelNonPayedBook() {
		this.isGotResponse = true;
	}

	public void setBookingDetails(BookingDetail bd) {
		newBooking = bd;
		this.isGotResponse = true;	

	}
	
	public void setPaymentStatus() {
		this.isGotResponse = true;
		
	}
	
	/*public void CreateNewBooking(String vistorID   ) {
		
	}
	
	
	
	public void checkAvialablity(boolean Condition) {
		return Condition;
	}
	
	
	*/
	

}
