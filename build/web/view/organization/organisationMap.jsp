<%--
    Document   : dataEntry_lighting
    Created on : Dec 9, 2011, 12:36:33 PM
    Author     : Soft_Tech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry:Organization Mapping Table</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="calendar.css" rel="stylesheet" type="text/css">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            var popupwin =null;
            jQuery(function(){

                $("#organisation_name").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });
                $("#organisation").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });
                $("#org_type_name").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgTypeName"}
                    }
                });
                $("#org_type").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgTypeName"}
                    }
                });
                $("#organisation_sub_type_name").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgSubTypeName"},
                        action2: function() { return document.getElementById("org_type_name").value;}
                    }
                });
                $("#org_sub_type").autocomplete("mapOrgCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgSubTypeName"},
                        action2: function() { return document.getElementById("org_type").value;}
                    }
                });
            });
            function makeEditable(id) {
                document.getElementById("organisation_name").disabled = false;
                document.getElementById("org_type_name").disabled = false;
                document.getElementById("organisation_sub_type_name").disabled = false;
                document.getElementById("description").disabled = false;
                document.getElementById("organisation_name").focus();
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("org_map_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("clickedButton2").value = "new";
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 5);
                }
                if(id == 'edit') {
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("clickedButton2").value = "edit";
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 5;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).

                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                document.getElementById("org_map_id").value= document.getElementById("org_map_id" + rowNo).value;
                document.getElementById("organisation_name").value = document.getElementById(t1id +(lower+1)).innerHTML;
                document.getElementById("org_type_name").value = document.getElementById(t1id +(lower+2)).innerHTML;
                document.getElementById("organisation_sub_type_name").value = document.getElementById(t1id +(lower+3)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id +(lower+4)).innerHTML;

                for(var i = 0; i <= 4; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else if(id == 'edit') {
                    document.getElementById("clickedButton").value = "Edit";0
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else document.getElementById("clickedButton").value = "Delete";;
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

            //############################################################################

            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var organisation_name = document.getElementById("organisation_name").value;
                    var org_type_name = document.getElementById("org_type_name").value;
                    var organisation_sub_type_name = document.getElementById("organisation_sub_type_name").value;

                    if(myLeftTrim(organisation_name).length == 0) {
                        //document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        document.getElementById("organisation_name").focus();
                        return false;
                    }
                    if(myLeftTrim(org_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Organisation Type Name is required...</b></td>");
                        document.getElementById("org_type_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(organisation_sub_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organization Sub Type Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organization Sub Type Name is required...</b></td>");
                        document.getElementById("organisation_sub_type_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false)
                    {
                        // if result has value false do nothing, so result will remain contain value false.
                    }
                    else{
                        result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }

            function setformStatus(){
                document.getElementById("organisation").value="";
                document.getElementById("org_type").value="";
                document.getElementById("organis").value="";
                document.getElementById("org_tpe").value="";
                document.getElementById("org_sub_type").value="";
                document.getElementById("org_sub_type1").value="";
            }

            function displayOrgnList(id){

                var organisation= document.getElementById("organisation").value;
                var org_type= document.getElementById("org_type").value;
                var org_sub_type= document.getElementById("org_sub_type").value;
                var queryString ;
                if(id=='viewPdf')
                 queryString = "requester=PRINT&organisation="+organisation+"&org_type="+org_type+"&org_sub_type="+org_sub_type;
             else
                  queryString = "requester=PRINTXls&organisation="+organisation+"&org_type="+org_type+"&org_sub_type="+org_sub_type;
                var url = "mapOrgCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }
        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>

                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td align="center"  class="header_table" width="800" >Organization Map Relation</td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td>
                                    <form name="form1" method="POST" action="mapOrgCont.do">
                                        <table width="100%"  class="heading1">
                                            <tr align="center">
                                                <td>
                                                    Organization <input type="text" id="organisation" name="organisation" value="${organisation}" size="30">
                                                    Org Type<input type="text" id="org_type" name="org_type" value="${org_type}">
                                                    Org Sub Type<input type="text" id="org_sub_type" name="org_sub_type" value="${org_sub_type}">
                                                    <input type="submit" class="button" id="search" name="task" value="Search">
                                                    <input type="button" class="button" id="clear_org" name="clear_org" value="CLEAR" onclick="setformStatus()">
                                                    <input type="button" class="pdf_button" id="viewPdf" name="view_pdf" value="" onclick="displayOrgnList(id)">
                                                    <input type="button" class="button" id="viewXls" name="view_pdf" value="Excel" onclick="displayOrgnList(id)">

                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr>

                                <td align="center">
                                    <form name="form1" method="post" action="mapOrgCont.do" >
                                        <DIV class="content_div">
                                            <table border="1" id="table1" align="center"  class="content" width="750">
                                                <tr>
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Organization name</th>
                                                    <th class="heading" >Organization Type</th>
                                                    <th class="heading" >Organization SubType</th>
                                                    <th class="heading" >Description</th>
                                                </tr>
                                                <c:forEach var="orgMap" items="${orgMapList}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="org_map_id${loopCounter.count}" value="${orgMap.org_map_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${orgMap.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${orgMap.org_type_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${orgMap.organisation_sub_type_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${orgMap.description}</td>

                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="5">
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
                                                                <input class="button" type='submit' name='buttonAction' value='Previous' disabled >
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
                                                                <input class="button" type='submit' name='buttonAction' value='Last'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table>
                                        </DIV>
                                        <input type="hidden" id="organis" name="organisation" value="${organisation}" >
                                        <input type="hidden" id="org_tpe" name="org_type" value="${org_type}">
                                        <input type="hidden" id="org_sub_type1" name="org_sub_type" value="${org_sub_type}">
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form2"  action="mapOrgCont.do" method="post" onsubmit="return verify();">
                                        <DIV>
                                            <table  border="0" id="table2" align="center"  class="content" width="750">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Organization Name</th>
                                                    <td>
                                                        <input type="hidden" id="org_map_id" name="org_map_id" value="" >
                                                        <input class="input" type="text" id="organisation_name" name="organisation_name" size="30" value="" disabled >
                                                    </td>

                                                    <th class="heading1">Organization Type</th><td><input class="input" id="org_type_name" name="org_type_name" value="" size="30" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Organization SubType</th><td><input class="input" id="organisation_sub_type_name" name="organisation_sub_type_name" value="" size="30" disabled></td>

                                                    <th class="heading1">Description</th><td><input class="input" type="text" id="description" name="description" value="" size="30" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="4">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id);setStatus(id);" disabled> 
                                                        <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);" disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> 
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                                <input type="hidden" id="clickedButton2" value="">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

