package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

import java.sql.SQLException;

public class Update extends AbstractCommand{
    public Update(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        checkCollectionManager();
        return accessCollectionManager.update(receiverStorage, getOwner(), argument[0], movies[0]);
    }
}
