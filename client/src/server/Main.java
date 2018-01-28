package server;

import javafx.fxml.FXML;
import proto.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;


public class Main {

    private static final int PORT = 9001;


    private HashSet<User> users = new HashSet<>();
    private User getUser(int id){
        for (User d: users){
            if(d.getId()==id) return d;
        }
        return null;
    }
    private User getUser(String username){
        for (User d: users){
            if(d.getUsername().equals(username)) return d;
        }
        return null;
    }

    private HashSet<Document> documents = new HashSet<>();
    private Document getDocument(int id){
        for (Document d: documents){
            if(d.getId()==id) return d;
        }
        return null;
    }

    private HashMap<User, ObjectOutputStream> userToWriterMap = new HashMap<>();

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
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                Test t = new Test();
                out.writeObject(new SocketData<>("test",t));
                while (true) {
                    try {
                        SocketDataBase data = (SocketDataBase) in.readObject();
                        if(data==null) break;
                        switch (data.getMeta()){
                            case "test":
                                handleTest(data, out);
                        }
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
                writers.add(out);

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

        private void handleTest(SocketDataBase base, ObjectOutputStream out){

        }

    }

}