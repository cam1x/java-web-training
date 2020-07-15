package by.training.validator;

import by.training.entity.CarriageType;

import java.util.Optional;

public class ValidatorFactory {

    public Optional<EntityValidator> getByType(String type) {
        Optional<CarriageType> carriageTypeOptional = CarriageType.fromString(type);

        if (!carriageTypeOptional.isPresent()) {
            return Optional.empty();
        }

        switch (carriageTypeOptional.get()) {
            case PREMIUM:
                return Optional.of(new PremiumCarriageValidator());
            case RESTAURANT:
                return Optional.of(new WagonRestaurantValidator());
            case COMPARTMENT:
                return Optional.of(new CompartmentValidator());
            default:
                return Optional.empty();
        }
    }
}
