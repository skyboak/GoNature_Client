package screenController;


import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import client.ClientController;
import client.ClientUI;
import controller.BookingController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DateCell;
import javafx.util.Callback;
//import server.ServerUI;



public class NewBookingController extends VisitorScreenController {

	private StringBuilder errorMessage = new StringBuilder();
	@FXML
	private ComboBox<String> timeCombo;
	@FXML
	private ComboBox<String> parkNameCombo;
	@FXML
	private ComboBox<String> numOfVisitorsCombo;
	@FXML
	private ComboBox<String> numOfVisitorsGCombo;
	@FXML
	private TextField telephoneT;
	@FXML
	private TextField emailT;
	@FXML
	private TextArea errorT;
	@FXML
	private DatePicker dateCombo;
	@FXML
	private CheckBox guide;
	
	public void getTime() {
		timeCombo.getValue();
	}
	
	public void getParkName() {
		parkNameCombo.getValue();
	}
	
	public void getNumOfVisitors() {
		numOfVisitorsCombo.getValue();
	}
	
	public void getNumOfVisitorsG() {
		numOfVisitorsGCombo.getValue();
	}
	
	public void getTelephone() {
		telephoneT.getText();
	}
	
	public void getEmail() {
		emailT.getText();
	}
	
	private void errorscreen(String toshow) throws IOException
	{
		errorMessage.append(toshow+".\n");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NotValidInputErrorScreen.fxml"));
		loader.setController(this); // Set the controller
		Parent root = loader.load();
    	Scene scene = new Scene(root);
    	Stage primaryStage = new Stage();
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	errorT.setText(errorMessage.toString());
	}
	
	private void configureDatePicker() {
	    // Set the date picker to show only dates that are at least 24 hours ahead
	    dateCombo.setDayCellFactory(new Callback<DatePicker, DateCell>() {
	        @Override
	        public DateCell call(DatePicker param) {
	            return new DateCell() {
	                @Override
	                public void updateItem(LocalDate item, boolean empty) {
	                    super.updateItem(item, empty);
	                    // Disable selection of dates less than 24 hours ahead
	                    LocalDateTime minimumDateTime = LocalDateTime.now().plusHours(24);
	                    LocalDate minimumDate = minimumDateTime.toLocalDate();
	                    setDisable(item.isBefore(minimumDate));
	                }
	            };
	        }
	    });
	}

	
	//check park capacity{}
	//check timeslot by maxstaytime
	
	public void nextBtn(ActionEvent event) throws Exception {
			BookingDetail details = new BookingDetail();
            details.setTime(timeCombo.getValue());
            details.setParkName(parkNameCombo.getValue());
            details.setNumOfVisitors(numOfVisitorsCombo.getValue());
            details.setEmail(emailT.getText());
            details.setVisitorID(ClientController.client.bookingController.getID());
 
            // Format the phone number into "xxx-xxx-xxxx" format
            String phoneNumber = telephoneT.getText().replaceAll("[^\\d]", ""); // Remove non-numeric characters
            String formattedPhoneNumber = String.format("%s-%s-%s",phoneNumber.substring(0, 3),phoneNumber.substring(3, 6),phoneNumber.substring(6));
            details.setTelephone(formattedPhoneNumber);

			if(guide.isSelected())
			{
				details.setVisitType("Guided Group");
				details.setNumOfVisitorsG(numOfVisitorsGCombo.getValue());
				
			}
			else
			{
				if (Integer.valueOf(numOfVisitorsCombo.getValue())>1)
				{
					details.setVisitType("Group");					
				}
				else
					details.setVisitType("Solo");				
			}
			//
    		if (validateInputs() && checkCurrentTime()) 
    		{

            
            // Combine date and time into LocalDateTime object
            LocalDateTime dateTime = dateCombo.getValue().atTime(LocalTime.parse(details.getTime()));
            
            // Format LocalDateTime into the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedDateTime = dateTime.format(formatter);
            details.setDate(formattedDateTime);
            
            
            //create message to server + command to server
            Message newbooking = new Message(details, Commands.CheckParkCapacity);
            
            //send message to server
            ClientController.client.sendToServer(newbooking);
            boolean awaitResponse = false;
    		while (!awaitResponse) {
    			try {
    				Thread.sleep(100);
    				awaitResponse = ClientController.client.bookingController.isGotResponse();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		ClientController.client.bookingController.setGotResponse();
            if(ClientController.client.bookingController.getCheckIfBookingAvailable())
            {
            	((Node)event.getSource()).getScene().getWindow().hide();
                PaymentController newScreen = new PaymentController();
                newScreen.start(new Stage(),details);
            	System.out.println("The booking is available in db.");            	
            	
            }
            else 
            {
            	
            	//System.out.println("The id:"+ ClientController.client.bookingController.getID()+" is already booked at:"+formattedDateTime+" in the database.");
            	errorscreen("The id: "+ ClientController.client.bookingController.getID()+" is already booked at: "+formattedDateTime);
            	// add cho
            }
            
            System.out.println(details.toString());
            //System.out.println(formattedDateTime);
            //if(bookingcontroller.isAvailable(send details to server) == true) then save details to DB
            //else waiting list
  
    		}
			else
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NotValidInputErrorScreen.fxml"));
				loader.setController(this); // Set the controller
				Parent root = loader.load();
		    	Scene scene = new Scene(root);
		    	Stage primaryStage = new Stage();
		    	primaryStage.setScene(scene);
		    	RemoveTopBar(primaryStage,root);
		    	primaryStage.show();
		    	errorT.setText(errorMessage.toString());
			}
	}
	
	private boolean validateInputs() {

	    // Check if all required fields are filled
	    if (timeCombo.getValue() == null) {
	        errorMessage.append("Time is required.\n");
	    }
	    if (parkNameCombo.getValue() == null) {
	        errorMessage.append("Park name is required.\n");
	    }
	    if (numOfVisitorsCombo.getValue() == null && numOfVisitorsGCombo.getValue() == null) {
	        errorMessage.append("Number of visitors is required.\n");
	    }
	    if (telephoneT.getText().isEmpty()) {
	        errorMessage.append("Telephone number is required.\n");
	    }
	    else if (telephoneT.getText().length() != 10) {
	        errorMessage.append("Phone number must be 10 digits long.\n");
	    }
	    if (emailT.getText().isEmpty()) {
	        errorMessage.append("Email is required.\n");
	    }
	    else {
	        // Validate email format using regular expression
	        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
	        if (!emailT.getText().matches(emailPattern)) {
	            errorMessage.append("Email is not in a valid format.\n");
	        }
	    }
	    
	    if (dateCombo.getValue() == null) {
	        errorMessage.append("Visit date is required.\n");
	    }
	    return errorMessage.length() == 0 ? true : false;
	}
	
	
	public boolean checkCurrentTime() {
	    LocalDate date = dateCombo.getValue();
	    String hour = timeCombo.getValue();

	    // Splitting the hour string to extract hours and minutes
	    String[] timeParts = hour.split(":");
	    int selectedHour = Integer.parseInt(timeParts[0]);
	    int selectedMinute = Integer.parseInt(timeParts[1]);

	    // Creating a LocalTime object for the selected time
	    LocalTime arrivalTime = LocalTime.of(selectedHour, selectedMinute);

	    // Getting the current date and time
	    LocalDate currentDate = LocalDate.now();
	    LocalTime currentTime = LocalTime.now();

	    // Combine date and time into LocalDateTime object
	    LocalDateTime bookingDateTime = date.atTime(arrivalTime);

	    // Calculate the minimum allowed booking time (24 hours from now)
	    LocalDateTime minimumBookingTime = LocalDateTime.now().plusHours(24);

	    // Comparing the selected date and time with the minimum allowed booking time
	    if (bookingDateTime.isBefore(minimumBookingTime)) {
	        errorMessage.append("You can only book 24 hours or more in advance. Please select a future time.\n");
	        return false;
	    }
	    return true;
	}


	
	public boolean checkGuide() {
		LoginDetail loginDetail = new LoginDetail(ClientController.client.bookingController.getID());
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
		if(!ClientController.client.mainScreenController.isGroupGuideLoginValid()) 
		{
			//isnt group guide 
			return true;
		}
		else {
			//he is group guide
			return false;
		}
	}
	
	 
	
	
	public void guideCB(ActionEvent event) throws Exception {
		if(guide.isSelected()) {
			numOfVisitorsCombo.setDisable(true);
			numOfVisitorsCombo.setVisible(false);
			numOfVisitorsGCombo.setDisable(false);
			numOfVisitorsGCombo.setVisible(true);
		}
		else
		{
			numOfVisitorsCombo.setDisable(false);
			numOfVisitorsCombo.setVisible(true);
			numOfVisitorsGCombo.setDisable(true);
			numOfVisitorsGCombo.setVisible(false);
		}
	}
	
	
	// clears error text and close the window
	public void okBtn(ActionEvent event) throws Exception {
		errorMessage.delete(0, errorMessage.length());
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	private void setComboBox() {
		ArrayList<String> parkNames = new ArrayList<String>();
		ArrayList<String> NumOfVisitors = new ArrayList<String>();
		ArrayList<String> NumOfVisitorsG = new ArrayList<String>();
		ArrayList<String> Time = new ArrayList<String>();
		
		parkNames.add("BlackForest");
		parkNames.add("Hyde Park");
		parkNames.add("YellowStone");
		
		for(int i=1; i < 6 ; i++) {
			NumOfVisitors.add(String.valueOf(i));
		}
		for(int i=1; i < 16 ; i++) {
			NumOfVisitorsG.add(String.valueOf(i));
		}
		//last time is closing time-visit length time
		//last book is closingtime - maxstaytime
		//isntead of i=8 i=hour time
		for(int i=8;i<=16;i++)
		{
			if(i<10)
				Time.add("0"+String.valueOf(i)+":00");
			else
				Time.add(String.valueOf(i)+":00");
		}
		
		
		ObservableList<String> list1 = FXCollections.observableArrayList(parkNames);
		parkNameCombo.setItems(list1);
		ObservableList<String> list2 = FXCollections.observableArrayList(NumOfVisitors);
		numOfVisitorsCombo.setItems(list2);
		ObservableList<String> list3 = FXCollections.observableArrayList(Time);
		timeCombo.setItems(list3);
		ObservableList<String> list4 = FXCollections.observableArrayList(NumOfVisitorsG);
		numOfVisitorsGCombo.setItems(list4);
		numOfVisitorsCombo.getSelectionModel().select("1");
		numOfVisitorsGCombo.getSelectionModel().select("1");//check if guide get enter alone?
	}
	

	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NewBookingScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("NewBooking");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	configureDatePicker();
    	guide.setDisable(checkGuide());//Permission for group guide only
	}
}
