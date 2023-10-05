package Product.Exceptions;

/**
 *it is related to the errors of the arguments : either their number does not match the one set when compiling the program,
 * or the arguments themselves are invalid
 *
 * @author Stt1xX
 */
public class InvalidArgumentException extends Exception{
    public InvalidArgumentException(String message){
        super(message);
    }
}
