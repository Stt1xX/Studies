import ru.ifmo.se.pokemon.*;

public class Leavanny extends Swadloon {

    public Leavanny(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.GRASS);
        setStats(75,103, 80, 70, 80, 92);
        setMove(new RazorLeaf(), new Tackle(), new GrassWhistle());
        addMove(new XScissor());
    }
}
