package Storage.Client.Exceptions;

/**
 * It's relate to errors in the execution of the script
 */
public class InvalidScriptException extends Exception{
    public InvalidScriptException(String message){
        super(message);
    }
}
