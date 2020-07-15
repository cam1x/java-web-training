<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/dialog.css"/>
    <script defer src="static/js/modal.js"></script>
</head>

<jsp:directive.attribute name="_contact" type="by.training.contact.ContactDto" required="true"
                         description="editing wallet"/>
<jsp:directive.attribute name="index" type="java.lang.Long" required="true" description="dlg index"/>

<dialog class="mdl-dialog">
    <h4 class="mdl-dialog__title">
        <fmt:message key="dialog.editContactTittle"/>
    </h4>
    <div class="mdl-dialog__content">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="firstName" beanName="contact" _pattern="[a-zа-яА-ЯA-Z]+"
                         value="${_contact.firstName}" _patternNotMatchingMsg="Should contain only letters!"/>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="lastName" beanName="contact" _pattern="[a-zа-яА-ЯA-Z]+"
                         value="${_contact.lastName}" _patternNotMatchingMsg="Should contain only letters!"/>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="email" beanName="contact" _patternNotMatchingMsg="emailFormat"
                         value="${_contact.email}" _pattern="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"/>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="phone" beanName="contact" _pattern="^\+(?:[0-9] ?){6,14}[0-9]$+"
                         value="${_contact.phone}" _patternNotMatchingMsg="phoneFormat"/>
        </div>
    </div>
    <div class="mdl-dialog__actions">
        <input type="submit" class="mdl-button mdl-js-button"
               value="<fmt:message key="dialog.save"/>"/>
        <button type="button" class="mdl-button close"
                onclick="closeDlg(${index})">
            <fmt:message key="dialog.cancel"/>
        </button>
    </div>
</dialog>