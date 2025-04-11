package org.example.domen_model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ActivityObject {

    protected double intelligencePoints;
    protected final ActivityType activityType;

    protected ActivityObject(ActivityType activityType) {
        this.activityType = activityType;
        this.intelligencePoints = computeIntelligencePoints();
    }

    protected abstract double computeIntelligencePoints();
}
