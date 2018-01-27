package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.Main;
import model.Document;


public class HomeController {
    @FXML
    private TableView<Document> documentTable;
    @FXML
    private TableColumn<Document, String> creatorColumn;
    @FXML
    private TableColumn<Document, String> titleColumn;

//    @FXML
//    private Label firstNameLabel;
//    @FXML
//    private Label lastNameLabel;
//    @FXML
//    private Label streetLabel;
//    @FXML
//    private Label postalCodeLabel;
//    @FXML
//    private Label cityLabel;
//    @FXML
//    private Label birthdayLabel;

    // Reference to the main application.
    private Main mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HomeController() {
        System.out.println("constructor of controller");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        creatorColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        documentTable.setItems(this.mainApp.getDocumentList());
    }
}
