package Lab4.Time;

import Lab4.Exception.AllDieException;
import Lab4.People.Human;
import Lab4.People.Neznayka;
import Lab4.People.Znayka;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Time {
    private int day = 0;
    public static ArrayList<Human> Humans;

    public void MainTimer(Human neznayka, Human znayka, Human kantik, Human kvantik, Human alpha,
                          Human momega, Znayka.Encyclopedia encyclopedia,
                          Neznayka.Glue glue) throws InterruptedException {

        for (int i = 1; i < 101; i++) {
            TimeUnit.SECONDS.sleep(6);
            day = i;
            System.out.println("\n----------День " + day + "----------\n");
            for (int j = 0; j < Humans.size(); j++) {
                Human c = Humans.get(j);
                if (c.die()) {
                    c.setHp(0);
                    c.setFatigue(0);
                    c.setIntellect(0);
                    c.setHunger(0);
                    c.setMood(0);
                    Humans.remove(c);
                } else {
                    c.update();
                    c.eat();
                    c.sleep();
                    c.dosomething();
                    c.checkingBoost();
                }
                if (c.equals(znayka) && Math.random() > 0.7) {
                    c.setIntellect(c.getIntellect() + encyclopedia.PlusIntellect());
                    c.setMood(c.getMood() + encyclopedia.PlusMood());
                    System.out.println("(ОСОБОЕ СОБЫТИЕ) Знайка опять читает энциклопедию");
                }
                if (c.equals(neznayka) && Math.random() > 0.7) {
                    c.setIntellect(c.getIntellect() + glue.PlusIntellect());
                    c.setMood(c.getMood() + glue.PlusMood());
                    System.out.println("(ОСОБОЕ СОБЫТИЕ) Незнайка сегодня обнюхался клеем");
                }

            }
            kantik.getspecifications();
            kvantik.getspecifications();
            alpha.getspecifications();
            momega.getspecifications();
            znayka.getspecifications();
            neznayka.getspecifications();
            if (Humans.size() == 0) {
                try {
                    throw new AllDieException("Все мертвы, игра прерывается!");
                } catch (AllDieException e) {
                    System.exit(0);
                }
            }
            Human.setDay(this.day);
        }
    }
}
