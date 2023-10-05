package Lab2proga.Moves;

import Lab2proga.Pokemon.*;

import ru.ifmo.se.pokemon.*;

public class GrassWhistle extends StatusMove {

    public GrassWhistle() {
        super(Type.GRASS, 0, 55);
    }

    @Override
    protected String describe() {
        return "свистит в свой устрашающий цветочек-свисток";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect.sleep(pokemon);
    }
}
