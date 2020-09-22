

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
        <title>Shift</title>

 <script type="text/javascript">
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


    jQuery(function(){
        $("#designation").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getDesignation"}
            }
        });
    });
      jQuery(function(){
        $("#shift_type").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getShiftType"}
            }
        });
    });
         jQuery(function(){
        $("#search_location").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getLocation"}
            }
        });
    });
    jQuery(function(){
        $("#search_designation").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getDesignation"}
            }
        });
    });
      jQuery(function(){
        $("#search_shift_type").autocomplete("ShiftDesinationLocationController", {
            extraParams: {
                action1: function() { return "getShiftType"}
            }
        });
    });
    function fillColumns(id)
     {
         
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 16;
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
     
        document.getElementById("map_id1").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("shift_type").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("designation").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("location_type").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("zone").value=document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("zone_no").value=document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("ward").value=document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("ward_no").value=document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("area").value=document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("area_no").value=document.getElementById(t1id +(lowerLimit+10)).innerHTML;
        document.getElementById("location").value=document.getElementById(t1id +(lowerLimit+11)).innerHTML;
        document.getElementById("location_no").value=document.getElementById(t1id +(lowerLimit+12)).innerHTML;
        document.getElementById("no_of_person").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        document.getElementById("map_id2").value=document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        changeStatus();
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }
        
        document.getElementById("edit").disabled = false;

        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as_new").disabled = true;
            document.getElementById("delete").disabled = false;
            document.getElementById("update").disabled = false;
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

        document.getElementById("shift_type").disabled = false;
        document.getElementById("location_type").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("ward").disabled = false;
        document.getElementById("area").disabled = false;
        document.getElementById("location").disabled = false;
        document.getElementById("zone_no").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("area_no").disabled = false;
        document.getElementById("location_no").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("no_of_person").disabled = false;
         document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("update").disabled =true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("map_id1").value=0;




        }
        if(id == 'edit'){
               $("#message").html("");
                document.getElementById("save_as").disabled = false;
                document.getElementById("delete").disabled = false;
                 document.getElementById("save").disabled = false;
                 document.getElementById("update").disabled = false;
            }

        }

function changeStatus(){
      var location_type =$("#location_type").val();
            if(location_type=='select'){
           $("#zone").hide();
           $("#zone_no").hide();
           $("#ward").hide();
           $("#area").hide();
           $("#location").hide();
           $("#ward_no").hide();
           $("#area_no").hide();
           $("#location_no").hide();
       }
       if(location_type=='zone'){
           $("#zone").show();
           $("#zone_no").show();
           $("#ward").hide();
           $("#area").hide();
           $("#location").hide();
           $("#ward_no").hide();
           $("#area_no").hide();
           $("#location_no").hide();
       }
          if(location_type=='ward'){
           $("#zone").show();
           $("#ward").show();
           $("#zone_no").show();
           $("#ward_no").show();
           $("#area").hide();
           $("#location").hide();
           $("#area_no").hide();
           $("#location_no").hide();
       }
          if(location_type=='area'){
           $("#zone").show();
           $("#ward").show();
           $("#area").show();
           $("#zone_no").show();
           $("#ward_no").show();
           $("#area_no").show();
           $("#location").hide();
           $("#location_no").hide();
       }
          if(location_type=='city_location'){
           $("#zone").show();
           $("#ward").show();
           $("#area").show();
           $("#location").show();
           $("#zone_no").show();
           $("#ward_no").show();
           $("#area_no").show();
           $("#location_no").show();
       }
}
function Status(){
      var location_type =$("#searchlocation_type").val();
            if(location_type=='select'){
           $("#searchZone").hide();
           $("#searchZone_no").hide();
           $("#searchWardType").hide();
           $("#searchArea").hide();
           $("#searchCityName").hide();
           $("#searchWardNo").hide();
           $("#searchAreaNo").hide();
           $("#searchCityNo").hide();
           $("#zone_name").hide();
           $("#zoneno").hide();
           $("#ward_name").hide();
           $("#wardno").hide();
           $("#area_name").hide();
           $("#areano").hide();
           $("#citylocation").hide();
           $("#citylocationno").hide();
       }
       if(location_type=='zone'){
           $("#searchZone").show();
           $("#searchZone_no").show();
           $("#searchWardType").hide();
           $("#searchArea").hide();
           $("#searchCityName").hide();
           $("#searchWardNo").hide();
           $("#searchAreaNo").hide();
           $("#searchCityNo").hide();
           $("#zone_name").show();
           $("#zoneno").show();
           $("#ward_name").hide();
           $("#wardno").hide();
           $("#area_name").hide();
           $("#areano").hide();
           $("#citylocation").hide();
           $("#citylocationno").hide();
       }
          if(location_type=='ward'){
           $("#searchZone").show();
           $("#searchWardType").show();
           $("#searchZone_no").show();
           $("#searchWardNo").show();
           $("#searchArea").hide();
           $("#searchCityName").hide();
           $("#searchAreaNo").hide();
           $("#searchCityNo").hide();
           $("#zone_name").show();
           $("#zoneno").show();
           $("#ward_name").show();
           $("#wardno").show();
           $("#area_name").hide();
           $("#areano").hide();
           $("#citylocation").hide();
           $("#citylocationno").hide();
       }
          if(location_type=='area'){
           $("#searchZone").show();
           $("#searchWardType").show();
           $("#searchArea").show();
           $("#searchZone_no").show();
           $("#searchWardNo").show();
           $("#searchAreaNo").show();
           $("#searchCityName").hide();
           $("#searchCityNo").hide();
           $("#zone_name").show();
           $("#zoneno").show();
           $("#ward_name").show();
           $("#wardno").show();
           $("#area_name").show();
           $("#areano").show();
           $("#citylocation").hide();
           $("#citylocationno").hide();

       }
          if(location_type=='city_location'){
           $("#searchZone").show();
           $("#searchWardType").show();
           $("#searchArea").show();
           $("#searchCityName").show();
           $("#searchZone_no").show();
           $("#searchWardNo").show();
           $("#searchAreaNo").show();
           $("#searchCityNo").show();
           $("#zone_name").show();
           $("#zoneno").show();
           $("#ward_name").show();
           $("#wardno").show();
           $("#area_name").show();
           $("#areano").show();
           $("#citylocation").show();
           $("#citylocationno").show();
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
        else if(id == 'update'){
            document.getElementById("clickedButton").value = "Revised";
        }

        else
            {}
    }
     function getPdf(){
                var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var search_shift_type = $("#search_shift_type").val();
                var search_designation = $("#search_designation").val();
                var queryString = "task=generateHSReport&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&search_shift_type="+search_shift_type+"&search_designation="+search_designation;
                var url = "ShiftDesinationLocationController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
            function getExcel(){
                   var searchWardType = $("#searchWardType").val();
                var searchZone = $("#searchZone").val();
                var searchArea = $("#searchArea").val();
                var searchCityName = $("#searchCityName").val();
                var search_shift_type = $("#search_shift_type").val();
                var search_designation = $("#search_designation").val();
              var queryString = "task=generateReport&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName+"&search_shift_type="+search_shift_type+"&search_designation="+search_designation;
                var url = "ShiftDesinationLocationController?" + queryString;
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
    <body >
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
                                        <td align="center" class="header_table" width="90%"><b>Shift Designation Location Map</b></td>
                                    </tr>
                                </table>
                            </td></tr>

                             <tr>
                                 <td> <div align="center" class="content_div">
                                    <form name="form0" method="get" action="ShiftDesinationLocationController">
                                  <table align="center" class="heading1" width="950">
                       
                           <tr>
                               <td colspan="12" style="text-align: center">
                                   <b> Location Type </b>
                                   <select name="searchlocation_type" id="searchlocation_type" onchange="Status()" value="$(searchlocation_type)">
                                       <option value="select">Select</option>
                                       <option value="zone">Zone</option>
                                       <option value="ward">Ward</option>
                                       <option value="area">Area</option>
                                       <option value="city_location">City Location</option>
                                   </select>
                              </td>
                           </tr>
                               

                           <tr>
                                         <th class="heading1" id="zone_name" style="display: none"> Zone Name</th>
                                         <td>   <input type="text" class="new_input" style="display: none" name="searchZone" size="15" id="searchZone"  value="${searchZone}"/></td>
                                         <th class="heading1" id="zoneno" style="display: none"> Zone No</th>
                                          <td> <input type="text" class="new_input" style="display: none" name="searchZone_no" size="5" id="searchZone_no"  value="${searchZone_no}"/></td>
                                          <th class="heading1" id="ward_name" style="display: none">Ward Name</th>
                                          <td> <input class="new_input" type="text" style="display: none" id="searchWardType" name="searchWardType" value="${searchWardType}" size="15" ></td>
                                          <th class="heading1" id="wardno" style="display: none">Ward No</th>
                                          <td> <input class="input" type="text" style="display: none" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" size="5" ></td>
                                          <th class="heading1" id="area_name" style="display: none"> Area Name</th>
                                          <td><input class="new_input"  type="text" name="searchArea" style="display: none" size="10" id="searchArea" value="${searchArea}" size="15"></td>
                                          <th class="heading1" id="areano" style="display: none"> Area No</th>
                                          <td> <input  type="text" name="searchAreaNo" style="display: none" size="5" id="searchAreaNo" value="${searchAreaNo}" size="5"></td>
                           </tr>
                           <tr>                <th id="citylocation" style="display: none">City Location</th>
                                              <td><input class=" new_input" type="text" style="display: none" id="searchCityName" name="searchCityName" value="${searchCityName}" size="15" ></td>
                                              <th id="citylocationno" style="display: none">City Location No</th>
                                              <td><input class="input" type="text" style="display: none" id="searchCityNo" name="searchCityNo" value="${searchCityNo}" size="15" ></td>
                                              <th>Shift Type</th>
                                              <td><input   type="text" class="new_input" id="search_shift_type" name="search_shift_type" value="${search_shift_type}" size="10" ></td>
                                               <th>Designation</th>
                                              <td><input class="new_input"  type="text" id="search_designation" name="search_designation" value="${search_designation}" size="10"></td>
                           </tr>
                                                <tr>  
                                               <td colspan="12" align="center">
                                                 <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                 <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getPdf()">
                                                <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()"></td>
                                                </tr>
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
 <form name="form1" action="ShiftDesinationLocationController">


        <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">


                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <th class="heading">S.no&nbsp;</th>
                <TH class="heading">Shift Type&nbsp;</TH>
                 <th class="heading">Designation&nbsp;</th>
                <TH class="heading">Location Type&nbsp;</TH>
                 <TH class="heading">Zone Name&nbsp;</TH>
                <TH class="heading">Ward Name&nbsp;</TH>
                 <TH class="heading">Area Name&nbsp;</TH>
                 <TH class="heading">City Location Name&nbsp;</TH>
                <th class="heading">No Of Person&nbsp;</th>
                <th class="heading">Remark&nbsp;</th>
            <c:forEach var="ShiftDesignationLocationModel" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftDesignationLocationModel.map_id1}</td>
            
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${ShiftDesignationLocationModel.shift_type}</td>
            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${ShiftDesignationLocationModel.designation}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.location_type}</td>
             <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${ShiftDesignationLocationModel.zone}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${ShiftDesignationLocationModel.zone_no}</td>
             <td id="t1c${IDGenerator.uniqueID}" class="new_input"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.ward}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${ShiftDesignationLocationModel.ward_no}</td>
             <td id="t1c${IDGenerator.uniqueID}" class="new_input"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.area}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${ShiftDesignationLocationModel.area_no}</td>
             <td id="t1c${IDGenerator.uniqueID}" class="new_input"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.location}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${ShiftDesignationLocationModel.location_no}</td>
              <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.no_of_person}</td>
             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftDesignationLocationModel.remark}</td>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftDesignationLocationModel.map_id2}</td>
            </tr>
            </TR>


            </c:forEach>

            <tr> <td align='center' colspan="10">
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
                    <input type="hidden"  name="search_shift_type" value="${search_shift_type}" >
                    <input type="hidden"  name="search_designation" value="${search_designation}" >
                     <input  type="hidden" name="searchWardType" value="${searchWardType}">
                   <input  type="hidden" name="searchCityName" value="${searchCityName}">
                   <input  type="hidden" name="searchZone" value="${searchZone}">
                   <input  type="hidden" name="searchWardType" value="${searchWardNo}">
                   <input  type="hidden" name="searchCityName" value="${searchCityNo}">
                   <input  type="hidden" name="searchCityName" value="${searchCity}">
                   <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
                  <input  type="hidden" name="searchZone" value="${searchZone_no}">
                   <input  type="hidden" name="searchlocation_type" value="${searchlocation_type}">

        </form>

                      <form  action="ShiftDesinationLocationController" method="post">
                          <table  align="center"  class="content" border="1">
                                         <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                              <tr><input class="input" type="hidden" id="map_id1" name="map_id1" value="" ></tr>
                              <tr><input class="input" type="hidden" id="map_id2" name="map_id2" value="" ></tr>
                             <tr>

                                 <th class="heading1"> Shift Type </th>
                                 <td><input type="text" class="new_input" id="shift_type"  name="shift_type" value="" disabled></td>
                                 <th class="heading1"> Designation </th>
                              <td><input type="text" class="new_input" id="designation" name="designation" value="" disabled></td>
                              </tr>
                              <tr>
                              <th class="heading1"> Location Type </th>
                              <td>
                              <select name="location_type" id="location_type" onchange="changeStatus()" disabled>
                                    <option value="select">Select</option>
                                <option value="zone">Zone</option>
                               <option value="ward">Ward</option>
                               <option value="area">Area</option>
                                 <option value="city_location">City Location</option>
                                 </select>
                              </td>
                             </tr>
                              <tr>
                                      <th class="heading1" >Zone Name</th>
                                      <td><input type="text" class="new_input"   id="zone" size="10" style="display: none" name="zone" value="" disabled>
                                      <input type="text"  id="zone_no"  size="10" style="display: none" name="zone_no" placeholder='zone_no'  value="" disabled>
                                      </td>
                                    <th class="heading1"  >Ward Name</th>
                              <td><input type="text"  class="new_input"  id="ward" name="ward" size="10" style="display: none"  value="" disabled>
                                  <input type="text"  id="ward_no" name="ward_no" style="display: none" size="10" placeholder='ward_no'  value="" disabled>
                              </td>
                              </tr>
                              <tr>
                               <th class="heading1">Area Name</th>
                              <td><input type="text" class="new_input"  id="area"  size="10"  style="display: none" name="area" value="" disabled>
                                  <input type="text"  id="area_no"  size="10"  style="display: none" name="area_no" placeholder='area_no' value="" disabled>
                              </td>
                              <th class="heading1">City Location Name</th>
                              <td><input type="text" class="new_input"  id="location"  size="10" style="display: none" name="location" value="" disabled>
                                  <input type="text"  id="location_no"  size="10"  style="display: none" name="location_no" placeholder='location_no' value="" disabled>
                              </td>
                              </tr>
                          
                               <tr>
                              <th class="heading1">No Of Person </th>
                              <td><input type="text"  id="no_of_person" name="no_of_person" value="" disabled></td>
                                <th class="heading1"> Remark </th>
                              <td><input type="text"  id="remark" name="remark" value="" disabled></td>
                              </tr>
                           <tr>
            <td align='center' colspan="4" >
           <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >
           <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
           <input  class="button" type="submit" name="task" id="update" value="Revised" onclick="setStatus(id)" disabled>
           <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
           <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
            <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
            </td>
            </tr>
      </table>
          <input type="hidden" id="clickedButton" value="">
         <input type="hidden" name="lowerLimit" value="${lowerLimit}">
         <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
         <input type="hidden"  name="search_shift_type" value="${search_shift_type}" >
         <input type="hidden"  name="search_location" value="${search_location}" >
         <input type="hidden"  name="search_designation" value="${search_designation}" >
         <input  type="hidden" name="searchWardType" value="${searchWardType}">
         <input  type="hidden" name="searchCityName" value="${searchCityName}">
         <input  type="hidden" name="searchZone" value="${searchZone}">
         <input  type="hidden" name="searchWardType" value="${searchWardNo}">
          <input  type="hidden" name="searchCityName" value="${searchCityNo}">
          <input  type="hidden" name="searchCityName" value="${searchCity}">
          <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
          <input  type="hidden" name="searchZone" value="${searchZone_no}">
          <input  type="hidden" name="searchlocation_type" value="${searchlocation_type}">
                      </form>
                       <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                   </table>
            </DIV>
         </td>
        
      </table>

    </body>
</html>
