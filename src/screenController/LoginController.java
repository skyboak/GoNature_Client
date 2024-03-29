package screenController;

import java.util.ArrayList;
import client.ClientController;
import client.ClientUI;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.LoginDetail;
import logic.Message;
import logic.WorkerDetail;
import workerScreenController.ParkManagerScreenController;
import workerScreenController.DepartmentManagerDashboardController;

public class LoginController extends ScreenController {

	@FXML
	private Text usernameS;
	@FXML
	private Text passwordS;
	@FXML
	private Text idS;
	@FXML
	private Text errorT;
	@FXML
	private TextField usernameT;
	@FXML
	private TextField passwordT;
	@FXML
	private TextField idT;
	@FXML
	private ImageView icon1;
	@FXML
	private ImageView icon2;
	@FXML
	private ImageView icon3;
	@FXML
	private CheckBox workerCB;
	
	
	private String getID() {
		return idT.getText();
	}
	
	private String getUsername() {
		return usernameT.getText();
	}
	
	private String getPassword() {
		return passwordT.getText();
	}
	
	public void checkBox(ActionEvent event) throws Exception {
		errorT.setVisible(false);
		if(workerCB.isSelected())
			setVisableDisable(true , false);
		else
			setVisableDisable(false , true);
	}
	
	public void setVisableDisable(boolean Condition1, boolean Condition2) {
		idS.setVisible(Condition2);
		idT.setVisible(Condition2);
		idT.setDisable(Condition1);
		icon1.setVisible(Condition2);
		usernameS.setVisible(Condition1);
		usernameT.setVisible(Condition1);
		passwordS.setVisible(Condition1);
		passwordT.setVisible(Condition1);
		usernameT.setDisable(Condition2);
		passwordT.setDisable(Condition2);
		icon2.setVisible(Condition1);
		icon3.setVisible(Condition1);
	}
	
	public void loginBtn(ActionEvent event) throws Exception {
		errorT.setVisible(false);
		//TODO: Add Implementation.
		if(workerCB.isSelected()) {
			LoginDetail loginDetail = new LoginDetail(getUsername(),getPassword());
			Message loginDetailMsg = new Message(loginDetail,Commands.CheckWorkerLogin);
			
			boolean awaitResponse = true;
			ClientController.client.sendToServer(loginDetailMsg);
	            // wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
					awaitResponse = ClientController.client.mainScreenController.isGotResponse();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ClientController.client.mainScreenController.setGotResponse(true);
			WorkerDetail workerDetail = ClientController.client.mainScreenController.getWorkerLoginValid();
			//move to workerscreen
			System.out.println(workerDetail.getRole());
			ClientController.client.workerController.setWorkerDetail(workerDetail);
			if(workerDetail.getRole().equals("Park Manager")) {
				System.out.println("Entring Park Manager..");
				System.out.println(workerDetail.getParkName());
				((Node)event.getSource()).getScene().getWindow().hide();
				ParkManagerScreenController newScreen = new ParkManagerScreenController();
				newScreen.start(new Stage());
			}

			if(workerDetail.getRole().equals("Department Manager")) {
				System.out.println("Entring Department Manager..");
				((Node)event.getSource()).getScene().getWindow().hide();
				DepartmentManagerDashboardController newScreen = new DepartmentManagerDashboardController();
				newScreen.start(new Stage());
			}
			

			if(workerDetail.getRole().equals("Park Worker")) {
				((Node)event.getSource()).getScene().getWindow().hide();
				WorkerParkDashboardController newScreen = new WorkerParkDashboardController();
				newScreen.start(new Stage());
			}

		}
		else
		{
			if(getID().length() != 9 ) {
				errorT.setText("**ID must be 9 digits");
				errorT.setVisible(true);
				return;
			}
			LoginDetail loginDetail = new LoginDetail(getID());
			Message loginDetailMsg = new Message(loginDetail,Commands.CheckVisitorLogin);
			ClientController.client.sendToServer(loginDetailMsg);
			boolean awaitResponse = true;
			while (awaitResponse) {
				try {
					Thread.sleep(100);
					awaitResponse = ClientController.client.mainScreenController.isGotResponse();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
			if(!ClientController.client.mainScreenController.isVisitorLoginValid()) {
				//Visitor is logged in  
				errorT.setVisible(true);
				errorT.setText("**User is already logged in");
			}
			else {
				//move to visitor screen
				((Node)event.getSource()).getScene().getWindow().hide();
				NewBookingController newScreen = new NewBookingController();
				ClientController.client.bookingController.setID(getID()); // saves user ID
				newScreen.start(new Stage());
			}
		}

	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("LoginScreen");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
	}
}
