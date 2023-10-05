import ru.ifmo.se.pokemon.*;

public class Golisopod extends Wimpod {

    public Golisopod(String name, int level){
        super(name, level);
        setType(Type.WATER, Type.BUG);
        setStats(75, 125,140,60,90,40);
        addMove(new XScissor());
    }
}
