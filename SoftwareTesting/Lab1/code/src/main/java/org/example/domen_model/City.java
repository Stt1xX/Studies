package org.example.domen_model;

import lombok.Getter;

@Getter
public class City extends ActivityObject{

    private final int population;
    private final int gdp;

    protected City(int population, int gdp) {
        super(ActivityType.CREATING);
        this.population = population;
        this.gdp = gdp;
    }

    @Override
    protected double computeIntelligencePoints() {
        return (double) gdp / population * 1000;
    }
}
