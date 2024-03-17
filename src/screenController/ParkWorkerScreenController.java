package screenController;

import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import logic.Message;
public class ParkWorkerScreenController extends WorkerScreenController{
	
	
	private TextField ParkCapacityT;
	private TextField OnlineBookingCapacityT;
	private TextField AverageParkStayTimeT;
	
	
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
		Message msg = new Message(getOnlineBookingCapacity(),Commands.ChangeParkCapacity);
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
		Message msg = new Message(getAverageParkStayTime(),Commands.ChangeParkCapacity);
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
}
