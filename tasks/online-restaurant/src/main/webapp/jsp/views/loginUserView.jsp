<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.application.ApplicationConstants" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/error.css">
</head>

<div class="authorization centered-container">
    <div class="mdl-cell mdl-cell--1-col">
    </div>
    <div class="mdl-cell mdl-cell--8-col">
        <img src="${pageContext.request.contextPath}/static/image/avatar.jpg" class="avatar">
        <h1>
            <fmt:message key="links.login"/>
        </h1>
        <form action="${pageContext.request.contextPath}/" method="POST">
            <input type="hidden" value="loginUser" name="commandName"/>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="login" beanName="user"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="password" beanName="user" password="true"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col">
                    <input class="mdl-button mdl-js-button mdl-button--raised" type="submit"
                           value="<fmt:message key="login.action"/> "/>
                </div>
            </div>
            <a class="mdl-navigation__link"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.REGISTER_VIEW_CMD_NAME}">
                <fmt:message key="links.registerFromLogin"/>
            </a>
            <c:if test="${loginFailed eq 'true'}">
                <h6 class="error-text">
                    <fmt:message key="error.loginFailed"/>
                </h6>
            </c:if>
        </form>
    </div>
</div>