<!DOCTYPE html>
<html>
    <head>
        <title>${topic.title}</title>
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
                    <security:authorize access="isAuthenticated()">
                        <a href="<c:url value="/topic/${topicId}/attachment/${attachment.name}" />">
                            <c:out value="${attachment.name}" /></a>
                        </security:authorize>    
                        <security:authorize access="isAnonymous()">
                            <c:out value="${attachment.name}" />
                        </security:authorize>
                    </c:forEach><br /><br />
            </c:if>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>題目 #${topicId}: <c:out value="${topic.title}" /></h2>
            <security:authorize access="hasRole('ADMIN')">            
                [<a href="<c:url value="/topic/delete/${topicId}" />">刪除</a>]
            </security:authorize>
            <br /><br />
            <i>用戶名稱 - <c:out value="${topic.customerName}" /></i><br /><br />
            <c:out value="${topic.msg}" /><br /><br />


            <c:if test="${topic.numberOfAttachments > 0}">
                副件:
                <c:forEach items="${topic.attachments}" var="attachment"
                           varStatus="status">
                    <c:if test="${!status.first}">, </c:if>
                    <security:authorize access="isAuthenticated()">
                        <a href="<c:url value="/topic/${topicId}/attachment/${attachment.name}" />">
                            <c:out value="${attachment.name}" /></a>
                        </security:authorize>    
                        <security:authorize access="isAnonymous()">
                            <c:out value="${attachment.name}" />
                        </security:authorize>
                    </c:forEach><br /><br />
            </c:if>
        </c:if>





        <br/> 
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/replys/view/${topicId}/${cate}" />">Go to see reply</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/replys/view/${topicId}/${cate}" />">看回覆</a>
        </c:if>

        <br/> <br/><br/>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/topic/${cate}" />">Return to Topic list</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/topic/${cate}" />">返回題目列表</a>
        </c:if>
    </body>
</html>
