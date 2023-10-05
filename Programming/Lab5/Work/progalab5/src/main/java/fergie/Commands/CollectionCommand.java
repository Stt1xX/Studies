package fergie.Commands;

import fergie.CollectionManager;

/**
 * Abstract class for commands that require access to the collection
 * @author FergieDoigrales
 * @version 0.1
 */
public abstract class CollectionCommand implements Command {
    protected CollectionManager collectionManager;

    protected static boolean status = true;

    public static void changeStatus(){
        status = !status;
    }

    public CollectionCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;

    }
}
