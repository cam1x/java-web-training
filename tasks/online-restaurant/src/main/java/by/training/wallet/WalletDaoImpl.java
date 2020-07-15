package by.training.wallet;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.entity.WalletEntity;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Bean
public class WalletDaoImpl implements WalletDao {
    private static final String SELECT_ALL_QUERY = "select wallet_id, wallet_name, wallet_amount, user_id from user_wallet";
    private static final String SELECT_BY_WALLET_ID_QUERY = "select wallet_id, wallet_name, wallet_amount, user_id from user_wallet where wallet_id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "select wallet_id, wallet_name, wallet_amount, user_id from user_wallet where user_id = ?";

    private static final String INSERT_QUERY = "insert into user_wallet (wallet_name, user_id) values (?, ?)";
    private static final String UPDATE_QUERY = "update user_wallet set wallet_name=?, wallet_amount=?, user_id=? where wallet_id = ?";
    private static final String DELETE_QUERY = "delete from user_wallet where wallet_id = ?";

    private ConnectionManager connectionManager;

    public WalletDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<WalletDto> findUserWallets(Long userId) throws WalletDaoException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, userId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WalletEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find user(id=" + userId + ") wallets");
            throw new WalletDaoException("Failed to find user(id=" + userId + ") wallets", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(WalletDto walletDto) throws WalletDaoException {
        WalletEntity entity = userEntityFromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getName());
            insertStmt.setLong(++i, entity.getUserId());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }

            }
        } catch (SQLException e) {
            log.error("Failed to save wallet: " + entity);
            throw new WalletDaoException("Failed to save wallet: " + entity, e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(WalletDto walletDto) throws WalletDaoException {
        WalletEntity entity = userEntityFromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getName());
            updateStmt.setDouble(++i, entity.getAmount());
            updateStmt.setLong(++i, entity.getUserId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update wallet: " + entity);
            throw new WalletDaoException("Failed to update wallet: " + entity, e);
        }
    }

    @Override
    public boolean delete(Long walletId) throws WalletDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, walletId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete wallet with id: " + walletId);
            throw new WalletDaoException("Failed to delete wallet with id: " + walletId, e);
        }
    }

    @Override
    public WalletDto getById(Long id) throws WalletDaoException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_WALLET_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WalletEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get wallet by id: " + id);
            throw new WalletDaoException("Failed to get wallet by id: " + id, e);
        }
        Optional<WalletDto> optionalWallet = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalWallet.isPresent()) {
            log.error("Failed to get wallet by id: " + id);
            throw new WalletDaoException("Failed to get wallet by id: " + id);
        }
        return optionalWallet.get();
    }

    @Override
    public List<WalletDto> findAll() throws WalletDaoException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    WalletEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all wallets");
            throw new WalletDaoException("Failed to find all wallets");
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private WalletEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("wallet_id");
        double amount = resultSet.getDouble("wallet_amount");
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("wallet_name");
        return WalletEntity.builder()
                .id(entityId)
                .name(name)
                .amount(amount)
                .userId(userId)
                .build();
    }

    private WalletEntity userEntityFromDto(WalletDto dto) {
        WalletEntity entity = new WalletEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAmount(dto.getAmount());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    private WalletDto fromEntity(WalletEntity entity) {
        WalletDto dto = new WalletDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAmount(entity.getAmount());
        dto.setUserId(entity.getUserId());
        return dto;
    }
}
