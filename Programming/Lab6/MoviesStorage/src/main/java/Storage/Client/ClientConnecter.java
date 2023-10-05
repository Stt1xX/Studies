package Storage.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientConnecter {
    public SocketChannel connect(String host, int port) {
        try {
            System.out.println("Connecting to the server...");
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            socketChannel.finishConnect();
            System.out.println("The connection was successful! Client is ready for work!");
            return socketChannel;
        } catch (IOException ex) {
            System.out.println("Failed to connect to the server\nRepeat after 5 seconds");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return connect(host, port);
        }
    }
}
