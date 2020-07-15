<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.application.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="${pageContext.request.contextPath}">
        <fmt:message key="links.home"/>
    </a>
    <a class="mdl-navigation__link"
       href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.SHOW_MENU_CMD_NAME}">
        <fmt:message key="links.menu"/>
    </a>
</nav>