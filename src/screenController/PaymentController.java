package screenController;



import java.util.ArrayList;

import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
	private Text errorT;
	@FXML
	private ComboBox<String> paymentCombo;

	private BookingDetail details;
	
	public int fullpricetoshow,discountPrice;
	
	private void setComboBox() 
	{
		ArrayList<String> payment = new ArrayList<String>();
		payment.add("Pay at visit");
		payment.add("Pay now");
		ObservableList<String> list1 = FXCollections.observableArrayList(payment);
		paymentCombo.setItems(list1);

	}
	
	public void price() {
	//    int fullPrice, discountPrice;
		fullpricetoshow = 100 * Integer.parseInt(details.getNumOfVisitors());
	    if ("Guided Group".equals(details.getVisitType())) {
	    	fullpricetoshow-=100;
	        discountPrice = (int) (fullpricetoshow * 0.75); // 25% discount
	        // Applying second discount of 12% if payment is "Cash"
	        if ("Pay now".equals(paymentCombo.getValue()))
	        {
	            discountPrice = (int) (discountPrice * 0.88); // 12% additional discount
	        }
	    } else 
	    {
	        discountPrice = (int) (0.85 * fullpricetoshow);
	    }
	    totalPrice.setText(String.valueOf(discountPrice) + "₪"); // Display the total price after discounts
	    priceBeforeDiscount.setText(String.valueOf(fullpricetoshow) + "₪"); // Display the full price before discounts
	    
	    
	}

	public void finishBtn(ActionEvent event) throws Exception 
	{
		if(paymentCombo.getValue()==null)
			errorT.setVisible(true);
		else {
			((Node)event.getSource()).getScene().getWindow().hide();
			ReceiptScreenController newScreen = new ReceiptScreenController();
			newScreen.start(new Stage(),details,discountPrice);
			//TODO add the booking to sql.
		}
			
		
		
	
	}
	public void start(Stage primaryStage,BookingDetail details) throws Exception 
	{
		
		this.details=details;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Payment.fxml"));
    	loader.setController(this); 
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("Payment");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	errorT.setVisible(false);
    	 paymentCombo.setOnAction(event -> {
    	        String selectedPayment = paymentCombo.getSelectionModel().getSelectedItem();
    	        if (selectedPayment != null) {
    	        	errorT.setVisible(false);
    	            if (selectedPayment.equals("Pay at visit")) {
    	               price();
    	            } else if (selectedPayment.equals("Pay now")) {
    	               price();
    	            }
    	        }
    	    });
    	
    	
    	
//    	paymentCombo.setOnMouseClicked(event -> {
//    	if (paymentCombo.getSelectionModel().isSelected(1)) 
//    		System.out.println("cash");
//    	
//    	if (paymentCombo.getSelectionModel().isSelected(0)) 
//    		System.out.println("visa");
//    	});
    	//price();
    }

//    	scene.setOnMousePressed(event -> {
//        if (!tableView.getBoundsInParent().contains(event.getX(), event.getY())) {
//            tableView.getSelectionModel().clearSelection();
//            cancel.setDisable(true);
 

	}