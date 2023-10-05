package fergie.Commands;


import fergie.CollectionManager;

import java.util.Map;

public class Help extends CollectionCommand implements Command{
    String description = "help : вывести справку по доступным командам";
    Map<String, Command> map;

    public Help(CollectionManager collectionManager, Map<String, Command> map) {
        super(collectionManager);
        this.map = map;
    }

    public void execute(String arg) throws IllegalArgumentException{
        if (arg.equals("")) {
            System.out.println("Список доступных команд:");
            for (Command cmd : map.values()) {
                System.out.println(cmd.getDescription() + "\n");
            }
        }
        else throw new IllegalArgumentException();
    }
    public String getDescription(){
        return description;
    }
}
