package by.training.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum CarriageType {
    COMPARTMENT,
    PREMIUM,
    RESTAURANT;

    public static Optional<CarriageType> fromString(String type) {
        return Stream.of(CarriageType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}

