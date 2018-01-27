package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Document {

    private final IntegerProperty id;
    private final StringProperty creator;
    private final StringProperty title;

    public Document() {
        this(null, null);
    }


    public Document(String creator, String title) {
        this.creator = new SimpleStringProperty(creator);
        this.title = new SimpleStringProperty(title);

        // Some initial dummy data, just for convenient testing.
        this.id = new SimpleIntegerProperty(1234);
    }

    public String getCreator() {
        return creator.get();
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }

    public StringProperty creatorProperty() {
        return creator;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

}
