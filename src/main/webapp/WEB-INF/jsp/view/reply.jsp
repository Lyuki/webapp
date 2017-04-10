<!DOCTYPE html>
<html>
    <head>
        <title>${topic.title}</title>
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

        <h2>Topic #${topicId}: <c:out value="${topic.title}" /></h2>
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="/topic/delete/${topicId}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <i>User Name - <c:out value="${topic.customerName}" /></i><br /><br />
        <c:out value="${topic.msg}" /><br /><br />
        <c:if test="${topic.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${topic.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/topic/${topicId}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
            <br/> 
            <a href="<c:url value="/replys/view/${topicId}/${cate}" />">Go to see reply</a>
            <br/> <br/><br/>
        <a href="<c:url value="/topic/${cate}" />">Return to topic list</a>
    </body>
</html>
