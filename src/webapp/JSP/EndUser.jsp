<%@ page import="com.auctionsite.dao.AuctionDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>End User Logged In!</title>
</head>
<%
    HttpSession ses = request.getSession();
    String username = (String)ses.getAttribute("enduser");
    out.print("Welcome " + username + "!");
    //session.setAttribute("username",username);
%>
<body onload="<%AuctionDao aucDao = new AuctionDao();
        aucDao.refreshAuctions(aucDao.allAuctions());%>">

<form action="JSP/createAuc.jsp" method="get">
    <input type="submit" value="Create an Auction">
</form>

<form action="JSP/viewAuc.jsp" method="get">
    <input type="hidden" name="uid" value=<%=(String)request.getAttribute("uid")%>>
    <input type="submit" value="View Current Auctions">
</form>

<form action="JSP/viewMessages.jsp" method="get">
    <input type="hidden" name="uid" value=<%=(String)request.getAttribute("uid")%>>
    <input type="submit" value="View Alerts">
</form>

<form action="<%=request.getContextPath()%>/ThreadsServlet" method="post">
    <input type="submit" value="View Threads">
</form>

<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
