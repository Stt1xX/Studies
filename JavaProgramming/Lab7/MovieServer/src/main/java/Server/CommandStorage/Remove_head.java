package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

import java.sql.SQLException;

public class Remove_head extends AbstractCommand{
    public Remove_head(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        return accessCollectionManager.remove_head(receiverStorage, getOwner());
    }
}
