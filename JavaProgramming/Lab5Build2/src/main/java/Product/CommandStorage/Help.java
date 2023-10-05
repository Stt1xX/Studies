package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Help extends AbstractCommand{

    public Help(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }
    @Override
    public void execute(String[] argument) {
        checkReceiverStorage(); // пока что бесполезная проверка,ибо Получатель-Хранилище есть во всех командах по умолчанию (прописано в конструктаре AbstractCommand)
        receiverStorage.help();
    }

    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
