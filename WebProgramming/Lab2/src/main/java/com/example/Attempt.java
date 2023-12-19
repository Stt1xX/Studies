package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attempt {
    private final Integer x;
    private final Double y;
    private final Double r;
    private final String isHit;
    private final String time;

    public Attempt(Integer x, Double y, Double r, String isHit, String time) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public String getIsHit() {
        return isHit;
    }

    public String getTime() {
        return time;
    }

    public static boolean attemptIsValid(Attempt attempt){
        if (attempt.x == null || attempt.y == null || attempt.r == null || attempt.isHit == null) {
            return false;
        }
        List<Integer> xValues = new ArrayList<>(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3));
        return xValues.contains(attempt.x) && attempt.y >= -3 && attempt.y <= 3 && attempt.r >= 1 &&
                attempt.r <= 4 && (attempt.isHit.equals("Yes") || attempt.isHit.equals("No"));
    }
}
