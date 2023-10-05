package Server.CommandStorage;

import Server.Receivers.ReceiverStorage;
import Storage.StorageData.Movie;

public class Add extends AbstractCommand {
    public Add(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkCollectionManager();
        return accessCollectionManager.add(movies[0]);
    }
}
