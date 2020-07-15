package by.training.order.state;

import by.training.order.OrderDto;

import static by.training.order.state.StateConstants.COMPLETED_MSG;

public class CompletedState implements OrderState {
    @Override
    public void next(OrderDto orderDto) {

    }

    @Override
    public void prev(OrderDto orderDto) {
        orderDto.setState(new ReadyState());
    }

    @Override
    public String getStatus() {
        return COMPLETED_MSG;
    }
}
