package org.example;

import org.example.domen_model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityDomainModelTest {

    static class GenericActivityObject extends ActivityObject {

        protected GenericActivityObject(ActivityType activityType, Double value) {
            super(activityType);
            this.intelligencePoints = value; // переопределяем
        }

        @Override
        protected double computeIntelligencePoints() {
            return 0;
        }
    }

    @Test
    void testActivityConstructorThrowsExceptionOnMismatch() {
        ActivityObject activityObject = new GenericActivityObject(ActivityType.CHILLING, 10.0);
        assertThrows(IllegalArgumentException.class, () -> {
            new Activity(activityObject, ActivityType.CREATING, "Ошибка соответствия");
        });
    }

    @Test
    void testHumanChillingReducesIQ() {
        Human human = new Human("Тимур", 0, 100);
        ActivityObject activityObject = new GenericActivityObject(ActivityType.CHILLING, 10.0);
        Activity activity = new Activity(activityObject, ActivityType.CHILLING, "Отдых");

        human.doActivity(activity);

        assertEquals(95.0, human.getIntelligence(), 0.01);
    }

    @Test
    void testDolphinChillingIncreasesIQ() {
        Dolphin dolphin = new Dolphin("Флиппер", 0, 80);
        ActivityObject activityObject = new GenericActivityObject(ActivityType.CHILLING, 10.0);
        System.out.println(activityObject.getIntelligencePoints());
        Activity activity = new Activity(activityObject, ActivityType.CHILLING, "Плескание");

        dolphin.doActivity(activity);

        assertEquals(85.0, dolphin.getIntelligence(), 0.01);
    }

    @ParameterizedTest
    @CsvSource({
            "HUMAN, CHILLING, 100, 10, 95.0",
            "HUMAN, CREATING, 100, 10, 110.0",
            "HUMAN, SPLASHING, 100, 10, 90.0",
            "DOLPHIN, CHILLING, 100, 10, 105.0",
            "DOLPHIN, CREATING, 100, 10, 90.0",
            "DOLPHIN, SPLASHING, 100, 10, 110.0"
    })
    void testActivityEffectOnCreatures(String creatureType, String activityType, double initialIQ, double points, double expectedIQ) {
        Creature creature = creatureType.equals("HUMAN") ? new Human("TestSubject", 0, initialIQ) : new Dolphin("TestSubject", 0, initialIQ);

        ActivityObject activityObject = new GenericActivityObject(ActivityType.valueOf(activityType), points);
        Activity activity = new Activity(activityObject, ActivityType.valueOf(activityType), "Test message");

        creature.doActivity(activity);

        assertEquals(expectedIQ, creature.getIntelligence(), 0.01);
    }


    @Test
    void testSetNegativeIntelligenceShouldThrowException() {
        Creature creature = new Human("TestHuman", 30, 10.0);
        NegativeIntelligenceException exception = assertThrows(NegativeIntelligenceException.class, () -> {
            creature.setIntelligence(-5.0);
        });
        assertEquals("IQ этой сущности опустился в ноль", exception.getMessage());
        assertEquals(0.0, creature.getIntelligence()); // IQ обнуляется
    }

    @Test
    void testActivityThatCausesNegativeIntelligence() {
        Creature creature = new Human("TestHuman", 30, 5.0);
        ActivityObject activityObject = new GenericActivityObject(ActivityType.SPLASHING, 10.0);
        Activity activity = new Activity(activityObject, ActivityType.SPLASHING, "test");
        activity.doMe(creature);
        assertEquals(0.0, creature.getIntelligence());
    }

    @Test
    void testActivityThatDoesNotCauseNegativeIntelligence() {
        Creature creature = new Dolphin("Flipper", 15, 5.0);
        ActivityObject activityObject = new GenericActivityObject(ActivityType.SPLASHING, 2.0);

        Activity activity = new Activity(activityObject, ActivityType.SPLASHING, "test");
        activity.doMe(creature);
        assertEquals(7.0, creature.getIntelligence(), 0.0001); // 5 + 2
    }

    @Test
    void testCreatureConstructorRejectsNegativeIntelligence() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Human("BadHuman", 25, -1.0);
        });
    }
}

