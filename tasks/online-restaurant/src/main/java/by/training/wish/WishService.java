package by.training.wish;

import java.util.List;

public interface WishService {
    List<WishDto> findOrderWishes(Long orderId) throws WishServiceException;

    Long saveWish(WishDto wishDto) throws WishServiceException;

    boolean deleteWish(Long id) throws WishServiceException;

    WishDto getById(Long id) throws WishServiceException;

    List<WishDto> findAll() throws WishServiceException;
}
