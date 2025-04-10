package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

public class FirstTaskTest {

    final double DELTA = 0.0001;

    @MethodSource("binomialCoefficientsInput")
    @ParameterizedTest
    public void testBinCoef(int n, int k, int expected) {
        assertEquals(BigDecimal.valueOf(expected), TrigonometricFunction.binomialCoefficient(n, k));
    }

    @Test
    public void testEulerSeq(){
        String[] eulerNumbers = {
                "1",
                "-1",
                "5",
                "-61",
                "1385",
                "-50521",
                "2702765",
                "-199360981",
                "19391512145",
                "-2404879675441",
                "370371188237525",
                "-69348874393137901",
                "15514534163557086905",
                "-4087072509293123892361",
                "1252259641403629865468285"
        };
        assertEquals(Stream.of(eulerNumbers).map(BigDecimal::new).toList(), TrigonometricFunction.computeEulerNumbers(14));
    }

    @MethodSource("secansInput")
    @ParameterizedTest
    public void sec(double x, double expected) {
        assertEquals(expected, TrigonometricFunction.sec(BigDecimal.valueOf(x)).doubleValue(), DELTA);
    }

    @Test
    public void secCheckParity(){
        assertEquals(TrigonometricFunction.sec(BigDecimal.valueOf(-1)).doubleValue(), TrigonometricFunction.sec(BigDecimal.valueOf(1)).doubleValue());
    }

    @Test
    public void secBadResult(){
        assertThrows(IllegalArgumentException.class, () -> TrigonometricFunction.sec(BigDecimal.valueOf(PI / 2 + 0.0001)));
        assertThrows(IllegalArgumentException.class, () -> TrigonometricFunction.sec(BigDecimal.valueOf(-PI / 2 - 0.0001)));
    }

    public static Stream<Arguments> binomialCoefficientsInput(){
        return Stream.of(
                Arguments.of(4, 1, 4),
                Arguments.of(4, 2, 6),
                Arguments.of(100, 100, 1),
                Arguments.of(1000, 0, 1),
                Arguments.of(1, -2, 0),
                Arguments.of(1, 1000, 0)
        );
    }

    public static Stream<Arguments> secansInput(){
        return Stream.of(
            Arguments.of(0, 1),
            Arguments.of(1.1, 2.2046),
            Arguments.of(0.5, 1.13949),
            Arguments.of(0.2345, 1.02814),
            Arguments.of(-1.1, 2.2046),
            Arguments.of(-1.5, 14.13683),
            Arguments.of(-0.1, 1.00502)
        );
    }
}