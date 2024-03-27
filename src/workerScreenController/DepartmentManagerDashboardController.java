package workerScreenController;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import logic.ManagerRequestDetail;
import logic.Message;
import javafx.scene.control.cell.PropertyValueFactory;

public class DepartmentManagerDashboardController extends WorkerScreenController {

	
    @FXML
    private Button parkDashboard;

    @FXML
    private Button parksVisitsReport;

    @FXML
    private Button cancellationReport;

    @FXML
    private Button aboutUs;

    @FXML
    private Button logout;

    @FXML
    private TableView<ManagerRequestDetail> requestTable;

    @FXML
    private TableColumn<ManagerRequestDetail, Integer> requestNumber;

    @FXML
    private TableColumn<ManagerRequestDetail, String> changes;

    private ManagerRequestDetail requestSelected;

    @FXML
    private Button confirmChanges;

    private ArrayList<ManagerRequestDetail> requestList;
    
    @FXML
    void cancellationReportBtn(ActionEvent event) {

    }

    @FXML
    void confirmChangesBtn(ActionEvent event) throws Exception {
    	confirm();
    }
    
    private void confirm() throws Exception {

    	Message msg = null;
    	System.out.println("."+requestSelected.getChangeTo()+".");
    	switch (requestSelected.getChangeTo()) {
    	
    	case "Set Park Capacity of ":
    		msg = new Message(requestSelected,Commands.ChangeParkCapacity);
    		ClientController.client.sendToServer(msg);
    		break;
    	case "Set Online Booking Capacity of ":
    		msg = new Message(requestSelected,Commands.ChangeOnlineBookingCapacity);
    		ClientController.client.sendToServer(msg);
    		break;
    	case "Set Park Stay Time of ":
    		msg = new Message(requestSelected,Commands.ChangeAverageParkStayTime);
    		ClientController.client.sendToServer(msg);
    		break;
    	default:
	  		System.out.println("Shouldn't have gotten here?!?!?!");
	  		break;  
    			
    	}    	
    	
		boolean awaitResponse = false;
            // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    	//requestTable.getItems().remove(requestSelected);
		ClientController.client.workerController.setGotResponse(false);
		System.out.println("Set complete");
		Message requestIndexmsg = new Message(requestSelected.getRequestNumber(),Commands.removeRequest);
		ClientController.client.sendToServer(requestIndexmsg);
		awaitResponse = false;
        // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
		refresh();
    }

    @FXML
    void parksVisitsReportBtn(ActionEvent event) {

    }
    
    @FXML
    void refreshBtn(ActionEvent event) throws Exception {
    	System.out.println("refreshed");
    	requestTable.getItems().clear();
    	refresh();
    }
    
    private void refresh() throws Exception {
    	requestTable.getItems().clear();
    	requestList = null;
    	System.out.println("got refreshed");
		Message msg = new Message(null,Commands.getRequestTable);
		ClientController.client.sendToServer(msg);
		boolean awaitResponse = false;
        // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		ClientController.client.workerController.setGotResponse(false);
		requestList =  ClientController.client.workerController.getRequestList();

		System.out.println(requestList.size());
    	requestTable.getItems().clear();
    	int counter = 1;
        for (ManagerRequestDetail RequestData : requestList) {
        	requestTable.getItems().add(RequestData);}
    	requestTable.refresh();
    }
    
    private void startRequestTable() throws Exception 
    {
        requestNumber.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));
        changes.setCellValueFactory(new PropertyValueFactory<>("changes"));
        // Add a TextFieldTableCell for the changes column
        changes.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add a CheckBoxTableCell for the checkBox column
        //checkBox.setCellFactory(CheckBoxTableCell.forTableColumn(checkBox));
     

        
        refresh();
        
    }
    
    
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/DepartmentManagerScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("GoNature Dashboard");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	startRequestTable();
    	//enables cancel button and saves orderID
    	requestTable.setOnMouseClicked(event -> {
    		confirmChanges.setDisable(false);
    		requestSelected = requestTable.getSelectionModel().getSelectedItem();
    		});
    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
    	scene.setOnMousePressed(event -> {
            if (!requestTable.getBoundsInParent().contains(event.getX(), event.getY())) {
            	requestTable.getSelectionModel().clearSelection();
            	confirmChanges.setDisable(true);
            }
        });

	}
}
