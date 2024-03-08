package Product.Receivers;

import Product.Exceptions.InvalidArgumentException;
import Product.StorageData.*;
import Product.Exceptions.InvalidStorageException;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * The biggest receiver
 * Contains methods for working with the Movies collection
 *
 * @author Stt1xX
 */
@XmlRootElement(name = "MoviesStorage")
public class CollectionManager extends Manager{

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

    public void searchLaidId(){
        for (Movie movie : movies){
            laidId.add(movie.getId());
        }
    }

    public void add(Scanner scanner){
            Movie movie = new Movie();
            setId(movie);
            setName(scanner, movie);
            setCoordinates(scanner, movie);
            setDate(movie);
            setOscarsCount(scanner, movie);
            setMovieGenre(scanner, movie);
            setMpaaRating(scanner, movie);
            setPerson(scanner, movie);
            movies.add(movie);
        System.out.println("Фильм успешно добавлен!");
    }

    public void remove_head(){
        if (movies.size() == 0){
            hidePrint("Коллекция фильмов пока пуста");
        }
        else{
            List<Movie> list = new ArrayList<>(movies);
            Collections.sort(list);
            Movie movie = list.remove(0);
            laidId.remove(movie.getId());
            movies = new ArrayDeque<>(list);
            System.out.println("Первый фильм коллекции успешно удален (с минимальным id)");
        }
    }

    public void add_if_min(Scanner scanner){
        boolean flag = true;
        Movie newMovie = new Movie();
        setName(scanner, newMovie);
        setCoordinates(scanner, newMovie);
        setDate(newMovie);
        setOscarsCount(scanner, newMovie);
        setMovieGenre(scanner, newMovie);
        setMpaaRating(scanner, newMovie);
        setPerson(scanner, newMovie);
        for(Movie movie: movies){
            if (movie.getOscarsCount() < newMovie.getOscarsCount()){
                flag = false;
                break;
            }
        }
        if (flag){
            setId(newMovie); // присваиваем id только тут, так как при присвоении автоматом закидывает в laidIdStorage, хотя newMovie может оказаться невалидным
            movies.add(newMovie);
            System.out.println("Фильм " + newMovie.getName() + " оказался с наименьшим значеним, поэтому был успешно добавлен!");
        }
        else{
            hidePrint("Фильм " + newMovie.getName() + " не оказался наимешньшим, поэтому добавление отменяется! ");
        }
    }

    public void remove_greater(){
        if (movies.size() == 0){
            hidePrint("Коллекция флиьмов пока пуста");
        }
        else{
            List<Movie> list = new ArrayList<>(movies);
            list.sort(new OscarCountComparator());
            Movie movie = list.remove(list.size() - 1);
            laidId.remove(movie.getId());
            movies = new ArrayDeque<>(list);
            System.out.println("Фильм " + movie.getName() + " был успешно удален!(Самое большое число премий Оскара)");
        }
    }

    public void count_greater_than_height(String heightStr){
        try {
            if (Float.parseFloat(heightStr) <= 0) {
                throw new InvalidArgumentException("Рост оператора должен быть больше нуля");
            }
            if (!Float.isFinite(Float.parseFloat(heightStr))) {
                throw new InvalidArgumentException("Число слишкмо большое");
            }
            int counter = 0;
            float height = Float.parseFloat(heightStr);
            for (Movie movie: movies){
                if (movie.getOperator().getHeight() > height){
                    counter++;
                }
            }
            System.out.println("В коллекции содержится " + counter + " фильмов, режиссеры которых выше заданного роста или такого же!");
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        } catch(NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        }
    }

    public void sum_of_oscars_count(){
        int sum = 0;
        for(Movie movie: movies){
            sum += movie.getOscarsCount();
        }
        System.out.println("Количество премий Оскара полученное всеми фильмами в коллекции: " + sum);
    }

    public void group_counting_by_genre(){
        ArrayList<String> actionGroup = new ArrayList<>();
        ArrayList<String> dramaGroup = new ArrayList<>();
        ArrayList<String> tragedyGroup = new ArrayList<>();
        for (Movie movie: movies){
            if (movie.getGenre() == MovieGenre.ACTION){
                actionGroup.add(movie.getName());
                continue;
            }
            if (movie.getGenre() == MovieGenre.DRAMA){
                dramaGroup.add(movie.getName());
                continue;
            }
            if (movie.getGenre() == MovieGenre.TRAGEDY){
                tragedyGroup.add(movie.getName());
            }
        }
        System.out.println("Фильмы жанра Экшн (Всего " + actionGroup.size() + "):\n" + actionGroup + "\n\n___________________________________________________\n");
        System.out.println("Фильмы жанра Драма (Всего " + dramaGroup.size() + "):\n" + dramaGroup + "\n\n___________________________________________________\n");
        System.out.println("Фильмы жанра Трагедия (Всего " + tragedyGroup.size() + "):\n" + tragedyGroup);
    }
    public void update(Scanner scanner, String idNumber) {
        try {
            Movie oldMovie = getMovieById(idNumber);
            Movie newMovie = new Movie();
            newMovie.setId(Integer.parseInt(idNumber));
            setName(scanner, newMovie);
            setCoordinates(scanner, newMovie);
            setDate(newMovie);
            setOscarsCount(scanner, newMovie);
            setMovieGenre(scanner, newMovie);
            setMpaaRating(scanner, newMovie);
            setPerson(scanner, newMovie);
            List<Movie> list = new ArrayList<>(movies);
            list.set(list.indexOf(oldMovie), newMovie);
            movies = new ArrayDeque<>(list);
            System.out.println("Фильм успешно обновлен!");
        } catch (NumberFormatException ex) {
                System.out.println("В id обнаружены недопустимые символы");
        } catch (InvalidArgumentException ex){
                System.out.println(ex.getMessage());
        }
    }

    public void remove_by_id(String idNumber){
        try{
            Movie movie = getMovieById(idNumber);
            movies.remove(movie);
            laidId.remove(Integer.parseInt(idNumber));
            System.out.println("Фильм " + movie.getName() + " под номером " + idNumber + " успешно удален!");
        } catch (NumberFormatException ex) {
            System.out.println("В id обнаружены недопустимые символы");
        } catch (InvalidArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void clear(){
        movies = new ArrayDeque<>();;
        System.out.println("Коллекция фильмов успешно очищена!");
    }

    public void info(){
        System.out.println("Коллекция фильмов Movies\nДата инициализации коллекции: " + new Date() + "\nКоличество эле" +
                "ментов коллекции: " + movies.size() + "\nТип коллекции: " + movies.getClass());
    }

    public void show(){
        if (movies.size() == 0)
            System.out.println("Коллекция пуста");
        else
            for (Movie movie : movies)
                System.out.println(movie);
    }

    private void hidePrint(String text){
        if(flagForHide){
            System.out.println(text);
        }
    }

    // команда должна выполняться один единственынй раз, когда запускается программа, которая считывает фильмы
    // из коллекции. Служит для оптимизации добавления фильма (пока не знаю, где выполнить)
    public void setCurrIdToMaxId(){
        int maxId = 0;
        for (Integer i : laidId) {
            if (maxId < i){
                maxId = i;
            }
        }
        currentId = maxId;
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

    private void setName(Scanner scanner, Movie movie){
        try {
            hidePrint("Введите название фильма(String)");
                String s = scanner.nextLine().trim();
                if (s.equals("")){ // проверка на null не нужна, она необходима в случае, когда мы читаем хранилище фильмов (FileManager)
                    throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
                }
                movie.setName(s);
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
            setName(scanner, movie); // рекурсивно вызываем еще раз, пока не введет валидное название фильма
        }
    }


    private int setCoordinateX(Scanner scanner){
        try{
            hidePrint("Введите координату X(int)\n" +
                    "Ограничения: Максимальное значение поля: 349");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                return 0;
            }
            if (Integer.parseInt(str) > 349){
                throw new InvalidArgumentException("Число слишком большое");
            }
            return Integer.parseInt(str);
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        } catch (NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        }
        return setCoordinateX(scanner);
    }

    private int setCoordinateY(Scanner scanner){
        try{
            hidePrint("Введите координату Y(int)");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                return 0;
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        }
        return setCoordinateY(scanner);
    }

    private void setCoordinates(Scanner scanner, Movie movie){
        hidePrint("Ввод координат... ");
        movie.setCoordinates(new Coordinates(setCoordinateX(scanner), setCoordinateY(scanner)));
    }
    private void setDate(Movie movie){
        movie.setCreationDate(new Date());
    }

    private void setOscarsCount(Scanner scanner, Movie movie){
        try {
            hidePrint("Введите число премий Оскара фильмa(int)\nОграничения: значение поля должно быть больше нуля");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Поле обязательно для заполнения");
            }
            int i = Integer.parseInt(str);
            if (i <= 0){
                throw new InvalidArgumentException("Число премий должно быть больше нуля");
            }
            movie.setOscarsCount(i);
        } catch(NumberFormatException ex) {
            hidePrint("Невалидный формат числа");
            setOscarsCount(scanner, movie);
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
            setOscarsCount(scanner, movie);
        }
    }

    private void setMovieGenre(Scanner scanner, Movie movie){
        try {
            hidePrint("Введите жанр фильма(enum)\nВозможные варианты: " + Arrays.toString(MovieGenre.values()));
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            movie.setGenre(MovieGenre.valueOf(str.toUpperCase()));
        } catch (IllegalArgumentException ex){
            hidePrint("Такого жанра не существует");
            setMovieGenre(scanner, movie);
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());
            setMovieGenre(scanner, movie);
        }
    }

    private void setMpaaRating(Scanner scanner, Movie movie){
        try {
            hidePrint("Введите рейтинг фильма(enum)\nВозможные варианты: " + Arrays.toString(MpaaRating.values()));
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            movie.setMpaaRating(MpaaRating.valueOf(str.toUpperCase()));
        } catch (IllegalArgumentException ex){
            hidePrint("Такого рейтинга не существует");
            setMpaaRating(scanner, movie);
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());
            setMpaaRating(scanner, movie);
        }
    }

    private String setPersonName(Scanner scanner){
        try {
            hidePrint("Введите имя оператора(String)");
            String s = scanner.nextLine().trim();
            if (s.equals("")){ // проверка на null не нужна, она необходима в случае, когда мы читаем хранилище фильмов (FileManager)
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            return s;
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        }
        return setPersonName(scanner); // рекурсивно вызываем еще раз, пока не введет валидное название фильма
    }

    private Float setPersonHeight(Scanner scanner){
        try{hidePrint("Введите рост оператора(Float)\n" +
                "Ограничения: Значение поля должно быть больше нуля");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            if (Float.parseFloat(str) <= 0){
                throw new InvalidArgumentException("Рост оператора должен быть больше нуля");
            }
            if (!Float.isFinite(Float.parseFloat(str))){
                throw new InvalidArgumentException("Число слишкмо большое");
            }
            return Float.parseFloat(str);
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        } catch (NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        }
        return setPersonHeight(scanner);
    }

    private Color setPersonEyeColor(Scanner scanner){
        try {
            hidePrint("Введите цвет глаз оператора(enum)\nВозможные варианты: " + Arrays.toString(Color.values()));
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            return Color.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException ex){
            hidePrint("Такого цвета глаз не существует");
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());

        }
        return setPersonEyeColor(scanner);
    }

    private Country setPersonCountry(Scanner scanner){
        try {
            hidePrint("Введите родную страну оператора(enum)\nВозможные варианты: " + Arrays.toString(Country.values()));
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            return Country.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException ex){
            hidePrint("Такой странаы не существует");
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        }
        return setPersonCountry(scanner);
    }

    private float setPersonCoordinateX(Scanner scanner){
        try {
            hidePrint("Введите координату местонахождения оператора X(float)");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                return 0f;
            }
            if (!Float.isFinite(Float.parseFloat(str))){
                throw new InvalidArgumentException("Число слишкмо большое");
            }
            return Float.parseFloat(str);
        } catch (NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        }
        return setPersonCoordinateX(scanner);
    }

    private float setPersonCoordinateY(Scanner scanner){
        try {
            hidePrint("Введите координату местонахождения оператора Y(float)");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                return 0f;
            }
            if (!Float.isFinite(Float.parseFloat(str))){
                throw new InvalidArgumentException("Число слишкмо большое");
            }
            return Float.parseFloat(str);
        } catch (NumberFormatException ex){
            hidePrint("Невалидный формат числа");
        } catch (InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        }
        return setPersonCoordinateY(scanner);
    }

    private Integer setPersonCoordinateZ(Scanner scanner){
        try {
            hidePrint("Введите координату местонахождения оператора Z(Integer)");
            String str = scanner.nextLine().trim();
            if (str.equals("")){
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            return Integer.parseInt(str);
        } catch(NumberFormatException ex) {
            hidePrint("Невалидный формат числа");
        } catch(InvalidArgumentException ex){
            hidePrint(ex.getMessage());
        }
        return setPersonCoordinateZ(scanner);
    }

    private String setPersonLocationName(Scanner scanner){
        try {
            hidePrint("Введите имя локации местонахождения оператора(String)\nОграничения:" +
                    "Длина строки не должна быть больше 870");
            String str = scanner.nextLine().trim();
            if (str.equals("")){ // проверка на null не нужна, она необходима в случае, когда мы читаем хранилище фильмов (FileManager)
                throw new InvalidArgumentException("Это обязательное поле. Пустым быть не может");
            }
            if (str.length() > 870){
                throw new InvalidArgumentException("Строка слишком длинная");
            }
            return str;
        } catch(InvalidArgumentException ex) {
            hidePrint(ex.getMessage());
        }
        return setPersonLocationName(scanner);
    }

    private Location setLocation(Scanner scanner){
        hidePrint("Ввод данных о местонахождении оператора...");
        return new Location(setPersonCoordinateX(scanner), setPersonCoordinateY(scanner), setPersonCoordinateZ(scanner), setPersonLocationName(scanner));
    }

    private void setPerson(Scanner scanner, Movie movie){
        hidePrint("Ввод данных об операторе...");
        movie.setOperator(new Person(setPersonName(scanner), setPersonHeight(scanner), setPersonEyeColor(scanner), setPersonCountry(scanner), setLocation(scanner)));
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
        return null;
    }

    public boolean checkMoviesAtStart(Deque<Movie> movies) throws NoSuchElementException{
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
            System.out.println("Нажмите 'y' для дальнейшей работы с пустой коллекцией или любой другой символ для " +
                    "прерывания программы");
            if(new Scanner(System.in).nextLine().equals("y")){
                System.out.println("Отлично! Коллекция фильмов пуста");
                return false;
            } else{
                System.exit(0);
            }
        }
        return false;
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