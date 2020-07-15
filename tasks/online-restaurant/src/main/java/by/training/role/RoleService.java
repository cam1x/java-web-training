package by.training.role;

import java.util.List;

public interface RoleService {
    boolean deleteRole(Long roleId) throws RoleServiceException;

    boolean deleteAllUserRoles(Long userId) throws RoleServiceException;

    boolean deleteUserRole(Long userId, Long roleId) throws RoleServiceException;

    List<RoleDto> findUserRoles(Long id) throws RoleServiceException;

    void assignRoles(Long userId, Long... rolesId) throws RoleServiceException;

    void assignDefaultRoles(Long userId) throws RoleServiceException;

    List<RoleDto> findAllRoles() throws RoleServiceException;

    boolean saveRole(RoleDto role) throws RoleServiceException;

    RoleDto getRole(Long id) throws RoleServiceException;
}