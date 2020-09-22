<%-- 
    Document   : updateVehicleError
    Created on : Dec 18, 2017, 2:28:47 PM
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
        <title>Error Log</title>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#vehicle_code").autocomplete("ErrorLogController", {
                    extraParams: {
                        action1: function() { return "getVehicleCode"}
                    }
                });
            });
           function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }
            function viewImage(){

        var error_id=document.getElementById("error_log_id").value;
        var error_causeby_id=document.getElementById("error_causeby_id").value;
        var date=document.getElementById("date").value;
        var vehicle_code=document.getElementById("vehicle_code").value;
        //alert(error_id);
        //alert(error_causeby_id);
        //alert(date);
        //alert(vehicle_code);
        var queryString = "error_id="+error_id+"&error_causeby_id="+error_causeby_id+"&date="+date+"&vehicle_code="+vehicle_code;
        var url = "ErrorLogController?task=updateVehicleRecord&" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }
         </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>

            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b> Update Vehicle Error</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="ErrorLogController">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">

                                            Vehicle Code<input type="text" class="new_input" name="vehicle_code" id="vehicle_code" size="19"/>
                                           <input type="button" class="button" name="task" id="delete" value="update" onclick="viewImage();">

                                            <input type="hidden" id="error_log_id" name="error_log_id" value="${error_log_id}">
                                            <input type="hidden" id="error_causeby_id" name="error_causeby_id" value="${error_causeby_id}">
                                            <input type="hidden" id="date" name="date" value="${date}">

                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
