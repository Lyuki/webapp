<!DOCTYPE html>
<html>
    <head>
        <title>
            <c:if test="${language == 'English'}">
                Course Discussion Forum
            </c:if>
            <c:if test="${language == 'Chinese'}">
                課程討論區
            </c:if>
        </title>
    </head>
    <body>
        <security:authorize access="isAuthenticated()">
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
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <c:if test="${language == 'English'}">
                <a href="<c:url value="/login" />">Login</a><br /><br />
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <a href="<c:url value="/login" />">登入</a><br /><br />
            </c:if> 
        </security:authorize>
        <br />

        <c:if test="${language == 'English'}">
            <a href="<c:url value="/" />">Return to index page</a>
        </c:if>
        <c:if test="${language == 'Chinese'}">
            <a href="<c:url value="/" />">返回主頁</a>
        </c:if>

        <security:authorize access="hasRole('ADMIN')">
            <c:if test="${language == 'English'}">
                <h2>Edit User</h2>

                <form:form method="POST"  enctype="multipart/form-data" modelAttribute="edituser">
                    Username: ${username}<br/><br/>
                    <form:label path="password">Password</form:label><br/>
                    <form:input type="password" path="password" /><br/><br/>
                    <form:label path="roles">Roles</form:label><br/>
                    <form:radiobutton path="roles" value="ROLE_USER" />ROLE_USER
                    <form:radiobutton path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
                    <br /><br />

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" name="editUser" value="Edit"/>
                </form:form>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <h2>修改用戶</h2>
                <form:form method="POST"  enctype="multipart/form-data" modelAttribute="edituser">
                    用戶名稱: ${username}<br/><br/>
                    <form:label path="password">密碼</form:label><br/>
                    <form:input type="password" path="password" /><br/><br/>
                    <form:label path="roles">角色</form:label><br/>
                    <form:radiobutton path="roles" value="ROLE_USER" />用戶
                    <form:radiobutton path="roles" value="ROLE_ADMIN" />管理員
                    <br /><br />

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" name="editUser" value="修改"/>
                </form:form>
            </c:if>
        </security:authorize>
    </body>
</html>
