package Product.Receivers;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * Contains main methods such as {@link #exit()} and {@link #start(CollectionManager, FileManager)}.
 *
 * @author Stt1xX
 */

public class ProgramManager extends Manager{
    public ProgramManager(String name) {
        super(name);
    }

    public void exit(){
        System.exit(0);
    }

    public void start(CollectionManager collectionManager, FileManager fileManager){
        try {
            collectionManager.movies = fileManager.read(); // присваеваем сохраненную коллекцию
            if (collectionManager.movies != null && collectionManager.checkMoviesAtStart(collectionManager.movies)) { // проверяем валидность коллекции
                collectionManager.searchLaidId(); // загружаем id сохраненной коллекции
                collectionManager.setCurrIdToMaxId();
            } else {
                collectionManager.movies = new ArrayDeque<>();
            }
        } catch(NoSuchElementException ex){
            System.exit(0);
        }
    }
}
