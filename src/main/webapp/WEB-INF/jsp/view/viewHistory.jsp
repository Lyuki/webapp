<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                View History
            </c:if>
            <c:if test="${language == 'Chinese'}">
                歷史回顧
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
            <a href="<c:url value="/login" />">
                <c:if test="${language == 'English'}">
                    Login
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    登入
                </c:if>
            </a>  
        </security:authorize>

        <a href="<c:url value="/chinese" />" style="float: right;">
            <c:if test="${language == 'English'}">
                Chinese
            </c:if>
            <c:if test="${language == 'Chinese'}">
                中文
            </c:if>
        </a>
        <label style="float: right;">&nbsp;/&nbsp;</label>

        <a href="<c:url value="/english" />" style="float: right;">
            <c:if test="${language == 'English'}">
                English
            </c:if>
            <c:if test="${language == 'Chinese'}">
                英文
            </c:if>
        </a>
        <br />

        <h2>
            <c:if test="${language == 'English'}">
                Poll Result:
            </c:if>
            <c:if test="${language == 'Chinese'}">
                投票結果：
            </c:if>     
        </h2>
        ${result.title}<br/><br/>
        ${result.ans1}: ${result.result1}<br/>
        ${result.ans2}: ${result.result2}<br/>
        ${result.ans3}: ${result.result3}<br/>
        ${result.ans4}: ${result.result4}<br/>
        
        <br/><br/><br/>
        <a href="<c:url value="/" />">
            <c:if test="${language == 'English'}">
                Return to index page
            </c:if>
            <c:if test="${language == 'Chinese'}">
                返回
            </c:if> 
        </a>
    </body>
</html>
