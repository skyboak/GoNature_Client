package screenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
		System.exit(0);
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
