package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import static java.lang.Math.PI;
import static org.example.Utils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MainTest {

    private static Sin sin;
    private static Cos cos;
    private static Cot cot;
    private static Sec sec;
    private static Csc csc;
    private static Ln ln;
    private static Log log2, log5, log10;
    private static SysFunc mainFunc;

    private final static Sin sinMock = mock(Sin.class);
    private final static Cos cosMock = mock(Cos.class);
    private final static Cot cotMock = mock(Cot.class);
    private final static Sec secMock = mock(Sec.class);
    private final static Csc cscMock = mock(Csc.class);
    private final static Ln lnMock = mock(Ln.class);
    private final static Log log2Mock = mock(Log.class), log5Mock = mock(Log.class), log10Mock = mock(Log.class);
    private final static SysFunc mainFuncMock = mock(SysFunc.class);

    @BeforeAll
    static void setUp() throws IllegalAccessException, NoSuchFieldException {

        final double LEFT = -10, RIGHT = 10, STEP = 0.5;

        sin = new Sin();
        cos = new Cos(sin);
        csc = new Csc(sin);
        sec = new Sec(cos);
        cot = new Cot(sin, cos);

        ln = new Ln();
        log2 = new Log(2, ln);
        log5 = new Log(5, ln);
        log10 = new Log(10, ln);

        mainFunc = new SysFunc(sin, cos, csc, sec, cot, ln, log2, log5, log10);

        Field[] fields = MainTest.class.getDeclaredFields();
        for(Field field : fields) {
            if (field.getName().endsWith("Mock"))
                continue;
            field.setAccessible(true);
            printTable((Computable) field.get(null), field.getName(), LEFT, RIGHT, STEP);
        }

        for (Field field : MainTest.class.getDeclaredFields()) {
            if (field.getName().endsWith("Mock")){
                Map<Double, Double> testCases = parseTable(field.getName());
                field.setAccessible(true);
                for (Map.Entry<Double, Double> entry : testCases.entrySet()) {
                    if (entry.getValue() == null)
                        when(((Computable) (field.get(null))).compute(entry.getKey())).thenThrow(new ArithmeticException());
                    else
                        when(((Computable) (field.get(null))).compute(entry.getKey())).thenReturn(BigDecimal.valueOf(entry.getValue()));
                }
            }
        }
    }

    @Test
    void testSin() {
        assertEquals(0, sin.compute(0).doubleValue(), DELTA);
        assertEquals(1, sin.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(0, sin.compute(PI).doubleValue(), DELTA);

        assertEquals(sin.compute(PI / 2).doubleValue(), sin.compute(PI / 2 + 2 * PI).doubleValue(), DELTA);
        assertEquals(sin.compute(PI).doubleValue(), sin.compute(PI - 2 * PI).doubleValue(), DELTA);
    }

    @Test
    void testCos() {
        assertEquals(1, cos.compute(0).doubleValue(), DELTA);
        assertEquals(0, cos.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(-1, cos.compute(PI).doubleValue(), DELTA);

        assertEquals(cos.compute(0).doubleValue(), cos.compute(2 * PI).doubleValue(), DELTA);
        assertEquals(cos.compute(PI / 2).doubleValue(), cos.compute(PI / 2 - 2 * PI).doubleValue(), DELTA);
    }

    @Test
    void testCot() {
        assertEquals(1, cot.compute(PI / 4).doubleValue(), DELTA);
        assertEquals(0, cot.compute(PI / 2).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> cot.compute(0));

        assertEquals(cot.compute(PI / 4).doubleValue(), cot.compute(PI / 4 + PI).doubleValue(), DELTA);
    }

    @Test
    void testSec() {
        assertEquals(-1, sec.compute(-PI).doubleValue(), DELTA);
        assertEquals(Math.sqrt(2), sec.compute(PI / 4).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> sec.compute(PI / 2));

        assertEquals(sec.compute(PI / 4).doubleValue(), sec.compute(PI / 4 + 2 * PI).doubleValue(), DELTA);
    }

    @Test
    void testCsc() {
        assertEquals(1, csc.compute(PI / 2).doubleValue(), DELTA);
        assertEquals(Math.sqrt(2), csc.compute(PI / 4).doubleValue(), DELTA);
        assertEquals(-1, csc.compute(-PI / 2).doubleValue(), DELTA);
        assertEquals(1, csc.compute(-3 * PI / 2).doubleValue(), DELTA);
        assertThrows(ArithmeticException.class, () -> csc.compute(0));

        assertEquals(csc.compute(PI / 2).doubleValue(), csc.compute(PI / 2 + 2 * PI).doubleValue(), DELTA);
    }


    @Test
    void testLn() {
        assertThrows(ArithmeticException.class, () -> ln.compute(0));
        assertEquals(0, ln.compute(1).doubleValue(), DELTA);
        assertEquals(1, ln.compute(Math.E).doubleValue(), DELTA);
    }

    @Test
    void testLog() {
        assertThrows(ArithmeticException.class, () -> log2.compute(0));
        assertEquals(0, log2.compute(1).doubleValue(), DELTA);
        assertEquals(1, log2.compute(2).doubleValue(), DELTA);
        assertEquals(5, log2.compute(32).doubleValue(), DELTA);

        assertThrows(ArithmeticException.class, () -> log5.compute(0));
        assertEquals(0, log5.compute(1).doubleValue(), DELTA);
        assertEquals(3, log5.compute(125).doubleValue(), DELTA);

        assertThrows(ArithmeticException.class, () -> log10.compute(0));
        assertEquals(0, log10.compute(1).doubleValue(), DELTA);
        assertEquals(2, log10.compute(100).doubleValue(), DELTA);
    }

    @Test
    void testTrigonometricPart() {
        assertThrows(ArithmeticException.class, () -> mainFunc.compute(0)); // csc cot
        assertThrows(ArithmeticException.class, () -> mainFunc.compute(-3 * PI / 2)); // sec

        final double PERIOD = 2 * PI;

        // check period
        assertEquals(mainFunc.compute(-12 - PERIOD + 0.5).doubleValue(), mainFunc.compute(-12 - 3 * PERIOD + 0.5).doubleValue(), DELTA);
        assertEquals(mainFunc.compute(-12 + PERIOD).doubleValue(), mainFunc.compute(-12 - 3 * PERIOD).doubleValue(), DELTA);

        // check random point
        assertEquals(17.6397, mainFunc.compute(-12).doubleValue(), DELTA);
        assertEquals(17.6397, mainFunc.compute(-12 - PERIOD).doubleValue(), DELTA);
        assertEquals(-0.71529, mainFunc.compute(-15).doubleValue(), DELTA);
        assertEquals(-0.71529, mainFunc.compute(-15 - PERIOD).doubleValue(), DELTA);

        // check vertices
        assertEquals(-0.78958, mainFunc.compute(-2.48636).doubleValue(), DELTA);
        assertEquals(-0.78958, mainFunc.compute(-2.48636 - PERIOD).doubleValue(), DELTA);
    }

    @Test
    void testFullLogPart() {
        assertEquals(0, mainFunc.compute(1).doubleValue(), DELTA);

        // rand points
        assertEquals(17.58713, mainFunc.compute(10).doubleValue(), DELTA);
        assertEquals(1.61768, mainFunc.compute(3).doubleValue(), DELTA);

        assertEquals(-1.00097, mainFunc.compute(0.5).doubleValue(), DELTA);
        assertEquals(-0.152, mainFunc.compute(0.9).doubleValue(), DELTA);
    }


    // integration tests
    @Test
    void fullMock(){
        SysFunc func = new SysFunc(sinMock, cosMock, cscMock, secMock, cotMock, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void sinDone(){
        SysFunc func = new SysFunc(sin, cosMock, cscMock, secMock, cotMock, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void cosDone(){
        SysFunc func = new SysFunc(sin, cos, cscMock, secMock, cotMock, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void cscDone(){
        SysFunc func = new SysFunc(sin, cos, csc, secMock, cotMock, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void secDone(){
        SysFunc func = new SysFunc(sin, cos, csc, sec, cotMock, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void fullTrigonometricDone(){
        SysFunc func = new SysFunc(sin, cos, csc, sec, cot, lnMock, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void lnDone(){
        SysFunc func = new SysFunc(sin, cos, csc, sec, cot, ln, log2Mock, log5Mock, log10Mock);
        integrationCheck(func);
    }

    @Test
    void fullSystemDone(){
        SysFunc func = new SysFunc(sin, cos, csc, sec, cot, ln, log2, log5, log10);
        integrationCheck(func);
    }


    private static void integrationCheck(SysFunc func) {
        Map<Double, Double> map = parseTable("mainFuncMock");
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            if (entry.getValue() == null)
                assertThrows(ArithmeticException.class, () -> func.compute(entry.getKey()));
            else
                assertEquals(entry.getValue(), func.compute(entry.getKey()).doubleValue(), DELTA);
        }
    }

}
