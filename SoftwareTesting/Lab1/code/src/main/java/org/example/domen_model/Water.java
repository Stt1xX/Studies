package org.example.domen_model;

import lombok.Getter;

@Getter
public class Water extends ActivityObject {

    private final int temperature;
    private final WaterType waterType;


    protected Water(int temperature, WaterType waterType) {
        super(ActivityType.SPLASHING);
        this.temperature = temperature;
        this.waterType = waterType;
    }

    @Override
    protected double computeIntelligencePoints() {
        return temperature * 3;
    }
}
