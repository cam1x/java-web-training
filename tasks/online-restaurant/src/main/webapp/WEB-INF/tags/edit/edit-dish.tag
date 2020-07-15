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
        <fmt:message key="dialog.editDishTittle"/>
    </h4>
    <div class="mdl-dialog__content">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="name" beanName="dish" value="${_dish.name}"
                         _pattern="[a-zA-Zа-яА-Я\s\-]+" _patternNotMatchingMsg="onlyLetters"/>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input:input fieldName="price" beanName="dish" value="${_dish.price}"
                         _pattern="[1-9][0-9]*(\.[0-9]+)?" _patternNotMatchingMsg="onlyNumbers"/>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <textarea class="mdl-textfield__input" type="text" rows="3" id="_dish.description"
                      name="dish.description" style="color: #9fa8da;"></textarea>
            <label class="mdl-textfield__label" for="_dish.description">
                <fmt:message key="dish.description"/>
            </label>
        </div>
        <div class="upload-btn-wrapper">
            <button class="upload-button"><fmt:message key="dish.photo"/></button>
            <input type="file" name="dish.photo" accept="image/vnd.adobe.photoshop"/>
        </div>
    </div>
    <div class="mdl-dialog__actions">
        <input type="submit" class="mdl-button mdl-js-button"
               value="<fmt:message key="dialog.save"/>"/>
        <button type="button" class="mdl-button close" onclick="closeDlg(${index})">
            <fmt:message key="dialog.cancel"/>
        </button>
    </div>
</dialog>