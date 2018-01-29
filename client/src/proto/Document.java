package proto;

import java.io.Serializable;

public class Document implements Serializable {
    private int id;
    private static int nextId=0;
    private User creator;
    private String title;
    private String content;

    public Document(User creator, String title){
        this.id=nextId;
        nextId++;
        this.creator=creator;
        this.title=title;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }

}
