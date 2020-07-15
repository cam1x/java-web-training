package by.training.role;

import by.training.dao.CRUDDao;

import java.util.List;

public interface RoleDao extends CRUDDao<RoleDto, Long> {
    void assignDefaultRoles(Long userId) throws RoleDaoException;

    void assignRole(Long userId, Long roleId) throws RoleDaoException;

    boolean deleteUserRole(Long userId, Long roleId) throws RoleDaoException;

    List<RoleDto> findUserRoles(Long userId) throws RoleDaoException;
}
