package by.training.util;

import by.training.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SelfOperationManager {
    public static boolean isSelfOperation(HttpServletRequest req) {
        String userIdString = req.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserDto admin = (UserDto) session.getAttribute("user");
            return admin.getUserId() == userId;
        }
        return false;
    }
}