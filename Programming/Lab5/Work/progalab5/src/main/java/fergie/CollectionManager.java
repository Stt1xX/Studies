package fergie;

import fergie.Commands.ExecuteScript;
import fergie.Data.Movie;
import fergie.Data.MovieGenre;

import java.time.LocalDate;
import java.util.*;
/**
 * Collection management class
 * @author FergieDoigrales/Stt1xX
 * @version 0.1
 * @throws IllegalArgumentException
 */

public class CollectionManager {

    protected ArrayDeque<Movie> arrayDeque;
    private LocalDate initializationDate;

    private Long current_id = 1L;
    private long currency = 0;
    String className;


    public CollectionManager() {
        initializationDate = LocalDate.now();
        arrayDeque = new ArrayDeque<>();
        className = arrayDeque.getClass().getName();
        currency = arrayDeque.size();
    }

    public CollectionManager(FileManager fileManager) {
        this.arrayDeque = fileManager.arrayDeque;
    }

//    public void addAll(Collection<Movie> collection) {
//        for (Movie movie : collection) {
//            movie.setId(current_id);
//            arrayDeque.add(movie);
//            current_id++;
//        }
//    }

    public void addElement(Movie movie) {
        ArrayList<Long> allId = new ArrayList<>();
        for (Movie movieForCheck: arrayDeque){
            allId.add(movieForCheck.getId());
        }
        while(allId.contains(current_id)) current_id++;
        movie.setId(current_id);
        arrayDeque.add(movie);
        current_id++;
        System.out.println("Фильм успешно добавлен.");
        System.out.println("Фильмов в коллекции:" + arrayDeque.size());
    }

    public void clear() {
        this.arrayDeque.clear();
    }

    public boolean checkID(Long id) {
        for (Movie currentMovie : this.arrayDeque) {
            if (currentMovie.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void updateMovie(Movie movie) {
        List<Movie> list = new ArrayList<>(this.arrayDeque);
        for (Movie currentMovie : list) {
            if (currentMovie.getId().equals(movie.getId())) {
                list.set(list.indexOf(currentMovie), movie);
                this.arrayDeque = new ArrayDeque<>(list);
            }
        }
    }

    public void addIfMin(Movie movie) {
        ArrayList<Long> allId = new ArrayList<>();
        for (Movie movieForCheck: arrayDeque){
            allId.add(movieForCheck.getId());
        }

        while(allId.contains(current_id)) current_id++;
        if (arrayDeque.isEmpty()) {
            movie.setId(current_id);
            arrayDeque.add(movie);
            current_id++;
            System.out.println("Коллекция была пуста. Фильм успешно добавлен.");
            System.out.println("Фильмов в коллекции:" + arrayDeque.size());
            return;
        }

        Movie minMovie = Collections.min(arrayDeque);
        if (movie.compareTo(minMovie) < 0) {
            movie.setId(current_id);
            arrayDeque.add(movie);
            current_id++;
            System.out.println("Фильм успешно добавлен.");
            System.out.println("Фильмов в коллекции:" + arrayDeque.size());

        } else
            System.out.println("Выбранный фильм не будет сохранен, т.к он больше минимального.");
    }

    public void removeIfGreater(Movie movie) {
        List<Movie> moviesForDelete = new ArrayList<>();

        for (Movie m: arrayDeque) {
            if (m.compareTo(movie) > 0) {
                moviesForDelete.add(m);
            }
        }
        arrayDeque.removeAll(moviesForDelete);
    }


    public void removeHead() {
        System.out.println(this.arrayDeque.removeFirst());
    }
    public void removeById(String argument) {
        try {
            Long id = Long.parseLong(argument);
            Movie del = null;
            for (Movie currentMovie : this.arrayDeque) {
                if (currentMovie.getId() == id) {
                    del = currentMovie;
                }
            }
            if (del != null) {
                this.arrayDeque.remove(del);
                System.out.println("Элемент удален.");
            } else
                System.out.println("Такого элемента нет в коллекции.");
        } catch (NullPointerException e) {
            System.out.println("Коллекция пуста.");
        }
    }

    public int sumOfOscarsCount() {
        int oscars = 0;
        for (Movie movie : this.arrayDeque) {
            oscars += movie.getOscarsCount();
        }
        return oscars;
    }

    public Integer countGreaterThanGenre(String str){
        Integer count = 0;
        for (Movie movie : arrayDeque) {
            if (movie.getGenre().ordinal() > MovieGenre.valueOf(str).ordinal()) {
                count++;
            }
        }
        return count;
    }

    public void show() {
        System.out.println("\n");
        for (Movie movie : arrayDeque) {
            System.out.print(" " + movie.toString() + "\n");
            for (int i = 0; i < 20; i++){
                System.out.print("_");
            }
            System.out.println("\n");
        }
        System.out.println("Коллекция содержит " + arrayDeque.size() + " элементов.");
        if (arrayDeque.size() == 0)
            System.out.println("Коллекция пуста.");
    }

    private void countingByGenre(String arg) throws IllegalArgumentException {
        int count = 0;
        Movie value = null;
        MovieGenre genre = MovieGenre.valueOf(arg);
        Map<Movie, MovieGenre> movies = new HashMap<>();
        for (Movie movie : arrayDeque) {
            if (movie.getGenre().equals(genre)) {
                movies.put(movie, genre);
                count += 1;
            }
        }
        System.out.println("Количество фильмов жанра " + genre + ": " + count);
        movies.clear();
    }

    public void groupCountingByGenre() throws IllegalArgumentException {
        countingByGenre("DRAMA");
        countingByGenre("ACTION");
        countingByGenre("TRAGEDY");
    }

    public void save() {
        FileManager fileManager = new FileManager();
        fileManager.exportToFile(this);
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    public String getClassName() {
        return this.className;
    }

    public int getCurrency() {
        return arrayDeque.size();
    }


}