<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="edit" tagdir="/WEB-INF/tags/edit" %>

<head>
    <link rel="stylesheet" type="text/css" href="static/style/admin-panel.css">
</head>

<div class="mdl-cell mdl-cell--10-col">
    <c:choose>
        <c:when test="${not empty users}">
            <table class="mdl-data-table mdl-js-data-table">
                <thead>
                <tr>
                    <th class="mdl-data-table__cell--non-numeric">
                        <fmt:message key="user.login"/>
                    </th>
                    <th class="mdl-data-table__cell--non-numeric">
                        <fmt:message key="contact.firstName"/>
                    </th>
                    <th class="mdl-data-table__cell--non-numeric">
                        <fmt:message key="contact.lastName"/>
                    </th>
                    <th class="mdl-data-table__cell--non-numeric">
                        <fmt:message key="contact.email"/>
                    </th>
                    <th class="mdl-data-table__cell--non-numeric">
                        <fmt:message key="contact.phone"/>
                    </th>
                    <th class="mdl-data-table__cell--non-numeric" colspan="3">
                        <fmt:message key="table.actions"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="_user">
                    <c:set var="_contacts" value="${_user.contacts}"/>
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">${_user.login}</td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <c:forEach var="_contact" items="${_contacts}">
                                <c:out value="${_contact.firstName}"/>
                            </c:forEach>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <c:forEach var="_contact" items="${_contacts}">
                                <c:out value="${_contact.lastName}"/>
                            </c:forEach>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <c:forEach var="_contact" items="${_contacts}">
                                <c:out value="${_contact.email}"/>
                            </c:forEach>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <c:forEach var="_contact" items="${_contacts}">
                                <c:out value="${_contact.phone}"/>
                            </c:forEach>
                        </td>

                        <td class="mdl-data-table__cell--non-numeric">
                            <form action="${pageContext.request.contextPath}/" method="POST">
                                <input type="hidden" name="userId" value="${_user.userId}"/>
                                <input type="hidden" name="commandName" value="editUser"/>
                                <button type="button" class="mdl-button" onclick="openDlg(${users.indexOf(_user)})">
                                    <fmt:message key="table.editUser"/>
                                </button>
                                <edit:edit-user _user="${_user}" roles="${roles}" index="${users.indexOf(_user)}"/>
                            </form>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <form action="${pageContext.request.contextPath}/" method="POST">
                                <input type="hidden" name="userId" value="${_user.userId}"/>
                                <c:choose>
                                    <c:when test="${_user.locked}">
                                        <input type="hidden" name="commandName" value="unlockUser"/>
                                        <input class="mdl-button mdl-js-button" type="submit"
                                               value="<fmt:message key="table.unlock"/>"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="commandName" value="lockUser"/>
                                        <input class="mdl-button mdl-js-button" type="submit"
                                               value="<fmt:message key="table.lock"/>"/>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <form action="${pageContext.request.contextPath}/" method="POST">
                                <input type="hidden" name="userId" value="${_user.userId}"/>
                                <input type="hidden" name="commandName" value="deleteUser"/>
                                <input class="mdl-button mdl-js-button" type="submit"
                                       value="<fmt:message key="table.deleteUser"/>"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="centered-text">
                <h1>
                    <fmt:message key="table.empty"/>
                </h1>
            </div>
        </c:otherwise>
    </c:choose>
</div>