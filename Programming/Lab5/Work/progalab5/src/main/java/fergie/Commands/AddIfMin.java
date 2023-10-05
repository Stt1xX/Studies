package fergie.Commands;

import fergie.CollectionManager;
import fergie.Data.Movie;

import java.util.Scanner;

public class AddIfMin extends InputCommand implements Command {
    String description = "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, " +
            "чем у наименьшего элемента этой коллекции";

    public AddIfMin(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }

    public void execute(String arg){
        Movie movie = Movie.createNewMovie(scanner);
        collectionManager.addIfMin(movie);

    };
    public String getDescription(){
        return description;
    };
}
