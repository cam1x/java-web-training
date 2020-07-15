<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.training.application.ApplicationConstants" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/error.css">
    <link rel="stylesheet" type="text/css" href="static/style/basket.css">
</head>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--1-col">
    </div>
    <c:set var="orderDishes" value="${pageContext.session.getAttribute('order').orderDishes}"/>
    <c:set var="totalPrice" value="${pageContext.session.getAttribute('order').totalPrice}"/>
    <c:set var="wallets" value="${pageContext.session.getAttribute('user').wallets}"/>
    <c:choose>
        <c:when test="${not empty orderDishes}">
            <div class="mdl-cell mdl-cell--8-col">
                <c:if test="${not empty error}">
                    <h6 class="error-text">
                        <fmt:message key="error.${error}"/>
                    </h6>
                </c:if>
                <h1 class="block-header">
                    <fmt:message key="table.dishesHeader"/>
                </h1>
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="dish.name"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="dish.quantity"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="dish.price"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric" colspan=2>
                            <fmt:message key="table.actions"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderDishes}" var="_dish">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">${_dish.name}</td>
                            <td class="mdl-data-table__cell--non-numeric">${_dish.quantity}</td>
                            <td class="mdl-data-table__cell--non-numeric">${_dish.price}</td>

                            <td class="mdl-data-table__cell--non-numeric">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="dishId" value="${_dish.id}"/>
                                    <input type="hidden" name="commandName" value="deleteDishFromOrder"/>
                                    <input class="mdl-button mdl-js-button" type="submit"
                                           value="<fmt:message key="table.deleteDish"/>"/>
                                </form>
                            </td>

                            <td>
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="dishId" value="${_dish.id}"/>
                                    <input type="hidden" name="commandName" value="editDishQuantity"/>
                                    <button type="button" class="mdl-button"
                                            onclick="openDlg(${orderDishes.indexOf(_dish)})">
                                        <fmt:message key="table.editDishQuantity"/>
                                    </button>
                                    <edit:edit-dish-quantity _dish="${_dish}" index="${orderDishes.indexOf(_dish)}"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="mdl-grid centered-container">
                    <div class="mdl-cell mdl-cell--8-col">
                        <form action="${pageContext.request.contextPath}/" method="POST">
                            <div class="mdl-grid">
                                <h1 class="block-header">
                                    <fmt:message key="order.check"/>
                                </h1>
                                <div class="block-wrapper mdl-cell mdl-cell--12-col centered-container">
                                    <div class="mdl-cell mdl-cell--5-col">
                                        <h1 class="inline-tittle-text">
                                            <fmt:message key="order.totalPrice"/>
                                            <span class="inline-content-text">
                                            <c:out value="${totalPrice}$"/></span>
                                        </h1>
                                    </div>
                                    <div class="mdl-cell mdl-cell--5-col">
                                        <h1 class="inline-tittle-text">
                                            <fmt:message key="order.chooseWallet"/>
                                            <span>
                                                <label>
                                                    <select class="wallet-selection" name="paymentWallet">
                                                        <c:forEach var="wallet" items="${wallets}">
                                                            <option value="${wallet.name}">${wallet.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </label>
                                            </span>
                                        </h1>
                                    </div>
                                    <div class="mdl-cell mdl-cell--5-col">
                                        <h1 class="inline-tittle-text">
                                            <fmt:message key="discount.amountFull"/>
                                            <span class="inline-content-text">
                                            <c:out value="${discount}%"/></span>
                                        </h1>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="commandName" value="confirmOrder">
                            <div class="mdl-grid centered-container">
                                <div class="mdl-cell mdl-cell--12-col">
                                    <div class="mdl-grid">
                                        <h1 class="block-header">
                                            <fmt:message key="order.details"/>
                                        </h1>
                                        <div class="block-wrapper mdl-cell mdl-cell--12-col centered-container">
                                            <div class="mdl-cell mdl-cell--6-col">
                                                <h1 class="inline-tittle-text">
                                                    <fmt:message key="order.bookingDate"/>
                                                </h1>
                                                <div class="mdl-textfield mdl-js-textfield  date-chooser">
                                                    <input class="mdl-textfield__input" type="date"
                                                           id="_order.bookingDate" name="order.bookingDate"/>
                                                </div>
                                            </div>
                                            <div class="mdl-cell mdl-cell--6-col">
                                                <h1 class="inline-tittle-text">
                                                    <fmt:message key="order.wish"/>
                                                </h1>
                                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                                    <textarea class="mdl-textfield__input" type="text" rows="6"
                                                              id="_order.wish" name="order.wish"
                                                              style="color: #9fa8da;"></textarea>
                                                    <label class="mdl-textfield__label" for="_order.wish">
                                                        <fmt:message key="order.wishSupportText"/>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mdl-grid centered-container">
                                <div class="mdl-cell mdl-cell--8-col">
                                    <input class="mdl-button mdl-js-button mdl-button--raised confirm-button"
                                           type="submit"
                                           value="<fmt:message key="order.confirm"/> "/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="mdl-cell mdl-cell--8-col centered-text">
                <h1>
                    <fmt:message key="dishOrder.empty"/>
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