<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" tagdir="/WEB-INF/tags/view" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/profile.css">
    <style>
        .block-header {
            font-size: 300%;
            margin-left: 35%;
            font-weight: bold;
            color: green;
        }

        .inline-header {
            text-align: center;
            font-size: 200%;
            color: #2a801c;
        }

        .inline-content {
            font-size: 150%;
            color: #ff4222;
        }
    </style>
</head>

<div class="mdl-grid">
    <c:set var="contacts" value="${user.contacts}"/>
    <div class="mdl-cell mdl-cell--10-col">
        <h1 class="block-header">
            <fmt:message key="contact.name"/>
        </h1>
        <div class="mdl-grid centered-container">
            <c:forEach var="_contact" items="${contacts}">
                <div class="mdl-cell mdl-cell-4-col">
                    <div class="mdl-card mdl-shadow--2dp">
                        <div class="mdl-card--expand mdl-card__title">
                            <view:display-user-contact fieldName="firstName" contact="${_contact}"/>
                            <view:display-user-contact fieldName="lastName" contact="${_contact}"/>
                            <view:display-user-contact fieldName="email" contact="${_contact}"/>
                            <view:display-user-contact fieldName="phone" contact="${_contact}"/>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="mdl-cell mdl-cell--10-col">
        <h1 class="block-header">
            <fmt:message key="discount.controlPanel"/>
        </h1>
        <div class="block-wrapper mdl-cell mdl-cell--12-col centered-container">
            <div class="mdl-cell mdl-cell--6-col">
                <h1 class="inline-header">
                    <fmt:message key="discount.amountFull"/>
                    <span class="inline-content">
                                            <c:out value="${discount}%"/></span>
                </h1>
            </div>
            <div class="mdl-cell mdl-cell--3-col">
                <form action="${pageContext.request.contextPath}/" method="POST">
                    <input type="hidden" name="userId" value="${user.userId}"/>
                    <input type="hidden" name="commandName" value="editDiscount"/>
                    <button type="button"
                            class="mdl-button" onclick="openDlg(0)" style="margin-top: 10%">
                        <fmt:message key="discount.edit"/>
                    </button>
                    <edit:edit-dicount/>
                </form>
            </div>
        </div>
    </div>
</div>