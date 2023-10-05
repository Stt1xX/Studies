import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientSocket; // сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args){
        try{
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу объявить о своем запуске
                clientSocket = server.accept(); // accept() будет ждать пока кто-нибудь не захочет подключиться
                try {
                    //установив связь и воссоздав сокет для общения с клиентом можно
                    // перейти к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()), 1  );

                    String word = in.readLine(); // ждем пока клиент что-нибудь напишет
                    System.out.println(word);
                    // не долго думая сервер ответит:
                    out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");
                    out.flush();
                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    // потоки также хорошо было бы закрыть
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
