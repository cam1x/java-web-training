<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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