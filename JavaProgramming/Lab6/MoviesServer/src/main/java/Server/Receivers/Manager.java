package Server.Receivers;

/**
 * first of all I want to say that I decided to separate the "Receivers" into {@link FileManager}, {@link ProgramManager},
 * {@link ReceiverStorage}, {@link CollectionManager}. I did it for encapsulation.
 *
 * <p>
 * {@link ReceiverStorage} is an unusual manager - it stores collections of all receivers,
 * contains a collection of Movies, also a collection of all program commands
 * </p>
 *
 * @author Stt1xX
 */

public abstract class Manager {
    private boolean status = false;
    private String name;

    protected Manager() {
    }

    public String getName() {
        return name;
    }

    public Manager(String name){
        this.name = name;
    }
    protected void turnOn(){
        this.status = true;
        System.out.println(writeStatus());
    }

    protected void turnOf(){
        this.status = false;
        System.out.println(writeStatus());
    }

    public String writeStatus() {
        return this.name + (this.status ? " is connected" : " is disabled");
    }

    @Override
    public String toString() {
        return name;
    }
}
