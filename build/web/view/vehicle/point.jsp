<%-- 
    Document   : point
    Created on : Oct 27, 2017, 3:02:38 PM
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
            jQuery(function () {
                $("#search_city_location").autocomplete("PointController", {
                    extraParams: {
                        action1: function () {
                            return "getsearch_city_name"
                        }
                    }
                });
            });

            //            $("#search_point").autocomplete("PointController", {
            //                    extraParams: {
            //                        action1: function() { return "getsearch_point_name"}
            //                    }
            //                });
            //            });


            jQuery(function () {
                $("#search_point").autocomplete("PointController", {
                    extraParams: {
                        action1: function () {
                            return "getpoint_name"
                        }
                    }
                });
            });
            jQuery(function () {
                $("#city_location").autocomplete("PointController", {
                    extraParams: {
                        action1: function () {
                            return "getcity_name"
                        }
                    }
                });


                $("#zone").autocomplete("cityLocationCont", {
                    extraParams: {
                        action1: function () {
                            return "getZone"
                        }
                    }
                });

                $("#ward").autocomplete("cityLocationCont", {
                    extraParams: {
                        action1: function () {
                            return "getWardName"
                        },
                        action2: function () {
                            return  $("#zone").val();
                        }
                    }
                });

                $("#area").autocomplete("cityLocationCont", {
                    extraParams: {
                        action1: function () {
                            return "getAreaName"
                        },
                        action2: function () {
                            return  $("#ward").val();
                        },
                        action3: function () {
                            return  $("#zone").val();
                        }
                    }
                });
            });

            function fillColumns(id)
            {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 6;
                var columnId = id;
                <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);
                <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for (var i = 0; i < noOfRowsTraversed; i++)
                {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

                    if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }

                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                document.getElementById("point_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("area").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("point_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("latitude").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("longitude").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;


                for (var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }


                document.getElementById("edit").disabled = false;

                if (!document.getElementById("save").disabled)
                {
                    document.getElementById("save_as_new").disabled = true;
                    document.getElementById("delete").disabled = false;
                    // document.getElementById("revised").disabled = false;
                    dodument.getElementById("save").disabled = true;

                }

                $("#message").html("");
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {

                for (var i = 0; i < noOfRowsTraversed; i++) {

                    for (var j = 1; j <= noOfColumns; j++) {

                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                    }
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for (var i = 0; i < str.length; i++) {
                    if (str.charAt(i) == ' ')
                        beginIndex++;
                    else
                        break;
                }
                return str.substring(beginIndex, str.length);
            }


            function makeEditable(id) {
                //         alert(id);
                document.getElementById("zone").disabled = false;
                document.getElementById("ward").disabled = false;
                document.getElementById("area").disabled = false;
                //document.getElementById("city_location").disabled = false;
                document.getElementById("point_name").disabled = false;
                document.getElementById("latitude").disabled = false;
                document.getElementById("longitude").disabled = false;
                document.getElementById("save").disabled = true;
                document.getElementById("get_cordinate").disabled = false;
                if (id == 'new') {
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_as").disabled = true;
                    document.getElementById("save").disabled = false;
                    document.getElementById("point_id").value = 0;
                    document.getElementById("get_cordinate").disabled = false;

                }
                if (id == 'edit') {
                    $("#message").html("");
                    document.getElementById("save_as").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = false;

                }

            }



            function setStatus(id) {

                if (id == 'save') {

                    document.getElementById("clickedButton").value = "save";
                } else if (id == 'save_as') {
                    document.getElementById("clickedButton").value = "Save AS New";
                } else if (id == 'delete') {
                    document.getElementById("clickedButton").value = "Delete";
                } else
                {
                }
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }


            function openMapForCord() {
                var url = "generalCont?task=GetCordinates4";//"getCordinate";
                popupwin = openPopUp(url, "", 600, 630);
            }
            function openMap(point_id) {
                //alert(vehicle_key_person_map_id);
                var url = "PointController?task=showMapWindow&point_id=" + point_id;
                popupwin = openPopUp(url, "", 580, 620);
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
                                        <td align="center" class="header_table" width="90%"><b>Point</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                        <form name="form0" action="PointController">
                            <table align="center" class="heading1" width="700">
                                <tr>
                                    <th>City Location</th>
                                    <td><input class="new_input" type="text" id="search_city_location" name="search_city_location" value="${search_city_location}" size="20" ></td>

                                    <th>Point Name</th>
                                    <td><input class="new_input" type="text" id="search_point" name="search_point" value="${search_point}" size="20" ></td>


                                    <td colspan="6" align="center">
                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                        <!--                   <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getlist()">
                                                           <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getCity()">-->
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <form name="form1" action="PointController">


                            <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <TH class="heading">S.no&nbsp;</TH>
                                <TH class="heading">City Location&nbsp;</TH>
                                <TH class="heading">Point Name&nbsp;</TH>
                                <TH class="heading">Latitude&nbsp;</TH>
                                <TH class="heading">Longitude&nbsp;</TH>



                                <c:forEach var="point" items="${requestScope['list']}" varStatus="loopCounter">
                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                    <tr>
                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${point.point_id}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${point.city_location}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="new_input"  onclick="fillColumns(id)">${point.point_name}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="input"  onclick="fillColumns(id)">${point.latitude}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  class="input"  onclick="fillColumns(id)">${point.longitude}</td>
                                        <td>
                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${point.point_id}');"/>
                                        </td>
                                    </tr>
                                    </TR>
                                </c:forEach>

                                <tr> <td align='center' colspan="5">
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

                        <form  action="PointController" method="post">

                            <table  align="center"  class="content" cellpadding="2%" border="1">
                                <tr id="message">
                                    <c:if test="${not empty message}">
                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                    </c:if>
                                </tr>
                                <tr><input class="input" type="hidden" id="point_id" name="point_id" value="" ></tr>
                                <tr> <th class="heading1">Zone</th><td><input class="new_input" type="text" id="zone" name="zone" value="${zone}" size="20" disabled></td>
                                    <th class="heading1">Ward</th><td><input class="new_input" type="text" id="ward" name="ward" value="${ward}" size="20" disabled></td>
                                
                                </tr>
                                <!--                                            <tr>
                                                                                <th class="heading1">Area</th><td><input class="new_input" type="text" id="area" name="area" value="" size="20" disabled></td>
                                                                               
                                                                               
                                                                                 
                                                                            </tr>-->
                                <tr>
                                    <th class="heading1">City Location</th>
                                    <td><input type="text" class="new_input" id="area"   name="city_location" value="${city_location}" size="20" disabled></td>

                                    <th class="heading1"> Point Name </th>
                                    <td><input type="text" class="new_input" id="point_name"   name="point_name" value="" size="20" disabled></td>

                                </tr>
                                <tr>
                                    <th class="heading1">Latitude</th><td><input class="input" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                                    <th class="heading1">Longitude</th><td><input  class="input" type="text" id="longitude" name="longitude" value="" size="20" disabled><input style="margin-left:18px;" class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                </tr>




                                <tr>
                                    <td colspan="4"  align="center">
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
                        </form>
                        <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                    </table>

                </DIV>
            </td>

        </table>
    </body>
</html>
