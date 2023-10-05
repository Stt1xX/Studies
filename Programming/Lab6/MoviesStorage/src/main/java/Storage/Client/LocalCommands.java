package Storage.Client;

import Storage.Client.Exceptions.InvalidScriptException;
import Storage.LightAbstractCommand.LightAbstractCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class LocalCommands {

    private static final Stack<Path> scriptStack = new Stack<>();

    public static void exit(){
        System.exit(0);
    }

    public static void help(ClientStorage clientStorage){
        Map<String, LightAbstractCommand> map = clientStorage.getCommandStorage();
        for (LightAbstractCommand command : map.values()){
            System.out.println(command.getDescription());
        }
    }
    public static void execute_script(String str, ClientStorage clientStorage, SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        try {
            Path path = Path.of(str);
            scriptStack.push(path.toAbsolutePath());
            if (Collections.frequency(scriptStack, path.toAbsolutePath()) == 2) {
                throw new InvalidScriptException("В скрипте присутствует рекурсивный вызов, его продолжение невозоможно");
            }
            Scanner scanner = new Scanner(new File(str));
            Client client = new Client(scanner);
            client.setFlagForHide(false);
            while(scanner.hasNextLine()) {
                LightAbstractCommand command = client.getLightAbstractCommand(clientStorage, socketChannel);
                if (command == null) continue;
                // сереализация и отправка
                ClientSender clientSender = new ClientSender();
                clientSender.send(command, socketChannel);
                // десереализация и получение (ответ сервера - строка)
                ClientReceiver clientReceiver = new ClientReceiver();
                System.out.println(clientReceiver.receive(socketChannel));
            }
            scriptStack.pop();
        } catch(FileNotFoundException ex){
            System.out.println("Файл по указанному пути не найден");
        } catch(InvalidScriptException ex){
            System.out.println(ex.getMessage());
        }
    }
}
