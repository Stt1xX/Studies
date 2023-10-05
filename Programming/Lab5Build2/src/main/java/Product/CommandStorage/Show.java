package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Show extends AbstractCommand {

    public Show(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.show();
    }

    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
