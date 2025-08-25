package org.example;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static final int N_SIZE = 200;
    public static final int PRECISION = 1000;
    public static final double DELTA = 0.001;
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

    public static void printTable(Computable module, String filename, double left, double right, double step) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("csvs/" + filename + ".csv"))) {
            for (double x = left; x <= right; x += step) {
                try {
                    double val = Math.round(module.compute(x).doubleValue() * 100000000) / 100000000f;
                    writer.write(x + " : " + val);
                } catch (ArithmeticException e) {
                    writer.write(x + " : " + "null");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public static Map<Double, Double> parseTable(String fieldName){
        double x = 0, y;
        Map<Double, Double> testCases = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "csvs/" + fieldName.substring(0, fieldName.length() - 4) + ".csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                try {
                    x = Double.parseDouble(parts[0].trim());
                    y = Double.parseDouble(parts[1].trim());
                    testCases.put(x, y);
                } catch (NumberFormatException ex){
                    testCases.put(x, null);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return testCases;
    }

}
