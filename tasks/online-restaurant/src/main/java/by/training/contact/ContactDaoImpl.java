package by.training.contact;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.entity.ContactEntity;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
public class ContactDaoImpl implements ContactDao {
    private static final String SELECT_ALL_QUERY = "select contact_id, first_name, last_name, phone, email, user_id from user_contact";
    private static final String SELECT_BY_ID_QUERY = "select contact_id, first_name, last_name, phone, email, user_id from user_contact where contact_id = ?";
    private static final String SELECT_BY_EMAIL_QUERY = "select contact_id, first_name, last_name, phone, email, user_id from user_contact where email = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "select contact_id, first_name, last_name, phone, email, user_id from user_contact where user_id = ?";

    private static final String INSERT_QUERY = "insert into user_contact (first_name, last_name, phone, email, user_id) values (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "update user_contact set first_name=?, last_name=?, phone=?, email=?, user_id=? where contact_id = ?";
    private static final String DELETE_QUERY = "delete from user_contact where contact_id = ?";

    private ConnectionManager connectionManager;

    public ContactDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<ContactDto> findByEmail(String email) throws ContactDaoException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_EMAIL_QUERY)) {
            selectStmt.setString(1, email);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    ContactEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find contact with email: " + email);
            throw new ContactDaoException("Failed to find contact with email: " + email, e);
        }
        return result.stream()
                .map(this::fromEntity)
                .findFirst();
    }

    @Override
    public List<ContactDto> findUserContacts(Long userId) throws ContactDaoException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, userId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    ContactEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find contacts of userId: " + userId);
            throw new ContactDaoException("Failed to find contacts of userId: " + userId, e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(ContactDto contactDto) throws ContactDaoException {
        ContactEntity entity = userContactEntityFromDto(contactDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getFirstName());
            insertStmt.setString(++i, entity.getLastName());
            insertStmt.setString(++i, entity.getPhone());
            insertStmt.setString(++i, entity.getEmail());
            insertStmt.setLong(++i, entity.getUserId());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save contact:" + entity);
            throw new ContactDaoException("Failed to save contact:" + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(ContactDto contactDto) throws ContactDaoException {
        ContactEntity entity = userContactEntityFromDto(contactDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getFirstName());
            updateStmt.setString(++i, entity.getLastName());
            updateStmt.setString(++i, entity.getPhone());
            updateStmt.setString(++i, entity.getEmail());
            updateStmt.setLong(++i, entity.getUserId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update contact: " + entity);
            throw new ContactDaoException("Failed to update contact: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long id) throws ContactDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete contact with id: " + id);
            throw new ContactDaoException("Failed to delete contact with id: " + id, e);
        }
    }

    @Override
    public ContactDto getById(Long id) throws ContactDaoException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    ContactEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Contact not found by id: " + id);
            throw new ContactDaoException("Contact not found by id: " + id);
        }
        Optional<ContactDto> optionalContact = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalContact.isPresent()) {
            throw new ContactDaoException("Contact not found by id: " + id);
        }
        return optionalContact.get();
    }

    @Override
    public List<ContactDto> findAll() throws ContactDaoException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    ContactEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all contacts", e);
            throw new ContactDaoException("Failed to find all contacts", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private ContactEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("contact_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        long userId = resultSet.getLong("user_id");
        return ContactEntity.builder()
                .id(entityId)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .email(email)
                .userId(userId)
                .build();
    }

    private ContactEntity userContactEntityFromDto(ContactDto dto) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(dto.getId());
        contactEntity.setFirstName(dto.getFirstName());
        contactEntity.setLastName(dto.getLastName());
        contactEntity.setEmail(dto.getEmail());
        contactEntity.setPhone(dto.getPhone());
        contactEntity.setUserId(dto.getUserId());
        return contactEntity;
    }

    private ContactDto fromEntity(ContactEntity entity) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(entity.getId());
        contactDto.setFirstName(entity.getFirstName());
        contactDto.setLastName(entity.getLastName());
        contactDto.setPhone(entity.getPhone());
        contactDto.setEmail(entity.getEmail());
        contactDto.setUserId(entity.getUserId());
        return contactDto;
    }
}
