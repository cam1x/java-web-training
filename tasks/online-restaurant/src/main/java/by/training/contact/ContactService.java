package by.training.contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    boolean deleteContact(Long contactId) throws ContactServiceException;

    List<ContactDto> findAllContacts() throws ContactServiceException;

    boolean saveContact(ContactDto role) throws ContactServiceException;

    boolean updateContact(ContactDto contactDto) throws ContactServiceException;

    boolean deleteAllUserContacts(Long userId) throws ContactServiceException;

    ContactDto getById(Long id) throws ContactServiceException;

    Optional<ContactDto> findByEmail(String email) throws ContactServiceException;

    List<ContactDto> findUserContacts(Long userId) throws ContactServiceException;
}