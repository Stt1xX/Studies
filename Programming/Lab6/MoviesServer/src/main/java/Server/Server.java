package Server;

import Server.CommandStorage.AbstractCommand;
import Server.CommandStorage.Exit;
import Server.CommandStorage.Save;
import Server.Receivers.ReceiverStorage;
import Storage.Client.*;
import Storage.LightAbstractCommand.LightAbstractCommand;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;

public class Server {
    public static void main(String[] args) {

        try{

            System.out.println("Server is starting...");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8081);
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            SocketChannel socketChannel;
            serverSocketChannel.register(selector, OP_ACCEPT);
            ReceiverStorage receiverStorage = new ReceiverStorage("ReceiverStorage"); // not a web
            System.out.println("The server is ready for work!");

            Thread shutDownHock = new Thread(() -> {
                receiverStorage.commandStorage.get("save").execute(null, null);
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            Runtime.getRuntime().addShutdownHook(shutDownHock);


            //Initialization localClient

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            Client localClient = new Client(new Scanner(bufferedReader));

            localClient.printPoster();

            //Initialization localClient



            while(true) {
                selector.select(200L);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                SelectionKey key;
                for (var iter = selectionKeys.iterator(); iter.hasNext(); ){
                    try{
                        key = iter.next();
                        iter.remove(); // ?? Зачем?
                        if (key.isValid()){
                            if (key.isAcceptable()){
                                var ssc = (ServerSocketChannel) key.channel();
                                socketChannel = ssc.accept();
                                System.out.printf("The connection with client %s was established!\n", socketChannel.getRemoteAddress().toString());
                                socketChannel.configureBlocking(false);
                                socketChannel.register(key.selector(), OP_READ);
                                ServerResponder responder = new ServerResponder();
                                String initialInformation = receiverStorage.getCollectionManager().start(receiverStorage.getFileManager());
                                responder.respond(initialInformation, socketChannel);

//                                System.out.println(localClientReceiver.receive(localSocketChannel));

                            }
                            if (key.isReadable()){
                                socketChannel = (SocketChannel) key.channel();
                                ServerReceiver receiver = new ServerReceiver();
                                ServerResponder responder = new ServerResponder();

                                LightAbstractCommand lightAbstractCommand = receiver.receive(key);

                                //Receiving obj
                                AbstractCommand definedCommand = receiverStorage.commandStorage.get(lightAbstractCommand.getName());
                                String response = definedCommand.execute(lightAbstractCommand.getValidArguments() , lightAbstractCommand.getValidMovie());

                                //Responding to the client
                                responder.respond(response, socketChannel);
                            }
                        }
                    } catch(IOException ex){
                        ex.printStackTrace();
                        System.out.println(ex.getMessage());
                    }
                }

                if (bufferedReader.ready()){
                    String s = bufferedReader.readLine();
                    if (s.equals("exit")) {
                        receiverStorage.searchCommand("exit").execute(null, null);
                    }
                    if (s.equals("save")) {
                        receiverStorage.searchCommand("save").execute(null, null);
                    }
                    else{
                        System.out.println("The server has two commands :\n'exit' - выход с сохранением коллекции\n'save' - сохранение коллекции");
                    }
                }
            }
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("The error in starting server");
        }
    }
}

class ServerReceiver{
    public LightAbstractCommand receive(SelectionKey key) throws IOException, ClassNotFoundException{
        SocketChannel socketChannel = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            socketChannel = (SocketChannel) key.channel();
            if (socketChannel.read(buffer) == -1) {
                socketChannel.close();
                throw new IOException();
            }
            buffer.flip();

            int sizeMessage = buffer.getInt();
            byte[] realMessage = new byte[buffer.remaining()];
            buffer.get(realMessage);
            buffer.flip();

            while (sizeMessage != realMessage.length) {
                if (socketChannel.read(buffer) == -1) {
                    socketChannel.close();
                    throw new IOException();
                }
                buffer.flip();

                byte[] pieceOfMessage = new byte[buffer.remaining()];
                buffer.get(pieceOfMessage);
                buffer.flip();

                byte[] sumOfArrays = new byte[realMessage.length + pieceOfMessage.length];

                for (int i = 0; i < realMessage.length; i++) {
                    sumOfArrays[i] = realMessage[i];
                }

                buffer.get(sumOfArrays, realMessage.length, pieceOfMessage.length);
                buffer.flip();

                realMessage = sumOfArrays;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(realMessage);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (LightAbstractCommand) objectInputStream.readObject();

        }   catch (IOException ex){
                key.cancel();
                throw new IOException("Connection is lost from " + socketChannel.getRemoteAddress());
        }
    }
}

class ServerResponder{
    public void respond(String message, SocketChannel socketChannel) throws IOException{
//        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        ByteBuffer buffer = ByteBuffer.allocate(byteArrayOutputStream.size() + 4);
        buffer.putInt(byteArrayOutputStream.size());
        buffer.put(byteArrayOutputStream.toByteArray());
        buffer.flip();

        if (socketChannel.write(buffer) == -1){
            socketChannel.close();
            throw new IOException();
        }
    }
}
