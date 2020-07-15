<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/dialog.css"/>
    <script defer src="static/js/modal.js"></script>
</head>

<jsp:directive.attribute name="_dish" type="by.training.dish.DishDto" required="true"
                         description="editing dish"/>
<jsp:directive.attribute name="index" type="java.lang.Long" required="true" description="dlg index"/>

<dialog class="mdl-dialog">
    <h4 class="mdl-dialog__title">
        <fmt:message key="dialog.editDishQuantityTittle"/>
    </h4>
    <div class="mdl-dialog__content">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="quantity" beanName="dish" value="${_dish.quantity}"
                         _pattern="[1-9][0-9]*" _patternNotMatchingMsg="onlyNumbers"/>
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