<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" tagdir="/WEB-INF/tags/view" %>

<head>
    <style>
        .details-img {
            min-width: 200px;
            max-width: 500px;
            min-height: 500px;
            max-height: 650px;
        }
    </style>
</head>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--2-col">
    </div>
    <div class="mdl-cell mdl-cell--4-col">
        <img class="details-img" src="data:image/jpg;base64,${dish.getBase64Encoded()}"
             alt="${dish.name}"/>
    </div>
    <div class="mdl-cell mdl-cell--4-col">
        <view:display-bean fieldName="name" beanName="dish"/>
        <view:display-bean fieldName="price" beanName="dish"/>
        <view:display-bean fieldName="description" beanName="dish"/>
    </div>
    <div class="mdl-cell mdl-cell--2-col">
        <c:set var="isLogged" value="${not empty pageContext.session.getAttribute('user')}"/>
        <c:if test="${isLogged eq 'true'}">
            <form action="${pageContext.request.contextPath}/" method="POST">
                <input type="hidden" name="dishId" value="${dish.id}"/>
                <input type="hidden" name="commandName" value="addDishToOrder"/>
                <button class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored fixed-btn">
                    <i type="submit" class="material-icons">shopping_cart</i>
                </button>
            </form>
        </c:if>
    </div>
</div>