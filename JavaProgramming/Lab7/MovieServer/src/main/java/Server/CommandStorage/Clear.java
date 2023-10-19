package Server.CommandStorage;

import Storage.StorageData.Movie;
import Server.Receivers.ReceiverStorage;

import java.sql.SQLException;

public class Clear extends AbstractCommand{
    public Clear(ReceiverStorage receiverStorage, String name) {
        super(receiverStorage, name);
    }

    @Override
    public String execute(String[] argument, Movie[] movies) throws SQLException {
        checkCollectionManager();
        return accessCollectionManager.clear(receiverStorage);
    }
    
}
