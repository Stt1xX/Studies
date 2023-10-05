package Lab4.People;

import Lab4.Actions.Death;
import Lab4.Actions.LeisureActivity;
import Lab4.Food.Food;

public abstract class Human extends GameObject implements LeisureActivity, Death {
    protected int fatigue, mood, boost = 0;

    public static int day;

    public static void setDay(int day) {
        Human.day = day;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    protected String name;


    public void sleep() {
        this.fatigue -= 5;
        if (this.fatigue < 0){
            this.fatigue = 0;
        }
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getMood() {
        return mood;
    }

    public String getName() {
        return name;
    }

    public void eat() {
        int x = (int)(Math.random()*((8)));
        this.hunger -= Food.values()[x].getValue();
        if (this.hunger < 0){
            this.hunger = 0;
        }
        System.out.println("Сегодня " + this.name + " впитал в себя " + Food.values()[x].getTranslate());

        if (x == 0 && this.boost == 0){
            System.out.println("ЧТО ЖЕ ЭТО??");
            System.out.printf("КАЖЕТСЯ, ПЛУТЕНЫШ %s ВКУСИЛ ЗАВЕТНЫЙ БОТОНЧИК БОДРОСТИ!%n", this.name);
            System.out.println("БЛИЖАЙШИЕ ДВА ДНЯ ЭТОТ МАСТАДОНТ БУДЕТ РАЗНОСТЬ ВСЕ ЧТО ВИДИТ!");
            this.intellect += 50;
            this.hp += 100;
            this.mood += 30;
            this.boost = day;
        }
    }

    public void getspecifications() {
        System.out.println("~~~~~~" + name + "~~~~~~\n" + "Утомляемость: " + this.fatigue + "\nГолод: "
                + this.hunger + "\nЗдоровье: " + this.hp +  "\nИнтеллект: " + this.intellect +
                "\nНастроение: " + this.mood);

    }

    public void update(){
        if (this.hp < 100){
            this.hp += 1;
        }
        this.hunger +=5;
        this.intellect += 1;
    }


    public void checkingBoost(){
        if (this.boost == day - 2 && this.boost != 0){
            this.hp -= 100;
            this.intellect -= 50;
            this.mood -= 30;
            this.boost = 0;
        }
    }
    @Override
    public boolean die() {
        if (this.fatigue > 20) {
            System.out.printf("Cегодня %s заработался до отказа %n", this.name);
            return true;
        }
        else if(this.hunger > 20){
            System.out.printf("Сегодня %s съелся%n", this.name);
            return true;
        }
        else if(this.mood < 5) {
            System.out.println("Сегодня " + this.name + " совершил суецид");
            return true;
        }
        else if(this.hp == 0){
            System.out.println("Cегодня " + this.name + " был причепыжен");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        Human human = (Human) obj;
        return this.name == human.name;
    }

}
