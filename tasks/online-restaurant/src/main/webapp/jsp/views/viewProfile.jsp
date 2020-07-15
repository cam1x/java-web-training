<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" tagdir="/WEB-INF/tags/view" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>
<%@ page import="by.training.application.ApplicationConstants" %>

<jsp:useBean id="_user" class="by.training.user.UserDto"/>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/profile.css">
</head>

<div class="mdl-grid">
    <c:set var="_user" value="${pageContext.session.getAttribute('user')}"/>
    <c:if test="${not empty _user}">
        <div class="mdl-cell mdl-cell--10-col">
            <c:set var="contacts" value="${_user.contacts}"/>
            <c:set var="wallets" value="${_user.wallets}"/>
            <c:set var="_avatar" value="${pageContext.request.contextPath}/static/image/default-avatar.jpg"/>
            <c:if test="${not empty _user.avatar}">
                <c:set var="_avatar" value="data:image/jpg;base64,${_user.getBase64Encoded()}"/>
            </c:if>
            <div class="mdl-grid centered-container">
                <div class="mdl-cell mdl-cell--5-col centered-container">
                    <img src="${_avatar}" class="user-avatar"/>
                    <form action="${pageContext.request.contextPath}/" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="commandName" value="changeAvatar"/>
                        <input type="hidden" name="userId" value="${_user.userId}"/>
                        <div class="upload-btn-wrapper" style="padding-top: 20%">
                            <button class="upload-button"><fmt:message key="user.loadAvatar"/></button>
                            <input type="file" name="user.avatar" accept="image/vnd.adobe.photoshop"/>
                        </div>
                        <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
                            <i type="submit" class="material-icons">save</i>
                        </button>
                    </form>
                </div>
            </div>
            <div class="mdl-grid centered-container">
                <c:forEach var="_wallet" items="${wallets}">
                    <div class="mdl-cell mdl-cell--3-col">
                        <div class="mdl-card mdl-shadow--2dp wallet-card">
                            <div class="mdl-card--expand mdl-card__title">
                                <view:display-user-wallet fieldName="name" wallet="${_wallet}"/>
                                <view:display-user-wallet fieldName="amount" wallet="${_wallet}"/>
                            </div>
                            <div class="mdl-card__menu">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="commandName" value="editWallet"/>
                                    <input type="hidden" name="walletId" value="${_wallet.id}"/>
                                    <button type="button"
                                            class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect"
                                            onclick="openDlg(${wallets.indexOf(_wallet)})">
                                        <i class="material-icons">edit</i>
                                    </button>
                                    <edit:edit-wallet _wallet="${_wallet}" index="${wallets.indexOf(_wallet)}"/>
                                </form>
                                <c:if test="${wallets.indexOf(_wallet) != 0}">
                                    <form action="${pageContext.request.contextPath}/" method="POST">
                                        <input type="hidden" name="commandName" value="deleteWallet"/>
                                        <input type="hidden" name="walletId" value="${_wallet.id}"/>
                                        <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
                                            <i type="submit" class="material-icons">delete</i>
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="mdl-cell mdl-cell--1-col">
                    <form action="${pageContext.request.contextPath}/" method="POST">
                        <input type="hidden" name="commandName" value="saveWallet"/>
                        <button class="mdl-button mdl-js-button mdl-button--fab
                        mdl-js-ripple-effect mdl-button--colored"
                                onclick="openDlg(${wallets.size()})" type="button">
                            <i class="material-icons">add</i>
                        </button>
                        <edit:add-wallet index="${wallets.size()}"/>
                    </form>
                </div>
            </div>
            <h1 class="header-text">
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
                            <div class="mdl-card__menu">
                                <form action="${pageContext.request.contextPath}/" method="POST">
                                    <input type="hidden" name="commandName" value="editContact"/>
                                    <input type="hidden" name="contactId" value="${_contact.id}"/>
                                    <button type="button"
                                            class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect"
                                            onclick="openDlg(${contacts.indexOf(_contact) + wallets.size() + 1})">
                                        <i class="material-icons">edit</i>
                                    </button>
                                    <edit:edit-contact _contact="${_contact}"
                                                       index="${contacts.indexOf(_contact) + wallets.size() + 1}"/>
                                </form>
                                <c:if test="${contacts.indexOf(_contact) != 0}">
                                    <form action="${pageContext.request.contextPath}/" method="POST">
                                        <input type="hidden" name="commandName" value="deleteContact"/>
                                        <input type="hidden" name="contactId" value="${_contact.id}"/>
                                        <button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
                                            <i type="submit" class="material-icons">delete</i>
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="mdl-cell mdl-cell--1-col">
                    <form action="${pageContext.request.contextPath}/" method="POST">
                        <input type="hidden" name="commandName" value="saveContact"/>
                        <button class="mdl-button mdl-js-button mdl-button--fab
                        mdl-js-ripple-effect mdl-button--colored"
                                onclick="openDlg(${wallets.size() + contacts.size() + 1})" type="button">
                            <i class="material-icons">add</i>
                        </button>
                        <edit:add-contact index="${wallets.size() + contacts.size() + 1}"/>
                    </form>
                </div>
            </div>
            <div class="centered-container">
                <a class="mdl-navigation__link history-link"
                   href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.VIEW_ORDER_HISTORY_CMD_NAME}">
                    <fmt:message key="links.orderHistory"/>
                </a>
            </div>
        </div>
    </c:if>
</div>