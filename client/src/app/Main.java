package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Document;
import view.HomeController;

import java.util.Date;

public class Main extends Application {

    private ObservableList<Document> documentList = FXCollections.observableArrayList();

    public ObservableList<Document> getDocumentList() {
        return documentList;
    }

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
        // Set person overview into the center of root layout.
//        rootLayout.setCenter(personOverview);

        // Give the controller access to the main app.
//        PersonOverviewController controller = loader.getController();
//        controller.setMainApp(this);

//        Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 600, 400));
//        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/Home.fxml"));
        HomeController controller = loader.getController();
        controller.setMainApp(this);
//        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        SocketClient socketClient = SocketClient.getSocketClient();
        socketClient.test();
        launch(args);
    }
}
