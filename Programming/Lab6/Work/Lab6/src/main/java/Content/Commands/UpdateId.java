package Content.Commands;


import Content.CollectionManager;
import Content.Data.Movie;

import java.util.Scanner;

public class UpdateId extends InputCommand implements Command {
    String description = "update id {element} : обновить значение элемента коллекции, id которого равен заданному";

    public UpdateId(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }

    public void execute(String arg) {
        try {
            String id = arg;
            Long correctId = Long.parseLong(id);

            if (this.collectionManager.checkID(correctId)) {
                Movie movie = Movie.createNewMovie(this.scanner);
                movie.setId(correctId);
                this.collectionManager.updateMovie(movie);
                System.out.println("Фильм успешно обновлен.");
            } else {
                System.out.println("ID не найден в текущей коллекции");
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы указали неподходящие данные. Попробуйте запустить команду еще раз.");
        } catch (NullPointerException e) {
            System.out.println("В коллекции нет элементов.");
        }

    }

    public String getDescription() {
        return description;
    }
}