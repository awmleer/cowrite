package view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import app.Main;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Document;

import java.io.IOException;
import java.util.Optional;


public class HomeController {
    @FXML
    private TableView<Document> documentTable;
    @FXML
    private TableColumn<Document, String> creatorColumn;
    @FXML
    private TableColumn<Document, String> titleColumn;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameLabel;


    private ObservableList<Document> documentList = FXCollections.observableArrayList();

    public ObservableList<Document> getDocumentList() {
        return documentList;
    }


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
        documentList.addListener((ListChangeListener<Document>) change -> {
            documentTable.setItems(documentList);
//            while (change.next()) {
//                if (change.wasUpdated()) {
//                    SomeObservableClass changedItem = observableList.get(change.getFrom());
//                    System.out.println("ListChangeListener item: " + changedItem);
//                }
//            }
        });
        documentList.add(new Document("Hans", "Muster"));
        documentList.add(new Document("Ruth", "Mueller"));
        documentList.add(new Document("Heinz", "Kurz"));
        documentList.add(new Document("Cornelia", "Meier"));
        documentList.add(new Document("Werner", "Meyer"));
        documentList.add(new Document("Lydia", "Kunz"));
        documentList.add(new Document("Anna", "Best"));
        documentList.add(new Document("Stefan", "Meier"));
        documentList.add(new Document("Martin", "Mueller"));
        creatorColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        documentTable.setRowFactory(tv -> {
            TableRow<Document> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Document document = row.getItem();
                    this.mainApp.openDocument(document.getId());
                    System.out.println(document.getTitle());
                }
            });
            return row ;
        });
    }


    @FXML
    private void loginButtonClicked(){
        usernameLabel.setText("test login");
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Login");
        dialog.setHeaderText("Login");
        dialog.setContentText("Please enter your username:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        documentTable.setItems(getDocumentList());
    }

}
