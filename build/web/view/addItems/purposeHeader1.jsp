<%--
    Document   : purposeHeader
    Created on : Sep 19, 2016, 10:51:51 AM
    Author     : Manpreet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purpose Header JSP Page</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

  var CheckUncheck;
         $(document).ready(function(){
            //  CheckUncheck = "N";
            //  alert("hi");
            //  alert(CheckUncheck);
              document.getElementById("add_parent").value = "N";
             // alert(document.getElementById("add_parent").value);
          });


           jQuery(function(){

                $("#parentHeaderName").autocomplete("PurposeHeaderController2", {
            extraParams: {
                action1: function() { return "getParentHeaderName" }
            }
              });

               $("#childHeaderName").autocomplete("PurposeHeaderController2", {
            extraParams: {
                action1: function() { return "getChildHeaderName" },
                action2: function() { return $("#parentHeaderName").val()}
            }
              });

               $("#searchheader_index").autocomplete("PurposeHeaderController2", {
            extraParams: {
                action1: function() { return "getSearchIndex" }
            }
              });

               $("#searchSub_header_name").autocomplete("PurposeHeaderController2", {
            extraParams: {
                action1: function() { return "getSearchSubHeader" }
            }
              });

               $("#searchParent_header_name").autocomplete("PurposeHeaderController2", {
            extraParams: {
                action1: function() { return "getSearchParentHeader" }
            }
              });

            });

            function makeEditable1() {
                var parentHeaderName = $("#parentHeaderName").val();
                var childHeaderName = $("#childHeaderName").val();
                //var childHeaderName = $('#'+id).val();
                //alert(parentHeaderName);
                if(myLeftTrim(parentHeaderName).length == 0 ){
                    //alert("if block");
                    document.getElementById("getSubHeader").disabled = true;
                    //document.getElementById("childHeaderName").value = "";
                    if(myLeftTrim(childHeaderName).length != 0){
                    $("#message").html("<td colspan='2' bgcolor='coral'><b>Please Select Parent Header Name first</b></td>");
                   // $('#'+id).val('');
                      $("#childHeaderName").val('');
                   // alert("Please Select Parent Header Name first");
                    }
                } else if(myLeftTrim(parentHeaderName).length != 0 ) {
                    //$("#message").val('');

                    document.getElementById("getSubHeader").disabled = false;
                    //alert("else block");
                }
            }

            function makeEditable(id) {
                document.getElementById("getParentHeader").disabled = false;
                document.getElementById("parentHeaderName").disabled = false;
                document.getElementById("newHeaderName").disabled = false;
                document.getElementById("add_parent").disabled = false;
                document.getElementById("childHeaderName").disabled = false;
                //document.getElementById("active").disabled = false;
                document.getElementById("description").disabled = false;
                document.getElementById("update").disabled = true;
                document.getElementById("delete").disabled = true;
                document.getElementById("save").disabled = true;
                if(id == 'new') {
                    $("#message").html("");
                    document.getElementById("delete").disabled = true;
                    document.getElementById("update").disabled = true;
                    document.getElementById("save").disabled = false;
              setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("parentHeaderName").focus();
                    document.getElementById("node_id").value ='';
           }
                /*if(id == 'edit') {
                    document.getElementById("delete").disabled = false;
                }*/
                //document.getElementById("save").disabled = false;
            }

            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Add Header";
                }
               else if(id == 'delete') {
                    document.getElementById("clickedButton").value = "Delete Header";
                }
                else if(id == 'update') {
                    document.getElementById("clickedButton").value = "Update Header";
                }
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
                var noOfColumns = 7;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("header_node_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
               // document.getElementById("parentHeaderName").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("HeaderName").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
              //  document.getElementById("active").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
              //  document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;

               // makeEditable('');
                $("#message").html("");
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
                if(document.getElementById("clickedButton").value == 'Add Header') {

                   var parentHeaderName = document.getElementById("parentHeaderName").value;
                    if(myLeftTrim(parentHeaderName).length == 0) {
                       // alert("Parent Header Name is required");
                        $("#message").html("<td colspan='2' bgcolor='red'><b>Parent Header Name is required</b></td>");
                        document.getElementById("parentHeaderName").focus();
                        return false; // code to stop from submitting the form2.
                    }

                if(document.getElementById("add_parent").value == 'N') {
                   var newHeaderName = document.getElementById("newHeaderName").value;
                    if(myLeftTrim(newHeaderName).length == 0) {
                       // alert("Sub Header Name is required");
                        $("#message").html("<td colspan='2' bgcolor='red'><b>Sub Header Name is required</b></td>");
                        document.getElementById("newHeaderName").focus();
                        return false; // code to stop from submitting the form2.
                    }
                 } else{
                      var childHeaderName = document.getElementById("childHeaderName").value;
                    if(myLeftTrim(childHeaderName).length == 0) {
                       // alert("Existing Sub Header is required");
                        $("#message").html("<td colspan='2' bgcolor='red'><b>Existing Sub Header is required</b></td>");
                        document.getElementById("childHeaderName").focus();
                        return false; // code to stop from submitting the form2.
                    }
                  }

                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }

                } else if(document.getElementById("clickedButton").value == 'Update Header') {
                    result = confirm("Are you sure you want to update this record?");
                  }
                else if(document.getElementById("clickedButton").value == 'Delete Header') {
                    result = confirm("Are you sure you want to delete this record?");
                  }
                    return result;
               }

                function changeFormFields_1(id){
                //alert(id);
                var CheckUncheckValue;
                if(document.getElementById(id).checked==true){
                    $('#'+id).val('Y');
                   CheckUncheckValue = $('#'+id).val();
                } else{
                    $('#'+id).val('N');
                   CheckUncheckValue = $('#'+id).val();
                }
               // alert(CheckUncheckValue);
                if(CheckUncheckValue == "Y"){
                   // alert("hi");
                    document.getElementById("update").disabled = false;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save").disabled = true;
                    document.getElementById("new").disabled = true;

                     document.getElementById("HeaderRow").style.display="table-row";
                     document.getElementById("updateHeaderRow").style.display="table-row";

                      document.getElementById("addHeaderRow").style.display="none";
                      document.getElementById("deleteHeaderRow").style.display="none";
                      document.getElementById("parentHeaderRow").style.display="none";
                      document.getElementById("descriptionRow").style.display="none";
                    if($("#add_parent").val() == "Y"){
                      document.getElementById("childHeaderRow").style.display="none";
                    }else{
                      document.getElementById("subHeaderRow").style.display="none";
                    }
                } else if(CheckUncheckValue == "N"){
                   // alert("bye");
                    document.getElementById("update").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save").disabled = true;
                    document.getElementById("new").disabled = false;

                      document.getElementById("HeaderRow").style.display="none";
                      document.getElementById("updateHeaderRow").style.display="table-row";

                      document.getElementById("addHeaderRow").style.display="table-row";
                      document.getElementById("deleteHeaderRow").style.display="table-row";
                      document.getElementById("parentHeaderRow").style.display="table-row";
                      document.getElementById("descriptionRow").style.display="table-row";
                    if($("#add_parent").val() == "Y"){
                      document.getElementById("childHeaderRow").style.display="table-row";
                    }else{
                      document.getElementById("subHeaderRow").style.display="table-row";
                    }

                }

            }

            function changeFormFields(id){
                //alert(id);
                var CheckUncheckValue;
                if(document.getElementById(id).checked==true){
                    $('#'+id).val('Y');
                   CheckUncheckValue = $('#'+id).val();
                } else{
                    $('#'+id).val('N');
                   CheckUncheckValue = $('#'+id).val();
                }
               // alert(CheckUncheckValue);
                if(CheckUncheckValue == "Y"){
                   // alert("hi");
                    document.getElementById("delete").disabled = false;
                    document.getElementById("update").disabled = true;
                    document.getElementById("save").disabled = true;
                    document.getElementById("new").disabled = true;

//                    $(".showhide").show();
//                    $(".HeaderName").show();
                     document.getElementById("HeaderRow").style.display="table-row";
                     document.getElementById("deleteHeaderRow").style.display="table-row";

//                    document.getElementById("add_parent").style.display="none";
//                    document.getElementById("add_parent_th").style.display="none";
//                    document.getElementById("parentHeaderName").style.display="none";
//                    document.getElementById("parentHeaderName_th").style.display="none";
//                    document.getElementById("getParentHeader").style.display="none";
//                    document.getElementById("description").style.display="none";
//                    document.getElementById("description_th").style.display="none";
                      document.getElementById("addHeaderRow").style.display="none";
                      document.getElementById("updateHeaderRow").style.display="none";
                      document.getElementById("parentHeaderRow").style.display="none";
                      document.getElementById("descriptionRow").style.display="none";
                    if($("#add_parent").val() == "Y"){
//                    document.getElementById("childHeaderName").style.display="none";
//                    document.getElementById("childHeaderName_th").style.display="none";
//                    document.getElementById("getSubHeader").style.display="none";
                      document.getElementById("childHeaderRow").style.display="none";
                    }else{
                      document.getElementById("subHeaderRow").style.display="none";
//                    document.getElementById("newHeaderName").style.display="none";
//                    document.getElementById("newHeaderName_th").style.display="none";
                    }
                } else if(CheckUncheckValue == "N"){
                   // alert("bye");
                    document.getElementById("delete").disabled = true;
                    document.getElementById("update").disabled = true;
                    document.getElementById("save").disabled = true;
                    document.getElementById("new").disabled = false;

//                    $(".showhide").hide();
//                    $(".HeaderName").hide();
                      document.getElementById("HeaderRow").style.display="none";
                      document.getElementById("deleteHeaderRow").style.display="table-row";

//                    document.getElementById("add_parent").style.display="table-column";
//                    document.getElementById("add_parent_th").style.display="table-header-group";
//                    document.getElementById("parentHeaderName").style.display="table-column";
//                    document.getElementById("parentHeaderName_th").style.display="table-header-group";
//                    document.getElementById("getParentHeader").style.display="table-column";
//                    document.getElementById("description").style.display="table-column";
//                    document.getElementById("description_th").style.display="table-header-group";
                      document.getElementById("addHeaderRow").style.display="table-row";
                      document.getElementById("updateHeaderRow").style.display="table-row";
                      document.getElementById("parentHeaderRow").style.display="table-row";
                      document.getElementById("descriptionRow").style.display="table-row";
                    if($("#add_parent").val() == "Y"){
//                    document.getElementById("childHeaderName").style.display="table-column";
//                    document.getElementById("childHeaderName_th").style.display="table-header-group";
//                    document.getElementById("getSubHeader").style.display="table-column";
                      document.getElementById("childHeaderRow").style.display="table-row";
                    }else{
                      document.getElementById("subHeaderRow").style.display="table-row";
//                    document.getElementById("newHeaderName").style.display="table-column";
//                    document.getElementById("newHeaderName_th").style.display="table-header-group";
                    }

                }

            }

            function singleCheckUncheckForNew(id){
                if(document.getElementById(id).checked==true){
                   // alert("Y");
                    $('#'+id).val('Y');
                   // alert($('#'+id).val());
                   document.getElementById("delete").disabled = true;
                 }else{
                   //alert("N");
                    $('#'+id).val('N');
                 }

            jQuery(function(){
                $(document).ready(function(){
                  $('#'+id).change(function(){
                     // alert("N");
                    CheckUncheck = $('#'+id).val();
                   // alert(CheckUncheck);
                    if(CheckUncheck == "Y"){
                       // alert("hi");
//                        $(".showhide").show();
//                        $(".childHeaderName").show();

                        document.getElementById("childHeaderRow").style.display="table-row";
                        document.getElementById("subHeaderRow").style.display="none";
                        document.getElementById("HeaderRow").style.display="none";
                       // document.getElementById("newHeaderName").style.visibility="collapse";
                      // document.getElementById("newHeaderName").setAttribute("autocomplete", "true");
                    }else if(CheckUncheck == "N"){
//                        $(".showhide").hide();
//                        $(".childHeaderName").hide();

                        document.getElementById("childHeaderRow").style.display="none";
                        document.getElementById("subHeaderRow").style.display="table-row";
                        document.getElementById("HeaderRow").style.display="none";
                      // document.getElementById("newHeaderName").setAttribute("autocomplete", "false");
                      }
                   });
                });
             });

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

            function GetItemPDF(){
                //alert("pdf yes");
//                var item_already =$("option:selected", $("#item_already")).val();
                var searchheader_index = $("#searchheader_index").val();
                var searchSub_header_name= $("#searchSub_header_name").val();
                var searchParent_header_name= $("#searchParent_header_name").val();
                var queryString = "requester=PRINT&pdfType=sort&searchheader_index="+searchheader_index+"&searchSub_header_name="+searchSub_header_name+"&searchParent_header_name="+searchParent_header_name;
                var url = "PurposeHeaderController2?" + queryString;
                popupwin = openPopUp(url, "Header List", 595, 750);
            }

        </script>
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>

            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                               <tr><td>
                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <div class="block1" style="width: 450px">
                                            <div><h1> Purpose Header </h1></div>
                                        </div>
                                    </div>
                                </td>
                               </tr>

                               <tr>
                                   <td>
                               <form name="form0" method="POST" action="PurposeHeaderController2">
                                   <table align="center">
                                       <tr align="center"><td colspan="4">
<!--                                                <td colspan="6" class="heading">-->
<!--                                                    <div class="block2" style="width: 700px">-->
                                                   <span class="heading1">Sub Header Index</span>
                                                   <input class="input" type="text" id="searchheader_index" name="searchheader_index" value="${headerindex}" size="25">
                                                   <span class="heading1">Sub Header Name</span>
                                                   <input class="input" type="text" id="searchSub_header_name" name="searchSub_header_name" value="${subheadername}" size="25">
                                                </td></tr>
                                                <tr align="center"><td colspan="4">
                                                   <span class="heading1">Parent Header Name</span>
                                                   <input class="input" type="text" id="searchParent_header_name" name="searchParent_header_name" value="${parentheadername}" size="25">
                                                   <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                   <input type="submit" class="button" name="task" id="showAllRecords" value="Show All Records">
                                                   <input class="button"type="button" id="PDF"name="PDF"value="PDF" onclick="GetItemPDF()" >
<!--                                                </div>-->
<!--                                                </td>-->
                                                </td></tr>
                                   </table>
                               </form>
                                   </td>
                               </tr>

                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="PurposeHeaderController2">
                                        <DIV class="content_div">
                                            <table border="1" id="table1" align="center"  class="content" width="380">
                                                <tr>
                                                    <th class="heading" style="display: none"><!-- Node ID --></th>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading" >Sub Header Index</th>
                                                    <th class="heading" >Sub Header Name</th>
                                                    <th class="heading" >Parent Header Name</th>
                                                    <th class="heading" style="display: none">Active</th>
                                                    <th class="heading" style="display: none">Description</th>
                                                </tr>
                                                <c:forEach var="PurposeHeader" items="${requestScope['PurposeHeaderList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                       <td id="t1c${IDGenerator.uniqueID}" style="display: none" onclick="fillColumns(id)">${PurposeHeader.node_id}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${PurposeHeader.index_no}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${PurposeHeader.tree_node_name}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${PurposeHeader.node_parent_name}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center" style="display: none">${PurposeHeader.tree_active}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center" style="display: none">${PurposeHeader.tree_remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="4">
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
                                                    </td> </tr>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="node_id" name="node_id" value="" disabled>
                                                <input type="hidden" name="searchheader_index" value="${headerindex}">
                                                <input type="hidden" name="searchSub_header_name" value="${subheadername}">
                                                <input type="hidden" name="searchParent_header_name" value="${parentheadername}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="PurposeHeaderController2" onsubmit="return verify()">
                                        <table border="0" id="table2" align="center" class="reference" width="380">
                                            <tr><td colspan="2"></td></tr>
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr id="updateHeaderRow">
                                                <th class="heading1">Update Header</th><td><input class="" type="checkbox" id="update_header" name="update_header" value="" onclick="changeFormFields_1(id)" ></td>
                                            </tr>

                                            <tr id="deleteHeaderRow">
                                                <th class="heading1">Delete Header</th><td><input class="" type="checkbox" id="delete_header" name="delete_header" value="" onclick="changeFormFields(id)" ></td>
                                            </tr>

                                            <tr id="HeaderRow" style="display: none"> <%-- class="showhide" --%>
                                                <th class="heading1" id="HeaderName_th">Header Name</th>
                                                <td>
                                                <input class="input" type="text" id="HeaderName" name="HeaderName" size="25"  value="" >
                                                <input type="hidden" id="header_node_id" name="header_node_id" value="">
                                                <input type="button" id="getHeader" name="getHeader" value="Get Header" onclick="getNode('header');" >
                                                </td>
                                            </tr>

                                            <tr id="addHeaderRow">
                                                <th class="heading1" id="add_parent_th">Is Sub Header Exists</th><td><input class="" type="checkbox" id="add_parent" name="add_parent" value="" onclick="singleCheckUncheckForNew(id)"  disabled></td>
                                            </tr>

                                            <tr id="parentHeaderRow">
                                                <th class="heading1" id="parentHeaderName_th">Parent Header Name</th><td>
                                                <input class="input" type="text" id="parentHeaderName" name="parentHeaderName" size="25"  value="" onkeyup="makeEditable1();" onblur="makeEditable1();" disabled>
                                                <input type="button" id="getParentHeader" name="getParentHeader" value="Get Parent Header" onclick="getNode('parent');" onblur="makeEditable1();" disabled>
                                                <input type="hidden" id="node_id" name="node_id" value=""></td>
                                            </tr>

                                            <tr id="childHeaderRow" style="display: none"> <%-- class="showhide" --%>
                                                <th class="heading1" id="childHeaderName_th">Existing Sub Header</th><td>
                                                    <input class="input" type="text" id="childHeaderName" name="childHeaderName" size="25"  value="" onkeyup="makeEditable1();" onfocus="makeEditable1();" disabled> <%-- onkeypress="makeEditable1();" --%>
                                                <input type="hidden" id="child_node_id" name="child_node_id" value="">
                                                <input type="button" id="getSubHeader" name="getSubHeader" value="Get Sub Header" onclick="getNode('child');" disabled></td>
                                            </tr>

                                            <tr id="subHeaderRow">
                                                <th class="heading1">Sub Header Name</th>
                                                <td><input class="input" type="text" id="newHeaderName" name="newHeaderName" size="35" value="" autocomplete="false" disabled></td>
                                            </tr>
                                            <tr id="descriptionRow">
                                                <%--  <th class="heading1">Active</th><td><input class="" type="checkbox" id="active" name="active" value="" onclick="singleCheckUncheckForNew(id)" disabled></td>  --%>
                                                <th class="heading1" id="description_th">Description</th><td><input class="input" type="text" id="description" name="description" size="35" value="" disabled></td>
                                            </tr>


                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="save" value="Add Header" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete Header" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="update" value="Update Header" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                           <!--  <input  type="hidden" id="node_id" name="node_id" value="" disabled>-->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
<!--                                            <input type="hidden" id="add_parent" name="add_parent" value="">-->
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" name="searchheader_index" value="${headerindex}">
                                            <input type="hidden" name="searchSub_header_name" value="${subheadername}">
                                            <input type="hidden" name="searchParent_header_name" value="${parentheadername}">
                                        </table>
                                    </form>
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
