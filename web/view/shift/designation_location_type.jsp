

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
        $("#location_type").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getLocationType"}
            }
        });
    });
    jQuery(function(){
        $("#designation").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getDesignation"}
            }
        });
    });
      jQuery(function(){
        $("#shift_type").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getShiftType"}
            }
        });
    });
         jQuery(function(){
        $("#search_location").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getLocation"}
            }
        });
    });
    jQuery(function(){
        $("#search_designation").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getDesignation"}
            }
        });
    });
      jQuery(function(){
        $("#search_shift_type").autocomplete("DesignationLocationTypeController", {
            extraParams: {
                action1: function() { return "getShiftType"}
            }
        });
    });
    function fillColumns(id)
     {

        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 7;
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

     document.getElementById("designation_location_type_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;

     document.getElementById("shift_type").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
      document.getElementById("location").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("designation").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("no_of_person").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("map_id2").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
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

      function setDefaultColor(noOfRowsTraversed, noOfColumns) {

        for(var i = 0; i < noOfRowsTraversed; i++) {

            for(var j = 1; j <= noOfColumns; j++) {

                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.

        }
        }
    }
    }

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
        if(clickedButton == 'save' || clickedButton == 'Save AS New' || clickedButton == 'Revised') {

                      var shift_type = document.getElementById("shift_type").value;
                                if(myLeftTrim(shift_type).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Shift Type is required...</b></td>");
                                    document.getElementById("shift_type").focus();
                                    return false;
                                }
                        var location = document.getElementById("location").value;
                                if(myLeftTrim(location).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Location  is required...</b></td>");
                                    document.getElementById("location").focus();
                                    return false;
                                }
                        var designation = document.getElementById("designation").value;
                                if(myLeftTrim(designation).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Designation  is required...</b></td>");
                                    document.getElementById("designation").focus();
                                    return false;
                                }

                             var no_of_person = document.getElementById("no_of_person").value;
                                if(myLeftTrim(no_of_person).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> No Of Person is required...</b></td>");
                                    document.getElementById("no_of_person").focus();
                                    return false;
                                }

        return result;

        }
    }

       function makeEditable(id) {

        document.getElementById("location_type").disabled = false;
        document.getElementById("common_type").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("no_of_person").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("update").disabled =true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("designation_location_type_id").value=0;




        }
        if(id == 'edit'){
               $("#message").html("");
                document.getElementById("save_as").disabled = false;
                document.getElementById("delete").disabled = false;
                 document.getElementById("save").disabled = false;
                 document.getElementById("update").disabled = false;
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
        else if(id == 'update'){
            document.getElementById("clickedButton").value = "Revised";
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
                                        <td align="center" class="header_table" width="90%"><b> Designation Location Type</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                        <%--       <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="DesignationLocationTypeController">
                                        <table align="center" class="heading1" width="800">
                                            <tr>
                                                <th>Shift Type</th>
                                                <td><input  type="text" id="search_shift_type" name="search_shift_type" value="${search_shift_type}" size="10" ></td>
                                                <th>Location</th>
                                                <td><input  type="text" id="search_location" name="search_location" value="${search_location}" size="10" ></td>

                                                <th>Designation</th>
                                                 <td>
                                                    <input  type="text" id="search_designation" name="search_designation" value="${search_designation}" size="10">
                                                </td>
                                                <td colspan="2"><input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" style="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                            </td>
                             </tr> --%>
 <form name="form1" action="DesignationLocationTypeController">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <th class="heading">S.no&nbsp;</th>
                <TH class="heading">Location Type&nbsp;</TH>
                <TH class="heading">Common Type&nbsp;</TH>
                <th class="heading">Designation&nbsp;</th>
                <th class="heading">No Of Person&nbsp;</th>
                <th class="heading">Remark&nbsp;</th>
            <c:forEach var="ShiftDesignationLocationModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftDesignationLocationModel.map_id1}</td>

            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.shift_type}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.location}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.designation}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.no_of_person}</td>
            <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)"> ${ShiftDesignationLocationModel.map_id2}</td>
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
    <input type="hidden"  name="search_shift_type" value="${search_shift_type}" >
    <input type="hidden"  name="search_location" value="${search_location}" >
    <input type="hidden"  name="search_designation" value="${search_designation}" >

        </form>

                      <form  action="DesignationLocationTypeController" method="post" >
                          <table  align="center"  class="content" border="1">
                                         <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="designation_location_type_id" name="designation_location_type_id" value="" ></tr>
                              <tr>
                              <th class="heading1"> Location Type </th>
                              <td><input type="text"  id="location_type" name="location_type" value="" disabled></td>
                              </tr>
                                <tr>
                              <th class="heading1"> Common Type </th>
                              <td><input type="text"  id="common_type" name="common_type" value="" disabled></td>
                              </tr>
                              <tr>
                              <th class="heading1"> Designation </th>
                              <td><input type="text"  id="designation" name="designation" value="" disabled></td>
                              </tr>
                               <tr>
                              <th class="heading1">No Of Person </th>
                              <td><input type="text"  id="no_of_person" name="no_of_person" value="" disabled></td>
                              </tr>
                                <tr>
                              <th class="heading1">Remark </th>
                              <td><input type="text"  id="remark" name="remark" value="" disabled></td>
                              </tr>
                           <tr>
            <td colspan="2" >
           <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >
           <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
           <input  class="button" type="submit" name="task" id="update" value="Revised" onclick="setStatus(id)" disabled>
          <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
           <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
            <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
            </td>
            </tr>
                          </table>
                    <input type="hidden" id="clickedButton" value="">
            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
             <input type="hidden"  name="search_shift_type" value="${search_shift_type}" >
       <input type="hidden"  name="search_location" value="${search_location}" >
       <input type="hidden"  name="search_designation" value="${search_designation}" >
                      </form>
                   </table>
            </DIV>
         </td>
         <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
      </table>

    </body>
</html>
