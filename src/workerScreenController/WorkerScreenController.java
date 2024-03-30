package workerScreenController;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import reportsScreenController.CancellationReportScreenController;
import reportsScreenController.VisitorStatisticReportScreenController;
import reportsScreenController.VisitsReportScreenController;
import screenController.ScreenController;
import logic.LoginDetail;
import logic.Message;
import screenController.LoginController;

public class WorkerScreenController extends ScreenController {
	

	
	
	
	//Those Buttons Will Transition From Screen to Screen in the workers Screens
//	
	public  void DashboardBtn(ActionEvent event) throws Exception{
		System.out.println("DUMB FUCK");
	}
//	
//	public void parkAvailabilityReportBtn(ActionEvent event) throws Exception{
//		
//	}
//	
	
	public void VisitorStatisticReportBtn(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		VisitorStatisticReportScreenController newScreen = new VisitorStatisticReportScreenController();
		newScreen.start(new Stage());
		
	}
//	
	
	
	public void VisitsReportBtn(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		VisitsReportScreenController newScreen = new VisitsReportScreenController();
		newScreen.start(new Stage());
	}
	
	
	public void cancellationReportBtn(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		CancellationReportScreenController newScreen = new CancellationReportScreenController();
		newScreen.start(new Stage());
	}
	
	
	public void logoutBtn(ActionEvent event) throws Exception {
		//LoginDetail logoutDetail = new LoginDetail(ClientController.client.bookingController.getID());
		Message logoutDetailMsg = new Message(ClientController.client.workerController.getWorkerDetail().getWorkerId(),Commands.WorkerLogOut);
		ClientController.client.sendToServer(logoutDetailMsg);
		((Node)event.getSource()).getScene().getWindow().hide();
		LoginController newScreen = new LoginController();
		newScreen.start(new Stage());
	}



	

    
   
    
    void parksVisitsReportBtn(ActionEvent event) {
    	System.out.println("DUMB FUCK");
    }
	
	
	
	

}
