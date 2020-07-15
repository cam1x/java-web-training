package by.training.discount;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.entity.DiscountEntity;
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
public class DiscountDaoImpl implements DiscountDao {
    private static final String SELECT_ALL_QUERY = "select discount_id, discount_amount from user_discount";
    private static final String SELECT_BY_ID_QUERY = "select discount_id, discount_amount from user_discount where discount_id = ?";

    private static final String INSERT_QUERY = "insert into user_discount (discount_amount) values (?)";
    private static final String UPDATE_QUERY = "update user_discount set discount_amount=? where discount_id = ?";
    private static final String DELETE_QUERY = "delete from user_discount where discount_id = ?";

    private ConnectionManager connectionManager;

    @Override
    public Long save(DiscountDto discountDto) throws DiscountDaoException {
        DiscountEntity entity = userDiscountFromDto(discountDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setDouble(++i, entity.getAmount());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save discount: " + entity);
            throw new DiscountDaoException("Failed to save discount: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(DiscountDto discountDto) throws DiscountDaoException {
        DiscountEntity entity = userDiscountFromDto(discountDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setDouble(++i, entity.getAmount());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update discount: " + entity);
            throw new DiscountDaoException("Failed to update discount: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long discountId) throws DiscountDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, discountId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete discount with id: " + discountId);
            throw new DiscountDaoException("Failed to delete discount with id: " + discountId, e);
        }
    }

    @Override
    public DiscountDto getById(Long id) throws DiscountDaoException {
        List<DiscountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    DiscountEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get discount by id: " + id);
            throw new DiscountDaoException("Failed to get discount by id: " + id, e);
        }
        Optional<DiscountDto> optionalDiscount = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalDiscount.isPresent()) {
            log.error("Failed to get discount by id: " + id);
            throw new DiscountDaoException("Failed to get discount by id: " + id);
        }
        return optionalDiscount.get();
    }

    @Override
    public List<DiscountDto> findAll() throws DiscountDaoException {
        List<DiscountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    DiscountEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all discounts", e);
            throw new DiscountDaoException("Failed to find all discounts", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private DiscountEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("discount_id");
        double amount = resultSet.getDouble("discount_amount");
        return DiscountEntity.builder()
                .id(entityId)
                .amount(amount)
                .build();
    }

    private DiscountEntity userDiscountFromDto(DiscountDto dto) {
        DiscountEntity entity = new DiscountEntity();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    private DiscountDto fromEntity(DiscountEntity entity) {
        DiscountDto dto = new DiscountDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        return dto;
    }
}
