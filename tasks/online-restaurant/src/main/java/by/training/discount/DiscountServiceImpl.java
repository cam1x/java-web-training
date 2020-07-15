package by.training.discount;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j
@Bean
@AllArgsConstructor
@TransactionSupport
public class DiscountServiceImpl implements DiscountService {
    private DiscountDao discountDao;

    @Override
    public boolean deleteDiscount(Long discountId) throws DiscountServiceException {
        try {
            return discountDao.delete(discountId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DeleteDiscountException(e);
        }
    }

    @Override
    @Transactional
    public List<DiscountDto> findAllDiscounts() throws DiscountServiceException {
        try {
            return discountDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new FindDiscountException(e);
        }
    }

    @Override
    @Transactional
    public boolean saveDiscount(DiscountDto discountDto) throws DiscountServiceException {
        try {
            return discountDao.save(discountDto) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new SaveDiscountException(e);
        }
    }

    @Override
    public boolean updateDiscount(DiscountDto discountDto) throws DiscountServiceException {
        try {
            return discountDao.update(discountDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new DiscountNotUpdatedException(e);
        }
    }

    @Override
    public long saveDefaultDiscount() throws DiscountServiceException {
        try {
            DiscountDto defaultDiscount = DiscountDto.builder().amount(0).build();
            return discountDao.save(defaultDiscount);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new SaveDiscountException(e);
        }
    }

    @Override
    public DiscountDto getById(Long id) throws DiscountServiceException {
        try {
            return discountDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new FindDiscountException(e);
        }
    }
}
