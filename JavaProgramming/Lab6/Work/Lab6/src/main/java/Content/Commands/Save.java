package Content.Commands;

import Content.CollectionManager;

public class Save extends CollectionCommand implements Command {
    String description = "save : сохранить коллекцию в файл";

    public Save(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public void execute(String arg) {
        this.collectionManager.save();
        System.out.println("Файл успешно сохранен.");
    }

    public String getDescription() {
        return description;
    }
}
