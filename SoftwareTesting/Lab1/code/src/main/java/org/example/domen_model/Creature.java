package org.example.domen_model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature {
    private String name;
    private int age;
    private double intelligence;

    public Creature(String name, int age, double intelligence) {
        if (intelligence < 0){
            throw new IllegalArgumentException("intelligence must be greater or equal 0");
        }
        this.name = name;
        this.age = age;
        this.intelligence = intelligence;
    }

    public void setIntelligence(double intelligence) throws NegativeIntelligenceException {
        if (intelligence < 0){
            this.intelligence = 0;
            throw new NegativeIntelligenceException("IQ этой сущности опустился в ноль");
        }
        this.intelligence = intelligence;
    }

    public void doActivity(Activity activity){
        String messageBefore = String.format("Сущность %s (IQ: %.2f, возраст: %d) начала действие %s",
                name, intelligence, age, activity.activityType().getName());
        System.out.println(messageBefore);

        activity.doMe(this);

        String messageAfter = String.format("Сущность %s (IQ: %.2f, возраст: %d) закончила действие %s",
                name, intelligence, age, activity.activityType().getName());
        System.out.println(messageAfter);
    }

}
