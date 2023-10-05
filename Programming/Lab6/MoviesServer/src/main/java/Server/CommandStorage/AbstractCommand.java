package Server.CommandStorage;

import Server.Receivers.*;
import Server.Exceptions.ReceiverNotFoundException;
import Storage.StorageData.Movie;

import java.util.ArrayList;

/**
 * This class describes common fields and methods containing commands
 * @author Stt1xX
 */

public class AbstractCommand {
//    private final int argumentNumber;

//    private TerminalInterpreter terminalInterpreter;
//    private String[] personalArguments;
    public ArrayList<Manager> personalAccesses = new ArrayList<>();
    protected CollectionManager accessCollectionManager;
    protected FileManager accessFileManager;
    protected ProgramManager accessProgramManager;
    protected final ReceiverStorage receiverStorage;
    protected final String name;

    public String execute(String[] argument, Movie[] movies) {return "";}

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

    public void setAccessFileManager(FileManager accessFileManager) {
        this.accessFileManager = accessFileManager;
        personalAccesses.add(accessFileManager);
    }

    public void setAccessProgramManager(ProgramManager accessProgramManager){
        this.accessProgramManager = accessProgramManager;
        personalAccesses.add(accessProgramManager);
    }

    public String getName() {
        return name;
    }
    public void checkFileManager(){
        try {
            boolean flag = false;
            for (Manager manager : personalAccesses) {
                if (manager instanceof FileManager) {
                    flag = true;
                }
            }
            if (!flag)
                throw new ReceiverNotFoundException("FileManager is disabled for this command: " + getName() +
                        "\nPlease connect the FileManager in code and try once again");
        } catch (ReceiverNotFoundException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
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
