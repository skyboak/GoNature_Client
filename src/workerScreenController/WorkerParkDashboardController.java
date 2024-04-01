package workerScreenController;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import client.ClientController;
import controller.WorkerController;
import enums.Commands;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;
import logic.ParkEntryDetails;
import logic.WorkerDetail;

public class WorkerParkDashboardController extends WorkerScreenController {
	
    @FXML
    private Text workerNameT;
    @FXML
    private Text bookingNumber;
    @FXML
    private Text bookingNumberT;
    @FXML
    private Text visitorsInParkT;
    @FXML
    private Text orderNumberT;
    @FXML
    private Text parkNameT;
    @FXML
    private Text dateT;
    @FXML
    private Text visitTypeT;
    @FXML
    private Text numOfVisitorsT;
    @FXML
    private Text visitorsT;
    @FXML
    private Text errorT;
    @FXML
    private Text orderNumber;  
    @FXML
    private TextField checkOrderT;
    @FXML
    private Button payment;
    @FXML
    private Button approve;    
    @FXML
    private Button checkOrder;
    @FXML
    private Text occasionalVisitNumT;
    @FXML
    private TextField visitorID;
    @FXML
    private Button checkID;
    @FXML
    private Button clearDetails;
    @FXML
    private Button clearDetails1;
    @FXML
    private ComboBox<String> visitorCombo;
    @FXML
    private CheckBox groupGuide;
    @FXML
    private CheckBox enterCheck;
    @FXML
    private CheckBox exitCheck;
    @FXML
    private ComboBox<String> groupCombo;
    @FXML
    private Text dateTimeT;
    @FXML
    private Text dateTime;
    @FXML
    private Text priceT;
    @FXML
    private Text error;
    @FXML
    private Text totalPriceT;;
    private BookingDetail order;
    private BookingDetail ocOrder;
    private int Occasioanlnumber;
    
    //clears booking order details
    public void clearDetailsBtn(ActionEvent event) throws IOException  {

    	order = null;
    	error.setText("");
    	orderNumberT.setText("");
    	checkOrderT.setText("");
		parkNameT.setText("");
		dateT.setText("");
		visitTypeT.setText("");
		numOfVisitorsT.setText("");
		errorT.setText("");
		priceT.setText("");
		totalPriceT.setText("");
		payment.setDisable(true);
		approve.setDisable(true);
		enterCheck.setDisable(true);
		exitCheck.setDisable(true);
		enterCheck.setSelected(false);
		exitCheck.setSelected(false);
    }
    
    //clears occaisonal visitor order details
    public void clearDetails1Btn(ActionEvent event) throws IOException {
    	bookingNumberT.setText("");
    	bookingNumber.setVisible(false);
    	visitorID.setText("");
    	groupGuide.setVisible(false);
		groupCombo.setVisible(false);
		occasionalVisitNumT.setVisible(false);
		visitorCombo.setVisible(false);
		dateTime.setVisible(false);
		dateTimeT.setText("");
		priceT.setText("");
		totalPriceT.setText("");
		payment.setDisable(true);
		approve.setDisable(true);
		groupGuide.setSelected(false);
		enterCheck.setSelected(false);
		exitCheck.setSelected(false);
    }
    
    // according to exit or enter updates current occupancy. 
    public void approveBtn(ActionEvent event) throws IOException  {
    	   	 	
    	if(!orderNumberT.getText().isEmpty()) {
    		//check if park is full
    		if(Integer.parseInt(getCurrentOccupancy()) + Integer.parseInt(order.getNumOfVisitors()) > Integer.parseInt(getMaxOccupancy()) && enterCheck.isSelected()) {
        		error.setText("Park is full!");
        		return;
        	}
	    	error.setText("");
	    	if(enterCheck.isSelected()) {
	    		ParkEntryDetails details = new ParkEntryDetails(
	    				order.getOrderNumber(), order.getVisitType(), order.getParkName(),
	    				Time.valueOf(LocalTime.now()), order.getNumOfVisitors(), getCurrentOccupancy(), Date.valueOf(LocalDate.now())
	    				);
	    		
	    		Message enterPark = new Message(details,Commands.EnterPark);
	    		ClientController.client.sendToServer(enterPark);
	    		boolean awaitResponse = false;
	    		while (!awaitResponse) 
	    		{
	    			try {
	    				Thread.sleep(100);
	    				awaitResponse = ClientController.client.workerController.isGotResponse();
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		ClientController.client.workerController.setGotResponse(false);
	    		visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
	    	}
	    	if(exitCheck.isSelected()) {
	    		ParkEntryDetails details = new ParkEntryDetails(
	    				order.getOrderNumber(), Time.valueOf(LocalTime.now()), order.getParkName(), order.getNumOfVisitors());
	    		Message exitPark = new Message(details,Commands.ExitPark);
	    		ClientController.client.sendToServer(exitPark);
	    		boolean awaitResponse = false;
	    		while (!awaitResponse) 
	    		{
	    			try {
	    				Thread.sleep(100);
	    				awaitResponse = ClientController.client.workerController.isGotResponse();
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		ClientController.client.workerController.setGotResponse(false);
	    		visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
	    	}
    	}	
	    if(!visitorID.getText().isEmpty()) {
    		if(groupGuide.isSelected()) {
    			
    			ocOrder.setNumOfVisitors(groupCombo.getSelectionModel().getSelectedItem());
    			
    			ocOrder.setVisitType("Guided");					
    		}
    		else {
    		if(Integer.valueOf(visitorCombo.getSelectionModel().getSelectedItem()) < 2)
    			ocOrder.setVisitType("Solo");
    		else 
    			ocOrder.setVisitType("Group");
    		ocOrder.setNumOfVisitors(visitorCombo.getSelectionModel().getSelectedItem());
    		}	
    		if(enterCheck.isSelected()) {
	    		ParkEntryDetails details = new ParkEntryDetails(
	    				ocOrder.getOrderNumber(), ocOrder.getVisitType(), ocOrder.getParkName(),
	    				Time.valueOf(LocalTime.now()), ocOrder.getNumOfVisitors(), getCurrentOccupancy(), Date.valueOf(LocalDate.now())
	    				);
	    		
	    		Message enterPark = new Message(details,Commands.EnterPark);
	    		ClientController.client.sendToServer(enterPark);
	    		boolean awaitResponse = false;
	    		while (!awaitResponse) 
	    		{
	    			try {
	    				Thread.sleep(100);
	    				awaitResponse = ClientController.client.workerController.isGotResponse();
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		ClientController.client.workerController.setGotResponse(false);
	    		visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
	    	}
	    	if(exitCheck.isSelected()) {
	    		ParkEntryDetails details = new ParkEntryDetails(
	    				ocOrder.getOrderNumber(), Time.valueOf(LocalTime.now()), ocOrder.getParkName(), ocOrder.getNumOfVisitors());
	    		Message exitPark = new Message(details,Commands.ExitPark);
	    		ClientController.client.sendToServer(exitPark);
	    		boolean awaitResponse = false;
	    		while (!awaitResponse) 
	    		{
	    			try {
	    				Thread.sleep(100);
	    				awaitResponse = ClientController.client.workerController.isGotResponse();
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		ClientController.client.workerController.setGotResponse(false);
	    		visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
	    	}
    		
    		
    	} else {
    		error.setText("");
    		if(enterCheck.isSelected()) {
	    		ParkEntryDetails details = new ParkEntryDetails(
	    				order.getOrderNumber(), order.getVisitType(), order.getParkName(),
	    				Time.valueOf(LocalTime.now()), order.getNumOfVisitors(), getCurrentOccupancy(), Date.valueOf(LocalDate.now())
	    				);
    		}
    		
    		if(groupGuide.isSelected()) {
    			
    			order.setNumOfVisitors(groupCombo.getSelectionModel().getSelectedItem());
    			
    			order.setVisitType("Group");					
    		}
    		else {
    		if(Integer.valueOf(visitorCombo.getSelectionModel().getSelectedItem()) < 2)
    			order.setVisitType("Solo");
    		else
    			order.setVisitType("Group");
    		order.setNumOfVisitors(visitorCombo.getSelectionModel().getSelectedItem());
    		}
    	}
    		
//	    	if(enterCheck.isSelected()) {
//	    		if(Integer.parseInt(getCurrentOccupancy()) + Integer.parseInt(visitorCombo.getSelectionModel().getSelectedItem()) > Integer.parseInt(getMaxOccupancy()) && enterCheck.isSelected()) {
//	        		error.setText("Park is full!");
//	        		return;
//	        	}
//	    		String type = null;
//	    		if(groupGuide.isSelected())
//	    			type = "Guided";
//	    		else if(Integer.valueOf(visitorCombo.getSelectionModel().getSelectedItem()) < 2)
//	    			type = "Solo";
//	    		else
//	    			type = "Group";
//	    		
//	    		//TODO insert to DB
//	    		ParkEntryDetails visit = new ParkEntryDetails(
//	    				bookingNumberT.getText(), type, 
//	    				ClientController.client.workerController.getWorkerDetail().getParkName(), 
//	    				Time.valueOf(LocalTime.now()), 
//	    				);
//	    	}
    	}
    
    
    //checks if the visitor is a group guide. if so, give the visitor option to book a visit as guide.
    public void checkIDBtn(ActionEvent event) throws IOException  {
    	clearDetails.fire();
    	LoginDetail loginDetail = new LoginDetail(visitorID.getText());
		Message loginDetailMsg = new Message(loginDetail,Commands.CheckIfGroupGuide);
		
		boolean awaitResponse = true;
		try {
			ClientController.client.sendToServer(loginDetailMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
            // wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.mainScreenController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//if the visitor is not a group guide
		if(!ClientController.client.mainScreenController.isGroupGuideLoginValid()) 
		{
			groupGuide.setVisible(false);
			groupCombo.setVisible(false);
			occasionalVisitNumT.setVisible(true);
			visitorCombo.setVisible(true);
		}
		//if the visitor is a group guide
		else {
			visitorCombo.setVisible(true);
			occasionalVisitNumT.setVisible(true);
			groupGuide.setVisible(true);
		}
		bookingNumber.setVisible(true);
		bookingNumberT.setVisible(true);
		dateTime.setVisible(true);
		dateTimeT.setVisible(true);
		payment.setDisable(false);
		
		//give the occaisonal visitor booking number
		Message msg = new Message(null,Commands.OccasionalBookingNumber);
		boolean awaitResponse1 = false;
		try {
			ClientController.client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
            // wait for response
		while (!awaitResponse1) {
			try {
				Thread.sleep(100);
				awaitResponse1 = ClientController.client.bookingController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.bookingController.setGotResponse();
		Occasioanlnumber = Integer.valueOf(ClientController.client.bookingController.getOccasioanlBookingNumber()) + 1;
		bookingNumberT.setText(String.valueOf(Occasioanlnumber));
		LocalDateTime currentDate = LocalDateTime.now();
		String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		dateTimeT.setText(formattedDate);
		priceT.setText("100");
		totalPriceT.setText("100");
		this.ocOrder = new BookingDetail();
		ocOrder.setOrderNumber(String.valueOf(Occasioanlnumber));
		ocOrder.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());
		
		}
		
		
    
    
    //checks if order exist in DB. if yes, order information is displayed
    public void checkOrderBtn(ActionEvent event) throws IOException {
    	clearDetails1.fire();
    	payment.setDisable(true);
		approve.setDisable(true);
    	BookingDetail orderDet = new BookingDetail();
    	orderDet.setOrderNumber(checkOrderT.getText());
    	orderDet.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());
    	//get booking details
    	Message checkOrder = new Message(orderDet,Commands.BookingDetails);
    	try {
			ClientController.client.sendToServer(checkOrder);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.bookingController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.bookingController.setGotResponse();
		order = ClientController.client.bookingController.getBookingDetails();
			
		//if booking exist in the database
		if(order != null) {
			      				
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			LocalDate currentDate = LocalDate.now();
			LocalTime currentTime = LocalTime.now();
			LocalDateTime visitDateTime = LocalDateTime.parse(order.getDate(), formatter);      
			LocalDate visitDate = visitDateTime.toLocalDate(); // Extracting just the date part
			LocalTime visitTime = visitDateTime.toLocalTime();// Extracting just the time part
			if (!visitDate.isEqual(currentDate)) {
			    errorT.setText("**Sorry, booking date is not today");
			    return;
			}
								
			errorT.setText("");
			orderNumberT.setText(order.getOrderNumber());
			parkNameT.setText(order.getParkName());
			dateT.setText(order.getDate());
			visitTypeT.setText(order.getVisitType());
			numOfVisitorsT.setText(order.getNumOfVisitors());	        	
				
									
			//if booking not payed
			if(order.getPaymentStatus().equals("NO")) {
				payment.setDisable(false);
				enterCheck.setDisable(true);
				exitCheck.setDisable(true);
				int fullpricetoshow;
		    	int discountPrice;	    	
	    		fullpricetoshow = 100 * Integer.parseInt(order.getNumOfVisitors());
	    	    if ("Guided".equals(order.getVisitType())) 
	    	        discountPrice = (int) (fullpricetoshow * 0.9); // 25% discount
	    	    else 
	    	        discountPrice = fullpricetoshow;
	    	    priceT.setText(String.valueOf(fullpricetoshow) + "₪");
	        	totalPriceT.setText(String.valueOf(discountPrice) + "₪");
			}
			//if booking payed
			else {
				enterCheck.setDisable(false);
				exitCheck.setDisable(false);
				
			}
		}
		else {
			//CheckBookInDB
	    	Message checkBook = new Message(checkOrderT.getText(),Commands.CheckBookInDB);
	    	try {
				ClientController.client.sendToServer(checkBook);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	boolean awaitResponsebook = false;
			while (!awaitResponsebook) 
			{
				try {
					Thread.sleep(100);
					awaitResponsebook = ClientController.client.workerController.isGotResponse();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ClientController.client.workerController.setGotResponse(false);
			int flag = ClientController.client.workerController.getCheckBookInDB();
			
			if(flag==1){
				
				ocOrder.setOrderNumber(getCurrentOccupancy());
				
				//Maybe add here the order number
				exitCheck.setDisable(false);
				approve.setDisable(false);
			}
			//getCheckBookInDB
			//if booking doesnt exist in the database
			else
			{
				order = null;
				errorT.setText("**Error, booking number doesnt exist!");
				orderNumberT.setText("");
				parkNameT.setText("");
				dateT.setText("");
				visitTypeT.setText("");
				numOfVisitorsT.setText("");
				payment.setDisable(true);
				enterCheck.setDisable(true);
				exitCheck.setDisable(true);
				
			}	
		}
    }
  
    //if user didnt pay from advance or it is an occaisional visitor
    public void paymentBtn(ActionEvent event) throws IOException {
		payment.setDisable(true);
    	enterCheck.setDisable(false);
    	exitCheck.setDisable(false);
    	approve.setDisable(false);
    	if(!orderNumberT.getText().isEmpty()) {
	    	BookingDetail BD = new BookingDetail();
	    	BD.setOrderNumber(order.getOrderNumber());
	    	BD.setPaymentStatus("YES");
	    	Message msg = new Message(BD,Commands.ChangePaymentStatusInDB);
	    	try {
				ClientController.client.sendToServer(msg);
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	}
    }
    
    // sets visuals
    public void setScreen() {
    	workerNameT.setText("Welcome back, " + ClientController.client.workerController.getWorkerDetail().getName());   	
    	visitorsInParkT.setText("Visitors in " + ClientController.client.workerController.getWorkerDetail().getParkName() + ":");   	
    	visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
    	
    	ArrayList<String> visit = new ArrayList<String>();
		ArrayList<String> group = new ArrayList<String>();
		
		for(int i=1; i < 6 ; i++) {
			visit.add(String.valueOf(i));
		}
		for(int i=1; i < 16 ; i++) {
			group.add(String.valueOf(i));
		}
		
		ObservableList<String> list1 = FXCollections.observableArrayList(visit);
		visitorCombo.setItems(list1);
		ObservableList<String> list2 = FXCollections.observableArrayList(group);
		groupCombo.setItems(list2);
    }
    
    // returns currOccupancy of the park
    public String getCurrentOccupancy() {
    	String parkName = ClientController.client.workerController.getWorkerDetail().getParkName();
    	Message currOccupancy = new Message(parkName,Commands.CurrentOccupancy);
    	try {
			ClientController.client.sendToServer(currOccupancy);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
    	return ClientController.client.workerController.getCurrentOccupancy();
    }
    
    // returns maxOccupancy of the park
    public String getMaxOccupancy() {
    	String parkName = ClientController.client.workerController.getWorkerDetail().getParkName();
    	Message maxOccupancy = new Message(parkName,Commands.MaxOccupancy);
    	try {
			ClientController.client.sendToServer(maxOccupancy);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
    	return ClientController.client.workerController.getMaxOccupancy();
    }
    
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/WorkerParkDashboardScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("WorkerScreen");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setScreen();
    	//disables 'CheckOrder' button whenever the textfield is empty
    	checkOrderT.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if TextField is empty or not
            if (newValue.trim().isEmpty()) {
                // If TextField is empty, disable the button
            	checkOrder.setDisable(true);
            } else {
                // If TextField has text, enable the button
            	checkOrder.setDisable(false);
            }
        });
    	//disables 'checkOrder' button whenever the textfield is empty
    	visitorID.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if TextField is empty or not
            if (newValue.trim().isEmpty() || visitorID.getText().length() != 9) {
                // If TextField is empty, disable the button
            	checkID.setDisable(true);
            } else {
                // If TextField has text, enable the button
            	checkID.setDisable(false);
            }
        });
    	// if group guide check box is clicked, show group combobox
    	groupGuide.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
            	groupCombo.setVisible(true);
    			visitorCombo.setVisible(false);
            } else {
            	groupCombo.setVisible(false);
    			visitorCombo.setVisible(true);
            }
        });
    	// listener for approve button
    	enterCheck.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue || enterCheck.isSelected()) {
                approve.setDisable(false); // Enable button if either checkbox is selected
                exitCheck.setSelected(false);
            } 
            if(!enterCheck.isSelected() && !exitCheck.isSelected()) {
                approve.setDisable(true);  // Disable button if neither checkbox is selected
            }
        });
    	// listener for approve button
    	exitCheck.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue || exitCheck.isSelected()) {
                approve.setDisable(false); // Enable button if either checkbox is selected
                enterCheck.setSelected(false);
            } 
            if(!enterCheck.isSelected() && !exitCheck.isSelected()) {
                approve.setDisable(true);  // Disable button if neither checkbox is selected
            }
        });
    	// Listener for visitorCombo
    	visitorCombo.setOnAction(event -> {
    	    if (visitorCombo.getSelectionModel().getSelectedItem() != null) {
    	        int numOfVisitors = Integer.parseInt(String.valueOf(visitorCombo.getSelectionModel().getSelectedItem()));  	        
    	        numOfVisitors = 100 * numOfVisitors;
    	        priceT.setText(String.valueOf(numOfVisitors) + "₪");
    	        totalPriceT.setText(String.valueOf(numOfVisitors) + "₪");
    	    }
    	});
    	// Listener for groupCombo
    	groupCombo.setOnAction(event -> {
    	    if (groupCombo.getSelectionModel().getSelectedItem() != null) {
    	        int numOfVisitors = Integer.parseInt(String.valueOf(groupCombo.getSelectionModel().getSelectedItem()));
    	        priceT.setText(String.valueOf(100 * numOfVisitors) + "₪");
    		    int price =(int) ((100 * numOfVisitors) - (0.1 * (100 * numOfVisitors)));
    		    totalPriceT.setText(String.valueOf(price) + "₪");
    	    }
    	});
    }
}
