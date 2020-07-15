package by.training.dish;

import by.training.dao.CRUDDao;

import java.util.List;

public interface DishDao extends CRUDDao<DishDto, Long> {
    void assignDish(Long orderId, Long dishId) throws DishDaoException;

    boolean deleteDishFromOrder(Long orderId, Long dishId) throws DishDaoException;

    List<DishDto> findOrderDishes(Long orderId) throws DishDaoException;
}