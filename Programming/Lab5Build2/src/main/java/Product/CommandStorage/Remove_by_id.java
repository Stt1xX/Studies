package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Remove_by_id extends AbstractCommand{

    public Remove_by_id(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.remove_by_id(argument[0]);
    }

    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
