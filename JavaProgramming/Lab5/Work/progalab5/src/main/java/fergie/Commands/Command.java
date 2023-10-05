package fergie.Commands;

import javax.management.InvalidAttributeValueException;

/**
 *Interface for commands
 * @author FergieDoigrales
 * @version 0.1
 */
public interface Command {
    void execute(String arg);
    String getDescription();

}
