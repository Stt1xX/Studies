package Content.Commands;

import Content.CollectionManager;

import java.util.NoSuchElementException;

public class RemoveHead extends CollectionCommand implements Command {
    String description = "remove_head : вывести первый элемент коллекции и удалить его";

    public RemoveHead(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public void execute(String arg) {
        try {
            collectionManager.removeHead();
            System.out.println("Первый элемент коллекции удален.");
        } catch (NoSuchElementException e) {
            System.out.println("В коллекции нет элементов.");
        }
    }

    public String getDescription() {
        return description;
    }
}
