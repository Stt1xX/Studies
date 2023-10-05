package Product.Exceptions;

/**
 * It's relate to errors in the invalid storage of Movies
 */
public class InvalidStorageException extends Exception{
    public InvalidStorageException(String message){
        super(message);
    }
}
