package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

public class Save extends AbstractCommand{
    public Save(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies){
        accessFileManager.save(accessCollectionManager);
        return null;
    }
}
