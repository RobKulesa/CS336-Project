<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.auctionsite.dao.AuctionDao" %>
<%@ page import="com.auctionsite.dao.AlertDao" %>
<%@ page import="com.auctionsite.beans.AlertBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<html>
<head>
    <title>View Messages below</title>
</head>

<body onload="<%AuctionDao aucDao = new AuctionDao();
        aucDao.refreshAuctions(aucDao.allAuctions());%>">
<table>
    <tr>
        <td>
            <p> Message Received At: </p>
        </td>
        <td>
            <p>    ||    Message Body: </p>
        </td>
    </tr>
   <%
       HttpSession session1 = request.getSession(false);
       AlertDao alertDao = new AlertDao();
       ArrayList<AlertBean> alerts = alertDao.getAllAlerts(Integer.parseInt((String)session1.getAttribute("uid")));
       for(AlertBean alert : alerts){
           out.print("<tr>");
           out.print("<td>");
           out.print(alert.getSendDateTime().toString());
           out.print("</td>");
           out.print("<td>");
           out.print(alert.getThread());
           out.print("</td>");
           out.print("</tr>");
       }
   %>

</table>


<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="EndUser.jsp">
    <input type="submit" value="Go Back!">
</form>
</body>

</html>

</html>
