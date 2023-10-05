package Lab4.Food;

public enum Food {
    СHOCOLATEBAR(6, "Шоколадку"), MILK(3, "Молоко"), CEREAL(7, "Кашу"),
    BEER(1, "Пивасик"), MEAT(9, "Мясцо"), VAGETABLES(4, "Овощи"), JUICE(3,
            "Сочидзе"), SHAVERMA(10, "Шаву от шера");
    private final int value;
    private final String translate;

    Food(int value, String translate){
        this.value = value;
        this.translate = translate;
    }

    public int getValue(){
        return this.value;
    }

    public String getTranslate(){
        return this.translate;
    }
}
