package org.example.domen_model;

import lombok.Getter;

@Getter
public class Wheel extends ActivityObject{

    private final int radius;
    private final int thickness;

    protected Wheel(int radius, int thickness) {
        super(ActivityType.CREATING);
        this.radius = radius;
        this.thickness = thickness;
    }

    @Override
    protected double computeIntelligencePoints() {
        return Math.max(this.radius, 10) + Math.max(this.thickness, 3);
    }
}
