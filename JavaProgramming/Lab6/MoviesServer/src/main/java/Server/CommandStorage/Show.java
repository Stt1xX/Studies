package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

public class Show extends AbstractCommand {

    public Show(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkCollectionManager();
        return accessCollectionManager.show();
    }
}
