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
            <form action="checkboxes" method="POST">       
                <c:forEach items="${poll}" var="poll">
                <c:out value="${poll.question}"/><br/>
                <input type="radio" name="ans" value="ans1"/><c:out value="${poll.ans1}"/><br/>
                <input type="radio" name="ans" value="ans2"/><c:out value="${poll.ans2}"/><br/>
                <input type="radio" name="ans" value="ans3"/><c:out value="${poll.ans3}"/><br/>
                <input type="radio" name="ans" value="ans4"/><c:out value="${poll.ans4}"/><br/>
                <br/><input type="submit" value="Submit"/>
                </c:forEach>
            </form>
        </security:authorize>

        <h2>Poll Result:</h2>
          Select the fruits you like to eat: <br/>
          Result 1: 0<br/>
          Result 2: 0<br/>
          Result 3: 0<br/>
          Result 4: 0<br/>
    </body>
</html>
