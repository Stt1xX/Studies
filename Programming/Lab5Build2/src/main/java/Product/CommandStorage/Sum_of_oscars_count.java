package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Sum_of_oscars_count extends AbstractCommand{
    public Sum_of_oscars_count(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.sum_of_oscars_count();
    }

    @Override
    public String getDescription() {
        return "sum_of_oscars_count : вывести сумму значений поля oscarsCount для всех элементов коллекции";
    }
}
