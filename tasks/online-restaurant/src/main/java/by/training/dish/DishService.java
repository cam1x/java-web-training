package by.training.dish;

import java.util.List;

public interface DishService {
    boolean deleteDish(Long dishId) throws DishServiceException;

    boolean deleteAllOrderDishes(Long orderId) throws DishServiceException;

    boolean deleteDishFromOrder(Long orderId, Long dishId) throws DishServiceException;

    List<DishDto> findOrderDishes(Long orderId) throws DishServiceException;

    void assignDish(Long orderId, Long... dishesId) throws DishServiceException;

    boolean updateDish(DishDto dishDto) throws DishServiceException;

    List<DishDto> findAllDishes() throws DishServiceException;

    boolean saveDish(DishDto dishDto) throws DishServiceException;

    DishDto getDish(Long id) throws DishServiceException;
}
