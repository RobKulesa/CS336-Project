<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accounts View</title>
</head>
<body>
<span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span><br>
<span style="font-size:20px;">Accounts View</span>
<br><br>
<c:forEach items="${accounts}" var="account">
    <span style="font-size:20px;">Username: ${account.username}</span><br>
    <span style="font-size:17px;">Password: ${account.password}</span><br>
    <form action="<%=request.getContextPath()%>/AccountInfoServlet" method="get">
        <input type="text" name="newusername" placeholder="New Username">
        <input type="text" name="newpwd" placeholder="New Password">
        <input type="hidden" name="reqtype" value="editinfo"/>
        <input type="hidden" name="uid" value="${account.uid}"/>
        <input type="submit" value="Edit Account Info">
    </form>
    <form action="<%=request.getContextPath()%>/AccountInfoServlet" method="get">
        <input type="hidden" name="reqtype" value="deleteaccount"/>
        <input type="hidden" name="uid" value="${account.uid}"/>
        <input type="submit" value="Delete Account">
    </form><br>
    <span style="font-size:17px;">&nbsp;&nbsp;&nbsp;&nbsp;Auctions:</span><br>
    <table>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;Part Number</td>
            <td>Brand</td>
            <td>Model</td>
        </tr>
        <c:forEach items="${account.usersAuctions}" var="auction">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;${auction.part_number}</td>
                <td>${auction.brand}</td>
                <td>${auction.model}</td>
                <td>
                    <form action="<%=request.getContextPath()%>/AccountInfoServlet" method="get">
                        <input type="hidden" name="reqtype" value="deleteauction"/>
                        <input type="hidden" name="aid" value="${auction.aid}"/>
                        <input type="submit" value="Delete Auction">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <span style="font-size:17px;">&nbsp;&nbsp;&nbsp;&nbsp;Bids:</span><br>
    <table>
        <tr>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;Part Number</td>
            <td>Brand</td>
            <td>Model</td>
            <td>Amount</td>
        </tr>
        <c:forEach items="${account.usersBids}" var="bid">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;${bid.part_number}</td>
                <td>${bid.brand}</td>
                <td>${bid.model}</td>
                <td>${bid.amnt}</td>
                <td>
                    <form action="<%=request.getContextPath()%>/AccountInfoServlet" method="get">
                        <input type="hidden" name="reqtype" value="deletebid"/>
                        <input type="hidden" name="bid" value="${bid.bid}"/>
                        <input type="submit" value="Delete Bid">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
</c:forEach>
<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="CustomerRep.jsp">
    <input type="submit" value="Go Back!">
</form>
</body>
</html>
