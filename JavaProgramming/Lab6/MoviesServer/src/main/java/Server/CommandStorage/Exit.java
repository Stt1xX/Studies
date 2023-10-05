package Server.CommandStorage;

import Server.Receivers.ReceiverStorage;
import Storage.StorageData.Movie;

public class Exit extends AbstractCommand{
    public Exit(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkProgramManager();
        return accessProgramManager.exit();
    }
}
