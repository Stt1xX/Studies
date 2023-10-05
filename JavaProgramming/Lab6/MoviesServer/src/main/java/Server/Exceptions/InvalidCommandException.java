package Server.Exceptions;

/**
 * It's related to the command writing errors
 */
public class InvalidCommandException extends Exception{
    public InvalidCommandException(String message){
        super(message);
    }
}
