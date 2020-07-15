package by.training.role;

import by.training.core.Bean;
import by.training.dao.ConnectionManager;
import by.training.entity.RoleEntity;
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
public class RoleDaoImpl implements RoleDao {
    private static final String SELECT_DEFAULT_ROLES_QUERY = "select role_id, role, default_role from user_role where default_role!=0";
    private static final String ASSIGN_ROLE_QUERY = "insert into user_role_relation (user_id, role_id) values (?, ?);";

    private static final String SELECT_ALL_USER_ROLES_QUERY = "select rel.user_id, rel.role_id, role, default_role " +
            "from user_role_relation rel inner join user_role " +
            "on rel.role_id=user_role.role_id where user_id = ?";
    private static final String SELECT_ALL_ROLES_QUERY = "select role_id, role, default_role from user_role";
    private static final String SELECT_ROLE_BY_ID_QUERY = "select role_id, role, default_role from user_role where role_id = ?";
    private static final String INSERT_ROLE_QUERY = "insert into user_role(role, default_role) values(?, ?)";
    private static final String UPDATE_ROLE_QUERY = "update user_role set role=?, default_role=? where role_id=?";
    private static final String DELETE_ROLE_QUERY = "delete from user_role where role_id=?";
    private static final String DELETE_USER_ROLE_QUERY = "delete from user_role_relation where role_id=? and user_id=?";

    private ConnectionManager connectionManager;

    @Override
    public void assignDefaultRoles(Long userId) throws RoleDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_DEFAULT_ROLES_QUERY)) {
            final ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                final long roleId = resultSet.getLong(1);
                assignRole(userId, roleId, connection);
            }
        } catch (SQLException e) {
            log.error("Failed to assign default role to user with id = " + userId);
            throw new RoleDaoException("Failed to assign default role to user with id = " + userId, e);
        }
    }

    @Override
    public void assignRole(Long userId, Long roleId) throws RoleDaoException {
        try {
            this.assignRole(userId, roleId, connectionManager.getConnection());
        } catch (SQLException e) {
            log.error("Failed to assign relation(userId - roleId): " + userId + "-" + roleId);
            throw new RoleDaoException("Failed to assign relation(userId - roleId): " + userId + "-" + roleId, e);
        }
    }

    @Override
    public boolean deleteUserRole(Long userId, Long roleId) throws RoleDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_USER_ROLE_QUERY)) {
            int i = 0;
            updateStmt.setLong(++i, roleId);
            updateStmt.setLong(++i, userId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete relation(userId - roleId): " + userId + "-" + roleId);
            throw new RoleDaoException("Failed to delete relation(userId - roleId): " + userId + "-" + roleId, e);
        }
    }

    @Override
    public List<RoleDto> findUserRoles(Long userId) throws RoleDaoException {
        List<RoleEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_USER_ROLES_QUERY)) {
            selectStmt.setLong(1, userId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RoleEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.warn("Failed to get user (id=" + userId + ") roles");
            throw new RoleDaoException("Failed to get user (id=" + userId + ") roles", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(RoleDto roleDto) throws RoleDaoException {
        RoleEntity roleEntity = userRoleFromDto(roleDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_ROLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, roleEntity.getRole());
            insertStmt.setInt(++i, (roleEntity.isDefaultRole()) ? 1 : 0);
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    roleEntity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save role: " + roleEntity);
            throw new RoleDaoException("Failed to save role: " + roleEntity, e);
        }
        return roleEntity.getId();
    }

    @Override
    public boolean update(RoleDto roleDto) throws RoleDaoException {
        RoleEntity roleEntity = userRoleFromDto(roleDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_ROLE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, roleEntity.getRole());
            updateStmt.setInt(++i, (roleEntity.isDefaultRole()) ? 1 : 0);
            updateStmt.setLong(++i, roleEntity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update role: " + roleEntity);
            throw new RoleDaoException("Failed to update role: " + roleEntity, e);
        }
    }

    @Override
    public boolean delete(Long roleId) throws RoleDaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_ROLE_QUERY)) {
            int i = 0;
            updateStmt.setLong(++i, roleId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete role with id: " + roleId);
            throw new RoleDaoException("Failed to delete role with id: " + roleId, e);
        }
    }

    @Override
    public RoleDto getById(Long id) throws RoleDaoException {
        List<RoleEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ROLE_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RoleEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get role by id: " + id);
            throw new RoleDaoException("Failed to get role by id: " + id, e);
        }
        Optional<RoleDto> optionalRole = result.stream()
                .map(this::fromEntity)
                .findFirst();
        if (!optionalRole.isPresent()) {
            log.error("Failed to get role by id: " + id);
            throw new RoleDaoException("Failed to get role by id: " + id);
        }
        return optionalRole.get();
    }

    @Override
    public List<RoleDto> findAll() throws RoleDaoException {
        List<RoleEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_ROLES_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RoleEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all users");
            throw new RoleDaoException("Failed to find all users", e);
        }
        return result.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private void assignRole(Long userId, Long roleId, Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(ASSIGN_ROLE_QUERY)) {
            int i = 0;
            stmt.setLong(++i, userId);
            stmt.setLong(++i, roleId);
            stmt.executeUpdate();
        }
    }

    private RoleEntity userRoleFromDto(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleDto.getId());
        roleEntity.setRole(roleDto.getRole());
        return roleEntity;
    }

    private RoleEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("role_id");
        String role = resultSet.getString("role");
        boolean defaultRole = resultSet.getBoolean("default_role");
        return RoleEntity.builder()
                .id(entityId)
                .role(role)
                .defaultRole(defaultRole)
                .build();
    }

    private RoleDto fromEntity(RoleEntity entity) {
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setDefaultRole(entity.isDefaultRole());
        return dto;
    }
}