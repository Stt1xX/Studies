package org.example.domen_model;

import lombok.Getter;

@Getter
public class Wave extends ActivityObject {

    private final int power;

    protected Wave(int power) {
        super(ActivityType.CHILLING);
        this.power = power;
    }

    @Override
    protected double computeIntelligencePoints() {
        return power * 20;
    }
}
