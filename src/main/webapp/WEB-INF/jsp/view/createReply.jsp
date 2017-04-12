<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                New Reply
            </c:if>
            <c:if test="${language == 'Chinese'}">
                新回覆
            </c:if>
            
        </title>
    </head>
    <body>
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

        <c:if test="${language == 'English'}">
            <h2>New Reply</h2>
            <form:form method="POST" enctype="multipart/form-data"
                       modelAttribute="replyForm">
                <form:label path="msg">Message</form:label><br/>
                <form:textarea path="msg" rows="5" cols="30" /><br/><br/>
                <form:hidden path="topicId" value="${topicId}"/>
                <b>Attachments</b><br/>
                <input type="file" name="attachments" multiple="multiple"/><br/><br/>
                <input type="submit" value="Submit"/>
            </form:form>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>新回覆</h2>
            <form:form method="POST" enctype="multipart/form-data"
                       modelAttribute="replyForm">
                <form:label path="msg">內容</form:label><br/>
                <form:textarea path="msg" rows="5" cols="30" /><br/><br/>
                <form:hidden path="topicId" value="${topicId}"/>
                <b>副件</b><br/>
                <input type="file" name="attachments" multiple="multiple"/><br/><br/>
                <input type="submit" value="Submit"/>
            </form:form>
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