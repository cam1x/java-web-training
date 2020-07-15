package by.training.user;

import by.training.dao.CRUDDao;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao extends CRUDDao<UserDto, Long> {
    Optional<UserDto> findByLogin(String login) throws UserDaoException;
    boolean isLoginTaken(String login) throws UserDaoException;
}