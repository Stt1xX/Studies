package Server.Receivers;


import Server.CommandStorage.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The main receiver
 * This is an unusual manager - it stores collections of all receivers,
 *  contains a collection of Movies, also a collection of all program commands
 *
 * @author Stt1xX
 */

public class ReceiverStorage extends Manager {
    public final Map<String, AbstractCommand> commandStorage = new HashMap<>();
    private ProgramManager programManager;
    private FileManager fileManager;
    private CollectionManager collectionManager;
    private ReceiverStorage receiverStorage;

    public ReceiverStorage(String name){

        super(name);
        FileManager fileManager = new FileManager("FileManager");
        CollectionManager collectionManager = new CollectionManager("CollectionManager");
        ProgramManager programManager = new ProgramManager("ProgramManager");

        this.receiverStorage = this;
        this.programManager = programManager;
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
        this.turnOn();
        programManager.turnOn();
        fileManager.turnOn();
        collectionManager.turnOn();

//        System.out.println("Сервер запущен и готов к работе!\n\n");

        AbstractCommand add = new Add(this, "add");
        add.setAccessCollectionManager(collectionManager);
        AbstractCommand info = new Info(this, "info");
        info.setAccessCollectionManager(collectionManager);
        AbstractCommand show = new Show(this,  "show");
        show.setAccessCollectionManager(collectionManager);
        AbstractCommand update = new Update(this, "update");
        update.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_by_id = new Remove_by_id(this, "remove_by_id");
        remove_by_id.setAccessCollectionManager(collectionManager);
        AbstractCommand clear = new Clear(this, "clear");
        clear.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_head = new Remove_head(this, "remove_head");
        remove_head.setAccessCollectionManager(collectionManager);
        AbstractCommand add_if_min = new Add_if_min(this, "add_if_min");
        add_if_min.setAccessCollectionManager(collectionManager);
        AbstractCommand remove_greater = new Remove_greater(this, "remove_greater");
        remove_greater.setAccessCollectionManager(collectionManager);
        AbstractCommand sum_of_oscars_count = new Sum_of_oscars_count(this, "sum_of_oscars_count");
        sum_of_oscars_count.setAccessCollectionManager(collectionManager);
        AbstractCommand group_counting_by_genre = new Group_counting_by_genre(this, "group_counting_by_genre");
        group_counting_by_genre.setAccessCollectionManager(collectionManager);
        AbstractCommand count_greater_than_height = new Count_greater_than_height(this, "count_greater_than_height");
        count_greater_than_height.setAccessCollectionManager(collectionManager);
        AbstractCommand save = new Save(this, "save");
        save.setAccessCollectionManager(collectionManager);
        save.setAccessFileManager(fileManager);
        AbstractCommand exit = new Exit(this, "exit");
        exit.setAccessProgramManager(programManager);
    }
    public AbstractCommand searchCommand(String nameCommand){
        return commandStorage.get(nameCommand);
    }


    public CollectionManager getCollectionManager(){
        return this.collectionManager;
    }

    public FileManager getFileManager(){
        return this.fileManager;
    }
}