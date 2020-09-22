

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shift</title>
        <script type="text/javascript">
                </script>
    </head>
    <body >





        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <c:choose>
                    <c:when test="${designation eq 'ड्राइवर'}">
                        <td><%@include file="/layout/menu.jsp" %></td>
                    </c:when>
                        <c:when test="${designation eq 'RWA'}">
                        <td><%@include file="/layout/rwa_menu.jsp" %></td>
                    </c:when>
                        <c:when test="${designation eq 'पब्लिक'}">
                        <td><%@include file="/layout/benificiary_menu.jsp" %></td>
                    </c:when>
                        <c:when test="${designation eq 'Clerk'}">
                        <td><%@include file="/layout/clerk_menu.jsp" %></td>
                    </c:when>
                    <c:otherwise>
                        <td><%@include file="/view/login/before_login_home.jsp" %></td>
                    </c:otherwise>
                </c:choose>

            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">




                    </table>
                    <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                </DIV>
            </td>

        </table>

    </body>
</html>
