package by.training.order.state;

import by.training.order.OrderDto;

import static by.training.order.state.StateConstants.PROCESSING_MSG;

public class ProcessingState implements OrderState {
    @Override
    public void next(OrderDto orderDto) {
        orderDto.setState(new ReadyState());
    }

    @Override
    public void prev(OrderDto orderDto) {

    }

    @Override
    public String getStatus() {
        return PROCESSING_MSG;
    }
}
