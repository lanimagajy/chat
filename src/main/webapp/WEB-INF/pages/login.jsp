<%@ page language="java" contentType="text/html; charset=Cp1251" pageEncoding="Cp1251" %>
<?xml version="1.0" encoding="Cp1251"?>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=Cp1251">
    <title>Login</title>
    <style type="text/css">
        <%@include file="../../static/chat.css" %>
    </style>
</head>
<body>
<div class="title">
    <div class="window-title">
        <span>
                <span>Welcome to the chat</span>
        </span>
        <div class="window-wrapper">
            <br>
            <span>Enter your nickname</span>
            <br>
            <br>
            <span>
                <jsp:text>${messageError}</jsp:text>
    <form action="chat" method="POST">
        <input type="hidden" name="command" value="LOGIN">

        <label>Name:
            <input type="text" name="userLogin" required minlength="3" maxlength="25">
            <br/>
        </label>
        <br>
        <input type="submit" value="LogIn">
    </form>
        </span>
        </div>
    </div>
</div>
</body>
</html>
