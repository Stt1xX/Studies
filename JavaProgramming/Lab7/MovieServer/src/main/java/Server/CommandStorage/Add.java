package Server.CommandStorage;

import Server.Receivers.ReceiverStorage;
import Storage.StorageData.Movie;

import java.sql.SQLException;

public class Add extends AbstractCommand {
    public Add(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        checkCollectionManager();
        return accessCollectionManager.add(movies[0], receiverStorage, getOwner());
    }
}
