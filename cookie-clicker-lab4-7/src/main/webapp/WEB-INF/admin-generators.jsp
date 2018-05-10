<%--
  Created by IntelliJ IDEA.
  User: DrSamsonnite
  Date: 4/20/18
  Time: 12:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Katts-Generator</title>
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
            Generator Name: <br> <input name="generatorName" id="generatorName"type="text" placeholder="Generator Name...">
            <br>
            Generator rate <br> <input name="generatorRate" id="generatorRate" type="text" placeholder="Generator Rate...">
            <br>
            Base cost <br> <input name="generatorCost" id="generatorCost" type="text" placeholder="i.e. 5, 20, 40...">
            <br>
            Unlock at <br> <input name="unlockAt" id="unlockAt" type="text" placeholder="i.e. 5, 20, 40...">
            <br>
            Generator Description <br> <textarea name="description" id ="description" rows="5" cols="30"> Insert description here </textarea>
            <br>

            <input type="hidden" name="id" value = ${id}>
            <input type="hidden" name="action" value="add">
			<input  type="submit" value="Add/Edit">

        </div>
    </form>

    <div id="table">
        <table>
            <tr>
                <th>Name </th>
                <th>Rate</th>
                <th>Base Cost</th>
                <th>Unclock At</th>
                <th>Description</th>
            </tr>
            <c:forEach items="${generators}" var="g" >
                <tr>
                    <td>${g.getName()}</td>
                    <td>${g.getRate()}</td>
                    <td>${g.getBaseCost()}</td>
                    <td>${g.getUnlockAt()}</td>
                    <td>${g.getDescription()}</td>
                    <td>${g.getId()}</td>
                    <td>  <a href="generators?id=${g.getId()}" > edit </a> | <form name="delete" method="POST"> <input type="hidden" name="action" value ="delete"> <input type="hidden" name="id" value =${g.getId()}> <button type="submit"> delete </button> </form> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
</div>
</div>

</body>
</html>
