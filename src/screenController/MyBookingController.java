package screenController;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientController;
import controller.BookingController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

public class MyBookingController extends VisitorScreenController {
	
	@FXML
	private TableColumn orderNumberC;
	@FXML
	private TableColumn parkNameC;
	@FXML
	private TableColumn dateTimeC;
	@FXML
	private TableColumn numOfVisitorsC;
	@FXML
	private TableView<BookingDetail> tableView;
	
	public void setTable() {
		LoginDetail myBookingList = new LoginDetail(ClientController.client.bookingController.getID());
		Message myBookingListMsg = new Message(myBookingList,Commands.VisitorMyBooking);
		try {
			ClientController.client.sendToServer(myBookingListMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<BookingDetail> myList = ClientController.client.bookingController.getMyBookingList();
		for (BookingDetail i : myList) {
			
		}
		//tableView.getItems().add(new BookingDetail("a"));
	}
	
	public void cancelBtn(ActionEvent event) throws Exception {
		
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
    	orderNumberC.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
    	parkNameC.setCellValueFactory(new PropertyValueFactory<>("parkName"));
    	dateTimeC.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
    	numOfVisitorsC.setCellValueFactory(new PropertyValueFactory<>("NumOfVisitors"));
	}
}
