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
            <a href="<c:url value="/login" />">Login</a>  
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
            <c:if test="${vote.id == 0}">
            <form:form enctype="multipart/form-data" method="POST" modelAttribute="VoteForm">       
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
                
            </form:form>  
           </c:if>
            <c:if test="${vote.id != 0}">
                Thank you for your participation.
            </c:if>
        </security:authorize>

        <h2>Poll Result:</h2>
        Select the fruits you like to eat: <br/>
        Result 1: 0<br/>
        Result 2: 0<br/>
        Result 3: 0<br/>
        Result 4: 0<br/>
    </body>
</html>
