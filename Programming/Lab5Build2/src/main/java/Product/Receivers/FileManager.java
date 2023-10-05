package Product.Receivers;

import Product.StorageData.Movie;
import Product.TerminalInterpreter;
import Product.Exceptions.InvalidScriptException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

/**
 * It works with files, both with Movie storage and with scripts.
 *
 * @author Stt1xX
 */
public class FileManager extends Manager{

//    public static final String PATH = "MoviesStorage.xml";
    public static final String PATH = System.getenv("MOVIES_PATH");
    private final Stack<Path> scriptStack = new Stack<>();
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
        }
    }
    public Deque<Movie> read(){
        try{
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarchaller = context.createUnmarshaller();
            return ((CollectionManager) unmarchaller.unmarshal(new File(PATH))).movies;
        } catch(JAXBException ex){
            System.out.println("Считать коллекцию не получилось, файл поврежден");
        } catch(IllegalArgumentException ex){
            System.out.println("Файл-хранилище не найден");
        }
        return null;
    }

    public void execute_script(String path, CollectionManager collectionManager, ReceiverStorage receiverStorage){
        try {
            scriptStack.push(Path.of(path).toAbsolutePath());
            if(Collections.frequency(scriptStack, Path.of(path).toAbsolutePath()) == 2){
                scriptStack.pop();
                throw new InvalidScriptException("В скрипте присутствует рекурсивный вызов, его продолжение невозоможно");
            }
            collectionManager.setFlagForHide(false);
            TerminalInterpreter terminalInterpreter = new TerminalInterpreter(new Scanner(new File(path)));
            terminalInterpreter.goAction(receiverStorage);
            scriptStack.pop();
            if (scriptStack.isEmpty())
                collectionManager.setFlagForHide(true);
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не найден");
        } catch (InvalidScriptException ex){
            System.out.println(ex.getMessage());
        }
    }
}
