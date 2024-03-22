package screenController;
import javafx.scene.image.Image;
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
import logic.Message;
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
	
	public void setParkCapacityBtn(ActionEvent event) throws Exception
	{
		Message msg = new Message(getParkCapacity(),Commands.ChangeParkCapacity);
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
		if(ClientController.client.workerController.getParkCapacity()) {
			System.out.println("Set complete");
		}else {System.out.println("Set failed");}
	}
	
	public void setOnlineBookingCapacityBtn(ActionEvent event) throws Exception
	{
		
		Message msg = new Message(getOnlineBookingCapacity(),Commands.ChangeOnlineBookingCapacity);
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
		if(ClientController.client.workerController.getOnlineBookingCapacity()) {
			System.out.println("Set complete");
		}else {System.out.println("Set failed");}		
	}
	
	public void setAverageParkStayTimeBtn(ActionEvent event) throws Exception
	{
		Message msg = new Message(getAverageParkStayTime(),Commands.ChangeAverageParkStayTime);
		System.out.println("C" + getAverageParkStayTime());
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
		if(ClientController.client.workerController.getAverageParkStayTime()) {
			System.out.println("Set complete");
		}else {System.out.println("Set failed");}
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

	}
	
	private void setPark(String parkName) {
		this.parkName = parkName;
		//getcapacity()
		parkNameT.setText(parkName);
		String imgS = "/images/" + parkName + ".jpeg";
		Image img = new Image(imgS);
		parkImgT.setImage(img);
		
		
	}
	public void logoutBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	
	
	
	
	
}
