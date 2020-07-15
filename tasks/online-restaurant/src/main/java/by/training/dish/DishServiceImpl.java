package by.training.dish;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class DishServiceImpl implements DishService {
    private DishDao dishDao;

    @Override
    public boolean deleteDish(Long dishId) throws DishServiceException {
        try {
            return dishDao.delete(dishId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteAllOrderDishes(Long orderId) throws DishServiceException {
        try {
            List<DishDto> orderDishes = dishDao.findOrderDishes(orderId);
            boolean isSuccess = true;
            for (DishDto orderDish : orderDishes) {
                isSuccess &= dishDao.deleteDishFromOrder(orderId, orderDish.getId());
            }
            return isSuccess;
        } catch (DishDaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotDeletedException(e);
        }
    }

    @Override
    public boolean deleteDishFromOrder(Long orderId, Long dishId) throws DishServiceException {
        try {
            return dishDao.deleteDishFromOrder(orderId, dishId);
        } catch (DishDaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotDeletedException(e);
        }
    }

    @Override
    public List<DishDto> findOrderDishes(Long orderId) throws DishServiceException {
        try {
            return dishDao.findOrderDishes(orderId);
        } catch (DishDaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotFoundException(e);
        }
    }

    @Override
    public void assignDish(Long orderId, Long... dishesId) throws DishServiceException {
        try {
            for (Long dishId : dishesId) {
                dishDao.assignDish(orderId, dishId);
            }
        } catch (DishDaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotAssignedException(e);
        }
    }

    @Override
    public boolean updateDish(DishDto dishDto) throws DishServiceException {
        try {
            return dishDao.update(dishDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotUpdatedException(e);
        }
    }

    @Override
    public List<DishDto> findAllDishes() throws DishServiceException {
        try {
            return dishDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotFoundException(e);
        }
    }

    @Override
    public boolean saveDish(DishDto dishDto) throws DishServiceException {
        try {
            return dishDao.save(dishDto) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotSavedException(e);
        }
    }

    @Override
    public DishDto getDish(Long id) throws DishServiceException {
        try {
            return dishDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DishNotFoundException(e);
        }
    }
}
