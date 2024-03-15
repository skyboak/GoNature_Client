package screenController;

import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectionScreenController extends ScreenController
{
	@FXML
	private Button connectBtn = null;
    @FXML
    private Button exitBtn = null;
	@FXML
	private TextField ipAddressT;
	@FXML
	private TextField portT;
	
	
	private String getIpAddress() {
		return ipAddressT.getText();
	}
	
	private Integer getPort() {
		return Integer.valueOf(portT.getText());
	}
	
	public void connectBtn(ActionEvent event) throws Exception {
		ClientController clientController = new ClientController(getIpAddress(), getPort());
		clientController.display("Connected");
		((Node)event.getSource()).getScene().getWindow().hide();
		LoginController newScreen = new LoginController();
		newScreen.start(new Stage());
	}
	
	
//	Example of how to override the x button to do your bidding
//	@Override
//	public void xBtn(MouseEvent event) {
//		System.out.println("damn");
//		System.exit(0);
//	}
	
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConnectionScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("GoNatureClient");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
	}
}
