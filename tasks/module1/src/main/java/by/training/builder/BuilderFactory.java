package by.training.builder;

import by.training.entity.CarriageType;

import java.util.Optional;

public class BuilderFactory {

    public Optional<Builder> getByType(String type) {
        Optional<CarriageType> carriageTypeOptional = CarriageType.fromString(type);

        if (!carriageTypeOptional.isPresent()) {
            return Optional.empty();
        }

        switch (carriageTypeOptional.get()) {
            case PREMIUM:
                return Optional.of(new PremiumCarriageBuilder());
            case RESTAURANT:
                return Optional.of(new WagonRestaurantBuilder());
            case COMPARTMENT:
                return Optional.of(new CompartmentBuilder());
            default:
                return Optional.empty();
        }
    }
}
