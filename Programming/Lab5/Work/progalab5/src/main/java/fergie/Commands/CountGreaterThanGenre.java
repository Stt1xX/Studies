package fergie.Commands;

import fergie.CollectionManager;

import java.util.Scanner;

public class CountGreaterThanGenre extends InputCommand implements Command {
    String description = "count_greater_than_genre genre : вывести количество элементов, значение поля genre которых больше заданного";

    public CountGreaterThanGenre(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager, scanner);
    }

    public void execute(String arg) {
        while (true) {
            try {
                //System.out.println("Выберите жанр из списка: ");
                //System.out.println(Arrays.toString(MovieGenre.values()));
                //String genre = scanner.nextLine();
                Integer count = collectionManager.countGreaterThanGenre(arg);
                System.out.println("Количество элементов, жанр которых превышающих заданный жанр: " + count);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Указанный аргумент не подходит.");
                return;
            } catch (NullPointerException e) {
                System.out.println("У заданного фильма отсутствует жанр.");
            }
        }


    }

    public String getDescription() {
        return description;
    }
}