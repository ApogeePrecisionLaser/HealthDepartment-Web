<%--
    Document   : item_name
    Created on : Jan 1, 2002, 12:34:10 AM
    Author     : Navitus1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>

<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript" language="javascript">
        jQuery(function(){
            $("#rate_applicable_from").datepicker({

                minDate: -100,
                showOn: "both",
                buttonImage: "images/calender.png",
                dateFormat: 'yy-mm-dd',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
            $("#rate_applicable_to").datepicker({

                minDate: -100,
                showOn: "both",
                buttonImage: "images/calender.png",
                dateFormat: 'yy-mm-dd',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
            $("#searchitem_name").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getItemName" }
                }
            });
            $("#item_name").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getItemName" }
                }
            });
            $("#item_name").result(function(event, data, formatted)
            {
                var id = this.id;
                var item_name = $("#item_name").val();
                $.ajax({url: "ItemController?action1=getSelect&item_name="+item_name, data: "action2="+ data +"&q=", success: function(response_data) {
                        var propertiess = response_data.trim().split("&#");
                        var length1=propertiess.length;
                        length1=length1-1;

                        var i=0;
                        for(i;i<length1;i++)
                        {

                            var properties=propertiess[i].trim().split("-")[1];
                            var id=propertiess[i].trim().split("-")[0];

                            if(i==0){
                                document.getElementById("properties1_id").value=id;
                                //alert(document.getElementById("properties1_id").value)
                                $("#properties_1_1").show();
                                $("#properties_1").show();
                                $("#tr1").show();
                                $("#td1").show();
                                document.getElementById("properties_1_1").innerHTML=properties;

                            }
                            if(i==1){
                                document.getElementById("properties2_id").value=id;
                                $("#properties_2_2").show();
                                $("#properties_2").show();
                                $("#tr1").show();
                                $("#td2").show();
                                document.getElementById("properties_2_2").innerHTML=properties;
                            }
                            if(i==2){
                                document.getElementById("properties3_id").value=id;

                                $("#properties_3_3").show();
                                $("#properties_3").show();
                                $("#tr2").show();
                                $("#td3").show();
                                document.getElementById("properties_3_3").innerHTML=properties;
                            }
                            if(i==3){
                                document.getElementById("properties4_id").value=id;
                                $("#properties_4_4").show();
                                $("#properties_4").show();
                                $("#tr2").show();
                                $("#td4").show();
                                document.getElementById("properties_4_4").innerHTML=properties;
                            }
                            if(i==4){
                                document.getElementById("properties5_id").value=id;
                                $("#properties_5_5").show();
                                $("#properties_5").show();
                                $("#tr3").show();
                                $("#td5").show();
                                document.getElementById("properties_5_5").innerHTML=properties;
                            }
                        }
                    }
                });
            });

            $("#properties_1").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getProperties" },
                    action2: function() {  return  $("#properties1_id").val() }
                }
            });
            $("#properties_2").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getProperties" },
                    action2: function() { return $("#properties2_id").val() }
                }
            });
            $("#properties_3").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getProperties" },
                    action2: function() { return $("#properties3_id").val() }
                }
            });
            $("#properties_4").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getProperties" },
                    action2: function() { return $("#properties4_id").val() }
                }
            });
            $("#properties_5").autocomplete("ItemController", {
                extraParams: {
                    action1: function() { return "getProperties" },
                    action2: function() { return $("#properties5_id").val() }
                }
            });
        });
        function fillColumns(id)
        {

            var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
            var noOfColumns = 18;
            var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1  ). --%>
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

            document.getElementById("item_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
            document.getElementById("item_name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
            document.getElementById("parentHeaderName").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
            //document.getElementById("item_serial_no").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
            //document.getElementById("item_rate").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
            //document.getElementById("rate_applicable_from").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
            //document.getElementById("rate_applicable_to").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
            //document.getElementById("item_unit").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
            document.getElementById("properties1_id").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
            document.getElementById("properties2_id").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
            document.getElementById("properties3_id").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
            document.getElementById("properties4_id").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
            document.getElementById("properties5_id").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
            document.getElementById("properties_1").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
            document.getElementById("properties_2").value=document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
            document.getElementById("properties_3").value=document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
            document.getElementById("properties_4").value=document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
            document.getElementById("properties_5").value=document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
            document.getElementById("properties_1_1").innerHTML=document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
            var value=document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
            if(value.length !=0 )
            {
                $("#properties_1_1").show();
                $("#properties_1").show();
                $("#tr1").show();
                $("#td1").show();
            }
            else
            {
                $("#properties_1_1").hide();
                $("#properties_1").hide();
                $("#tr1").hide();
                $("#td1").hide();
            }
            document.getElementById("properties_2_2").innerHTML=document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
            value=document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
            if(value.length !=0 )
            {

                $("#properties_2_2").show();
                $("#properties_2").show();
                $("#tr1").show();
                $("#td2").show();
            }
            else
            {
                $("#properties_2_2").hide();
                $("#properties_2").hide();
                $("#td2").hide();
            }
            document.getElementById("properties_3_3").innerHTML=document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
            value=document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
            if(value.length !=0 )
            {

                $("#properties_3_3").show();
                $("#properties_3").show();
                $("#tr2").show();
                $("#td3").show();
            }
            else
            {
                $("#properties_3_3").hide();
                $("#properties_3").hide();

                $("#td3").hide();
            }
            document.getElementById("properties_4_4").innerHTML=document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
            value=document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
            if(value.length !=0 )
            {
                $("#properties_4_4").show();
                $("#properties_4").show();
                $("#tr2").show();
                $("#td4").show();
            }
            else
            {
                $("#properties_4_4").hide();
                $("#properties_4").hide();

                $("#td4").hide();
            }
            document.getElementById("properties_5_5").innerHTML=document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
            value=document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
            if(value.length !=0 )
            {

                $("#properties_5_5").show();
                $("#properties_5").show();
                $("#tr3").show();
                $("#td5").show();
            }
            else
            {
                $("#properties_5_5").hide();
                $("#properties_5").hide();
                $("#td5").hide();
            }


            for(var i = 0; i < noOfColumns; i++)
            {
                document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
            }

            $("#message").html("");
            makeEditable('');
        }
        function setDefaultColor(noOfRowsTraversed, noOfColumns) {
            for(var i = 0; i < noOfRowsTraversed; i++) {
                for(var j = 1; j <= noOfColumns; j++) {
                    document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                }
            }

        }
        function makeEditable(id)
        {
            document.getElementById("getParentHeader").disabled = false;
            document.getElementById("parentHeaderName").disabled = false;
            document.getElementById("item_name").disabled = false;
            //       document.getElementById("item_serial_no").disabled = false;
            //      document.getElementById("item_rate").disabled = false;
            //      document.getElementById("rate_applicable_from").disabled = false;
            //      document.getElementById("rate_applicable_to").disabled = false;
            //      document.getElementById("item_unit").disabled = false;

            document.getElementById("save").disabled = true;
            document.getElementById("update").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = false;
            if(id == 'new')
            {
                $("#properties_1_1").hide();
                $("#properties_1").hide();
                $("#properties_2_2").hide();
                $("#properties_2").hide();
                $("#properties_3_3").hide();
                $("#properties_3").hide();
                $("#properties_4_4").hide();
                $("#properties_4").hide();
                $("#properties_5_5").hide();
                $("#properties_5").hide();
                $("#tr1").hide();
                $("#tr2").hide();
                $("#tr3").hide();
                $("#message").html("");
                document.getElementById("update").disabled = true;
                document.getElementById("delete").disabled = true;
                document.getElementById("save_As").disabled = true;
                document.getElementById("save").disabled = false;
                document.getElementById("item_name").disabled = false;
                //        document.getElementById("item_serial_no").disabled = false;
                //      document.getElementById("item_rate").disabled = false;
                //      document.getElementById("rate_applicable_from").disabled = false;
                //      document.getElementById("rate_applicable_to").disabled = false;
                //      document.getElementById("item_unit").disabled = false;
                document.getElementById("item_name").focus();
            }
        }
        function setStatus(id) {
            if(id == 'save'){

                document.getElementById("clickedButton").value = "Save";
            }else if(id == 'update'){
                document.getElementById("clickedButton").value = "Update";
            } else if(id == 'save_As'){
                document.getElementById("clickedButton").value = "Save AS New";
            }

            else document.getElementById("clickedButton").value = "Delete";
        }

        function verify()
        {
            var result;
            var value=document.getElementById("clickedButton").value;
            alert(value);
            if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                if((document.getElementById("item_name").value).trim().length == 0) {
                    //document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Key Person Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Item Name is required...</b></td>");
                    document.getElementById("item_name").focus();
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

        function getNode(name){
            var parentHeaderName = $("#parentHeaderName").val();
            var url = "GetParentHeaderController2?task="+name+"&parentHeaderName="+parentHeaderName;
            popupwin = openPopUp(url, "Header List", 600, 725);
        }

        function openPopUp(url, window_name, popup_height, popup_width) {
            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
            return window.open(url, window_name, window_features);
            //                window.open('/pageaddress.html','winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400,height=350');
        }


    </script>
    <body>
        <table align="center" class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">

            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <form name="form1" action="item_Controller.do">
                            <table width="100%" align="center" >

                                <tr align="center">
                                    <td>

                                        <span class="heading1">Item Name</span>
                                        <input class="input" type="text" id="searchitem_name" name="searchitem_name" value="${searchitem_name}" size="30">
                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">

                                    </td>
                                </tr>
                            </table>
                        </form>


                        <table>
                            <tr>
                                <td align="center">
                                    <div  class="content_div" style="width:990px" >
                                        <form name="form1" action="item_Controller.do" >

                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content" >
                                                <TR>
                                                    <th class="heading" >S.no</th>

                                                    <th class="heading">Node Name</th>
                                                    <th class="heading" >item_name</th>
                                                    <!--                                                    <th class="heading" >item_rate</th>
                                                                                                        <th class="heading" >rate_applicable_from</th>
                                                                                                        <th class="heading" >rate_applicable_to</th>
                                                                                                        <th class="heading" >item_unit</th>-->
                                                </TR>

                                                <c:forEach var="item" items="${requestScope['list']}" varStatus="loopCounter">
                                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.item_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${item.node_name}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${item.item_name}</TD>

                                                    </TR>
                                                    <TR>
                                                        <th class="heading" style="background-color: lightblue">S.no</th>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_name_1}"><th class="heading" style="background-color: lightblue" id="p_name_1">${item.properties_name_1}</th></c:when>
                                                            <c:otherwise> </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_name_2}"><th class="heading" style="background-color: lightblue" id="p_name_2">${item.properties_name_2}</th></c:when>
                                                            <c:otherwise> </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_name_3}"><th class="heading" style="background-color: lightblue" id="p_name_3">${item.properties_name_3}</th></c:when>
                                                            <c:otherwise> </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_name_4}"><th class="heading" style="background-color: lightblue" id="p_name_4">${item.properties_name_4}</th></c:when>
                                                            <c:otherwise> </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_name_5}"><th class="heading" style="background-color: lightblue" id="p_name_5">${item.properties_name_5}</th></c:when>
                                                            <c:otherwise> </c:otherwise>
                                                        </c:choose>
                                                        <th style="background-color: lightblue" colspan="16"></th>
                                                    </TR>
                                                    <TR>
                                                        <td style="background-color: lightgray" align="center">
                                                            ${loopCounter.count}
                                                            <%--<input type="hidden" id="${tp.traffic_police_id}" value="${loopCounter.count}">--%>
                                                        </td>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.properties_1}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.properties_2}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)">${item.properties_3}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.properties_4}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)">${item.properties_5}</TD>

                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_value_1}"><TD id="t1c${IDGenerator.uniqueID}" style="background-color: lightgray;"  >${item.properties_value_1}</TD></c:when>
                                                            <c:otherwise> <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  >${item.properties_value_1}</TD></c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_value_2}"><TD id="t1c${IDGenerator.uniqueID}"  style="background-color: lightgray;" >${item.properties_value_2}</TD></c:when>
                                                            <c:otherwise> <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  >${item.properties_value_2}</TD></c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_value_3}"><TD id="t1c${IDGenerator.uniqueID}" style="background-color: lightgray; " colspan="" >${item.properties_value_3}</TD></c:when>
                                                            <c:otherwise> <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  >${item.properties_value_3}</TD></c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_value_4}"><TD id="t1c${IDGenerator.uniqueID}"  style="background-color: lightgray;"  >${item.properties_value_4}</TD></c:when>
                                                            <c:otherwise> <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  >${item.properties_value_4}</TD></c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${not empty item.properties_value_5}"><TD id="t1c${IDGenerator.uniqueID}" style="background-color: lightgray;"  >${item.properties_value_5}</TD></c:when>
                                                            <c:otherwise> <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  >${item.properties_value_5}</TD></c:otherwise>
                                                        </c:choose>
                                                        <TD colspan="16" style="background-color: lightgray;"></TD>

                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)">${item.properties_name_1}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)">${item.properties_name_2}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">${item.properties_name_3}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.properties_name_4}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)">${item.properties_name_5}</TD>



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
                                            <input type="hidden"  id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchitem_name" name="searchitem_name" value="${searchitem_name}" >

                                        </form>
                                    </div>

                                    <div  style="width:990px" >
                                        <form name="form2" action="item_Controller.do" onsubmit="return verify()">
                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr id="parentHeaderRow">
                                                    <th class="heading1" id="parentHeaderName_th">Parent Header Name</th><td>
                                                        <input class="input" type="text" id="parentHeaderName" name="parentHeaderName" size="25"  value="" onkeyup="makeEditable1();" onblur="makeEditable1();" disabled>
                                                        <input type="button" id="getParentHeader" name="getParentHeader" value="Get Parent Header" onclick="getNode('parent');" onblur="makeEditable1();" disabled>
                                                        <input type="hidden" id="node_id" name="node_id" value=""></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Item Name</th>
                                                    <td><input class="input" type="hidden" id="item_id" name="item_id"  value="" >
                                                        <input type="text" id="item_name" name="item_name" value="" disabled></td>
                                                </tr>
                                            
                                                <tr style="display: none" id="tr1">
                                                    <th class="heading1" id="properties_1_1" style="display: none" value=""></th>
                                                    <td style="display: none" id="td1">
                                                        <input  type="hidden" name="properties1_id" size="5" id="properties1_id"  value=""/>
                                                        <input class="input" type="text" style="display: none" name="properties_1" size="15" id="properties_1"  value=""/>
                                                    </td>
                                                    <th class="heading1" id="properties_2_2" style="display: none"> </th>
                                                    <td style="display: none" id="td2">
                                                        <input class="input" type="text"  style="display: none" name="properties_2" size="15" id="properties_2"  value=""/>
                                                        <input class="input" type="hidden" name="properties2_id" size="5" id="properties2_id"  value=""/>
                                                    </td>
                                                </tr>
                                                <tr style="display: none" id="tr2">
                                                    <th class="heading1" id="properties_3_3" style="display: none"></th>
                                                    <td style="display: none" id="td3">
                                                        <input class="input" type="hidden"  name="properties3_id" size="5" id="properties3_id"  value=""/>
                                                        <input class="input" type="text" style="display: none" id="properties_3" name="properties_3" value="" size="15" >
                                                    </td>
                                                    <th class="heading1" id="properties_4_4" style="display: none"></th>
                                                    <td style="display: none" id="td4">
                                                        <input class="input" type="hidden" name="properties4_id" size="5" id="properties4_id"  value=""/>
                                                        <input class="input" type="text" style="display: none" id="properties_4" name="properties_4" value="" size="15" >
                                                    </td>
                                                </tr>
                                                <tr style="display: none" id="tr3">
                                                    <th class="heading1" id="properties_5_5" style="display: none"></th>
                                                    <td style="display: none" id="td5">
                                                        <input class="input" type="hidden" name="properties5_id" size="5" id="properties5_id"  value=""/>
                                                        <input class="input" type="text"  style="display: none" name="properties_5" size="10" id="properties_5" value="" size="15">
                                                    </td>
                                                </tr>

                                                <%--<tr>
                                                    <td>properties_1</td>
                                                    <td><input type="hidden" id="revision_no" name="revision_no" value="">
                                                     <input type="text" id="properties_1" name="properties_1" value="" disabled></td>

                                                                                     <td>properties_2</td>
                                                                                     <td><input type="text" id="properties_2" name="properties_2" value="" disabled></td>
                                                                               </tr>
                                                                               <tr>
                                                                                     <td>properties_3</td>
                                                                                     <td><input type="text" id="properties_3" name="properties_3" value="" disabled></td>

                                                                                     <td>properties_4</td>
                                                                                     <td><input type="text" id="properties_4" name="properties_4" value="" disabled></td>
                                                                               </tr>
                                                                               <tr>
                                                                                     <td>properties_5</td>
                                                                                     <td><input type="text" id="properties_5" name="properties_5" value="" disabled></td>
                                                                               </tr>--%>

                                                <tr>
                                                    <td colspan="4">
                                                        <input class="button"  type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                        <input class="button"  type="submit" name="task" id="save" value="Save" onclick="setStatus(id)"  disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)"  disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input type="hidden" id="clickedButton" name="clickedButton" value="">
                                                        <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                                                        <input  type="hidden" id="searchitem_name" name="searchitem_name" value="${searchitem_name}" >
                                                        <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">

                                                    </td>
                                                </tr>
                                            </TABLE>
                                        </form>
                                    </div>



                                </td>
                            </tr>
                        </table>

                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
