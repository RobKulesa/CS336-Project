<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.auctionsite.dao.AuctionDao" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Bid Creation</title>
</head>
<body>
<div>
  <p> Create your automatic bid below </p>

  <form action="<%=request.getContextPath()%>/CreateBidServlet" method="post">
    <table>
      <tr>
        <td>Upper Limit</td>
        <td><label>
          <input type="text" name="upper_limit">
        </label></td>
      </tr>
      <tr>
        <td>Desired Increment</td>
        <td><label>
          <input type="text" name="desired_increment">
        </label></td>
      </tr>
      <tr>
        <td><span style="color:red"><%=(request.getAttribute("createMessage") == null) ? "" : request.getAttribute("createMessage")%></span></td>
      </tr>
      <tr>
        <td><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span></td>
      </tr>
    </table>
    <input type="hidden" name = "bid_increment" value = <%=request.getParameter("bid_increment")%>>
    <input type="hidden" name = "aid" value = <%=request.getParameter("aid")%>>
    <input type="submit" value="Place bid.">
  </form>
  <p></p>
  <p></p>
  <p></p>
  <form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="viewAuc.jsp">
    <input type="submit" value="Go Back!">
  </form>


</div>
</body>
</html>