package workerScreenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import reportsScreenController.VisitorStatisticReportScreenController;
import screenController.ScreenController;

public class WorkerScreenController extends ScreenController {
	

	
	
	
	//Those Buttons Will Transition From Screen to Screen in the workers Screens
//	
//	public  void parkDashboardBtn(ActionEvent event) throws Exception{
//		
//	}
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
	
	
//	public void VisitsReportBtn(ActionEvent event) throws Exception{
//		
//	}
//	
//	public void CancellationReportBtn(ActionEvent event) throws Exception{
//		
//	}
	
	
	
	
	
	
	

}
