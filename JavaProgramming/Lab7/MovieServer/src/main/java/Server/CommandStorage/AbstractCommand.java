package Server.CommandStorage;

import Server.Receivers.*;
import Server.Exceptions.ReceiverNotFoundException;
import Storage.StorageData.Movie;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class describes common fields and methods containing commands
 * @author Stt1xX
 */

public class AbstractCommand {

    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public ArrayList<Manager> personalAccesses = new ArrayList<>();
    protected CollectionManager accessCollectionManager;
    protected ProgramManager accessProgramManager;
    protected final ReceiverStorage receiverStorage;
    protected final String name;

    public String execute(String[] argument, Movie[] movies) throws SQLException {return "";}

    public AbstractCommand(ReceiverStorage receiverStorage, String name){
        receiverStorage.commandStorage.put(name, this);
        this.receiverStorage = receiverStorage;
        personalAccesses.add(receiverStorage);
        this.name = name;
    }

    public void setAccessCollectionManager(CollectionManager accessCollectionManager) {
        this.accessCollectionManager = accessCollectionManager;
        personalAccesses.add(accessCollectionManager);
    }
    public void setAccessProgramManager(ProgramManager accessProgramManager){
        this.accessProgramManager = accessProgramManager;
        personalAccesses.add(accessProgramManager);
    }

    public String getName() {
        return name;
    }
    public void checkProgramManager(){
        try {
            boolean flag = false;
            for (Manager manager : personalAccesses) {
                if (manager instanceof ProgramManager) {
                    flag = true;
                }
            }
            if (!flag)
                throw new ReceiverNotFoundException("ProgramManager is disabled for this command: " + getName() +
                        "\nPlease connect the Program in code and try once again");
        } catch (ReceiverNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public void checkCollectionManager(){
        try {
            boolean flag = false;
            for (Manager manager : personalAccesses) {
                if (manager instanceof CollectionManager) {
                    flag = true;
                }
            }
            if (!flag)
                throw new ReceiverNotFoundException("CollectionManager is disabled for this command: " + getName() +
                        "\nPlease connect the CollectionManager in code try run once again");
        } catch (ReceiverNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public void checkReceiverStorage(){
        try {
            boolean flag = false;
            for (Manager manager : personalAccesses) {
                if (manager instanceof ReceiverStorage) {
                    flag = true;
                }
            }
            if (!flag)
                throw new ReceiverNotFoundException("ReceiverStorage is disabled for this command: " + getName() +
                        "\nPlease connect the RecieverStorage in code and try once again");
        } catch (ReceiverNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
}
