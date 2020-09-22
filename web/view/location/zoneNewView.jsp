<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <title>Zone Form</title>
        <script type="text/javascript">
               jQuery(function(){
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

                             var cssFunction = function(){
                             $(".ac_results li").css({
                                 'margin': '0px',
                                 'padding': '2px 5px',
                                 'cursor' : 'default',
                                 'display' : 'block',
                                 'color' : '#972800',
                                 'font-family' :'Sans-Serif',//, 'kruti Dev 010',
                                 'font-size': '12px',
                                 'line-height': '16px',
                                 'overflow': 'hidden'
                             });
                         }

                         $("#searchZone").click(cssFunction);
                         $("#CityName").click(cssFunction);

                     });

            function setButton(id)
            {
                if(id=="delete")
                    document.getElementById('buttonClick').value="delete";
                 if(id=="saveAsNew")
                    document.getElementById('buttonClick').value="saveAsNew";
                 if(id=="save")
                    document.getElementById('buttonClick').value="save";
            }
            function makeEditable(id)
            {


                if(id=="new")
                {
                document.getElementById('edit').disabled=true;
                document.getElementById('delete').disabled=true;
                document.getElementById('saveAsNew').disabled=true;
                document.getElementById('zoneName').disabled=false;
                document.getElementById('zoneDescription').disabled=false;
                document.getElementById('zone_no').disabled=false;
                document.getElementById('save').disabled=false;
                document.getElementById('zoneName').focus()
                document.getElementById('message').innerHTML="";

                }
                if(id=="edit")
                {
                    document.getElementById('zoneName').disabled=false;
                    document.getElementById('zone_no').disabled=false;
                    document.getElementById('zoneDescription').disabled=false;
                    document.getElementById('save').disabled=false;
                    document.getElementById('saveAsNew').disabled=false;
                    document.getElementById('delete').disabled=false;
                }

            }
            function varification()
            {
                var click = document.getElementById('buttonClick').value;

                if($.trim(document.getElementById('zoneName').value)=="")
                {
                    document.getElementById('zoneName').value="";
                    alert("Please enter Zone name")
                    document.getElementById('zoneName').focus()
                    return false;
                }
//                if($.trim(document.getElementById('zoneDescription').value)=="")
//                {
//                    alert("Please enter Zone description")
//                      document.getElementById('zoneDescription').value="";
//                    document.getElementById('zoneDescription').focus()
//                    return false;
//                }
                if(document.getElementById('zoneId').value=="")
                {
                    addRow();
                    return false;
                }
                if(click=="saveAsNew")
                {
                  var con = confirm("Do you want to save as this this record")
                  return con;
                }else if(click=="save")
                {
                  var con = confirm("Do you want to update this record")
                  return con;
                }else if(click=="delete")
                {
                   var con = confirm("Do you want to delete this record")
                   return con;
                 }

                return true;
            }
            function addRow()
            {

                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);

                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.name="zone_id";
                element1.id="zone_id"+rowCount;
                element1.type="hidden";
                element1.value=1;
                element1.size=1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name="zoneCheckbox";
                element2.id="zoneCheckbox"+rowCount;
                element2.type="checkbox";
                element2.checked=true;
                element2.setAttribute("onclick", 'singleCheck('+rowCount+')');
                cell1.appendChild(element2);

                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name="CityName";
                element3.id="CityName"+rowCount;
                element3.value=document.getElementById('CityName').value;
                element3.size=30;
                element3.className = "new_input";
                cell2.appendChild(element3);

                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name="zoneName";
                element4.id="zoneName"+rowCount;
                element4.value=document.getElementById('zoneName').value;
                element4.size=30;
                element4.className = "new_input";
                cell3.appendChild(element4);


                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name="zoneDescription";
                element5.id="zoneDescription"+rowCount;
                element5.value=document.getElementById('zoneDescription').value;
                element5.size=30;
                element5.className = "new_input";
                cell4.appendChild(element5);


                var height = (rowCount * 40)+ 60;
                 document.getElementById('autoCreatedTableDiv').style.visibility="visible";
                  document.getElementById("autoCreatedTableDiv").style.height = height+'px';
            }
            function singleCheck(id)
            {
                if(document.getElementById('zoneCheckbox'+id).checked==true)
                    document.getElementById('zone_id'+id).value=1;
                else
                    document.getElementById('zone_id'+id).value=0;
            }
            function deleteTable()
            {
                var table = document.getElementById('insertTable');
                var rowCount=table.rows.length;
                for(var i =0;i<rowCount-1;i++)
                    table.deleteRow(1);
                 document.getElementById('autoCreatedTableDiv').style.visibility="hidden";

            }
            function check()
            {
               var value = document.getElementById('uncheckAll').value;
               var length = document.getElementsByName('zoneCheckbox').length;
               if(value=="UncheckAll")
               {
               for(var checkbox=0;checkbox<length;checkbox++)
               {
                  document.getElementsByName('zoneCheckbox')[checkbox].checked=false;
                  document.getElementsByName('zone_id')[checkbox].value=0;
               }
               document.getElementById('uncheckAll').value="CheckAll";
               }
               else
               {
                 for(var checkbox=0;checkbox<length;checkbox++)
                {
                  document.getElementsByName('zoneCheckbox')[checkbox].checked=true;
                  document.getElementsByName('zone_id')[checkbox].value=1;
                }
               document.getElementById('uncheckAll').value="UncheckAll";
              }
            }
            function findfill(id)
            {
                setDefault();
                document.getElementById(id).bgColor="#d0dafd";
                document.getElementById('edit').disabled=false;
                document.getElementById('save').disabled=true;
                document.getElementById('zoneId').value = document.getElementById(id+'1').innerHTML;
                document.getElementById('zoneName').value = document.getElementById(id+'3').innerHTML;
                document.getElementById('zoneDescription').value = document.getElementById(id+'4').innerHTML;
                document.getElementById('zone_no').value=document.getElementById(id+'5').innerHTML;

                document.getElementById('message').innerHTML="";
            }
            function setDefault()
            {
                for(var i=1;i<=document.getElementById('noOfRowsTraversed').value;i++)
                    document.getElementById(i).bgColor="";
            }
            function displayMapList(id)
            {
                var searchZone = document.getElementById('searchZone').value;
                 var action;
                if(id=='viewPdf')
              action ="task=generateMapReport&searchZone="+searchZone;
               else
               action="task=generateXlsMapReport&searchZone="+searchZone;
                var url="zoneTypeCont?"+action;
                popup = popupWindow(url,"Zone new Report");

            }
            function popupWindow(url,windowName)
            {
                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url,windowName,windowfeatures)
            }
        </script>
    </head>

    <body>
     <table align="center" cellpadding="0" cellspacing="0"  class="main">
   <tr>
       <td colspan="2"><%@include file="/layout/header.jsp" %></td>
   </tr>
   <tr>
       <td colspan="2"><%@include file="/layout/menu.jsp" %><br style="line-height: 6px;"></td>
   </tr>
   <tr>
       <td class="header_table" align="center" width="870">Zone Table</td>
            <td><%@include file="/layout/org_menu.jsp" %></td>
   </tr>
   <tr>
       <td colspan="2" align="center"><br style="line-height: 6px;">
            <form action="zoneTypeCont" method="post">
                <span class="heading1"> Zone Name:</span>
                <input type="text" class="new_input" name="searchZone" size="20" id="searchZone"  value="${searchZone}"/>
                <span class="heading1"> Zone No:</span>
                 <input type="text" class="new_input" name="searchZone_no" size="20" id="searchZone_no"  value="${searchZone_no}"/>
                    <input class="button" type="submit" name="searchZoneModel" value="Search"/>
                    <input class="button" type="submit" name="task" value="Search All Records"/>
                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                    <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/>
            </form>

            <form action="zoneTypeCont" method="post">
                <input type="hidden" name="searchZone" id="searchZone" value="${searchZone}"/>
                <input type="hidden" name="searchZone_no" id="searchZone_no" value="${searchZone_no}"/>
             <table id="table1" border="1" align="center"  class="reference">
                <tr>
                    <th class="heading">S.No.</th>
                    <th class="heading">Zone Name</th>
                    <th class="heading">Zone Description</th>
                    <th class="heading">Zone No</th>
                    
                </tr>
                <c:forEach var="zoneBean" items="${requestScope['zoneList']}" varStatus="loopcounter">
                    <tr id="${loopcounter.count}"onclick="findfill(id)">
                        <td id="${loopcounter.count}1" style="display: none">${zoneBean.zoneId}</td>
                        <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                        <td class="new_input" id="${loopcounter.count}3">${zoneBean.zoneName}</td>
                        <td class="new_input" id="${loopcounter.count}4">${zoneBean.zoneDescription}</td>
                        <td class="new_input" id="${loopcounter.count}5">${zoneBean.zone_no}</td>
                    </tr>
                </c:forEach>
                 <tr>
                    <td align='center' colspan="6">
                        <c:choose>
                            <c:when test="${showFirst eq 'false'}">
                                <input class="button" type='submit' name='task' value='First' disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="button" type='submit' name='task' value='First'>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${showPrevious == 'false'}">
                                <input class="button" type='submit' name='task' value='Previous' disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="button" type='submit' name='task' value='Previous'>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${showNext eq 'false'}">
                                <input class="button" type='submit' name='task' value='Next' disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="button" type='submit' name='task' value='Next'>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${showLast == 'false'}">
                                <input class="button" type='submit' name='task' value='Last' disabled>
                            </c:when>
                            <c:otherwise>
                                <input class="button" type='submit' name='task' value='Last'>
                            </c:otherwise>
                        </c:choose>
                    </td>
                 </tr>
                    <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                    <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
             </table>
            </form>

            <div id="autoCreatedTableDiv" style="overflow: auto; visibility: hidden;height: 0px" align="center">
                <form method="post" action="zoneTypeCont">
                    <table id="insertTable" border="1" class="content" border="1"  align="center">
                        <tr>
                            <th width="100" class="heading1">S. No</th>
                            <th class="heading1" width="100">City Name</th>
                            <th class="heading1" width="100">Zone Name</th>
                            <th class="heading1" width="100">Zone Description</th>
                        </tr>
                    </table>
                    <table class="reference" border="1" align="center">
                        <tr>
                            <td><input  class="button"  id="uncheckAll" name="uncheckAll" type="button" value="UncheckAll" onclick="check()"/>
                                <input  class="button"  type="submit"  id="saveAllRecords" name="task" value="Save all records"/>
                                <input  class="button"  type="submit" name="cancel" value="Cancel" onclick="deleteTable()"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        <div align="center">
            <form action="zoneTypeCont" method="post" name="form2" onsubmit="return varification()">
                <table class="content" border="1" align="center">
                        <c:if test="${not empty message}">
                            <tr id="message">
                                <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                            </tr>
                        </c:if>
                              <tr>
                        <td colspan="2"><input style="display: none;" type="text" id="zoneId" name="zoneId" value="" readonly/><td>
                    </tr>
                   
                     <tr>
                        <td class="heading1">Zone Name</td><td><input type="text" class="new_input" name="zoneName" id="zoneName" value="" size="40" disabled></td>
                    </tr>
                      <tr>
                         <td class="heading1" width="210px;">Zone No</td><td><input type="text" class="new_input" name="zone_no" id="zone_no" value="" size="40" disabled></td>
                    </tr>
                    <tr>
                        <td class="heading1">Zone Description</td><td><input type="text" class="new_input" name="zoneDescription" id="zoneDescription" value="" size="40" disabled></td>
                    </tr>
                    <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                    <tr>
                        <td class="heading1" colspan="2" align="center" style="text-align: center;">
                            <input class="button"  type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled/>
                            <input class="button"  type="submit" name="task" id="save" value="Save" onclick="setButton(id)" disabled/>
                            <input class="button" type="submit" name="task" id="saveAsNew" value="Save As New" onclick="setButton(id)" disabled/>
                            <input class="button"  type="reset" name="new" value="New" id="new" onclick="makeEditable(id)"/>
                            <input class="button"  type="submit" name="task" value="Delete" id="delete" onclick="setButton(id)" disabled/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        </td>
    </tr>
     <tr>
         <td colspan="2"> <br style="line-height: 180px;"/><%@include file="/layout/footer.jsp" %></td>
     </tr>
    </table>
    </body>
</html>