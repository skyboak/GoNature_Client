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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	@FXML
	private Button cancel;


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
		 if(paymentCombo.getValue() == null) {
		        errorT.setVisible(true);
		    } 
		 else if(paymentCombo.getValue().equals("Pay at visit")) {
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/payatparkconfirm.fxml"));
				loader.setController(this); // Set the controller
		    	Parent root = loader.load();
		    	Scene scene = new Scene(root);
		    	Stage primaryStage = new Stage();
		    	primaryStage.setTitle("ConfimMsg");
		    	primaryStage.setScene(scene);
		    	primaryStage.initModality(Modality.APPLICATION_MODAL); //blocks all actions until user presses yes/no
		    	primaryStage.initStyle(StageStyle.UNDECORATED); // removes top bar
		    	primaryStage.show();
		        ((Node)event.getSource()).getScene().getWindow().hide();
		    } else {
		        ((Node)event.getSource()).getScene().getWindow().hide();
		        ReceiptScreenController newScreen = new ReceiptScreenController();
		        newScreen.start(new Stage(), details, discountPrice);
		        //TODO add the booking to sql.
		    }
	}
	public void okBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	public void cancelBtn(ActionEvent event) throws Exception
	{
		Message CancelNonPayed = new Message(details.getOrderNumber(),Commands.CancelNonPayedBooking);
		ClientController.client.sendToServer(CancelNonPayed);
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
		ClientController.client.bookingController.setGotResponse();
		((Node)event.getSource()).getScene().getWindow().hide();
		NewBookingController newScreen = new NewBookingController();
		newScreen.start(new Stage());
		
	}
	
	
	public void start(Stage primaryStage) throws Exception 
	{

		this.details=ClientController.client.bookingController.getNewBooking();
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
    	
    	
    } 

}