import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {

    public Rest(){super(Type.PSYCHIC, 0, -1);}

    @Override
    protected String describe() {
        return "отправляется в спячку, чтобы исцелить себя";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect e = new Effect().condition(Status.SLEEP).attack(0.0).turns(2);
        pokemon.setMod(Stat.HP, (int) -pokemon.getStat(Stat.HP));
        pokemon.addEffect(e);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }
}
