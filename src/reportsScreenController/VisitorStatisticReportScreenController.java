package reportsScreenController;

import java.awt.event.ActionEvent;
import java.io.IOException;
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

import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Callback;
import logic.DateDetail;
import logic.Message;
import screenController.WorkerScreenController;

public class VisitorStatisticReportScreenController extends WorkerScreenController implements Initializable {

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
	    private Label Totalvisitortxt;
		
	    @FXML
	    private DatePicker FromDate;

	    @FXML
	    private DatePicker ToDate;

	    @FXML
	    private Button btnShow;

	    @FXML
	    private Button BackBtn;

	    @FXML
	    void BackFunc(ActionEvent event) {

	    }

	    @FXML
	    void aboutUsBtn(ActionEvent event) {

	    }

	    @FXML
	    void logoutBtn(ActionEvent event) {

	    }

	    @FXML
	    void parkAvailabilityReportBtn(ActionEvent event) {

	    }

	    @FXML
	    void parkDashboardBtn(ActionEvent event) {

	    }

	    @FXML
	    void visitorStatisticReportBtn(ActionEvent event) {

	    }
	    
	    


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setDatePickerCellFactory(FromDate);
        setDatePickerCellFactory(ToDate);
        // Initialize method
        setDateDefultForVisitsReport();
    }

    // Method to handle report button action
    public void showReportBtn() throws IOException {
        // HashMap to store visitor data
        HashMap<LocalDate, int[]> visitorData;
        // Variables to store the fromDate and toDate
        LocalDate fromDate = FromDate.getValue();
        LocalDate toDate = ToDate.getValue();
        DateDetail dateDetail = new DateDetail(fromDate, toDate);
        // dateDetail.setParkName("Hyde Park"); // Uncomment if needed
        Message msg = new Message(dateDetail, Commands.VisitorStatisticRequest);
        boolean awaitResponse = true;

        ClientController.client.sendToServer(msg);
        while (awaitResponse) {
            try {
                Thread.sleep(100);
                awaitResponse = ClientController.client.reportController.isGotResponse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        visitorData = (HashMap<LocalDate, int[]>) ClientController.client.reportController.getVisitorStatisticData();
        CreateVisitorStatisticsBarChar(visitorData);
    }

    
    // Method to create visitor statistics bar chart
	private void CreateVisitorStatisticsBarChar(HashMap<LocalDate, int[]> visitorData) {
        // Clear existing data from the chart
        visitorChart.getData().clear();
        xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		visitorChart.setAnimated(false);
		visitorChart.setBarGap(1.0d);
		visitorChart.setCategoryGap(10.0);
        // new array to hold the dates
        ArrayList<LocalDate> dates = new ArrayList<>(visitorData.keySet());
        Label Totalvisitortxt1;
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
   
	private void backbtn() {
        // Clear existing data from the chart
        visitorChart.getData().clear();
        Totalvisitortxt.setText("Total visitor number is: ");
    }

    // Method to set default values for visitors report
    private void setDateDefultForVisitsReport() {
    	
    	xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		visitorChart.setAnimated(false);
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
}
   
    
