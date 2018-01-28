package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DocController {
    @FXML
    private TextArea contentEditor;

    private StringProperty textProperty;

    private Stage stage;

    @FXML
    private void initialize(){
        textProperty= contentEditor.textProperty();
        textProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
        });
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public void initDocument(int id){
        System.out.println(id);
    }

}
