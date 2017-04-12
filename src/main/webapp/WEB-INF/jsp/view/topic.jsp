<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                <c:out value="${cate}" />Topics
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <c:out value="${cate}" />題目
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
                            (<c:out value="${entry.numOfReply}" />)
                            <security:authorize access="hasRole('ADMIN')">            
                                [<a href="<c:url value="/topic/delete/${entry.id}" />">Delete</a>]
                            </security:authorize>           
                            <br />
                        </li>
                    </ul>
                </c:forEach>
            </c:otherwise>
        </c:choose>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <h2>題目</h2>
        <security:authorize access="isAuthenticated()">
            <a href="<c:url value="/topic/create/${cate}" />">新題目</a><br /><br /> 
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(topicDatabase) == 0}">
                <i>沒有題目</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${topicDatabase}" var="entry">
                    <ul>
                        <li><a href="<c:url value="/topic/reply/${cate}/${entry.id}" />">
                                <c:out value="${entry.title}" /></a>
                            (<c:out value="${entry.numOfReply}" />)
                            <security:authorize access="hasRole('ADMIN')">            
                                [<a href="<c:url value="/topic/delete/${entry.id}" />">刪除</a>]
                            </security:authorize>           
                            <br />
                        </li>
                    </ul>
                </c:forEach>
            </c:otherwise>
        </c:choose>
            </c:if>
                
                
                
      
        <br/><br/>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/" />">Return to index page</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/" />">返回主頁</a>
        </c:if>

    </body>
</html>
