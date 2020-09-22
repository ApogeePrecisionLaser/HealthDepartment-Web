<%--
    Document   : trip
    Created on : Nov 16, 2016, 3:31:39 PM
    Author     : Nishu
--%>

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
        <title>Trip</title>
<script type="text/javascript">
           jQuery(function(){
           /*$("#week_days").autocomplete("tripCont.do",{
            extraParams:
                {
                action1: function() { return "getWeek" }
                }
              });*/
               $("#searchTime").autocomplete("TripCont.do",{
            extraParams: {
                action1: function() { return "getTime" }
                }
              });
               $("#searchRoute").autocomplete("TripCont.do",
           {
            extraParams:
                {
                action1: function() { return "getRoute" }
                }
              });
              /*$("#searchDay").autocomplete("tripCont.do",{
              extraParams: {
                  action1: function() { return "getWeek" }
              }
              });*/
              $("#route_name").autocomplete("TripCont.do", {
              extraParams:
                  {
                  action1: function() { return "getRoute" }
              }
              });

    $("#week_days").result(function(event, data, formatted) {debugger;

          var day = data;
         $.ajax({url: "TripCont.do",data: "task=getTime&day=" +day, success: function(response_data){
                 alert(response_data);

//              $("#unit_name2").html(response_data);
                  var responseArray = response_data.split("##");
               var length = responseArray.length;
//               alert(length);
               $("#start_time").html("");
                 var  opt = '<option  value=""> select</option>';
                  $("#start_time").append(opt);
             for(var i=0; i < length; i++){
                     var array1 = responseArray[i];
//                     alert(array1);
//                   var opt = "<option value='"+ array1.split("&#")[0] +"'>"+ array1.split("&#")[1] +"</option>";
                     var opt = "<option value='"+ array1 +"'>"+ array1 +"</option>";;
                     $("#start_time").append(opt);
             }
        }
            });
         });
         /*$("#route_name").result(function(event, data, formatted)         {

          var route = data;
         $.ajax({url: "tripCont.do",data: "task=getDay&route=" +route, success: function(response_data){

//              $("#unit_name2").html(response_data);
                  var responseArray = response_data.split("##");
               var length = responseArray.length;
//               alert(length);
               $("#week_days").html("");
                 var  opt = '<option  value=""> select</option>';
                  $("#week_days").append(opt);
             for(var i=0; i < length; i++){
                     var array1 = responseArray[i];
//                     alert(array1);
//                   var opt = "<option value='"+ array1.split("&#")[0] +"'>"+ array1.split("&#")[1] +"</option>";
                     var opt = "<option value='"+ array1 +"'>"+ array1 +"</option>";;
                     $("#week_days").append(opt);
             }
        }
            });
         });*/
              });

   function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 6;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;
        for(var i = 0; i < noOfRowsTraversed; i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

     document.getElementById("trip_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
    // document.getElementById("rev_no").value= document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
    document.getElementById("trip_name").value=(document.getElementById(t1id + (lowerLimit + 2)).innerHTML).replace('-', '&');
     document.getElementById("route_name").value=(document.getElementById(t1id + (lowerLimit + 3)).innerHTML).replace('-', '&');
     var week_days = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
     document.getElementById("week_days").innerHTML = '<option value="'+ week_days +'" selected>'+week_days +'</option>';
     setStartTime(document.getElementById(t1id +(lowerLimit+5)).innerHTML);

     // document.getElementById("starting_time").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;

       // document.getElementById("ending_time").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }
        document.getElementById("edit").disabled = false;

        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as").disabled = true;
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
        var url="generalCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
        popupwin = openPopUp(url, "",  580, 620);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
     function displayRouteList(id){

                var queryString;
                if(id=='viewPdf')
                queryString = "requester=PRINT";
            else
                queryString = "requester=PRINTXls";
                var url = "TripCont.do?"+queryString;
                popupwin = openPopUp(url, "Route", 600, 900);
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
        document.getElementById("start_time_hour").disabled = false;
        document.getElementById("start_time_min").disabled = false;
        document.getElementById("trip_name").disabled = false;
        document.getElementById("save").disabled = true;
        if(id == 'new') {
           $("#message").html("");
           document.getElementById("every_day").disabled = false;
           $("#dailySpan").show();
           document.getElementById("edit").disabled = true;
           document.getElementById("delete").disabled = true;
           document.getElementById("save_as").disabled =true;
           document.getElementById("save").disabled =false;
           document.getElementById("trip_id").value=0;
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
            else{}
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
                                        <td align="center" class="header_table" width="90%"><b>Trip</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="TripCont.do">
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
                                            </tr><tr>
                                                <th>start time</th>
                                                <td><input class="input " type="text" id="searchTime" name="searchTime" value="${searchTime}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayRouteList(id)"></td>
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
                                        <form name="form1" action="TripCont.do">


                                            <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Trip Name</th>
                                                    <th  class="heading">Route Name</th>
                                                    <th  class="heading">week_days</th>
                                                    <th  class="heading">Start Time</th>

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
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${rideAppBean.trip_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.trip_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.route_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.week_days}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)" >${rideAppBean.start_time}</td>

                                                    </tr>
                                                </c:forEach>

                                                <tr> <td align='center' colspan="8">
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
                                    <form  action="TripCont.do" method="post" onsubmit="return verify()">
                                        <table  align="center"  class="content" cellpadding="2%" border="1">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr><input class="input" type="hidden" id="trip_id" name="trip_id" value="" ></tr>
                                            <tr>

                                                <th class="heading1"> Route Name </th>
                                                <td><input type="text" class="input new_input" id="route_name" size="15" name="route_name" value="" disabled></td>

                                                <th class="heading1"> Week days </th>

                                                <td><select class="dropdown3" id="week_days" name="week_days">
                                                        <option <c:if test="${month == 'Sun'}">selected</c:if>>Sun</option>
                                                        <option <c:if test="${month == 'Mon'}">selected</c:if>>Mon</option>
                                                        <option <c:if test="${month == 'Tue'}">selected</c:if>>Tue</option>
                                                        <option <c:if test="${month == 'Wed'}">selected</c:if>>Wed</option>
                                                        <option <c:if test="${month == 'Thu'}">selected</c:if>>Thu</option>
                                                        <option <c:if test="${month == 'Fri'}">selected</c:if>>Fri</option>
                                                        <option  <c:if test="${month == 'Sat'}">selected</c:if>>Sat</option>
                                                    </select>
                                                    <span id="dailySpan">
                                                        <input type="checkbox" id="every_day" name="every_day" value="Y" disabled>EveryDay
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1"> Starting time </th>
                                                <td>
                                                    H.<input type="numeric"  id="start_time_hour" size="5" maxlength="2" name="start_time_hour" value="" disabled>
                                                    M.<input type="numeric"  id="start_time_min" size="5" maxlength="2" name="start_time_min" value="" disabled>
                                                </td>
                                                <th class="heading1"> Trip Name </th>
                                                <td><input type="text" class="input" id="trip_name" size="15" name="trip_name" value="" disabled></td>
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

