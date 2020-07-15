<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>
<%@ page import="by.training.application.ApplicationConstants" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/basket.css">
    <style>
        .mdl-button{
            color: #d6e9a6;
        }
    </style>
</head>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--1-col">
    </div>
    <c:choose>
        <c:when test="${not empty orders}">
            <div class="mdl-cell mdl-cell--8-col">
                <h1 class="block-header">
                    <fmt:message key="table.historyHeader"/>
                </h1>
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="order.date"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="order.bookingDate"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="order.status"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="order.totalPrice"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="table.actions"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orders}" var="_order">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">
                                <c:out value="${_order.orderDate}"/>
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <c:out value="${_order.bookingDate}"/>
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <c:out value="${_order.status}"/>
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">
                                <c:out value="${_order.totalPrice}"/>
                            </td>

                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="orderId" value="${_order.id}"/>
                                    <input type="hidden" name="commandName" value="deleteOrder"/>
                                    <input class="mdl-button mdl-js-button" type="submit"
                                           value="<fmt:message key="table.deleteOrder"/>"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="mdl-grid centered-container">
                <div class="mdl-cell mdl-cell--8-col" style="position: fixed">
                    <h1 class="inline-tittle-text">
                        <fmt:message key="order.totalPrice"/>
                        <span class="inline-content-text">
                                            <c:out value="${totalPrice}$"/></span>
                    </h1>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="mdl-cell mdl-cell--8-col centered-text">
                <h1>
                    <fmt:message key="orderHistory.empty"/>
                </h1>
                <a class="mdl-navigation__link"
                   href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.SHOW_MENU_CMD_NAME}">
                    <fmt:message key="links.menu"/>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="mdl-cell mdl-cell--1-col">
    </div>
</div>