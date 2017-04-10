<!DOCTYPE html>
<html>
    <head>
        <title>Topics</title>
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

        <h2>Reply</h2>
        <c:choose>
            <c:when test="${fn:length(replyDatabase) == 0}">
                <i>There are no reply in there</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${replyDatabase}" var="entry">
                    <ul>
                        <li><a href="<c:out value="${entry.id}" />">
                                <c:out value="${entry.msg}" /></a>
                            (<c:out value="${entry.customerName}" /> reply)
                            <security:authorize access="hasRole('ADMIN')">            
                                [<a href="<c:url value="/replys/delete/${entry.id}" />">Delete</a>]
                            </security:authorize>           
                            <br />
                        </li>
                        <c:if test="${entry.numberOfAttachments > 0}">
                            Attachments:
                            <c:forEach items="${entry.attachments}" var="attachment"
                                       varStatus="status">
                                <c:if test="${!status.first}">, </c:if>
                                <a href="<c:url value="/replys/${entry.topicId}/attachment/${attachment.name}" />">
                                    <c:out value="${attachment.name}" /></a>
                            </c:forEach><br /><br />
                        </c:if>
                    </ul>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br/><br/>
        <security:authorize access="isAuthenticated()">
            <a href="<c:url value="/replys/create/${topicId}" />">Create Reply</a><br /><br /> 
        </security:authorize>
        <a href="<c:url value="/topic" />">Return to Topic list</a>

    </body>
</html>