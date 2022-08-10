<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sandwich Condiment</title>
</head>
<body>

<h1>Sandwich Condiment</h1>
<form action="save" method="post">
    <input type="checkbox" name="condiment" id="Lettuce" value="Lettuce">
    <lable id="Lettuce">Lettuce</lable><br>
    <input type="checkbox" name="condiment" id="Tomato" value="Tomato">
    <lable id="Tomato">Tomato</lable><br>
    <input type="checkbox" name="condiment" id="Mustard" value="Mustard">
    <lable id="Mustard">Mustard</lable><br>
    <input type="checkbox" name="condiment" id="Sprouts" value="Sprouts">
    <lable id="Sprouts">Sprouts</lable><br>
    <button type="submit" value="submit">Save</button>

</form>
    <div>
    <c:forEach items="${condiment}" var="b">
        <c:out value="${b}"></c:out>
    </c:forEach>

    </div>
</body>
</html>