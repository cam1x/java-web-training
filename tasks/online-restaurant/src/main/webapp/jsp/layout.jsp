<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>
<%@ page import="by.training.application.ApplicationConstants" %>

<head>
    <title>Online restaurant</title>
    <link rel="stylesheet" type="text/css" href="static/style/material.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/style/icons.css"/>
    <link rel="stylesheet" type="text/css" href="static/style/layout.css"/>
    <link rel="stylesheet" type="text/css" href="static/style/authorization.css">
    <link rel="stylesheet" type="text/css" href="static/style/card.css">
    <link rel="stylesheet" type="text/css" href="static/style/details.css">
    <link rel="stylesheet" type="text/css" href="static/style/admin-panel.css">
    <script defer src="static/js/material.min.js"></script>
    <style>
        .demo-layout-transparent {
            background: url(${pageContext.request.contextPath}/static/image/background.jpg) center/cover;
        }

        .demo-layout-transparent .mdl-layout__header,
        .demo-layout-transparent .mdl-layout__drawer-button {
            color: white;
        }

        .mdl-layout__drawer {
            background-color: #212121;
            color: white;
        }
    </style>
</head>

<c:choose>
    <c:when test="${not empty requestScope.get('lang')}">
        <fmt:setLocale value="${requestScope.get('lang')}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="/i18n/ApplicationMessages" scope="application"/>

<div class="demo-layout-transparent mdl-layout mdl-js-layout">
    <header class="mdl-layout__header mdl-layout__header--transparent">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">
                <fmt:message key="app.navigation"/>
            </span>
            <div class="mdl-layout-spacer">
                <c:set var="currPath" value="${pageContext.request.contextPath}/?"/>
                <c:set var="_query" value="${pageContext.request.queryString}"/>
                <c:if test="${not empty _query}">
                    <c:if test="${_query.contains('lang=')}">
                        <c:set var="_query" value="${_query.replaceAll('&?lang=[a-z]{2,}', '')}"/>
                    </c:if>
                    <c:set var="currPath" value="${currPath.concat(_query).concat('&')}"/>
                </c:if>
                <a href="${currPath}lang=en" style="margin-left: 15px; margin-right: 7px">
                    <img src="${pageContext.request.contextPath}/static/image/eng.jpg"
                         alt="<fmt:message key="links.lang.en"/>" height="20" width="30">
                </a>
                <a href="${currPath}lang=ru" style="margin-left: 7px">
                    <img src="${pageContext.request.contextPath}/static/image/ru.jpg"
                         alt="<fmt:message key="links.lang.ru"/>" height="20" width="30">
                </a>
            </div>
            <nav:nav-links/>
            <nav:nav-menu/>
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">
             <fmt:message key="app.navigation"/>
        </span>
        <nav:nav-links/>
    </div>
    <main class="mdl-layout__content">
        <div class="page-content">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--2-col">
                </div>
                <div class="mdl-cell mdl-cell--10-col">
                    <c:choose>
                        <c:when test="${not empty viewName}">
                            <jsp:include page="views/${viewName}.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <div class="centered-text">
                                <h1>
                                    <fmt:message key="app.welcomeText"/>
                                </h1>
                                <a class="mdl-navigation__link"
                                   href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.SHOW_MENU_CMD_NAME}">
                                    <fmt:message key="links.menu"/>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="mdl-cell mdl-cell--2-col">
                </div>
            </div>
        </div>
    </main>
    <footer class="mdl-mini-footer">
        <div class="mdl-mini-footer--middle-section">
            <div class="mdl-logo">L&W Restaurant</div>
            <ul class="mdl-mini-footer__link-list">
                <li><fmt:message key="contact.phone"/> : +375-44-7700007</li>
                <li><fmt:message key="contact.email"/> : maxy.chechetkin@gmail.com</li>
                <li><fmt:message key="footer.address"/>: Belarus, Minsk, Kosmonavtov st.</li>
            </ul>
        </div>
    </footer>
</div>