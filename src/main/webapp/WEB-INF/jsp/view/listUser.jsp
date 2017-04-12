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
        <security:authorize access="hasRole('ADMIN')">
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


            <c:if test="${language == 'English'}">
                <h2>Users</h2>
                <a href="<c:url value="/user/create" />">Create a User</a><br /><br />
                <c:choose>
                    <c:when test="${fn:length(allUsers) == 0}">
                        <i>There are no users in the system.</i>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Roles</th>
                                <th>Action</th>
                            </tr>
                            <c:forEach items="${allUsers}" var="user">
                                <tr>
                                    <td>${user.username}</td>
                                    <td>${user.password}</td>
                                    <td>${user.roles}</td>
                                    <td>[<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>]
                                        [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${language == 'Chinese'}">
                <h2>用戶</h2>
                <a href="<c:url value="/user/create" />">建立用戶</a><br /><br />
                <c:choose>
                    <c:when test="${fn:length(allUsers) == 0}">
                        <i>沒有用戶</i>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th>用戶名稱</th>
                                <th>密碼</th>
                                <th>角色</th>
                                <th>行動</th>
                            </tr>
                            <c:forEach items="${allUsers}" var="user">
                                <tr>
                                    <td>${user.username}</td>
                                    <td>${user.password}</td>
                                    <td>${user.roles}</td>
                                    <td>[<a href="<c:url value="/user/edit/${user.username}" />">修改</a>]
                                        [<a href="<c:url value="/user/delete/${user.username}" />">刪除</a>]</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </security:authorize>
    </body>
</html>
