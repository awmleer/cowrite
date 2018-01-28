package view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DocController {
    @FXML
    private TextArea editor;
    private StringProperty textProperty;

    @FXML
    private void initialize(){
        textProperty=editor.textProperty();
        textProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("TextField Text Changed (newValue: " + newValue + ")");
        });
    }

}
