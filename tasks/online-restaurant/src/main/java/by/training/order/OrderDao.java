package by.training.order;

import by.training.dao.CRUDDao;

import java.util.List;

public interface OrderDao extends CRUDDao<OrderDto, Long> {
    List<OrderDto> findUserOrders(Long userId) throws OrderDaoException;
}
