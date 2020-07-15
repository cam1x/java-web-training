package by.training.role;

import by.training.core.Bean;
import by.training.dao.DaoException;
import by.training.dao.TransactionSupport;
import by.training.dao.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Bean
@Log4j
@TransactionSupport
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Override
    public boolean deleteRole(Long roleId) throws RoleServiceException {
        try {
            return roleDao.delete(roleId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteAllUserRoles(Long userId) throws RoleServiceException {
        try {
            List<RoleDto> userRoles = roleDao.findUserRoles(userId);
            boolean isSuccess = true;
            for (RoleDto userRole : userRoles) {
                isSuccess &= roleDao.deleteUserRole(userId, userRole.getId());
            }
            return isSuccess;
        } catch (RoleDaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotDeletedException(e);
        }
    }

    @Override
    public boolean deleteUserRole(Long userId, Long roleId) throws RoleServiceException {
        try {
            return roleDao.deleteUserRole(userId, roleId);
        } catch (RoleDaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotDeletedException(e);
        }
    }

    @Override
    @Transactional
    public List<RoleDto> findUserRoles(Long id) throws RoleNotFoundException {
        try {
            return roleDao.findUserRoles(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotFoundException(e);
        }
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, Long... rolesId) throws RoleServiceException {
        try {
            for (Long roleId : rolesId) {
                roleDao.assignRole(userId, roleId);
            }
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotAssignedException(e);
        }
    }

    @Override
    public void assignDefaultRoles(Long userId) throws RoleServiceException {
        try {
            roleDao.assignDefaultRoles(userId);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotAssignedException(e);
        }
    }

    @Override
    @Transactional
    public List<RoleDto> findAllRoles() throws RoleServiceException {
        try {
            return roleDao.findAll();
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotFoundException(e);
        }
    }

    @Override
    public boolean saveRole(RoleDto role) throws RoleServiceException {
        try {
            return roleDao.save(role) != null;
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotSavedException(e);
        }
    }

    @Override
    public RoleDto getRole(Long id) throws RoleServiceException {
        try {
            return roleDao.getById(id);
        } catch (DaoException e) {
            log.error(e.getMessage(), e);
            throw new RoleNotFoundException(e);
        }
    }
}