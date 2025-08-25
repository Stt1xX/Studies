package org.example;

import java.math.BigDecimal;
import static org.example.Utils.*;

public class Sin implements Computable {

    @Override
    public BigDecimal compute(double x){
        BigDecimal result = BigDecimal.valueOf(0), term;
        for (int n = 0; n < N_SIZE; n++) {
            int prefix = n % 2 == 0 ? 1 : -1;
            term = BigDecimal.valueOf(x).pow(2 * n + 1)
                    .divide(factorials.get(2 * n + 1), mc)
                    .multiply(BigDecimal.valueOf(prefix));
            result = result.add(term);
        }
        return result.round(mc);
    }
}

class Cos implements Computable {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public BigDecimal compute(double x) {
        int m = Math.floor((Math.abs(x) - Math.PI / 2 ) / Math.PI) % 2 == 0 ? -1 : 1;
        return sin.compute(x).pow(2, mc)
                .multiply(BigDecimal.valueOf(-1), mc)
                .add(BigDecimal.valueOf(1), mc)
                .sqrt(mc)
                .multiply(BigDecimal.valueOf(m), mc);
    }
}

class Sec implements Computable {

    private final Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    @Override
    public BigDecimal compute(double x) {
        if (Math.abs(x) % (Math.PI / 2) <= DELTA && Math.floor(Math.abs(x) / (Math.PI / 2)) % 2 == 1)
            throw new ArithmeticException("В данной точке функция sec(x) не определена");
        return BigDecimal.valueOf(1)
                .divide(cos.compute(x), mc);
    }
}

class Cot implements Computable {

    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal compute(double x) {
        if (Math.abs(x) % Math.PI <= DELTA)
            throw new ArithmeticException("В данной точке функция cot(x) не определена");
        return cos.compute(x)
                .divide(sin.compute(x), mc);
    }
}

class Csc implements Computable {

    private final Sin sin;
    public Csc(Sin sin) {
        this.sin = sin;
    }

    @Override
    public BigDecimal compute(double x) {
        if (Math.abs(x) % Math.PI <= DELTA)
            throw new ArithmeticException("В данной точке функция csc(x) не определена");
        return BigDecimal.valueOf(1)
                .divide(sin.compute(x), mc);
    }
}
