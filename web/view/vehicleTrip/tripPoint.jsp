<%--
    Document   : tripstopage
    Created on : Nov 14, 2016, 1:02:22 PM
    Author     : Nishu
--%>

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
            jQuery(function(){
                $("#searchTime").autocomplete("TripPointCont.do", {
                    extraParams: {
                        action1: function() { return "getTime" }
                    }
                });
                $("#searchRoute").autocomplete("TripPointCont.do", {
                    extraParams: {
                        action1: function() { return "getRoute" }
                    }
                });
                $("#searchDay").autocomplete("TripPointCont.do", {
                    extraParams: {
                        action1: function() { return "getWeek" }
                    }
                });
                $("#route_name").autocomplete("TripPointCont.do", {
                    extraParams: {
                        action1: function() { return "getRoute" }
                    }
                });
                $("#stopage_name").autocomplete("TripPointCont.do", {
                    extraParams: {
                        action1: function() { return "getStopageName" },
                        trip_id: function() { return $("#start_time").val();}
                    }
                });
                $("#week_days").change(function() {
                    var day = $("#week_days").val();
                    var route_name = $("#route_name").val();
                    getTripStartTime(day,route_name);
                });

                $("#route_name").result(function(event, data, formatted) {
                    var route = data.toString();
                    getWeekDays(route);
                });

                $("#start_time").change(function(){
                    getStopageName();
                });
            });

            function getWeekDays(route){
                $.ajax({url: "TripPointCont.do",data: "action1=getTripDay&route=" +route.replace('&', '-'), success: function(response_data){
                            var responseArray = response_data.split("##");
                            var length = responseArray.length;
                            $("#week_days").html("");
                            var  opt = '<option  value=""> select</option>';
                            $("#week_days").append(opt);
                            for(var i=0; i < length; i++){
                                var array1 = responseArray[i];
                                var opt = "<option value='"+ array1 +"'>"+ array1 +"</option>";;
                                $("#week_days").append(opt);
                            }
                            var day = $("#weekDays").val();
                            if(day != ""){
                                $("#week_days").val(day);
                                getTripStartTime(day);
                            }
                        }
                    });
            }

            function getTripStartTime(day,route_name){
                $.ajax({url: "TripPointCont.do",data: "action1=getTripTime&day=" +day+"&route_name="+route_name, success: function(response_data){
                            var responseArray = response_data.split("##");
                            var length = responseArray.length;
                            $("#start_time").html("");
                            if(length == 1){
                                var opt = "<option value='"+ (responseArray[0].split("_")[0]) +"'>"+ (responseArray[0].split("_")[1]) +"</option>";
                                $("#start_time").append(opt);
                                getStopageName();
                            }
                            else{
                                var  opt = '<option  value=""> select</option>';
                                $("#start_time").append(opt);
                                for(var i=0; i < length; i++){
                                    var array1 = responseArray[i].split("_");
                                    opt = "<option value='"+ array1[0] +"'>"+ array1[1] +"</option>";
                                    $("#start_time").append(opt);
                                }
                                var startTime = $("#startTime").val();
                                if(startTime != ""){
                                    $("#start_time").val(startTime);
                                    getStopageName();
                                }
                            }
                        }
                    });
            }

            function getStopageName(){
                $.ajax({url: "TripPointCont.do",data: "action1=getStopageName&trip_id=" +$("#start_time").val(), success: function(response_data){
                        response_data = response_data.trim();
                        if(response_data == "")
                            $("#message").html("<td colspan='6' bgcolor='coral'><b> No more StopPage in this Trip...</b></td>");
                        else{
                            $("#stopage_name").val(response_data.trim());
                            $("#message").html("");
                            $("#starting_time_hour").focus();
                        }
                    }
                });
            }

            function fillColumns(id) {
               // alert(id);
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 11;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for(var i = 0; i < noOfRowsTraversed; i++)
                {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

                    if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
                }

                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns)
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                var trip_stopage_map_id = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("trip_stopage_map_id").value=trip_stopage_map_id;
                // document.getElementById("rev_no").value= document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                var route_name = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("route_name").value = route_name.replace('-', '&');
                document.getElementById("stopage_name").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML.replace('-', '&');;
                var week_days = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
                document.getElementById("week_days").innerHTML = '<option value="'+ week_days +'" selected>'+week_days +'</option>';
                //document.getElementById("location_code").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                document.getElementById("order_no").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                var start_time = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
                var trip_id = $("#trip_id"+trip_stopage_map_id).val();
                document.getElementById("start_time").innerHTML = '<option value="'+ trip_id +'" selected>'+start_time +'</option>';
                setStartingTime(document.getElementById(t1id +(lowerLimit+7)).innerHTML);
                setEndingTime(document.getElementById(t1id +(lowerLimit+8)).innerHTML);
                document.getElementById("latitude").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                document.getElementById("longitude").value=document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
                // document.getElementById("starting_time").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;

                // document.getElementById("ending_time").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;

                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }


                document.getElementById("edit").disabled = false;

                if(!document.getElementById("save").disabled)
                {
                    document.getElementById("save_as_new").disabled = true;
                    document.getElementById("delete").disabled = false;
                    // document.getElementById("revised").disabled = false;
                    dodument.getElementById("save").disabled=true;

                }

                $("#message").html("");
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                    }
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
            function viewMap(lattitude,longitude) {
                var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                var y = longitude;//$.trim(doc.getElementById(logitude).value);
                var url="TripPointCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
                popupwin = openPopUp(url, "",  580, 620);
            }
            function displayRouteList(id){
                var queryString;
                if(id=='viewPdf')
                    queryString = "requester=PRINT";
                else
                    queryString = "requester=PRINTXls";
                var url = "TripPointCont.do?"+queryString;
                popupwin = openPopUp(url, "Route", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }
            function verify() {

                var result;
                var clickedButton = document.getElementById("clickedButton").value;
                if(clickedButton == 'save' || clickedButton == 'Save AS New') {

                    var route_name = document.getElementById("route_name").value;
                    if(myLeftTrim(shift_type).length == 0) {
                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Route Name is required...</b></td>");
                        document.getElementById("route_name").focus();
                        return false;
                    }
                    var week_days = document.getElementById("week_days").value;
                    if(myLeftTrim(shift_type).length == 0) {
                        $("#message").html("<td colspan='6' bgcolor='coral'><b> week days is required...</b></td>");
                        document.getElementById("week_days").focus();
                        return false;
                    }
                    var starting_time_hour = document.getElementById("starting_time_hour").value;
                    if(myLeftTrim(starting_time_hour).length == 0) {

                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Starting Time Hour is required...</b></td>");
                        document.getElementById("starting_time_hour").focus();
                        return false;
                    }
                    var starting_time_min = document.getElementById("starting_time_min").value;
                    if(myLeftTrim(starting_time_min).length == 0) {

                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Starting Time Min is required...</b></td>");
                        document.getElementById("starting_time_min").focus();
                        return false;
                    }

                    var ending_time_hour = document.getElementById("ending_time_hour").value;
                    if(myLeftTrim(ending_time_hour).length == 0) {

                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Ending Time Hour is required...</b></td>");
                        document.getElementById("ending_time_hour").focus();
                        return false;
                    }
                    var ending_time_min = document.getElementById("ending_time_min").value;
                    if(myLeftTrim(ending_time_min).length == 0) {

                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Ending Time Min is required...</b></td>");
                        document.getElementById("ending_time_min").focus();
                        return false;
                    }
                    return result;

                }
            }

            function makeEditable(id) {
                document.getElementById("route_name").disabled = false;
                document.getElementById("week_days").disabled = false;
                document.getElementById("stopage_name").disabled = false;
                document.getElementById("order_no").disabled = false;
                document.getElementById("start_time").disabled = false;
                document.getElementById("starting_time_hour").disabled = false;
                document.getElementById("starting_time_min").disabled = false;
                document.getElementById("ending_time_hour").disabled = false;
                document.getElementById("ending_time_min").disabled = false;

                document.getElementById("save").disabled = true;

                if(id == 'new') {
                    $("#message").html("");
                    $("#start_time").html("");
                    $("#week_days").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_as").disabled =true;
                    document.getElementById("save").disabled =false;
                    document.getElementById("trip_stopage_map_id").value=0;
                    var route_name = $("#route_name").val();
                    if(route_name != ""){
                        getWeekDays(route_name);
                        //getTripStartTime(day);
                    }
                }
                if(id == 'edit'){
                    $("#message").html("");
                    document.getElementById("save_as").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = false;
                }
            }

            function setStatus(id) {

                if(id == 'save'){

                    document.getElementById("clickedButton").value = "save";
                }
                else if(id == 'save_as'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'delete'){
                    document.getElementById("clickedButton").value = "Delete";
                }

                else
                {}
            }
            function setStartTime(st){
                var array= st.split(":");
                document.getElementById("start_time_hour").value=array[0];
                document.getElementById("start_time_min").value=array[1];

            }
            function setStartingTime(st){
                var array= st.split(":");
                document.getElementById("starting_time_hour").value=array[0];
                document.getElementById("starting_time_min").value=array[1];

            }
            function setEndingTime(et){
                var array= et.split(":");
                document.getElementById("ending_time_hour").value=array[0];
                document.getElementById("ending_time_min").value=array[1];


            }
        </script>
    </head>
    <body >
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
                                        <td align="center" class="header_table" width="90%"><b>Trip Point</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="TripPointCont.do">
                                        <table align="center" class="heading1" width="700">
                                            <tr>
                                                <th>route Name</th>
                                                <td><input class="input new_input" type="text" id="searchRoute" name="searchRoute" value="${searchRoute}" size="30" ></td>
                                                <th>week days</th>
                                                <td><select class="dropdown3" id="searchDay" name="searchDay">
                                                        <option <c:if test="${searchDay == ''}">selected</c:if> value="">SELECT</option>
                                                        <option <c:if test="${searchDay == 'Sun'}">selected</c:if>>Sun</option>
                                                        <option <c:if test="${searchDay == 'Mon'}">selected</c:if>>Mon</option>
                                                        <option <c:if test="${searchDay == 'Tue'}">selected</c:if>>Tue</option>
                                                        <option <c:if test="${searchDay == 'Wed'}">selected</c:if>>Wed</option>
                                                        <option <c:if test="${searchDay == 'Thu'}">selected</c:if>>Thu</option>
                                                        <option <c:if test="${searchDay == 'Fri'}">selected</c:if>>Fri</option>
                                                        <option  <c:if test="${searchDay == 'Sat'}">selected</c:if>>Sat</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>start time</th>
                                                <td><input class="input " type="text" id="searchTime" name="searchTime" value="${searchTime}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayRouteList(id)"></td>
<!--                                                <td><input type="button" class="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayMapList(id)"></td>-->
                                                <td><input type="button" class="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayRouteList(id)"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>

                        <table>
                            <tr>
                                <td align="center">
                                    <div  class="content_div" style="width:990px" >
                                        <form name="form1" action="TripPointCont.do">


                                            <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th  class="heading">Route Name</th>
                                                    <th  class="heading">Point name</th>
                                                    <th  class="heading">Week days</th>
<!--                                                    <th  class="heading">location_code</th>-->
                                                    <th  class="heading">Order No.</th>
                                                    <th  class="heading">Start Time</th>
                                                    <th  class="heading">Arrival Time</th>
                                                    <th  class="heading">Departure Time </th>
                                                    <th  class="heading">Latitude </th>
                                                    <th  class="heading">Longitude </th>

                                                </tr>

                                                <c:forEach var="rideAppBean" items="${requestScope['list']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                              <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                              <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                          </td> --%>
                                                        <%--                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">
                                                                                                                <input type="text" id="trip_stopage_map_id${loopCounter.count}" value = "${rideAppBean.trip_stopage_map_id}">
                                                        </td>--%>
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${rideAppBean.trip_stopage_map_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class=""  onclick="fillColumns(id)" >${rideAppBean.route_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.stopage_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.week_days}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.order_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.start_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.arrival_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.departure_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.latitude}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.longitude}</td>
                                                        <td>
                                                            <input type="hidden" id="trip_id${rideAppBean.trip_stopage_map_id}" value="${rideAppBean.trip_id}">
                                                            <input type="button" value="View Map" onclick="viewMap(${rideAppBean.latitude}, ${rideAppBean.longitude})">
                                                        </td>
                                                    </tr>
                                                </c:forEach>

                                                <tr> <td align='center' colspan="11">
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
                                                                <input class="button" type='submit' name='buttonAction' value='Last' >
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </TABLE>
                                            <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" name="searchRoute" value="${searchRoute}">
                                            <input  type="hidden" name="searchDay" value="${searchDay}">
                                            <input  type="hidden" name="searchTime" value="${searchTime}">
                                        </form>
                                    </div>
                                    <br>
                                    <br>

                                    <form  action="TripPointCont.do" method="post" onsubmit="return verify()">

                                        <table  align="center"  class="content" cellpadding="2%" border="1">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr><input class="input" type="hidden" id="trip_stopage_map_id" name="trip_stopage_map_id" value="" ></tr>
                                            <tr>
                                                <th class="heading1"> Route Name </th>
                                                <td>
                                                    <input type="text" class="input new_input"  id="route_name" size="15" name="route_name" value="${route_name}" disabled>
                                                    <input type="hidden"  id="location" size="15" name="location" value="" disabled>
                                                </td>
                                                <th class="heading1"> Week days </th>
                                                <td>
                                                    <input type="hidden"  id="weekDays" size="15" name="" value="${week_days}">
                                                    <select class="dropdown"  name="week_days"   id="week_days">
                                                        <option value="" >select</option>
                                                        <!--                                                                                    <option value="" ></option>-->
                                                    </select>
                                                    <input type="hidden"  id="startTime" size="15" name="startTime" value="${start_time}">
                                                    <span class="heading1">Time</span>
                                                    <select class="dropdown"  name="start_time"   id="start_time">
                                                        <option value="" >select</option>
                                                        <!--                                                                                    <option value="" ></option>-->
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="4" align="center">
                                                    <span class="heading1"> Point Name </span>
                                                    <input class="input " type="text"  id="stopage_name" size="15" name="stopage_name" value="" disabled>
                                                    <input type="hidden"  id="order_no" size="8" name="order_no" value="" disabled>
                                                    <input type="hidden"  id="location_code" size="8" name="location_code" value="" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Arrival Time</th>
                                                <td>
                                                    H.<input type="numeric"  id="starting_time_hour" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" size="2" maxlength="2" name="starting_time_hour" value="" required disabled>
                                                    M.<input type="numeric"  id="starting_time_min" pattern="[0-5]{1}[0-9]{1}" size="2" maxlength="2" name="starting_time_min" value="" required disabled>
                                                </td>
                                                <th class="heading1">Dept. Time</th>
                                                <td>
                                                    H.<input type="numeric"  id="ending_time_hour" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" size="2" maxlength="2" name="ending_time_hour" value="" required disabled>
                                                    M.<input type="numeric"  id="ending_time_min" pattern="[0-5]{1}[0-9]{1}" size="2" maxlength="2" name="ending_time_min" value="" required disabled>
                                                    <input type="hidden"  id="latitude" size="" name="latitude" value="" disabled>
                                                    <input type="hidden"  id="longitude" size="" name="longitude" value="" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="5" align="center" >
                                                    <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >
                                                    <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
                                                    <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                </td>
                                            </tr>

                                        </table>

                                        <input type="hidden" id="clickedButton" value="">
                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                        <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        <input  type="hidden" name="searchRoute" value="${searchRoute}">
                                        <input  type="hidden" name="searchDay" value="${searchDay}">
                                        <input  type="hidden" name="searchTime" value="${searchTime}">

                                    </form>
                                </td>
                            </tr>
                        </table>

                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
