<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                New Topic
            </c:if>
            <c:if test="${language == 'Chinese'}">
                新題目
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
            <h2>Create a Topic</h2>
            <form:form method="POST" enctype="multipart/form-data"
                       modelAttribute="topicForm">
                <form:label path="title">Title</form:label><br/>
                <form:input type="text" path="title" /><br/><br/>
                <form:label path="msg">Message</form:label><br/>
                <form:textarea path="msg" rows="5" cols="30" /><br/><br/>
                <form:input type="hidden" path="category" value="${cate}"/>
                <b>Attachments</b><br/>
                <input type="file" name="attachments" multiple="multiple"/><br/><br/>
                <input type="submit" value="Submit"/>
            </form:form>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>建立題目</h2>
            <form:form method="POST" enctype="multipart/form-data"
                       modelAttribute="topicForm">
                <form:label path="title">標題</form:label><br/>
                <form:input type="text" path="title" /><br/><br/>
                <form:label path="msg">內容</form:label><br/>
                <form:textarea path="msg" rows="5" cols="30" /><br/><br/>
                <form:input type="hidden" path="category" value="${cate}"/>
                <b>副件</b><br/>
                <input type="file" name="attachments" multiple="multiple"/><br/><br/>
                <input type="submit" value="Submit"/>
            </form:form>
        </c:if>



        <br/><br/>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/topic/${cate}" />">Return to Topic list</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/topic/${cate}" />">返回題目列表</a>
        </c:if>
    </body>
</html>
