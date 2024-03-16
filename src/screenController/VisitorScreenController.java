package screenController;

import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import logic.LoginDetail;
import logic.Message;

public class VisitorScreenController extends ScreenController {


	private String ID;
	
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getID() {
		return ID;
	}

	public void pricesBtn(ActionEvent event) throws Exception {
		
	}
	
	public void newBookingBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		NewBookingController newScreen = new NewBookingController();
		newScreen.start(new Stage());
	}
	
	public void myBookingBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		MyBookingController newScreen = new MyBookingController();
		newScreen.start(new Stage());
	}
	
	public void logoutBtn(ActionEvent event) throws Exception {
		LoginDetail logoutDetail = new LoginDetail(ClientController.client.bookingController.getID());
		Message logoutDetailMsg = new Message(logoutDetail,Commands.VisitorLogOut);
		ClientController.client.sendToServer(logoutDetailMsg);
		((Node)event.getSource()).getScene().getWindow().hide();
		LoginController newScreen = new LoginController();
		newScreen.start(new Stage());
	}
	
	public void aboutUsBtn(ActionEvent event) throws Exception {
		
	}
}
