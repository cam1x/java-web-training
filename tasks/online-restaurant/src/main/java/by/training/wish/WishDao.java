package by.training.wish;

import by.training.dao.CRUDDao;

import java.util.List;

public interface WishDao extends CRUDDao<WishDto, Long> {
    List<WishDto> findOrderWishes(Long orderId) throws WishDaoException;
}
