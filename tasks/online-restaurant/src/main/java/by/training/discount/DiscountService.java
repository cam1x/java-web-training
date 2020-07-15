package by.training.discount;

import java.util.List;

public interface DiscountService {
    boolean deleteDiscount(Long discountId) throws DiscountServiceException;

    List<DiscountDto> findAllDiscounts() throws DiscountServiceException;

    boolean saveDiscount(DiscountDto discountDto) throws DiscountServiceException;

    boolean updateDiscount(DiscountDto discountDto) throws DiscountServiceException;

    long saveDefaultDiscount() throws DiscountServiceException;

    DiscountDto getById(Long id) throws DiscountServiceException;
}