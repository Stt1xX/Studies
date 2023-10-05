package Server.Receivers;

import Storage.StorageData.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

/**
 * It works with files, both with Movie storage and with scripts.
 *
 * @author Stt1xX
 */
public class FileManager extends Manager {

//    public static final String PATH = "MoviesStorage.xml";
    public static final String PATH = System.getenv("MOVIES_PATH3");
    private final Stack<String> scriptStack = new Stack<>();
    public FileManager(String name) {
        super(name);
    }

    public void save(CollectionManager collectionManager){
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marchaller = context.createMarshaller();
            marchaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marchaller.marshal(collectionManager, new File(PATH));
            System.out.println("Коллекция успешно сохранена!");
        } catch(JAXBException ex){
            System.out.println("Сохранить коллекцию не получилось...");
        } catch(NullPointerException ex){
            System.out.println("We can't save your data. Please, fix your environment variable!");
        }
    }
    public String read(CollectionManager collectionManager){
        try{
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarchaller = context.createUnmarshaller();
            if (collectionManager.movies == null || collectionManager.movies.size() == 0){
                collectionManager.movies = ((CollectionManager) unmarchaller.unmarshal(new File(PATH))).movies;
            }
            return "Коллекция считана. В коллекции " + collectionManager.movies.size() + " элементов.";
        } catch(JAXBException ex){
            return "Считать коллекцию не получилось, файл поврежден";
        } catch(IllegalArgumentException ex){
            return "Файл-хранилище не найден";
        } catch(NullPointerException ex){
            System.exit(0);
            return null;
        }
    }
}
