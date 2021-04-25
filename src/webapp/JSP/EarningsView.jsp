<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Earnings View</title>
</head>
<body>
${earningsBean.type}
<table>
    <tr>
        <c:forEach items="${earningsBean.columnNames}" var="column">
            <td>${column}</td>
        </c:forEach>
    </tr>
    <c:forEach items="${earningsBean.data}" var="row">
        <tr>
            <c:forEach items="${row}" var="str">
                <td>${str}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
<br><br>
<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="Admin.jsp">
    <input type="submit" value="Go Back!">
</form>
</body>
</html>