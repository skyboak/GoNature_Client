package screenController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class NewOrderController extends ScreenController {

	@FXML
	private ComboBox<String> visitTypeCombo;
	@FXML
	private ComboBox<String> timeCombo;
	@FXML
	private ComboBox<String> parkNameCombo;
	@FXML
	private ComboBox<String> numOfVisitorsCombo;
	@FXML
	private Text telephoneT;
	@FXML
	private Text emailT;
	@FXML
	private Button pricesBtn;
	@FXML
	private Button nextBtn;
	@FXML
	private Button newOrderBtn;
	@FXML
	private Button myOrderBtn;
	@FXML
	private Button logOutBtn;
	@FXML
	private Button aboutUsBtn;
	@FXML
	private DatePicker datePicker;
	
	public void getVisitType() {
		visitTypeCombo.getValue();
	}
	
}
