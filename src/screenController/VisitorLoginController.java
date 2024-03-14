package screenController;

import java.util.ArrayList;
import client.ClientController;
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

public class VisitorLoginController extends ScreenController {

	@FXML
	private Text usernameS;
	@FXML
	private Text passwordS;
	@FXML
	private Text idS;
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
			if(!ClientController.client.mainScreenController.isWorkerLoginValid()) {
				//error in usernameandpasssword. 
				System.out.println("error worker");
			}
			else {
				//move to workerscreen
				System.out.println("good worker");
			}
		}
		else
		{
			LoginDetail loginDetail = new LoginDetail(getID());
			Message loginDetailMsg = new Message(loginDetail,Commands.CheckVisitorLogin);
			ClientController.client.sendToServer(loginDetailMsg);

			((Node)event.getSource()).getScene().getWindow().hide();
			NewOrderController newScreen = new NewOrderController();
			newScreen.start(new Stage());
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VisitorLoginScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("VisitorLogin");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
	}
}
