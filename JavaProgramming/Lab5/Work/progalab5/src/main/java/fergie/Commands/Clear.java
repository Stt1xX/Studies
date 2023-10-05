package fergie.Commands;

import fergie.CollectionManager;

public class Clear extends CollectionCommand implements Command {
    String description = "clear: очистить коллекцию";

    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public void execute(String arg) {
        collectionManager.clear();
        System.out.println("Коллекция успешно очищена.");
    }

    public String getDescription() {
        return description;
    }

}
