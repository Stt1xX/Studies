package Server.Receivers;

import Storage.StorageData.Movie;
import Storage.StorageData.MovieGenre;
import Server.Exceptions.InvalidArgumentException;
import Server.Exceptions.InvalidStorageException;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * The biggest receiver
 * Contains methods for working with the Movies collection
 *
 * @author Stt1xX
 */
@XmlRootElement(name = "MoviesStorage")
public class CollectionManager extends Manager {

    public CollectionManager(){

    }
    @XmlElement(name = "movie")
    public Deque<Movie> movies = new ArrayDeque<>();
    private final HashSet<Integer> laidId = new HashSet<>();

    public CollectionManager(String name) {
        super(name);
    }

    public Stream<Movie> getMovieStream() {
        return movies.stream();
    }

    public boolean getPermission(String name, Movie movie){
        return name.equals(movie.getOwner());
    }


    public Lock lock = new  ReentrantLock();
    public String add(Movie movie, ReceiverStorage receiverStorage, String owner) throws SQLException {

            receiverStorage.getDataBaseManager().add(movie, owner);
            movies.add(movie);
            return "The film has been added successfully!";
    }

    public String remove_head(ReceiverStorage receiverStorage, String owner) throws SQLException{
        lock.lock();
        try {
            if (movies.isEmpty()) {
                return "The collection is empty";
            } else {
                List<Movie> list = new ArrayList<>(movies);
                Collections.sort(list);
                Movie movie = list.get(0);
                if (!getPermission(owner, movie)) {
                    return "You do not have the rights to delete this object, it belongs to the user " + movie.getOwner();
                }
                receiverStorage.getDataBaseManager().remove(movie.getId(), movie.getOperator().getOperatorId());
                list.remove(0);
                movies = new ArrayDeque<>(list);
                return "The first movie of the collection was successfully deleted (with minimal id)";
            }
        } finally {
            lock.unlock();
        }
    }

    public String add_if_min(ReceiverStorage receiverStorage, String owner, Movie newMovie) throws SQLException{
        lock.lock();
        try {
            if (movies.isEmpty() ||
                    getMovieStream().min(new OscarCountComparator()).get().getOscarsCount() > newMovie.getOscarsCount()) {
                receiverStorage.getDataBaseManager().add(newMovie, owner);
                movies.add(newMovie);
                return "The film " + newMovie.getName() + " with the lowest value, so was successfully added! (according to the Oscars count)";
            } else {
                return "The film " + newMovie.getName() + " with no lowest value, so wasn't added! (according to the Oscars count)";
            }
        } finally{
            lock.unlock();
        }
    }

    public String remove_greater(ReceiverStorage receiverStorage, String owner) throws SQLException{
        lock.lock();
        try {
            if (movies.isEmpty()) {
                return "The collection is empty";
            } else {
                List<Movie> list = new ArrayList<>(movies);
                list.sort(new OscarCountComparator());
                Movie movie = list.get(list.size() - 1);
                if (!getPermission(owner, movie)) {
                    return "You do not have the rights to delete this object, it belongs to the user " + movie.getOwner();
                }
                receiverStorage.getDataBaseManager().remove(movie.getId(), movie.getOperator().getOperatorId());
                list.remove(movie);
                movies = new ArrayDeque<>(list);
                return "The film " + movie.getName() + " was successful deleted!(the biggest number of Oscars)";
            }
        } finally {
            lock.unlock();
    }
    }

    public String count_greater_than_height(String heightStr){
        lock.lock();
        try {
            if (Float.parseFloat(heightStr) <= 0) {
                throw new InvalidArgumentException("Height should be greater than zero");
            }
            if (!Float.isFinite(Float.parseFloat(heightStr))) {
                throw new InvalidArgumentException("The number is too high");
            }

        return "The collection contains " +
                getMovieStream().filter(movie -> movie.getOperator().getHeight() > Float.parseFloat(heightStr)).count() +
                    " films whose directors are above the specified height or the same!";
        } catch(InvalidArgumentException ex){
            return ex.getMessage();
        } catch(NumberFormatException ex){
            return "Invalid number format";
        } finally {
            lock.unlock();
        }
    }

    public String sum_of_oscars_count(){
        lock.lock();
        try {
            int sum = 0;
            if (!movies.isEmpty()) {
                sum = getMovieStream().map(Movie::getOscarsCount).reduce(Integer::sum).get();
            }
            return "The number of Oscars received by all films in the collection: " + sum;
        } finally {
            lock.unlock();
        }
    }

    public String group_counting_by_genre(){
        lock.lock();
        try {
            var List1 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.ACTION).toList();
            var List2 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.DRAMA).toList();
            var List3 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.TRAGEDY).toList();

            return "Action Movies (Total " + List1.size() + "):\n" + List1 + "\n\n___________________________________________________\n"
                    + "\nDrama Movies(Total " + List2.size() + "):\n" + List2 + "\n\n___________________________________________________\n"
                    + "\nTragedy Movies (Total " + List3.size() + "):\n" + List3;
        } finally {
            lock.unlock();
        }
    }
    public String update(ReceiverStorage receiverStorage, String owner, String idNumber, Movie newMovie) throws SQLException{
        lock.lock();
        try {
            Movie oldMovie = getMovieById(idNumber);
            if(!getPermission(owner, oldMovie)){
                return "You do not have the rights to delete this object, it belongs to the user " + oldMovie.getOwner();
            }
            receiverStorage.getDataBaseManager().update(oldMovie.getId(), oldMovie.getOperator().getOperatorId(), newMovie);
            List<Movie> list = new ArrayList<>(movies);
            list.set(list.indexOf(oldMovie), newMovie);
            movies = new ArrayDeque<>(list);
            return "The film has been successfully updated!";
        } catch (NumberFormatException ex) {
                return "Invalid characters found in id";
        } catch (InvalidArgumentException ex){
                return ex.getMessage();
        } finally {
            lock.unlock();
        }
    }

    public String remove_by_id(ReceiverStorage receiverStorage, String owner, String idNumber) throws SQLException{
        lock.lock();
        try{
            Movie movie = getMovieById(idNumber);
            if(!getPermission(owner, movie)){
                return "You do not have the rights to delete this object, it belongs to the user " + movie.getOwner();
            }
            receiverStorage.getDataBaseManager().remove(movie.getId(), movie.getOperator().getOperatorId());
            movies.remove(movie);
            return "The film " + movie.getName() + " at number " + idNumber + " has been successfully deleted!";
        } catch (NumberFormatException ex) {
            return "Invalid characters found in id";
        } catch (InvalidArgumentException ex){
            return ex.getMessage();
        } finally {
            lock.unlock();
        }
    }

    public String clear(ReceiverStorage receiverStorage) throws SQLException {
        lock.lock();
        try {
            movies = new ArrayDeque<>();
            receiverStorage.getDataBaseManager().clear();
            return "Collection has been successfully cleared!";
        } finally {
            lock.unlock();
        }
    }

    public String info(){
        lock.lock();
        try {
            return "The Collection of Movies\nInitialization date: " + new Date() + "\nnumber of elements: " +
                    movies.size() + "\nCollection Type: " + movies.getClass();
        } finally {
            lock.unlock();
        }
    }

    public String show(){
        lock.lock();
        try {
            StringBuilder s = new StringBuilder();
            if (movies.size() == 0)
                return "The collection is empty";
            else
                for (Movie movie : movies)
                    s.append(movie.toString());
            return s.toString();
        }finally {
            lock.unlock();
        }
    }

    private Movie getMovieById(String idNumber) throws InvalidArgumentException, NumberFormatException{

        int id = Integer.parseInt(idNumber);
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        throw new InvalidArgumentException("There is no such movie id number");
    }

    private boolean checkMoviesAtStart(Deque<Movie> movies) throws NoSuchElementException{
        try{
            //проверка на уникальность id
            Set<Integer> laidId = new HashSet<>();
            for(Movie movie : movies){
                laidId.add(movie.getId());
            }
            if(laidId.size() != movies.size()){
                throw new InvalidStorageException("It looks like the repository contains movies with the same id");
            }

            //проверка на корректность полей и их присутствие, если оно обязательно
            for (Movie movie : movies){
                if (movie.getId() <= 0 || movie.getName() == null || movie.getName().equals("") ||
                        movie.getCoordinates() == null || movie.getCoordinates().getX() > 349
                        || movie.getCreationDate() == null || movie.getOscarsCount() <= 0 ||
                        movie.getGenre() == null || movie.getMpaaRating() == null ||
                        movie.getOperator() == null || movie.getOperator().getName() == null ||
                        movie.getOperator().getName().equals("") || movie.getOperator().getHeight() < 0 ||
                        movie.getOperator().getHeight() == null || movie.getOperator().getEyeColor() == null ||
                        movie.getOperator().getNationality() == null || movie.getOperator().getLocation() == null ||
                        movie.getOperator().getLocation().getZ() == null ||
                        movie.getOperator().getLocation().getName() == null ||
                        movie.getOperator().getLocation().getName().equals("") ||
                        movie.getOperator().getLocation().getName().length() > 870)

                    throw new InvalidStorageException("It looks like the repository contains movies with " +
                            "invalid values");
                else
                    return true;
            }

        } catch (InvalidStorageException ex){
            System.out.println(ex.getMessage());
            System.out.println("The collection is empty");
            return false;
        }
        return false;
    }

    public String start(DataBaseManager dataBaseManager){
        lock.lock();
        try {
            String response = dataBaseManager.read(this); // присваеваем сохраненную коллекцию
            if (!(movies != null && checkMoviesAtStart(movies))) { // проверяем валидность коллекции
//                searchLaidId(); // загружаем id сохраненной коллекции
//                setCurrIdToMaxId();
                movies = new ArrayDeque<>();
            }
            return response;
        } catch(NoSuchElementException ex){
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}

/**
 * As you can guess, This is a class who compare movies by the number of Oscars
 */
class OscarCountComparator implements Comparator<Movie>{

    @Override
    public int compare(Movie movie1, Movie movie2) {
        return movie1.getOscarsCount() - movie2.getOscarsCount();
    }
}

class NameComparator implements Comparator<Movie>{
    @Override
    public int compare(Movie movie1, Movie movie2){
        return movie1.getName().compareTo(movie2.getName());
    }
}