package Server.Receivers;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * Contains main methods such as {@link #exit()} and {@link #start(CollectionManager, FileManager)}.
 *
 * @author Stt1xX
 */

public class ProgramManager extends Manager {
    public ProgramManager(String name) {
        super(name);
    }

    public String exit(){
        System.exit(0);
        return null;
    }
}
