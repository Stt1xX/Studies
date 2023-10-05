package Server.Receivers;

import Storage.StorageData.Movie;
import Storage.StorageData.MovieGenre;
import Server.Exceptions.InvalidArgumentException;
import Server.Exceptions.InvalidStorageException;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.*;
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

    private Integer currentId = 0;

    private boolean flagForHide = true;

    public CollectionManager(String name) {
        super(name);
    }
    public void setFlagForHide(boolean flagForHide) {
        this.flagForHide = flagForHide;
    }

    private void searchLaidId(){
        for (Movie movie : movies){
            laidId.add(movie.getId());
        }
    }

    public Stream<Movie> getMovieStream() {
        return movies.stream();
    }

    public String add(Movie movie){
            setId(movie);
            setDate(movie);
            movies.add(movie);
            return "Фильм успешно добавлен!";
    }

    public String remove_head(){
        if (movies.isEmpty()){
            return "Коллекция фильмов пока пуста";
        }
        else{
            List<Movie> list = new ArrayList<>(movies);
            Collections.sort(list);
            Movie movie = list.remove(0);
            laidId.remove(movie.getId());
//            movies = getMovieStream()
//                    .sorted()
//                    .skip(1)
//                    .collect(Collectors.toCollection(ArrayDeque::new));
            return "Первый фильм коллекции успешно удален (с минимальным id)";
        }
    }

    public String add_if_min(Movie newMovie){
        setDate(newMovie);
        if (movies.isEmpty() ||
                getMovieStream().min(new OscarCountComparator()).get().getOscarsCount() > newMovie.getOscarsCount()){
            setId(newMovie); // присваиваем id только тут, так как при присвоении автоматом закидывает в laidIdStorage, хотя newMovie может оказаться невалидным
            movies.add(newMovie);
            return "Фильм " + newMovie.getName() + " оказался с наименьшим значеним, поэтому был успешно добавлен! (по премиям Оскара)";
        }
        else{
            return "Фильм " + newMovie.getName() + " не оказался наимешньшим, поэтому добавление отменяется! (по премиям Оскара)";
        }
    }

    public String remove_greater(){
        if (movies.isEmpty()){
            return "Коллекция флиьмов пока пуста";
        }
        else{
            List<Movie> list = new ArrayList<>(movies);
            list.sort(new OscarCountComparator());
            Movie movie = list.remove(list.size() - 1);
            laidId.remove(movie.getId());
            movies = new ArrayDeque<>(list);
            return "Фильм " + movie.getName() + " был успешно удален!(Самое большое число премий Оскара)";
        }
    }

    public String count_greater_than_height(String heightStr){
        try {
            if (Float.parseFloat(heightStr) <= 0) {
                throw new InvalidArgumentException("Рост оператора должен быть больше нуля");
            }
            if (!Float.isFinite(Float.parseFloat(heightStr))) {
                throw new InvalidArgumentException("Число слишкмо большое");
            }
//            int counter = 0;
//            float height = Float.parseFloat(heightStr);
//            for (Movie movie: movies){
//                if (movie.getOperator().getHeight() > height){
//                    counter++;
//                }
//            }
        return "В коллекции содержится " +
                getMovieStream().filter(movie -> movie.getOperator().getHeight() > Float.parseFloat(heightStr)).count() +
                " фильмов, режиссеры которых выше заданного роста или такого же!";
        } catch(InvalidArgumentException ex){
            return ex.getMessage();
        } catch(NumberFormatException ex){
            return "Невалидный формат числа";
        }
    }

    public String sum_of_oscars_count(){
        int sum = 0;
        if (!movies.isEmpty()){
            sum = getMovieStream().map(Movie::getOscarsCount).reduce(Integer::sum).get();
        }
        return "Количество премий Оскара полученное всеми фильмами в коллекции: " + sum;
    }

    public String group_counting_by_genre(){

        var List1 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.ACTION).toList();
        var List2 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.DRAMA).toList();
        var List3 = getMovieStream().filter(movie -> movie.getGenre() == MovieGenre.TRAGEDY).toList();

        return "Фильмы жанра Экшн (Всего " + List1.size() + "):\n" + List1 + "\n\n___________________________________________________\n"
        + "\nФильмы жанра Драма (Всего " + List2.size() + "):\n" + List2 + "\n\n___________________________________________________\n"
        + "\nФильмы жанра Трагедия (Всего " + List3.size() + "):\n" + List3;
    }
    public String update(String idNumber, Movie newMovie) {
        try {
            Movie oldMovie = getMovieById(idNumber);
            newMovie.setId(Integer.parseInt(idNumber));
            setDate(newMovie);

            List<Movie> list = new ArrayList<>(movies);
            list.set(list.indexOf(oldMovie), newMovie);
            movies = new ArrayDeque<>(list);
            return "Фильм успешно обновлен!";
        } catch (NumberFormatException ex) {
                return "В id обнаружены недопустимые символы";
        } catch (InvalidArgumentException ex){
                return ex.getMessage();
        }
    }

    public String remove_by_id(String idNumber){
        try{
            Movie movie = getMovieById(idNumber);
            movies.remove(movie);
            laidId.remove(Integer.parseInt(idNumber));
            return "Фильм " + movie.getName() + " под номером " + idNumber + " успешно удален!";
        } catch (NumberFormatException ex) {
            return "В id обнаружены недопустимые символы";
        } catch (InvalidArgumentException ex){
            return ex.getMessage();
        }
    }

    public String clear(){
        movies = new ArrayDeque<>();
        return "Коллекция фильмов успешно очищена!";
    }

    public String info(){
        return "Коллекция фильмов Movies\nДата инициализации коллекции: " + new Date() + "\nКоличество эле" +
                "ментов коллекции: " + movies.size() + "\nТип коллекции: " + movies.getClass();
    }

    public String show(){
        StringBuilder s = new StringBuilder();
        if (movies.size() == 0)
            return "Коллекция пуста";
        else
            for (Movie movie : movies)
                s.append(movie.toString());
        return s.toString();
    }




    // команда должна выполняться один единственынй раз, когда запускается программа, которая считывает фильмы
    // из коллекции. Служит для оптимизации добавления фильма (пока не знаю, где выполнить)
    private void setCurrIdToMaxId(){
        int maxId = 0;
        for (Integer i : laidId) {
            if (maxId < i){
                maxId = i;
            }
        }
        currentId = laidId.isEmpty() ? laidId.stream().max(Integer::compareTo).get() : 0;
    }

    private void setId(Movie movie){ // Может быть бесконечным
        currentId++;
        if (currentId == Integer.MAX_VALUE){
            currentId = 1;
        }
        while(laidId.contains(currentId)){
            currentId++;
        }
        movie.setId(currentId);
        laidId.add(currentId);
    }

    private void setDate(Movie movie){
        movie.setCreationDate(new Date());
    }

    private Movie getMovieById(String idNumber) throws InvalidArgumentException, NumberFormatException{
        int id = Integer.parseInt(idNumber);
        if (!laidId.contains(id)){
            throw new InvalidArgumentException("Такого номера id фильма не сущесвтует");
        }
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return getMovieStream().filter(movie -> movie.getId() == id).findAny().get();
    }

    private boolean checkMoviesAtStart(Deque<Movie> movies) throws NoSuchElementException{
        try{
            //проверка на уникальность id
            Set<Integer> laidId = new HashSet<>();
            for(Movie movie : movies){
                laidId.add(movie.getId());
            }
            if(laidId.size() != movies.size()){
                throw new InvalidStorageException("Похоже в хранилище содержатся фильмы с одинаковым id");
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

                    throw new InvalidStorageException("Похоже в хранилище содержатся фильмы с " +
                            "невалидными значениями");
                else
                    return true;
            }

        } catch (InvalidStorageException ex){
            System.out.println(ex.getMessage());
            System.out.println("Коллекция фильмов пуста");
            return false;
        }
        return false;
    }

    public String start(FileManager fileManager){
        try {
            String response = fileManager.read(this); // присваеваем сохраненную коллекцию
            if (movies != null && checkMoviesAtStart(movies)) { // проверяем валидность коллекции
                searchLaidId(); // загружаем id сохраненной коллекции
                setCurrIdToMaxId();
//                System.out.println("bob");
            } else {
//                System.out.println("blob");
                movies = new ArrayDeque<>();
            }
            return response;
        } catch(NoSuchElementException ex){
            System.exit(0);
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