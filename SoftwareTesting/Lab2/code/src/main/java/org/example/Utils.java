package org.example.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final int N_SIZE = 100;
    public static final int PRECISION = 100;
    public static final double DELTA = 0.0001;
    public static final int[] logBases = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static final List<BigDecimal> factorials = factorial();
    public static MathContext mc = new MathContext(PRECISION, RoundingMode.HALF_UP);

    private static List<BigDecimal> factorial(){
        List<BigDecimal> factorial = new ArrayList<>(2 * N_SIZE + 2);
        factorial.add(BigDecimal.valueOf(-1));
        factorial.add(BigDecimal.ONE);
        for(int i = 2; i < 2 * N_SIZE + 2; i++){
            factorial.add(BigDecimal.valueOf(i).multiply(factorial.get(i - 1)));
        }
        return factorial;
    }

}
