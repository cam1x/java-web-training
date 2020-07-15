package by.training.order;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import by.training.dish.DishDto;
import by.training.dish.DishService;
import by.training.dish.DishServiceException;
import by.training.order.state.OrderState;
import by.training.order.state.OrderStateBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private DishService dishService;
    private OrderStateBuilder stateBuilder;

    @Override
    @Transactional
    public List<OrderDto> findUserOrders(Long userId) throws OrderServiceException {
        try {
            List<OrderDto> userOrders = orderDao.findUserOrders(userId);
            userOrders.forEach(this::assignState);
            return userOrders;
        } catch (OrderDaoException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotFoundException(e);
        }
    }

    @Override
    public boolean saveOrder(OrderDto orderDto) throws OrderServiceException {
        try {
            long saved = orderDao.save(orderDto);
            if (saved > 0) {
                for (DishDto orderDish : orderDto.getOrderDishes()) {
                    dishService.assignDish(saved, orderDish.getId());
                }
                return true;
            }
            return false;
        } catch (DaoException | DishServiceException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotSavedException(e);
        }
    }

    @Override
    public boolean deleteOrder(Long orderId) throws OrderServiceException {
        try {
            return orderDao.delete(orderId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotDeletedException(e);
        }
    }

    @Override
    public boolean updateOrder(OrderDto orderDto) throws OrderServiceException {
        try {
            return orderDao.update(orderDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotUpdatedException(e);
        }
    }

    @Override
    public OrderDto getById(Long orderId) throws OrderServiceException {
        try {
            OrderDto orderDto = orderDao.getById(orderId);
            assignState(orderDto);
            return orderDto;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public List<OrderDto> findAll() throws OrderServiceException {
        try {
            List<OrderDto> orders = orderDao.findAll();
            orders.forEach(this::assignState);
            return orders;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new OrderNotFoundException(e);
        }
    }

    private void assignState(OrderDto orderDto) {
        Optional<OrderState> stateOptional = stateBuilder.fromString(orderDto.getStatus());
        stateOptional.ifPresent(orderDto::setState);
    }
}
