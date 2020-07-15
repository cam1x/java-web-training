package by.training.contact;

import by.training.dao.CRUDDao;

import java.util.List;
import java.util.Optional;

public interface ContactDao extends CRUDDao<ContactDto, Long> {
    Optional<ContactDto> findByEmail(String email) throws ContactDaoException;

    List<ContactDto> findUserContacts(Long userId) throws ContactDaoException;
}