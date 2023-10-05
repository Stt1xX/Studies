import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {

    public Swagger(){
        super(Type.NORMAL, 0, 85);
    }

    @Override
    protected String describe() {
        return "пытается превратить проитвника в личинку";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {

        Effect e = new Effect().stat(Stat.ATTACK, 2);
        pokemon.addEffect(e);
        Effect.confuse(pokemon);
    }
}
