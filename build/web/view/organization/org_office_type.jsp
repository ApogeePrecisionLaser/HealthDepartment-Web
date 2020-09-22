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
        <title>Data Entry:Organization office Type </title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#searchOrgOfficeType").autocomplete("orgOfficeTypeCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganisationOfficeType"}
                    }
                });

            });
            jQuery(function(){
                $("#searchOrgOfficeCode").autocomplete("orgOfficeTypeCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganisationOfficeCode"}
                    }
                });
                $("#office_code").autocomplete("orgOfficeTypeCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganisationOfficeCode"}
                    }
                });

            });
            function makeEditable(id) {
                document.getElementById("office_type").disabled = false;
                //document.getElementById("office_code").disabled = false;
                document.getElementById("description").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("office_type_id").value = "";
                    document.getElementById("save").disabled = false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                    document.getElementById("office_code").focus();
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
                document.getElementById("office_type_id").value = document.getElementById("office_type_id" + rowNo).value;
                document.getElementById("office_type").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                //  document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
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
                    var office_type = document.getElementById("office_type").value;
                   
                    if(myLeftTrim(office_type).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Office Type is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Office Type is required...</b></td>");
                        document.getElementById("office_type").focus();
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
             function orgOfficeReport(id)
            {
                var searchOfficeType = document.getElementById('searchOrgOfficeType').value;
                var action;
                if(id=='viewPdf')
                 action="task=generateOrgOfficeReport&searchOfficeType="+searchOfficeType;
               else
                action="task=generateOrgOfficeXlsReport&searchOfficeType="+searchOfficeType;
                var url="orgOfficeTypeCont.do?"+action;
                popup = popupWindow(url,"Org_Office_View_Report");
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
                            <tr><td>
                                    <table>
                                        <tr>
                                            <td align="center" class="header_table"  width="800">Organization Office Type</td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td align="center">
                                    <form name="form0" method="POST" action="orgOfficeTypeCont.do">
                                        <table  align="center"  class="heading1" >
                                            <tr>
                                                <th>Office Type</th>
                                                <td><input class="input new_input" type="text" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                 <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="orgOfficeReport(id);"/>
                                                 <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick="orgOfficeReport(id);"/>
                                                
                                                </td>

                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="orgOfficeTypeCont.do">
                                        <DIV class="content_div">
                                            <table  border="0" id="table1" width="500" align="center"  class="content">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Office Type</th>
                                                    <th class="heading">Description</th>
                                                </tr>
                                                <c:forEach var="orgOfficeType" items="${requestScope['orgOfficeTypeList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                                <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                                    <input type="hidden" id="office_type_id${loopCounter.count}" value="${orgOfficeType.office_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                </td>
                                                                <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgOfficeType.office_type}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgOfficeType.description}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="4">
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
                                                <input  type="hidden" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" >
                                                <input  type="hidden" id="searchOrgOfficeCode" name="searchOrgOfficeCode" value="${searchOrgOfficeCode}" >
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <div >
                                        <form name="form2" method="POST" action="orgOfficeTypeCont.do" onsubmit="return verify()">
                                            <table border="0" id="table2" align="center"  class="content" width="500">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="4" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>


                                                    <th class="heading1">Office Type</th>
                                                    <td>
                                                        <input type="hidden" id="office_type_id" name="office_type_id" value="" >
                                                        <input class="input new_input" type="text" id="office_type" name="office_type" size="30" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>

                                                    <th class="heading1">Description</th><td><input class="input new_input" type="text" id="description" name="description" size="30" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="4">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>

                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" >
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
