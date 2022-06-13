<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="eng">
<head>
    <title>Polynomial Calculator</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="./styles.css" />
</head>
<body>
<div class="homePage">
    <h1>Polynomial calculator</h1>
    <div class="taskDescriptionParent">
        <p class="taskDescription">This application allows user to calculate polynomial value, first derivative
            and first derivative value.
        </p>
        <hr>
        <p class="taskDescription">
            <br> In order to perform calculation,
            type polynomial factors followed by polynomial value, eg.
            <br> for equation <strong>y=ax^2+bx+c </strong> where x=d type:<strong> a b c d</strong>
            <br>a,b,c and d must be Integers!
        </p>
    </div>
    <div class="forms">
        <form action="Calculate" class="calculateForm" method="POST">
            <p>Polynomial values:<br>
                <input type="text" size="20" name="polynomialValues" class="inputField" />
            </p>
            <input type="submit" value="calculate" class="calculateButton" />
        </form>
        <form action="HistoryFromContext" class="historyForm" method="POST">
            <input type="submit" value="history from context" class="historyButton" />
        </form>
        <form action="History" class="historyForm" method="POST">
            <input type="submit" value="history from database" class="historyButton" />
        </form>
    </div>
</div>
</body>
</html>
