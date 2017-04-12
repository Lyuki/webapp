<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                Topics
            </c:if>
            <c:if test="${language == 'Chinese'}">
                題目
            </c:if>
        </title>
    </head>
    <body>
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <c:if test="${language == 'English'}">
                    <input type="submit" value="Log out" />
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    <input type="submit" value="登出" />
                </c:if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <c:if test="${language == 'English'}">
                <a href="<c:url value="/login" />">Login</a><br /><br />
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <a href="<c:url value="/login" />">登入</a><br /><br />
            </c:if>   
        </security:authorize>

        <c:if test="${language == 'English'}">
            <h2>Reply</h2>
            <c:choose>
                <c:when test="${fn:length(replyDatabase) == 0}">
                    <i>There are no reply in there</i>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${replyDatabase}" var="entry">
                        <ul>
                            <li> >>
                                <c:out value="${entry.msg}" />
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
                                    <security:authorize access="isAuthenticated()">
                                        <a href="<c:url value="/replys/${entry.id}/attachment/${attachment.name}" />">
                                            <c:out value="${attachment.name}" /></a>
                                        </security:authorize>
                                        <security:authorize access="isAnonymous()">
                                            <c:out value="${attachment.name}" />
                                        </security:authorize>
                                    </c:forEach><br /><br />
                            </c:if>

                        </ul>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>回覆</h2>
            <c:choose>
                <c:when test="${fn:length(replyDatabase) == 0}">
                    <i>沒有回覆</i>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${replyDatabase}" var="entry">
                        <ul>
                            <li> >>
                                <c:out value="${entry.msg}" />
                                (<c:out value="${entry.customerName}" /> reply)
                                <security:authorize access="hasRole('ADMIN')">            
                                    [<a href="<c:url value="/replys/delete/${entry.id}" />">刪除</a>]
                                </security:authorize>           
                                <br />
                            </li>

                            <c:if test="${entry.numberOfAttachments > 0}">
                                副件:
                                <c:forEach items="${entry.attachments}" var="attachment"
                                           varStatus="status">
                                    <c:if test="${!status.first}">, </c:if>
                                    <security:authorize access="isAuthenticated()">
                                        <a href="<c:url value="/replys/${entry.id}/attachment/${attachment.name}" />">
                                            <c:out value="${attachment.name}" /></a>
                                        </security:authorize>
                                        <security:authorize access="isAnonymous()">
                                            <c:out value="${attachment.name}" />
                                        </security:authorize>
                                    </c:forEach><br /><br />
                            </c:if>

                        </ul>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if> 




        <br/><br/>
        <security:authorize access="isAuthenticated()">
            <c:if test="${language == 'English'}">
                <a href="<c:url value="/replys/create/${topicId}/${cate}" />">Create Reply</a><br /><br /> 
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <a href="<c:url value="/replys/create/${topicId}/${cate}" />">建立回覆</a><br /><br /> 
            </c:if>
        </security:authorize>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/topic/${cate}" />">Return to Topic list</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/topic/${cate}" />">返回題目列表</a>
        </c:if>

    </body>
</html>