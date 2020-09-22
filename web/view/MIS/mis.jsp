<%-- 
    Document   : mis
    Created on : Dec 20, 2017, 2:21:36 PM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
    jQuery(function(){

       
         $("#date_from").datepicker({
                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
        });
        $("#date_to").datepicker({
                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
        });
    


    });




</script>
    </head>
    <body>
         <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
             <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>MIS</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                    </table>
                    <form name="form0" method="get" action="MISController" target="_blank">
<table>
                    <tr>
<!--                                  <th class="heading1">From</th>
                                <td>
                                          <input class="input " type="text" id="date_from" name="date_from" value="" size="12" disabled>
                                 </td>
                               <th class="heading1">TO</th>
                                      <td>
                                       <input class="input " type="text" id="date_to" name="date_to" value="" size="12" disabled>
                                   </td>-->
                              </tr>
                              <tr>

                                   <tr>
                               <td colspan="12" style="text-align: center">
                                   <b> Select Type </b>
                                   <select name="select_type" id="select_type" onchange="Status()" value="">
                                       <option value="select">Select</option>
                                       <option value="attendance">Attendance</option>
                                       <option value="error">Error</option>
                                       <option value="shift">Shift</option>
                                       <option value="vehicle">Vehicle</option>
                                       
                                   </select>
                              </td>
                           </tr>

                          </tr>
                          <tr>

                              </tr>
                          <tr>
                              <tr>
                              <td></td><td><input class="button" type="submit" name="task" id="save" value="Search Records"></td>
                              </tr>
                          </tr>

</table>
                    </form>
                </DIV>
</td>
                    </table>
    </body>
</html>
