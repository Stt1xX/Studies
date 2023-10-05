package fergie.Commands;

import fergie.CollectionManager;

public class Show extends CollectionCommand implements Command {
    String description = "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public void execute(String arg) {
        collectionManager.show();
    }

    public String getDescription() {
        return description;
    }
}
