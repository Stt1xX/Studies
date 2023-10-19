package Server.Receivers;

import Server.ServerReceiver;
import Server.ServerResponder;
import Storage.StorageData.*;
import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class DataBaseManager extends Manager{



    Connection connection;
    private final ReceiverStorage receiverStorage;
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public DataBaseManager(ReceiverStorage receiverStorage, String name){
        this.receiverStorage = receiverStorage;
        setName(name);
    }

    public Connection DBConnecting() {
        try {
            String HostName = "/localhost:5432";
            String hostname = System.getenv("MOVIE_HOST");
            if (hostname != null) HostName = hostname;
            System.out.println("Connecting to the DB...");
            String jdbcConnectionString = "jdbc:postgresql:/" + HostName + "/studs";
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter sysadmins username:");
            String username = scanner.nextLine();
//        System.out.println("Enter sysadmins password:");
//        String password = new String(System.console().readPassword());
            Properties properties = new Properties();
            properties.setProperty("user", username);
            connection = DriverManager.getConnection(jdbcConnectionString, properties);
            System.out.println("The connection with DB was successfully");
            return connection;
            } catch (SQLException ex) {
                System.out.println("Invalid username or password. Try again");
                return DBConnecting();
            }
    }

    public void authorization(Connection connection, SocketChannel socketChannel, ServerResponder serverResponder, ServerReceiver serverReceiver) throws ClassNotFoundException, IOException, SQLException {
        String username;
        while(true) {
            username = serverReceiver.authorizationReceive(socketChannel);
            if (checkName(username, connection)) {

                serverResponder.authorizationRespond((byte) 1, socketChannel);

                if (checkPassword(serverReceiver.authorizationReceive(socketChannel), username, connection)) {
                    serverResponder.authorizationRespond((byte) 1, socketChannel);
                    break;
                } else {
                    serverResponder.authorizationRespond((byte) 0, socketChannel);
                }
            }
            else {
                serverResponder.authorizationRespond((byte) 0, socketChannel);
            }
        }
        if (bufferedReader.ready()) {
            String s = bufferedReader.readLine();
            if (s.equals("exit")) {
                receiverStorage.searchCommand("exit").execute(null, null);
            }
            else {
                System.out.println("The server has one command:\n'exit' - выход");
            }
        }
    }

    public boolean checkName(String username, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM Users WHERE name='" + username + "'");
             ResultSet rs = preparedStatement.executeQuery()) {
            return rs.next();
        }
    }

    public boolean checkPassword(String password, String username, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM Users WHERE name='" + username + "'");
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            return rs.getString("password").equals(password);
        }
    }

    public void add(Movie movie, String owner) throws SQLException{
        try (PreparedStatement insertMovie = connection.prepareStatement("INSERT INTO Movies(name, x, y, oscarscount, genre, mpaarating, owner) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insertOperator = connection.prepareStatement("INSERT INTO Operators(operatorname, nationality, eyecolor, locname, xloc, yloc, zloc, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insertMiddleTable = connection.prepareStatement("INSERT INTO Movies_Operators VALUES (?, ?)")){

            insertMovie.setString(1, movie.getName());
            insertMovie.setInt(2, movie.getCoordinates().getX());
            insertMovie.setInt(3, movie.getCoordinates().getY());
            insertMovie.setInt(4, movie.getOscarsCount());
            insertMovie.setString(5, movie.getGenre().name());
            insertMovie.setString(6, movie.getMpaaRating().name());
            insertMovie.setString(7, owner);
            if (insertMovie.executeUpdate() == 0){
                throw new SQLException("NO MOVIES ROWS ADDED");
            }
            insertOperator.setString(1, movie.getOperator().getName());
            insertOperator.setString(2, movie.getOperator().getNationality().name());
            insertOperator.setString(3, movie.getOperator().getEyeColor().name());
            insertOperator.setString(4, movie.getOperator().getLocation().getName());
            insertOperator.setFloat(5, movie.getOperator().getLocation().getX());
            insertOperator.setFloat(6, movie.getOperator().getLocation().getY());
            insertOperator.setInt(7, movie.getOperator().getLocation().getZ());
            insertOperator.setFloat(8, movie.getOperator().getHeight());
            if (insertOperator.executeUpdate() == 0){
                throw new SQLException("NO OPERATORS ROWS ADDED");
            }

            ResultSet operatorId = insertOperator.getGeneratedKeys();
            operatorId.next();
            int currentIdOperator = operatorId.getInt(1);
            ResultSet movieId = insertMovie.getGeneratedKeys();
            movieId.next();
            int currentIdMovie = movieId.getInt(1);

            movie.getOperator().setOperatorId(currentIdOperator);
            movie.setId(currentIdMovie);
            movie.setCreationDate(new Date());

            insertMiddleTable.setInt(1, currentIdMovie);
            insertMiddleTable.setInt(2, currentIdOperator);
            if (insertMiddleTable.executeUpdate() == 0){
                throw new SQLException("NO MIDDLE ROWS ADDED");
            }
        }
    }

    public String read(CollectionManager collectionManager) throws SQLException{
        int currentMovieId = 0;
        int currentOperatorId = 0;
        try(PreparedStatement movies = connection.prepareStatement("select m.*, o.* from movies as m " +
                "join movies_operators on m.id = movies_operators.id_movie " +
                "join operators as o on movies_operators.id_Operator = o.operatorid");
            ResultSet resultSet = movies.executeQuery())
        {
            collectionManager.movies.clear();
            while(resultSet.next()){
                int movieId;
                int operatorId = resultSet.getInt("operatorId");
                Movie movie = new Movie();
                movie.setId(movieId = resultSet.getInt("id"));
                movie.setName(resultSet.getString("name"));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getInt("X"));
                coordinates.setY(resultSet.getInt("Y"));
                movie.setCoordinates(coordinates);
                movie.setCreationDate(resultSet.getDate("creationDate"));
                movie.setOscarsCount(resultSet.getInt("oscarsCount"));
                movie.setGenre(MovieGenre.valueOf(resultSet.getString("genre")));
                movie.setMpaaRating(MpaaRating.valueOf(resultSet.getString("mpaarating")));
                movie.setOwner(resultSet.getString("owner"));
                Person person = new Person();
                person.setName(resultSet.getString("operatorName"));
                person.setHeight(resultSet.getFloat("height"));
                person.setEyeColor(Color.valueOf(resultSet.getString("eyeColor")));
                person.setNationality(Country.valueOf(resultSet.getString("nationality")));
                person.setOperatorId(resultSet.getInt("operatorId"));
                Location location = new Location();
                location.setName(resultSet.getString("locname"));
                location.setX(resultSet.getFloat("xloc"));
                location.setY(resultSet.getFloat("yloc"));
                location.setZ(resultSet.getInt("zloc"));
                person.setLocation(location);
                movie.setOperator(person);
                collectionManager.movies.add(movie);
                if (movieId > currentMovieId){
                    currentMovieId = movieId;
                }
                if (operatorId > currentOperatorId){
                    currentOperatorId = operatorId;
                }
            }
            setMoviesId(currentMovieId + 1);
            setOperatorsId(currentOperatorId + 1);
            return "The collection is read. There are " + collectionManager.movies.size() + " items in the collection.";
        }
    }

    public void setMoviesId(int newId) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT setval('movies_id_seq'," + newId + " , false)"))
        {
            preparedStatement.executeQuery();
        }
    }

    public void setOperatorsId(int newId) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT setval('operators_operatorid_seq'," + newId + " , false)"))
        {
            preparedStatement.executeQuery();
        }
    }

    public void remove(int movieId, int operatorId) throws SQLException{
        try(PreparedStatement deleteMiddleTable = connection.prepareStatement("DELETE FROM Movies_operators WHERE id_Movie=" + movieId);
        PreparedStatement deleteMovies = connection.prepareStatement("DELETE FROM Movies WHERE id=" + movieId);
        PreparedStatement deleteOperators = connection.prepareStatement("DELETE FROM Operators WHERE operatorid=" + operatorId))
        {
            deleteMiddleTable.executeUpdate();
            deleteMovies.executeUpdate();
            deleteOperators.executeUpdate();
        }
    }

    public void clear() throws SQLException{
        try(PreparedStatement deleteMovies = connection.prepareStatement("TRUNCATE TABLE Movies CASCADE");
        PreparedStatement deleteOperators = connection.prepareStatement("TRUNCATE TABLE Operators CASCADE");
        PreparedStatement deleteMiddleTable = connection.prepareStatement("TRUNCATE TABLE  Movies_operators CASCADE")){
            deleteMiddleTable.executeUpdate();
            deleteMovies.executeUpdate();
            deleteOperators.executeUpdate();
        }
    }
    public void update(int movieId, int operatorId, Movie movie) throws SQLException{
        try(PreparedStatement updateMovies = connection.prepareStatement("UPDATE Movies SET name = ?, x = ?, y = ?, oscarscount = ?, genre = ?, mpaarating = ? WHERE id = " + movieId);
            PreparedStatement updateOperators = connection.prepareStatement("UPDATE Operators SET operatorname = ?, height = ?, nationality = ?, eyecolor = ?, locname = ?, xloc = ?, yloc = ?, zloc = ? WHERE operatorid = " + operatorId)){
            updateMovies.setString(1, movie.getName());
            updateMovies.setInt(2, movie.getCoordinates().getX());
            updateMovies.setInt(3, movie.getCoordinates().getY());
            updateMovies.setInt(4, movie.getOscarsCount());
            updateMovies.setString(5, movie.getGenre().name());
            updateMovies.setString(6, movie.getMpaaRating().name());

            if (updateMovies.executeUpdate() == 0){
                throw new SQLException("NO MOVIES ROWS UPDATED");
            }

            updateOperators.setString(1, movie.getOperator().getName());
            updateOperators.setFloat(2, movie.getOperator().getHeight());
            updateOperators.setString(3, movie.getOperator().getNationality().name());
            updateOperators.setString(4, movie.getOperator().getEyeColor().name());
            updateOperators.setString(5, movie.getOperator().getLocation().getName());
            updateOperators.setFloat(6, movie.getOperator().getLocation().getX());
            updateOperators.setFloat(7, movie.getOperator().getLocation().getY());
            updateOperators.setInt(8, movie.getOperator().getLocation().getZ());

            if (updateOperators.executeUpdate() == 0){
                throw new SQLException("NO OPERATORS ROWS UPDATED");
            }
        }
    }
}
