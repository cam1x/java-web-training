<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.application.ApplicationConstants" %>

<style>
    .mdl-menu__item {
        color: honeydew;
    }

    .mdl-menu {
        background-color: #3b3b3b;
    }

    .mdl-menu__item:hover {
        background-color: #737373;
    }
</style>

<button id="demo-menu-lower-right"
        class="mdl-button mdl-js-button mdl-button--icon">
    <i class="material-icons">more_vert</i>
</button>
<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
    for="demo-menu-lower-right">
    <c:set var="isAdmin" value="false"/>
    <c:forEach items="${pageContext.session.getAttribute('user').roles}" var="userRole">
        <c:if test="${userRole.role eq ApplicationConstants.ADMIN_ROLE}">
            <c:set var="isAdmin" value="true"/>
        </c:if>
    </c:forEach>
    <c:if test="${isAdmin eq 'true'}">
        <a class="mdl-menu__item"
           href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_ALL_USERS_CMD_NAME}">
            <fmt:message key="links.viewAllUsers"/>
        </a>
    </c:if>
    <c:set var="isManager" value="false"/>
    <c:forEach items="${pageContext.session.getAttribute('user').roles}" var="userRole">
        <c:if test="${userRole.role eq ApplicationConstants.MANAGER_ROLE}">
            <c:set var="isManager" value="true"/>
        </c:if>
    </c:forEach>
    <c:if test="${isManager eq 'true'}">
        <a class="mdl-menu__item"
           href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_ALL_ORDERS_CMD_NAME}">
            <fmt:message key="links.viewAllOrders"/>
        </a>
    </c:if>
    <c:choose>
        <c:when test="${empty pageContext.session.getAttribute('user')}">
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.REGISTER_VIEW_CMD_NAME}">
                <fmt:message key="links.register"/>
            </a>
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.LOGIN_VIEW_CMD_NAME}">
                <fmt:message key="links.login"/>
            </a>
        </c:when>
        <c:otherwise>
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME}">
                <fmt:message key="links.profile"/>
            </a>
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_SHOPPING_BASKET_CMD_NAME}">
                <fmt:message key="links.orderBasket"/>
            </a>
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_ORDER_HISTORY_CMD_NAME}">
                <fmt:message key="links.orderHistory"/>
            </a>
            <a class="mdl-menu__item"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.LOGOUT_CND_NAME}">
                <fmt:message key="links.logout"/>
            </a>
        </c:otherwise>
    </c:choose>
</ul>