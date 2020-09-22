

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

          $("#mobileno").autocomplete("AttendenceViewController", {
            extraParams: {
                action1: function() { return "getmobileno"}
            }
           });
         $("#searchemp").autocomplete("AttendenceViewController", {
            extraParams: {
                action1: function() { return "getsearchemp"}
            }
           });
          $("#empcode").autocomplete("AttendenceViewController", {
            extraParams: {
                action1: function() { return "getempcode"}
            }
           });

           });

 function getPdf(){
                var search_date = $("#search_date").val();
                var searchemp = $("#searchemp").val();
                var empcode = $("#empcode").val();
                var mobileno = $("#mobileno").val();
                var queryString = "task=generateHSReport&search_date="+search_date+"&searchemp="+searchemp+"&empcode="+empcode+"&mobileno="+mobileno;
                var url = "AttendenceViewController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
       function getExcel(){
                var search_date = $("#search_date").val();
                var searchemp = $("#searchemp").val();
                var empcode = $("#empcode").val();
                var mobileno = $("#mobileno").val();
              var queryString = "task=generateReport&search_date="+search_date+"&searchemp="+searchemp+"&empcode="+empcode+"&mobileno="+mobileno;
                var url = "AttendenceViewController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
     function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, active=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
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
                <DIV id="body" class="content_div maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>Shift Attendance detail</b></td>
                                    </tr>
                                </table>
                            </td></tr>

         <tr>
           <td> <div align="center">
              <form name="form0" method="get" action="AttendenceViewController">
                   <table align="center" class="heading1" width="800">
                      
                        
                            <tr>
                                <th>Employee Name</th>
                                <td><input class="new_input" type="text" id="searchemp" name="searchemp" value="${searchemp}" size="10" ></td>
                                  <th>Employee Code</th>
                               <td><input  type="text" id="empcode" name="empcode" value="${empcode}" size="10" ></td>
                               <th>Mobile No</th>
                               <td><input  type="text" id="mobileno" name="mobileno" value="${mobileno}" size="10" ></td>
                               <th>Date</th>
                               <td><input  type="text" id="search_date" name="search_date" value="${search_date}" size="10" ></td>
                   
                            </tr>
                              <tr>
                              <td>
                                           <b> Attendance </b>
                                          <select id="attendence" name="attendence">
                                               <option value="" ${attendence == ""?'selected':''}>All</option>
                                               <option value="Y" ${attendence == "Y"?'selected':''}>Y</option>
                                               <option value="N" ${attendence == "N"?'selected':''}>N</option>
                                           </select>
                                  </td>
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
               <form name="form1" action="AttendenceViewController" class="content_div">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">Emp Name&nbsp;</TH>
                 <TH class="heading">Emp Code&nbsp;</TH>
                <TH class="heading">Mobile No&nbsp;</TH>
                <TH class="heading">Date&nbsp;</TH>
                <TH class="heading">Attendence&nbsp;</TH>
          
            <c:forEach var="ShiftLoginModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" > ${ShiftLoginModel.attendence_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"   align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}"  class="new_input">${ShiftLoginModel.emp_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  class="new_input">${ShiftLoginModel.emp_code}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.mobile_no}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.date}</td>
            <td id="t1c${IDGenerator.uniqueID}"  >${ShiftLoginModel.attendence}</td>
            

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
                <input  type="hidden" name="mobileno" value="${mobileno}">
                <input  type="hidden" name="empcode" value="${empcode}">
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
