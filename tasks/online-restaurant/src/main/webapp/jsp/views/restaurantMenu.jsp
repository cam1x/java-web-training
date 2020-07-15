<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>
<%@ page import="by.training.application.ApplicationConstants" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/menu.css">
</head>

<div class="mdl-grid">
    <c:set var="isManager" value="false"/>
    <c:forEach items="${pageContext.session.getAttribute('user').roles}" var="userRole">
        <c:if test="${userRole.role eq ApplicationConstants.MANAGER_ROLE}">
            <c:set var="isManager" value="true"/>
        </c:if>
    </c:forEach>
    <div class="mdl-cell mdl-cell--10-col">
        <c:choose>
            <c:when test="${not empty dishes}">
                <c:set var="isLogged" value="${not empty pageContext.session.getAttribute('user')}"/>
                <div class="mdl-grid centered-container">
                    <c:if test="${isLogged eq 'false'}">
                        <div class="mdl-cell mdl-cell--10-col">
                            <a class="mdl-navigation__link redirect-link"
                               href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.LOGIN_VIEW_CMD_NAME}">
                                <fmt:message key="links.loginToBuy"/>
                            </a>
                        </div>
                    </c:if>
                    <c:forEach items="${dishes}" var="_dish">
                        <div class="mdl-cell mdl-cell--4-col">
                            <div class="mdl-card mdl-shadow--2dp">
                                <div class="mdl-card__title">
                                    <h2 class="mdl-card__title-text">
                                            ${_dish.name}
                                    </h2>
                                </div>
                                <div class="mdl-card__media">
                                    <img src="data:image/jpg;base64,${_dish.getBase64Encoded()}"
                                         alt="${_dish.name}" class="center">
                                </div>
                                <div class="mdl-card__supporting-text">
                                    <c:set var="visibleDescription" value="${_dish.description}"/>
                                    <c:if test="${visibleDescription.length()>40}">
                                        <c:set var="visibleDescription"
                                               value="${visibleDescription.substring(0, 40)}..."/>
                                    </c:if>
                                    <jsp:text>${visibleDescription}</jsp:text>
                                </div>
                                <div class="mdl-card__actions mdl-card--border">
                                    <form action="${pageContext.request.contextPath}/" method="GET">
                                        <input type="hidden" name="dishId" value="${_dish.id}"/>
                                        <input type="hidden" name="commandName" value="dishDetails"/>
                                        <input class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                                               type="submit" value="<fmt:message key="links.details"/> "/>
                                    </form>
                                    <c:if test="${isLogged eq 'true'}">
                                        <form action="${pageContext.request.contextPath}/" method="POST">
                                            <input type="hidden" name="dishId" value="${_dish.id}"/>
                                            <input type="hidden" name="commandName" value="addDishToOrder"/>
                                            <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
                                                <i type="submit" class="material-icons">shopping_cart</i>
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                                <div class="mdl-card__menu">
                                    <c:if test="${isManager eq 'true'}">
                                        <form action="${pageContext.request.contextPath}/" method="POST">
                                            <input type="hidden" name="commandName" value="deleteDish"/>
                                            <input type="hidden" name="dishId" value="${_dish.id}"/>
                                            <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
                                                <i type="submit" class="material-icons">delete</i>
                                            </button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/" method="POST"
                                              enctype="multipart/form-data">
                                            <input type="hidden" name="commandName" value="editDish"/>
                                            <input type="hidden" name="dishId" value="${_dish.id}"/>
                                            <button type="button"
                                                    class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect"
                                                    onclick="openDlg(${dishes.indexOf(_dish)})">
                                                <i class="material-icons">edit</i>
                                            </button>
                                            <edit:edit-dish _dish="${_dish}" index="${dishes.indexOf(_dish)}"/>
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="centered-text">
                    <h1>
                        Menu will be added soon
                    </h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="mdl-cell mdl-cell--2-col">
        <c:if test="${isManager eq 'true'}">
            <form action="${pageContext.request.contextPath}/" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="commandName" value="saveDish"/>
                <button class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored fixed-btn"
                        onclick="openDlg(${dishes.size()})" type="button">
                    <i class="material-icons">add</i>
                </button>
                <edit:add-dish index="${dishes.size()}"/>
            </form>
        </c:if>
    </div>
</div>