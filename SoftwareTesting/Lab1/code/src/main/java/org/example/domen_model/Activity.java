package org.example.domen_model;


public record Activity(ActivityObject activityObject, ActivityType activityType, String message) {

    public Activity {
        if (activityObject.getActivityType() != activityType) {
            throw new IllegalArgumentException("ActivityObject does not match activityType " + activityObject.getActivityType());
        }
    }

    public void doMe(Creature creature) {
        String message = "";
        try {
            switch (activityType) {
                case CHILLING -> {
                    if (creature instanceof Human) {
                        creature.setIntelligence(creature.getIntelligence() - activityObject.getIntelligencePoints() * 0.5);
                        message = String.format("Человек %s теряет %.2fIQ", creature.getName(), activityObject.getIntelligencePoints() * 0.5);
                    }
                    if (creature instanceof Dolphin) {
                        creature.setIntelligence(creature.getIntelligence() + activityObject.getIntelligencePoints() * 0.5);
                        message = String.format("У дельфина %s интеллект увеличивается на %.2fIQ", creature.getName(), activityObject.getIntelligencePoints() * 0.5);
                    }
                }
                case CREATING -> {
                    if (creature instanceof Human) {
                        creature.setIntelligence(creature.getIntelligence() + activityObject.getIntelligencePoints());
                        message = String.format("Человек %s получает %.2fIQ", creature.getName(), activityObject.getIntelligencePoints());
                    }
                    if (creature instanceof Dolphin) {
                        creature.setIntelligence(creature.getIntelligence() - activityObject.getIntelligencePoints());
                        message = String.format("У дельфина %s интеллект уменьшается на %.2fIQ", creature.getName(), activityObject.getIntelligencePoints());
                    }
                }
                case SPLASHING -> {
                    if (creature instanceof Human) {
                        creature.setIntelligence(creature.getIntelligence() - activityObject.getIntelligencePoints());
                        message = String.format("Человек %s теряет %.2fIQ", creature.getName(), activityObject.getIntelligencePoints());
                    }
                    if (creature instanceof Dolphin) {
                        creature.setIntelligence(creature.getIntelligence() + activityObject.getIntelligencePoints());
                        message = String.format("У дельфина %s интеллект увеличивается на %.2fIQ", creature.getName(), activityObject.getIntelligencePoints());
                    }
                }
            }
        } catch (NegativeIntelligenceException e) {
            message = String.format("У сущности %s интеллект опустился в ноль", creature.getName());
        }
        System.out.println(message);
    }


}
