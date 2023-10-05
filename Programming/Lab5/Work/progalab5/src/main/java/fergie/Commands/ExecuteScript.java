package fergie.Commands;

import fergie.CollectionManager;
import fergie.CommandManager;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ExecuteScript extends InputCommand implements Command {
    private static int mode;

    private static Stack<String> stackOfScripts = new Stack<>();
    String description = "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";

    public static int getMode(){
        return mode;
    }
    public static void setMode(int mode_type){
        mode = mode_type;
    }
    public ExecuteScript(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }
    public void execute(String str){
        setMode(2);

        //проверка рекурсии
        stackOfScripts.push(str);
        if(Collections.frequency(stackOfScripts, str) == 2){
            System.out.println("Продолжение выполнения скрипта невозможно. Удостоверьтесь, что рекурсия в скрипте отсутствует.");
            System.exit(0);
        }


        Path path = Paths.get(str);
        try {
            Scanner scannerForScript = new Scanner(path);
            String arg[];
            CommandManager commandManagerForScript = new CommandManager(collectionManager, scannerForScript);
//                while (scannerForScript.hasNextLine()) {
//                    String s = (scannerForScript.nextLine());
//                    arg = s.split("\s+");
//                    if (arg.length == 1) {
//                        if (s.equals("exit")) {
//                            break;
//                        }
//                        try {
//                            if (arg.length == 2)
//                                commandManagerForScript.getCommands().get(arg[0].toLowerCase()).execute(arg[1]);
//                            else
//                                commandManagerForScript.getCommands().get(s.toLowerCase()).execute("");
//                        } catch (NullPointerException e) {
//                            System.out.println("Введенной команды не существует.");
//                        }
//                    } else
//                        System.out.println("Слишком длинная команда.");
//
//                }
            while (scannerForScript.hasNextLine()) {

                System.out.println("\n");
                String s = (scannerForScript.nextLine());
                arg = s.split("\s+");
                try{
                    StringBuilder strForScript = new StringBuilder();
                    for (String value : arg) {
                        strForScript.append(value).append(" ");
                    }
                    if (arg.length > 1)
                        commandManagerForScript.getCommands().get(arg[0].toLowerCase()).execute(strForScript.
                                substring(arg[0].length() + 1, strForScript.length() - 1));
                    else
                        commandManagerForScript.getCommands().get(arg[0].toLowerCase()).execute("");
                } catch (NullPointerException e) {
                    System.out.println("Указана несуществующая команда (возможно, в скрипте ошибка).");
                    return;
                } catch (InvalidPathException e){
                    System.out.println("Путь к файлу не должен содержать следующих знаков: * ? < > |");
                } catch (IllegalArgumentException e){
                    System.out.println("Данная команда не имеет аргументов.");
                }
            }
            System.out.println("Скрипт выполнен.");
        } catch (IOException e) {
            System.out.println("Ошибка в пути к файлу.");
        } catch (NoSuchElementException e){
            System.out.println("Скрипт некорректный, переделывай!");
        } finally {
            stackOfScripts.pop();
        }
    }


    public String getDescription() {
        return description;
    }

}