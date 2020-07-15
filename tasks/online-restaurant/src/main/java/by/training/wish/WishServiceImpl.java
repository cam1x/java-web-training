package by.training.wish;

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
public class WishServiceImpl implements WishService {
    private WishDao wishDao;

    @Override
    @Transactional
    public List<WishDto> findOrderWishes(Long orderId) throws WishServiceException {
        try {
            return wishDao.findOrderWishes(orderId);
        } catch (WishDaoException e) {
            log.error(e.getMessage(), e);
            throw new WishNotFoundException(e);
        }
    }

    @Override
    public Long saveWish(WishDto wishDto) throws WishServiceException {
        try {
            return wishDao.save(wishDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WishNotSavedException(e);
        }
    }

    @Override
    public boolean deleteWish(Long id) throws WishServiceException {
        try {
            return wishDao.delete(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WishNotDeletedException(e);
        }
    }

    @Override
    public WishDto getById(Long id) throws WishServiceException {
        try {
            return wishDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WishNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public List<WishDto> findAll() throws WishServiceException {
        try {
            return wishDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new WishNotFoundException(e);
        }
    }
}