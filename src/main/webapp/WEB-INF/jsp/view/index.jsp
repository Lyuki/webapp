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
            <a href="<c:url value="/login" />">
                <c:if test="${language == 'English'}">
                    Login
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    登入
                </c:if>
            </a>  
        </security:authorize>

        <a href="<c:url value="/chinese" />" style="float: right;">
            <c:if test="${language == 'English'}">
                中文
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
                English
            </c:if>
        </a>
        <br />

        <security:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/user/list" />">
                <c:if test="${language == 'English'}">
                    Manager User Account
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    使用者管理
                </c:if>
            </a><br /><br />  
        </security:authorize>

        <h2>
            <c:if test="${language == 'English'}">
                Category
            </c:if>
            <c:if test="${language == 'Chinese'}">
                類別
            </c:if>
        </h2>
        <ul>
            <li><a href="<c:url value="/Lecture" />">
                    <c:if test="${language == 'English'}">
                        Lecture
                    </c:if>
                    <c:if test="${language == 'Chinese'}">
                        演講
                    </c:if>           
                </a>(${fn:length(Lecture)})<br /><br /></li>
            <li><a href="<c:url value="/Lab" />">
                    <c:if test="${language == 'English'}">
                        Lab
                    </c:if>
                    <c:if test="${language == 'Chinese'}">
                        實踐
                    </c:if>
                </a>
                    (${fn:length(Lab)})<br /><br /></li>
            <li><a href="<c:url value="/Other" />">
                    <c:if test="${language == 'English'}">
                        Other
                    </c:if>
                    <c:if test="${language == 'Chinese'}">
                        其他
                    </c:if>
                </a>
                    (${fn:length(Other)})<br /><br /></li>
        </ul>

        <security:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/createPoll" />">
                <c:if test="${language == 'English'}">
                    Create Poll
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    建立投票
                </c:if>

            </a><br /><br />
        </security:authorize>

        <security:authorize access="isAuthenticated()">

            <h2>
                <c:if test="${language == 'English'}">
                    Poll:
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    投票：
                </c:if>
            </h2>
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
                <c:if test="${language == 'English'}">
                    Thank you for your participation.
                </c:if>
                <c:if test="${language == 'Chinese'}">
                    多謝你的參與。
                </c:if>

            </c:if>
        </security:authorize>

        <h2>
            <c:if test="${language == 'English'}">
                Poll Result:
            </c:if>
            <c:if test="${language == 'Chinese'}">
                投票結果：
            </c:if>     
        </h2>
        ${result.title}<br/><br/>
        ${result.ans1}: ${result.result1}<br/>
        ${result.ans2}: ${result.result2}<br/>
        ${result.ans3}: ${result.result3}<br/>
        ${result.ans4}: ${result.result4}<br/>
        
        <br/><br/><br/>
        <c:choose>
            <c:when test="${fn:length(pollHistory) == 0}">
                <i>There are no poll history in there</i>
            </c:when>
            <c:otherwise>
                <c:if test="${language == 'English'}">
                View History:
            </c:if>
            <c:if test="${language == 'Chinese'}">
                歷史回顧：
            </c:if>
                
                <ul>
                <c:forEach items="${pollHistory}" var="entry">    
                        <li><a href="<c:url value="/${entry.id}" />">
                                ${entry.question}</a>
                        </li>                               
                </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </body>
</html>
