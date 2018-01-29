package view;

import app.SocketClient;
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
            if(oldValue!=null&&newValue!=null&&!oldValue.equals(newValue)){
                SocketClient.getSocketClient().editDocument(newValue);
                System.out.println("TextField Text Changed (newValue: " + newValue + ")");
            }
        });
    }

    public void setStage(Stage stage){
        this.stage=stage;
        this.stage.setOnHiding(e->{
            SocketClient.getSocketClient().stopDocumentEditing();
        });
    }

    public void initDocument(int id){
        SocketClient.getSocketClient().startDocumentEditing(id);
    }

    public void setText(String content){
        contentEditor.setText(content);
    }

}
