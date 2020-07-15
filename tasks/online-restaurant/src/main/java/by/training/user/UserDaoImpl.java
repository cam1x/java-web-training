package by.training.user;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.entity.UserEntity;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
public class UserDaoImpl implements UserDao {
    private static final String SELECT_ALL_QUERY = "select user_id, login, password, is_locked, discount_id, " +
            "user_avatar from user_account";
    private static final String SELECT_BY_ID_QUERY = "select user_id, login, password, is_locked, discount_id, " +
            "user_avatar from user_account where user_id = ?";
    private static final String SELECT_BY_LOGIN_QUERY = "select user_id, login, password, is_locked, discount_id, " +
            "user_avatar from user_account where login = ?";
    private static final String SELECT_LOGIN_COUNT = "select count(*) from user_account where login = ?";
    private static final String INSERT_QUERY = "insert into user_account (login, password, discount_id) values (?,?,?)";
    private static final String UPDATE_QUERY = "update user_account set login=?, password=?, is_locked=?, " +
            "discount_id=?, user_avatar=? where user_id = ?";
    private static final String DELETE_QUERY = "delete from user_account where user_id = ?";

    private ConnectionManager connectionManager;

    public UserDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(UserDto userDto) throws UserDaoException {
        UserEntity entity = userEntityFromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getLogin());
            insertStmt.setString(++i, entity.getPassword());
            insertStmt.setLong(++i, entity.getDiscountId());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            log.error("Failed to saved user: " + entity);
            throw new UserDaoException("Failed to saved user: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(UserDto userDto) throws UserDaoException {
        UserEntity entity = userEntityFromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getLogin());
            updateStmt.setString(++i, entity.getPassword());
            updateStmt.setBoolean(++i, entity.isLocked());
            updateStmt.setLong(++i, entity.getDiscountId());
            updateStmt.setBytes(++i, entity.getAvatar());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update user: " + entity);
            throw new UserDaoException("Failed to update user: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long userId) throws UserDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, userId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete user with id: " + userId);
            throw new UserDaoException("Failed to delete user with id: " + userId, e);
        }
    }

    @Override
    public UserDto getById(Long id) throws UserDaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    UserEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get user by id: " + id);
            throw new UserDaoException("Failed to get user by id: " + id, e);
        }
        Optional<UserDto> optionalUser = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalUser.isPresent()) {
            log.error("Failed to get user by id: " + id);
            throw new UserDaoException("Failed to get user by id: " + id);
        }
        return optionalUser.get();
    }

    @Override
    public List<UserDto> findAll() throws UserDaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    UserEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all users");
            throw new UserDaoException("Failed to find all users", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findByLogin(String login) throws UserDaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_LOGIN_QUERY)) {
            selectStmt.setString(1, login);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    UserEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find user by login = " + login);
            throw new UserDaoException("Failed to find user by login = " + login, e);
        }
        return result.stream().map(this::fromEntity).findFirst();
    }

    @Override
    public boolean isLoginTaken(String login) throws UserDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_LOGIN_COUNT)) {
            selectStmt.setString(1, login);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to check is login = " + login + " already exists");
            throw new UserDaoException("Failed to check is login = " + login + " already exists", e);
        }
        return false;
    }

    private UserEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] avatar = resultSet.getBytes("user_avatar");
        boolean isLocked = resultSet.getBoolean("is_locked");
        long discountId = resultSet.getLong("discount_id");
        return UserEntity.builder()
                .id(entityId)
                .login(login)
                .password(password)
                .avatar(avatar)
                .locked(isLocked)
                .discountId(discountId)
                .build();
    }

    private UserEntity userEntityFromDto(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setId(dto.getUserId());
        entity.setDiscountId(dto.getDiscountId());
        entity.setLocked(dto.isLocked());
        entity.setAvatar(dto.getAvatar());
        return entity;
    }

    private UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setUserId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setAvatar(entity.getAvatar());
        dto.setLocked(entity.isLocked());
        dto.setDiscountId(entity.getDiscountId());
        return dto;
    }
}

