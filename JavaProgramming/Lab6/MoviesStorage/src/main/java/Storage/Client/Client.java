package Storage.Client;

import Storage.Client.Exceptions.InvalidArgumentException;
import Storage.Client.Exceptions.InvalidCommandException;
import Storage.LightAbstractCommand.LightAbstractCommand;
import Storage.StorageData.*;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private Scanner scanner;
    private boolean flagForHide = true;
    public Client(Scanner scanner){
        this.scanner = scanner;
    }

    public void printPoster(){
        System.out.println
                (" __  __            _      __  __                                   \n" +
                "|  \\/  |          (_)    |  \\/  |                                  \n" +
                "| \\  / | _____   ___  ___| \\  / | __ _ _ __   __ _  __ _  ___ _ __ \n" +
                "| |\\/| |/ _ \\ \\ / / |/ _ \\ |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\n" +
                "| |  | | (_) \\ V /| |  __/ |  | | (_| | | | | (_| | (_| |  __/ |   \n" +
                "|_|  |_|\\___/ \\_/ |_|\\___|_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \n" +
                "                                                    __/ |          \n" +
                "                                                   |___/                        \n\n");
    }


    public LightAbstractCommand getLightAbstractCommand(ClientStorage clientStorage, SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        try {
                String s = scanner.nextLine();
                String[] str = s.trim().split("\s+");
                if (str.length == 0 || s.equals("")) {
                    return null;
                }
                LightAbstractCommand command = clientStorage.searchCommand(str[0]);
                if (command == null) {
                    throw new InvalidCommandException("Неверная команда: " + str[0]);
                }
                if (str.length - 1 == command.getPrimitiveTypes().length) {
                    if (checkLocalCommands(command, str, clientStorage, socketChannel)) return null;
                    for (int i = 0; i < command.getPrimitiveTypes().length; i++) {
                        switch (command.getPrimitiveTypes()[i]) {
                            case INT -> command.addValidArguments(String.valueOf(Integer.parseInt(str[i + 1])));
                            case FLOAT -> command.addValidArguments(String.valueOf(Float.parseFloat(str[i + 1])));
                            case STRING -> command.addValidArguments(str[i + 1]);
                            case LONG, DOUBLE -> {
                            }
                        }
                    }

                } else throw new InvalidCommandException("Невалидное количество аргументов команды");

                if (command.getComplexTypes().length > 0) {
                    for (int i = 0; i < command.getComplexTypes().length; i++) {
                        switch (command.getComplexTypes()[i]) {
                            case MOVIE -> command.addValidMovie(createMovie(scanner));
                            case COORDINATES, LOCATION, PERSON -> {
                            }
                        }
                    }
                }
                return command;
        } catch (InvalidCommandException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Невалидный аргумент команды");
        }
        return null;
    }

    public void goAction(ClientStorage clientStorage, String host, int port) {
        try {
            ClientConnecter clientConnecter = new ClientConnecter();
            SocketChannel socketChannel = clientConnecter.connect(host, port);

            printPoster();

            ClientReceiver startClientReceiver = new ClientReceiver();
            try {
                System.out.println(startClientReceiver.receive(socketChannel));
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

            while(scanner.hasNextLine()) {
                LightAbstractCommand command = getLightAbstractCommand(clientStorage, socketChannel);
                if (command == null) continue;
                // сереализация и отправка
                ClientSender clientSender = new ClientSender();
                clientSender.send(command, socketChannel);
                // десереализация и получение (ответ сервера - строка)
                ClientReceiver clientReceiver = new ClientReceiver();
                System.out.println(clientReceiver.receive(socketChannel));
            }
        } catch (IOException ex){
            goAction(clientStorage, host, port);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private boolean checkLocalCommands(LightAbstractCommand command, String[] str, ClientStorage clientStorage, SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        if (command.getName().equals("exit")) {
            LocalCommands.exit();
            return true;
        }
        if (command.getName().equals("execute_script")){
            LocalCommands.execute_script(str[1], clientStorage, socketChannel);
            return true;
        }
        if (command.getName().equals("help")){
            LocalCommands.help(clientStorage);
            return true;
        }
        return false;
    }
    public Movie createMovie(Scanner scanner){
        Movie movie = new Movie();
        setName(scanner, movie);
        setCoordinates(scanner, movie);
        setOscarsCount(scanner, movie);
        setMovieGenre(scanner, movie);
        setMpaaRating(scanner, movie);
        setPerson(scanner, movie);
        return movie;
    }
    private void hidePrint(String text){
        if(flagForHide){
            System.out.println(text);
        }
    }

    public void setFlagForHide(boolean flagForHide) {
        this.flagForHide = flagForHide;
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
        try{
            hidePrint("Введите рост оператора(Float)\n" +
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
}

