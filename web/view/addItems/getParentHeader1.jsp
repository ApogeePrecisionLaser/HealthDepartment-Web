<%--
    Document   : getParentHeader
    Created on : Sep 28, 2016, 3:46:28 PM
    Author     : Manpreet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>get Parent Header JSP Page</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

          jQuery(function(){
              $("#searchheader_index").autocomplete("GetParentHeaderController2", {
            extraParams: {
                action1: function() { return "getIndex" }
                   }
              });

//           $("#searchheader_name").autocomplete("getParentHeaderCont.do", {
//            extraParams: {
//                action1: function() { return "getsection" }
//
//            }
//              });

           });

                $("#searchBySerialInput").result(function(event, data, formatted){
                    //  alert("gfg");
                    //$("#selectTable").trigger('reset');
                    document.getElementById("form0div").style.visibility = "hidden";
                    // document.getElementById('form0').reset();
                    var serialInput= $("#searchBySerialInput").val();
                    // alert("kaam avsaye karuga " +serialInput);
                    $.ajax({url: "getEstimateItemsPopCont", async: false,data: "task=getitemvalue&serialInput="+serialInput, success: function(response_data) {
                            var arr = response_data.split("&#");
                            // alert("return with data")
                            if(arr.length>1){
                                var table = document.getElementById('search_item_data');
                                var rowCount=table.rows.length;
                                // alert("no of rows"+rowCount);
                                if(rowCount>1){
                                    for(var i=1;i<rowCount;i++){
                                        $("#searach_data_row"+i).remove();
                                    }
                                }
                                for( var i = 0; i < arr.length; i++) {
                                    rowCount = table.rows.length;
                                    var row = table.insertRow(rowCount);

                                    row.id="searach_data_row"+rowCount;
                                    var cell1 = row.insertCell(0);
                                    cell1.innerHTML=arr[i];
                                    // alert(arr[i]);
                                    i++;
                                    var cell2=row.insertCell(1);
                                    cell2.innerHTML=arr[i];
                                    cell2.setAttribute("style","white-space: normal");
                                    cell2.style.width="10%";
                                    //alert(arr[i]);
                                    // style="white-space: normal"width="50%"
                                }
                            }
                            document.getElementById("search_item_table").style.visibility = "visible";
                            //                            $("#innner").html(response_data);
                            //$("#item_name_value").val(response_data);
                        }
                    });
                    //                    $.ajax({url: "getEstimateItemsPopupCont", async: false,data: "task=checkGrandChildExist&serialInput="+serialInput, success: function(response_data) {
                    //                            var status=response_data.trim();
                    //                            if(status=='YES'){
                    //                                //code for disabling search button
                    //                                $("#task").attr('disabled','disabled');
                    //
                    //                            }
                    //                            else{
                    //                                $("#task").removeAttr('disabled');
                    //                            }
                    //                        }
                    //
                    //
                    //                    });
                });



            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

         function getSectionName(id, node){
             var task = $("#task").val();
             if(task == "child"){
                 opener.document.getElementById("child_node_id").value = id;
                 opener.document.getElementById("childHeaderName").value = node;
             }else if(task == "parent") {
                 opener.document.getElementById("node_id").value = id;
                 opener.document.getElementById("parentHeaderName").value = node;
             }else if(task == "header") {
                 opener.document.getElementById("header_node_id").value = id;
                 opener.document.getElementById("HeaderName").value = node;
             }else if(task == "sections") {
                 opener.document.getElementById("section_node_id").value = id;
                 opener.document.getElementById("section").value = node;
             }else if(task == "parent1") {
                 opener.document.getElementById("node_id").value = id;
                 opener.document.getElementById("organization_name").value = node;
             }
             window.close();
         }

        </script>
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->

            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                                  <tr>
                    <td align="" >
                        <table width="700" class="header_table">
                            <tr>
                                <td  align="center"    >
                                    Header List
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="">
                                <form name="form1" align="center" action="GetParentHeaderController2"   margin-bottom:200 >
                                    <table align="center"   class="content" width="600">
                                   <tr align="center">
                                       <td colspan="3" class="heading"><div class="block2" style="width: 700px">


                                                   <span class="heading1">Header Index</span>
                                                   <input class="input" type="text" id="searchheader_index" name="searchheader_index" value="${headerindex}" size="30">
                                                   <span class="heading1">Header Name</span>
                                                   <input class="input" type="text" id="searchheader_name" name="searchheader_name" value="${headername}" size="30">
                                                   <input class="button" type="submit" name="task1" id="searchIn" value="Search">
                                                   <input class="" type="hidden" name="task" id="task" value="${task}">
                                                   <input class="" type="hidden" name="view" id="view" value="${view}">

                                                     </div>
                                    </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <tr  align="center">
                                                    <th class="heading" style="display: none"><!-- Node ID --></th>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading" >Header Index</th>
                                                    <th class="heading" style="width: 600px" colspan="3" >Header Name</th>
                                                </tr>
                                                <c:forEach var="PurposeHeader" items="${requestScope['PurposeHeaderList']}" varStatus="loopCounter">
                                                    <tr align="center" class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                       <td id="t1c${IDGenerator.uniqueID}" style="display: none" >${PurposeHeader.node_id}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}"  align="center"><input type="radio" name="getSection" onclick="getSectionName('${PurposeHeader.node_id}', '${PurposeHeader.tree_node_name}');">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}" align="center">${PurposeHeader.index_no}</td>
                                                       <td id="t1c${IDGenerator.uniqueID}" colspan="3"   align="center" style="white-space: normal">${PurposeHeader.tree_node_name}</td>
                                                    </tr>
                                                </c:forEach>

                                                    <tr><td>
                                                    <input type="hidden" id="parentHeaderName" name="parentHeaderName" size="25"  value="${parentHeaderName}" >
                                                    </td></tr>
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
