<%-- 
    Document   : keyperson
    Created on : Dec 15, 2011, 4:23:44 PM
    Author     : SoftTech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry module: Key Person Table</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <style>
            .new_input
            {

                font-size: 14px;
                font-family:"kruti Dev 010", Sans-Serif;
                font-weight: 500;
/*                background-color: white;*/

            }
            .new_input:focus
            {
                background-color: lightsteelblue;
            }
/*            .input
            {

                font-size: 12px;
                font-family:"Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
                font-weight: 500;
                background-color: white;

            }

            .input:focus
            {
                background-color: lightsteelblue;
            }*/
        </style>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#office_code").result(function(event, data, formatted){

                    document.getElementById("org_office_name").value = "";
                    $("#org_office_name").flushCache();
                });
                $("#office_code").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getOrgOfficeCode"}
                        //action2: function() { return document.getElementById("office_type").value;}

                    }
                });
                $("#state_name").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getStateName"}
                    }
                });
                $("#searchOfficeCode").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getsearchOrganisation"}
                    }
                });
                $("#city_name").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getCityName"}
                        //action2: function() { return document.getElementById("state_name").value;}

                    }
                });
                $("#office_type").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getOfficeType"}
                    }
                });
                $("#searchEmpCode").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getSearchEmpCode"}
                        //action2: function() { return document.getElementById("searchOfficeCode").value;}
                    }
                });
                $("#searchKeyPerson").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getSearchKeyPerson"}
                        // action2: function() { return document.getElementById("searchEmpCode").value;}
                    }
                });
                $("#org_office_name").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getOrgOfficeName"},
                        action2: function() { return document.getElementById("office_code").value; }
                    }
                });
                $("#designation").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getDesignation"}
                    }
                });
                $("#searchDesignation").autocomplete("personCount.do", {
                    extraParams: {
                        action1: function() { return "getSearchDesignation"}
                    }
                });
                $("#office_code").click(function(){
                    $("#org_office_name").val('');
                    $("#org_office_name").flushCache();
                    $("#designation").val('');
                    $("#designation").flushCache();
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
                document.getElementById("office_code").disabled = false;
                document.getElementById("org_office_name").disabled = false;
                document.getElementById("salutation").disabled = false;
                document.getElementById("employeeId").disabled = false;
                document.getElementById("key_person_name").disabled = false;
                document.getElementById("designation").disabled = false;
                document.getElementById("city_name").disabled = false;
               // document.getElementById("state_name").disabled = false;
                document.getElementById("address_line1").disabled = false;
                document.getElementById("address_line2").disabled = false;
                document.getElementById("address_line3").disabled = false;
                document.getElementById("mobile_no1").disabled = false;
                document.getElementById("mobile_no2").disabled = false;
                document.getElementById("landline_no1").disabled = false;
                document.getElementById("landline_no2").disabled = false;
                document.getElementById("email_id1").disabled = false;
                document.getElementById("email_id2").disabled = false;
                document.getElementById("design_name").disabled=false;
                document.getElementById("father_name").disabled=false;
                document.getElementById("age").disabled=false;
                document.getElementById("id_proof").disabled=false;
                if(id == 'new') {
                    $("#message").html("");
                    document.getElementById("key_person_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 23);
                    //document.getElementById("office_type").focus();
                }
                if(id == 'edit'){
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = false;
                    // document.getElementById("office_type").focus();
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

            function fillColumnKeyPerson(id) {

                //  var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                //                alert("get total count rows"+ noOfRowsTraversed);
                var noOfColumns = 23;
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
                // alert(lower);
                var upper= higherLimit;
                setDefaultColordOrgn(noOfRowsTraversed, noOfColumns);// set default color of rows (i.e. of multiple coloumns).
                //                alert("Total number of column in the set the valeu   "+  noOfColumns);
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                //var t2id = "t2c";
                document.getElementById("key_person_id").value= document.getElementById("key_person_id"+rowNo).value;
                // document.getElementById("office_type").value = document.getElementById(t1id +(lower+1)).innerHTML
                document.getElementById("office_code").value = document.getElementById(t1id +(lower+2)).innerHTML
                //  document.getElementById("office_type").value = document.getElementById(t1id +(lower+3)).innerHTML
                document.getElementById("org_office_name").value = document.getElementById(t1id +(lower+4)).innerHTML
                document.getElementById("employeeId").value = $.trim(document.getElementById(t1id +(lower+5)).innerHTML);
                /*var role=  $.trim(document.getElementById(t1id +(lower+5)).innerHTML);
                $("#salutation option" ).each(function()
                {
                    if($(this).val() == role){
                        $(this).attr('selected', true);
                    }
                });*/
                document.getElementById("salutation").value = $.trim(document.getElementById(t1id +(lower+6)).innerHTML);
                document.getElementById("key_person_name").value = document.getElementById(t1id +(lower+7)).innerHTML;
                document.getElementById("father_name").value = document.getElementById(t1id +(lower+8)).innerHTML;
                document.getElementById("age").value = document.getElementById(t1id +(lower+9)).innerHTML;
                document.getElementById("designation").value = document.getElementById(t1id +(lower+10)).innerHTML;
                document.getElementById("address_line1").value = document.getElementById(t1id +(lower+11)).innerHTML;
                document.getElementById("address_line2").value = document.getElementById(t1id +(lower+12)).innerHTML;
                document.getElementById("address_line3").value = document.getElementById(t1id +(lower+13)).innerHTML;
                document.getElementById("city_name").value = document.getElementById(t1id +(lower+14)).innerHTML;
               // document.getElementById("state_name").value = document.getElementById(t1id +(lower+15)).innerHTML;
                document.getElementById("mobile_no1").value = document.getElementById(t1id +(lower+15)).innerHTML;
                document.getElementById("mobile_no2").value = document.getElementById(t1id +(lower+16)).innerHTML;
                document.getElementById("landline_no1").value = document.getElementById(t1id +(lower+17)).innerHTML;
                document.getElementById("landline_no2").value = document.getElementById(t1id +(lower+18)).innerHTML;
                document.getElementById("email_id1").value = document.getElementById(t1id +(lower+19)).innerHTML;
                document.getElementById("email_id2").value = document.getElementById(t1id +(lower+20)).innerHTML;
                document.getElementById("general_image_details_id").value = document.getElementById(t1id +(lower+21)).innerHTML;
               
                for(var i = 0; i <= 20; i++) {

                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and send button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
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
                    var city_name = document.getElementById("city_name").value;
                   // var state_name = document.getElementById("state_name").value;
                                    
                    if(myLeftTrim(document.getElementById("office_code").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                        document.getElementById("office_code").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("org_office_name").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                        document.getElementById("org_office_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("employeeId").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Person Id is required...</b></td>");
                        document.getElementById("employeeId").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("salutation").value)=='---Select---') {
                       $("#message").html("<td colspan='8' bgcolor='coral'><b>Salutation is required...</b></td>");
                        document.getElementById("salutation").focus();
                       return false; // code to stop from submitting the form2.
                    }
                    
                    if(myLeftTrim(document.getElementById("key_person_name").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Key Person Name is required...</b></td>");
                        document.getElementById("key_person_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("designation").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Designation Name is required...</b></td>");
                        document.getElementById("designation").focus();
                        return false; // code to stop from submitting the form2.
                    }                    
                   // if(myLeftTrim(state_name).length == 0) {
                     //   $("#message").html("<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>");
                        // alert("State Name is required");
                       // document.getElementById("state_name").focus();
                      //  return false; // code to stop from submitting the form2.
                  //  }
                    if(myLeftTrim(city_name).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                        document.getElementById("city_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("address_line1").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Address_line1 is required...</b></td>");
                        document.getElementById("address_line1").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("mobile_no1").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Mobile_no1 is required...</b></td>");
                        document.getElementById("mobile_no1").focus();
                        return false; // code to stop from submitting the form2.2.
                    }if(myLeftTrim(document.getElementById("email_id1").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Email_id1 is required...</b></td>");
                        document.getElementById("email_id1").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    /*if(myLeftTrim(document.getElementById("design_name").value).length == 0) {
                        // alert(document.getElementById("design_name").value);
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Photograph is mandatory...</b></td>");
                        document.getElementById("design_name").focus();
                        return false; // code to stop from submitting the form2.
                    }*/
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

            
            function displayOrgnList(id){
                var queryString;
                var searchOfficeCode=document.getElementById("searchOfficeCode").value;

              var searchKeyPerson=document.getElementById("searchKeyPerson").value;
              var searchEmpCode=document.getElementById("searchEmpCode").value;
              var searchDesignation=document.getElementById("searchDesignation").value;
              // alert(designation);
                if(id == "viewPdf")
                    queryString = "task1=PRINTRecordList&searchOfficeCode="+searchOfficeCode+"&searchDesignation="+searchDesignation+"&searchKeyPerson="+searchKeyPerson+"&searchEmpCode="+searchEmpCode;
                else
                    queryString = "task1=PRINTExcelList&searchOfficeCode="+searchOfficeCode+"&searchDesignation="+searchDesignation+"&searchKeyPerson="+searchKeyPerson+"&searchEmpCode="+searchEmpCode;
                //alert(queryString);
                var url = "personCount.do?" + queryString;
                popupwin = openPopUp(url, "Division List", 600, 900);
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
            function viewDemandNote(id, img){
                //alert(id);
                //var emp_code= document.getElementById("emp_code1"+id).value;
                var queryString = "task1=viewImage&kp_id="+id+"&type="+img;
                // alert(queryString);
                var url = "personCount.do?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900);
            }
            function  codeIsEmpty()
            {
                var office_code = document.getElementById("searchOfficeCode").value;

                if(myLeftTrim(office_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                    document.getElementById("office_code").focus();

                }else{
                    $("#message").html("");
                }
            }
            function  EmpCodeIsEmpty()
            {
                var emp_code = document.getElementById("searchEmpCode").value;

                if(myLeftTrim(emp_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Employee Id is required...</b></td>");
                    document.getElementById("emp_code").focus();

                }else{
                    $("#message").html("");
                }
            }
            function readURL(input) {
                document.getElementById("image_perview").style.visibility='visible';
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#blah')
                        .attr('src', e.target.result)
                        .width(150)
                        .height(200);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            }
            function changeClass(){
                var language=document.getElementById("language").value;
                if(language=='English'){
                    $( "#status_type" ).addClass('input').removeClass('new_input');
                    $( "#remark" ).addClass('input').removeClass('new_input');
                }
                else{
                    $( "#status_type" ).addClass('new_input').removeClass('input');
                    $( "#remark" ).addClass('new_input').removeClass('input');
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
                                    <table>
                                        <tr>
                                            <td align="center" width="800" class="header_table">Organization's Key Person Table </td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr> <td>
                                    <form name="form1" method="POST" action="personCount.do" >
                                        <table width="100%"  class="heading1" >

                                            <tr align="center">
                                                <td >
                                                    Office Code <input class="input" type="text" id="searchOfficeCode" name="searchOfficeCode" value="${searchOfficeCode}" size="20">
                                                    Employee Id <input class="input" type="text" id="searchEmpCode" name="searchEmpCode" value="${searchEmpCode}" size="20">
                                                </td>
                                            </tr>
                                            <tr align="center">
                                                <td>
                                                    Person Name <input class="input new_input" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}"  size="20">
                                                    Designation<input class="input new_input" type="text" id="searchDesignation" name="searchDesignation" value="${searchDesignation}" size="20">
                                                </td>
                                            </tr>
                                            <tr align="center">
                                                <td>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="button" name="task" id="search" value="Search">
                                                    <input type="submit" class="button" name="task1" id="showAllRecords" value="Show All Records">
                                                      <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayOrgnList(id)">
                                                    <input class="button" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayOrgnList(id)">
                                                    <label id="org_msg">  </label></td>
                                            </tr>
                                        </table>
                                    </form>
                                </td></tr>
                            <tr><td>
                                    <form name="form1" method="POST" action="personCount.do">
                                        <DIV class="content_div">
                                            <table id="table1"  border="1" align="center" class="content">
                                                <tr>
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Organization</th>
                                                    <th class="heading" >Office Code</th>
                                                    <th class="heading" >Office Type</th>
                                                    <th class="heading" >Office Name</th>
                                                    <th class="heading" >Employee Id</th>
                                                    <th class="heading" style="display: none">Salutation</th>
                                                    <th class="heading" >Person Name</th>
                                                    <th class="heading" >Father's Name</th>
                                                    <th class="heading" >Age</th>
                                                    <%--  <th class="heading" >Image</th>--%>
                                                    <th class="heading" >Designation</th>
                                                    <th class="heading" >Address Line1</th>
                                                    <th class="heading" style="display: none">Address Line2</th>
                                                    <th class="heading" style="display: none" >Address Line3</th>
                                                    <th class="heading" >City</th>
                                                    <th class="heading" style="display: none">State</th>
                                                    <th class="heading" >Mobile No. 1st</th>
                                                    <th class="heading" style="display: none">Mobile No. 2nd</th>
                                                    <th class="heading" style="display: none">LandLine No. 1st </th>
                                                    <th class="heading" style="display: none">LandLine No. 2nd</th>
                                                    <th class="heading" >Email ID 1st</th>
                                                    <th class="heading" style="display: none">Email ID 2nd</th>
                                                    <th class="heading" >Image</th>
                                                </tr>
                                                <c:forEach var="key" items="${requestScope['keyList']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="key_person_id${loopCounter.count}" value="${key.key_person_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.org_office_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.office_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.org_office_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >${key.emp_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.salutation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.key_person_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.father_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumnKeyPerson(id)">${key.age}</td>
                                                        <%-- <td id="t1c${IDGenerator.uniqueID}" ><img src="E:/Traffic/ImageUpload/DSP police_pic_0.jpg" height="90"width="90"></td>--%>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.designation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.address_line1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="display: none">${key.address_line2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="display: none">${key.address_line3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.city_name}</td>
                                                        <%-- <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.state_name}</td> --%>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)">${key.mobile_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.mobile_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.landline_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.landline_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)">${key.email_id1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.email_id2}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.general_image_details_id}</td>
                                                        <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.image_name}</td>--%>

                                                        <td id="t1c${IDGenerator.uniqueID}" >
                                                            <input type="button" class="button" id="${loopCounter.count}" name="emp_code" value="Photo" onclick="viewDemandNote(${key.key_person_id}, 'ph')">
                                                            <input type="button" class="button" id="${loopCounter.count}" name="emp_code" value="ID" onclick="viewDemandNote(${key.key_person_id}, 'id')">
                                                        </td>

                                                    </tr>
                                                    <input type="hidden"  name="emp_code" id="emp_code1${loopCounter.count}" value="${key.emp_code}">
                                                </c:forEach>
                                                <tr>
                                                    <td colspan="13" align="center">
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
                                                <input type="hidden"  name="searchOfficeCode" value="${searchOfficeCode}">
                                                <input type="hidden"  name="searchEmpCode" value="${searchEmpCode}">
                                                <input type="hidden"  name="searchKeyPerson" value="${searchKeyPerson}">
                                                <input type="hidden"  name="searchDesignation" value="${searchDesignation}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <form name="form2" method="POST" action="personCount.do" encType="multipart/form-data"  onsubmit="return verify()" accept-charset=="UTF-8">
                                        <DIV >
                                            <table id="table2" class="content" border="0" align="center" style="width: 100%" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                              <%--  <tr>
                                                    <th class="heading1">Data Entry</th>

                                                    <td id="selectByTd" colspan="6" style="background-color: #00bfa8">
                                                        <input type="radio"  id="select_by_hindi" name="selectBy"value="hindi" onchange="showSelectedDiv(id);">Hindi Entry
                                                        <input type="radio" id="select_by_english" name="selectBy"value="english"  onchange="showSelectedDiv(id);">English Entry

                                                    </td>

</tr>--%>
                                                <tr>


                                                <input type="hidden" id="key_person_id" name="key_person_id" value="">
                                                <input type="hidden" id="general_image_details_id" name="general_image_details_id" value="" size="28"   />

                                                <th class="heading1" >Office Code</th>
                                                <td><input class="input" type="text" id="office_code" name="office_code"  size="20" disabled></td>
                                                <th class="heading1" >Office Name</th>
                                                <td><input class="input new_input" type="text" id="org_office_name" name="org_office_name"  size="28"  disabled></td>
                                                <th class="heading1" >Employee Id</th>
                                                <td><input class="input" type="text" id="employeeId" name="employeeId" value=""  size="28" disabled></td>

                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Salutation </th>
                                                    <td><select class="dropdown" id="salutation" name="salutation" value=""  disabled>
                                                            <option>---Select--- </option>
                                                            <option style="text-align: center">Mr.</option>
                                                            <option style="text-align: center">Ms.</option>
                                                            <option style="text-align: center">Mrs.</option>
                                                        </select>
                                                    </td>

                                                    <th class="heading1" >Person Name</th>
                                                    <td><input class="input new_input" type="text" id="key_person_name" name="key_person_name" value=""  size="30" disabled></td>
                                                    <th class="heading1" >Father's Name</th>
                                                    <td><input class="input new_input" type="text" id="father_name" name="father_name" value=""  size="30" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Age</th>
                                                    <td><input class="input" type="text" id="age" name="age" value=""  size="5" disabled></td>
                                                    <th class="heading1" >Designation</th>
                                                    <td><input class="input new_input" type="text" id="designation" name="designation" value=""  size="30" disabled></td>
                                                    <th class="heading1" >Address Line1</th>
                                                    <td><input class="input new_input" type="text" id="address_line1" name="address_line1" value=""  size="28"  disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Address Line2</th>
                                                    <td><input class="input new_input" type="text" id="address_line2" name="address_line2" value=""   size="25" disabled></td>
                                                    <th class="heading1" >Address Line3</th>
                                                    <td><input class="input new_input" type="text" id="address_line3" name="address_line3" value=""  size="30"  disabled></td>
                                                 <!--   <th class="heading1" >State</th>
                                                    <td><input class="input new_input" type="text" id="state_name" name="state_name" value="" size="28"  disabled></td> -->
                                                </tr>
                                                <tr>                                                    
                                                    <th class="heading1" >City</th>
                                                    <td><input class="input new_input" type="text" id="city_name" name="city_name"  size="25" value="" disabled></td>
                                                     <th class="heading1" >Mobile No. 1st</th>
                                                    <td><input class="input" type="text" id="mobile_no1" name="mobile_no1" value=""  size="30" maxlength="10" disabled></td>
                                                    <th class="heading1" >Mobile No. 2nd</th>
                                                    <td><input class="input" type="text" id="mobile_no2" name="mobile_no2" value=""  size="25" maxlength="10" disabled></td>
                                                </tr>
                                                <tr>                                                    
                                                    <th class="heading1" >LandLine No. 1st</th>
                                                    <td><input class="input" type="text" id="landline_no1" name="landline_no1" value=""  size="25"  disabled></td>
                                                    <th class="heading1" >LandLine No. 2nd</th>
                                                    <td ><input class="input" type="text" id="landline_no2" name="landline_no2" value=""  size="30"   disabled></td>
                                                    <th class="heading1" >Email ID 1st</th>
                                                    <td><input class="input" type="text" id="email_id1" name="email_id1" value=""  size="30"  disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Email ID 2nd</th>
                                                    <td><input class="input" type="text" id="email_id2" name="email_id2" value=""  size="25"  disabled></td>
                                                    <th class="heading1">Select Photo </th>
                                                    <td>
                                                        <input type="file" id="design_name" name="design_name" value="" size="35"  onchange="readURL(this);" disabled  />
                                                        <input type="hidden" id="img" name="img" value="">
                                                    </td>
                                                    <th class="heading1">Select ID OR DL </th>
                                                    <td>
                                                        <input type="file" id="id_proof" name="id_proof" value="" size="30"  onchange="readURL(this);"   disabled/>
                                                        <input type="hidden" id="id_img" name="img" value="">
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td align='center' colspan="8">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled> &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button"  type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>  &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">  &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>

                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden"  name="searchOfficeCode" value="${searchOfficeCode}">
                                                <input type="hidden"  name="searchKeyPerson" value="${searchKeyPerson}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </DIV>
                                        <%--  <div id="image_perview" style="visibility: hidden;">
                                          <table id="table1" cellspacing="10" border="8.0" width="200" align="center" style="background-color:white;margin-top: 20px">
                                         <tr align="center">
                                             <td>
                                                 <img id="blah" src="#" alt=""/><br>
                                             </td>
                                         </tr>
                                     </table>
</div>--%>
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
