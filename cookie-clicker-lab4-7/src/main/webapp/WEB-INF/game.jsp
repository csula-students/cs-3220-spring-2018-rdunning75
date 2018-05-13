<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lazer CATS</title>
    <link rel="stylesheet" href="\cs3220stu115\css\app.css">
    <link rel="stylesheet" href="\cs3220stu115\css\generator.css">
    <script>
        window.game = {};
        window.game.state = ${state};
        var generators = window.game.state.generators;
        console.log(window.game.state);
        console.log(window.game.state.generators);
    </script>
</head>
<body>


<h1 class="title">The Lazer CATS Lab</h1>
<div class="scroll-box"><p>*Year 2556, you are a Mad Scientist who
    lives in your Cat fanatic Aunts basement. <br> *You are bent on world
    domination, but have nothing but your aunts seemingly endless supply
    of cats. <br> *"Where there is a will" you say "there is away". <br> * Build your
    cat army.<br><br><br><br>
    <p></div>

<div class="you-flex">
    <h2 class="space">Current CATS count: </h2>
    <h2 id="count">${counter}</h2>
    <button id="you-steal">Steal a Cat</button>
</div>

<div class="generator-flex">
    <c:forEach items="${generators}" var="e">
        <game-generator data-id="${(e.getId()-1)}"></game-generator>
    </c:forEach>
</div>



<script src="\cs3220stu115\app.bundle.js"></script>

</body>
</html>