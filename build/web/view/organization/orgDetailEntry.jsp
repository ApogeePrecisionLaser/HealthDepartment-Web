<%--
    Document   : createOrgan
    Created on : Dec 28, 2012, 11:33:42 AM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Data Entry: Org Detail</title>
    <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
    <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
    <%-- <link rel="stylesheet" type="text/css" href="css/jquery-ui.css"/> --%>

    <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="JS/css-pop.js"></script>
    <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="css/calendar.css" >
    <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
    <script type="text/javascript" language="javascript">
        jQuery(function(){
            $("#office_city").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return  "City";}
                }
            });
            $("#searchOrganisation").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return "searchOrganisation";}
                }
            });
            $("#searchOrganisation").result(function(event, data, formatted){
            });
            //            $("#searchPerson").autocomplete("orgDetailEntryCont.do", {
            //                extraParams: {
            //                    action1: function() { return "searchPerson";},
            //                    action2: function() { return $("#searchOrganisation").val();},
            //                    action3: function() { return $('#searchOrgOffice :selected').text()}
            //                }
            //            });
            $("#searchPerson").change(function(event, data, formatted){
                $("#key_person").val("");
                $("#designation").val("");
                $("#person_city").val("");
                $("#person_address1").val("");
                $("#person_address2").val("");
                $("#person_mail_id1").val("");
                $("#person_mail_id2").val("");
                $("#person_mobile1").val("");
                $("#person_mobile2").val("");
                $("#person_landLine1").val("");
                $("#person_landLine2").val("");
                $("#employeeId").val("");
               // $("#general_image_details_id").val("");
                var organisation_id = $.trim($("#organisation_id").val());
                var org_office_id =  $('#searchOrgOffice :selected').val();
                var person_id =  $('#searchPerson :selected').val();
                if(person_id != 0){
                    var queryString = "ajaxRequest=getPersonDetail&organisation_id="+organisation_id+"&org_office_id="+org_office_id+"&person_id="+person_id ;
                    $.getJSON("orgDetailEntryCont.do", queryString , function(response_data) {
                         var items = [];
                        $.each(response_data, function(key, value) {
                            items.push(value);
                        });
                        
                        $("#salutation").val(items[0] == 'null' ? '': items[0]);
                        $("#key_person").val(items[1] == 'null' ? '': items[1]);//                    $("#searchPerson").val(items[1] == 'null' ? '': items[1]);
                        $("#designation").val(items[2] == 'null' ? '': items[2]);
                        $("#person_city").val(items[3] == 'null' ? '': items[3]);
                        $("#person_address1").val(items[4] == 'null' ? '': items[4]);
                        $("#person_address2").val(items[5] == 'null' ? '': items[5]);
                        $("#person_mail_id1").val(items[6] == 'null' ? '': items[6]);
                        $("#person_mail_id2").val(items[7] == 'null' ? '': items[7]);
                        $("#person_mobile1").val(items[8] == 'null' ? '': items[8]);
                        $("#person_mobile2").val(items[9] == 'null' ? '': items[9]);
                        $("#person_landLine1").val(items[10] == 'null' ? '': items[10]);
                        $("#person_landLine2").val(items[11] == 'null' ? '': items[11]);//                     alert("hi");
                        $("#employeeId").val(items[12] == 'null' ? '': items[12]);
                    //    $("#general_image_details_id").val(items[13] == 'null' ? '': items[13]);
                    });
                    $("#keyPersonId").val(person_id);
                }
                clearErrorFieldPerson();
                

            });
            $("#org_type_name").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return "getOrgTypeName"}
                }
            });
            $("#org_type_name").result(function(event, data, formatted){
                var idValue = $("#org_type_name").val();
                $("#org_sub_type").val("");
                $("#org_sub_type").flushCache();
                if(idValue.length > 0){
                    document.getElementById("org_type_name").style.borderColor = "" ;
                    document.getElementById("org_type_name").style.borderStyle = "" ;
                }
            });
            $("#org_sub_type").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return "getOrgSubTypeName"},
                    action2: function() { return document.getElementById("org_type_name").value;}
                }
            });
            $("#org_sub_type").result(function(event, data, formatted){
                var idValue = $("#org_sub_type").val();
                if(idValue.length > 0){
                    document.getElementById("org_sub_type").style.borderColor = "" ;
                    document.getElementById("org_sub_type").style.borderStyle = "" ;
                }
            });
            $("#person_city").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return "City";}
                }
            });           
            $("#designation").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    action1: function() { return "Designation";}
                }
            });
            $("#office_type").autocomplete("organisationCont.do", {
                extraParams: {                  
                    action1: function() { return "getOrgOfficeType"}
                }
            });
            $("#office_code").autocomplete("orgDetailEntryCont.do", {
                extraParams: {
                    office_type:function(){return document.getElementById('office_type').value;},
                    action1: function() { return "getOfficeCode"}
                }
            });


        });

        function myLeftTrim(str) {
            var beginIndex = 0;
            for(var i = 0; i < str.length; i++) {
                if(str.charAt(i) == ' ') {
                    beginIndex++;
                } else {
                    break;
                }
            }
            return str.substring(beginIndex, str.length);
        }
        function verifySearch() {
            var result;
            var searchOrganisation = document.getElementById("searchOrganisation").value;
            if(myLeftTrim(searchOrganisation).length == 0) {
                document.getElementById("message").innerHTML = "<td bgcolor='coral'><b>Organisation is required for Search...</b></td>";
                document.getElementById("searchOrganisation").focus();
                result = false; // code to stop from submitting the form2.
            }
            if(result == false) {
                return false;
            } else {
                result = true;
                popup('popUpDiv');
            }

            return result;
        }
        function verifyOrg() {
            var result;           
            var organisation = document.getElementById("organisation").value;
            if(myLeftTrim(organisation).length == 0) {
                document.getElementById("message").innerHTML = "<td bgcolor='coral'><b>Organisation Name is required...</b></td>";
                document.getElementById("organisation").focus();
                errorField("organisation");
                result = false; // code to stop from submitting the form2.
            }else{
                removeErrorField("organisation");
            }
//            if(myLeftTrim(document.getElementById("org_type_name").value).length == 0) {
//                document.getElementById("message").innerHTML = "<td bgcolor='coral'><b>Organisation Type  is required...</b></td>";
//                document.getElementById("org_type_name").focus();
//                errorField("org_type_name");
//                result = false;// code to stop from submitting the form2.
//            }else{
//                removeErrorField("org_type_name");
//            }
//            if(myLeftTrim(document.getElementById("org_sub_type").value).length == 0) {
//                document.getElementById("message").innerHTML = "<td  bgcolor='coral'><b>Organisation Sub Type  is required...</b></td>";
//                document.getElementById("org_sub_type").focus();
//                errorField("org_sub_type");
//                result = false;// code to stop from submitting the form2.
//            }else{
//                removeErrorField("org_sub_type");
//            }
            if(result == false) {
                return false;
            } else {
                result = true;
            }
            
            return result;
        }
        function verifyOffice() {
            var result;
//            if(myLeftTrim(document.getElementById("org_sub_type").value).length == 0) {
//                document.getElementById("message").innerHTML = "<td  bgcolor='coral'><b>Organisation Sub Type  is required...</b></td>";
//                document.getElementById("org_sub_type").focus();
//                errorField("org_sub_type");
//                result = false;// code to stop from submitting the form2.
//            }else{
//                removeErrorField("org_sub_type");
//            }
            var office_name = document.getElementById("office_name").value;
            if(myLeftTrim(office_name).length == 0) {
                document.getElementById("message").innerHTML = "<td  bgcolor='coral'><b>Office is required...</b></td>";
                document.getElementById("office_name").focus();
                errorField("office_name");
                result = false; // code to stop from submitting the form2.
            }else{
                removeErrorField("office_name");
            }
            if(myLeftTrim(document.getElementById("office_type").value).length == 0) {
                document.getElementById("message").innerHTML = "<td  bgcolor='coral'><b>Office Type  is required...</b></td>";
                document.getElementById("office_type").focus();
                errorField("office_type");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("office_type");
            }
            if(myLeftTrim(document.getElementById("office_code").value).length == 0) {
                document.getElementById("message").innerHTML = "<td  bgcolor='coral'><b>Office Code  is required...</b></td>";
                document.getElementById("office_code").focus();
                errorField("office_code");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("office_code");
            }
            var city_name = document.getElementById("office_city").value;
            if(myLeftTrim(city_name).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>City Name is required...</b></td>";
                document.getElementById("office_city").focus();
                errorField("office_city");
                result = false; // code to stop from submitting the form2.
            }else{
                removeErrorField("office_city");
            }
            var office_address1 = document.getElementById("office_address1").value;
            if(myLeftTrim(office_address1).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Office Address Line 1 is required...</b></td>";
                document.getElementById("office_address1").focus();
                errorField("office_address1");
                result = false;
            }else{
                removeErrorField("office_address1");
            }
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

            var office_mobile1 = document.getElementById("office_mobile1").value;
            var office_mobile2 = document.getElementById("office_mobile2").value;
            if(myLeftTrim(office_mobile1).length != 10 || isNaN(myLeftTrim(office_mobile1))) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>First Office Mobile No. is required</b></td>";
                document.getElementById("office_mobile1").focus();
                errorField("office_mobile1");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("office_mobile1");
            }
            if(myLeftTrim(office_mobile2).length > 0 ) {
                if( myLeftTrim(office_mobile2).length != 10  || isNaN(myLeftTrim(office_mobile2))){
                    document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Second Office Mobile No. is required</b></td>";
                    document.getElementById("office_mobile2").focus();
                    errorField("office_mobile2");
                    result = false;// code to stop from submitting the form2.
                }
            }else{
                removeErrorField("office_mobile2");
            }
            var office_mail_id1 = document.getElementById("office_mail_id1").value;
            var office_mail_id2 = document.getElementById("office_mail_id2").value;
            if(reg.test(myLeftTrim(office_mail_id1)) == false) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct  First Office Email ID...</b></td>";
                document.getElementById("office_mail_id1").focus();
                errorField("office_mail_id1");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("office_mail_id1");
            }
            if(myLeftTrim(office_mail_id2).length > 0 && reg.test(myLeftTrim(office_mail_id2)) == false) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct Second Office Email ID...</b></td>";
                document.getElementById("office_mail_id2").focus();
                errorField("office_mail_id2");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("office_mail_id2");
            }
            if(result == false) {
                return false;
            } else {
                result = true;
            }
            return result;
        }
        function verifyPerson() {
            var result;
            if(myLeftTrim(document.getElementById("employeeId").value).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Employee ID  is required...</b></td>";
                document.getElementById("employeeId").focus();
                errorField("employeeId");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("employeeId");
            }
            if(myLeftTrim(document.getElementById("key_person").value).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>key person  is required...</b></td>";
                document.getElementById("key_person").focus();
                errorField("key_person");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("key_person");
            }
            if(myLeftTrim(document.getElementById("designation").value).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Designation  is required...</b></td>";
                document.getElementById("designation").focus();
                errorField("designation");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("designation");
            }
            if(myLeftTrim(document.getElementById("person_city").value).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>City of  key person  is required...</b></td>";
                document.getElementById("person_city").focus();
                errorField("person_city");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("person_city");
            }
            var person_address1 = document.getElementById("person_address1").value;
            if(myLeftTrim(person_address1).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>";
                document.getElementById("person_address1").focus();
                errorField("person_address1");
                result = false;
            }else{
                removeErrorField("person_address1");
            }
            var person_mobile1 = document.getElementById("person_mobile1").value;
            var person_mobile2 = document.getElementById("person_mobile2").value;
            if(myLeftTrim(person_mobile1).length != 10 || isNaN(myLeftTrim(person_mobile1))) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>First Person Mobile No. is required...</b></td>";
                document.getElementById("person_mobile1").focus();
                errorField("person_mobile1");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("person_mobile1");
            }
            if(myLeftTrim(person_mobile2).length > 0 ) {//                    alert(!isNaN(myLeftTrim(person_mobile2)));
                if( myLeftTrim(person_mobile2).length != 10 || isNaN(myLeftTrim(person_mobile2))){
                    document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Second Person Mobile No. is required...</b></td>";
                    document.getElementById("person_mobile2").focus();
                    errorField("person_mobile2");
                    result = false;// code to stop from submitting the form2.
                }
            }else{
                removeErrorField("person_mobile2");
            }
            var person_mail_id1 = document.getElementById("person_mail_id1").value;
            var person_mail_id2 = document.getElementById("person_mail_id2").value;
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if(reg.test(myLeftTrim(person_mail_id1)) == false) {
                document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct First Person Email ID...</b></td>";
                document.getElementById("person_mail_id1").focus();
                errorField("person_mail_id1");
                result = false;// code to stop from submitting the form2.
            }else{
                removeErrorField("person_mail_id1");
            }

            if(myLeftTrim(person_mail_id2).length > 0) {
                if(reg.test(myLeftTrim(person_mail_id2)) == false) {
                    document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct Second Person Email ID...</b></td>";
                    document.getElementById("person_mail_id2").focus();
                    errorField("person_mail_id2");
                    result = false;// code to stop from submitting the form2.
                }
            }else{
                removeErrorField("person_mail_id2");
            }
            if(result == false) {
                return false;
            } else {
                result = true;
            }
            return result;
        }
        function verifyAll() {
            if(verifyOrg() && verifyOffice() && verifyPerson() ){
                return true;
            }else{
                return false;
            }
        }

        function errorField(id){
            document.getElementById(id).style.borderColor = "red" ;
            document.getElementById(id).style.borderStyle = "solid" ;
        }
        function removeErrorField(id){
            var idValue = $("#"+id).val();
            if(idValue.length > 0){
                document.getElementById(id).style.borderColor = "" ;
                document.getElementById(id).style.borderStyle = "" ;
            }
        }

        function getSearchOfficeList(data){//            alert("vfxcz");
            $("#searchOrgOffice option").each(function()
            {
                if($(this).val() != 0){
                    $(this).remove();
                }
            });
            var organisation_id = $.trim($("#organisation_id").val());;
            var queryString = "ajaxRequest=searchOffice&organisation_id="+organisation_id;
            $.ajaxSetup({'async': false});
            $.ajax({url: "orgDetailEntryCont.do", data: queryString, success: function(response_data) {                            //alert(response_data);
                    var arr = response_data.split("&#;");                                           //           alert(data);
                    var i;
                    for(i = 0; i < arr.length - 1; i++) {
                        var opt = document.createElement("option");
                        for(var j = 0; j < 2 ; j++) {
                            if(j == 0){
                                opt.value = $.trim(arr[i]);
                                i++;
                            }else{
                                opt.text = $.trim(arr[i]);
                                if(data == opt.text){
                                    opt.selected = 'selected';
                                }
                            }
                        }
                        document.getElementById("searchOrgOffice").options.add(opt);
                    }                            // alert(i + " : " + arr[1])
                    popup('popUpDiv');
                    getOfficeDetail();
                }
            });
        }

        function getOfficeDetail() {
            $("#office_code").val("");
            $("#office_name").val("");
            $("#office_type").val("");
            $("#office_city").val("");
            $("#office_address1").val("");
            $("#office_address2").val("");
            $("#office_mail_id1").val("");
            $("#office_mail_id2").val("");
            $("#office_mobile1").val("");
            $("#office_mobile2").val("");
            $("#office_landLine1").val("");
            $("#office_landLine2").val("");
            $("#key_person").val("");
            $("#designation").val("");
            $("#person_city").val("");
            $("#person_address1").val("");
            $("#person_address2").val("");
            $("#person_mail_id1").val("");
            $("#person_mail_id2").val("");
            $("#person_mobile1").val("");
            $("#person_mobile2").val("");
            $("#person_landLine1").val("");
            $("#person_landLine2").val("");
            $("#employeeId").val("");
            $("#key_person").flushCache();
            var organisation_id = $.trim($("#organisation_id").val());
            var org_office_id =  $('#searchOrgOffice :selected').val();
            if(org_office_id != 0){
                var queryString = "ajaxRequest=getOfficeDetail&organisation_id="+organisation_id+"&org_office_id="+org_office_id ;
                popup('popUpDiv');
                $.getJSON("orgDetailEntryCont.do", queryString , function(response_data) {
                    var items = [];               // alert(response_data);
                    $.each(response_data, function(key, value) {
                        items.push(value);
                    });
                    $("#office_code").val(items[0]  == 'null' ? '': items[0]);
                    $("#office_name").val(items[1]  == 'null' ? '': items[1]);
                    $("#office_type").val(items[2] == 'null' ? '': items[2]);
                    $("#office_city").val(items[3]) == 'null' ? '': items[3];
                    $("#office_address1").val(items[4] == 'null' ? '': items[4]);
                    $("#office_address2").val(items[5] == 'null' ? '': items[5]);
                    $("#office_mail_id1").val(items[6] == 'null' ? '': items[6]);
                    $("#office_mail_id2").val(items[7] == 'null' ? '': items[7]);
                    $("#office_mobile1").val(items[8] == 'null' ? '': items[8]);
                    $("#office_mobile2").val(items[9] == 'null' ? '': items[9]);
                    $("#office_landLine1").val(items[10] == 'null' ? '': items[10]);
                    $("#office_landLine2").val(items[11] == 'null' ? '': items[11]);
                    $("#salutation").val(items[12] == 'null' ? '': items[12]);
                    $("#key_person").val(items[13] == 'null' ? '': items[13] );
                    $("#searchPerson").val(items[13] == 'null' ? '': items[13] );
                    $("#designation").val(items[14] == 'null' ? '': items[14]);
                    $("#person_city").val(items[15] == 'null' ? '': items[15]);
                    $("#person_address1").val(items[16] == 'null' ? '': items[16]);
                    $("#person_address2").val(items[17] == 'null' ? '': items[17]);
                    $("#person_mail_id1").val(items[18] == 'null' ? '': items[18]);
                    $("#person_mail_id2").val(items[19] == 'null' ? '': items[19]);
                    $("#person_mobile1").val(items[20] == 'null' ? '': items[20]);
                    $("#person_mobile2").val(items[21] == 'null' ? '': items[21]);
                    $("#person_landLine1").val(items[22] == 'null' ? '': items[22]);
                    $("#person_landLine2").val(items[23] == 'null' ? '': items[23]);
                    $("#employeeId").val(items[24] == 'null' ? '': items[24]);
                    $("#office_id").val(org_office_id);
                    if(items[0] == '') {
                        var message = "Error: No OrgOffice entry found for " + $.trim($("#organisation_id").text()) + " " + $('#searchOrgOffice :selected').text() + ".";
                        $("#message").html("<td colspan='4' bgcolor='orange'><b>" + message + "</b></td>");
                        document.getElementById("searchOrgOffice").focus();
                    } else {
                        $("#message").html("");
                    }
                    if(items[13] == '' || items[13] == 'null') {
                        var message = "Error: No Key Person entry found for " + $.trim($("#organisation_id").text()) + " " + $('#searchOrgOffice :selected').text() + ".";
                        $("#message").html("<td colspan='4' bgcolor='orange'><b>" + message + "</b></td>");
                        document.getElementById("searchPerson").focus();
                        $("#searchPerson option").each(function()
                        {
                            if($(this).val() != 0){
                                $(this).remove();
                            }
                        });
                        popup('popUpDiv');
                    } else {
                        getSearchPersonList(items[13])
                        $("#message").html("");
                    }
                });
            }else{
                $("#searchPerson option").each(function()
                {
                    if($(this).val() != 0){
                        $(this).remove();
                    }
                }); 
            }
            clearErrorField();
            makeFieldDisabled();
            
        }
        function getSearchPersonList(data){//            alert("vfxcz");
            $("#searchPerson option").each(function()
            {
                if($(this).val() != 0){
                    $(this).remove();
                }
            }); 
            var organisation_id = $.trim($("#organisation_id").val());
            var org_office_id =  $('#searchOrgOffice :selected').val();
            var queryString = "ajaxRequest=searchPerson&organisation_id="+organisation_id+"&org_office_id="+org_office_id ;
            $.ajaxSetup({'async': false});
            $.ajax({url: "orgDetailEntryCont.do", data: queryString, success: function(response_data) {                            //alert(response_data);
                    var arr = response_data.split("&#;");                                           //           alert(data);
                    var i;
                    for(i = 0; i < arr.length - 1; i++) {
                        var opt = document.createElement("option");
                        for(var j = 0; j < 2 ; j++) {
                            if(j == 0){
                                opt.value = $.trim(arr[i]);
                                i++;
                            }else{
                                opt.text = $.trim(arr[i]);
                                if(data == opt.text){
                                    opt.selected = 'selected';
                                }
                            }
                        }
                        document.getElementById("searchPerson").options.add(opt);
                    }                            // alert(i + " : " + arr[1])

                    popup('popUpDiv');
                }
            });           
        }
         
        function saveAllRecord() {            
            if(verifyAll()) {
                var serializedForm = $('#mainForm').serialize();               
                popup('popUpDiv');
                var queryString = "task=Save&subTask=SaveAllRecord&" + serializedForm;
                $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);               
                    response_data = response_data.split("&#;");
                    var msgColor = response_data[0];
                    var message = response_data[1];
                    $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
                    popup('popUpDiv');
                });

            }
        }

        function saveOrgMap() {
            if(verifyOrg()) {
                var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
                var queryString = "task=Save&subTask=saveOrgMap&" + serializedForm;
                popup('popUpDiv');
                $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                    response_data = response_data.split("&#;");
                    var msgColor = response_data[0];
                    var message = response_data[1];
                    popup('popUpDiv');
                    $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
                });
                ;

            }
        }


        function saveOrgOffice() {
            if(verifyOffice()) {
                var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
                var queryString = "task=Save&subTask=saveOrgOffice&" + serializedForm;
                popup('popUpDiv');
                $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                    response_data = response_data.split("&#;");
                    var updatedValue = response_data[0];
                    var isUpdate = response_data[1];
                    var msgColor = response_data[2];
                    var message = response_data[3];
                    if(isUpdate == "Yes"){
                        getSearchOfficeList(updatedValue);
                    }else{
                        popup('popUpDiv');
                    }
                    $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
                });

            }
        }



        function savePerson() {
            if(verifyPerson()) {
                var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
                var queryString = "task=Save&subTask=savePerson&" + serializedForm;
                popup('popUpDiv');
                $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                    response_data = response_data.split("&#;");
                    var updatedValue = response_data[0];
                    var isUpdate = response_data[1];
                    var msgColor = response_data[2];
                    var message = response_data[3];
                    if(isUpdate == "Yes"){
                        getSearchPersonList(updatedValue);
                    }else{
                        popup('popUpDiv');  
                    }

                    $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
                });

            }
        }

        function deleteOrg() {
          
            var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
            var queryString = "task=Save&subTask=deleteOrg&" + serializedForm;
            popup('popUpDiv');
            $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                response_data = response_data.split("&#;");
                var updatedValue = response_data[0];
                var isUpdate = response_data[1];
                var msgColor = response_data[2];
                var message = response_data[3];
                if(isUpdate >  0){
                    clearField();
                }
                popup('popUpDiv');
                $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
            });
            ;

            
        }
        function deleteOrgOffice() {
           
            var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
            var queryString = "task=Save&subTask=deleteOrgOffice&" + serializedForm;
            popup('popUpDiv');
            $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                response_data = response_data.split("&#;");
                var updatedValue = response_data[0];
                var isUpdate = response_data[1];
                var msgColor = response_data[2];
                var message = response_data[3];
                if(isUpdate >  0){
                    getSearchOfficeList(updatedValue);
                    clearFieldOffice();
                }else{
                    popup('popUpDiv');
                }
                $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
            });

            
        }

        function deletePerson() {
           
            var serializedForm = $('#mainForm').serialize();//                alert(serializedForm)
            var queryString = "task=Save&subTask=deletePerson&" + serializedForm;
            popup('popUpDiv');
            $.post("orgDetailEntryCont.do", queryString, function( response_data ) { //do some stuff with the response data //   alert(response_data);
                response_data = response_data.split("&#;");
                var updatedValue = response_data[0];
                var isUpdate = response_data[1];
                var msgColor = response_data[2];
                var message = response_data[3];
                if(isUpdate > 0){
                    clearFieldPerson();
                    getSearchPersonList(updatedValue);
                }else{
                    popup('popUpDiv');
                }
                $("#message").html("<td colspan='4' bgcolor='" + msgColor + "'><b>" + message + "</b></td>");
            });

           
        }



        function copyAsAbove(){
            if ($('#sameAsAbove').is(':checked')) {
                $("#person_city").val($("#office_city").val());
                $("#person_address1").val($("#office_address1").val());
                $("#person_address2").val($("#office_address2").val());
                $("#person_mail_id1").val($("#office_mail_id1").val());
                $("#person_mail_id2").val($("#office_mail_id2").val());
                $("#person_mobile1").val($("#office_mobile1").val());
                $("#person_mobile2").val($("#office_mobile2").val());
                $("#person_landLine1").val($("#office_landLine1").val());
                $("#person_landLine2").val($("#office_landLine2").val());
                removeErrorField("person_city");
                removeErrorField("person_address1");
                removeErrorField("person_address2");
                removeErrorField("person_mail_id1");
                removeErrorField("person_mail_id2");
                removeErrorField("person_mobile1");
                removeErrorField("person_mobile2");
                removeErrorField("person_landLine1");
                removeErrorField("person_landLine1");
            } else {
                $("#person_city").val("");
                $("#person_address1").val("");
                $("#person_address2").val("");
                $("#person_mail_id1").val("");
                $("#person_mail_id2").val("");
                $("#person_mobile1").val("");
                $("#person_mobile2").val("");
                $("#person_landLine1").val("");
                $("#person_landLine2").val("");
            }
        }
        // Remove Error Field !!!!!!!!!!!!!!!!!!!!!!!!!
        
        function removeAllErrorField(id){            
            document.getElementById(id).style.borderColor = "" ;
            document.getElementById(id).style.borderStyle = "" ;            
        }
        function clearErrorFieldOrg(){
            removeAllErrorField("organisation");
            removeAllErrorField("org_type_name");
            removeAllErrorField("org_sub_type");
            $("#message").html("");
        }
        function clearErrorFieldOffice(){
            removeAllErrorField("office_name");
            removeAllErrorField("office_type");
            removeAllErrorField("office_city");
            removeAllErrorField("office_address1");
            removeAllErrorField("office_address2");
            removeAllErrorField("office_mail_id1");
            removeAllErrorField("office_mail_id2");
            removeAllErrorField("office_mobile1");
            removeAllErrorField("office_mobile2");
            removeAllErrorField("office_landLine1");
            removeAllErrorField("office_landLine2");
            $("#message").html("");
        }
        function clearErrorFieldPerson(){
            removeAllErrorField("key_person");
            removeAllErrorField("designation");
            removeAllErrorField("person_city");
            removeAllErrorField("person_address1");
            removeAllErrorField("person_address2");
            removeAllErrorField("person_mail_id1");
            removeAllErrorField("person_mail_id2");
            removeAllErrorField("person_mobile1");
            removeAllErrorField("person_mobile2");
            removeAllErrorField("person_landLine1");
            removeAllErrorField("person_landLine2");
            removeAllErrorField("employeeId");
           // removeAllErrorField("general_image_details_id");
            $("#message").html("");
        }
        function clearErrorField(){
            clearErrorFieldOrg();
            clearErrorFieldOffice();
            clearErrorFieldPerson();
        }

        function clearFieldOrg(){
            $("#organisation").val("");
            $("#org_type_name").val("");
            $("#org_sub_type").val("");
        }
        function clearFieldOffice(){
            $("#office_code").val("");
            $("#office_name").val("");
            $("#office_type").val("");
            $("#office_city").val("");
            $("#office_address1").val("");
            $("#office_address2").val("");
            $("#office_mail_id1").val("");
            $("#office_mail_id2").val("");
            $("#office_mobile1").val("");
            $("#office_mobile2").val("");
            $("#office_landLine1").val("");
            $("#office_landLine2").val("");
        }
        function clearFieldPerson(){
            $("#employeeId").val("");
            $("#key_person").val("");
            $("#designation").val("");
            $("#person_city").val("");
            $("#person_address1").val("");
            $("#person_address2").val("");
            $("#person_mail_id1").val("");
            $("#person_mail_id2").val("");
            $("#person_mobile1").val("");
            $("#person_mobile2").val("");
            $("#person_landLine1").val("");
            $("#person_landLine2").val("");
            //$("#general_image_details_id").val("");
            $("#message").html("");
        }
        function clearField(){
            $("#searchOrganisation").val("");
            $("#searchOrgOffice").val("");
            $("#searchPerson").val("");
            $("#searchPerson").attr("disabled", "disabled");
            $("#searchOrgOffice").attr("disabled", "disabled");
            clearFieldOrg();
            clearFieldOffice();
            clearFieldPerson();
            $("#message").html("");
        }

        // ####################### REMOVE Disabled @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        function clearFieldDisabledOrg(){
            $("#organisation").removeAttr("disabled", "disabled");
            $("#org_type_name").removeAttr("disabled", "disabled");
            $("#org_sub_type").removeAttr("disabled", "disabled");
        }
        function clearFieldDisabledOffice(){
            $("#office_code").removeAttr("disabled","disabled");
            $("#office_name").removeAttr("disabled", "disabled");
            $("#office_type").removeAttr("disabled", "disabled");
            $("#office_city").removeAttr("disabled", "disabled");
            $("#office_address1").removeAttr("disabled", "disabled");
            $("#office_address2").removeAttr("disabled", "disabled");
            $("#office_mail_id1").removeAttr("disabled", "disabled");
            $("#office_mail_id2").removeAttr("disabled", "disabled");
            $("#office_mobile1").removeAttr("disabled", "disabled");
            $("#office_mobile2").removeAttr("disabled", "disabled");
            $("#office_landLine1").removeAttr("disabled", "disabled");
            $("#office_landLine2").removeAttr("disabled", "disabled");
        }
        function clearFieldDisabledPerson(){
            $("#employeeId").removeAttr("disabled", "disabled");
            $("#salutation").removeAttr("disabled", "disabled");
            $("#key_person").removeAttr("disabled", "disabled");
            $("#designation").removeAttr("disabled", "disabled");
            $("#sameAsAbove").removeAttr("disabled", "disabled");
            $("#person_city").removeAttr("disabled", "disabled");
            $("#father_name").removeAttr("disabled", "disabled");
            $("#age").removeAttr("disabled", "disabled");
            $("#person_address1").removeAttr("disabled", "disabled");
            $("#person_address2").removeAttr("disabled", "disabled");
            $("#person_mail_id1").removeAttr("disabled", "disabled");
            $("#person_mail_id2").removeAttr("disabled", "disabled");
            $("#person_mobile1").removeAttr("disabled", "disabled");
            $("#person_mobile2").removeAttr("disabled", "disabled");
            $("#person_landLine1").removeAttr("disabled", "disabled");
            $("#person_landLine2").removeAttr("disabled", "disabled");
        }
        function clearFieldDisabled(){
            clearFieldDisabledOrg();
            clearFieldDisabledOffice();
            clearFieldDisabledPerson();
            $("#searchOrganisation").val("");
            $("#saveUp").removeAttr("disabled", "disabled");
            $("#message").html("");
        }

        function makeFieldButtonDisable(){
            //  $("#orgNew").attr("disabled", "disabled");
            $("#orgEdit").attr("disabled", "disabled");
            $("#orgSave").attr("disabled", "disabled");
            $("#orgDelete").attr("disabled", "disabled");
            $("#orgOfNew").attr("disabled", "disabled");
            $("#orgOfEdit").attr("disabled", "disabled");
            $("#orgOfSave").attr("disabled", "disabled");
            $("#orgOfDelete").attr("disabled", "disabled");
            $("#kpNew").attr("disabled", "disabled");
            $("#kpEdit").attr("disabled", "disabled");
            $("#kpSave").attr("disabled", "disabled");
            $("#kpDelete").attr("disabled", "disabled");
        }

        // ####################### Make Field Disabled @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        function makeFieldDisabledOrg(){
            $("#organisation").attr("disabled", "disabled");
            $("#org_type_name").attr("disabled", "disabled");
            $("#org_sub_type").attr("disabled", "disabled");
            $("#orgSave").attr("disabled", "disabled");
            $("#orgDelete").attr("disabled", "disabled");
            $("#orgOfEdit").removeAttr("disabled", "disabled");
        }
        function makeFieldDisabledOffice(){
            $("#office_name").attr("disabled", "disabled");
            $("#office_type").attr("disabled", "disabled");
            $("#office_city").attr("disabled", "disabled");
            $("#office_address1").attr("disabled", "disabled");
            $("#office_address2").attr("disabled", "disabled");
            $("#office_mail_id1").attr("disabled", "disabled");
            $("#office_mail_id2").attr("disabled", "disabled");
            $("#office_mobile1").attr("disabled", "disabled");
            $("#office_mobile2").attr("disabled", "disabled");
            $("#office_landLine1").attr("disabled", "disabled");
            $("#office_landLine2").attr("disabled", "disabled");
            $("#orgOfSave").attr("disabled", "disabled");
            $("#orgOfDelete").attr("disabled", "disabled");           
        }
        function makeFieldDisabledPerson(){            
            $("#key_person").attr("disabled", "disabled");
            $("#designation").attr("disabled", "disabled");
            $("#person_city").attr("disabled", "disabled");
            $("#person_address1").attr("disabled", "disabled");
            $("#person_address2").attr("disabled", "disabled");
            $("#person_mail_id1").attr("disabled", "disabled");
            $("#person_mail_id2").attr("disabled", "disabled");
            $("#person_mobile1").attr("disabled", "disabled");
            $("#person_mobile2").attr("disabled", "disabled");
            $("#person_landLine1").attr("disabled", "disabled");
            $("#person_landLine2").attr("disabled", "disabled");
            //$("#general_image_details_id").attr("disabled", "disabled");
            $("#kpSave").attr("disabled", "disabled");
            $("#kpDelete").attr("disabled", "disabled");
            $("#kpEdit").removeAttr("disabled", "disabled");
        }
        function makeFieldDisabled(){
            makeFieldDisabledOrg();
            makeFieldDisabledOffice();
            makeFieldDisabledPerson();
        }
        
        function checkIsSearch() {
            var isSearch =  $("#isSearch").val();//            alert(isSearch);
            if(isSearch == 'Yes'){
                makeFieldDisabled();
                $("#searchPerson").removeAttr("disabled", "disabled");
                $("#searchOrgOffice").removeAttr("disabled", "disabled");
                $("#orgEdit").removeAttr("disabled", "disabled");
                $("#orgOfNew").removeAttr("disabled", "disabled");
                $("#orgOfEdit").removeAttr("disabled", "disabled");
                $("#kpNew").removeAttr("disabled", "disabled");
                $("#kpEdit").removeAttr("disabled", "disabled");
            }
        }

        function onOrgEdit(){
            makeFieldDisabled();
            clearFieldDisabledOrg();
            $("#orgSave").removeAttr("disabled", "disabled");
            $("#orgDelete").removeAttr("disabled", "disabled");
        }
        function onOrgOfficeEdit(){
            makeFieldDisabled();
            clearFieldDisabledOffice();
            $("#orgOfSave").removeAttr("disabled", "disabled");
            $("#orgOfDelete").removeAttr("disabled", "disabled");
        }
        function onOrgOfficeNew(){
            makeFieldDisabled();
            clearFieldOffice();
            clearFieldDisabledOffice();
            $("#office_id").val("");
            $("#orgOfSave").removeAttr("disabled", "disabled");
            $("#orgOfEdit").attr("disabled", "disabled");
            $("#orgOfDelete").attr("disabled", "disabled");
        }
        function onPersonEdit(){
            makeFieldDisabled();
            clearFieldDisabledPerson();
            $("#kpSave").removeAttr("disabled", "disabled");
            $("#kpDelete").removeAttr("disabled", "disabled");
        }
        function onPersonNew(){
            makeFieldDisabled();
            clearFieldPerson();
            clearFieldDisabledPerson();
            $("#keyPersonId").val("");
            $("#kpEdit").attr("disabled", "disabled");
            $("#kpSave").removeAttr("disabled", "disabled");
        }



        function setDefaultValues(id){
            var result_type=   document.getElementById(id).checked;
            var default_mobile_no="9999999999";
            var default_email_id="abc@xyz.com";
            var default_address= "ABC";
            if(result_type){
                $("#office_mobile1").val(default_mobile_no);
                $("#office_mail_id1").val(default_email_id);
                $("#office_address1").val(default_address);
                $("#person_mobile1").val(default_mobile_no);
                $("#person_mail_id1").val(default_email_id);
                $("#person_address1").val(default_address);
            }else{
                $("#office_mobile1").val(" ");
                $("#office_mail_id1").val("");
                $("#office_address1").val(" ");
                $("#person_mobile1").val(" ");
                $("#person_mail_id1").val("");
                $("#person_address1").val("");
            }

        }

        function printAddress(id){
            var queryString = "requester=PRINTAddress&org_office_id="+id;
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


    </script>
</head>
<body onload="checkIsSearch();">
    <div id="blanket" style="display:none;"></div>
    <div id="popUpDiv" style="display:none;">
    </div>
    <table align="center" cellpadding="0" cellspacing="0"  class="main" style="table-layout: auto; ">
        <tr><td><%@include file="/layout/header.jsp" %></td></tr>
        <tr>
            <td><%@include file="/layout/menu.jsp" %> </td>
        <tr>
            <td nowrap>               
                <DIV id="divMain" class="maindiv" >
                    <table width="100%">

                        <tr>
                            <th  align="center" style="border-right: none" nowrap>
                                <form name="searchForm" id="searchForm" action="orgDetailEntryCont.do" method="post" onsubmit="return verifySearch()">
                                    <table class="header_table" width="100%">
                                        <tr align="center">
                                            <td>
                                                <input class="button" align="left" type="button" name="task" id="search" value="New Record" onclick="clearField(); clearFieldDisabled(); makeFieldButtonDisable()">
                                            </td>
                                            <td>                                                
                                                Organization-<input type="text" class="new_input" name="searchOrganisation" id="searchOrganisation" value="${searchOrganisation}" size="40">
                                                <input class="button" type="submit" name="task" id="search" value="Search">                                                
                                                <input class="button" type="button" id="saveUp" name="task" value="Save New Record" disabled  onclick="saveAllRecord()">
                                                default value<input type="checkbox" id="default" name="default" onclick="setDefaultValues(id)">
                                                <input type="hidden" name="isSearch" id="isSearch" value="${isSearch}" >
                                            </td>
                                        </tr>
                                    </table>

                                </form>
                            </th>
                        </tr>
                        <form name="mainForm" id="mainForm"  method="POST" action="orgDetailEntryCont.do" onsubmit="return verify()">
                            <tr class="row" id="message">
                                <c:if test="${not empty message}">
                                    <td bgcolor="${messageBgColor}"><b>Result: ${message}</b></td>
                                </c:if>
                            </tr>
                            <tr >
                                <th class="header_table" style="text-align: center" >
                                    <table width="100%">
                                        <tr >
                                            <th width="30%"></th>
                                            <th align="center" width="40%">
                                                Organization Detail
                                                <input type="hidden" id="organisation_id" name="organisation_id" value="${orgDetail.organisation_id}" >
                                                <input type="hidden" id="org_map_id" name="org_map_id" value="${orgDetail.org_map_id}" >
                                            </th>
                                            <th width="30%" align="right" nowrap>
                                                <input type="button" class="button" name="orgEdit" id="orgEdit" value="Edit" onclick="onOrgEdit();" disabled >
                                                <input type="button" class="button" name="orgSave" id="orgSave" value="Save" onclick="saveOrgMap();" disabled >
                                                <input type="button" class="button" name="orgDelete" id="orgDelete" value="Delete" onclick="deleteOrg();" disabled >
                                            </th>
                                        </tr>
                                    </table>
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <table id="table2" align="center"  border="1"  class="content" width="100%">
                                        <tr>
                                            <th class="heading1">Organization Name</th>
                                            <td>
                                                <input type="text" class="new_input" id="organisation" name="organisation" value="${orgDetail.organisation}" size="30" onkeyup="removeErrorField(id)" disabled>
                                            </td>
                                            <th class="heading1" >Organization Type</th>
                                            <td>
                                                <input type="text" class="new_input" id="org_type_name" name="org_type_name" value="${orgDetail.org_type_name}" size="25" disabled>
                                            </td>
                                            <th class="heading1" >Org Sub Type</th>
                                            <td>
                                                <input type="text" class="new_input" id="org_sub_type" name="org_sub_type" value="${orgDetail.org_sub_type}" size="25"  disabled>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr style="margin-bottom: 5px">
                                <th class="header_table" align="center" >
                                    <table width="100%">
                                        <tr >
                                            <%-- <th width="30%"></th>--%>
                                            <th align="center" width="40%">
                                                Organisation Office Detail
                                                <input type="hidden" id="office_id" name="office_id" value="${orgDetail.office_id}">
                                            </th>
                                            <th width="30%" align="right" nowrap>
                                                <select id="searchOrgOffice" class="dropdown" name="searchOrgOffice" onchange="getOfficeDetail();" style="width: 200px" disabled>
                                                    <option value="0" style="color: red" selected>Select Office</option>
                                                    <c:forEach var="orgOffice" items="${orgDetail.orgOfficeList}">
                                                        <option value="${orgOffice.key}" ${orgOffice.key eq orgDetail.office_id ? 'selected' : ''}>${orgOffice.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="button" class="button" name="orgOfNew" id="orgOfNew" value="New"  onclick="onOrgOfficeNew();" disabled>
                                                <input type="button" class="button" name="orgOfEdit" id="orgOfEdit" value="Edit" onclick="onOrgOfficeEdit();" disabled>
                                                <input type="button" class="button" name="orgOfSave" id="orgOfSave" value="Save" onclick="saveOrgOffice();" disabled>
                                                <input type="button" class="button" name="orgOfDelete" id="orgOfDelete" value="Delete" onclick="deleteOrgOffice();" disabled>
                                                <input type="button" name="print" id="orgDetail.office_id" value="Print" onclick="printAddress(${orgDetail.office_id})">
                                            </th>
                                        </tr>
                                    </table>
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <table id="table3" align="center"  border="1"  class="content" width="100%">
                                        <tr>
                                            <th class="heading1">Office Type</th><td><input type="text" class="new_input" id="office_type" name="office_type" value="${orgDetail.office_type}" size="30" onkeyup="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Office Code</th><td><input type="text" class="input" id="office_code" name="office_code" value="${orgDetail.office_code}" size="30" onkeyup="removeErrorField(id)" disabled></td>                                          
                                                                      </tr>
                                                                      <tr>
                                                                          <th class="heading1">Office Name</th><td><input type="text" class="new_input" id="office_name" name="office_name" value="${orgDetail.office_name}" size="30" onkeyup="removeErrorField(id)" disabled></td>
                                            <th class="heading1">City(Office)</th><td><input type="text" class="new_input" id="office_city" name="office_city" value="${orgDetail.office_city}" size="30" onkeyup="removeErrorField(id)" disabled></td>
              
                                                                      </tr>
                                        <tr>
                                            <th class="heading1">Address1</th><td colspan="5"><input type="text" class="new_input" id="office_address1" name="office_address1" value="${orgDetail.office_address1}" size="90" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                        </tr>
                                        <tr>
                                            <th class="heading1">Address2</th><td colspan="5"><input type="text" class="new_input" id="office_address2" name="office_address2" value="${orgDetail.office_address2}" size="90" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <table id="table3" align="center"  border="1"  class="content" width="70%">
                                        <tr>
                                            <th class="heading1">First Mobile No</th><td><input type="text" class="input" id="office_mobile1" name="office_mobile1" value="${orgDetail.office_mobile1}" size="30" maxlength="10" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Second Mobile No</th><td><input type="text" class="input" id="office_mobile2" name="office_mobile2" value="${orgDetail.office_mobile2}" size="30" maxlength="10" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>

                                        </tr>
                                        <tr>

                                            <th class="heading1">First Email ID</th><td><input type="text" class="input" id="office_mail_id1" name="office_mail_id1" value="${orgDetail.office_mail_id1}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Second Email ID</th><td><input type="text" class="input" id="office_mail_id2" name="office_mail_id2" value="${orgDetail.office_mail_id2}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                        </tr>

                                        <tr>
                                            <th class="heading1">First Landline No</th><td><input type="text" class="input" id="office_landLine1" name="office_landLine1" value="${orgDetail.office_landLine1}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled> </td>
                                            <th class="heading1">Second Landline No</th><td><input type="text" class="input" id="office_landLine2" name="office_landLine2" value="${orgDetail.office_landLine2}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>

                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr  style="margin-bottom: 5px">
                                <th class="header_table" align="center" >
                                    <table width="100%">
                                        <tr >
                                            <%-- <th width="30%"></th>--%>
                                            <th align="center" width="40%">
                                                Key Person Detail
                                                <input type="hidden" id="keyPersonId" name="keyPersonId" value="${orgDetail.keyPersonId}">
                                            </th>
                                            <th width="30%" align="right" nowrap>
                                                <select id="searchPerson" class="dropdown" name="searchPerson" style="width: 200px" disabled>
                                                    <option value="0" style="color: red" selected> Select Person</option>
                                                    <c:forEach var="person" items="${orgDetail.personList}">
                                                        <option value="${person.key}" ${person.key eq orgDetail.keyPersonId ? 'selected' : ''}>${person.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="button" class="button" name="kpOfNew" id="kpNew" value="New" onclick="onPersonNew();" disabled>
                                                <input type="button" class="button" name="kpEdit" id="kpEdit" value="Edit" onclick="onPersonEdit();" disabled>
                                                <input type="button" class="button" name="kpSave" id="kpSave" value="Save"  onclick="savePerson();" disabled>
                                                <input type="button" class="button" name="kpDelete" id="kpDelete" value="Delete"  onclick="deletePerson();" disabled>

                                            </th>
                                        </tr>
                                    </table>
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <table id="table3" align="center" border="1"  class="content" width="100%">
                                        <tr>
                                            <th class="heading1">Employee ID</th><td><input type="text" class="input" id="employeeId" name="employeeId" value="${orgDetail.employeeId}"  size="25" onkeyup="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Person Name</th><td nowrap>
                                             
                                                <select class="dropdown" id="salutation" name="salutation" style="width: 70px" disabled>
                                                    <option style="text-align: center" selected>Mr.</option>
                                                    <option style="text-align: center" ${orgDetail.salutation eq 'Ms.' ? 'selected' : ''}>Ms.</option>
                                                    <option style="text-align: center" ${orgDetail.salutation eq 'Mrs.' ? 'selected' : ''} >Mrs.</option>
                                                </select>
                                                <input type="text" class="new_input" id="key_person" name="key_person" value="${orgDetail.keyperson}" size="30" onkeyup="removeErrorField(id)" disabled></td>
                                             <th class="heading1">Father Name</th><td><input type="text" class="new_input" id="father_name" name="father_name" value="${orgDetail.father_name}"  size="25"  disabled></td>
                                             <th class="heading1">Age</th><td><input type="text" size="10" class="input" id="age" name="age" value="${orgDetail.age}"  size="25"  disabled></td>
                                                  </tr>
                                                  <tr> <th class="heading1">Designation</th><td><input type="text" class="new_input" id="designation" name="designation" value="${orgDetail.designation}"  size="25" onkeyup="removeErrorField(id)" disabled></td>
                                            <th class="heading1" colspan="2">Same As Office Detail <input type="checkbox" name="sameAsAbove" id="sameAsAbove" onchange="copyAsAbove()" disabled></th>
</tr>
                                        <tr>
                                            <th class="heading1">Address1</th><td colspan="3"><input type="text" class="new_input" id="person_address1" name="person_address1" value="${orgDetail.person_address1}" size="60" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                             <th class="heading1">City(Person)</th><td><input type="text" class="new_input" id="person_city" name="person_city" value="${orgDetail.person_city}" size="25" onkeyup="removeErrorField(id)" disabled></td>
                                              </tr>
                                              <tr>
                                            <th class="heading1">Address2</th><td colspan="5"><input type="text" class="new_input" id="person_address2" name="person_address2" value="${orgDetail.person_address2}" size="60" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                              </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <table id="table3" align="center"  border="1"  class="content" width="70%">
                                        <tr>
                                            <th class="heading1">First Mobile No</th><td><input type="text" class="input" id="person_mobile1" name="person_mobile1" value="${orgDetail.person_mobile1}" size="30" maxlength="10" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Second Mobile No</th><td><input type="text" class="input" id="person_mobile2" name="person_mobile2" value="${orgDetail.person_mobile2}" size="30" maxlength="10" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                        </tr>
                                        <tr>
                                            <th class="heading1">First Email ID</th><td><input type="text" class="input" id="person_mail_id1" name="person_mail_id1" value="${orgDetail.person_mail_id1}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Second Email ID</th><td><input type="text" class="input" id="person_mail_id2" name="person_mail_id2" value="${orgDetail.person_mail_id2}" size="30" onkeyup="removeErrorField(id)"  onfocus="removeErrorField(id)" disabled></td>
                                        </tr>
                                        <tr>
                                            <th class="heading1">First Landline No</th><td><input type="text" class="input" id="person_landLine1" name="person_landLine1" value="${orgDetail.person_landLine1}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                            <th class="heading1">Second Landline No</th><td><input type="text" class="input" id="person_landLine2" name="person_landLine2" value="${orgDetail.person_landLine2}" size="30" onkeyup="removeErrorField(id)" onfocus="removeErrorField(id)" disabled></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </form>
                    </table>
                </DIV>

            </td>
        </tr>
    </table>


</body>
</html>
