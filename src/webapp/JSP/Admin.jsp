<%@ page import="com.auctionsite.dao.AuctionDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Homepage</title>
</head>
<body onload="<%AuctionDao aucDao = new AuctionDao();
    aucDao.refreshAuctions(aucDao.allAuctions());%>">
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
<form action="<%=request.getContextPath()%>/EarningsServlet" method="post">
    <p>Run Earnings Report:</p>
    <input type="radio" id="1" name="earningstype" value="1">
    <label for="1">Total Earnings</label><br>
    <input type="radio" id="2" name="earningstype" value="2">
    <label for="2">Earnings Per Item</label><br>
    <input type="radio" id="3" name="earningstype" value="3">
    <label for="3">Earnings Per Item Type</label><br>
    <input type="radio" id="4" name="earningstype" value="4">
    <label for="4">Earnings Per End User</label><br>
    <input type="radio" id="5" name="earningstype" value="5">
    <label for="5">Earnings By x Best-Selling Items (Enter x:)</label>
    <input type="number" name="limit5" value="5">
    <br>
    <input type="radio" id="6" name="earningstype" value="6">
    <label for="6">Earnings By x Best Buyers (Enter x:)</label>
    <input type="number" name="limit6" value="6">
    <br>
    <span style="color:red"><%=(request.getAttribute("earningsMessage") == null) ? "" : request.getAttribute("earningsMessage")%></span>
    <input type="submit" value="Run Earnings Report">
</form>
<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>