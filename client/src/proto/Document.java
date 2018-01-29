package proto;

import java.io.Serializable;
import java.util.HashSet;

public class Document implements Serializable {
    private int id;
    private static int nextId=0;
    private User creator;
    private HashSet<User> editingUsers;
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

    public HashSet<User> getEditingUsers() {
        return editingUsers;
    }

    public void addEditingUser(User user){
        this.editingUsers.add(user);
    }

    public void removeEditingUser(User user){
        this.editingUsers.remove(user);
    }
}
