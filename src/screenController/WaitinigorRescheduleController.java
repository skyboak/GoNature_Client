package screenController;
import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.Message;

public class WaitinigorRescheduleController extends VisitorScreenController
{

	
	    @FXML
	    private Button enter;

	    @FXML
	    private TableView<?> dateTime;

	    @FXML
	    private TableColumn<?, ?> pointsColumn_tableExam;

	    @FXML
	    private Button ok;

		private BookingDetail details;

	    @FXML
	    void enterBtn(ActionEvent event) throws Exception //enter waiting list
	    {
	    	//set the apropriate table
	    	details.setTableName("waitinglist");
	    	//sent the bookwing details with the new table info to server
	    	Message newBookin = new Message(details, Commands.newBookingToDB);
	    	ClientController.client.sendToServer(newBookin);
            boolean awaitResponse = false;
    		while (!awaitResponse) 
    		{
    			try {
    				Thread.sleep(100);
    				awaitResponse = ClientController.client.bookingController.isGotResponse();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		//pop Confirmation_Waitinglist.fxml to screen.
	    	
	    }

	    

	    @FXML
	    void okBtn(ActionEvent event) throws Exception //alternative date 
	    { 
	    	
	    	details.setTableName("booking");
	    	((Node)event.getSource()).getScene().getWindow().hide();
            PaymentController newScreen = new PaymentController();
            newScreen.start(new Stage());
	    }

	
	    
	    
	    public void start(Stage primaryStage) throws Exception 
		{
	    	
	    	this.details=ClientController.client.bookingController.getNewBooking();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Waitinglist_Reschedule.fxml"));
	    	loader.setController(this); 
	    	Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	primaryStage.setTitle("Waitinglist_Reschedule");
	    	primaryStage.setScene(scene);
	    	RemoveTopBar(primaryStage,root);
	    	primaryStage.show();
		}

}
