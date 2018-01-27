package app;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Document;
import view.HomeController;

import java.util.Date;

public class Main extends Application {

    private ObservableList<Document> documentList = FXCollections.observableArrayList();

    public ObservableList<Document> getDocumentList() {
        return documentList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        documentList.add(new Document("Hans", "Muster"));
        documentList.add(new Document("Ruth", "Mueller"));
        documentList.add(new Document("Heinz", "Kurz"));
        documentList.add(new Document("Cornelia", "Meier"));
        documentList.add(new Document("Werner", "Meyer"));
        documentList.add(new Document("Lydia", "Kunz"));
        documentList.add(new Document("Anna", "Best"));
        documentList.add(new Document("Stefan", "Meier"));
        documentList.add(new Document("Martin", "Mueller"));

        // Load person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view/Home.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        // Set person overview into the center of root layout.
//        rootLayout.setCenter(personOverview);

        // Give the controller access to the main app.
//        PersonOverviewController controller = loader.getController();
//        controller.setMainApp(this);

//        Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 600, 400));
//        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/Home.fxml"));
        HomeController controller = loader.getController();
        controller.setMainApp(this);
//        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
//        String host = "127.0.0.1";
//        int port = 8080;
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        class TimeClientHandler extends ChannelInboundHandlerAdapter {
//            @Override
//            public void channelRead(ChannelHandlerContext ctx, Object msg) {
//                ByteBuf m = (ByteBuf) msg; // (1)
//                try {
//                    long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//                    System.out.println(new Date(currentTimeMillis));
//                    ctx.close();
//                } finally {
//                    m.release();
//                }
//            }
//
//            @Override
//            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//                cause.printStackTrace();
//                ctx.close();
//            }
//        }
//        Bootstrap b = new Bootstrap(); // (1)
//        b.group(workerGroup); // (2)
//        b.channel(NioSocketChannel.class); // (3)
//        b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
//        b.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            public void initChannel(SocketChannel ch) throws Exception {
//                ch.pipeline().addLast(new TimeClientHandler());
//            }
//        });
//
//        // Start the client.
//        ChannelFuture f = b.connect(host, port).sync(); // (5)
//
//        // Wait until the connection is closed.
//        f.channel().closeFuture().sync();
        launch(args);
    }
}
