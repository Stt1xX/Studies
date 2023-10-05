package fergie.Commands;

import fergie.CollectionManager;

import java.util.Scanner;

public class RemoveById extends CollectionCommand implements Command {
    String description = "remove_by_id id : удалить элемент из коллекции по его id";

    public RemoveById(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager);
    }

    public void execute(String argument) {
        try {collectionManager.removeById(argument);}
        catch (NumberFormatException e){
            System.out.println("Указанный аргумент не подходит.");
        }
    }

    public String getDescription() {
        return description;
    }
}