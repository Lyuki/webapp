<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <a href="<c:url value="/login" />">Login</a><br /><br />  
        </security:authorize>
        <br />
        <security:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/user/list" />">Manage User Account</a><br /><br />  
        </security:authorize>

        <h2>Categories</h2>
        <ul>
            <li><a href="<c:url value="/Lecture" />">Lecture</a><br /><br /></li>
            <li><a href="<c:url value="/Lab" />">Lab</a><br /><br /></li>
            <li><a href="<c:url value="/Other" />">Other</a><br /><br /></li>
        </ul>

        <security:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/createPoll" />">Create Poll</a><br /><br />
        </security:authorize>

        <security:authorize access="isAuthenticated()">
            <h2>Poll:</h2>
            <form:form enctype="multipart/form-data" method="POST" modelAttribute="VoteForm">       
                <c:forEach items="${poll}" var="poll">
                    <c:out value="${poll.question}"/><br/>
                    <form:input type="hidden" path="pollId" value="${poll.id}" />              
                    <form:radiobutton path="ansId" value="1"/><form:label path="ansId">
                        ${poll.ans1}</form:label><br/>
                        <form:radiobutton path="ansId" value="2"/><form:label path="ansId">
                        ${poll.ans2}</form:label><br/>
                        <form:radiobutton path="ansId" value="3"/><form:label path="ansId">
                        ${poll.ans3}</form:label><br/>
                        <form:radiobutton path="ansId" value="4"/><form:label path="ansId">
                        ${poll.ans4}</form:label><br/>
                    <br/><input type="submit" value="Submit"/>
                </c:forEach>
            </form:form>                 
        </security:authorize>

        <h2>Poll Result:</h2>
        Select the fruits you like to eat: <br/>
        Result 1: 0<br/>
        Result 2: 0<br/>
        Result 3: 0<br/>
        Result 4: 0<br/>
    </body>
</html>
