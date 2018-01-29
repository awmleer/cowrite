package view;

import app.SocketClient;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.Main;
import model.DocumentRow;
import proto.User;

import java.util.Optional;


public class HomeController {
    @FXML
    private TableView<DocumentRow> documentTable;
    @FXML
    private TableColumn<DocumentRow, String> creatorColumn;
    @FXML
    private TableColumn<DocumentRow, String> titleColumn;
    @FXML
    private Button loginButton;
    @FXML
    private Label usernameLabel;


    private ObservableList<DocumentRow> documentRowList = FXCollections.observableArrayList();

    public ObservableList<DocumentRow> getDocumentRowList() {
        return documentRowList;
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
        documentRowList.addListener((ListChangeListener<DocumentRow>) change -> {
            documentTable.setItems(documentRowList);
//            while (change.next()) {
//                if (change.wasUpdated()) {
//                    SomeObservableClass changedItem = observableList.get(change.getFrom());
//                    System.out.println("ListChangeListener item: " + changedItem);
//                }
//            }
        });
        documentRowList.add(new DocumentRow(1, "Hans", "Muster"));
        documentRowList.add(new DocumentRow(2, "Ruth", "Mueller"));
        documentRowList.add(new DocumentRow(3, "Heinz", "Kurz"));
        creatorColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        documentTable.setRowFactory(tv -> {
            TableRow<DocumentRow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    DocumentRow documentRow = row.getItem();
                    this.mainApp.openDocument(documentRow.getId());
                    System.out.println(documentRow.getTitle());
                }
            });
            return row ;
        });
    }


    @FXML
    private void loginButtonClicked(){
        usernameLabel.setText("test login");
        TextInputDialog dialog;
        Optional<String> result;
        dialog = new TextInputDialog("");
        dialog.setTitle("Login");
        dialog.setHeaderText("Login");
        dialog.setContentText("Please enter your username:");
        result = dialog.showAndWait();
        String username;
        if (result.isPresent()){
            username=result.get();
        }else{
            return;
        }
        dialog = new TextInputDialog("");
        dialog.setTitle("Login");
        dialog.setHeaderText("Login");
        dialog.setContentText("Please enter your password:");
        result = dialog.showAndWait();
        String password;
        if (result.isPresent()){
            password=result.get();
        }else{
            return;
        }
        SocketClient.getSocketClient().login(username,password);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        documentTable.setItems(getDocumentRowList());
    }

    public void updateUser(User user){
        usernameLabel.setText(user.getUsername());
    }

}
