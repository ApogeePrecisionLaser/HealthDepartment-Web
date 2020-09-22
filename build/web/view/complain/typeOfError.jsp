<%-- 
    Document   : typeOfError
    Created on : Dec 18, 2017, 11:11:34 AM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" language="javascript">

            jQuery(function(){
       
                $("#priority").autocomplete("TypeOfErrorController", {
                    extraParams: {
                        action1: function() { return "getPriority"}
                    }
                });
                $("#designation").autocomplete("TypeOfErrorController", {
                    extraParams: {
                        action1: function() { return "getDesignation"}
                    }
                });

                $("#search_type_of_error").autocomplete("TypeOfErrorController", {
                    extraParams: {

                        action1: function() { return "getTypeOfErrorName"}
                    }
                });
                $("#search_designation").autocomplete("TypeOfErrorController", {
                    extraParams: {
                        action1: function() { return "getDesignation"}
                    }
                });
            });


           function fillColumns(id)
            {
                alert(id);
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 5;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1       ). --%>
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

                document.getElementById("type_of_error_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("type_of_error").value=document.getElementById(t1id +(lowerLimit+2)).innerHTML;
//                document.getElementById("error_time").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
                document.getElementById("priority").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
//                document.getElementById("designation").value=document.getElementById(t1id +(lowerLimit+5)).innerHTML;
                document.getElementById("remark").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;

//                var date=document.getElementById(t1id +(lowerLimit+10)).innerHTML;
//                var date_time=date.split(" ")[0];
//                var date_time1=date_time.split("-")[2]+"-"+date_time.split("-")[1]+"-"+date_time.split("-")[0];
//                document.getElementById("date_time").value=date_time1;
//                document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
//
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }

                document.getElementById("edit").disabled = false;

                if(!document.getElementById("save").disabled)
                {
                   // document.getElementById("save_as_new").disabled = true;
                    document.getElementById("delete").disabled = false;
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



            function makeEditable(id) {

                document.getElementById("priority").disabled = false;
//                document.getElementById("designation").disabled = false;
                document.getElementById("type_of_error").disabled=false;
//                document.getElementById("error_time").disabled=false;
                document.getElementById("remark").disabled=false;
                if(id == 'new') {

                    document.getElementById("priority").disabled=false;
//                    document.getElementById("designation").disabled=false;
                    document.getElementById("type_of_error").disabled=false;
//                    document.getElementById("error_time").disabled=false;
                    document.getElementById("remark").disabled=false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("update").disabled = true;
                }
                if(id == 'edit') {
                    document.getElementById("update").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }

           function getTypeOfErrorList(){
                var searchTypeOfError = $("#searchTypeOfError").val();
                var searchDesignation = $("#searchDesignation").val();
               // var searchemail = $("#searchemail").val();

                var queryString = "task=generateHSReport&searchTypeOfError="+searchTypeOfError+"&searchDesignation="+searchDesignation;
                var url = "TypeOfErrorController?" + queryString;
                popupwin = openPopUp(url, "NameStatus List", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
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
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>Type Of Error</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="TypeOfErrorController" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="11" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Type Of Error</th>
<!--                                                <td><input class="input" type="text" id="search_type_of_error" name="search_type_of_error" value="${type_of_error}" size="30" ></td>-->
                                          <td><input type="text" id="search_type_of_error" name="search_type_of_error" value="${type_of_error}" size="30" ></td>
<!--                                                <th>Designation</th>
                                                <td><input class="input" type="text" id="search_designation" name="search_designation" value="${designation}" size="30" ></td>-->


                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="getTypeOfErrorList()"></td>

                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>



                        <tr>
                            <td>
                        <tr align="center">
                            <td >
                                <form name="form1" method="post" action="TypeOfErrorController">
                                    <div class="content_div">
                                        <TABLE BORDER="1" align="center" cellpadding="5%" width="40%" height="100%" class="content">


                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <th class="heading">S.no&nbsp;</th>
                                            <TH class="heading">Error Name&nbsp;</TH>
<!--                                            <th class="heading">Error Time&nbsp;</th>-->
                                            <th class="heading">Priority&nbsp;</th>
<!--                                            <th class="heading">Designation&nbsp;</th>-->
                                            <th class="heading">Remark&nbsp;</th>
                                            <c:forEach var="item" items="${requestScope['List']}" varStatus="loopCounter">
                                                <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                <tr>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.errorType_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.error_name}</td>
<!--                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.value}</td>-->
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.priority_name}</td>
<!--                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.designation}</td>-->
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.remark}</td>
                                                </tr>
                                                </TR>
                                            </c:forEach>
                                            <tr> <td align='center' colspan="6">
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
                                    </div>
                                    <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                    <input type="hidden"  id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                </form>
                            </td>
                        </tr>
                        <tr align="center">
                            <td>
                                <form name="form2" method="post" action="TypeOfErrorController" >
                                    <table class="content" border="0" id="table2" align="center" width="20%">
                                        <tr id="message">
                                            <c:if test="${not empty message}">
                                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                            </c:if>
                                        </tr>
                                        <tr>
                                            <th class="heading1">Type of Error</th><td>
                                                <input class="input new_input" type="text" id="type_of_error" name="type_of_error"  value="" disabled>
                                                <input class="input new_input" type="hidden" id="type_of_error_id" name="type_of_error_id" value="" ></td>

<!--                                            <th class="heading1">Error Time</th>
                                            <td><input class="input new_input" type="text" id="error_time" name="error_time"  value="" disabled></td>-->
                                        </tr>

                                        <tr>
                                            <th class="heading1">Priority</th>
                                            <td> <input class="input new_input" type="text" id="priority" name="priority"  value=""   disabled >
                                            </td>

<!--                                            <th class="heading1">Designation</th>
                                            <td><input class="input new_input" type="text" id="designation" name="designation"  value="" disabled></td>-->
                                        </tr>

                                        <tr>
                                            <th class="heading1">Remark</th>
                                            <td><input class="input new_input" type="text" id="remark" name="remark"  value="" disabled></td>
                                        </tr>
                                        <tr>
                                            <td align='center' colspan="4" >
                                                <input type="button" class="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                <input type="submit" class="button" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                <input type="submit" class="button" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                <input type="submit" class="button" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                <input type="reset" class="button" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                            </td>
                                        </tr>

                                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        <input type="hidden" id="clickedButton" value="">

                                    </table>
                                </form>
                            </td>
                        </tr>
                    </table>
                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>



    </body>
</html>
