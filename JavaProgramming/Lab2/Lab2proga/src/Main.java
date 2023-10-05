import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {

        Battle b = new Battle();
        Pokemon golisopod = new Golisopod("Боб", 1);
        Pokemon wimpod = new Wimpod("Пит", 1);
        Pokemon sewaddle = new Sewaddle("Тарас", 15);
        Pokemon manaphy = new Manaphy("Тристан", 54);
        Pokemon leavanny = new Leavanny("Николя", 39);
        Pokemon swadloon = new Swadloon("Бодян", 1);

        b.addAlly(golisopod);
        b.addAlly(sewaddle);
        b.addAlly(leavanny);
        b.addAlly(swadloon);
        b.addAlly(wimpod);
        b.addFoe(manaphy);
        b.go();
        System.out.println();
    }
}