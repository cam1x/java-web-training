package by.training.order.state;

import by.training.order.OrderDto;

import static by.training.order.state.StateConstants.READY_MSG;

public class ReadyState implements OrderState {
    @Override
    public void next(OrderDto orderDto) {
        orderDto.setState(new CompletedState());
    }

    @Override
    public void prev(OrderDto orderDto) {
        orderDto.setState(new ProcessingState());
    }

    @Override
    public String getStatus() {
        return READY_MSG;
    }
}
