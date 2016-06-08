<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Cписок приема пищи</title>
    <link rel="stylesheet" href="http://localhost:8080/topjava/css/style.css" type="text/css">
</head>
<body>
<form action="meals">
    <input type="hidden" name="action" value="add"/>
    <button type="submit">Добавить</button>
</form>

<table style="text-align: center" border="1px">
    <thead>
    <tr>
        <th>№</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Дата приема</th>
        <th colspan="2">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
    <tr class=<c:if test="${meal.exceed}">red</c:if><c:if test="${!meal.exceed}">green</c:if>>
        <td><c:out value="${meal.id}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><c:out value="${fn:replace(meal.dateTime,'T',' ')}"/></td>
        <td>
            <form action="meals">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="mealId" value="<c:out value="${meal.id}"/>">
                <button type="submit">Редактировать</button>
            </form>
        </td>
        <td>
            <form action="meals">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="mealId" value="<c:out value="${meal.id}"/>">
                <button type="submit">Удалить</button>
            </form>
        </td>
        </c:forEach>
    </tbody>
</table>
<br>
<h3>Фильтр по времени</h3>
<form method="post" action="meals">
    <label>
        <input type="time" name="startTime" value="">
    </label>
    <label>
        <input type="time" name="endTime" value="">
    </label>
    <input type="submit" value="Поиск">
</form>
<br>
<form action="meals">
    <input type="hidden" name="action" value="return">
    <button type="submit">Сброс</button>
</form>
</body>
</html>
