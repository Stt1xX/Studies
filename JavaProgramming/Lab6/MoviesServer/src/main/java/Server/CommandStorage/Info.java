package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

public class Info extends AbstractCommand{
    public Info(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkCollectionManager();
        return accessCollectionManager.info();
    }

}
