<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                Course Discussion Forum Register
            </c:if>
            <c:if test="${language == 'Chinese'}">
                註冊
            </c:if>

        </title>
    </head>
    <body>
        <c:if test="${param.error != null}">
            <c:if test="${language == 'English'}">
                <p>Register failed.</p>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                註冊失敗
            </c:if>
        </c:if>

        <c:if test="${language == 'English'}">
            <h2>Course Discussion Forum Register</h2>
            <form:form method="POST"  enctype="multipart/form-data" modelAttribute="user">
                <form:label path="username">Username</form:label><br/>
                <form:input type="text" path="username" /><br/><br/>
                <form:label path="password">Password</form:label><br/>
                <form:input type="password" path="password" /><br/><br/>
                <security:authorize access="isAnonymous()">
                    <form:hidden path="roles" value="ROLE_USER" checked="checked"/>  
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                <form:label path="roles">Roles</form:label><br/>
                <form:radiobutton path="roles" value="ROLE_USER" />ROLE_USER
                <form:radiobutton path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
                    </security:authorize>
                <br /><br />

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" name="addUser" value="Register"/>
            </form:form>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <h2>註冊</h2>
            <form:form method="POST"  enctype="multipart/form-data" modelAttribute="user">
                <form:label path="username">用戶名稱</form:label><br/>
                <form:input type="text" path="username" /><br/><br/>
                <form:label path="password">密碼</form:label><br/>
                <form:input type="password" path="password" /><br/><br/>
                <security:authorize access="isAnonymous()">
                    <form:hidden path="roles" value="ROLE_USER" checked="checked"/>  
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                <form:label path="roles">角色</form:label><br/>
                <form:radiobutton path="roles" value="ROLE_USER" />用戶
                <form:radiobutton path="roles" value="ROLE_ADMIN" />管理員
                    </security:authorize>
                <br /><br />

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" name="addUser" value="註冊"/>
            </form:form>
        </c:if>
    </body>
</html>
