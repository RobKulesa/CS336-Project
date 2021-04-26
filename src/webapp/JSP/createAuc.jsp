<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <title>Make an Auction</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
    <input type="hidden" name="origin" value="EndUser.jsp">
    <input type="submit" value="Go Back!">
</form>
<h2>Create an Auction</h2>
</div>
<div>

    <form action="aucInsert.jsp" method="GET">
        <h3>Select an Item Type</h3>

        <select name="item">
            <option selected="selected" disabled="disabled">Item</option>
            <option value="keyboard">Keyboard</option>
            <option value="mouse">Mouse</option>
            <option value="monitor">Monitor</option>
        </select>
        <br></br>

        <select name="condition">
            <option selected="selected" disabled="disabled">Condition</option>
            <option value="new">New</option>
            <option value="like new">Like New</option>
            <option value="used">Used</option>
            <option value="damaged">Damaged</option>

        </select>
        <br></br>

        Brand* &nbsp;<input type="text" name="brand"/>
        <br></br>

        Part* &nbsp;<input type="text" name="part"/>
        <br></br>

        Model* &nbsp;<input type="text" name="model"/>
        <br></br>

        <h3>Keyboard Details</h3>
        <select name="switches">
            <option selected="selected" disabled="disabled">Switches</option>
            <option value="brown">MX Brown</option>
            <option value="red">MX Red</option>
            <option value="blue">MX Blue</option>
        </select>
        <br></br>

        <select name="kwire">
            <option selected="selected" disabled="disabled">Wired/Wireless</option>
            <option value="wired">Wired</option>
            <option value="wireless">Wireless</option>
        </select>
        <br></br>

        Layout* &nbsp;<input type="text" name="klayout"/>
        <br></br>
        <h3>Mouse Details</h3>
        <select name="mwire">
            <option selected="selected" disabled="disabled">Wired/Wireless</option>
            <option value="wired">Wired</option>
            <option value="wireless">Wireless</option>
        </select>
        <br></br>

        Weight* &nbsp;<input type="text" name="weight"/>
        <br></br>

        DPI* &nbsp;<input type="text" name="dpi"/>

        <h3>Monitor Details</h3>
        Display Type* &nbsp;<input type="number" name="dtype"/>
        <br></br>

        Display Size* &nbsp;<input type="number" name="dsize"/>
        <br></br>

        Refresh Rate* &nbsp;<input type="number" name="refresh"/>
        <br></br>

        <h3>Auction Settings</h3>

        Closing Date* &nbsp;<input type="date" name="close_date"/>
        <br></br>
        Closing Time* &nbsp;<input type="time" name="close_time"/>
        <br></br>
        Minimum Increment Price* &nbsp;<input type="number" name="min_increment" min="0.01" step="0.01"/>
        <br></br>
        Hidden Minimum Price &nbsp;<input type="number" name="min_price" min="0.00" step="0.01"/>
        <br></br>
        <input type="submit" value="Submit"/>
        &#09;*Required fields
    </form>
</div>


</body>
</html>