package server;

import javafx.fxml.FXML;
import proto.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Main {

    private static final int PORT = 9001;


    private static HashSet<User> users = new HashSet<>();
    private static User getUser(int id){
        for (User d: users){
            if(d.getId()==id) return d;
        }
        return null;
    }
    private static User getUser(String username){
        for (User d: users){
            if(d.getUsername().equals(username)) return d;
        }
        return null;
    }

    private static HashSet<Document> documents = new HashSet<>();
    private static Document getDocument(int id){
        for (Document d: documents){
            if(d.getId()==id) return d;
        }
        return null;
    }

    private static HashMap<User, ObjectOutputStream> userToWriterMap = new HashMap<>();

    /**
     * The set of all the print writers for all the clients.  This
     * set is kept so we can easily broadcast messages.
     */
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {
        initData();
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static void initData(){
        users.add(new User(1, "john", "123"));
        users.add(new User(2, "lucy", "456"));
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        private User userForThisSocket;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a
         * screen name until a unique one has been submitted, then
         * acknowledges the name and registers the output stream for
         * the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                writers.add(out);
//                Test t = new Test();
//                out.writeObject(new SocketData<>("test",t));
                try {
                    while (true) {
                        System.out.println("going to read data");
                        SocketDataBase data = (SocketDataBase) in.readObject();
                        System.out.println(data);
                        if(data==null) break;
                        switch (data.getMeta()){
                            case "login":
                                handleLogin(((SocketData<Login>)data).getData());
                                break;
                            case "addDocument":
                                handleAddDocument(((SocketData<String >)data).getData());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleLogin(Login login) throws IOException{
            User user = getUser(login.getUsername());
            if (user==null){
                return;
            }else{
                if(user.checkPassword(login.getPassword())){
                    userForThisSocket=user;
                    out.writeObject(new SocketData<>(
                            "updateUser",
                            user
                    ));
                    updateDocuments();
                }
            }
        }
        private void handleAddDocument(String title) throws IOException{
            documents.add(new Document(userForThisSocket,title));
            System.out.println(documents.size());
            updateDocuments();
        }

        private void updateDocuments() throws IOException{
            System.out.println("update documents");
//            Document[] documentArray=(Document[]) documents.toArray();
//            System.out.println(documentArray.length);
            out.writeObject(new SocketData<>(
                    "updateDocuments",
                    new ArrayList<>(documents)
            ));
        }
    }

}