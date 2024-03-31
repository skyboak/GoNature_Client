package workerScreenController;
import javafx.scene.image.Image;

import java.io.IOException;

import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.LoginDetail;
import logic.ManagerRequestDetail;
import logic.Message;
import workerScreenController.WorkerScreenController;


public class ParkManagerScreenController extends WorkerScreenController{
	
	@FXML
	private TextField ParkCapacityT;
	@FXML
	private TextField OnlineBookingCapacityT;
	@FXML
	private TextField AverageParkStayTimeT;
	@FXML
	private ImageView parkImgT;
	@FXML
	private Text parkNameT;
	@FXML
	private Text visitorAmountT;
	
	private String parkName;
	
	private String getParkCapacity() {
		return ParkCapacityT.getText();
	}
	private String getOnlineBookingCapacity() {
		return OnlineBookingCapacityT.getText();
	}
	private String getAverageParkStayTime() {
		return AverageParkStayTimeT.getText();
	}
	
	public void refreshBtn(ActionEvent event) throws Exception{
		setVisitorAmount();
	}
	
	public void setParkCapacityBtn(ActionEvent event) throws Exception
	{
		String valueTo = getParkCapacity();
		//Message msg = new Message(valueTo,Commands.ChangeParkCapacity);
		ManagerRequestDetail managerRequestDetail = new ManagerRequestDetail(parkName,"Set Park Capacity of ",valueTo);
		//ClientController.client.workerController.addRequest(managerRequestDetail);
		
		Message addmsg = new Message(managerRequestDetail,Commands.AddManagerRequestDetail);
		ClientController.client.sendToServer(addmsg);
		
	}
	
	public void setOnlineBookingCapacityBtn(ActionEvent event) throws Exception
	{
		String valueTo = getOnlineBookingCapacity();
		//Message msg = new Message(valueTo,Commands.ChangeOnlineBookingCapacity);
		ManagerRequestDetail managerRequestDetail = new ManagerRequestDetail(parkName,"Set Online Booking Capacity of ",valueTo);
		
		Message addmsg = new Message(managerRequestDetail,Commands.AddManagerRequestDetail);
		ClientController.client.sendToServer(addmsg);
	}
	
	public void setAverageParkStayTimeBtn(ActionEvent event) throws Exception
	{
		
		String valueTo = getAverageParkStayTime();
		//Message msg = new Message(valueTo,Commands.ChangeAverageParkStayTime);
		ManagerRequestDetail managerRequestDetail = new ManagerRequestDetail(parkName,"Set Park Stay Time of ",valueTo);

		
		Message addmsg = new Message(managerRequestDetail,Commands.AddManagerRequestDetail);
		ClientController.client.sendToServer(addmsg);
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/ParkManagerScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("GoNature Dashboard");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setPark(ClientController.client.workerController.getWorkerDetail().getParkName());
    	setVisitorAmount();

	}
	
	private void setPark(String parkName) {
		this.parkName = parkName;
		//getcapacity()
		parkNameT.setText(parkName);
		String imgS = "/images/" + parkName + ".jpeg";
		Image img = new Image(imgS);
		parkImgT.setImage(img);
	}
	
	private void setVisitorAmount() throws Exception 
	{
		Message msg = new Message(parkName,Commands.getVisitorAmountInPark);
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
		int VistorAmount =  ClientController.client.workerController.getVistorAmount();
		visitorAmountT.setText(Integer.toString(VistorAmount));
	}

	
	
	
	
	
	
}
