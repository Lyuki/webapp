<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                Poll creation
            </c:if>
            <c:if test="${language == 'Chinese'}">
                建立投票
            </c:if>
            
        </title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <p>Create failed.</p>
        </c:if>
            
        <c:if test="${param.question != null}">
            <b>Question should not be empty!</b><br /><br />
        </c:if>
            
        <c:if test="${param.ans1 || param.ans2 || param.ans3 || param.ans4 != null}">
            <b>Answer should not be empty!</b><br /><br />
        </c:if>

        <h2>Create new poll</h2>
        <form:form method="POST"  enctype="multipart/form-data" modelAttribute="poll">
            <label for="question">Question:</label><br/>
            <input type="text" id="question" name="question" /><br/><br/>
            <label for="ans1">Answer 1:</label><br/>
            <input type="text" id="ans1" name="ans1" /><br/><br/>
            <label for="ans2">Answer 2:</label><br/>
            <input type="text" id="ans2" name="ans2" /><br/><br/>
            <label for="ans3">Answer 3:</label><br/>
            <input type="text" id="ans3" name="ans3" /><br/><br/>
            <label for="ans4">Answer 4:</label><br/>
            <input type="text" id="ans4" name="ans4" /><br/><br/>
            <input type="submit" name="create" value="Create"/>
        </form:form>
        
    </body>
</html>
