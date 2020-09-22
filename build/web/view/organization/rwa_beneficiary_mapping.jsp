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

          $("#rwa_name").autocomplete("RwaBeneficiaryController", {
            extraParams: {
                action1: function() { return "getRwaName";}
                 
            }
            });

        $("#beneficiary_name").autocomplete("RwaBeneficiaryController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code").val();}
            }
            });
           $("#beneficiary_name").result(function(event, data, formatted){
            $.ajax({url: "RwaBeneficiaryController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code").val(response_data.trim());
                   }
                   });
            });

                $("#emp_code").autocomplete("RwaBeneficiaryController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code").result(function(event, data, formatted){
                   $.ajax({url: "RwaBeneficiaryController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#beneficiary_name").val(response_data.trim());
                   }
                   });
            });

               });
    function fillColumns(id)
     {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 5;
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

     document.getElementById("rwa_beneficiary_mapping_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
    // document.getElementById("rev_no").value= document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
     document.getElementById("rwa_name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
    document.getElementById("beneficiary_name").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
    document.getElementById("emp_code").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
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
        document.getElementById("rwa_name").disabled = false;
        document.getElementById("beneficiary_name").disabled = false;
    document.getElementById("emp_code").disabled = false;
        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("shift_type_id").value=0;




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
                                        <td align="center" class="header_table" width="90%"><b>RWA Beneficiary Mapping</b></td>
                                    </tr>
                                </table>
                            </td></tr>


   <form name="form1" action="RwaBeneficiaryController">


        <TABLE BORDER="1" align="center" cellpadding="%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">RWA Name&nbsp;</TH>
                <TH class="heading">Beneficiary Name&nbsp;</TH>
           

            <c:forEach var="ShiftModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftModel.rwa_beneficiary_mapping_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${ShiftModel.rwa_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.b_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="display: none">${ShiftModel.emp_code}</td>
   

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

                      <form  action="RwaBeneficiaryController" method="post" >

                           <table  align="center"  class="content" cellpadding="2%" border="1">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="rwa_beneficiary_mapping_id" name="rwa_beneficiary_mapping_id" value="" ></tr>
                              <tr>

                                 <th class="heading1">RWA Name </th>
                                 <td><input type="text" class="new_input" id="rwa_name" size="19" name="rwa_name" value="" disabled>
                              </tr>

                                 <tr>

                                 <th class="heading1"> Beneficiary Name </th>
                                 <td><input type="text" class="new_input" id="beneficiary_name" size="19" name="beneficiary_name" value="" disabled>
                                     <input type="text"  id="emp_code"  size="5"  name="emp_code" placeholder='code'  value="" disabled></td>
                              </tr>


             <tr>
            <td colspan="2" >
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
