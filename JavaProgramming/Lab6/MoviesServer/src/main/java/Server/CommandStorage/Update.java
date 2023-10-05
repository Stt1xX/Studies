package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

public class Update extends AbstractCommand{
    public Update(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkCollectionManager();
        return accessCollectionManager.update(argument[0], movies[0]);
    }
}
