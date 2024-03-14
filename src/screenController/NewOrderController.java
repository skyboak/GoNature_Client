package screenController;


import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.BookingDetails;


public class NewOrderController extends ScreenController {

	private StringBuilder errorMessage = new StringBuilder();
	@FXML
	private ComboBox<String> visitTypeCombo;
	@FXML
	private ComboBox<String> timeCombo;
	@FXML
	private ComboBox<String> parkNameCombo;
	@FXML
	private ComboBox<String> numOfVisitorsCombo;
	@FXML
	private ComboBox<String> combobox;
	@FXML
	private TextField telephoneT;
	@FXML
	private TextField emailT;
	@FXML
	private TextArea errorT;
	@FXML
	private DatePicker dateCombo;
	@FXML
	private CheckBox guideCB;
	
	
	public void getVisitType() {
		visitTypeCombo.getValue();
	}
	
	public void getTime() {
		timeCombo.getValue();
	}
	
	public void getParkName() {
		parkNameCombo.getValue();
	}
	
	public void getNumOfVisitors() {
		numOfVisitorsCombo.getValue();
	}
	
	public void getTelephone() {
		telephoneT.getText();
	}
	
	public void getEmail() {
		emailT.getText();
	}
	
	public void nextBtn(ActionEvent event) throws Exception {
		if (validateInputs()) {
			BookingDetails details = new BookingDetails();
            details.setVisitType(visitTypeCombo.getValue());
            details.setTime(timeCombo.getValue());
            details.setParkName(parkNameCombo.getValue());
            details.setNumOfVisitors(numOfVisitorsCombo.getValue());
            details.setTelephone(telephoneT.getText());
            details.setEmail(emailT.getText());
            details.setVisitDate(dateCombo.getValue());
            //Call next window
            //((Node)event.getSource()).getScene().getWindow().hide();		
		}
		else
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NotValidInputErrorScreen.fxml"));
			loader.setController(this); // Set the controller
			Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	Stage primaryStage = new Stage();
	    	primaryStage.setScene(scene);
	    	RemoveTopBar(primaryStage,root);
	    	primaryStage.show();
	    	errorT.setText(errorMessage.toString()); //FIX THIS!!!
		}
	}
	
	private boolean validateInputs() {

	    // Check if all required fields are filled
	    if (visitTypeCombo.getValue() == null) {
	        errorMessage.append("Visit type is required.\n");
	    }
	    if (timeCombo.getValue() == null) {
	        errorMessage.append("Time is required.\n");
	    }
	    if (parkNameCombo.getValue() == null) {
	        errorMessage.append("Park name is required.\n");
	    }
	    if (numOfVisitorsCombo.getValue() == null) {
	        errorMessage.append("Number of visitors is required.\n");
	    }
	    if (telephoneT.getText().isEmpty()) {
	        errorMessage.append("Telephone number is required.\n");
	    }
	    if (emailT.getText().isEmpty()) {
	        errorMessage.append("Email is required.\n");
	    }
	    if (dateCombo.getValue() == null) {
	        errorMessage.append("Visit date is required.\n");
	    }
	    return errorMessage.length() == 0 ? true : false;
	}
	
	// clears error text and close the widnow
	public void okBtn(ActionEvent event) throws Exception {
		errorMessage.delete(0, errorMessage.length());
		((Node)event.getSource()).getScene().getWindow().hide();;
	}
	
	private void setComboBox() {
		ArrayList<String> parkNames = new ArrayList<String>();
		ArrayList<String> NumOfVisitors = new ArrayList<String>();
		ArrayList<String> Time = new ArrayList<String>();
		
		parkNames.add("YellowStone");
		parkNames.add("Hyde Park");
		parkNames.add("YellowStone");
		for(int i=1; i < 16 ; i++) {
			NumOfVisitors.add(String.valueOf(i));
		}
		Time.add("06:00 - 10:00");
		Time.add("10:00 - 14:00");
		Time.add("14:00 - 18:00");
		
		ObservableList<String> list1 = FXCollections.observableArrayList(parkNames);
		parkNameCombo.setItems(list1);
		ObservableList<String> list2 = FXCollections.observableArrayList(NumOfVisitors);
		numOfVisitorsCombo.setItems(list2);
		ObservableList<String> list3 = FXCollections.observableArrayList(Time);
		timeCombo.setItems(list3);
	}
	
//	private boolean checkGuide() {
//		if user is guide:
//			return false;
//		return true;
//	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NewOrderScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("NewOrder");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	//guideCB.setDisable(checkGuide());
	}
}
