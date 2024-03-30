package screenController;



import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientController;
import enums.Commands;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

public class WaitinigorRescheduleController extends VisitorScreenController
{

	

		@FXML
		private TableView<String> dateTime;

		@FXML
		private TableColumn<String, String> pointsColumn_tableExam;
		
		@FXML
		private Button ok;
 

		private BookingDetail details;
		
		private String NewDateAndTime;

	    @FXML
	    public void enterBtn(ActionEvent event) throws Exception //enter waiting list
	    {
	    	//set the apropriate table
	    	details.setTableName("waitinglist");
	    	//sent the bookwing details with the new table info to server
	    	Message newBookin = new Message(details, Commands.newBookingToDB);
	    	ClientController.client.sendToServer(newBookin);
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
    		//pop Confirmation_Waitinglist.fxml to screen.
           ((Node)event.getSource()).getScene().getWindow().hide();
           PopUpWaitinglist newScreen = new PopUpWaitinglist();
           newScreen.start(new Stage());   
    	
	    	
	    }
	    

	    

	    @FXML
	    public void okBtn(ActionEvent event) throws Exception //alternative date 
	    { 
	    	details.setDate(NewDateAndTime);
	    	details.setTableName("booking");
	    	ClientController.client.bookingController.setNewBooking(details);
	    	
	    	Message newBookin = new Message(details, Commands.newBookingToDB);
	    	ClientController.client.sendToServer(newBookin);
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
	    	((Node)event.getSource()).getScene().getWindow().hide();
            PaymentController newScreen = new PaymentController();
            newScreen.start(new Stage());
	    	
	    }
	    	
	    
	    
	    
	   
	    
	    public void start(Stage primaryStage) throws Exception 
		{
	    	ArrayList<String> availableBooks[];
	    	
	    	this.details=ClientController.client.bookingController.getNewBooking();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Waitinglist_Reschedule.fxml"));
	    	loader.setController(this); 
	    	Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	primaryStage.setTitle("Waitinglist_Reschedule");
	    	primaryStage.setScene(scene);
	    	RemoveTopBar(primaryStage,root);
	    	primaryStage.show();
	    	CheckavailableSlotinDB();
	    	dateTime.setOnMouseClicked(event -> {
	    		if (dateTime.getSelectionModel().getSelectedItem() != null) {
	    			ok.setDisable(false);
	    			NewDateAndTime = dateTime.getSelectionModel().getSelectedItem().toString();
	    	    } 
	    	});
	    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
	    	scene.setOnMousePressed(event -> {
	            if (!dateTime.getBoundsInParent().contains(event.getX(), event.getY())) {
	            	dateTime.getSelectionModel().clearSelection();
	                ok.setDisable(true);
	            }
	        });
	    	
		}
	    
	    public void CheckavailableSlotinDB() throws IOException
	    {
	    	pointsColumn_tableExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
	    	//create message to server + command to server
            Message CheckSlot = new Message(details, Commands.CheckSixSlots);
            
            //send message to server
            ClientController.client.sendToServer(CheckSlot);
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
    		ArrayList<String> AvailableSlots = ClientController.client.bookingController.getSixSlots();
    		
    		// Convert ArrayList to ObservableList
    	    ObservableList<String> observableSlots = FXCollections.observableArrayList(AvailableSlots);
    	    dateTime.getItems().clear(); // Clear previous data
    	    dateTime.setItems(observableSlots); // Set the items directly to the TableView
	    	
	    }

}
