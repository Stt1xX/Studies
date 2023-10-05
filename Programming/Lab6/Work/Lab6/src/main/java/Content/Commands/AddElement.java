package Content.Commands;

import Content.CollectionManager;
import Content.Data.Movie;

import java.util.Scanner;

public class AddElement extends InputCommand implements Command {
    String description = "add {element} : добавить новый элемент в коллекцию";

    public AddElement(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }

    public void execute(String arg) {

        this.collectionManager.addElement(Movie.createNewMovie(this.scanner));
    }

    public String getDescription() {
        return description;
    }

}
