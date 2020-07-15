<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/material.min.css"/>
    <link rel="stylesheet" type="text/css" href="static/style/layout.css"/>
</head>

<div class="mdl-grid">
    <div class="mdl-cell mdl-cell--10-col">
        <div class="centered-text">
            <h1>
                <fmt:message key="error.403"/>
            </h1>
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}">
                <fmt:message key="links.home"/>
            </a>
        </div>
    </div>
</div>