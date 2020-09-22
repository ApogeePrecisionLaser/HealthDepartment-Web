<%--
    Document   : existingEstimate
    Created on : Dec 16, 2013, 3:29:53 PM
    Author     : Ritesh
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
      $("#date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
            });

    function fillColumns(id,loopcount)
     {
         
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 7;
        var columnId = id;                                  <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
        }

        var t1id = "t1c";// particular column id of table 1 e.g. t1c3.
        var attendence=document.getElementById("attendence_"+loopcount).value;
        //alert(attendence);
         if(attendence=='Y')
             {
                 document.getElementById("attendence_"+loopcount).value='N';
                 document.getElementById(t1id + (lowerLimit + 5)).innerHTML='N';
                 //$("#reason_name_"+loopcount).show();
             }


            if(attendence=='N')
             {
                 document.getElementById("attendence_"+loopcount).value='Y';
                 document.getElementById(t1id + (lowerLimit + 5)).innerHTML='Y';
                // $("#reason_name_"+loopcount).hide();
             }


        for(var i = 0; i < noOfColumns; i++) {
           if(attendence=='Y')
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#ff4d4d";
          if(attendence=='N')
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#52D017";

        }

        document.getElementById("edit").disabled = false;

        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as_new").disabled = true;
            document.getElementById("delete").disabled = false;
            document.getElementById("update").disabled = false;
            dodument.getElementById("save").disabled=true;

        }

        $("#message").html("");
    }


     function makeEditable(id) {

        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("beneficiary_id").value=0;




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
                                        <td align="center" class="header_table" width="90%"><b>Attendance</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                          <td> <div align="center">
                                    <form name="form0" method="get" action="AttendenceController">
                                    <table align="center" class="heading1" width="800">
                                  <tr>
                                   
                                     <th class="heading1">Date </th>
                                     <td><input type="text"  id="date" name="date" value="${date}" required></td>
                                  
                                     <td colspan="2"><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                              </tr>
                           </table>

                      </form>
                                </div>
                            </td>
       <form name="form1" action="AttendenceController" method="post">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <th class="heading">S.no&nbsp;</th>
                 <th class="heading">Emp Code&nbsp;</th>
                 <th class="heading">Emp Name&nbsp;</th>
                  <th class="heading">Mobile No&nbsp;</th>
                 <th class="heading">Attendance&nbsp;</th>
                      <tr id="message">
                         <c:if test="${not empty message}">
                              <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                         </c:if>
                     </tr>
          <c:forEach var="ShiftTimeModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor" >
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id,${loopCounter.count})"> ${ShiftTimeModel.attendence_id}<input type="hidden" id="attendence_id" name="attendence_id" value="${ShiftTimeModel.attendence_id}"></td>
            <td id="t1c${IDGenerator.uniqueID}" align="center" onclick="fillColumns(id,${loopCounter.count})"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.emp_code}</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.emp_name}</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})" >${ShiftTimeModel.mobile_no}</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.attendence}</td>
            <td  id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id,${loopCounter.count})"><input type="hidden" id="attendence_${loopCounter.count}"name="attendence" value="${ShiftTimeModel.attendence}"></td>
            </tr>
          </c:forEach>
       <tr> <td align="center" colspan="10"> <input  type="submit" name="task" id="save" value="Save"></td></tr>
        </TABLE>
     <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
     <input type="hidden" id="noOfRowsTraversed"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
     <input type="hidden"  name="noOfRowsInTable" value="${noOfRowsInTable}">
     <input type="hidden"  name="date" value="${date}">

        </form>
         <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
            </DIV>
         </td>

      </table>

    </body>
</html>
