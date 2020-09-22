<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <script type="text/javascript">

        jQuery(function(){
        $("#type_of_beneficiary").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getTypeOfOccupation"}
            }
        });
              $("#person_name").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getPersonName";},
                 action2: function() { return  $("#emp_code").val();}
            }
            });
           $("#person_name").result(function(event, data, formatted){
            $.ajax({url: "BeneficiaryController?action1=getEmp_code", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#emp_code").val(response_data.trim());
                   }
                   });
            });

            $("#emp_code").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getEmp_code"},
                 action2: function() { return  $("#person_name").val();}
            }
            });
              $("#emp_code").result(function(event, data, formatted){
                   $.ajax({url: "BeneficiaryController?action1=getPersonName", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#person_name").val(response_data.trim());
                   }
                   });
            });
    
  
           $("#searchPersonName").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getSearchPersonName"}
            }
        });
          $("#person_code").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getperson_code"}
            }
        });
    });
       jQuery(function(){
        $("#searchOccupationName").autocomplete("BeneficiaryController", {
            extraParams: {
                action1: function() { return "getSearchOccupationName"}
            }
        });
    });
  jQuery(function(){
           $("#zone").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZone";},
                 action2: function() { return  $("#zone_no").val();}
            }
            });
           $("#zone").result(function(event, data, formatted){
            $.ajax({url: "ShiftDesinationLocationController?action1=getZoneNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#zone_no").val(response_data.trim());
                   }
                   });
            });

            $("#zone_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZoneNo"},
                 action2: function() { return  $("#zone").val();}
            }
            });
              $("#zone_no").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getZone", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#zone").val(response_data.trim());
                   }
                   });
            });

           $("#ward").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWard"},
                action2: function() { return  $("#zone").val();},
                 action3: function() { return  $("#ward_no").val();}
              }
              });
               $("#ward").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getWardNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#ward_no").val(response_data.trim());
                   }
                   });
            });

            $("#ward_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWardNo"},
                 action2: function() { return  $("#ward").val();}
            }
            });
              $("#ward_no").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getWard", data: "action3="+ data +"&q=", success: function(response_data) {
                       $("#ward").val(response_data.trim());
                   }
                   });
            });

            $("#area").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getArea"},
                action2: function() { return  $("#ward").val();},
                action3: function() { return  $("#zone").val();},
                 action4: function() { return  $("#area_no").val();}
           }
            });
           $("#area").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getAreaNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#area_no").val(response_data.trim());
                   }
                   });
            });

          $("#area_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getAreaNo"},
                 action2: function() { return  $("#area").val();}
            }
            });
               $("#area_no").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getArea", data: "action4="+ data +"&q=", success: function(response_data) {
                       $("#area").val(response_data.trim());
                   }
                   });
            });


           $("#location").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getlocation"},
                action2: function() { return  $("#area").val();},
                action3: function() { return  $("#ward").val();},
                action4: function() { return  $("#zone").val();},
                action5: function() { return  $("#location_no").val();}
           }
         });
              $("#location").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getLocationNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#location_no").val(response_data.trim());
                   }
                   });
            });

             $("#location_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getLocationNo"},
                 action2: function() { return  $("#location").val();}
            }
            });
            $("#location_no").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getlocation", data: "action5="+ data +"&q=", success: function(response_data) {
                       $("#location").val(response_data.trim());
                   }
                   });
            });


                      $("#searchZone").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZone";},
                 action2: function() { return  $("#searchZone_no").val();}
            }
            });
           $("#searchZone").result(function(event, data, formatted){
            $.ajax({url: "ShiftDesinationLocationController?action1=getZoneNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchZone_no").val(response_data.trim());
                   }
                   });
            });

            $("#searchZone_no").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getZoneNo"},
                 action2: function() { return  $("#searchZone").val();}
            }
            });
              $("#searchZone_no").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getZone", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchZone").val(response_data.trim());
                   }
                   });
                   });
           $("#searchWardType").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWard"},
                action2: function() { return  $("#searchZone").val();},
                 action3: function() { return  $("#searchWardNo").val();}
              }
              });
               $("#searchWardType").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getWardNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchWardNo").val(response_data.trim());
                   }
                   });
                });

            $("#searchWardNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getWardNo"},
                 action2: function() { return  $("#searchWardType").val();}
            }
            });
              $("#searchWardNo").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getWard", data: "action3="+ data +"&q=", success: function(response_data) {
                       $("#searchWardType").val(response_data.trim());
                   }
                   });
            });

            $("#searchArea").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getArea"},
                action2: function() { return  $("#searchWardType").val();},
                action3: function() { return  $("#searchZone").val();},
                 action4: function() { return  $("#searchAreaNo").val();}
           }
            });
           $("#searchArea").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getAreaNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchAreaNo").val(response_data.trim());
                   }
                   });
            });

          $("#searchAreaNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getAreaNo"},
                 action2: function() { return  $("#searchArea").val();}
            }
            });
               $("#searchAreaNo").result(function(event, data, formatted){
                   $.ajax({url: "ShiftDesinationLocationController?action1=getArea", data: "action4="+ data +"&q=", success: function(response_data) {
                       $("#searchArea").val(response_data.trim());
                   }
                   });
            });

          $("#searchCityName").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getlocation"},
                action2: function() { return  $("#searchArea").val();},
                action3: function() { return  $("#searchWardType").val();},
                action4: function() { return  $("#searchZone").val();},
                action5: function() { return  $("#searchCityNo").val();}
           }
         });
              $("#searchCityName").result(function(event, data, formatted){
                $.ajax({url: "ShiftDesinationLocationController?action1=getLocationNo", data: "action2="+ data +"&q=", success: function(response_data) {
                       $("#searchCityNo").val(response_data.trim());
                   }
                   });
            });

             $("#searchCityNo").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getLocationNo"},
                 action2: function() { return  $("#searchCityName").val();}
            }
            });
            $("#searchCityNo").result(function(event, data, formatted){
                 $.ajax({url: "ShiftDesinationLocationController?action1=getlocation", data: "action5="+ data +"&q=", success: function(response_data) {
                       $("#searchCityName").val(response_data.trim());
                   }
                   });
            });


    });


    function fillColumns(id)
     {

        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 18;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
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

     document.getElementById("beneficiary_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
     document.getElementById("person_name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("emp_code").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
       document.getElementById("no_of_person").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
     document.getElementById("type_of_beneficiary").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
     document.getElementById("occupation_name").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
      document.getElementById("zone").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("zone_no").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
     document.getElementById("ward").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
       document.getElementById("ward_no").value=document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
     document.getElementById("area").value=document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
       document.getElementById("area_no").value=document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
       document.getElementById("location").value=document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
         document.getElementById("location_no").value=document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
         document.getElementById("is").value=document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
           document.getElementById("description").value=document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
         document.getElementById("rfid").value=document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }

        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as_new").disabled = true;
            document.getElementById("delete").disabled = false;
            dodument.getElementById("save").disabled=true;

        }

        $("#message").html("");
    }
          function setDefaultColor(noOfRowsTraversed, noOfColumns) {

        for(var i = 0; i < noOfRowsTraversed; i++) {

            for(var j = 1; j <= noOfColumns; j++) {

                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
        }
        }}
  function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }


     function makeEditable(id) {
        document.getElementById("type_of_beneficiary").disabled = false;
        document.getElementById("occupation_name").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("ward").disabled = false;
        document.getElementById("area").disabled = false;
        document.getElementById("location").disabled = false;
        document.getElementById("zone_no").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("area_no").disabled = false;
        document.getElementById("location_no").disabled = false;
        document.getElementById("person_name").disabled = false;
        document.getElementById("emp_code").disabled = false;
        document.getElementById("no_of_person").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("is").disabled = false;
        document.getElementById("rfid").disabled = false;
        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("beneficiary_id").value=0;




        }
        if(id == 'edit'){
               $("#message").html("");
                document.getElementById("save_as").disabled = false;
                document.getElementById("delete").disabled = false;
                 document.getElementById("save").disabled = false;

            }

        }



function setStatus(id) {

        if(id == 'save'){

            document.getElementById("clickedButton").value = "save";
        }
        else if(id == 'save_as'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'delete'){
            document.getElementById("clickedButton").value = "Delete";
        }

        else
            {}
    }


          function getPdf(){
                var searchOccupationName = $("#searchOccupationName").val();
                var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var searchPersonName = $("#searchPersonName").val();
                var person_code = $("#person_code").val();
                var queryString = "task=generateHSReport&searchOccupationName="+searchOccupationName+"&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&searchPersonName="+searchPersonName+"&person_code="+person_code;
                var url = "BeneficiaryController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
      function getExcel(){
               var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var searchOccupationName = $("#searchOccupationName").val();
                var searchPersonName = $("#searchPersonName").val();
                var person_code = $("#person_code").val();
                var queryString = "task=generateReport&searchOccupationName="+searchOccupationName+"&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&searchPersonName="+searchPersonName+"&person_code="+person_code;
                var url = "BeneficiaryController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
  function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, active=no, dialog=yes, dependent=yes";
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
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>Beneficiary</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                         <form name="form0" action="BeneficiaryController">
                             <table align="center" class="heading1">
                    
                           <tr>
                             <th> Zone Name</th>
                             <td><input type="text" class="new_input" name="searchZone" size="12" id="searchZone"  value="${searchZone}"/></td>
                             <th> Zone No</th>
                             <td><input type="text" class="input" name="searchZone_no" size="5" id="searchZone_no"  value="${searchZone_no}"/></td>
                             <th>Ward Name</th>
                             <td>  <input class="new_input" type="text" id="searchWardType" name="searchWardType" value="${searchWardType}" size="12" > </td>
                             <th> Ward No </th>
                             <td> <input class="input" type="text" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" size="5" ></td>
                           </tr>
                           <tr>
                               <th>Area Name</th>
                               <td><input class="new_input" type="text" name="searchArea" size="12" id="searchArea" value="${searchArea}"></td>
                               <th>  Area No</th>
                               <td> <input  class="input" type="text" name="searchAreaNo" size="5" id="searchAreaNo" value="${searchAreaNo}"></td>
                               <th>City Location</th>
                               <td><input class="new_input" type="text" id="searchCityName" name="searchCityName" value="${searchCityName}" size="12" ></td>
                               <th>City Location No</th>
                               <td><input class="input" type="text" id="searchCityNo" name="searchCityNo" value="${searchCityNo}" size="5" ></td>
                             </tr>
                          <tr>
                            <th>Person Name</th>
                            <td><input class=" new_input" type="text" id="searchPersonName" name="searchPersonName" value="${searchPersonName}" size="12" ></td>
                                <th>Person Code</th>
                            <td><input class="input" type="text" id="person_code" name="person_code" value="${person_code}" size="8" ></td>
                            <th>Occupation Name</th>
                            <td><input class=" new_input" type="text" id="searchOccupationName" name="searchOccupationName" value="${searchOccupationName}" size="12" ></td>
                          </tr>
                          <tr align="center">
                              <td colspan="8">
                                  <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                  <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                  <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getPdf()">
                                  <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()">
                              </td>
                          </tr>
                             </table>
                         </form>
      <div class="content_div">
       <form name="form1"  action="BeneficiaryController">


        <TABLE BORDER="1" align="center" cellpadding="5%"  class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <TH class="heading">S.no</TH>
                 <TH class="heading">Person Name</TH>
                 <TH class="heading" style="white-space: normal">No Of Person</TH>
                <TH class="heading" style="white-space: normal">Type Of Occupation</TH>
                <TH class="heading" style="white-space: normal">Occupation Name</TH>
                <TH class="heading">Zone Name</TH>
                <TH class="heading">Ward Name</TH>
                <TH class="heading">Area Name</TH>
                 <TH class="heading">City Location Name</TH>
                 <TH class="heading" style="white-space: normal">Is Residencial</TH>
                <TH class="heading">Description</TH>
                <TH class="heading">RF-ID</TH>

            <c:forEach var="beneficiary" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)"> ${beneficiary.beneficiary_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.person_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="display: none">${beneficiary.person_code}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.no_of_person}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.type_of_beneficiary}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.occupation_name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.zone}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${beneficiary.zone_no}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.ward}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${beneficiary.ward_no}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.area}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${beneficiary.area_no}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.location}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${beneficiary.location_no}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.is_residencial}</td>
          
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.description}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${beneficiary.rfid}</td>
            </tr>
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
                 <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                 <input type="hidden"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                 <input  type="hidden" name="searchOccupationName" value="${searchOccupationName}">
                 <input  type="hidden" name="searchWardType" value="${searchWardType}">
                 <input  type="hidden" name="searchCityName" value="${searchCityName}">
                 <input  type="hidden" name="searchZone" value="${searchZone}">
                 <input  type="hidden" name="searchWardType" value="${searchWardNo}">
                 <input  type="hidden" name="searchCityName" value="${searchCityNo}">
                 <input  type="hidden" name="searchCityName" value="${searchCity}">
                <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
                <input  type="hidden" name="searchZone" value="${searchZone_no}">
                <input  type="hidden" name="searchPersonName" value="${searchPersonName}">
                 <input  type="hidden" name="person_code" value="${person_code}">
        </form>
                        </div>
        <br>
        <br>

                      <form  action="BeneficiaryController" method="post">

                           <table  align="center"  class="content" cellpadding="2%" border="1">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="beneficiary_id" name="beneficiary_id" value="" ></tr>
                                <tr>
                                 <th class="heading1"> Person Name </th>
                                 <td><input type="text" class="new_input" id="person_name"  name="person_name" value="" disabled>
                                      <input type="text"  id="emp_code"  size="5"  name="emp_code" placeholder='code'  value="" disabled></td>

                                 <th class="heading1">No Of Person </th>
                                 <td><input type="text"  id="no_of_person"  name="no_of_person" value="" disabled></td>
                              </tr>
                                 <tr>

                                 <th class="heading1">Type Of Occupation </th>
                                 <td><input type="text" class="new_input" id="type_of_beneficiary"  name="type_of_beneficiary" value="" disabled></td>
                            
                                 <th class="heading1"> Occupation Name </th>
                                 <td><input type="text" class="new_input" id="occupation_name"  name="occupation_name" value="" disabled></td>
                              </tr>
                                     <tr>
                                      <th class="heading1" >Zone Name</th>
                                      <td><input type="text" class="new_input" id="zone" size="10"  name="zone" value="" disabled>
                                      <input type="text"  id="zone_no"  size="10"  name="zone_no" placeholder='zone_no'  value="" disabled>
                                      </td>
                                    <th class="heading1"  >Ward Name</th>
                              <td><input type="text"  id="ward" class="new_input" name="ward" size="10"   value="" disabled>
                                  <input type="text"  id="ward_no" name="ward_no"  size="10" placeholder='ward_no'  value="" disabled>
                              </td>
                              </tr>
                              <tr>
                               <th class="heading1">Area Name</th>
                              <td><input type="text"  id="area" class="new_input" size="10"   name="area" value="" disabled>
                                  <input type="text"  id="area_no"  size="10"   name="area_no" placeholder='area_no' value="" disabled>
                              </td>
                              <th class="heading1">City Location Name</th>
                              <td><input type="text"  id="location" class="new_input" size="10"  name="location" value="" disabled>
                                  <input type="text"  id="location_no"  size="10"   name="location_no" placeholder='location_no' value="" disabled>
                              </td>
                              </tr>
                       
                              <tr>
                                                <th class="heading1">Is Residencel</th>
                                                <td><select name="is" id="is" disabled>
                                                        <option value="Y">Y</option>
                                                        <option value="N">N</option>
                                                    </select>
                            
                                 <th class="heading1"> Description</th>
                                 <td><input type="text" class="new_input" id="description"  name="description" value="" disabled></td>
                              </tr>
                              <tr>
                                   <th class="heading1"> RF-ID</th>
                                   <td><input type="text" class="input" id="rfid"  name="rfid" value="" disabled></td>
                              </tr>

             <tr>
                 <td align="center" colspan="4" >
           <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >
           <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
           <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
           <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
            <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
            </td>
            </tr>
            </table>
             <input type="hidden" id="clickedButton" value="">
            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
             <input  type="hidden" name="searchOccupationName" value="${searchOccupationName}">
            <input  type="hidden" name="searchWardType" value="${searchWardType}">
             <input  type="hidden" name="searchCityName" value="${searchCityName}">
            <input  type="hidden" name="searchZone" value="${searchZone}">
             <input  type="hidden" name="searchWardType" value="${searchWardNo}">
             <input  type="hidden" name="searchCityName" value="${searchCityNo}">
             <input  type="hidden" name="searchCityName" value="${searchCity}">
             <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
             <input  type="hidden" name="searchZone" value="${searchZone_no}">
             <input  type="hidden" name="searchPersonName" value="${searchPersonName}">
             <input  type="hidden" name="person_code" value="${person_code}">
          </form>
  <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                   </table>

            </DIV>
         </td>
       
      </table>
    </body>
</html>
