import ru.ifmo.se.pokemon.*;

public class RazorLeaf extends PhysicalMove {

    public RazorLeaf() {
        super(Type.BUG, 55, 95);
    }

    @Override
    protected String describe() {
        return "протыкает противника лезвием одуванчика";
    }

    @Override
    protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
        if (var1.getStat(Stat.SPEED) / 512.0 * 4 > Math.random()) {
            return 2.0;
        } else {
            return 1.0;
        }
    }
}
