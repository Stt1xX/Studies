package Server.CommandStorage;


import Server.Receivers.ReceiverStorage;
import Storage.StorageData.Movie;

import java.sql.SQLException;

public class Add_if_min extends AbstractCommand{
    public Add_if_min(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        checkCollectionManager();
        return accessCollectionManager.add_if_min(receiverStorage, getOwner(), movies[0]);
    }
}
