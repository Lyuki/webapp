<!DOCTYPE html>
<html>
    <head>
        <title>Categories</title>
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
      
        <h2>Categories</h2>
        <ul>
            <li><a href="<c:url value="/Lecture" />">Lecture</a><br /><br /></li>
            <li><a href="<c:url value="/Lab" />">Lab</a><br /><br /></li>
            <li><a href="<c:url value="/Other" />">Other</a><br /><br /></li>
        </ul>
        
        
        
        <security:authorize access="isAuthenticated()">
          <h2>Poll:</h2>
          <form action="checkboxes" method="POST">
            Select the fruits you like to eat: <br/>
            <input type="radio" name="fruit" value="ans1"/> 1 <br/>
            <input type="radio" name="fruit" value="ans2"/> 2 <br/>
            <input type="radio" name="fruit" value="ans3"/> 3 <br/>
            <input type="radio" name="fruit" value="ans4"/> 4 <br/>
            <br/><input type="submit" value="Submit"/>
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
