package Lab2proga.Pokemon;

import ru.ifmo.se.pokemon.*;

import Lab2proga.Moves.*;

public class Wimpod extends Pokemon {

    public Wimpod(String name, int level){
        super(name, level);
        setType(Type.WATER, Type.BUG);
        setStats(25, 35,40,20,30,80);
        setMove(new Rest(), new Confide(), new Swagger());
    }
}
