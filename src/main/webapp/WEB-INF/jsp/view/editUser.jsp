<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <a href="<c:url value="/login" />">Login</a><br /><br />  
        </security:authorize>
        <br />
        <a href="<c:url value="/" />">Return to Course Discussion Forum.</a>

        <security:authorize access="hasRole('ADMIN')">
            <h2>Edit User</h2>

            <form:form method="POST"  enctype="multipart/form-data" modelAttribute="edituser">
                Username: ${username}<br/><br/>
                <form:label path="password">Password</form:label><br/>
                <form:input type="password" path="password" /><br/><br/>
                <form:label path="roles">Roles</form:label><br/>
                <form:radiobutton path="roles" value="ROLE_USER" />ROLE_USER
                <form:radiobutton path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
                <br /><br />

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" name="editUser" value="Edit"/>
            </form:form>
        </security:authorize>
    </body>
</html>
