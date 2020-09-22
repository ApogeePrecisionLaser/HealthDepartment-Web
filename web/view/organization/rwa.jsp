<%-- 
    Document   : rwa
    Created on : Jul 14, 2016, 3:16:21 PM
    Author     : Administrator
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
        <title>JSP Page</title>
         <script type="text/javascript">

        jQuery(function(){
    
          $("#president_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code1").val();}
            }
            });
           $("#president_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code1").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code1").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code1").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#president_name").val(response_data.trim());
                   }
                   });
            });



       $("#secretary_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code2").val();}
            }
            });
           $("#secretary_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code2").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code2").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code2").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#secretary_name").val(response_data.trim());
                   }
                   });
            });




              $("#treasrar_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code3").val();}
            }
            });
           $("#treasrar_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code3").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code3").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code3").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#treasrar_name").val(response_data.trim());
                   }
                   });
            });



            $("#working1_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code4").val();}
            }
            });
           $("#working1_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code4").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code4").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code4").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#working1_name").val(response_data.trim());
                   }
                   });
            });



              $("#working2_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code5").val();}
            }
            });
           $("#working2_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code5").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code5").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code5").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#working2_name").val(response_data.trim());
                   }
                   });
            });



                    $("#working3_name").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code6").val();}
            }
            });
           $("#working3_name").result(function(event, data, formatted){
            $.ajax({url: "RwaController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code6").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code6").autocomplete("RwaController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code6").result(function(event, data, formatted){
                   $.ajax({url: "RwaController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#working3_name").val(response_data.trim());
                   }
                   });
            });

    });
 


   


    function fillColumns(id)
     {

        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 18;
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

     document.getElementById("rwa_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
     document.getElementById("rwa_name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
 
     document.getElementById("president_name").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
     document.getElementById("emp_code1").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
     document.getElementById("secretary_name").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
     document.getElementById("emp_code2").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
     document.getElementById("treasrar_name").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
     document.getElementById("emp_code3").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
     document.getElementById("working1_name").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
     document.getElementById("emp_code4").value=document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
     document.getElementById("working2_name").value=document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
     document.getElementById("emp_code5").value=document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
     document.getElementById("working3_name").value=document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
     document.getElementById("emp_code6").value=document.getElementById(t1id + (lowerLimit + 14)).innerHTML
       var vehicle_type = document.getElementById(t1id +(lowerLimit+15)).innerHTML;
        $("#payment_schedule option").each(function()
        {
            if($(this).text()==vehicle_type)
                $(this).attr('selected', true);
        });
      document.getElementById("monthly_fee").value=document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
      document.getElementById("description").value=document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
    
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }

        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as_new").disabled = true;
            document.getElementById("delete").disabled = false;
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
         document.getElementById("rwa_name").disabled = false;
        document.getElementById("president_name").disabled = false;
          document.getElementById("emp_code1").disabled = false;
        document.getElementById("secretary_name").disabled = false;
          document.getElementById("emp_code2").disabled = false;
        document.getElementById("treasrar_name").disabled = false;
          document.getElementById("emp_code3").disabled = false;
        document.getElementById("working1_name").disabled = false;
          document.getElementById("emp_code4").disabled = false;
        document.getElementById("working2_name").disabled = false;
          document.getElementById("emp_code5").disabled = false;
        document.getElementById("working3_name").disabled = false;
          document.getElementById("emp_code6").disabled = false;
        document.getElementById("payment_schedule").disabled = false;
           document.getElementById("monthly_fee").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("rwa_id").value=0;




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
    <body>
    <table align="center" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr><td>
                <DIV id="body" class="maindiv" align="center" >
                    <table  align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" ><b>RWA</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr ><td>
      <div class="content_div">
       <form name="form1"  action="RwaController">
        <TABLE BORDER="1" align="center" cellpadding="5%"  class="content">             
                <TH class="heading">S.no</TH>
                <TH class="heading">RWA Name</TH>
                <TH class="heading" style="white-space: normal">President Name</TH>
                <TH class="heading" style="white-space: normal">Secretary Name</TH>
                <TH class="heading" style="white-space: normal">Treasrar Name</TH>
                <TH class="heading">Working1</TH>
                <TH class="heading">Working2</TH>
                <TH class="heading">Working3</TH>
                <TH class="heading" style="white-space: normal">Payment Schedule</TH>
                <TH class="heading">Monthly Fee</TH>
                <TH class="heading">Description</TH>              

         <c:forEach var="rwa" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">           
            <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)"> ${rwa.rwa_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.rwa_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.president_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="display: none">${rwa.emp_code1}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.secretary_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${rwa.emp_code2}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.treasrar_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${rwa.emp_code3}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.working1_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${rwa.emp_code4}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.working2_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${rwa.emp_code5}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.working3_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${rwa.emp_code6}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.payment_schedule_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.monthly_fee}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${rwa.description}</td>           
            </TR>
         </c:forEach>

            <tr> <td align='center' colspan="12">
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
    </div></td></tr>
                        <tr><td>            <div  align="center"  style="margin-top: 0px">
                      <form  action="RwaController" method="post" >
                           <table  align="center"  class="content" cellpadding="0%" border="1"  >
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="rwa_id" name="rwa_id" value="" ></tr>
                              <tr>
                                        <th class="heading1"> RWA Name</th>
                                        <td><input type="text" class="new_input" id="rwa_name"  name="rwa_name" value="" disabled></td>

                                       <th class="heading1"> Monthly Fee</th>
                                       <td><input type="text" id="monthly_fee"  name="monthly_fee" value="" disabled></td>
                              </tr>
                             <tr>
                                 <th class="heading1"> President Name </th>
                                 <td><input type="text" class="new_input" id="president_name"  name="president_name" value="" disabled>
                                      <input type="text"  id="emp_code1"  size="5"  name="emp_code1" placeholder='code'  value="" disabled></td>
                                  <th class="heading1"> Secretary Name </th>
                                     <td><input type="text" class="new_input" id="secretary_name"  name="secretary_name" value="" disabled>
                                      <input type="text"  id="emp_code2"  size="5"  name="emp_code2" placeholder='code'  value="" disabled></td>
                              </tr>
                              <tr>
                                 <th class="heading1"> Treasrar Name </th>
                                 <td><input type="text" class="new_input" id="treasrar_name"  name="treasrar_name" value="" disabled>
                                      <input type="text"  id="emp_code3"  size="5"  name="emp_code3" placeholder='code'  value="" disabled></td>
                                  <th class="heading1"> Working1 Name </th>
                                     <td><input type="text" class="new_input" id="working1_name"  name="working1_name" value="" disabled>
                                      <input type="text"  id="emp_code4"  size="5"  name="emp_code4" placeholder='code'  value="" disabled></td>
                                </tr>
                                 <tr>
                                 <th class="heading1"> Working2 Name </th>
                                 <td><input type="text" class="new_input" id="working2_name"  name="working2_name" value="" disabled>
                                      <input type="text"  id="emp_code5"  size="5"  name="emp_code5" placeholder='code'  value="" disabled></td>
                                  <th class="heading1"> working3 Name </th>
                                     <td><input type="text" class="new_input" id="working3_name"  name="working3_name" value="" disabled>
                                      <input type="text"  id="emp_code6"  size="5"  name="emp_code6" placeholder='code'  value="" disabled></td>
                                </tr>
                              <tr>
                                   <th class="heading1">Payment Schedule</th>
                                                  <td>
                                                        <select class="dropdown1" id="payment_schedule" name="payment_schedule" disabled>
                                                             <option value="0">Select</option>
                                                            <c:forEach var="payment" items="${payment_type}">
                                                                <option value="${payment.key}">${payment.value}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                  <th class="heading1"> Description</th>
                                 <td><input type="text" class="input" id="description"  name="description" value="" disabled></td>
                              </tr>

             <tr>
                 <td align="center" colspan="4" >
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
            </div></td></tr>

              <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
              
             </table>
            </DIV>
         </td>
            </tr>
      </table>
    </body>
</html>
