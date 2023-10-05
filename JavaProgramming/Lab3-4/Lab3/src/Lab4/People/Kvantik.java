package Lab4.People;

import Lab4.Actions.LeisureActivitys;

public class Kvantik extends Human {
    public Kvantik(String name, int hp, int hunger, int fatigue, int intellect, int mood){
        super.name = name;
        super.hp = hp;
        super.intellect = intellect;
        super.hunger = hunger;
        super.fatigue = fatigue;
        super.mood = mood;
    }

    @Override
    public void dosomething() {
        if (Math.random() > 0.5) {
            LeisureActivitys.watchTV(this);
        }
        if (Math.random() > 0.5) {
            LeisureActivitys.readbook(this);
        }
        if (Math.random() > 0.985) {
            LeisureActivitys.programming(this);
        }
    }
}
