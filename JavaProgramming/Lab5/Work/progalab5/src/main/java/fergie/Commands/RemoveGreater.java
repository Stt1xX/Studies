package fergie.Commands;

import fergie.CollectionManager;
import fergie.Data.Movie;

import java.util.Scanner;

public class RemoveGreater extends InputCommand implements Command {
    String description = "remove_greater {element_oscars_count} : удалить из коллекции все элементы, превышающие заданный";

    public RemoveGreater(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }

    public void execute(String argument) {
        Movie movie = Movie.createNewMovie(scanner);
        collectionManager.removeIfGreater(movie);
        System.out.println("Все элементы коллекции, превышающие заданный, удалены.");
    }

    public String getDescription() {
        return description;
    }
}