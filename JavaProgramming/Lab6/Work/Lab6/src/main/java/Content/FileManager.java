package Content;

import Content.Data.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Class for all file operations and XML marshalling.
 *
 * @author St1xXx
 * @version 0.1
 * @exception IOException, JAXBException
 */
@XmlType(name = "storage")
@XmlRootElement
public class FileManager extends CollectionManager {

    @XmlElement(name = "movies")
    public ArrayDeque<Movie> movies = arrayDeque;
    @XmlTransient
    public final String path = System.getenv("MOVIES_PATH");

    public FileManager() {

    }

    public void exportToFile(CollectionManager collectionManager) {
        // add to movies in Content.FileManager
        FileManager fileManager = new FileManager();
        fileManager.movies.addAll(collectionManager.arrayDeque);

        try {

            // create marshaller
            JAXBContext context = JAXBContext.newInstance(Movie.class, Person.class, Location.class,
                    Coordinates.class, FileManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            PrintWriter fileOutput = new PrintWriter(path + "Movies.xml");
            marshaller.marshal(fileManager, fileOutput);
            // create marshaller

        } catch (JAXBException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public CollectionManager importFromFile() { // return ready Collection
        try {

            JAXBContext context = JAXBContext.newInstance(Movie.class, Person.class, Location.class,
                    Coordinates.class, FileManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();


            // Bloody parse

            Path path = Paths.get(this.path + "Movies.xml");
            Scanner scanner = new Scanner(path);
            StringBuilder storage = new StringBuilder();
            while (scanner.hasNextLine()) {
                storage.append(scanner.nextLine());
            }
            StringReader reader = new StringReader(storage.toString());
            // Bloody parse

            // Content.FileManager fileManager = (Content.FileManager) unmarshaller.unmarshal(new File("***"));
            //how would parse the normal guy
            FileManager fileManager = (FileManager) unmarshaller.unmarshal(reader);
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.arrayDeque = fileManager.movies;
            ArrayList<Long> allId = new ArrayList<>();
            for (Movie movieForCheck : collectionManager.arrayDeque) {
                if (
                        movieForCheck.getOperator().getNationality() != null
                                && movieForCheck.getOperator().getEyeColor() != null
                                && movieForCheck.getOperator().getLocation() != null
                                && movieForCheck.getId() != null
                                && movieForCheck.getOperator() != null
                                && movieForCheck.getCreationDate() != null
                                && movieForCheck.getName() != null
                                && movieForCheck.getGenre() != null
                                && movieForCheck.getCoordinates() != null
                                && movieForCheck.getOscarsCount() != null
                                && movieForCheck.getOperator().getName() != null
                                && movieForCheck.getCoordinates().getY() != null
                                && movieForCheck.getCoordinates().getX() != null
                                && movieForCheck.getOperator().getLocation().getX() != null
                                && movieForCheck.getOperator().getLocation().getY() != null
                                && movieForCheck.getOperator().getLocation().getZ() != null
                                && !allId.contains(movieForCheck.getId())
                ) {
                    allId.add(movieForCheck.getId());
                } else {
                    System.out.println("исходный xml файл некорректный, " +
                            "убедитесь, что у всех фильмов разное поле id,enum заданы правильно, а также, " +
                            "все необходимые поля заполнены.\nНапишите 'y' " +
                            "если хотите продолжить с пустой коллекцией " +
                            "и любой другой символ если хотите преравть программу.");
                    Scanner scannerForConsole = new Scanner(System.in);
                    String otvet = scannerForConsole.nextLine();
                    if (otvet.equals("y")) {
                        return new CollectionManager();
                    } else {
                        System.out.println("Работа программы завершена.");
                        System.exit(0);
                    }
                }

            }
            return collectionManager;
        } catch (IOException e) {
            System.out.println("Убедитесь, что в переменной окружения правильный путь.");
        } catch (JAXBException e) {
            System.out.println("Маша права, файл не валидный.");
        }
        return new CollectionManager();
    }

//    public Scanner getScanner(String scriptAdress) {
//        Path path = Paths.get(scriptAdress);
//        try {
//            return new Scanner(path);
//        } catch (IOException e) {
//            System.out.println("Убедитесь, что в переменной окружения правильный путь.");
//        }
//        return null;
//    }
}