package reportsScreenController;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import workerScreenController.WorkerScreenController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.DateDetail;
import logic.Message;
import logic.ReportDetail;


public class ParkAvailabilityReportScreenController extends WorkerScreenController implements Initializable {
	
	    @FXML
	    private Button parkDashboard;

	    @FXML
	    private Button visitorStatisticReport;

	    @FXML
	    private Button parkAvailabilityReport;
	    
	    @FXML
	    private Button logout;

	    @FXML
	    private BarChart<String, Number> UsageChart;

	    @FXML
	    private CategoryAxis xAxisR;

	    @FXML
	    private NumberAxis yAxisR;

	    @FXML
	    private DatePicker FromDate;

	    @FXML
	    private DatePicker ToDate;

	    @FXML
	    private Button Show;

	    @FXML
	    private Button BackBtn;

	    @FXML
	    private Text errortxt;
	    
	    @FXML
	    private Text msgToUser;
	    
	    
	    private DateDetail dateDetail;
	    
	    @FXML
	    private TextField directoryTextField;

	    @FXML
	    private Button sendReportToSystem;
	    
	    private DirectoryChooser directoryChooser;
	    
	    @FXML
	    public void sendReportToSystemBtn(ActionEvent event) throws IOException {
	        WritableImage image = UsageChart.snapshot(new SnapshotParameters(), null);

	        // Get the directory path from the text field
	        String directoryPath = directoryTextField.getText();

	        // Create a File object with the directory path and image file name
	        File outputFile = new File(directoryPath, "VisitorStatistic.png");

	        // Save the WritableImage to the file
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
	        
	        // Create the ReportDetail object with the image byte array
	        ReportDetail report = new ReportDetail(dateDetail.getParkName(), dateDetail.getStart(), dateDetail.getEnd(), "Visitor Statistic Report", outputFile.getAbsolutePath(), directoryTextField.getText());
	        System.out.println("gina");
	        Message msg = new Message(report, Commands.AddReport);
	        ClientController.client.sendToServer(msg);
	        System.out.println("chloe");
	        boolean awaitResponse = true;
	        while (awaitResponse) {
	            try {
	                Thread.sleep(100);
	                awaitResponse = ClientController.client.reportController.isGotResponse();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        ClientController.client.reportController.setGotResponse(true);
	        if (ClientController.client.reportController.isReportCheck())
	            msgToUser.setText("Report Created And Saved To Server Successfully");
	        else {
	            msgToUser.setText("Error Creating Report");
	        }
	        errortxt.setFill(Color.RED);
	        sendReportToSystem.setDisable(true);
	    }
	    
	    @FXML
	    void backBtn(ActionEvent event) {
	    	UsageChart.getData().clear();
	    }
	    

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			UsageChart.getData().clear();
			
			setDatePickerCellFactory(FromDate);
	        setDatePickerCellFactory(ToDate);
	        
	        directoryChooser = new DirectoryChooser();
			
		}


	    @FXML
	    void showBtn(ActionEvent event) throws IOException {
	    	 // HashMap to store visitor data
	        Map<String, Integer> visitorstatData;
	        // Variables to store the fromDate and toDate
	        LocalDate fromDate = FromDate.getValue();
	        LocalDate toDate = ToDate.getValue();
	        if (fromDate != null && toDate != null) { // Check if both dates are selected
	            dateDetail = new DateDetail(fromDate, toDate);
	            dateDetail = new DateDetail(fromDate, toDate);
		        dateDetail.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName()); 
		        
		        System.out.println(dateDetail.getEnd());
		        Message msg = new Message(dateDetail, Commands.GetVisitorStatReport);
		        ClientController.client.sendToServer(msg);
		        
		        boolean awaitResponse = true;
		        
		        while (awaitResponse) {
		            try {
		                Thread.sleep(100);
		                awaitResponse = ClientController.client.reportController.isGotResponse();
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		        ClientController.client.reportController.setGotResponse(true);
		        visitorstatData =  ClientController.client.reportController.getVisitorStatData();
		        CreateAVBarChar(visitorstatData);
	        }else {
	        	errortxt.setVisible(true);
	            errortxt.setText("Please select both From and To dates.");
	            errortxt.setFill(Color.RED);
	        }
	    }


	
	    private void CreateAVBarChar(Map<String, Integer> visitorStatMap) {
	    	UsageChart.getData().clear();
	        xAxisR = new CategoryAxis();
	        yAxisR = new NumberAxis();
	        //visitorChart.setAnimated(false);
	        UsageChart.setBarGap(1.0d);
	        UsageChart.setCategoryGap(10.0);
	        
	        // Set the category axis to display hours as labels from 8:00 to 16:00
	        List<String> hours = new ArrayList<>();
	        for (int hour = 8; hour <= 16; hour++) {
	            hours.add(String.format("%02d:00", hour));
	        }
	        xAxisR.setCategories(FXCollections.observableArrayList(hours));

	        // Create a new series for the chart
	        XYChart.Series<String, Number> series = new XYChart.Series<>();

	        // Add data from the visitorStatMap to the series
	        for (String hour : hours) {
	            Integer value = visitorStatMap.getOrDefault(hour, 0);
	            series.getData().add(new XYChart.Data<>(hour, value));
	        }

	        // Set the name of the series
	        series.setName("Average Park Usage");

	        // Set the category axis to display hours as labels
	        xAxisR.setLabel("Hours");

	        // Set the number axis to display appropriate range and label
	        yAxisR.setLabel("Average Usage");
	        
	        yAxisR.setTickUnit(2); // Set the tick unit spacing to 2
	        // Add the series to the chart
	        UsageChart.getData().add(series);
	    }



	    
	   
		   private void setDatePickerCellFactory(DatePicker datePicker) {
		        // Create a custom day cell factory to disable dates of today and future
		        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
		            @Override
		            public DateCell call(DatePicker param) {
		                return new DateCell() {
		                    @Override
		                    public void updateItem(LocalDate item, boolean empty) {
		                        super.updateItem(item, empty);
		                        if (item.isAfter(LocalDate.now())) {
		                            // Disable dates of future
		                            setDisable(true);
		                            //change color for disabled cells
		                            setStyle("-fx-background-color: #ffc0cb;"); 
		                        }
		                    }
		                };
		            }
		        };

		        // Set the custom day cell factory to the date picker
		        datePicker.setDayCellFactory(dayCellFactory);
		    }



		public void start(Stage primaryStage) throws Exception {
	 			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/ParkAvilabilityScreen.fxml"));
	 			loader.setController(this); // Set the controller
	 	    	Parent root = loader.load();
	 	    	Scene scene = new Scene(root);
	 	    	primaryStage.setTitle("cancelation report");
	 	    	primaryStage.setScene(scene);
	 	    	RemoveTopBar(primaryStage,root);
	 	    	primaryStage.show();
	 		}
	 	 
	
		@FXML
		private void handleChooseDirectory(ActionEvent event) {
		    // Show the directory chooser dialog
		    File selectedDirectory = directoryChooser.showDialog(null);

		    if (selectedDirectory != null) {
		        // Update the text field with the selected directory path
		        directoryTextField.setText(selectedDirectory.getAbsolutePath());
		    }
		}
	    
	

}
