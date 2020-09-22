

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
    
     document.getElementById("shift_type_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
    // document.getElementById("rev_no").value= document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
     document.getElementById("shift_type").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
      setStartingTime(document.getElementById(t1id +(lowerLimit+3)).innerHTML);
      setEndingTime(document.getElementById(t1id +(lowerLimit+4)).innerHTML);
     // document.getElementById("starting_time").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
     
       // document.getElementById("ending_time").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        
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
 function verify() {
     
       var result;
        var clickedButton = document.getElementById("clickedButton").value;
        if(clickedButton == 'save' || clickedButton == 'Save AS New') {
          
                      var shift_type = document.getElementById("shift_type").value;
                                if(myLeftTrim(shift_type).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Shift Type is required...</b></td>");
                                    document.getElementById("shift_type").focus();
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
        document.getElementById("shift_type").disabled = false;
        document.getElementById("starting_time_hour").disabled = false;
        document.getElementById("starting_time_min").disabled = false;
        document.getElementById("ending_time_hour").disabled = false;
        document.getElementById("ending_time_min").disabled = false;
       
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
                                        <td align="center" class="header_table" width="90%"><b>Shift Type</b></td>
                                    </tr>
                                </table>
                            </td></tr>


   <form name="form1" action="ShiftController">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">Shift Type&nbsp;</TH>
                <TH class="heading">Starting Time&nbsp;</TH>
                <TH class="heading">Ending Time&nbsp;</TH>
               
            <c:forEach var="ShiftModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftModel.shift_type_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${ShiftModel.shift_type}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.starting_time}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.ending_time}</td>
            
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

                      <form  action="ShiftController" method="post" onsubmit="return verify()">
                       
                           <table  align="center"  class="content" cellpadding="2%" border="1">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="shift_type_id" name="shift_type_id" value="" ></tr>
                              <tr>
                              
                                 <th class="heading1"> Shift Type </th>
                                 <td><input type="text" class="new_input" id="shift_type" size="19" name="shift_type" value="" disabled></td>
                             
                              </tr>
                              <tr>
                                                <th class="heading1">Starting Time</th>
                                                <td>
                                                    H.<input type="numeric"  id="starting_time_hour" size="5" name="starting_time_hour" value="" disabled>
                                                    M.<input type="numeric"  id="starting_time_min" size="5" name="starting_time_min" value="" disabled>
                                                </td>
                              </tr>
                              <tr>
                                                <th class="heading1">Ending Time</th>
                                                <td>
                                                    H.<input type="numeric"  id="ending_time_hour" size="5" name="ending_time_hour" value="" disabled>
                                                    M.<input type="numeric"  id="ending_time_min" size="5" name="ending_time_min" value="" disabled>
                                                </td>
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
