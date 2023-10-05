package Lab2proga.Moves;

import Lab2proga.Pokemon.*;

import ru.ifmo.se.pokemon.*;

public class XScissor extends PhysicalMove {

    public XScissor() {
        super(Type.BUG, 80, 100);
    }

    @Override
    protected String describe() {
        return "пытается превратить противника в маленького опарыша";
    }



}
