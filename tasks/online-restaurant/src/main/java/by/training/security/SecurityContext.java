package by.training.security;

import by.training.role.RoleDto;
import by.training.user.UserDto;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static by.training.application.ApplicationConstants.ALL_COMMANDS;

@Log4j
public class SecurityContext {
    private static final Lock singletonLock = new ReentrantLock();
    private static final String DELIMITER = "\\s*\\,\\s*";
    private static final String ALLOWED_TO_ALL_USERS_SYMBOL = "*";
    private static final String IS_ALLOWED_TO_ANONYMOUS_PROPERTY = "command.{0}.allowAnonymous";
    private static final String ALLOWED_ROLES_FOR_CMD = "command.{0}.roles";
    private static SecurityContext instance;
    private Map<String, Set<String>> commandPermission = new HashMap<>();

    private SecurityContext() {
        Properties property = new Properties();
        try {
            FileReader propertyFile = new FileReader(new File(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource("commandPermission.properties")).getFile()));
            property.load(propertyFile);
            for (String command : ALL_COMMANDS) {
                String allowedToAnonymous =
                        property.getProperty(MessageFormat.format(IS_ALLOWED_TO_ANONYMOUS_PROPERTY, command));
                if (allowedToAnonymous.equals("true")) {
                    commandPermission.put(command, Collections.singleton(ALLOWED_TO_ALL_USERS_SYMBOL));
                } else {
                    String[] allowedRoles = property.getProperty(MessageFormat.format(ALLOWED_ROLES_FOR_CMD, command))
                            .split(DELIMITER);
                    Set<String> allowedRolesSet = Arrays.stream(allowedRoles).collect(Collectors.toSet());
                    commandPermission.put(command, allowedRolesSet);
                }
            }
        } catch (IOException | NullPointerException e) {
            throw new SecurityContextException("Failed to initialise security context", e);
        }
    }

    public static SecurityContext getInstance() {
        if (instance == null) {
            singletonLock.lock();
            try {
                if (instance == null) {
                    instance = new SecurityContext();
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return instance;
    }

    //To anonymous
    public boolean canExecute(String commandName) {
        if (!commandPermission.containsKey(commandName)) {
            return false;
        }
        Set<String> allowedRoles = commandPermission.get(commandName);
        return (allowedRoles.contains(ALLOWED_TO_ALL_USERS_SYMBOL) && allowedRoles.size() == 1);
    }

    //To logged
    public boolean canExecute(String commandName, UserDto userDto) {
        if (!SecurityService.isLogged(userDto)) {
            return canExecute(commandName);
        }
        List<String> userRoles = userDto.getRoles()
                .stream()
                .map(RoleDto::getRole)
                .collect(Collectors.toList());
        return isCommandAllowed(commandName, userRoles);
    }

    private boolean isCommandAllowed(String commandName, List<String> roleList) {
        if (!commandPermission.containsKey(commandName)) {
            return false;
        }
        Set<String> allowedRoles = commandPermission.get(commandName);
        return (allowedRoles.contains(ALLOWED_TO_ALL_USERS_SYMBOL) && allowedRoles.size() == 1) ||
                roleList.stream().anyMatch(allowedRoles::contains);
    }
}