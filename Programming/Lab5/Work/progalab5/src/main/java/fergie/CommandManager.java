package fergie;

import fergie.Commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Command management class
 *
 * @author FergieDoigrales/Stt1xX
 * @version 0.1
 * @throws IllegalArgumentException
 */
public class CommandManager {
    private CollectionManager collectionManager;

    private final Map<String, Command> commands = new HashMap<>();

    public CommandManager(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        commands.put("exit", new Exit());
        commands.put("update_id", new UpdateId(collectionManager, scanner));
        commands.put("add", new AddElement(collectionManager, scanner));
        commands.put("show", new Show(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("help", new Help(collectionManager, commands));
        commands.put("add_if_min", new AddIfMin(collectionManager, scanner));
        commands.put("remove_by_id", new RemoveById(collectionManager, scanner));
        commands.put("remove_head", new RemoveHead(collectionManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager, scanner));
        commands.put("sum_of_oscars_count", new SumOfOscarsCount(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("execute_script", new ExecuteScript(collectionManager, scanner));
        commands.put("group_counting_by_genre", new GroupCountingByGenre(collectionManager));
        commands.put("count_greater_than_genre", new CountGreaterThanGenre(collectionManager, scanner));
        commands.put("clear", new Clear(collectionManager));

    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}