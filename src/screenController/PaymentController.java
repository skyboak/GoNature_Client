package screenController;



import java.util.ArrayList;

import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

public class PaymentController extends VisitorScreenController 
{

	private StringBuilder errorMessage = new StringBuilder();
	@FXML
	private Text totalPrice;
	@FXML
	private Text priceBeforeDiscount;
	@FXML
	private ComboBox<String> paymentCombo;
	

	private BookingDetail details;
	

	private void setComboBox() 
	{
		ArrayList<String> payment = new ArrayList<String>();
		payment.add("Cash");
		payment.add("Visa");
		ObservableList<String> list1 = FXCollections.observableArrayList(payment);
		paymentCombo.setItems(list1);

	}
	
	public void price() {
	    int fullPrice, discountPrice;
	    fullPrice = 100 * Integer.parseInt(details.getNumOfVisitors());
	    if ("Guided Group".equals(details.getVisitType())) {
	        discountPrice = (int) (fullPrice * 0.75); // 25% discount
	        // Applying second discount of 12% if payment is "Cash"
	        if ("Cash".equals(paymentCombo.getValue())) {
	            discountPrice = (int) (discountPrice * 0.88); // 12% additional discount
	        }
	    } else {
	        discountPrice = (int) (0.85 * fullPrice);
	    }
	    totalPrice.setText(String.valueOf(discountPrice)); // Display the total price after discounts
	    priceBeforeDiscount.setText(String.valueOf(fullPrice)); // Display the full price before discounts
	}



	
		
	

	public void start(Stage primaryStage,BookingDetail details) throws Exception 
	{
		
		this.details=details;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Payment.fxml"));
    	loader.setController(this); 
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("MyBooking");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	//price();
    }



}