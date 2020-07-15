package by.training.command;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ServletCommandType {
    DEFAULT("default", "all"),
    LOGIN("loginUser", "all"),
    LOGOUT("logoutUser", "all"),
    VIEW_USER_LIST("viewUserList", "admin"),
    REGISTER("registerUser", "all");

    private String name;
    private Set<String> roles;

    ServletCommandType(String name, String... roles) {
        this.name = name;
        this.roles = Arrays.stream(roles).collect(Collectors.toSet());
    }
}
