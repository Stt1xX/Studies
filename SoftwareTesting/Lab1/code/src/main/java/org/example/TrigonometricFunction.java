package org.example;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TrigonometricFunction {

        public static BigDecimal binomialCoefficient(int n, int k) {
        if (k < 0 || k > n) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ONE;
        for (int i = 1; i <= k; i++) {
            result = result.multiply(BigDecimal.valueOf(n - k + i))
                    .divide(BigDecimal.valueOf(i), RoundingMode.HALF_UP);
        }
        return result;
    }

    public static List<BigDecimal> computeEulerNumbers(int n) {
        List<BigDecimal> eulerNumbers = new ArrayList<>();
        eulerNumbers.add(BigDecimal.ONE);
        for (int m = 2; m / 2 <= n; m += 2) {
            BigDecimal sum = BigDecimal.ZERO;

            for (int k = 0; k < m; k += 2) {
                BigDecimal binom = binomialCoefficient(m, k);
                BigDecimal ek = eulerNumbers.get(k / 2);
                sum = sum.subtract(binom.multiply(ek));
            }
            eulerNumbers.add(sum);
        }

        return eulerNumbers;
    }

    public static BigDecimal sec(BigDecimal x) {

        if (x.abs().compareTo(new BigDecimal("1.5708")) >= 0) {
            throw new IllegalArgumentException("|x| must be less than π/2 ≈ 1.5708");
        }

        final int PRECISION = 1000;
        final int N_SIZE = 200;
        final List<BigDecimal> EULER_NUMBERS = computeEulerNumbers(N_SIZE);

        MathContext mc = new MathContext(PRECISION, RoundingMode.HALF_UP);
        BigDecimal sum = BigDecimal.ONE;
        BigDecimal term;
        BigDecimal xSquared = x.pow(2, mc);
        BigDecimal power = xSquared;

        BigDecimal factorial = BigDecimal.valueOf(2);

        for (int n = 1; n <= N_SIZE - 1; n++) {
            term = EULER_NUMBERS.get(n).abs().multiply(power, mc)
                    .divide(factorial, mc);
            sum = sum.add(term, mc);

            power = power.multiply(xSquared, mc);

            factorial = factorial.multiply(BigDecimal.valueOf((2*n+1)*(2*n+2)), mc);
        }

        return sum.round(mc);
    }

}
