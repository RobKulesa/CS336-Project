<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Homepage</title>
</head>
<body>
<p>Create an account for a customer representative</p>
<form action="<%=request.getContextPath()%>/CreateUserServlet" method="post">
    <table>
        <tr>
            <td>NEWREP Username</td>
            <td><label>
                <input type="text" name="newusername">
            </label></td>
        </tr>
        <tr>
            <td>NEWREP Password</td>
            <td><label>
                <input type="password" name="newpassword">
            </label></td>
        </tr>
        <tr>
            <td><span style="color:red"><%=(request.getAttribute("createMessage") == null) ? "" : request.getAttribute("createMessage")%></span></td>
        </tr>
    </table>
    <input type="submit" value="Create rep account!">
    <input type="hidden" name="newusertype" value="customer rep">
    <input type="hidden" name="origin" value="Admin.jsp">
</form>
<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
