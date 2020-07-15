package by.training.application;

import java.util.HashSet;
import java.util.Set;

public class ApplicationConstants {
    //Roles
    public static final String DEFAULT_ROLE = "customer";
    public static final String ADMIN_ROLE = "admin";
    public static final String MANAGER_ROLE = "manager";
    //Page attributes
    public static final String AVATAR_ATTRIBUTE = "user.avatar";
    public static final String LOGIN_ATTRIBUTE = "user.login";
    public static final String PASSWORD_ATTRIBUTE = "user.password";
    public static final String REPEAT_PASSWORD_ATTRIBUTE = "user.repeatPassword";
    public static final String FIRST_NAME_ATTRIBUTE = "contact.firstName";
    public static final String LAST_NAME_ATTRIBUTE = "contact.lastName";
    public static final String EMAIL_ATTRIBUTE = "contact.email";
    public static final String PHONE_ATTRIBUTE = "contact.phone";
    public static final String DISH_NAME_ATTRIBUTE = "dish.name";
    public static final String DISH_PRICE_ATTRIBUTE = "dish.price";
    public static final String DISH_DESCRIPTION_ATTRIBUTE = "dish.description";
    public static final String DISH_PHOTO_ATTRIBUTE = "dish.photo";
    //Commands
    public static final String ACTION_PROHIBITED_CMD_NAME = "actionProhibited";
    public static final String DELETE_CMD_NAME = "deleteUser";
    public static final String LOGIN_CMD_NAME = "loginUser";
    public static final String LOGIN_VIEW_CMD_NAME = "loginUserView";
    public static final String LOGOUT_CND_NAME = "logoutUser";
    public static final String LOGIN_SUCCESS_CMD_NAME = "loginSuccess";
    public static final String REGISTER_VIEW_CMD_NAME = "registerUserView";
    public static final String REGISTER_SAVE_CMD_NAME = "registerUserSave";
    public static final String VIEW_ALL_USERS_CMD_NAME = "viewAllUsers";
    public static final String VIEW_USER_PROFILE_CMD_NAME = "viewProfile";
    public static final String CHANGE_USER_AVATAR_CMD_NAME = "changeAvatar";
    public static final String LOCK_USER_CMD_NAME = "lockUser";
    public static final String UNLOCK_USER_CMD_NAME = "unlockUser";
    public static final String EDIT_USER_ROLES_CMD_NAME = "editUser";
    public static final String SHOW_MENU_CMD_NAME = "restaurantMenu";
    public static final String SAVE_DISH_CMD_NAME = "saveDish";
    public static final String EDIT_DISH_CMD_NAME = "editDish";
    public static final String EDIT_DISH_QUANTITY_CMD_NAME = "editDishQuantity";
    public static final String DELETE_DISH_CMD_NAME = "deleteDish";
    public static final String SHOW_DISH_DETAILS_CMD_NAME = "dishDetails";
    public static final String SAVE_CONTACT_CMD_NAME = "saveContact";
    public static final String DELETE_CONTACT_CMD_NAME = "deleteContact";
    public static final String EDIT_CONTACT_CMD_NAME = "editContact";
    public static final String SAVE_WALLET_CMD_NAME = "saveWallet";
    public static final String DELETE_WALLET_CMD_NAME = "deleteWallet";
    public static final String EDIT_WALLET_CMD_NAME = "editWallet";
    public static final String VIEW_ORDER_HISTORY_CMD_NAME = "viewHistory";
    public static final String VIEW_SHOPPING_BASKET_CMD_NAME = "viewOrderBasket";
    public static final String ADD_DISH_TO_ORDER_CMD_NAME = "addDishToOrder";
    public static final String DELETE_DISH_FROM_ORDER_CMD_NAME = "deleteDishFromOrder";
    public static final String CONFIRM_ORDER_CMD_NAME = "confirmOrder";
    public static final String DELETE_ORDER_CMD_NAME = "deleteOrder";
    public static final String VIEW_ALL_ORDERS_CMD_NAME = "viewAllOrders";
    public static final String ORDER_NEXT_STATUS_CMD_NAME = "nextStatus";
    public static final String ORDER_PREV_STATUS_CMD_NAME = "prevStatus";
    public static final String ORDER_SUCCESS_CMD_NAME = "orderSuccess";
    public static final String ORDER_DETAILS_CMD_NAME = "orderDetails";
    public static final String EDIT_DISCOUNT_CMD_NAME = "editDiscount";
    public static final String CMD_REQ_PARAMETER = "commandName";
    public static final String VIEW_NAME_REQ_PARAMETER = "viewName";
    public static final Set<String> ALL_COMMANDS = new HashSet<>();

    static {
        ALL_COMMANDS.add(LOGIN_CMD_NAME);
        ALL_COMMANDS.add(LOGIN_VIEW_CMD_NAME);
        ALL_COMMANDS.add(LOGOUT_CND_NAME);
        ALL_COMMANDS.add(REGISTER_VIEW_CMD_NAME);
        ALL_COMMANDS.add(REGISTER_SAVE_CMD_NAME);
        ALL_COMMANDS.add(VIEW_ALL_USERS_CMD_NAME);
        ALL_COMMANDS.add(DELETE_CMD_NAME);
        ALL_COMMANDS.add(LOCK_USER_CMD_NAME);
        ALL_COMMANDS.add(UNLOCK_USER_CMD_NAME);
        ALL_COMMANDS.add(EDIT_USER_ROLES_CMD_NAME);
        ALL_COMMANDS.add(SHOW_MENU_CMD_NAME);
        ALL_COMMANDS.add(SAVE_DISH_CMD_NAME);
        ALL_COMMANDS.add(DELETE_DISH_CMD_NAME);
        ALL_COMMANDS.add(SHOW_DISH_DETAILS_CMD_NAME);
        ALL_COMMANDS.add(VIEW_USER_PROFILE_CMD_NAME);
        ALL_COMMANDS.add(CHANGE_USER_AVATAR_CMD_NAME);
        ALL_COMMANDS.add(DELETE_CONTACT_CMD_NAME);
        ALL_COMMANDS.add(EDIT_WALLET_CMD_NAME);
        ALL_COMMANDS.add(SAVE_WALLET_CMD_NAME);
        ALL_COMMANDS.add(DELETE_WALLET_CMD_NAME);
        ALL_COMMANDS.add(SAVE_CONTACT_CMD_NAME);
        ALL_COMMANDS.add(EDIT_CONTACT_CMD_NAME);
        ALL_COMMANDS.add(VIEW_ORDER_HISTORY_CMD_NAME);
        ALL_COMMANDS.add(ADD_DISH_TO_ORDER_CMD_NAME);
        ALL_COMMANDS.add(VIEW_SHOPPING_BASKET_CMD_NAME);
        ALL_COMMANDS.add(DELETE_DISH_FROM_ORDER_CMD_NAME);
        ALL_COMMANDS.add(EDIT_DISH_CMD_NAME);
        ALL_COMMANDS.add(EDIT_DISH_QUANTITY_CMD_NAME);
        ALL_COMMANDS.add(CONFIRM_ORDER_CMD_NAME);
        ALL_COMMANDS.add(DELETE_ORDER_CMD_NAME);
        ALL_COMMANDS.add(ACTION_PROHIBITED_CMD_NAME);
        ALL_COMMANDS.add(VIEW_ALL_ORDERS_CMD_NAME);
        ALL_COMMANDS.add(ORDER_NEXT_STATUS_CMD_NAME);
        ALL_COMMANDS.add(ORDER_PREV_STATUS_CMD_NAME);
        ALL_COMMANDS.add(ORDER_SUCCESS_CMD_NAME);
        ALL_COMMANDS.add(ORDER_DETAILS_CMD_NAME);
        ALL_COMMANDS.add(LOGIN_SUCCESS_CMD_NAME);
        ALL_COMMANDS.add(EDIT_DISCOUNT_CMD_NAME);
    }
}
