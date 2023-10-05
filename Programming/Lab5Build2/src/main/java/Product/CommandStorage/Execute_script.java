package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Execute_script extends AbstractCommand{
    public Execute_script(ReceiverStorage receiverStorage, String name, int argumentNumber) {
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument) {
        checkCollectionManager();
        checkFileManager();
        checkReceiverStorage();
        accessFileManager.execute_script(argument[0], accessCollectionManager, receiverStorage);
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся " +
                "команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
