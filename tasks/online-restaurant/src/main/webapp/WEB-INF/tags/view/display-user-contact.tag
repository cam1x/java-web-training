<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.attribute name="fieldName" required="true" description="field name to display"/>
<jsp:directive.attribute name="contact" required="true" description="contact" type="by.training.contact.ContactDto"/>

<c:if test="${not empty fieldName and not empty contact}">
    <h1 class="inline-tittle-text">
        <fmt:message key="contact.${fieldName}"/>
        <span class="inline-content-text">
            <c:out value="${contact[fieldName]}"/>
        </span>
    </h1>
</c:if>