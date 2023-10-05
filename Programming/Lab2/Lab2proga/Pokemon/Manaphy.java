package Lab2proga.Pokemon;

import Lab2proga.Moves.*;

import ru.ifmo.se.pokemon.*;


public class Manaphy extends Pokemon {

    public Manaphy(String name, int level){
        super(name, level);
        setType(Type.WATER);
        setStats(100, 100,100,100,100,100);
        setMove(new WaterPulse(), new ShadowBall(), new Rest(), new AquaRing());
    }

}
