package org.example;

import java.math.BigDecimal;

import static org.example.Utils.*;
import static org.example.Utils.mc;

public class Ln implements Computable {

    @Override
    public BigDecimal compute(double x) {
        if (x <= 0) {
            throw new ArithmeticException("В данной точке функция ln(x) не определена");
        }
        double top = x - 1, bot = x + 1;
        BigDecimal result = BigDecimal.valueOf(0), term;
        for (int n = 0; n < N_SIZE; n++) {
            term = BigDecimal.valueOf(top)
                    .divide(
                    BigDecimal.valueOf(bot),
                    mc)
                    .pow(2 * n + 1, mc)
                    .divide(BigDecimal.valueOf(2 * n + 1), mc);
            result = result.add(term);
        }
        return result.multiply(BigDecimal.TWO, mc).round(mc);
    }
}

class Log implements Computable {

    private final int base;
    private final Ln ln;
    public Log(int base, Ln ln) {
        this.base = base;
        this.ln = ln;
        boolean flag = false;
        for (int b : logBases) {
            if (b == this.base) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new ArithmeticException("Я не могу создать такой логарифм");
        }
    }

    @Override
    public BigDecimal compute(double x) {
        return ln.compute(x).divide(ln.compute(base), mc);
    }
}