package fergie.Commands;

import fergie.CollectionManager;

import java.util.Scanner;
/**
 * Abstract class for commands that require access to user input (scanner)
 * and collection
 * @author FergieDoigrales
 * @version 0.1
 */
public abstract class InputCommand extends CollectionCommand {
    protected Scanner scanner;

    public InputCommand(CollectionManager collectionManager, Scanner scanner) {
        super(collectionManager);
        this.scanner = scanner;
    }
}
