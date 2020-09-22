<%--
    Document   : dataEntry_organisation
    Created on : Dec 14, 2011, 5:07:16 PM
    Author     : Soft_Tech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Organization Office</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <style type="text/css">
                .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    /*background-color: white;*/
                }
            </style>
        <script type="text/javascript" language="javascript">
            var popupwin;
            jQuery(function(){

                $("#office_code").result(function(event, data, formatted){

                    document.getElementById("office_type").value = "";
                    $("#office_type").flushCache();
                });

                $("#organisation_name").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });
                $("#org_name").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });
                
                $("#office_code_search").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgOfficeCodeSearch"}

                    }
                });
                $("#office_name_search").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgOfficeNameSearch"},
                        action2:function(){return document.getElementById("office_code_search").value }

                    }
                });
                $("#office_type").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrgOfficeType"}
                        
                    }
                });
                $("#state_name").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getStateName"}
                    }
                });
                $("#city_name").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getCityName"}
                       // action2: function() { return document.getElementById("state_name").value;}

                    }
                });

                var cssFunction = function(){
                        $(".ac_results li").css({
                            'margin': '0px',
                            'padding': '2px 5px',
                            'cursor' : 'default',
                            'display' : 'block',
                            'color' : '#972800',
                            'font-family' :'Sans-Serif',//, 'kruti Dev 010',
                            /*font-family:Arial, Helvetica, sans-serif;
                            /*
	                    if width will be 100% horizontal scrollbar will apear
	                    when scroll mode will be used
	                    */
                            /*width: 100%;*/
                            'font-size': '12px',
                            /*
	                    it is very important, if line-height not setted or setted
	                    in relative units scroll will be broken in firefox
	                    */
                           'line-height': '16px',
                           'overflow': 'hidden'
                       });
                    }
                    $("#state_name").click(cssFunction);
                    $("#city_name").click(cssFunction);
            });

            function makeEditable(id) {                
                document.getElementById("organisation_name").disabled = false;
                document.getElementById("org_office_name").disabled = false;
                document.getElementById("office_type").disabled = false;
                document.getElementById("org_office_code").disabled = false;
                document.getElementById("address_line1").disabled = false;
                document.getElementById("address_line2").disabled = false;
                document.getElementById("address_line3").disabled = false;
               // document.getElementById("state_name").disabled = false;
                document.getElementById("city_name").disabled = false;
                document.getElementById("email_id1").disabled = false;
                document.getElementById("email_id2").disabled = false;
                document.getElementById("mobile_no1").disabled = false;
                document.getElementById("mobile_no2").disabled = false;
                document.getElementById("landline_no1").disabled = false;
                document.getElementById("landline_no2").disabled = false;
                document.getElementById("landline_no3").disabled = false;

                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("org_office_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 17);
                    document.getElementById("organisation_name").focus();
                }
                if(id == 'edit'){
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColordOrgn(noOfRowsTraversed, noOfColumns) {
                //alert(noOfColumns);
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {

                //  var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var countrow =document.getElementById('table1').getElementsByTagName('tr').length;//// for getting the number of rows
                //  alert("no of Rows"+ countrow);
                var noOfRowsTraversed = countrow-2 ;
                //                alert("get total count rows"+ noOfRowsTraversed);
                var noOfColumns = 17;
                var columnId = id;
                //                alert("Get  Column id of the Row "+ columnId);<%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                //                alert("After getting the column  " +columnId );
                var lowerLimit, higherLimit, rowNo = 0;
                var noOfRows;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    noOfRows = i;
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    //                    alert("lower limit of row " +lowerLimit);
                    higherLimit = (i + 1) * noOfColumns;
                    //                    alert("higher limit of row "+higherLimit)// e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }
                //                alert("noOfRows: " + ++noOfRows);

                var lower= lowerLimit;
                var upper= higherLimit;
                setDefaultColordOrgn(noOfRowsTraversed, noOfColumns);// set default color of rows (i.e. of multiple coloumns).
                //                alert("Total number of column in the set the valeu   "+  noOfColumns);
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                //var t2id = "t2c";
               
                document.getElementById("org_office_id").value= document.getElementById("org_office_id" + rowNo).value;
                document.getElementById("org_office_code").value = document.getElementById(t1id +(lower+1)).innerHTML;
                document.getElementById("organisation_name").value = document.getElementById(t1id +(lower+2)).innerHTML;
                document.getElementById("org_office_name").value = document.getElementById(t1id +(lower+3)).innerHTML;
                document.getElementById("office_type").value = document.getElementById(t1id +(lower+4)).innerHTML;
                document.getElementById("address_line1").value = document.getElementById(t1id +(lower+5)).innerHTML;
                document.getElementById("address_line2").value = document.getElementById(t1id +(lower+6)).innerHTML;
                document.getElementById("address_line3").value = document.getElementById(t1id +(lower+7)).innerHTML;
                document.getElementById("city_name").value = document.getElementById(t1id +(lower+8)).innerHTML;
              //  document.getElementById("state_name").value = document.getElementById(t1id +(lower+9)).innerHTML;
                document.getElementById("email_id1").value = document.getElementById(t1id +(lower+9)).innerHTML;
                document.getElementById("email_id2").value = document.getElementById(t1id +(lower+10)).innerHTML;
                document.getElementById("mobile_no1").value = document.getElementById(t1id +(lower+11)).innerHTML;
                document.getElementById("mobile_no2").value = document.getElementById(t1id +(lower+12)).innerHTML;
                document.getElementById("landline_no1").value = document.getElementById(t1id +(lower+13)).innerHTML;
                document.getElementById("landline_no2").value = document.getElementById(t1id +(lower+14)).innerHTML;
                document.getElementById("landline_no3").value = document.getElementById(t1id +(lower+15)).innerHTML;

                for(var i = 0; i <= 16; i++) {

                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and send button enabled too.
                // document.getElementById("send").disabled = false;
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
                else if(id == 'search_org'){
                    var org_name=document.getElementById("org_name").value;
                    document.getElementById("org_name1").value =  org_name;
                    document.getElementById("org_name2").value =  org_name;
                    document.getElementById("clickedButton").value = "SEARCH";
                }
                else if(id == 'clear_org'){
                    document.getElementById("clickedButton").value = ' ';
                    // document.getElementById("").innerHTML = "   ";
                    $("#org_msg").html("");
                    document.getElementById("org_name").value =" ";
                    document.getElementById("office_code_search").value =" ";
                    document.getElementById("office_name_search").value =" ";
                    document.getElementById("org_name1").value =  " ";
                    document.getElementById("org_name2").value =  " ";
                }
                else document.getElementById("clickedButton").value = "Delete";
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
                    var org_office_name = document.getElementById("org_office_name").value;
                    var office_type = document.getElementById("office_type").value;
                    var office_code = document.getElementById("org_office_code").value;
                    var address_line1 = document.getElementById("address_line1").value;
                   // var state_name = document.getElementById("state_name").value;
                    var city_name = document.getElementById("city_name").value;
                    var email_id1 = document.getElementById("email_id1").value;
                    var email_id2 = document.getElementById("email_id2").value;
                    var landline_no1 = document.getElementById("landline_no1").value;

                    if(myLeftTrim(organisation_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        document.getElementById("organisation_name").focus();
                        return false;
                    }
                    if(myLeftTrim(office_code).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                        document.getElementById("office_code").focus();
                        return false;
                    }
                    if(myLeftTrim(org_office_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                        document.getElementById("org_office_name").focus();
                        return false;
                    }
                    if(myLeftTrim(office_type).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>");
                        document.getElementById("office_type").focus();
                        return false;
                    }
                   // if(myLeftTrim(state_name).length == 0) {
                        //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>";
                       // $("#message").html("<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>");
                      //  document.getElementById("state_name").focus();
                     //   return false;// code to stop from submitting the form2.
                  //  }
                    if(myLeftTrim(city_name).length == 0) {
                        //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                        document.getElementById("city_name").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(myLeftTrim(address_line1).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>");
                        document.getElementById("address_line1").focus();
                        return false;
                    }
                    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

                    if(reg.test(email_id1) == false) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>");
                        document.getElementById("email_id1").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(myLeftTrim(email_id2).length > 0) {
                        if(reg.test(email_id2) == false) {
                            //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>";
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>");
                            document.getElementById("email_id2").focus();
                            return false;// code to stop from submitting the form2.
                        }
                    }
                    if(myLeftTrim(landline_no1).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>");
                        document.getElementById("landline_no1").focus();
                        return false;// code to stop from submitting the form2.
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


            function verifySearch(){
                var result;
                if(document.getElementById("clickedButton").value == 'SEARCH') {
                    var org_name = document.getElementById("org_name").value;

                    if(myLeftTrim(org_name).length == 0) {
                        //document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                        $("#org_msg").html("<b>Organization Name is required...</b>");
                        document.getElementById("org_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                }
            }

            function displayOrgnList(id){
                var queryString ;
                var organisation= document.getElementById("org_name").value;
                if(id == "viewPdf")
                    queryString = "requester=PRINT&organisation="+organisation;
                else
                    queryString = "requester=PrintExcel&organisation="+organisation;
                var url = "organisationCont.do?"+queryString;
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

            function printAddress(id){
                var queryString = "requester=PRINTAddress&org_office_id="+id;
                var url = "organisationCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }
            function codeIsEmpty(){

                var office_code = document.getElementById("office_code").value;

                if(myLeftTrim(office_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                    document.getElementById("office_code").focus();
                        
                }else{
                $("#message").html("");
                }
            }

            //            $(function() {
            //                var $el = $('#container').jScrollPane({
            //                }),
            //                pane = $el.data('jsp');
            //            });

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
                                            <td  align="center" class="header_table" width="800" >Organization Office</td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr> <td>
                                    <form name="form1" method="POST" action="organisationCont.do" onsubmit="return verifySearch();">
                                        <table  width="100%" class="heading1">

                                            <tr><td align="center">
                                                    Organization Name&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="new_input" id="org_name" name="org_name" value="${org_name}" size="20">
                                                    Office Code&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="office_code_search" name="office_code_search" value="${office_code_search}" size="20">
                                                    Office Name&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="new_input" id="office_name_search" name="office_name_search" value="${office_name_search}" size="20">
                                                    <input type="submit" class="button" id="search_org" name="search_org" value="SEARCH" onclick="setStatus(id)">
                                                    <input type="submit" class="button" id="clear_org" name="clear_org" value="CLEAR" onclick="setStatus(id)">
                                                    <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayOrgnList(id)">
                                                    <input type="button" class="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayOrgnList(id)">
                                                    <label id="org_msg">  </label>
                                                </td></tr>
                                        </table>
                                    </form>
                                </td></tr>
                            <tr><td>
                                    <form name="form1" method="POST" action="organisationCont.do" onsubmit="return verifySearch();">
                                        <DIV class="content_div"  >
                                            <table id="table1"  border="1" align="center" class="content">
                                                <tr>
                                                    <th  class="heading">S.No.</th>
                                                    <th  class="heading" style=" overflow:inherit">Office Type Code</th>
                                                    <th  class="heading">Organization_Name</th>
                                                    <th class="heading">Office_Name</th>
                                                    <th class="heading">Office_Type</th>
                                                    <th class="heading">Address_1</th>
                                                    <th class="heading" style="display: none">Address_2</th>
                                                    <th class="heading" style="display: none">Address_3</th>
                                                    <th class="heading">City Name</th>
                                                    <th class="heading">First Email ID</th>
                                                    <th class="heading" style="display: none">Second Email ID</th>
                                                    <th class="heading">Mobile No1</th>
                                                    <th class="heading" style="display: none">Mobile No2</th>
                                                    <th class="heading">Landline No1</th>
                                                    <th class="heading" style="display: none">Landline No2</th>
                                                    <th class="heading" style="display: none">Landline No3</th>
                                                    <th class="heading">Address</th>
                                                </tr>
                                                <c:forEach var="organisation" items="${requestScope['organisationList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="org_office_id${loopCounter.count}" value="${organisation.org_office_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.org_office_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.org_office_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.office_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.address_line1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" style="display: none">${organisation.address_line2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" style="display: none">${organisation.address_line3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.city_name}</td>
                                                       <%-- <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.state_name}</td>--%>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.email_id1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.email_id2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.mobile_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.mobile_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.landline_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.landline_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.landline_no3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">
                                                            <input type="button" name="print" id="${organisation.org_office_id}" value="Print" onclick="printAddress(id)">
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="17">
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
                                               
                                                <input type="hidden" id="org_name" name="org_name" value="${org_name}">
                                                <input type="hidden" id="office_name_search" name="office_name_search" value="${office_name_search}">
                                                <input type="hidden" id="office_code_search" name="office_code_search" value="${office_code_search}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="organisationCont.do" onsubmit="return verify()">
                                        <DIV>
                                            <table id="table2" border="0" align="center" class="content">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                 <tr>
                                                    <th class="heading1" >Organization Name</th>
                                                    <td>
                                                        <input type="hidden" id="org_office_id" name="org_office_id" value="" >
                                                        <input class="input new_input" type="text" id="organisation_name" name="organisation_name" value="" disabled>
                                                    </td>
                                                     <th class="heading1">Office Type</th>
                                                    <td><input class="input new_input" type="text" id="office_type" name="office_type" value="" onclick ="codeIsEmpty()" disabled></td>
                                                  
                                                    <th class="heading1" nowrap>Office Code</th>
                                                    <td><input class="input" type="text" id="org_office_code" name="org_office_code" value="" disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                     <th class="heading1" nowrap>Office Name</th>
                                                    <td><input class="input new_input" type="text" id="org_office_name" name="org_office_name" value="" disabled></td>

                                                    <th class="heading1" nowrap>Address Line1</th>
                                                    <td><input class="input new_input"  type="text" id="address_line1" name="address_line1" value="" disabled></td>
                                                    <th class="heading1">Address Line2</th>
                                                    <td><input class="input new_input" type="text" id="address_line2" name="address_line2" value="" disabled></td>
                                                   
                                                </tr>
                                                <tr>
                                                     <th class="heading1">Address Line3</th>
                                                    <td><input class="input new_input" type="text" id="address_line3" name="address_line3" value="" disabled></td>
                                                  <!--   <th class="heading1">State Name</th>
                                                    <td><input class="input new_input" style="display: none" type="text" id="state_name" name="state_name" value="" disabled></td> -->

                                                    <th class="heading1">City Name</th>
                                                    <td><input class="input new_input" type="text" id="city_name" name="city_name" value="" disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading1">First EmailID</th>
                                                    <td><input class="input" type="text" id="email_id1" name="email_id1" value="" disabled></td>
                                                    <th class="heading1">Second EmailID</th>
                                                    <td><input class="input" type="text" id="email_id2" name="email_id2" value="" disabled></td>
                                                    <th class="heading1">Mobile No1</th>
                                                    <td><input class="input" type="text" id="mobile_no1" name="mobile_no1" value=""  maxlength="10" disabled></td>

                                                          </tr>
                                                          <tr>
                                                              <th class="heading1">Mobile No2</th>
                                                    <td><input class="input" type="text" id="mobile_no2" name="mobile_no2" value="" maxlength="10" disabled></td>
                                                    <th class="heading1">Landline No1</th>
                                                    <td><input class="input" type="text" id="landline_no1" name="landline_no1" value="" disabled></td>
                                                    <th class="heading1">Landline No2</th>
                                                    <td><input class="input" type="text" id="landline_no2" name="landline_no2" value="" disabled></td>
                                                          </tr>
                                                          <tr>
                                                               <th class="heading1" nowrap>Landline No3</th>
                                                    <td><input class="input" type="text" id="landline_no3" name="landline_no3"  value="" disabled></td>
                                          
                                                          </tr>
                                                <tr>

                                                    <td align='center' colspan="8">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled> 
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> 
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> 
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> 
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="org_name" name="org_name" value="${org_name}">                                       
                                                <input type="hidden" id="clickedButton" value="">
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

