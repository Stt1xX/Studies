package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Count_greater_than_height extends AbstractCommand{
    public Count_greater_than_height(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        accessCollectionManager.count_greater_than_height(argument[0]);
    }

    @Override
    public String getDescription() {
        return "count_greater_than_height float : вывести количество элементов, значение поля height(Person) которых больше заданного";
        // было count_greater_than_genre - что за бред, лучше уж по сложнее сделаю
    }
}
