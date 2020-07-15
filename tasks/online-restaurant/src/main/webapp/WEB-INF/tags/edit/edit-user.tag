<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="_user" required="true" type="by.training.user.UserDto" %>
<%@ attribute name="roles" required="true" type="java.util.List" %>
<%@ attribute name="index" required="true" type="java.lang.Long" %>
<head>
    <link rel="stylesheet" type="text/css" href="static/style/dialog.css">
    <script defer src="static/js/modal.js"></script>
</head>

<dialog class="mdl-dialog">
    <h4 class="mdl-dialog__title">
        <fmt:message key="dialog.editRolesTittle"/> ${_user.login}
    </h4>
    <div class="mdl-dialog__content">
        <ul class="demo-list-control mdl-list">
            <c:forEach items="${roles}" var="_role">
                <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                        <fmt:message key="role.${_role.role}"/>
                    </span>
                    <span class="mdl-list__item-secondary-action">
                      <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect"
                             for="${_user.login}-${_role.role}">
                      <c:set var="_contains" value="false"/>
                        <c:forEach items="${_user.roles}" var="userRole">
                            <c:if test="${userRole eq _role}">
                                <c:set var="_contains" value="true"/>
                            </c:if>
                        </c:forEach>
                          <c:choose>
                              <c:when test="${_contains eq 'true'}">
                                  <input type="checkbox" id="${_user.login}-${_role.role}" name="${_role.role}"
                                         class="mdl-checkbox__input" value="${_role.role}" checked/>
                              </c:when>
                              <c:otherwise>
                                  <input type="checkbox" id="${_user.login}-${_role.role}" name="${_role.role}"
                                         class="mdl-checkbox__input" value="${_role.role}"/>
                              </c:otherwise>
                          </c:choose>
                      </label>
                    </span>
                </li>
            </c:forEach>
        </ul>
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