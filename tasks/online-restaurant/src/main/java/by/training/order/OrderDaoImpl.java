package by.training.order;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.dao.DaoException;
import by.training.entity.FoodOrderEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
@AllArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ALL_QUERY = "select order_id, order_date, booking_date, order_status, " +
            "customer_id from food_order";
    private static final String SELECT_BY_ID_QUERY = "select order_id, order_date, booking_date, order_status, " +
            "customer_id from food_order where order_id = ?";
    private static final String SELECT_BY_CUSTOMER_ID_QUERY = "select order_id, order_date, booking_date, " +
            "order_status, customer_id from food_order where customer_id = ?";

    private static final String INSERT_QUERY = "insert into food_order (order_date, booking_date, customer_id) values (?, ?, ?)";
    private static final String UPDATE_QUERY = "update food_order set order_date=?, booking_date=?, order_status=?, customer_id=? where order_id = ?";
    private static final String DELETE_QUERY = "delete from food_order where order_id = ?";


    private ConnectionManager connectionManager;

    @Override
    public List<OrderDto> findUserOrders(Long customerId) throws OrderDaoException {
        List<FoodOrderEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_CUSTOMER_ID_QUERY)) {
            selectStmt.setLong(1, customerId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrderEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.warn("Failed to get user (id=" + customerId + ") orders");
            throw new OrderDaoException("Failed to get user (id=" + customerId + ") orders", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(OrderDto orderDto) throws DaoException {
        FoodOrderEntity entity = orderFromDto(orderDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setTimestamp(++i, new Timestamp(entity.getOrderDate().getTime()));
            insertStmt.setTimestamp(++i, new Timestamp(entity.getBookingDate().getTime()));
            insertStmt.setLong(++i, entity.getCustomerId());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save food order: " + entity);
            throw new OrderDaoException("Failed to food order: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(OrderDto orderDto) throws DaoException {
        FoodOrderEntity entity = orderFromDto(orderDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setTimestamp(++i, new Timestamp(entity.getOrderDate().getTime()));
            updateStmt.setTimestamp(++i, new Timestamp(entity.getBookingDate().getTime()));
            updateStmt.setString(++i, entity.getStatus());
            updateStmt.setLong(++i, entity.getCustomerId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update food order: " + entity);
            throw new OrderDaoException("Failed to update food order: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete food order with id: " + id);
            throw new OrderDaoException("Failed to delete food order with id: " + id, e);
        }
    }

    @Override
    public OrderDto getById(Long id) throws DaoException {
        List<FoodOrderEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrderEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get food order by id: " + id);
            throw new OrderDaoException("Failed to get food order by id: " + id, e);
        }
        Optional<OrderDto> optionalDiscount = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalDiscount.isPresent()) {
            log.error("Failed to get food order by id: " + id);
            throw new OrderDaoException("Failed to get food order by id: " + id);
        }
        return optionalDiscount.get();
    }

    @Override
    public List<OrderDto> findAll() throws DaoException {
        List<FoodOrderEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrderEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all food orders", e);
            throw new OrderDaoException("Failed to find all food orders", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private FoodOrderEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("order_id");
        Date orderDate = resultSet.getTimestamp("order_date");
        Date bookingDate = resultSet.getTimestamp("booking_date");
        String status = resultSet.getString("order_status");
        long customerId = resultSet.getLong("customer_id");
        return FoodOrderEntity.builder()
                .id(id)
                .orderDate(orderDate)
                .bookingDate(bookingDate)
                .status(status)
                .customerId(customerId)
                .build();
    }

    private FoodOrderEntity orderFromDto(OrderDto dto) {
        FoodOrderEntity entity = new FoodOrderEntity();
        entity.setId(dto.getId());
        entity.setOrderDate(dto.getOrderDate());
        entity.setBookingDate(dto.getBookingDate());
        entity.setStatus(dto.getStatus());
        entity.setCustomerId(dto.getCustomerId());
        return entity;
    }

    private OrderDto fromEntity(FoodOrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setBookingDate(entity.getBookingDate());
        dto.setStatus(entity.getStatus());
        dto.setCustomerId(entity.getCustomerId());
        return dto;
    }
}
