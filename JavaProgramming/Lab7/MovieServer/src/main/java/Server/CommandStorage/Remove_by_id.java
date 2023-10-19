package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

import java.sql.SQLException;

public class Remove_by_id extends AbstractCommand{

    public Remove_by_id(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        checkCollectionManager();
        return accessCollectionManager.remove_by_id(receiverStorage, getOwner(), argument[0]);
    }
}
