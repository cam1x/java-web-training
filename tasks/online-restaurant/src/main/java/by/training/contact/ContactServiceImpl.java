package by.training.contact;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Log4j
@Bean
@AllArgsConstructor
@TransactionSupport
public class ContactServiceImpl implements ContactService {
    private ContactDao contactDao;

    @Override
    public boolean deleteContact(Long contactId) throws ContactServiceException {
        try {
            return contactDao.delete(contactId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotDeletedException(e);
        }
    }

    @Override
    public List<ContactDto> findAllContacts() throws ContactServiceException {
        try {
            return contactDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public boolean saveContact(ContactDto contactDto) throws ContactServiceException {
        try {
            return contactDao.save(contactDto) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotSavedException(e);
        }
    }

    @Override
    public boolean updateContact(ContactDto contactDto) throws ContactServiceException {
        try {
            return contactDao.update(contactDto);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotUpdatedException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteAllUserContacts(Long userId) throws ContactServiceException {
        List<ContactDto> userContacts;
        try {
            userContacts = contactDao.findUserContacts(userId);
            boolean isSuccess = true;
            for (ContactDto userContact : userContacts) {
                isSuccess &= contactDao.delete(userContact.getId());
            }
            return isSuccess;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotDeletedException(e);
        }
    }

    @Override
    public ContactDto getById(Long id) throws ContactServiceException {
        try {
            return contactDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotFoundException(e);
        }
    }

    @Override
    public Optional<ContactDto> findByEmail(String email) throws ContactServiceException {
        try {
            return contactDao.findByEmail(email);
        } catch (ContactDaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotFoundException(e);
        }
    }

    @Override
    public List<ContactDto> findUserContacts(Long userId) throws ContactServiceException {
        try {
            return contactDao.findUserContacts(userId);
        } catch (ContactDaoException e) {
            log.error(e.getMessage(), e);
            throw new ContactNotFoundException(e);
        }
    }
}
