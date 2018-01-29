package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.DocController;
import view.HomeController;

import java.io.IOException;

public class Main extends Application {


    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Home.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Cowrite");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        this.stage =primaryStage;
        loader.setLocation(getClass().getResource("../view/Home.fxml"));
        HomeController controller = loader.getController();
        SocketClient.getSocketClient().setHomeController(controller);
        controller.setMainApp(this);
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void openDocument(int id){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../view/Doc.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DocController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.initDocument(id);
            SocketClient.getSocketClient().setDocController(controller);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
