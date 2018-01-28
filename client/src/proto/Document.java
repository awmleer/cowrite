package proto;

import java.util.HashSet;

public class Document {
    private int id;
    private User creator;
    private HashSet<User> editingUsers;
    private String title;
    private String content;

    public Document(int id, String title, String content){
        this.id=id;
        this.title=title;
        this.content=content;
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
