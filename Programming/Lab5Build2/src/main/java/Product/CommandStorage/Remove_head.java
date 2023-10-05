package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Remove_head extends AbstractCommand{
    public Remove_head(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        accessCollectionManager.remove_head();
    }

    @Override
    public String getDescription() {
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
