package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Document;
import view.DocController;
import view.HomeController;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<Document> documentList = FXCollections.observableArrayList();

    public ObservableList<Document> getDocumentList() {
        return documentList;
    }

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        documentList.add(new Document("Hans", "Muster"));
        documentList.add(new Document("Ruth", "Mueller"));
        documentList.add(new Document("Heinz", "Kurz"));
        documentList.add(new Document("Cornelia", "Meier"));
        documentList.add(new Document("Werner", "Meyer"));
        documentList.add(new Document("Lydia", "Kunz"));
        documentList.add(new Document("Anna", "Best"));
        documentList.add(new Document("Stefan", "Meier"));
        documentList.add(new Document("Martin", "Mueller"));

        // Load person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Home.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        this.stage =primaryStage;
        // Set person overview into the center of root layout.
//        rootLayout.setCenter(personOverview);

        // Give the controller access to the main app.
//        PersonOverviewController controller = loader.getController();
//        controller.setMainApp(this);

//        Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
//        stage.setTitle("Hello World");
//        stage.setScene(new Scene(root, 600, 400));
//        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/Home.fxml"));
        HomeController controller = loader.getController();
        controller.setMainApp(this);
//        stage.show();
    }


    public static void main(String[] args) throws Exception {
        SocketClient socketClient = SocketClient.getSocketClient();
        socketClient.test();
        launch(args);
    }

    public void openDocument(int id){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Doc.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DocController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.initDocument(id);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

//            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
