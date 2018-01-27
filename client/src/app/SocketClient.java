package app;


import proto.SocketData;
import proto.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
    private static SocketClient socketClient = null;

    private String host = "127.0.0.1";
    private int port = 9001;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;

    private SocketClient(){
        try{
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected");
            isConnected=true;
        }catch (IOException e){
            return;
        }
    }

    public void test(){
        try {
            SocketData<Test> data = (SocketData<Test>) inputStream.readObject();
            System.out.println("Object received = " + data.getData());
            System.out.println(data.getData().a);
            System.out.println(data.getData().b);
        }catch (Exception e){
            System.out.println("test failed");
        }
    }

    public static SocketClient getSocketClient(){
        if(socketClient==null){
            socketClient = new SocketClient();
        }
        return socketClient;
    }



}
