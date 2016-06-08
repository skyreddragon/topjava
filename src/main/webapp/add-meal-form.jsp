<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Добавление(редактирование) пользователя</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/meals">
    <table>
        <tbody>
        <tr>
            <td>№:</td>
            <td><input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/></td>
        </tr>
        <tr>
            <td>Название:</td>
            <td><input type="text" name="description" value="<c:out value="${meal.description}"/>"/></td>
        </tr>
        <tr>
            <td>Количество калорий:</td>
            <td><input type="number" name="calories" min="0" value="<c:out value="${meal.calories}"/>"/></td>
        </tr>
        <tr>
            <td>Дата:</td>
            <td><input type="datetime-local" name="dateTime"
                       value="${meal.dateTime}"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Подтвердить"/></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>