package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Save extends AbstractCommand{
    public Save(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument){
        checkCollectionManager();
        checkFileManager();
        accessFileManager.save(accessCollectionManager);
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
