package Product.CommandStorage;
import Product.Receivers.ReceiverStorage;

public class Add extends AbstractCommand {
    public Add(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.add(getTerminalInterpreter().getScanner());
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
