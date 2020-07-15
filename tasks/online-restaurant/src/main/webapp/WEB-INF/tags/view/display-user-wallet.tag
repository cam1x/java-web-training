<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.attribute name="fieldName" required="true" description="field name to display"/>
<jsp:directive.attribute name="wallet" required="true" description="contact" type="by.training.wallet.WalletDto"/>

<c:if test="${not empty fieldName and not empty wallet}">
    <h1 class="inline-tittle-text">
        <fmt:message key="wallet.${fieldName}"/>
        <span class="inline-content-text">
            <c:out value="${wallet[fieldName]}"/>
        </span>
    </h1>
</c:if>
