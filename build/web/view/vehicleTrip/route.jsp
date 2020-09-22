<%--
    Document   : route
    Created on : Nov 14, 2016, 6:22:17 PM
    Author     : Manpreet Kaur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bus Trip: Route</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

            var popupwin = null;

            jQuery(function(){
                $("#search_route_name").autocomplete("RouteCont.do", {
                    extraParams: {
                        action1: function() { return "getRouteName"}
                                            }
                    });

                    $("#search_stopage_name").autocomplete("RouteCont.do", {
                    extraParams: {
                        action1: function() { return "getStopageName"}
                                            }
                    });

                    $("#route_name").autocomplete("RouteCont.do", {
                    extraParams: {
                        action1: function() { return "getInputRouteName"}
                                            }
                    });

                    $("#stopage_name").autocomplete("RouteCont.do", {
                    extraParams: {
                        action1: function() { return "getInputStopageName"}
                                            }
                    });
            });

            function makeEditable(id) {
                document.getElementById("route_name").disabled = false;
                document.getElementById("stopage_name").disabled = false;
                  document.getElementById("order_no").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("route_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                    document.getElementById("route_name").focus();
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }

            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 4;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("route_id").value = document.getElementById("route_id" + rowNo).value;
                document.getElementById("route_name").value = (document.getElementById(t1id + (lowerLimit + 1)).innerHTML).replace('-', '&');
                document.getElementById("stopage_name").value = (document.getElementById(t1id + (lowerLimit + 2)).innerHTML).replace('-', '&');
                 document.getElementById("order_no").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
              for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)
                {// if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }

            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
//                else if(id == 'search'){
//                    var org_name=document.getElementById("org_name").value;
//                    document.getElementById("org_name1").value =  org_name;
//                    document.getElementById("org_name2").value =  org_name;
//                    document.getElementById("clickedButton").value = "SEARCH";
//                }
//                else if(id == 'clear'){
//                    document.getElementById("clickedButton").value = " ";
//                    $("#route_msg").html("");
//                    document.getElementById("org_name").value =" ";
//                    document.getElementById("org_name1").value =  " ";
//                    document.getElementById("org_name2").value =  " ";
//                }
                else {
                    document.getElementById("clickedButton").value = "Delete";;
                }
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

            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {

                    var route_name = document.getElementById("route_name").value;
                    if(myLeftTrim(route_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Route Name is required..</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Route Name is required..</b></td>");
                        document.getElementById("route_name").focus();
                        return false; // code to stop from submitting the form2.
                    }

                    var stopage_name = document.getElementById("stopage_name").value;
                    if(myLeftTrim(stopage_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Stoppage Name is required..</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Stoppage Name is required..</b></td>");
                        document.getElementById("stopage_name").focus();
                        return false; // code to stop from submitting the form2.
                    }

                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as a New record?")
                        return result;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }

            function verifySearch(){
                var result;
                if(document.getElementById("clickedButton").value == 'SEARCH') {
                    var search_route_name = document.getElementById("search_route_name").value;
                    if(myLeftTrim(search_route_name).length == 0) {
                        document.getElementById("route_msg").innerHTML = "<b>Route Name is required..</b>";
                        document.getElementById("search_route_name").focus();
                        return false; // code to stop from submitting the form2.
                    }

                    var search_stopage_name = document.getElementById("search_stopage_name").value;
                    if(myLeftTrim(search_stopage_name).length == 0) {
                        document.getElementById("route_msg").innerHTML = "<b>Stopage Name is required..</b>";
                        document.getElementById("search_stopage_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                }
            }

            function displayRouteList(id){
                var queryString;
                var search_route_name = document.getElementById("search_route_name").value;
                var search_stopage_name = document.getElementById("search_stopage_name").value;

                if(id=='viewPdf')
                queryString = "requester=PRINT";
            else
                queryString = "requester=PRINTXls";
                var url = "RouteCont.do?"+queryString+"&search_route_name="+search_route_name+"&search_stopage_name="+search_stopage_name;
                popupwin = openPopUp(url, "Route", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }

        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>

                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">

                            <tr>
                                <td>
                                    <table align="center" >
                                        <tr>
                                            <td class="header_table"  align="center" width="1000"> Route Details </td> <%-- width="800" --%>
                                            <%-- <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td> --%>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="RouteCont.do" > <%-- onsubmit="return verifySearch();" --%>
                                        <table  class="heading1" > <%-- width="700" --%>
                                            <tr>
                                                <td colspan="4" align="center">
                                                    Route Name<input type="text" class="new_input" id="search_route_name" name="search_route_name" value="${searchRouteName}" size="25">
                                                    Point Name<input type="text" class="new_input" id="search_stopage_name" name="search_stopage_name" value="${searchStopageName}" size="25">
                                                    <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                    <input type="submit" class="button" name="task" id="showAllRecords" value="Show All Records">
                                                    <%-- <input type="submit" class="button" id="search" name="search" value="SEARCH" onclick="setStatus(id)">
                                                    <input type="submit" class="button" id="clear" name="clear" value="CLEAR" onclick="setStatus(id)"> --%>
                                                    &ensp; <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayRouteList(id)">
                                                      <input type="button" class="button" id="viewXls" name="viewXls" value="Excel" onclick="displayRouteList(id)">
                                                     <label id="route_msg">  </label> 
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="RouteCont.do">
                                        <DIV class="content_div">
                                            <table border="1" id="table1" align="center"  class="content" > <%-- width="650" --%>
                                                <tr>
                                                    <th class="heading"  style="width: 20px">S.No.</th>
                                                    <th class="heading">Route Name</th>
                                                    <th class="heading">Point Name</th>
                                                    <th class="heading"  style="width: 20px">Order No.</th>
                                                </tr>
                                                <c:forEach var="routeDetails" items="${requestScope['routeDetailsList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="route_id${loopCounter.count}" value="${routeDetails.route_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${routeDetails.route_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${routeDetails.stopage_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" align="center" >${routeDetails.order_no}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="4">
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
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" name="search_route_name" value="${searchRouteName}">
                                                <input type="hidden" name="search_stopage_name" value="${searchStopageName}">
                                                <%-- <input type="hidden" id="org_name2" name="org_name" value="${org_name}"> --%>
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="RouteCont.do" onsubmit="return verify()">
                                        <table class="content" border="0" id="table2" align="center" > <%-- width="650" --%>

                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Route Name</th>
                                                <td>
                                                    <input class="input new_input" type="hidden" id="route_id" name="route_id" value="" >
                                                    <input class="input new_input" type="text" id="route_name" name="route_name" size="25" value="${route_name}" disabled>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Point Name</th>
                                                <td>
                                                    <input class="input new_input" type="text" id="stopage_name" name="stopage_name" size="25" value="" disabled>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Order No</th>
                                                <td>
                                                    <input class="input " type="text" id="order_no" name="order_no" size="25" value="" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="2" >
                                                    <input type="button" class="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input type="submit" class="button" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input type="submit" class="button" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input type="reset" class="button" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input type="submit" class="button" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <%-- <input type="hidden" id="org_name1" name="org_name" value="${org_name}"> --%>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

