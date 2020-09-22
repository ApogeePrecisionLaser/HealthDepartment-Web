<%--
    Document   : vehicle_key_person
    Created on : Oct 26, 2017, 2:06:34 PM
    Author     : Com7_2
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
                $("#search_given_by").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getSearchKey_person_name_givenBy"}
                    }
                });
            });
            jQuery(function(){
                $("#search_taken_by").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getSearchKey_person_name_takenBy"}
                    }
                });
            });
            //          jQuery(function(){
            //               $("#key_person_name").autocomplete("VehicleKeyPersonController", {
            //               extraParams: {
            //               action1: function() { return "getkey_person_name"}
            //             }
            //            });
            //         });
            jQuery(function(){
                $("#vehicle_code").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getVehicle_code"}
                    }
                });
            });
            jQuery(function(){
                $("#vehicle_number").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getVehicle_number"}
                    }
                });
            });
            jQuery(function(){
                $("#point").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getPoint"}
                    }
                });

                $("#vehicle_code").result(function(event, data, formatted){
                    var temp=$("#key_person_name").val();
                    $.ajax({url: "WeightMIS?action1=getZone&temp="+temp, data: "action3="+ data +"&q=", success: function(response_data) {
                            $("#vehicle_number").val(response_data.trim());
                        }
                    });
                });

                $("#date").datepicker({
                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#search_date").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
            });
            jQuery(function(){
                $("#given_by").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getKey_person_name_givenBy"}
                    }
                });
            });
            jQuery(function(){
                $("#taken_by").autocomplete("WeightMIS", {
                    extraParams: {
                        action1: function() { return "getKey_person_name_takenBy"}
                    }
                });
            });


            function fillColumns(id)
            {
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

                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                document.getElementById("shift_vehicle_detail_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("given_by").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("taken_by").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("vehicle_code").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("point").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                setStartingTime(document.getElementById(t1id + (lowerLimit + 6)).innerHTML);
                document.getElementById("status").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                document.getElementById("latitude").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                document.getElementById("longitude").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                document.getElementById("vehicle_number").value=document.getElementById(t1id + (lowerLimit + 10)).innerHTML;


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
                }}
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }


            function makeEditable(id) {
                //         alert(id);
                document.getElementById("given_by").disabled = false;
                document.getElementById("taken_by").disabled = false;
                document.getElementById("vehicle_code").disabled = false;
                document.getElementById("vehicle_number").disabled = false;
                document.getElementById("point").disabled = false;
                document.getElementById("date").disabled = false;
                document.getElementById("status").disabled = false;
                document.getElementById("latitude").disabled = false;
                document.getElementById("longitude").disabled = false;
                document.getElementById("get_cordinate").disabled = false;
                document.getElementById("starting_time_hour").disabled = false;
                document.getElementById("starting_time_min").disabled = false;



                document.getElementById("save").disabled = true;

                if(id == 'new') {
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_as").disabled =true;
                    document.getElementById("save").disabled =false;
                    document.getElementById("vehicle_key_person_id").value=0;

                }
                if(id == 'edit'){
                    $("#message").html("");
                    document.getElementById("save_as").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = false;

                }

            }

            function setStartingTime(st){
                var array1= st.split(" ");
                var array2=array1[0].split("-")[2]+"-"+array1[0].split("-")[1]+"-"+array1[0].split("-")[0];
                var array= array1[1].split(":");
                document.getElementById("date").value=array2;
                document.getElementById("starting_time_hour").value=array[0];
                document.getElementById("starting_time_min").value=array[1];

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

            function openMap(WeightMISId) {
                //alert(vehicle_key_person_map_id);
                var url="WeightMIS?task=showMapWindow&WeightMISId="+WeightMISId;
                popupwin = openPopUp(url, "",  580, 620);
            }


            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }

            function openMapForCord() {
                var url="generalCont?task=GetCordinates4";//"getCordinate";
                popupwin = openPopUp(url, "",  600, 630);
            }


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
                                        <td align="center" class="header_table" width="90%"><b>Shift Vehicle Details</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                        <form name="form0" action="WeightMIS">
                            <table align="center" class="heading1" width="700">
                                <tr>
                                    <th>Given by</th>
                                    <td><input class="new_input" type="text" id="search_given_by" name="search_given_by" value="${search_given_by}" size="20" ></td>

                                    <th>Taken by</th>
                                    <td><input class="new_input" type="text" id="search_taken_by" name="search_taken_by" value="${search_taken_by}" size="20" ></td>



                                    <td colspan="6" align="center">
                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                        <!--                   <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getlist()">
                                                           <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getCity()">-->
                                    </td>
                                </tr>
                                <tr>
                                    <th>Date</th>
                                    <td><input  type="text" id="search_date" name="search_date" value="${searchdate}" size="10" ></td>
                                </tr>
                            </table>
                        </form>
                        <form name="form1" action="WeightMIS">

                            <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <TH class="heading">S.no&nbsp;</TH>
                                <TH class="heading">Given by&nbsp;</TH>
                                <TH class="heading">Taken by&nbsp;</TH>
                                <TH class="heading" style="white-space: normal">Vehicle given by&nbsp;</TH>
                                <TH class="heading">Point&nbsp;</TH>
                                <TH class="heading">Date-Time&nbsp;</TH>
                                <TH class="heading">Status &nbsp;</TH>
                                <TH class="heading">Weight &nbsp;</TH>
                                




                                <c:forEach var="WeightMIS" items="${requestScope['list']}" varStatus="loopCounter">
                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                    <tr>
                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${WeightMIS.shiftVehicleDetailId}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${WeightMIS.givenBy}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="new_input"  onclick="fillColumns(id)">${WeightMIS.takenBy}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="new_input"  onclick="fillColumns(id)">${WeightMIS.vehicleCode}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="new_input"  onclick="fillColumns(id)">${WeightMIS.point_name}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"    onclick="fillColumns(id)">${WeightMIS.date}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"    onclick="fillColumns(id)">${WeightMIS.status}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"    onclick="fillColumns(id)">${WeightMIS.weight}</td>
                                     
                                        <td id="t1c${IDGenerator.uniqueID}"  style="display: none" class="new_input"  onclick="fillColumns(id)">${WeightMIS.vehicleNumber}</td>
                                      
                                    </tr>

                                    </TR>
                                </c:forEach>
                                   <tr><td>Total</td><td></td><td></td><td></td><td></td><td></td><td></td><td>${total}</td></tr>
                                <tr> <td align='center' colspan="9">
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
                        </form>
                        <br>
                        <br>

                     
                        <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                    </table>

                </DIV>
            </td>

        </table>
    </body>
</html>
