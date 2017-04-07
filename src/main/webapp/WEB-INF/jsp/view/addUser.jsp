<!DOCTYPE html>
<html>
  <head>
    <title>Course Discussion Forum Register</title>
  </head>
  <body>
    <c:if test="${param.error != null}">
      <p>Register failed.</p>
    </c:if>

    <h2>Course Discussion Forum Register</h2>
    <form:form method="POST"  enctype="multipart/form-data" modelAttribute="user">
      <form:label path="username">Username</form:label><br/>
      <form:input type="text" path="username" /><br/><br/>
      <form:label path="password">Password</form:label><br/>
      <form:input type="password" path="password" /><br/><br/>
      <security:authorize access="isAnonymous()">
        <form:hidden path="roles" value="ROLE_USER" checked="checked"/>  
      </security:authorize>
      <security:authorize access="hasRole('ADMIN')">
        <form:label path="roles">Roles</form:label><br/>
        <form:checkbox path="roles" value="ROLE_USER" />ROLE_USER
        <form:checkbox path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
        <br /><br />
      </security:authorize>

      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="submit" name="addUser" value="Register"/>
    </form:form>

  </body>
</html>
