<%--
    Document   : wardView
    Created on : Jul 11, 2014, 10:09:08 AM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
    jQuery(function(){

        $("#search_route_name").autocomplete("RouteNameCont.do", {
            extraParams: {
                action1: function() { return "getRoute"}
            }
        });
        $("#search_route_no").autocomplete("RouteNameCont.do", {
            extraParams: {
                action1: function() { return "getRouteNo"}
            }
        });
//        $("#search_route_type").autocomplete("RouteNameCont.do", {
//            extraParams: {
//                action1: function() { return "getRouteType"}
//            }
//        });
//        $("#route_type").autocomplete("RouteNameCont.do", {
//            extraParams: {
//                action1: function() { return "getRouteType"}
//            }
//        });
    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {

        //document.getElementById("route_type").disabled = false;
        document.getElementById("route_name").disabled = false;
        document.getElementById("route_no").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
        document.getElementById("cancel").disabled =true;
        document.getElementById("save_As").disabled =true;
        if(id == 'new')
        {
            $("#message").html("");
            document.getElementById("route_name_id").value=0;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 6);
            document.getElementById("ward_no").focus();
        }
        if(id == 'edit'){
            document.getElementById("save_As").disabled = false;
            document.getElementById("cancel").disabled = false;
        }
    }
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }
        else document.getElementById("clickedButton").value = "Delete";
    }

    function fillColumns(id) {
        //alert(id);
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        //alert(1);
        var noOfColumns = 5;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        //setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("route_name_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        //document.getElementById("route_type").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        //alert(2);
        document.getElementById("route_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("route_no").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //makeEditable('');
//alert(3);
        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and cancel button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }
    var popupwin = null;
    function getPdf(){
        //var search_route_type = $("#search_route_type").val();
        var search_route_name = $("#search_route_name").val();
          var search_route_no = $("#search_route_no").val();
        var queryString = "task=generateMapReport&search_route_name="+search_route_name+"&search_route_no="+search_route_no;
        var url = "RouteNameCont.do?"+queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

    }
    function getExcel(){
        //var search_route_type = $("#search_route_type").val();
        var search_route_name = $("#search_route_name").val();
          var search_route_no = $("#search_route_no").val();
        var queryString = "task=generateReport&search_route_name="+search_route_name+"&search_route_no="+search_route_no;
        var url = "RouteNameCont.do?" + queryString;
        popupwin = openPopUp(url, "List", 600, 900);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ward Page</title>
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
                        <tr>
                            <td>
                                <table align="center" >
                                    <tr>
                                        <td class="header_table"  align="center" width="1000"> Route Name </td> <%-- width="800" --%>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="RouteNameCont.do">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
<!--                                                <th>Route Type</th>
                                                <td> <input type="text"  name="search_route_type" size="20" id="search_route_type"  value="${search_route_type}" size="10"/></td>-->
                                                <th> Route Name </th>
                                                <td><input class="new_input" type="text" id="search_route_name" name="search_route_name" value="${search_route_name}" size="20" ></td>
                                                <th>Route No</th>
                                                <td><input class="input" type="text" id="search_route_no" name="search_route_no" value="${search_route_no}" size="4" ></td>
                                            </tr>
                                            <tr>
                                                <td colspan="12" align="center">  <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                    <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                    <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getPdf()">
                                                    <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="RouteNameCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                
                                                <th class="heading">Route Name</th>
                                                <th class="heading">Route No</th>
                                                <th class="heading">Remark</th>

                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="wardTypeBean" items="${requestScope['list']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >         
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${wardTypeBean.route_name_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                   
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.route_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.route_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.remark}</td>

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="8">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>

                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden"  name="search_route_type" value="${search_route_type}" >
                                            <input  type="hidden"  name="search_route_name" value="${search_route_name}" >
                                            <input  type="hidden"  name="search_route_no" value="${search_route_no}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="RouteNameCont.do">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
<!--                                            <tr>
                                                <th class="heading1">Route Type </th>
                                                <td>
                                                    <input class="input" type="hidden" id="route_name_id" name="route_name_id" value="" >
                                                    <input class="input" type="text" id="route_type" name="route_type" value="" size="40" disabled>
                                                </td>
                                            </tr>-->
                                            <tr>
                                                <th class="heading1">Route Name</th>
                                                <td> <input class="input" type="hidden" id="route_name_id" name="route_name_id" value="" >
                                                    <input class="new_input" type="text" id="route_name" name="route_name" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Route No</th>
                                                <td><input class="input" type="text" id="route_no" name="route_no" value="" size="40" disabled></td>
                                            </tr>


                                            <tr>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <td align='center' colspan="2">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="active" id="active" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input  type="hidden"  name="search_route_type" value="${search_route_type}" >
                                            <input  type="hidden"  name="search_route_name" value="${search_route_name}" >
                                            <input  type="hidden"  name="search_route_no" value="${search_route_no}" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
