<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/dialog.css"/>
    <script defer src="static/js/modal.js"></script>
</head>

<dialog class="mdl-dialog">
    <h4 class="mdl-dialog__title">
        <fmt:message key="dialog.editDiscountTittle"/>
    </h4>
    <div class="mdl-dialog__content">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="amount" beanName="discount"
                         _pattern="[1-9][0-9]*(\.[0-9]+)?" _patternNotMatchingMsg="onlyNumbers"/>
        </div>
    </div>
    <div class="mdl-dialog__actions">
        <input type="submit" class="mdl-button mdl-js-button"
               value="<fmt:message key="dialog.save"/>"/>
        <button type="button" class="mdl-button close" onclick="closeDlg(0)">
            <fmt:message key="dialog.cancel"/>
        </button>
    </div>
</dialog>