package Content.Commands;

import Content.CollectionManager;

public class GroupCountingByGenre extends CollectionCommand implements Command {
    String description = "group_counting_by_genre : сгруппировать элементы коллекции по значению поля Genre, " +
            " вывести количество элементов в каждой группе";

    public GroupCountingByGenre(CollectionManager collectionManager) {
        super(collectionManager);

    }

    public void execute(String a) {
        try {
            collectionManager.groupCountingByGenre();
        } catch (IllegalArgumentException e) {
            System.out.println("Такого жанра не существует. ");
        }
    }


    public String getDescription() {
        return description;
    }

}