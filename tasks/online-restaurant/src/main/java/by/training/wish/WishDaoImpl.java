package by.training.wish;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.dao.DaoException;
import by.training.entity.WishEntity;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
public class WishDaoImpl implements WishDao {
    private static final String SELECT_ALL_QUERY = "select wish_id, wish, food_order_id from order_wish";
    private static final String SELECT_BY_ID_QUERY = "select wish_id, wish, food_order_id from order_wish where wish_id = ?";
    private static final String SELECT_BY_ORDER_ID_QUERY = "select wish_id, wish, food_order_id from order_wish where food_order_id = ?";

    private static final String INSERT_QUERY = "insert into order_wish (wish, food_order_id) values (?, ?)";
    private static final String UPDATE_QUERY = "update order_wish set wish=?, food_order_id=? where wish_id = ?";
    private static final String DELETE_QUERY = "delete from order_wish where wish_id = ?";

    private ConnectionManager connectionManager;

    public WishDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<WishDto> findOrderWishes(Long orderId) throws WishDaoException {
        List<WishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ORDER_ID_QUERY)) {
            selectStmt.setLong(1, orderId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all order wishes", e);
            throw new WishDaoException("Failed to find all order wishes", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(WishDto wishDto) throws DaoException {
        WishEntity entity = orderWishFromDto(wishDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getWish());
            insertStmt.setLong(++i, entity.getFoodOrderId());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save wish: " + entity);
            throw new WishDaoException("Failed to save wish: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(WishDto wishDto) throws DaoException {
        WishEntity entity = orderWishFromDto(wishDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getWish());
            updateStmt.setLong(++i, entity.getFoodOrderId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update wish: " + entity);
            throw new WishDaoException("Failed to update wish: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete wish with id: " + id);
            throw new WishDaoException("Failed to delete wish with id: " + id, e);
        }
    }

    @Override
    public WishDto getById(Long id) throws DaoException {
        List<WishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get wish by id: " + id);
            throw new WishDaoException("Failed to get wish by id: " + id, e);
        }
        Optional<WishDto> optionalWish = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalWish.isPresent()) {
            log.error("Failed to get wish by id: " + id);
            throw new WishDaoException("Failed to get wish by id: " + id);
        }
        return optionalWish.get();
    }

    @Override
    public List<WishDto> findAll() throws DaoException {
        List<WishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all wishes", e);
            throw new WishDaoException("Failed to find all wishes", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private WishEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("wish_id");
        String wish = resultSet.getString("wish");
        long orderId = resultSet.getLong("food_order_id");
        return WishEntity.builder()
                .id(entityId)
                .wish(wish)
                .foodOrderId(orderId)
                .build();
    }

    private WishEntity orderWishFromDto(WishDto dto) {
        WishEntity entity = new WishEntity();
        entity.setId(dto.getId());
        entity.setWish(dto.getWish());
        entity.setFoodOrderId(dto.getFoodOrderId());
        return entity;
    }

    private WishDto fromEntity(WishEntity entity) {
        WishDto dto = new WishDto();
        dto.setId(entity.getId());
        dto.setWish(entity.getWish());
        dto.setFoodOrderId(entity.getFoodOrderId());
        return dto;
    }
}
