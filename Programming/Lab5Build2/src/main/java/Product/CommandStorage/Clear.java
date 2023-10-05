package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Clear extends AbstractCommand{
    public Clear(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.clear();
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
