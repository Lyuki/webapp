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
      <h2>Users</h2>
      <a href="<c:url value="/user/create" />">Create a User</a><br /><br />
      <c:choose>
        <c:when test="${fn:length(allUsers) == 0}">
          <i>There are no users in the system.</i>
        </c:when>
        <c:otherwise>
          <table>
            <tr>
              <th>Username</th>
              <th>Password</th>
              <th>Roles</th>
              <th>Action</th>
            </tr>
            <c:forEach items="${allUsers}" var="user">
              <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>
                  <c:forEach items="${user.roles}" var="role" varStatus="status">
                    <c:if test="${!status.first}">, </c:if>
                    ${role}
                  </c:forEach>
                </td>
                <td>[<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>]
                    [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]</td>
              </tr>
            </c:forEach>
          </table>
        </c:otherwise>
      </c:choose>
    </security:authorize>
  </body>

</html>
