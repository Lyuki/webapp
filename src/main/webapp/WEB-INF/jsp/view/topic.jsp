<!DOCTYPE html>
<html>
    <head>
        <title><c:out value="${cate}" />Topics</title>
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
      
        <h2>Topics</h2>
        <security:authorize access="isAuthenticated()">
             <a href="<c:url value="/topic/create/${cate}" />">New Topic</a><br /><br /> 
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(topicDatabase) == 0}">
                <i>There are no topics in there</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${topicDatabase}" var="entry">
                    <ul>
                    <li><a href="<c:url value="/topic/reply/${cate}/${entry.id}" />">
                        <c:out value="${entry.title}" /></a>
                    (<c:out value="${entry.customerName}" /> reply)
                    <security:authorize access="hasRole('ADMIN')">            
                        [<a href="<c:url value="/topic/delete/${entry.id}" />">Delete</a>]
                    </security:authorize>           
                    <br />
                    </li>
                    </ul>
                </c:forEach>
            </c:otherwise>
        </c:choose>
                <br/><br/>
        <a href="<c:url value="/" />">Return to index page</a>
      
    </body>
</html>
