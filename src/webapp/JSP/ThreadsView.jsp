<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Threads View</title>
</head>
<body>
<c:set var="usertype" scope="session" value="${sessionScope.usertype}"/>
<span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span><br>
<c:if test="${usertype.equalsIgnoreCase('enduser')}">
    <form action="<%=request.getContextPath()%>/ThreadsServlet" method="get">
        <input type="text" name="question">
        <input type="hidden" name="reqtype" value="newpost"/>
        <input type="submit" value="Post New Question!">
    </form>
    <br>
    <form action="<%=request.getContextPath()%>/ThreadsServlet" method="get">
        <input type="text" name="keyword">
        <input type="hidden" name="reqtype" value="search"/>
        <input type="submit" value="Search Threads by Keyword!">
    </form>
</c:if>
<span style="font-size:20px;">Threads View</span>
<br><br>
<c:forEach items="${threads}" var="thread">
    <span style="font-size:20px;">Question: ${thread.msg}</span><br>
    <span style="font-size:17px;">Posted By User: ${thread.display_name}</span>
    <table>
        <c:forEach items="${thread.replies}" var="reply">
            <tr><td><span>&nbsp;&nbsp;&nbsp;&nbsp;Reply: ${reply}</span></td></tr>
        </c:forEach>
    </table>
    <c:if test="${usertype.equalsIgnoreCase('customerrep')}">
        <form action="<%=request.getContextPath()%>/ThreadsServlet" method="get">
            &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="reply">
            <input type="hidden" name="ticketnum" value="${thread.ticket_num}"/>
            <input type="submit" value="Add Reply!">
        </form>
    </c:if>
    <br><br>
</c:forEach>
<c:if test="${usertype.equalsIgnoreCase('enduser')}">
    <form action="<%=request.getContextPath()%>/ThreadsServlet" method="get">
        <input type="hidden" name="reqtype" value="clear"/>
        <input type="submit" value="Clear Search Results!">
    </form>
    <br>
</c:if>
<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="EndUser.jsp">
    <input type="submit" value="Go Back!">
</form>
</body>
</html>
