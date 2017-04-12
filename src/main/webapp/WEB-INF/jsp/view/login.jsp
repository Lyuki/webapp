<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                Course Discussion Forum Login
            </c:if>
            <c:if test="${language == 'Chinese'}">
                登入
            </c:if>
        </title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <c:if test="${language == 'English'}">
                <p>Login failed. The username and password you entered are not correct. Please try again.</p>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                用戶名稱或密碼不正確，請重試。
            </c:if>  
        </c:if>

        <c:if test="${param.logout != null}">
            <c:if test="${language == 'English'}">
                <p>You have logged out.</p>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                登出成功
            </c:if>  
        </c:if>

        <c:if test="${language == 'English'}">
            <h2>Course Discussion Forum Login</h2>
            <form action="login" method="POST">
                <label for="username">Username:</label><br/>
                <input type="text" id="username" name="username" /><br/><br/>
                <label for="password">Password:</label><br/>
                <input type="password" id="password" name="password" /><br/><br/>
                <input type="checkbox" id="remember-me" name="remember-me" />
                <label for="remember-me">Remember me</label><br/><br/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="Log In"/><br/><br/>
                <a href="<c:url value="/user/create" />">Register</a>
            </form>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>登入</h2>
            <form action="login" method="POST">
                <label for="username">用戶名稱:</label><br/>
                <input type="text" id="username" name="username" /><br/><br/>
                <label for="password">密碼:</label><br/>
                <input type="password" id="password" name="password" /><br/><br/>
                <input type="checkbox" id="remember-me" name="remember-me" />
                <label for="remember-me">記住我</label><br/><br/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="登入"/><br/><br/>
                <a href="<c:url value="/user/create" />">新用戶註冊</a>
            </form>
        </c:if>          

        <br/><br/>
        <c:if test="${language == 'English'}">
            <a href="<c:url value="/" />">Return to index page</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/" />">返回主頁</a>
        </c:if>
    </body>
</html>
