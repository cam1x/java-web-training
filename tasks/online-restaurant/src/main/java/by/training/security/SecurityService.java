package by.training.security;

import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.user.UserServiceException;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.application.ApplicationConstants.LOGIN_ATTRIBUTE;
import static by.training.application.ApplicationConstants.PASSWORD_ATTRIBUTE;

@Log4j
public class SecurityService {
    private static final Lock SINGLETON_LOCK = new ReentrantLock();
    private static SecurityService instance;
    private static Map<Long, List<HttpSession>> loggedUsers = new ConcurrentHashMap<>();
    private UserService userService;
    private CryptographyManager cryptographyManager;

    private SecurityService(UserService userService) {
        this.userService = userService;
        this.cryptographyManager = new CryptographyManagerImpl();
    }

    public static SecurityService getInstance(UserService userService) {
        if (instance == null) {
            SINGLETON_LOCK.lock();
            try {
                if (instance == null) {
                    instance = new SecurityService(userService);
                }
            } finally {
                SINGLETON_LOCK.unlock();
            }
        }
        return instance;
    }

    public static boolean isLogged(UserDto userDto) {
        return (userDto != null) && loggedUsers.containsKey(userDto.getUserId());
    }

    public Long login(HttpServletRequest request) throws UserServiceException {
        final String login = request.getParameter(LOGIN_ATTRIBUTE);
        final String password = request.getParameter(PASSWORD_ATTRIBUTE);

        UserDto dto = new UserDto();
        dto.setPassword(password);
        dto.setLogin(login);

        Optional<UserDto> user = userService.findByLogin(login);
        boolean isValidToLog = user.isPresent() &&
                password.equals(cryptographyManager.decrypt(user.get().getPassword()));

        UserDto userDto = null;
        if (isValidToLog) {
            userDto = user.get();
            long userId = userDto.getUserId();
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userDto);
            if (loggedUsers.containsKey(userId)) {
                loggedUsers.get(userId).add(session);
            } else {
                loggedUsers.put(userId, new ArrayList<>(Collections.singletonList(session)));
            }
        }

        return (userDto == null) ? null : userDto.getUserId();
    }

    public boolean logout(UserDto userDto) {
        long userId = userDto.getUserId();
        if (loggedUsers.containsKey(userId)) {
            List<HttpSession> sessions = loggedUsers.get(userId);
            try {
                sessions.forEach(HttpSession::invalidate);
                loggedUsers.remove(userId);
                return true;
            } catch (IllegalStateException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        }
        return false;
    }
}