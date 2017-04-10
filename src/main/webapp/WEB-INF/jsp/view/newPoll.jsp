<!DOCTYPE html>
<html>
    <head>
        <title>New Poll</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <br/><br/>
        <a href="<c:url value="/" />">Return to index page</a>
        
        <h2>Create a Poll</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="pollForm">
            <form:label path="question">Question</form:label><br/>
            <form:textarea path="question" rows="5" cols="30" /><br/><br/>
            <form:label path="ans1">Answer 1</form:label><br/>
            <form:input type="text" path="ans1" /><br/><br/>
            <form:label path="ans2">Answer 2</form:label><br/>
            <form:input type="text" path="ans2" /><br/><br/>
            <form:label path="ans3">Answer 3</form:label><br/>
            <form:input type="text" path="ans3" /><br/><br/>
            <form:label path="ans4">Answer 4</form:label><br/>
            <form:input type="text" path="ans4" /><br/><br/>
            <input type="submit" value="Create"/>
        </form:form>
    </body>
</html>