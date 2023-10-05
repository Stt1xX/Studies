package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Remove_greater extends AbstractCommand{
    public Remove_greater(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.remove_greater();
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
