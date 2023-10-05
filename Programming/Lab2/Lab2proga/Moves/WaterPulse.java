package Lab2proga.Moves;

import Lab2proga.Pokemon.*;

import ru.ifmo.se.pokemon.*;


public class WaterPulse extends SpecialMove {

    public WaterPulse(){
        super(Type.WATER, 60, 100);
    }

    @Override
    protected java.lang.String	describe(){

        return "контузит противника своей струйкой";

    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() <= 0.2) Effect.confuse(pokemon);

    }

}
