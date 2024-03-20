package screenController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;

public class PricesScreenController extends VisitorScreenController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> typeC;
    @FXML
    private TableColumn<List<String>, String> paymentC;
    @FXML
    private TableColumn<List<String>, String> valueC;

    public void setTable() {
        // Create sample data for each row
        ObservableList<List<String>> data = FXCollections.observableArrayList(
            Arrays.asList("Type 1", "Payment 1", "Value 1"),
            Arrays.asList("Type 2", "Payment 2", "Value 2"),
            Arrays.asList("Type 3", "Payment 3", "Value 3"),
            Arrays.asList("Type 4", "Payment 4", "Value 4")
        );

        // Bind the data to the table columns
        typeC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        paymentC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        valueC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
             
        // Set the data to the table view
        tableView.setItems(data);
        
     // Lock column resizing
        tableView.getColumns().forEach(column -> {
            column.setResizable(false);
        });
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Prices.fxml"));
        loader.setController(this); // Set the controller
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("OurPrices");
        primaryStage.setScene(scene);
        RemoveTopBar(primaryStage, root);
        primaryStage.show();
        setTable();
    }
}
