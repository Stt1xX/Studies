import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStreamWriter;
public class Client {
    public static void main(String[] args){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("localhost", 4004);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()), 1);){
            System.out.println("Вы что-то хотели сказать? Введите это здесь:");
            String word = reader.readLine(); // ждем пока клиент что-нибудь не напишет в консоль
            out.write(word + "\n"); // отправляем сообщение на сервер
            out.flush();
            String serverWord = in.readLine(); // ждем, что скажет сервер
            System.out.println(serverWord); // получив - выводим на экран
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
