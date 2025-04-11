package org.example.domen_model;

import lombok.Getter;

@Getter
public enum ActivityType {

    CREATING("Создание чего-то нового"),
    CHILLING("Отдых"),
    SPLASHING("Плескание");

    private final String name;

    ActivityType(String name) {
        this.name = name;
    }
}
