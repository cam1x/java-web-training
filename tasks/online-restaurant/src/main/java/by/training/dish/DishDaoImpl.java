package by.training.dish;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.dao.DaoException;
import by.training.entity.DishEntity;
import by.training.wish.WishDaoException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
@AllArgsConstructor
public class DishDaoImpl implements DishDao {
    private static final String ASSIGN_DISH_QUERY = "insert into food_order_relation (order_id, dish_id) values (?, ?);";
    private static final String SELECT_ALL_ORDER_DISHES_QUERY = "select rel.order_id, rel.dish_id, dish_name, dish_price, dish_description, dish_photo " +
            "from food_order_relation rel inner join ordered_dish " +
            "on rel.dish_id=ordered_dish.dish_id where order_id = ?";
    private static final String SELECT_ALL_DISHES_QUERY = "select dish_id, dish_name, dish_price, dish_description,dish_photo from ordered_dish";
    private static final String SELECT_DISH_BY_ID_QUERY = "select dish_id, dish_name, dish_price, dish_description, dish_photo from ordered_dish where dish_id = ?";
    private static final String INSERT_DISH_QUERY = "insert into ordered_dish(dish_name, dish_price, dish_description, dish_photo) values(?, ?, ?, ?)";
    private static final String UPDATE_DISH_QUERY = "update ordered_dish set dish_name=?, dish_price=?, dish_description=?, dish_photo=?  where dish_id=?";
    private static final String DELETE_DISH_QUERY = "delete from ordered_dish where dish_id=?";
    private static final String DELETE_ORDER_DISH_QUERY = "delete from food_order_relation where order_id=? and dish_id=?";

    private ConnectionManager connectionManager;

    @Override
    public void assignDish(Long orderId, Long dishId) throws DishDaoException {
        try {
            this.assignDish(orderId, dishId, connectionManager.getConnection());
        } catch (SQLException e) {
            log.error("Failed to assign relation(orderId - dishId): " + orderId + "-" + dishId);
            throw new DishDaoException("Failed to assign relation(orderId - dishId): " + orderId + "-" + dishId, e);
        }
    }

    @Override
    public boolean deleteDishFromOrder(Long orderId, Long dishId) throws DishDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_ORDER_DISH_QUERY)) {
            int i = 0;
            updateStmt.setLong(++i, orderId);
            updateStmt.setLong(++i, dishId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete relation(orderId - dishId): " + orderId + "-" + dishId);
            throw new DishDaoException("Failed to delete relation(orderId - dishId): " + orderId + "-" + dishId, e);
        }
    }

    @Override
    public List<DishDto> findOrderDishes(Long orderId) throws DishDaoException {
        List<DishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_ORDER_DISHES_QUERY)) {
            selectStmt.setLong(1, orderId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    DishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.warn("Failed to get order (id=" + orderId + ") dishes");
            throw new DishDaoException("Failed to get order (id=" + orderId + ") dishes", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(DishDto dishDto) throws DaoException {
        DishEntity entity = dishFromDto(dishDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_DISH_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getName());
            insertStmt.setDouble(++i, entity.getPrice());
            insertStmt.setString(++i, entity.getDescription());
            insertStmt.setBytes(++i, entity.getPhoto());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save dish: " + entity);
            throw new DishDaoException("Failed to save dish: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(DishDto dishDto) throws DaoException {
        DishEntity entity = dishFromDto(dishDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_DISH_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getName());
            updateStmt.setDouble(++i, entity.getPrice());
            updateStmt.setString(++i, entity.getDescription());
            updateStmt.setBytes(++i, entity.getPhoto());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update dish: " + entity);
            throw new DishDaoException("Failed to update dish: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_DISH_QUERY)) {
            int i = 0;
            updateStmt.setLong(++i, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete dish with id: " + id);
            throw new DishDaoException("Failed to delete dish with id: " + id, e);
        }
    }

    @Override
    public DishDto getById(Long id) throws DaoException {
        List<DishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_DISH_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    DishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get dish by id: " + id);
            throw new DishDaoException("Failed to get dish by id: " + id, e);
        }
        Optional<DishDto> optionalDish = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalDish.isPresent()) {
            log.error("Failed to get dish by id: " + id);
            throw new DishDaoException("Failed to get dish by id: " + id);
        }
        return optionalDish.get();
    }

    @Override
    public List<DishDto> findAll() throws DaoException {
        List<DishEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_DISHES_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    DishEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all dishes");
            throw new WishDaoException("Failed to find all dishes", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private void assignDish(Long orderId, Long dishId, Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(ASSIGN_DISH_QUERY)) {
            int i = 0;
            stmt.setLong(++i, orderId);
            stmt.setLong(++i, dishId);
            stmt.executeUpdate();
        }
    }

    private DishEntity dishFromDto(DishDto dto) {
        DishEntity entity = new DishEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setPhoto(dto.getPhoto());
        return entity;
    }

    private DishEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("dish_id");
        String dishName = resultSet.getString("dish_name");
        double dishPrice = resultSet.getDouble("dish_price");
        String dishDescription = resultSet.getString("dish_description");
        byte[] photo = resultSet.getBytes("dish_photo");
        return DishEntity.builder()
                .id(id)
                .name(dishName)
                .price(dishPrice)
                .description(dishDescription)
                .photo(photo)
                .build();
    }

    private DishDto fromEntity(DishEntity entity) {
        DishDto dto = new DishDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }
}