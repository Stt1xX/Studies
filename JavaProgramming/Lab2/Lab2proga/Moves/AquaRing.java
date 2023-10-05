package Lab2proga.Moves;

import ru.ifmo.se.pokemon.*;

import Lab2proga.Pokemon.*;

public class AquaRing extends StatusMove {

    public AquaRing(){super(Type.WATER, 0, -1);}

    @Override
    protected String describe() {
        return "востанавливет 1/16 hp пользователя";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.HP, (int) -pokemon.getStat(Stat.HP) / 4);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {return true;}
}
