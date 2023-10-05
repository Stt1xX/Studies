import ru.ifmo.se.pokemon.*;

public class ShadowBall extends SpecialMove {

    public ShadowBall() {
        super(Type.GHOST, 80, 100);
    }

    @Override
    protected String describe() {
        return "понижает защиту противника на одну ступень";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect e = new Effect().chance(0.2).stat(Stat.SPECIAL_DEFENSE, -1);
        pokemon.addEffect(e);
    }
}