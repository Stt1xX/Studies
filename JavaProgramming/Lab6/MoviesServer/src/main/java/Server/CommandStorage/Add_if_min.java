package Server.CommandStorage;


import Server.Receivers.ReceiverStorage;
import Storage.StorageData.Movie;

public class Add_if_min extends AbstractCommand{
    public Add_if_min(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) {
        checkCollectionManager();
        return accessCollectionManager.add_if_min(movies[0]);
    }
}
