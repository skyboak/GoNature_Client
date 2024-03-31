package screenController;

import java.io.IOException;
import java.util.ArrayList;
import client.ClientController;
import enums.Commands;
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


public class NotficationController extends VisitorScreenController {
	private StringBuilder popupscreen = new StringBuilder();

	
	private String orderID;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private TableView<BookingDetail> BookingTable;

    @FXML
    private TableColumn<String, BookingDetail> Bookingnumber;

    @FXML
    private TableColumn<String, BookingDetail> ParkName;

    @FXML
    private TableColumn<String, BookingDetail> DateAndTime;

    @FXML
    void ConfirmBtn(ActionEvent event) throws IOException {
    	BookingDetail alertToConfirm = new BookingDetail();
    	alertToConfirm.setOrderNumber(orderID);
    	Message alertToConfirmMsg = new Message(alertToConfirm, Commands.AlertConfirmInDB);
    	ClientController.client.sendToServer(alertToConfirmMsg);
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
		if(ClientController.client.bookingController.getAlertedConfirmStatus())
		{
			popupscreen("Confirm");
		}
		else
		{
			
			popupscreen("Error");
		}
		((Node)event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    void cancelBtn(ActionEvent event) throws IOException {
    	BookingDetail alertToCancel = new BookingDetail();
    	alertToCancel.setOrderNumber(orderID);
    	Message alertToCancelMsg = new Message(alertToCancel, Commands.AlertCancelInDB);
    	ClientController.client.sendToServer(alertToCancelMsg);
    	((Node)event.getSource()).getScene().getWindow().hide();

    }
    
	private void popupscreen(String toshow) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/2Hours"+toshow+".fxml"));
	        loader.setController(this); // Set the controller
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage primaryStage = new Stage();
	        primaryStage.setScene(scene);
	        RemoveTopBar(primaryStage,root);
	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle any IOException that might occur during loading the FXML file
	    }
	}
    
    
    public void setTable() {
    	Bookingnumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
    	ParkName.setCellValueFactory(new PropertyValueFactory<>("parkName"));
    	DateAndTime.setCellValueFactory(new PropertyValueFactory<>("Date"));
		LoginDetail myBookingList = new LoginDetail(ClientController.client.bookingController.getID());
		Message myBookingListMsg = new Message(myBookingList,Commands.AlertBooks);
		try {
			ClientController.client.sendToServer(myBookingListMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		ArrayList<BookingDetail> myList = ClientController.client.bookingController.getMyBookingList();
		for (BookingDetail i : myList) 
			BookingTable.getItems().add(i);
	}
    
    
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BookingNearby.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("AlertToClient");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setTable();    	
    	//enables cancel button and saves orderID
    	BookingTable.setOnMouseClicked(event -> {
    		if (BookingTable.getSelectionModel().getSelectedItem() != null) {
    	        cancel.setDisable(false);
    	        confirm.setDisable(false);
    	        orderID = BookingTable.getSelectionModel().getSelectedItem().getOrderNumber();
    	    } else {
    	        // If no row is selected, disable the cancel button
    	        cancel.setDisable(true);
    	        }
    		});
    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
    	scene.setOnMousePressed(event -> {
            if (!BookingTable.getBoundsInParent().contains(event.getX(), event.getY())) {
            	BookingTable.getSelectionModel().clearSelection();
                cancel.setDisable(true);
                confirm.setDisable(true);
            }
        });
    	
	}

}



