<%@ page import="com.auctionsite.dao.AuctionDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Rep Logged In!</title>
</head>
<body onload="<%AuctionDao aucDao = new AuctionDao();
        aucDao.refreshAuctions(aucDao.allAuctions());%>">
Customer rep yeet
<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
