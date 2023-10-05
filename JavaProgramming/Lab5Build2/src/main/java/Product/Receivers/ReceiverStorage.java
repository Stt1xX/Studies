package Product.Receivers;

import Product.CommandStorage.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The main receiver
 * This is an unusual manager - it stores collections of all receivers,
 *  contains a collection of Movies, also a collection of all program commands
 *
 * @author Stt1xX
 */

public class ReceiverStorage extends Manager{
    public final Map<String, AbstractCommand> commandStorage = new HashMap<>();
    private final Map<String, Manager> managerStorage = new HashMap<>();

    public ReceiverStorage(String name){

        super(name);
        FileManager fileManager = new FileManager("FileManager");
        CollectionManager collectionManager = new CollectionManager("CollectionManager");
        ProgramManager programManager = new ProgramManager("ProgramManager");

        addManagerToReceiverStorage(this);
        addManagerToReceiverStorage(programManager);
        addManagerToReceiverStorage(fileManager);
        addManagerToReceiverStorage(collectionManager);
        System.out.println("Программа запущена и готова к работе!\n\n");

        programManager.start(collectionManager, fileManager);

        AbstractCommand exit = new Exit(this, "exit", 0);
        exit.setAccessProgramManager(programManager);
        AbstractCommand add = new Add(this, "add", 0);
        add.setAccessCollectionManager(collectionManager);
        AbstractCommand help = new Help(this, "help", 0);
        AbstractCommand info = new Info(this, "info", 0);
        info.setAccessCollectionManager(collectionManager);
        AbstractCommand show = new Show(this,  "show", 0);
        show.setAccessCollectionManager(collectionManager);
        AbstractCommand update = new Update(this, "update", 1);
        update.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_by_id = new Remove_by_id(this, "remove_by_id", 1);
        remove_by_id.setAccessCollectionManager(collectionManager);
        AbstractCommand clear = new Clear(this, "clear", 0);
        clear.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_head = new Remove_head(this, "remove_head", 0);
        remove_head.setAccessCollectionManager(collectionManager);
        AbstractCommand add_if_min = new Add_if_min(this, "add_if_min", 0);
        add_if_min.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_greater = new Remove_greater(this, "remove_greater", 0);
        remove_greater.setAccessCollectionManager(collectionManager);
        AbstractCommand sum_of_oscars_count = new Sum_of_oscars_count(this, "sum_of_oscars_count", 0);
        sum_of_oscars_count.setAccessCollectionManager(collectionManager);
        AbstractCommand group_counting_by_genre = new Group_counting_by_genre(this, "group_counting_by_genre", 0);
        group_counting_by_genre.setAccessCollectionManager(collectionManager);
        AbstractCommand count_greater_than_height = new Count_greater_than_height(this, "count_greater_than_height", 1);
        count_greater_than_height.setAccessCollectionManager(collectionManager);
        AbstractCommand save = new Save(this, "save", 0);
        save.setAccessCollectionManager(collectionManager);
        save.setAccessFileManager(fileManager);
        AbstractCommand execute_script = new Execute_script(this, "execute_script", 1);  // путь файла не должен содержать пробелов - пощитает за лишний аргумент
        execute_script.setAccessCollectionManager(collectionManager); // как минимум нужен чтобы запретить вывод всем командам, использующим hidePrint
        execute_script.setAccessFileManager(fileManager);
    }
    public void addManagerToReceiverStorage(Manager manager){
        this.managerStorage.put(manager.getName(), manager);
        manager.turnOn();
    }

    public void deleteManagerToReceiverStorage(Manager manager){ // пока не используется (может в будущеем понадобится
        this.managerStorage.remove(manager.getName());
        manager.turnOf();
    }

    public AbstractCommand searchCommand(String nameCommand){
        return commandStorage.get(nameCommand);
    }

    public void help(){
        for (AbstractCommand command : commandStorage.values()){
            System.out.println(command.getDescription());
        }
    }
}