package reportsScreenController;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.TextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import logic.ReportDetail;
import client.ClientController;
import client.ClientUI;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.DateDetail;
import logic.Message;
import workerScreenController.WorkerScreenController;


import java.io.File; 
public class VisitorStatisticReportScreenController extends WorkerScreenController implements Initializable {
	@FXML
	private TextField directoryTextField;

	private DirectoryChooser directoryChooser;
	
    @FXML
    private Button parkDashboard;

    @FXML
    private Button visitorStatisticReport;

    @FXML
    private Button parkAvailabilityReport;

    @FXML
    private Button aboutUs;

    @FXML
    private Button logout;

    @FXML
    private BarChart<?, ?> visitorChart;

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
    private Label Totalvisitortxt;

    @FXML
    private Button BackBtn;
    
    @FXML
    private Text msgToUser;
    @FXML
    private Button sendReportToSystem;
    @FXML
    private DateDetail dateDetail;
    
    @FXML
    public void sendReportToSystemBtn(ActionEvent event) throws IOException {
        WritableImage image = visitorChart.snapshot(new SnapshotParameters(), null);

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
        sendReportToSystem.setDisable(true);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setDatePickerCellFactory(FromDate);
        setDatePickerCellFactory(ToDate);
//        if (visitorChart == null) {
//            visitorChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
//        }
        // Initialize method
        setDateDefultForVisitsReport();
	    // Initialize the DirectoryChooser
	    directoryChooser = new DirectoryChooser();
    }

    // Method to handle report button action
    public void showBtn(ActionEvent event) throws IOException {
        // HashMap to store visitor data
        Map<LocalDate, int[]> visitorData;
        // Variables to store the fromDate and toDate
        LocalDate fromDate = FromDate.getValue();
        LocalDate toDate = ToDate.getValue();
        dateDetail = new DateDetail(fromDate, toDate);
        dateDetail.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());
        //dateDetail.setParkName("Hyde Park"); 
        System.out.println(dateDetail.getEnd());
        Message msg = new Message(dateDetail, Commands.VisitorStatisticRequest);
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
        visitorData =  ClientController.client.reportController.getVisitorStatisticData();
        sendReportToSystem.setDisable(false);
        CreateVisitorStatisticsBarChar(visitorData);
    }

    
    // Method to create visitor statistics bar chart
	private void CreateVisitorStatisticsBarChar(Map<LocalDate, int[]> visitorData) {
        // Clear existing data from the chart
        visitorChart.getData().clear();
        xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		visitorChart.setAnimated(false);
		visitorChart.setBarGap(1.0d);
		visitorChart.setCategoryGap(10.0);
        // new array to hold the dates
        ArrayList<LocalDate> dates = new ArrayList<>(visitorData.keySet());
        
        // Sort the dates in ascending order
        Collections.sort(dates);

        // Create categories array from the sorted dates (contains dates)
        String[] categories = new String[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            categories[i] = dates.get(i).toString();
        }

        // Set categories on the x-axis
        xAxisR.setCategories(FXCollections.observableArrayList(Arrays.asList(categories)));

        // Create new series for each type of visitor
        XYChart.Series<String, Number> Solo = new XYChart.Series<>();
        XYChart.Series<String, Number> Group = new XYChart.Series<>();
        XYChart.Series<String, Number> Guided_group = new XYChart.Series<>();

        // Setting names for the series
        Solo.setName("Solo");
        Group.setName("Group");
        Guided_group.setName("Guided group");
        int visitorcounter = 0;
        
        for (Map.Entry<LocalDate, int[]> entry : visitorData.entrySet()) {
            LocalDate date = entry.getKey();
            int[] visitorCounts = entry.getValue();
            System.out.println(date+"3");
            // Add data points to the series
            Solo.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[0]));
            visitorcounter += visitorCounts[0] ;
            Group.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[1]));
            visitorcounter += visitorCounts[1] ;
            Guided_group.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[2]));
            visitorcounter += visitorCounts[2] ;
            
        }
        
        
        // Add series to the chart
        visitorChart.getData().addAll(new XYChart.Series[]{Solo, Group, Guided_group});
        
        
        Totalvisitortxt.setText("Total visitor number is: " + visitorcounter);
    }

    // Method to clear the data in the graph screen
   
	 public void backBtn(ActionEvent event) {
        // Clear existing data from the chart
        visitorChart.getData().clear();
        Totalvisitortxt.setText("Total visitor number is: ");
    }

    // Method to set default values for visitors report
    private void setDateDefultForVisitsReport() {
    	
    	xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		//visitorChart.setAnimated(false);
		visitorChart.setBarGap(1.0d);
		visitorChart.setCategoryGap(10.0);
		
		HashMap<LocalDate, int[]> defaultVisitorData = generateDefaultVisitorDataForLastWeek();
		
		CreateVisitorStatisticsBarChar(defaultVisitorData);
    	
    }
    
    private HashMap<LocalDate, int[]> generateDefaultVisitorDataForLastWeek() {
        // Create a HashMap to hold visitor data
        HashMap<LocalDate, int[]> defaultVisitorData = new HashMap<>();

        // Get today's date
        LocalDate currentDate = LocalDate.now();

        // Generate data for the last week
        for (int i = 0; i < 7; i++) {
            // Subtract i days from the current date to get the date of each day in the last week
            LocalDate date = currentDate.minusDays(i);
            
            // For demonstration purposes, let's assume some random visitor counts for each day
            int[] visitorCounts = new int[]{10, 15, 20}; // Example counts for solo, group, and guided group visitors
            
            // Add the date and visitor counts to the HashMap
            defaultVisitorData.put(date, visitorCounts);
        }

        return defaultVisitorData;
    }
    // Method to check if the selected date is before today's date
    public boolean checkCurrentTime() {
        LocalDate fromdate = FromDate.getValue();
        LocalDate todate = ToDate.getValue();

        // Check if both dates are before today
        if (fromdate != null && todate != null) {
            return fromdate.isBefore(LocalDate.now()) && todate.isBefore(LocalDate.now());
        }

        return false; // Return false if any of the dates is not selected
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/VisitorStatisticReportScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("LoginScreen");
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
   
    
