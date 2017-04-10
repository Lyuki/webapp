<!DOCTYPE html>
<html>
    <head>
        <title>New Topic</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

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
            <br/><br/>
            <a href="<c:url value="/topic/${cate}" />">Return to Topic list</a>
    </body>
</html>
