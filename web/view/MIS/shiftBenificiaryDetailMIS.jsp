

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
        <title>JSP Page</title>
        <script type="text/javascript">
 function getPdf(){
                var search_date = $("#search_date").val();
                var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var searchemp = $("#searchemp").val();
                var occupationtype = $("#occupationtype").val();
                var mobileno = $("#mobileno").val();
                var queryString = "task=generateHSReport&search_date="+search_date+"&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&searchemp="+searchemp+"&occupationtype="+occupationtype+"&mobileno="+mobileno;
                var url = "ShiftShowController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
       function getExcel(){
                var search_date = $("#search_date").val();
                 var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var searchemp = $("#searchemp").val();
                var occupationtype = $("#occupationtype").val();
                var mobileno = $("#mobileno").val();
              var queryString = "task=generateReport&search_date="+search_date+"&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&searchemp="+searchemp+"&occupationtype="+occupationtype+"&mobileno="+mobileno;
                var url = "ShiftShowController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
     function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, active=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
      function openCurrentMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);
                            var url="ShiftShowController?task=showMapWindow&logitude="+y+"&lattitude="+x;
                            window.open(url);
                            //popupwin = openPopUp(url, "",  580, 620);
                        }
       jQuery(function(){
         $("#search_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });

                                    $("#searchZone").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZone";},
                 action2: function() { return  $("#searchZone_no").val();}
            }
            });
           $("#searchZone").result(function(event, data, formatted){
            $.ajax({url: "ShiftDesinationLocationController?action1=getZoneNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchZone_no").val(response_data.trim());
                   }
                   });
            });

            $("#searchZone_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZoneNo"},
                 action2: function() { return  $("#searchZone").val();}
            }
            });
              $("#searchZone_no").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getZone", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchZone").val(response_data.trim());
                   }
                   });
                   });
           $("#searchWardType").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWard"},
                action2: function() { return  $("#searchZone").val();},
                 action3: function() { return  $("#searchWardNo").val();}
              }
              });
               $("#searchWardType").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getWardNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchWardNo").val(response_data.trim());
                   }
                   });
                });

            $("#searchWardNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWardNo"},
                 action2: function() { return  $("#searchWardType").val();}
            }
            });
              $("#searchWardNo").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getWard", data: "action3="+ data +"&q=", success: function(response_data) {
                       $("#searchWardType").val(response_data.trim());
                   }
                   });
            });

            $("#searchArea").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getArea"},
                action2: function() { return  $("#searchWardType").val();},
                action3: function() { return  $("#searchZone").val();},
                 action4: function() { return  $("#searchAreaNo").val();}
           }
            });
           $("#searchArea").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getAreaNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchAreaNo").val(response_data.trim());
                   }
                   });
            });

          $("#searchAreaNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getAreaNo"},
                 action2: function() { return  $("#searchArea").val();}
            }
            });
               $("#searchAreaNo").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getArea", data: "action4="+ data +"&q=", success: function(response_data) {
                       $("#searchArea").val(response_data.trim());
                   }
                   });
            });

          $("#searchCityName").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getlocation"},
                action2: function() { return  $("#searchArea").val();},
                action3: function() { return  $("#searchWardType").val();},
                action4: function() { return  $("#searchZone").val();},
                action5: function() { return  $("#searchCityNo").val();}
           }
         });
              $("#searchCityName").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getLocationNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchCityNo").val(response_data.trim());
                   }
                   });
            });

             $("#searchCityNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getLocationNo"},
                 action2: function() { return  $("#searchCityName").val();}
            }
            });
            $("#searchCityNo").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getlocation", data: "action5="+ data +"&q=", success: function(response_data) {
                       $("#searchCityName").val(response_data.trim());
                   }
                   });
            });

         $("#occupationtype").autocomplete("ShiftShowController", {
            extraParams: {
                action1: function() { return "getOccupationtype"}
            }
           });
        $("#mobileno").autocomplete("ShiftShowController", {
            extraParams: {
                action1: function() { return "getmobileno"}
            }
           });
        $("#searchemp").autocomplete("ShiftShowController", {
            extraParams: {
                action1: function() { return "getsearchemp"}
            }
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
                <DIV id="body" class="content_div maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>Shift Beneficiary detail</b></td>
                                    </tr>
                                </table>
                            </td></tr>

         <tr>
           <td> <div align="center">
              <form name="form0" method="get" action="ShiftShowController">
                  <input id="map" class="" type="button" onclick="openCurrentMap('' , '');" value="Map">
                   <table align="center" class="heading1" width="800">
                          <tr>
                               <th> Zone Name</th>
                               <td> <input type="text" class="new_input" name="searchZone" size="10" id="searchZone"  value="${searchZone}"/></td>
                             <th> Zone No</th>
                             <td><input type="text" class="input" name="searchZone_no" size="5" id="searchZone_no"  value="${searchZone_no}"/></td>
                             <th>Ward Name</th>
                             <td><input class="new_input" type="text" id="searchWardType" name="searchWardType" value="${searchWardType}" size="10" ></td>
                             <th>Ward No</th>
                             <td><input class="input" type="text" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" size="5" ></td>
                          </tr>
                            <tr>
                              <th> Area Name</th>
                              <td><input  class="new_input" type="text" name="searchArea" size="10" id="searchArea" value="${searchArea}" size="10"></td>
                              <th> Area No</th>
                              <td><input  type="text" name="searchAreaNo" size="5" id="searchAreaNo" value="${searchAreaNo}" size="5"></td>
                              <th>City Location</th>
                              <td><input class="new_input" type="text" id="searchCityName" name="searchCityName" value="${searchCityName}" size="15" ></td>
                              <th>City Location No</th>
                              <td><input class="input" type="text" id="searchCityNo" name="searchCityNo" value="${searchCityNo}" size="5" ></td>
                            </tr>
                            <tr>
                               <th>Mobile No</th>
                               <td><input  type="text" id="mobileno" name="mobileno" value="${mobileno}" size="10" ></td>
                               <th>Occupation Type</th>
                               <td><input class="new_input" type="text" id="occupationtype" name="occupationtype" value="${occupationtype}" size="10" ></td>
                               <th>Date</th>
                               <td><input  type="text" id="search_date" name="search_date" value="${search_date}" size="10" ></td>
                               <th>Employee Name</th>
                               <td><input class="new_input" type="text" id="searchemp" name="searchemp" value="${searchPersonName}" size="10" ></td>
                            </tr>
                              <tr>
                                  <td colspan="12" align="center">
                                   <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                   <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                   <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getPdf()">
                                   <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()">
                                   </td>
                             </tr>
                     </table>
            </form>
         </div>
       </td>
   </tr>
           <div align="center">
               <form name="form1" action="ShiftShowController" class="content_div">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">Emp Name&nbsp;</TH>
                 <TH class="heading">Emp Code&nbsp;</TH>
                <TH class="heading">Person Name&nbsp;</TH>
                <TH class="heading">Father Name&nbsp;</TH>
                <TH class="heading">Mobile No&nbsp;</TH>
                <TH class="heading">Occupation Name&nbsp;</TH>
                <TH class="heading">Occupation Type&nbsp;</TH>
                <TH class="heading">City Location&nbsp;</TH>
                <TH class="heading" style="white-space: normal">Is Residencial&nbsp;</TH>
                <TH class="heading">Date-Time&nbsp;</TH>
                <TH class="heading">Status&nbsp;</TH>
                <TH class="heading">Reason&nbsp;</TH>
            <c:forEach var="ShiftLoginModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" > ${ShiftLoginModel.shift_key_person_detail_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"   align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}"  class="new_input">${ShiftLoginModel.emp_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  class="new_input">${ShiftLoginModel.emp_code}</td>
            <td id="t1c${IDGenerator.uniqueID}"  class="new_input">${ShiftLoginModel.name}</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" >${ShiftLoginModel.father_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.mobile_no}</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" >${ShiftLoginModel.occupation_name}</td>
             <td id="t1c${IDGenerator.uniqueID}" class="new_input" >${ShiftLoginModel.occupation_type}</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" >${ShiftLoginModel.location}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.is_residencial}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.date}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.status}</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input"  >${ShiftLoginModel.reason}</td>

            </tr>
            </TR>

            </c:forEach>

            <tr> <td align='center' colspan="13">
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
    <input type="hidden"  name="search_date" value="${search_date}">
           <input  type="hidden" name="searchOccupationName" value="${searchOccupationName}">
                 <input  type="hidden" name="searchWardType" value="${searchWardType}">
                 <input  type="hidden" name="searchCityName" value="${searchCityName}">
                 <input  type="hidden" name="searchZone" value="${searchZone}">
                 <input  type="hidden" name="searchWardType" value="${searchWardNo}">
                 <input  type="hidden" name="searchCityName" value="${searchCityNo}">
                 <input  type="hidden" name="searchCityName" value="${searchCity}">
                <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
                <input  type="hidden" name="searchZone" value="${searchZone_no}">
                <input  type="hidden" name="searchPersonName" value="${searchPersonName}">
                <input  type="hidden" name="mobileno" value="${mobileno}">
                <input  type="hidden" name="occupationtype" value="${occupationtype}">
                <input  type="hidden" name="searchemp" value="${searchemp}">
        </form>
                </div>
               <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                             </table>

            </DIV>
         </td>

      </table>
    </body>
</html>
