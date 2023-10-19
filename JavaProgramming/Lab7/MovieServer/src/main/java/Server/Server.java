package Server;

import Server.CommandStorage.AbstractCommand;
import Server.Exceptions.InvalidUserRequisites;
import Server.Receivers.DataBaseManager;
import Server.Receivers.ReceiverStorage;
import Storage.Client.*;
import Storage.LightAbstractCommand.LightAbstractCommand;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

public class Server {
    public static void main(String[] args) {

        System.out.println("Server is starting...");

        Connection connection;
        ReceiverStorage receiverStorage = new ReceiverStorage("ReceiverStorage"); // not a web
        DataBaseManager dataBaseManager = receiverStorage.getDataBaseManager();
        ServerReceiver serverReceiver = new ServerReceiver();
        ServerResponder serverResponder = new ServerResponder();

        //threads
        ExecutorService readerRequests = Executors.newCachedThreadPool();
        ExecutorService executorRequests = Executors.newCachedThreadPool();
        ForkJoinPool responderRequests = new ForkJoinPool(1);
        try{

            connection = dataBaseManager.DBConnecting();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8081);
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, OP_ACCEPT);

            System.out.println("The server is ready for work!");

            Thread shutDownHock = new Thread(() -> {
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
                try {
                    selector.select(200L);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    SelectionKey key;
                    for (var iter = selectionKeys.iterator(); iter.hasNext(); ) {
                        key = iter.next();
                        iter.remove(); // ?? Зачем?
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                var ssc = (ServerSocketChannel) key.channel();
                                final SocketChannel socketChannel = ssc.accept();
                                System.out.printf("The connection with client %s was established!\n", socketChannel.getRemoteAddress().toString());

                                readerRequests.submit(
                                        () -> {
                                            try {
                                                dataBaseManager.authorization(connection, socketChannel, serverResponder, serverReceiver);
                                                String initialInformation = receiverStorage.getCollectionManager().start(receiverStorage.getDataBaseManager());
                                                serverResponder.respond(initialInformation, socketChannel);

                                                while (true) {
                                                    LightAbstractCommand lightAbstractCommand = serverReceiver.receive(socketChannel);
                                                    if (!(dataBaseManager.checkName(lightAbstractCommand.getOwner(), connection) && dataBaseManager.checkPassword(lightAbstractCommand.getPassword(), lightAbstractCommand.getOwner(), connection))) {
                                                        throw new InvalidUserRequisites("It's look like your command was intercepted. Take care of security!");
                                                    }
                                                    executorRequests.submit(
                                                            () -> {
                                                                try {
                                                                    AbstractCommand definedCommand = receiverStorage.commandStorage.get(lightAbstractCommand.getName());
                                                                    definedCommand.setOwner(lightAbstractCommand.getOwner());
                                                                    String response = definedCommand.execute(lightAbstractCommand.getValidArguments(), lightAbstractCommand.getValidMovie());
                                                                    responderRequests.submit(
                                                                            () -> {
                                                                                try {
                                                                                    serverResponder.respond(response, socketChannel);
                                                                                } catch (
                                                                                        IOException e) {
                                                                                    System.out.println("Отправить не удалось!");
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                    );
                                                                } catch (SQLException e) {
                                                                    System.out.println(e.getMessage());
                                                                }
                                                            }
                                                    );
                                                }
                                            } catch (IOException | ClassNotFoundException |
                                                     SQLException ex) {
                                                System.out.println(ex.getMessage());
                                            } catch (InvalidUserRequisites ex) {
                                                System.out.println(ex.getMessage());
                                                System.exit(0);
                                            }
                                        }
                                );
                            }
                        }
                    }
                    if (bufferedReader.ready()) {
                        String s = bufferedReader.readLine();
                        if (s.equals("exit")) {
                            receiverStorage.searchCommand("exit").execute(null, null);
                        } else {
                            System.out.println("The server has one command:\n'exit' - выход");
                        }
                    }
                } catch (IOException ex){
                    System.out.println("bob");
                    System.out.println(ex.getMessage());
                }
            }
        } catch(IOException | SQLException ex) {
            System.out.println("Exception in starting server!");
            ex.printStackTrace();
        }
    }
}

