package by.training.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean loginUser(UserDto userDto) throws UserServiceException;

    Optional<UserDto> findByLogin(String login) throws UserServiceException;

    boolean registerUser(UserDto userDto) throws UserServiceException;

    boolean updateUser(UserDto userDto) throws UserServiceException;

    boolean delete(Long userId) throws UserServiceException;

    boolean isLoginTaken(String login) throws UserServiceException;

    UserDto getById(Long id) throws UserServiceException;

    List<UserDto> getAllUsers() throws UserServiceException;
}