<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<form method="post" action="users">
    <p><select name="list">
        <option disabled selected>Выберите пользователя</option>
        <option value="1">1</option>
        <option value="2">2</option>
    </select></p>
    <input type="submit" value="Выбрать">
</form>
<h2>Проект "<a href="https://github.com/JavaWebinar/topjava07" target="_blank">Java Enterprise (Topjava)"</a></h2>
<hr>
<ul>
    <li><a href="users">User List</a></li>
    <li><a href="meals">Meal List</a></li>
</ul>
</body>
</html>
