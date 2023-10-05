package Lab4.People;

import Lab4.Actions.LeisureActivitys;

public class Neznayka extends Human {

    public Neznayka(String name, int hp, int hunger, int fatigue, int intellect, int mood){
        super.name = name;
        super.hp = hp;
        super.intellect = intellect;
        super.hunger = hunger;
        super.fatigue = fatigue;
        super.mood = mood;
    }
    @Override
    public void dosomething(){
        if (Math.random() > 0.2) {
            LeisureActivitys.watchTV(this);
        }
        if (Math.random() > 0.7) {
            LeisureActivitys.readbook(this);
        }
        if (Math.random() > 0.99) {
            LeisureActivitys.programming(this);
        }
    }

    public record Glue(int PlusIntellect, int PlusMood){

    }
}
