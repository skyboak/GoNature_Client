package screenController;

import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.BookingDetail;

public class PopUpWaitinglist extends VisitorScreenController{
	private BookingDetail details;

    @FXML
    public void okConfirmBtn(ActionEvent event) throws Exception
    {
       ((Node)event.getSource()).getScene().getWindow().hide();
       NewBookingController newScreen = new NewBookingController();
       newScreen.start(new Stage());
    }

    
    
    
   
    
    public void start(Stage primaryStage) throws Exception 
	{
    	
    	this.details=ClientController.client.bookingController.getNewBooking();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Confirmation_Waitinglist.fxml"));
    	loader.setController(this); 
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("Waitinglist_Reschedule");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
	
	}
}
