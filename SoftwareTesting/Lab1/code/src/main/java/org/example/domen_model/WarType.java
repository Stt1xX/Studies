package org.example.domen_model;

import lombok.Getter;

@Getter
public enum WarType {
    SMALL(100),
    MEDIUM(200),
    LARGE(500),
    GLOBAL(1000);

    private final int intelligencePoints;

    WarType(int i) {
        this.intelligencePoints = i;
    }
}
