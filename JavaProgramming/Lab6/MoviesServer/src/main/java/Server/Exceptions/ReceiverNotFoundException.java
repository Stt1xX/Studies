package Server.Exceptions;

/**
 * It's relate to inner errors with writing code when guy doesn't connect the necessary managers with command which needs in it
 */
public class ReceiverNotFoundException extends Exception{
        public ReceiverNotFoundException(String message){
            super(message);
        }
}
