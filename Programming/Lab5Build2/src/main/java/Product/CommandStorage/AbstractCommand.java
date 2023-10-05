package Product.CommandStorage;

import Product.Receivers.*;
import Product.Exceptions.InvalidArgumentException;
import Product.Exceptions.ReceiverNotFoundException;
import Product.TerminalInterpreter;
import java.util.ArrayList;

/**
 * This class describes common fields and methods containing commands
 *
 * @see #argumentNumber the number of command arguments (arguments separated by spaces) required for the correct interpretation of the command
 *
 * @author Stt1xX
 */

public abstract class AbstractCommand {
    private final int argumentNumber;
    private String[] personalArguments;
    private TerminalInterpreter terminalInterpreter;
    public ArrayList<Manager> personalAccesses = new ArrayList<>();
    protected CollectionManager accessCollectionManager;
    protected FileManager accessFileManager;
    protected ProgramManager accessProgramManager;
    protected final ReceiverStorage receiverStorage;
    protected final String name;

    public abstract void execute(String[] argument);
    public abstract String getDescription();
    public AbstractCommand(ReceiverStorage receiverStorage, String name, int argumentNumber){
        receiverStorage.commandStorage.put(name, this);
        this.receiverStorage = receiverStorage;
        personalAccesses.add(receiverStorage);
        this.name = name;
        this.argumentNumber = argumentNumber;
        if (argumentNumber < 0){
            try {
                throw new InvalidArgumentException("Число аргументов команды должно быть равно нулю или положительным: " +
                        getName());
            } catch (InvalidArgumentException ex){
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
    }

    public void setTerminalInterpreter(TerminalInterpreter terminalInterpreter) {
        this.terminalInterpreter = terminalInterpreter;
    }

    public TerminalInterpreter getTerminalInterpreter() {
        return terminalInterpreter;
    }

    public int getArgumentNumber() {
        return argumentNumber;
    }

    public void setPersonalArguments(String[] personalArguments) {
        this.personalArguments = personalArguments;
    }

    public String[] getPersonalArguments() {
        return personalArguments;
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
