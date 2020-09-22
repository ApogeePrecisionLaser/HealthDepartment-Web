<%--
    Document   : workType
    Created on : Feb 16, 2012, 6:18:45 PM
    Author     : Tarun
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Organization Name Table</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            var popupwin = null;
            jQuery(function(){

//                $("#organisation_name").autocomplete("orgNameCont.do", {
//                    extraParams: {
//                        action1: function() { return "getOrganisationName"}
//                              }
//                });
                $("#org_name").autocomplete("orgNameCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganisationName"}
                                            }
                    });
            });
            function makeEditable(id) {
                document.getElementById("organisation_name").disabled = false;
                document.getElementById("description").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("organisation_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("organisation_name").focus();
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
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
                var noOfColumns = 3;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("organisation_id").value = document.getElementById("organisation_id" + rowNo).value;
                document.getElementById("organisation_name").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
              for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)
                {// if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                } else if(id == 'search_org'){
                    var org_name=document.getElementById("org_name").value;
                    document.getElementById("org_name1").value =  org_name;
                    document.getElementById("org_name2").value =  org_name;
                    document.getElementById("clickedButton").value = "SEARCH";
                }
                else if(id == 'clear_org'){
                    document.getElementById("clickedButton").value = " ";
                    $("#org_msg").html("");
                    document.getElementById("org_name").value =" ";
                    document.getElementById("org_name1").value =  " ";
                    document.getElementById("org_name2").value =  " ";
                }
                else {
                    document.getElementById("clickedButton").value = "Delete";;
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
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var organisation_name = document.getElementById("organisation_name").value;
                    if(myLeftTrim(organisation_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        document.getElementById("organisation_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }
            function verifySearch(){
                var result;
                if(document.getElementById("clickedButton").value == 'SEARCH') {
                    var org_name = document.getElementById("org_name").value;
                    if(myLeftTrim(org_name).length == 0) {
                        document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                        document.getElementById("org_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                }
            }

            function displayOrgnList(id){               
                var queryString;
                if(id=='viewPdf')
                queryString = "requester=PRINT";
            else
                queryString = "requester=PRINTXls";
                var url = "orgNameCont.do?"+queryString;
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

//             function changeClass(){
//                        var language=document.getElementById("language").value;
//                        if(language=='English'){
//                            $( "#organisation_name" ).addClass('input').removeClass('new_input');
//                            $("#description" ).addClass('input').removeClass('new_input');
//                             $("#org_name" ).addClass('input').removeClass('new_input');
//                              }
//                        else{
//                            $( "#organisation_name" ).addClass('new_input').removeClass('input');
//                            $("#description" ).addClass('new_input').removeClass('input');
//                                $("#org_name" ).addClass('new_input').removeClass('input');
//                            }
//                    }
//                     function changeSearchClass(){
//                        var language=document.getElementById("language_type").value;
//                        if(language=='English'){
//                            $( "#org_name" ).addClass('input').removeClass('new_input');
//                              }
//                        else{
//                            $( "#org_name" ).addClass('new_input').removeClass('input');
//                                            }
//                    }
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
                                    <table align="center" >
                                        <tr>
                                            <td class="header_table" width="800" align="center"> Organization Name </td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="orgNameCont.do" onsubmit="return verifySearch();">
                                        <table  class="heading1" width="700">
                                            <tr>
                                                <td colspan="4" align="center">
                                                    Organization&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="new_input" id="org_name" name="org_name" value="${org_name}" size="35">
                                                      <input type="submit" class="button" id="search_org" name="search_org" value="SEARCH" onclick="setStatus(id)">
                                                    <input type="submit" class="button" id="clear_org" name="clear_org" value="CLEAR" onclick="setStatus(id)">
                                                    &ensp; <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayOrgnList(id)">
                                                    <input type="button" class="button" id="viewXls" name="viewXls" value="Excel" onclick="displayOrgnList(id)">
                                                    <label id="org_msg">  </label>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="orgNameCont.do">
                                        <DIV class="content_div">
                                            <table border="1" id="table1" align="center"  class="content" width="650">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Organisation Name</th>
                                                    <th class="heading">Description</th>
                                                </tr>
                                                <c:forEach var="orgName" items="${requestScope['orgNameList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="organisation_id${loopCounter.count}" value="${orgName.organisation_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgName.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgName.description}</td>
                                                        </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="3">
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
                                                                <input class="button" type='submit' name='buttonAction' value='Last'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="org_name2" name="org_name" value="${org_name}">
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="orgNameCont.do" onsubmit="return verify()">
                                        <table class="content" border="0" id="table2" align="center" width="650">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                                 <tr>

                                                <th class="heading1">Organisation Name</th><td>
                                                    <input class="input new_input" type="hidden" id="organisation_id" name="organisation_id" value="" >
                                                    <input class="input new_input" type="text" id="organisation_name" name="organisation_name" size="60" value="" disabled>
                                                </td>
                                            </tr>
                                            <tr>

                                                <th class="heading1">Description</th><td><input class="input" type="text" id="description" name="description" size="60" value="" disabled></td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="2" >
                                                    <input type="button" class="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input type="submit" class="button" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input type="submit" class="button" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input type="reset" class="button" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input type="submit" class="button" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" id="org_name1" name="org_name" value="${org_name}">
                                        </table>
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
