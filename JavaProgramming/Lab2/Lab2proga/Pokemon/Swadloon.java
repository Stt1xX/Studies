package Lab2proga.Pokemon;

import Lab2proga.Moves.*;

import ru.ifmo.se.pokemon.*;

public class Swadloon extends Sewaddle {
    public Swadloon(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.GRASS);
        setStats(55, 63, 90, 50, 80, 42);
        setMove(new RazorLeaf(), new Tackle());
        addMove(new GrassWhistle());
    }

}
