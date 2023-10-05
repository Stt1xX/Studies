package Content.Commands;

/**
 *Interface for commands
 * @author FergieDoigrales
 * @version 0.1
 */
public interface Command {
    void execute(String arg);
    String getDescription();

}
