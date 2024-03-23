package screenController;
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
	    	details.setTableName("waitinglist");
	    	
	    }

	    

	    @FXML
	    void okBtn(ActionEvent event) throws Exception //alternative date 
	    { 
	    	details.setTableName("booking");
	    	((Node)event.getSource()).getScene().getWindow().hide();
            PaymentController newScreen = new PaymentController();
            newScreen.start(new Stage(),details);
	    }

	
	    
	    
	    public void start(Stage primaryStage,BookingDetail details) throws Exception 
		{
	    	
	    	this.details=details;
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
