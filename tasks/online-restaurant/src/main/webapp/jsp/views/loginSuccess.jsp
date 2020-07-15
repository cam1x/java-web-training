<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.application.ApplicationConstants" %>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--8-col">
        <div class="centered-text">
            <h1>
                <fmt:message key="login.success"/>
            </h1>
            <a class="mdl-navigation__link"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.SHOW_MENU_CMD_NAME}">
                <fmt:message key="links.menu"/>
            </a>
        </div>
    </div>
</div>