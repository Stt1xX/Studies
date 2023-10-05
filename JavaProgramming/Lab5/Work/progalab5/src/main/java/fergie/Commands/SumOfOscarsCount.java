package fergie.Commands;


import fergie.CollectionManager;

public class SumOfOscarsCount extends CollectionCommand implements Command {
    String description = "sum_of_oscars_count : вывести сумму значений поля oscarsCount для всех элементов коллекции";

    public SumOfOscarsCount(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public void execute(String arg) {
        System.out.println("Общая сумма оскаров: " + collectionManager.sumOfOscarsCount());
    }


    public String getDescription() {
        return description;
    }
}
