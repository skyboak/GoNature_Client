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
    private ComboBox visitorCombo;
    @FXML
    private CheckBox groupGuide;
    @FXML
    private CheckBox enterCheck;
    @FXML
    private CheckBox exitCheck;
    @FXML
    private ComboBox groupCombo;
    @FXML
    private Text email;
    @FXML
    private Text telephone;
    @FXML
    private Text dateTime;
    @FXML
    private TextField emailT;
    @FXML
    private TextField telephoneT;
    @FXML
    private TextField dateTimeT;
    @FXML
    private Text priceT;
    @FXML
    private Text error;
    @FXML
    private Text totalPriceT;;
    private BookingDetail order;
    
    
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
    }
    
    //clears occaisonal visitor order details
    public void clearDetails1Btn(ActionEvent event) throws IOException {
    	orderNumberT.setText("");
    	visitorID.setText("");
    	groupGuide.setVisible(false);
		groupCombo.setVisible(false);
		occasionalVisitNumT.setVisible(false);
		visitorCombo.setVisible(false);
		email.setVisible(false);
		telephone.setVisible(false);
		dateTime.setVisible(false);
		emailT.setVisible(false);
		telephoneT.setVisible(false);
		dateTimeT.setVisible(false);
		emailT.setText("");
		telephoneT.setText("");
		priceT.setText("");
		totalPriceT.setText("");
		payment.setDisable(true);
		approve.setDisable(true);
    }
    
    // according to exit or enter updates current occupancy. 
    public void approveBtn(ActionEvent event) throws IOException  {
    	if(Integer.parseInt(getCurrentOccupancy()) + Integer.parseInt(order.getNumOfVisitors()) > Integer.parseInt(getMaxOccupancy())) {
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
		email.setVisible(true);
		telephone.setVisible(true);
		dateTime.setVisible(true);
		emailT.setVisible(true);
		telephoneT.setVisible(true);
		dateTimeT.setVisible(true);
    }
    
    //checks if order exist in DB. if yes, order information is displayed
    public void checkOrderBtn(ActionEvent event) throws IOException {
    	clearDetails1.fire();
    	payment.setDisable(true);
		approve.setDisable(true);
    	BookingDetail orderDet = new BookingDetail();
    	orderDet.setOrderNumber(checkOrderT.getText());
    	orderDet.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());
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
//			//checks if booking is not old
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//	        LocalDateTime currentDateTime = LocalDateTime.now();
//	        LocalDateTime visitDateTime = LocalDateTime.parse(order.getDate(),formatter);      
//	        if(visitDateTime.isBefore(currentDateTime)) {
//	        	errorT.setText("**Sorry, wait until time of booking");
//	        	return;
//	        }
					
			errorT.setText("");
			orderNumberT.setText(order.getOrderNumber());
			parkNameT.setText(order.getParkName());
			dateT.setText(order.getDate());
			visitTypeT.setText(order.getVisitType());
			numOfVisitorsT.setText(order.getNumOfVisitors());	        	
				
			//if booking not payed
			if(order.getPaymentStatus().equals("NO")) {
				payment.setDisable(false);
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
		}
    }
  
    //if user didnt pay from advance or it is an occaisional visitor
    public void paymentBtn(ActionEvent event) throws IOException {
    	//TODO show receipt to visitor and update payment to 'yes'
    	payment.setDisable(true);
    	enterCheck.setDisable(false);
		exitCheck.setDisable(false);
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
		
		visitorCombo.getSelectionModel().select("1");
		groupCombo.getSelectionModel().select("1");
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
    }

}
