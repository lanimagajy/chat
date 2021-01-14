<%@ page language="java" contentType="text/html; charset=Cp1251" pageEncoding="Cp1251" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<?xml version="1.0" encoding="Cp1251"?>
<!DOCTYPE html>


<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=Cp1251">
    <title>Chat</title>
    <style type="text/css">
        <%@include file="/static/chat.css" %>
    </style>
</head>
<body>
<div class="wrapper">
    <header class="header">
        <table>
            <tbody>
            <tr>
                <td>
                    Welcome, ${userSession.nick}
                </td>
                <td>
                    EPAM CHAT
                </td>
                <td>
                    <form action="chat" method="POST">
                        <input type="hidden" name="command" value="LOGOUT"/>
                        <input type="hidden" name="logoutUser" value="${userSession.nick}"/>
                        <input type="submit" value="LogOut"/>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </header>
    <div class="middle">
        <div class="container">
            <main class="content">
                <ul>
                    <div class="title-message">
                        <li>
                            Messages
                        </li>
                    </div>
                    <div class="messages">
                        <c:forEach var="messages" items="${chatDataProvider.messageList}">
                            <li>
                                <fmt:formatDate value="${messages.date}" pattern="dd-MM-yyyy HH:mm"/>
                                <c:out value="${messages.author.nick}: ${messages.message}"/>
                            </li>
                        </c:forEach>
                    </div>
                    <a href="#" class="expand"></a>
                </ul>
            </main>
        </div>
        <aside class="left-sidebar">
            <ul>
                <div class="title-users">
                    <li>
                        Users
                    </li>
                </div>
                <div class="users">
                    <c:forEach var="usersOnline" items="${chatDataProvider.userListOnline}">
                            <form action="chat" method="POST">
                                <input type="hidden" name="command" value="KICK"/>
                                <input name="kickUser" type="hidden" value="${usersOnline.nick}"/>
                                <a class="nickGoToMessageForm">${usersOnline.nick}</a>
                                <c:if test="${userSession.role == 'ADMIN'}">
                                    <c:if test="${usersOnline.role == 'USER'}">
                                        <input type="submit" value="Kick"/>
                                    </c:if>
                                </c:if>
                            </form>
                    </c:forEach>
                </div>
            </ul>
        </aside>
        <aside class="right-sidebar">
            <c:choose>
                <c:when test="${userSession.role == 'ADMIN'}">
                    <ul>
                        <div class="title-admin">
                            Administration
                        </div>
                        <br>
                        <c:forEach var="usersOfflineAndOnline" items="${chatDataProvider.userListKickedAndOffline}">
                            <form action="chat" method="POST">
                                <c:if test="${usersOfflineAndOnline.role == 'USER'}">
                                    <c:if test="${usersOfflineAndOnline.status == 'OFFLINE'}">
                                        <input type="hidden" name="command" value="KICK"/>
                                        <input type="hidden" name="kickUser" value="${usersOfflineAndOnline.nick}"/>
                                        <c:out value="${usersOfflineAndOnline.nick}"/>
                                        <input type="submit" value="Kick"/>
                                    </c:if>
                                    <c:if test="${usersOfflineAndOnline.status == 'KICKED'}">
                                        <input type="hidden" name="command" value="UNKICK"/>
                                        <input type="hidden" name="unkickUser" value="${usersOfflineAndOnline.nick}"/>
                                        <c:out value="${usersOfflineAndOnline.nick}"/>
                                        <input type="submit" value="UnKick"/>
                                    </c:if>
                                </c:if>
                            </form>
                        </c:forEach>
                    </ul>
                </c:when>
            </c:choose>
        </aside>
    </div>
</div>
<footer class="footer">
    <form action="chat" method="POST">
        <input type="hidden" name="command" value="MESSAGE"/>
        <div class="input-area">
            <div class="input-wrapper">
                <label>
                    <input id="sendMessage" name="message"/>
                </label>
            </div>
            <input type="submit" value="Submit"/>
        </div>
    </form>
</footer>
<script type="text/javascript">
    <%@include file="/static/chat.js"%>
</script>
</body>
</html>
