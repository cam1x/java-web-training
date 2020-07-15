package by.training.order.state;

import by.training.core.Bean;

import java.util.Optional;

import static by.training.order.state.StateConstants.*;

@Bean
public class OrderStateBuilder {
    public Optional<OrderState> fromString(String status) {
        switch (status) {
            case PROCESSING_MSG: {
                return Optional.of(new ProcessingState());
            }
            case READY_MSG: {
                return Optional.of(new ReadyState());
            }
            case COMPLETED_MSG: {
                return Optional.of(new CompletedState());
            }
            default: {
                return Optional.empty();
            }
        }
    }
}
