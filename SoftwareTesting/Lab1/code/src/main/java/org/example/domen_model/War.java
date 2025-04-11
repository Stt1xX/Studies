package org.example.domen_model;

import lombok.Getter;

@Getter
public class War extends ActivityObject{

    private final WarType size;
    private final double time; // in years

    protected War(WarType size, double time) {
        super(ActivityType.CREATING);
        this.size = size;
        this.time = time;
    }

    @Override
    protected double computeIntelligencePoints() {
        return size.getIntelligencePoints() + time * 100;
    }
}
