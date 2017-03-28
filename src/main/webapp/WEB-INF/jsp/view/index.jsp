<!DOCTYPE html>
<html>
    <head>
        <title>Categories</title>
    </head>
    <body>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="<c:url value="/login" />">Login</a><br /><br />  
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </c:if>
      
        <h2>Categories</h2>
        <ul>
            <li><a href="<c:url value="/Lecture" />">Lecture</a><br /><br /></li>
            <li><a href="<c:url value="/Lab" />">Lab</a><br /><br /></li>
            <li><a href="<c:url value="/Other" />">Other</a><br /><br /></li>
        </ul>
    </body>
</html>
