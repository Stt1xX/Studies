package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Group_counting_by_genre extends AbstractCommand{
    public Group_counting_by_genre(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.group_counting_by_genre();
    }

    @Override
    public String getDescription() {
        return "group_counting_by_genre : сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе";
        // было group_counting_by_id но id - уникальный
    }
}
