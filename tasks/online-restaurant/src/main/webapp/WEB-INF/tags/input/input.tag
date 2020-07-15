<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.attribute name="fieldName" required="true" description="field name to display"/>
<jsp:directive.attribute name="beanName" required="true" description="some object"/>
<jsp:directive.attribute name="_pattern" required="false" description="validation pattern"/>
<jsp:directive.attribute name="_patternNotMatchingMsg" required="false" description="not matching pattern msg"/>
<jsp:directive.attribute name="validationErrors" required="false"
                         description="validation errors" type="java.util.Set"/>
<jsp:directive.attribute name="password" required="false" description="is password" type="java.lang.Boolean"/>
<jsp:directive.attribute name="date" required="false" description="is date" type="java.lang.Boolean"/>
<jsp:directive.attribute name="value" required="false" description="field value"/>

<style>
    .error-subtext{
        color: #ff0015;
        margin-top: 0;
        margin-bottom: 0;
        font-size: 85%;
    }
</style>

<c:if test="${not empty fieldName and not empty beanName}">
    <c:set var="_patternValue" value=".*"/>
    <c:if test="${not empty _pattern}">
        <c:set var="_patternValue" value="${_pattern}"/>
    </c:if>
    <c:set var="_type" value="text"/>
    <c:if test="${password eq 'true'}">
        <c:set var="_type" value="password"/>
    </c:if>
    <c:if test="${date eq 'true'}">
        <c:set var="_type" value="date"/>
    </c:if>
    <input class="mdl-textfield__input" type="${_type}" id="_${beanName}.${fieldName}" name="${beanName}.${fieldName}"
           pattern="${_patternValue}" value="${value}">
    <label class="mdl-textfield__label" for="_${beanName}.${fieldName}">
        <fmt:message key="${beanName}.${fieldName}"/>
    </label>

    <span class="mdl-textfield__error">
        <fmt:message key="error.${_patternNotMatchingMsg}"/>
    </span>

    <c:if test="${not empty validationErrors}">
        <c:forEach items="${validationErrors}" var="error">
                <h6 class="error-subtext">
                    <fmt:message key="error.${error}"/>
                </h6>
        </c:forEach>
    </c:if>
</c:if>