package server;

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
    private static HashMap<User, Document> userToDocumentMap = new HashMap<>();

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
        System.out.println("The server is running.");
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


    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        private User userForThisSocket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                writers.add(out);
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
                                break;
                            case "startEditDocument":
                                handleStartEditDocument(((SocketData<Integer>)data).getData());
                                break;
                            case "stopEditDocument":
                                handleStopEditDocument();
                                break;
                            case "editDocument":
                                handleEditDocument(((SocketData<String>)data).getData());
                                break;
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
                    if(userForThisSocket!=null){
                        userToWriterMap.remove(userForThisSocket);
                    }
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
                    userToWriterMap.put(userForThisSocket,out);
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
        private void handleStartEditDocument(Integer id)throws IOException{
            Document document=getDocument(id);
            userToDocumentMap.put(userForThisSocket,document);
            out.writeObject(new SocketData<>(
                    "updateDocument",
                    document.getContent()
            ));
        }
        private void handleStopEditDocument(){
            userToDocumentMap.remove(userForThisSocket);
        }
        private void handleEditDocument(String content)throws IOException{
            Document document=userToDocumentMap.get(userForThisSocket);
            document.setContent(content);
            System.out.println(content);
            for(HashMap.Entry<User,Document> entry: userToDocumentMap.entrySet()){
                if(entry.getValue()==document){
                    ObjectOutputStream writer=userToWriterMap.get(entry.getKey());
                    if(writer!=out){
                        writer.writeObject(new SocketData<>(
                                "updateDocument",
                                document.getContent()
                        ));
                    }
                }
            }
        }

        private void updateDocuments() throws IOException{
            System.out.println("update documents");
            for(ObjectOutputStream out: writers){
                out.writeObject(new SocketData<>(
                        "updateDocuments",
                        new ArrayList<>(documents)
                ));
            }
        }
    }

}