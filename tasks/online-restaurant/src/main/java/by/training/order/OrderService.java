package by.training.order;

import java.util.List;

public interface OrderService {
    List<OrderDto> findUserOrders(Long userId) throws OrderServiceException;

    boolean saveOrder(OrderDto orderDto) throws OrderServiceException;

    boolean deleteOrder(Long orderId) throws OrderServiceException;

    boolean updateOrder(OrderDto orderDto) throws OrderServiceException;

    OrderDto getById(Long orderId) throws OrderServiceException;

    List<OrderDto> findAll() throws OrderServiceException;
}
