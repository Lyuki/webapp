<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum Register</title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <p>Register failed.</p>
        </c:if>
            
        <c:if test="${param.password != param.password2}">
            <b>The username and password you entered are not correct. Please try again.</b><br /><br />
        </c:if>

        <h2>Course Discussion Forum Register</h2>
        <form action="register" method="POST">
            <label for="username">Username:</label><br/>
            <input type="text" id="username" name="username" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password" name="password" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password2" name="password2" /><br/><br/>
             
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Register"/>
        </form>
    </body>
</html>