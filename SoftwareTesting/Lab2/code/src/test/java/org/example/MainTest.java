package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.example.Utils.DELTA;
import static org.junit.jupiter.api.Assertions.*;

public class TrigonometricTest {

    private static Sin sin;
    private static Cos cos;
    private static Cot cot;
    private static Sec sec;
    private static Csc csc;

    private static Ln ln;
    private static Log ln2, ln5, ln10;

    @BeforeAll
    static void setUp() {
        sin = new Sin();
        cos = new Cos(sin);
        csc = new Csc(sin);
        sec = new Sec(cos);
        cot = new Cot(sin, cos);

        ln = new Ln();
        ln2 = new Log(2, ln);
        ln5 = new Log(5, ln);
        ln10 = new Log(10, ln);

    }

    @Test
    void testSin() {
        assertEquals(0, sin.compute(0).doubleValue(), DELTA);
        assertEquals(1, sin.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(0, sin.compute(PI).doubleValue(), DELTA);
    }

    @Test
    void testCos() {
        assertEquals(1, cos.compute(0).doubleValue(), DELTA);
        assertEquals(0, cos.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(-1, cos.compute(PI).doubleValue(), DELTA);
    }

    @Test
    void testLn() {
        assertThrows(ArithmeticException.class, () -> ln.compute(0));
        assertEquals(0, ln.compute(1).doubleValue(), DELTA);
        assertEquals(1, ln.compute(Math.E).doubleValue(), DELTA);
    }

    @Test
    void testCot() {
        assertEquals(1, cot.compute(PI / 4).doubleValue(), DELTA);
        assertEquals(0, cot.compute(PI / 2).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> cot.compute(0));
    }

    @Test
    void testSec() {
        assertEquals(-1, sec.compute(-PI).doubleValue(), DELTA);
        assertEquals(Math.sqrt(2), sec.compute(PI / 4).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> sec.compute(PI / 2));
    }

    @Test
    void testCsc() {
        assertEquals(1, csc.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(Math.sqrt(2), csc.compute(PI / 4).doubleValue(), DELTA);
        assertEquals(-1, csc.compute(-PI / 2).doubleValue(), DELTA);
        assertEquals(1, csc.compute(-3 * PI / 2).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> csc.compute(0));
    }

    @Test
    void testLog() {
        assertThrows(ArithmeticException.class, () -> ln2.compute(0));
        assertEquals(0, ln2.compute(1).doubleValue(), DELTA);
        assertEquals(1, ln2.compute(2).doubleValue(), DELTA);
        assertEquals(5, ln2.compute(32).doubleValue(), DELTA);

        assertThrows(ArithmeticException.class, () -> ln5.compute(0));
        assertEquals(0, ln5.compute(1).doubleValue(), DELTA);
        assertEquals(3, ln5.compute(125).doubleValue(), DELTA);

        assertThrows(ArithmeticException.class, () -> ln10.compute(0));
        assertEquals(0, ln10.compute(1).doubleValue(), DELTA);
        assertEquals(2, ln10.compute(100).doubleValue(), DELTA);
    }




}
