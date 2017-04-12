<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                New Poll
            </c:if>
            <c:if test="${language == 'Chinese'}">
                新投票
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
        
        <br/><br/>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/" />">Return to index page</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/" />">返回主頁</a>
        </c:if>


        <c:if test="${language == 'English'}">
            <h2>Create a Poll</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="pollForm">
            <form:label path="question">Question</form:label><br/>
            <form:textarea path="question" rows="5" cols="30" /><br/><br/>
            <form:label path="ans1">Answer 1</form:label><br/>
            <form:input type="text" path="ans1" /><br/><br/>
            <form:label path="ans2">Answer 2</form:label><br/>
            <form:input type="text" path="ans2" /><br/><br/>
            <form:label path="ans3">Answer 3</form:label><br/>
            <form:input type="text" path="ans3" /><br/><br/>
            <form:label path="ans4">Answer 4</form:label><br/>
            <form:input type="text" path="ans4" /><br/><br/>
            <input type="submit" value="Create"/>
        </form:form>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>建立投票</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="pollForm">
            <form:label path="question">問題</form:label><br/>
            <form:textarea path="question" rows="5" cols="30" /><br/><br/>
            <form:label path="ans1">答案 1</form:label><br/>
            <form:input type="text" path="ans1" /><br/><br/>
            <form:label path="ans2">答案 2</form:label><br/>
            <form:input type="text" path="ans2" /><br/><br/>
            <form:label path="ans3">答案 3</form:label><br/>
            <form:input type="text" path="ans3" /><br/><br/>
            <form:label path="ans4">答案 4</form:label><br/>
            <form:input type="text" path="ans4" /><br/><br/>
            <input type="submit" value="建立"/>
        </form:form>
        </c:if>
        
    </body>
</html>