package Product;

import Product.CommandStorage.AbstractCommand;
import Product.Receivers.ReceiverStorage;
import Product.Exceptions.InvalidArgumentException;
import Product.Exceptions.InvalidCommandException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * It's like engine. Method {@link #goAction(ReceiverStorage)} start main program loop
 *
 * @author Stt1xX
 */

public class TerminalInterpreter {

    private Scanner scanner;
    public TerminalInterpreter(Scanner scanner){
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }
    public void printPoster(){
        System.out.println
                (" __  __            _      __  __                                   \n" +
                "|  \\/  |          (_)    |  \\/  |                                  \n" +
                "| \\  / | _____   ___  ___| \\  / | __ _ _ __   __ _  __ _  ___ _ __ \n" +
                "| |\\/| |/ _ \\ \\ / / |/ _ \\ |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\n" +
                "| |  | | (_) \\ V /| |  __/ |  | | (_| | | | | (_| | (_| |  __/ |   \n" +
                "|_|  |_|\\___/ \\_/ |_|\\___|_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \n" +
                "                                                    __/ |          \n" +
                "                                                   |___/                        \n\n");
    }

    public void goAction(ReceiverStorage receiverStorage){
        while(scanner.hasNextLine()){
            try {
                String s = scanner.nextLine();
                String[] str = s.trim().split("\s+");
                if (str.length == 0 || s.equals("")){
                    continue;
                }
                AbstractCommand command = receiverStorage.searchCommand(str[0]);
                if (command == null){
                    throw new InvalidCommandException("Неверная команда: " + str[0]);
                }
                if (str.length - 1 == command.getArgumentNumber()) {
                    if (command.getArgumentNumber() > 0){
                        command.setPersonalArguments(Arrays.copyOfRange(str, 1, str.length));
                    }
                    command.setTerminalInterpreter(this); // получаем ссылку на объект интерпретатора для ресиверов(Abstract Command)
                    command.execute(command.getPersonalArguments());
                } else {
                    throw new InvalidArgumentException("Неверное количество аргументов команды: " + command.getName());
                }
            } catch(InvalidArgumentException | InvalidCommandException ex){
                System.out.println(ex.getMessage());
            } catch(NoSuchElementException ex){
                System.exit(0);
            }
        }
        scanner.close();
    }
}
