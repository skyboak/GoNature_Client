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

public class MyBookingController extends VisitorScreenController {
	
	private String orderID;
	@FXML
	private TableColumn<String,BookingDetail> orderNumberC;
	@FXML
	private TableColumn<String,BookingDetail> parkNameC;
	@FXML
	private TableColumn<String,BookingDetail> dateTimeC;
	@FXML
	private TableColumn<String,BookingDetail> numOfVisitorsC;
	@FXML
	private TableView<BookingDetail> tableView;
	@FXML
	private Button cancel;
	@FXML
	private Text errorT;
	
	
	public void setTable() {
		orderNumberC.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
    	parkNameC.setCellValueFactory(new PropertyValueFactory<>("parkName"));
    	dateTimeC.setCellValueFactory(new PropertyValueFactory<>("Date"));
    	numOfVisitorsC.setCellValueFactory(new PropertyValueFactory<>("NumOfVisitors"));
		LoginDetail myBookingList = new LoginDetail(ClientController.client.bookingController.getID());
		Message myBookingListMsg = new Message(myBookingList,Commands.VisitorMyBooking);
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
			tableView.getItems().add(i);
	}
	
	// when cancel button is pressed, asks the user to confirm the action
	public void cancelBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConfrimMessageScreen.fxml"));
		loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	Stage primaryStage = new Stage();
    	primaryStage.setTitle("ConfimMsg");
    	primaryStage.setScene(scene);
    	primaryStage.initModality(Modality.APPLICATION_MODAL); //blocks all actions until user presses yes/no
    	primaryStage.initStyle(StageStyle.UNDECORATED); // removes top bar
    	primaryStage.show();
	}
	
	// if the user presses 'yes', deletes the booking
	public void yesBtn(MouseEvent event) throws Exception {
		BookingDetail cancelBookingID = new BookingDetail();
		cancelBookingID.setOrderNumber(orderID);
		Message cancelBooking = new Message(cancelBookingID,Commands.CancelBooking);
		ClientController.client.sendToServer(cancelBooking);
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
		//if cancel booking succeeded
		if(ClientController.client.bookingController.getCancelBooking()) {
			int selectedIndex = tableView.getSelectionModel().getSelectedIndex(); // saves index of the selected row
			tableView.getItems().remove(selectedIndex); // deletes the row from the table
			tableView.getSelectionModel().clearSelection(); // clear tableView selection
			cancel.setDisable(true);
			errorT.setVisible(false);
		}
		//if cancel booking failed
		else
		{
			errorT.setVisible(true);
			errorT.setText("**Error, cant cancel booking at this moment. try again later");
		}
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	public void noBtn(MouseEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MyBookingScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("MyBooking");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setTable();    	
    	//enables cancel button and saves orderID
    	tableView.setOnMouseClicked(event -> {
    		cancel.setDisable(false);
    		orderID = tableView.getSelectionModel().getSelectedItem().getOrderNumber();
    		});
    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
    	scene.setOnMousePressed(event -> {
            if (!tableView.getBoundsInParent().contains(event.getX(), event.getY())) {
                tableView.getSelectionModel().clearSelection();
                cancel.setDisable(true);
            }
        });
	}
}
