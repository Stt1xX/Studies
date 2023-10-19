package Storage.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ClientConnecter {
    private Client client;
    ClientConnecter(Client client){
        this.client = client;
    }
    private String host;
    private int port;
    private SocketChannel socketChannel;
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
    public void connect(String host, int port) {
        try {
            System.out.println("Connecting to the server...");
            SocketChannel socketChannel = SocketChannel.open();
//            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
//            socketChannel.finishConnect();
            System.out.println("The connection was successful! Client is ready for work!");
            this.host = host;
            this.port = port;
            this.socketChannel = socketChannel;
        } catch (IOException ex) {
            System.out.println("Failed to connect to the server\nRepeat after 5 seconds");
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            connect(host, port);
        }
    }

    public void clientAuthorization(ClientReceiver clientReceiver, ClientSender clientSender){
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your username:");
            String name = scanner.nextLine();
            clientSender.authorizationSend(name, this.socketChannel);
            if (clientReceiver.authorizationReceive(this.socketChannel) == 0){
                throw new InvalidParameterException("User isn't found! Try again");
            }
            else {
                System.out.println("User is found!");
            }

            System.out.println("Enter your password:");
            String pass = new String(System.console().readPassword());
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] array = md.digest(pass.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
                }
                pass = sb.toString();
                clientSender.authorizationSend(pass, this.socketChannel);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            if (clientReceiver.authorizationReceive(this.socketChannel) == 0){
                throw new InvalidParameterException("Invalid password. Try again");
            }
            else{
                System.out.println("Access is allowed");
                client.setMyName(name);
                client.setMyPassword(pass);
            }
        } catch (IOException e) {
            System.out.println("The connection lost\nWe will try to reconnect");
            connect(this.host, this.port);
            clientAuthorization(clientReceiver, clientSender);
        } catch (ClassNotFoundException ex){
            throw new RuntimeException();
        } catch (InvalidParameterException ex){
            System.out.println(ex.getMessage());
            clientAuthorization(clientReceiver, clientSender);
        }
    }
}
