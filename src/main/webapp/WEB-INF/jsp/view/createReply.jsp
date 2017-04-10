<!DOCTYPE html>
<html>
    <head>
        <title>New Reply</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

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
         <a href="<c:url value="/topic" />">Return to topic list</a>
    </body>
</html>