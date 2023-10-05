package Product.CommandStorage;

import Product.Receivers.ReceiverStorage;

public class Exit extends AbstractCommand{

    public Exit(ReceiverStorage receiverStorage, String name, int argumentNumber){
        super(receiverStorage, name, argumentNumber);
    }

    @Override
    public void execute(String[] argument){
        checkProgramManager();
        accessProgramManager.exit();
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
