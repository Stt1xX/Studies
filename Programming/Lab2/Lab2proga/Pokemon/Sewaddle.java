package Lab2proga.Pokemon;

import Lab2proga.Moves.*;

import ru.ifmo.se.pokemon.*;

public class Sewaddle extends Pokemon {
    public Sewaddle(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.GRASS);
        setStats(45,53, 70, 40, 60, 42);
        setMove(new RazorLeaf(), new Tackle());
    }
}
