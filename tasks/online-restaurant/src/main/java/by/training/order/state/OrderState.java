package by.training.order.state;

import by.training.order.OrderDto;

public interface OrderState {
    void next(OrderDto orderDto);
    void prev(OrderDto orderDto);
    String getStatus();
}
