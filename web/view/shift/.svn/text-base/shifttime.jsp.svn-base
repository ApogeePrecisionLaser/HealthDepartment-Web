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
        $("#person_name").autocomplete("ShiftTimeController", {
            extraParams: {
                action1: function() { return "getSearchPerson"},
                 action2: function() { return  $("#emp_code").val();}
            }
        });
          $("#person_name").result(function(event, data, formatted){
            $.ajax({url: "ShiftTimeController?action1=getEmpCode", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code").val(response_data.trim());
                   }
                   });
            });
          $("#person_name").result(function(event, data, formatted){
            $.ajax({url: "ShiftTimeController?action1=getName", data: "action2="+ data +"&q=", success: function(response_data) {
                        var varr=response_data.split("&#");
                        var arr=varr[0].split("_");
                       $("#zone").val(arr[0].trim());
                       $("#zone_no").val(arr[1].trim());
                       arr=varr[1].split("_");
                       $("#ward").val(arr[0].trim());
                       $("#ward_no").val(arr[1].trim());
                        arr=varr[2].split("_");
                       $("#area").val(arr[0].trim());
                       $("#area_no").val(arr[1].trim());
                   }
                   });
            });

             $("#emp_code").autocomplete("ShiftTimeController", {
            extraParams: {
                action1: function() { return "getEmpCode"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code").result(function(event, data, formatted){
                   $.ajax({url: "ShiftTimeController?action1=getSearchPerson", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#person_name").val(response_data.trim());
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
            });

     function Unique(noOfRowsInTable) {
                //alert(id);
                var noOfRowsInTable = noOfRowsInTable;
              // alert(noOfRowsInTable);
                var  time_h=document.getElementById("time_h").value;
               // alert(time_h);
                for(var i=1;i<=noOfRowsInTable;i++){
                $("#time_hour_${loopCounter.count}"+i).val(time_h);
                 //alert(status);
              }
              
              
     }
    function Unique1(noOfRowsInTable) {
                //alert(id);
                var noOfRowsInTable = noOfRowsInTable;
              // alert(noOfRowsInTable);
                var  time_m=document.getElementById("time_m").value;
               // alert(time_h);
                for(var i=1;i<=noOfRowsInTable;i++){
                $("#time_min_${loopCounter.count}"+i).val(time_m);
                 //alert(status);
              }


     }
    function fillColumns(id,loopcount)
     {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 12;
        var columnId = id;                                  <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;
       
        for(var i = 0; i < noOfRowsTraversed; i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
        }

       // setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
   
        var status=document.getElementById("status_"+loopcount).value;
         //alert(status);
         if(status=='Yes')
             {
                 document.getElementById("status_"+loopcount).value='No';
                 document.getElementById(t1id + (lowerLimit + 9)).innerHTML='No';
                 $("#reason_name_"+loopcount).show();
             }
       
        
            if(status=='No')
             {
                 document.getElementById("status_"+loopcount).value='Yes';
                 document.getElementById(t1id + (lowerLimit + 9)).innerHTML='Yes';
                 $("#reason_name_"+loopcount).hide();
             }
         
       
        for(var i = 0; i < noOfColumns; i++) {
           if(status=='Yes')
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#ff4d4d";
          if(status=='No')
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
                                        <td align="center" class="header_table" width="90%"><b>Shift Time</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                          <td> <div align="center">
                                    <form name="form0" method="get" action="ShiftTimeController">
                                    <table align="center" class="heading1" width="800">
                                  <tr>
                                     <th>Person Name</th>
                                     <td><input  type="text" class="new_input" id="person_name" name="person_name" value="${person_name}" size="12" >
                                         <input type="text"  id="emp_code"  size="10"   name="emp_code" placeholder='emp_code' value="${emp_code}">
                                     </td>
                                     <th class="heading1">Date </th>
                                     <td><input type="text"  id="date" name="date" value="${cut_dt}" required></td>
                                  </tr>
                                  <tr>
                                       <th class="heading1" >Zone Name</th>
                                       <td><input type="text"  id="zone" size="10"  name="zone" value="${zone}">
                                        <input type="text"  id="zone_no"  size="10"  name="zone_no" placeholder='zone_no'  value="${zone_no}">
                                       </td>
                                      <th class="heading1"  >Ward Name</th>
                                      <td><input type="text"  id="ward" name="ward" size="10"  value="${ward}">
                                       <input type="text"  id="ward_no" name="ward_no"  size="10" placeholder='ward_no'  value="${ward_no}" >
                                     </td>
                                </tr>
                              <tr>
                                   <th class="heading1">Area Name</th>
                                  <td><input type="text"  id="area"  size="10"   name="area" value="${area}">
                                   <input type="text"  id="area_no"  size="10"   name="area_no" placeholder='area_no' value="${area_no}">
                                 </td>
                                     <td colspan="2"><input class="button" type="submit" name="task" id="searchIn" value="Search">
                              </tr>
                           </table>
       
                      </form>
                                </div>
                            </td>
       <form name="form1" action="ShiftTimeController" method="post">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <th class="heading">S.no&nbsp;</th>
                <th class="heading">Name&nbsp;</th>
                 <th class="heading">Father Name&nbsp;</th>
                 <th class="heading">Mobile No.&nbsp;</th>
                 <TH class="heading">Occupation Name&nbsp;</TH>
                <TH class="heading">City Location&nbsp;</TH>
                <TH class="heading" style="white-space: normal">Is Residencial&nbsp;</TH>
                 <TH class="heading"> H.<input type="numeric"  id="time_h" size="2" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)"  name="time_h" maxlength="2" value="" onchange="Unique(${noOfRowsInTable})">
                     M.<input type="numeric" pattern="[0-5]{1}[0-9]{1}" id="time_m" size="2" maxlength="2" name="time_m" onchange="Unique1(${noOfRowsInTable})" value=""  ></TH>
                 <th class="heading">Status&nbsp;</th>
                  <th class="heading">Reason&nbsp;</th>
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
          <c:forEach var="ShiftTimeModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor" >
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id,${loopCounter.count})"> ${ShiftTimeModel.beneficiary_id}<input type="hidden" id="beneficiary_id" name="beneficiary_id" value="${ShiftTimeModel.beneficiary_id}"></td>
            <td id="t1c${IDGenerator.uniqueID}" align="center" onclick="fillColumns(id,${loopCounter.count})"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.name}<input type="hidden" id="name" name="name" value="${ShiftTimeModel.name}"></td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.father_name}</td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})" >${ShiftTimeModel.mobile_no}<input type="hidden" id="mobile_no" name="mobile_no" value="${ShiftTimeModel.mobile_no}"></td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.occupation_name}<input type="hidden" id="occupation_name" name="occupation_name" value="${ShiftTimeModel.occupation_name}"></td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.location}<input type="hidden" id="location" name="location" value="${ShiftTimeModel.location}"></td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.is_residencial}<input type="hidden" id="is_residencial" name="is_residencial" value="${ShiftTimeModel.is_residencial}"></td>
             <td id="t1c${IDGenerator.uniqueID}">
                  H.<input type="numeric"  id="time_hour_${loopCounter.count}" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" size="2" maxlength="2" name="time_hour" value="" >
                  M.<input type="numeric"  id="time_min_${loopCounter.count}"  size="2"  maxlength="2" pattern="[0-5]{1}[0-9]{1}"  name="time_min" value="" >
             </td>
            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id,${loopCounter.count})">${ShiftTimeModel.status}</td>
            <td  id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id,${loopCounter.count})"><input type="hidden" id="status_${loopCounter.count}"name="status" value="${ShiftTimeModel.status}"></td>
            <td id="t1c${IDGenerator.uniqueID}"   onclick="fillColumns(id,${loopCounter.count}">
                <select class="dropdown1" id="reason_name_${loopCounter.count}" name="reason" style="display: none">
                                    <option value="0" >Select</option>
                                    <c:forEach var="reason" items="${reason_name}">
                                       <option value="${reason.key}">${reason.value}</option>
                                   </c:forEach>
                               </select>
                  </td>

            </tr>
          </c:forEach>        
       <tr> <td align="center" colspan="10"> <input  type="submit" name="task" id="save" value="Save"></td></tr>
        </TABLE>
     <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
     <input type="hidden" id="noOfRowsTraversed"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
     <input type="hidden"  name="noOfRowsInTable" value="${noOfRowsInTable}">
     <input type="hidden"  name="person_name" value="${person_name}">
     <input type="hidden"  name="date" value="${date}">
     <input type="hidden"  name="zone" value="${zone}">
      <input type="hidden"  name="ward" value="${ward}">
      <input type="hidden"  name="area" value="${area}">
      <input type="hidden"  name="zone_no" value="${zone_no}">
      <input type="hidden"  name="ward_no" value="${ward_no}">
      <input type="hidden"  name="area_no" value="${area_no}">
       <input type="hidden"  name="emp_code" value="${emp_code}">
         <input type="hidden"  name="cut_dt" value="${cut_dt}">
        </form>
         <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
            </DIV>
         </td>
        
      </table>

    </body>
</html>
