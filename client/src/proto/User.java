package proto;

public class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}
