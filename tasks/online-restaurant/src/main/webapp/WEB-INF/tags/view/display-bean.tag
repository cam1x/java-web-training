<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.attribute name="fieldName" required="true" description="field name to display"/>
<jsp:directive.attribute name="beanName" required="true" description="some object"/>
<c:if test="${not empty fieldName and not empty beanName}">
    <c:set var="bean" value="${requestScope[beanName]}"/>
    <h1 class="header-text">
        <fmt:message key="${beanName}.${fieldName}"/>
    </h1>
    <p class="header-content">${bean[fieldName]}</p>
</c:if>