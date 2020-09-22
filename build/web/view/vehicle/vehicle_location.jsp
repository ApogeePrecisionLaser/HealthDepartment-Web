<%--
    Document   : level
    Created on : Aug 12, 2016, 5:52:45 PM
    Author     : Nishu
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

        $("#vehicleSubType").autocomplete("VehicleController", {
            extraParams: {
                action1: function() { return "getVehicleSubType"}
            }
        });
       $("#vehicleNo").autocomplete("VehicleController", {
            extraParams: {
                action1: function() { return "getVehicleNo"}
            }
        });
       $("#mobileNo").autocomplete("VehicleController", {
            extraParams: {
                action1: function() { return "getMobileNo"}
            }
        });
            });

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }


             function openMap(vehicle_location_id) {
        //alert(vehicle_location_id);
        var url="VehicleLocationController?task=showMapWindow&vehicle_location_id="+vehicle_location_id;
        popupwin = openPopUp(url, "",  580, 620);
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
                                        <td align="center" class="header_table" width="90%"><b>Vehicle Location</b></td>
                                    </tr>
                                </table>
                            </td></tr>
     <form name="form0" action="VehicleLocationController">
          <table align="center" class="heading1" width="700">
                   <tr> <th>Vehicle Sub Type</th>
                   <td><input class="new_input" type="text" id="vehicleSubType" name="vehicleSubType" value="${vehicleSubType}" size="20" ></td>
                    <th>Vehicle No</th>
                   <td><input class="input" type="text" id="vehicleNo" name="vehicleNo" value="${vehicleNo}" size="20" ></td>
                  <th>Mobile No</th>
                   <td><input class="input" type="text" id="mobileNo" name="mobileNo" value="${mobileNo}" size="20" ></td>
                </tr>
                <tr>
                    <td colspan="6" align="center">
                   <input class="button" type="submit" name="task" id="searchIn" value="Search">
                   <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
<!--                   <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getlist()">
                   <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getCity()">-->
                   </td>
                 </tr>
          </table>
     </form>

   <form name="form1" action="VehicleLocationController">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">Vehicle Sub Type&nbsp;</TH>
                <TH class="heading">Vehicle No&nbsp;</TH>
                <TH class="heading">Vehicle Code&nbsp;</TH>
                <TH class="heading">Mobile No&nbsp;</TH>
                <TH class="heading">Latitude&nbsp;</TH>
                <TH class="heading">Longitude&nbsp;</TH>


            <c:forEach var="level_type" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" > ${level_type.vehicle_location_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"   align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" >${level_type.vehicle_sub_type}</td>
            <td id="t1c${IDGenerator.uniqueID}">${level_type.vehicle_no}</td>
            <td id="t1c${IDGenerator.uniqueID}">${level_type.vehicle_code}</td>
            <td id="t1c${IDGenerator.uniqueID}" >${level_type.mobile_no}</td>
            <td id="t1c${IDGenerator.uniqueID}">${level_type.latitude}</td>
            <td id="t1c${IDGenerator.uniqueID}">${level_type.longitude}</td>

            <td>
              <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${level_type.vehicle_location_id}');"/>
              </td>

            </tr>
            </TR>
            </c:forEach>

            <tr> <td align='center' colspan="7">
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
     <input  type="hidden" name="vehicleSubType" value="${vehicleSubType}">
     <input  type="hidden" name="vehicleNo" value="${vehicleNo}">
     <input  type="hidden" name="mobileNo" value="${mobileNo}">
        </form>
        <br>
        <br>


                   </table>

            </DIV>
         </td>
         <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
      </table>
    </body>

</html>