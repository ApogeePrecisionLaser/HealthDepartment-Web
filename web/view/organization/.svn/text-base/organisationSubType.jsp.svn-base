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
        <title>Data Entry: Organization Sub Type </title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#org_type_name").autocomplete("organisationSubTypeCont.do", {
                    extraParams: {
                        param1: function() { return "getOrgTypeName"}
                    }
                });
                $("#searchOrgType").autocomplete("organisationSubTypeCont.do", {
                    extraParams: {
                        param1: function() { return "getOrgTypeName"}
                    }
                });
                $("#searchOrgSubType").autocomplete("organisationSubTypeCont.do", {
                    extraParams: {
                        param1: function() { return "getOrgSubTypeName"}
                    }
                });
            });
            function makeEditable(id) {
                document.getElementById("org_type_name").disabled = false;
                document.getElementById("organisation_sub_type_name").disabled = false;
                if(id == 'new') {
                    document.getElementById("org_type_name").focus();
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("organisation_sub_type_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                }
                if(id == 'edit'){
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("org_type_name").focus();
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

                document.getElementById("organisation_sub_type_id").value= document.getElementById("organisation_sub_type_id" + rowNo).value;
                document.getElementById("org_type_name").value = document.getElementById(t1id +(lowerLimit+1)).innerHTML;
                document.getElementById("organisation_sub_type_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
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
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
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
            function verify() {
                var result;                
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var organisation_sub_type_name = document.getElementById("org_type_name").value;
                    var org_type_name = document.getElementById("organisation_sub_type_name").value;
                    if(myLeftTrim(organisation_sub_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Sub Type Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Sub Type Name is required...</b></td>");
                        document.getElementById("org_type_name").focus();
                        return false;  // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(org_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        alert("Organization Type Name is required");
                        document.getElementById("organisation_sub_type_name").focus();
                        return false;  // code to stop from submitting the form2.
                    }
                    if(result == false)
                    {// if result has value false do nothing, so result will remain contain value false.
                    }
                    else{ result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }
            function displayOrgSubTypeList(id)
            {
              var searchOrgType = document.getElementById('searchOrgType').value;
              var searchOrgSubType = document.getElementById('searchOrgSubType').value;             
                var action;
                if(id=='viewPdf')
                 action="task=generateOrgReport&searchOrgType="+searchOrgType+"&searchOrgSubType="+searchOrgSubType;
               else
                action="task=generateOrgXlsReport&searchOrgType="+searchOrgType+"&searchOrgSubType="+searchOrgSubType;
                var url="organisationSubTypeCont.do?"+action;
                popup = popupWindow(url,"Org_Subtype_Report");
            }
            function popupWindow(url,windowName)
            {
                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url,windowName,windowfeatures)
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
                                    <table width="100%">
                                        <tr>
                                            <td class="header_table" align="center" width="800">Organization Sub Type</td>
                                             <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                        <table>
                                            <tr>
                                                <td align="center">
                                                    <form name="form1" method="POST" action="organisationSubTypeCont.do">
                                                    <table  align="center"  class="heading1">
                                                        <tr>
                                                            <th> Organization Type </th>
                                                            <td><input class="input new_input" type="text" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" size="30" ></td>
                                                            <th> Organization Sub Type </th>
                                                            <td><input class="input new_input" type="text" id="searchOrgSubType" name="searchOrgSubType" value="${searchOrgSubType}" size="30" ></td>
                                                            <td><input class="button" type="submit" name="task" id="searchInOrgSubType" value="Search"></td>
                                                            <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                        </tr>
                                                        <tr><td colspan="5" align="center">
                                                             <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayOrgSubTypeList(id);"/>
                                                           <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayOrgSubTypeList(id);"/>
                                                            </td> </tr>
                                                    </table>
                                                    </form>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <form name="form1" method="POST" action="organisationSubTypeCont.do">
                                                    <DIV class="content_div">
                                                        <table id="table1"  border="1" align="center" width="600" class="content">
                                                            <tr>
                                                                <th class="heading">S.No.</th>
                                                                <th class="heading">Organization Type Name</th>
                                                                <th class="heading">Organization Sub Type Name</th>
                                                            </tr>
                                                            <c:forEach var="organisationSubType" items="${requestScope['orgSubTypeList']}"  varStatus="loopCounter">
                                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                                        <input type="hidden" id="organisation_sub_type_id${loopCounter.count}" value="${organisationSubType.organisation_sub_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                    </td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisationSubType.org_type_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisationSubType.organisation_sub_type_name}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td align='center' colspan="3">
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
                                                            <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}"  >
                                                            <input  type="hidden"  name="searchOrgSubType" value="${searchOrgSubType}" >
                                                        </table>
                                                    </DIV>
                                                        </form>
                                                </td>
                                            </tr>
                                        </table>

                                    
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <DIV>
                                        <form name="form2" method="POST" action="organisationSubTypeCont.do" onsubmit="return verify()">
                                            <table id="table2" border="0" align="center" class="content" width="600">

                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>
                                                    <th class="heading1" >Organization Type Name</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="organisation_sub_type_id" name="organisation_sub_type_id" value="" >
                                                        <input class="input new_input" type="text" id="org_type_name" name="org_type_name" value="" size="45" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Organization Sub Type Name</th><td><input class="input new_input" type="text" id="organisation_sub_type_name" name="organisation_sub_type_name" value="" size="45" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="2">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled> 
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> 
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}"  >
                                                <input  type="hidden"  name="searchOrgSubType" value="${searchOrgSubType}" >
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">

                                            </table>
                                        </form> </DIV>
                                </td>
                            </tr>
                        </table>

                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

