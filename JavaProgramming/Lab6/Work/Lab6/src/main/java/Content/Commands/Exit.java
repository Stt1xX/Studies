package Content.Commands;


public class Exit implements Command {
    String description = "exit : завершить программу (без сохранения в файл)";

    public void execute(String arg) {
        System.out.println("Работа программы завершена.");
        System.exit(0);
    }


    public String getDescription() {
        return description;
    }

}
