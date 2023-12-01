package com.example;

public class Attempt {
    private final int x;
    private final double y;
    private final double r;
    private final String isHit;
    private final String time;

    public Attempt(int x, double y, double r, String isHit, String time) {
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

}
