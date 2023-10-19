package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

import java.sql.SQLException;

public class Remove_greater extends AbstractCommand{
    public Remove_greater(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException{
        checkCollectionManager();
        return accessCollectionManager.remove_greater(receiverStorage, getOwner());
    }
}
