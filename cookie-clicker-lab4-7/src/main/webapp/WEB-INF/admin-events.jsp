
<%--
  Created by IntelliJ IDEA.
  User: DrSamsonnite
  Date: 4/15/18
  Time: 9:27 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Katts-game</title>
    <link rel="stylesheet" href="\cs3220stu115\css\basic.css">
</head>
<body>
<div id="title">
    <h1>IncraKatts Game Framework</h1>
</div>

<nav>
    <a href="admin-info.html">Game Information</a> |
    <a href="/cs3220stu115/admin/generators">Generators</a> |
    <a href="/cs3220stu115/admin/events">Events</a>
    <form action="/cs3220stu115/admin/auth" method="get">
        <button type="submit">Log Out</button>
        <input type="hidden" value="delete" name="logout">
    </form>
</nav>


<div class="container">
    <form name = "add" method="POST" id="form">
        <div id="stuff">
            Event Name: <br> <input name="eventName" id="eventName"type="text" placeholder="Event Name...">
            <br>
            Event Description <br>
            <textarea name="description" id ="description" rows="5" cols="30"> Insert description here </textarea> <br>

            Trigger at: <br> <input type="text"placeholder="Trigger @ this number..." name="trigger"> <br>
            <input type="hidden" name="id" value = ${id} >
            <input type="hidden" name="action" value="add">
            <input  type="submit" value="Add/Edit">

        </div>
    </form>

    <div id="table">
        <table>
            <tr>
                <th>Name </th>
	            <th>Description</th>
	            <th>Trigger At</th>
            </tr>
            <c:forEach items="${events}" var="e" >
                <tr>
                    <td>${e.getName()}</td>
                    <td>${e.getDescription()}</td>
                    <td>${e.getTriggerAt()}</td>
                    <td>${e.getId()}</td>
                    <td>  <a href="events?id=${e.getId()}" > edit </a> | <form name="delete" method="POST"> <input type="hidden" name="action" value ="delete"> <input type="hidden" name="id" value =${e.getId()}> <button type="submit"> delete </button> </form> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
</div>
</div>

</body>
</html>
