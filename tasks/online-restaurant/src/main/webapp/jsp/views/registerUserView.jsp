<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <div class="mdl-cell mdl-cell--10-col">
        <img src="${pageContext.request.contextPath}/static/image/avatar.jpg" class="avatar">
        <h1>
            <fmt:message key="links.register"/>
        </h1>
        <form action="${pageContext.request.contextPath}/" method="POST">
            <input type="hidden" value="registerUserSave" name="commandName"/>
            <c:set var="_contact" value="${user.contacts.get(0)}"/>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="firstName" beanName="contact" _pattern="[a-zа-яА-ЯA-Z]+"
                                     _patternNotMatchingMsg="onlyLetters"
                                     value="${_contact.firstName}"/>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="lastName" beanName="contact" _pattern="[a-zа-яА-ЯA-Z]+"
                                     _patternNotMatchingMsg="onlyLetters"
                                     value="${_contact.lastName}"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="email" beanName="contact"
                                     _pattern="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
                                     _patternNotMatchingMsg="emailFormat"
                                     value="${_contact.email}"/>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="phone" beanName="contact" _pattern="^\+(?:[0-9] ?){6,14}[0-9]$"
                                     _patternNotMatchingMsg="phoneFormat" value="${_contact.phone}"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="password" beanName="user" password="true"
                                     validationErrors="${errors.errors['user.password']}"/>
                    </div>
                </div>
                <div class="mdl-cell mdl-cell--6-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="repeatPassword" beanName="user" password="true"
                                     validationErrors="${errors.errors['user.password']}"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid centered-container">
                <div class="mdl-cell mdl-cell--8-col">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input:input fieldName="login" beanName="user"
                                     validationErrors="${errors.errors['user.login']}" value="${user.login}"/>
                    </div>
                </div>
            </div>
            <div class="mdl-grid centered-container">
                <div class="mdl-cell mdl-cell--8-col">
                    <input class="mdl-button mdl-js-button mdl-button--raised" type="submit"
                           value="<fmt:message key="registration.action"/>"/>
                </div>
            </div>
            <a class="mdl-navigation__link"
               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.LOGIN_VIEW_CMD_NAME}">
                <fmt:message key="links.loginFromRegister"/>
            </a>
            <c:set var="_generalErrors" value="${errors.errors['general']}"/>
            <c:choose>
                <c:when test="${not empty _generalErrors}">
                    <c:forEach var="error" items="${_generalErrors}">
                        <h6 class="error-text">
                            <fmt:message key="error.${error}"/>
                        </h6>
                    </c:forEach>
                </c:when>
            </c:choose>
        </form>
    </div>
</div>