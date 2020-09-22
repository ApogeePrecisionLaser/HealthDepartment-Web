<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <title>District Entry</title>
        <script type="text/javascript">
               jQuery(function(){
                $("#searchDivision").autocomplete("divisionTypeCont", {
                    extraParams: {
                                    action1: function() { return "getDivision"}
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
                                 'font-size': '12px',
                                 'line-height': '16px',
                                 'overflow': 'hidden'
                             });
                         }
                         var stateFontCount = 1;
                         var remarkFontCount = 1;
                         var divisionFontCount = 1;

                         $("#searchDivision").click(cssFunction);
                         $("#zoneName").click(cssFunction);
                         $("#stateName").click(cssFunction);
                         $("#utName").click(cssFunction);

                          $("#changeStateFont").click(function(){
                        if(stateFontCount == 1 ){                           
                              $(".state_name").removeClass("new_input");
                            stateFontCount = 2;
                        }else{                            
                              $(".state_name").addClass("new_input");
                            stateFontCount = 1;
                        }
                    });
                    $("#changeRemarkFont").click(function(){
                        if(remarkFontCount == 1 ){
                              $(".remark").removeClass("new_input");
                            remarkFontCount = 2;
                        }else{
                              $(".remark").addClass("new_input");
                            remarkFontCount = 1;
                        }
                    });
                    $("#changeDivisionFont").click(function(){
                        if(divisionFontCount == 1 ){
                              $(".division_name").removeClass("new_input");
                            divisionFontCount = 2;
                        }else{
                              $(".division_name").addClass("new_input");
                            divisionFontCount = 1;
                        }
                    });

                     });
                 jQuery(function(){
                $("#zoneName").autocomplete("divisionTypeCont", {
                    extraParams: {
                                    action1: function() { return "getZone"}
                                }
                             });
                     });

              jQuery(function(){
                  $("#stateName").autocomplete("divisionTypeCont", {
                    extraParams: {
                                    zoneName: function(){return document.getElementById("zoneName").value;},
                                    action1: function() { return "getStateName"}
                                }
                             });
                     });
              jQuery(function(){
              $("#utName").autocomplete("divisionTypeCont", {
                    extraParams: {
                                    zoneName: function(){return document.getElementById("zoneName").value;},
                                    action1: function() { return "getUtName"}
                                }
                             });
                     });
//                     jQuery(function(){
//              $("#divisionName").autocomplete("divisionTypeCont", {
//                    extraParams: {
//                                    stateName: function(){return document.getElementById("stateName").value;},
//                                    utName: function(){return document.getElementById("utName").value;},
//                                    action1: function() { return "getDivName"}
//                                }
//                             });
//                     });
            
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
                document.getElementById('stateName').disabled=false;
                document.getElementById('utName').disabled=false;
                document.getElementById('divisionName').disabled=false;
                document.getElementById('divisionDescription').disabled=false;
                document.getElementById('save').disabled=false;
                document.getElementById('zoneName').focus()
                document.getElementById('message').innerHTML="";
                
                }
                if(id=="edit")
                {
                    document.getElementById('zoneName').disabled=false;
                    document.getElementById('stateName').disabled=false;
                    document.getElementById('utName').disabled=false;
                    document.getElementById('divisionName').disabled=false;
                    document.getElementById('divisionDescription').disabled=false;
                    document.getElementById('save').disabled=false;
                    document.getElementById('saveAsNew').disabled=false;
                    document.getElementById('delete').disabled=false;
                }
               
            }
            function varification()
            {
                var click = document.getElementById('buttonClick').value;
                if(click=="delete")
                    {
                        var con = confirm("Do you want to delete this record")
                        return con;
                    }
                if(click=="saveAsNew")
                {
                  var con = confirm("Do you want to save as this this record")
                  return con;
                }
                if(document.getElementById('zoneName').value=="")
                {
                    alert("Please enter Zone name")
                    document.getElementById('zoneName').focus()
                    return false;
                }
                if((document.getElementById('stateName').value=="") && (document.getElementById('utName').value=""))
                {
                    alert("Please enter State or UT name")
                    document.getElementById('stateutName').focus()
                    return false;
                }
                if(document.getElementById('divisionName').value=="")
                {
                    alert("Please enter Division name")
                    document.getElementById('divisionName').focus()
                    return false;
                }
//                if(document.getElementById('divisionDescription').value=="")
//                {
//                    alert("Please enter Division description")
//                    document.getElementById('divisionDescription').focus()
//                    return false;
//                }
                if(document.getElementById('divisionId').value=="")
                {
                    addRow();
                    return false;
                }
                if(click=="saveAsNew")
                {
                  var con = confirm("Do you want to save as this this record")
                  return con;
                }
                 if(click=="save")
                {
                  var con = confirm("Do you want to update this record")
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
                element1.name="division_id";
                element1.id="division_id"+rowCount;
                element1.type="hidden";
                element1.value=1;                
                element1.size=1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name="divisionCheckbox";
                element2.id="divisionCheckbox"+rowCount;
                element2.type="checkbox";
                element2.checked=true;
                element2.setAttribute("onclick", 'singleCheck('+rowCount+')');
                cell1.appendChild(element2);
              
                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name="zoneName";
                element3.id="zoneName"+rowCount;
                element3.value=document.getElementById('zoneName').value;
                element3.size=30;
                cell2.appendChild(element3);

                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name="stateName";
                element4.id="stateName"+rowCount;
                element4.value=document.getElementById('stateName').value;
                element4.size=30;
                element4.className = "new_input";
                cell3.appendChild(element4);

                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name="utName";
                element5.id="utName"+rowCount;
                element5.value=document.getElementById('utName').value;
                element5.size=30;
                element5.className = "new_input";
                cell4.appendChild(element5);

               
                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name="divisionName";
                element6.id="divisionName"+rowCount;
                element6.value=document.getElementById('divisionName').value;
                element6.size=30;
                element6.className = "new_input";
                cell5.appendChild(element6);

                var cell6 = row.insertCell(5);
                var element7 = document.createElement("input");
                element7.name="divisionDescription";
                element7.id="divisionDescription"+rowCount;
                element7.value=document.getElementById('divisionDescription').value;
                element7.size=30;
                element7.className = "new_input";
                cell6.appendChild(element7);                
               
                var height = (rowCount * 40)+ 60;
                document.getElementById('autoCreatedTableDiv').style.visibility="visible";
                document.getElementById("autoCreatedTableDiv").style.height = height+'px';
            }
            function singleCheck(id)
            {
                if(document.getElementById('divisionCheckbox'+id).checked==true)
                    document.getElementById('division_id'+id).value=1;
                else
                    document.getElementById('division_id'+id).value=0;
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
               var length = document.getElementsByName('divisionCheckbox').length;
               if(value=="UncheckAll")
               {
               for(var checkbox=0;checkbox<length;checkbox++)
               {
                  document.getElementsByName('divisionCheckbox')[checkbox].checked=false;
                  document.getElementsByName('division_id')[checkbox].value=0;
               }
               document.getElementById('uncheckAll').value="CheckAll";
               }
               else
               {
                 for(var checkbox=0;checkbox<length;checkbox++)
                {
                  document.getElementsByName('divisionCheckbox')[checkbox].checked=true;
                  document.getElementsByName('division_id')[checkbox].value=1;
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
                document.getElementById('divisionId').value = document.getElementById(id+'1').innerHTML;
                document.getElementById('divisionName').value = document.getElementById(id+'3').innerHTML;
                document.getElementById('divisionDescription').value = document.getElementById(id+'4').innerHTML;
                document.getElementById('zoneName').value=document.getElementById(id+'6').innerHTML;
                var sep = document.getElementById(id+'5').innerHTML;
                var no = sep.indexOf("(",1)
                if(no!='-1')
                 {
                     document.getElementById('stateName').value=sep.substr(0, no)
                     var i = (sep.indexOf(")", 0)-(no+1))                     
                     document.getElementById('utName').value=sep.substr(no+1, i)
                 }            
                else
                {
                    document.getElementById('utName').value="";
                    document.getElementById('stateName').value=document.getElementById(id+'5').innerHTML;
                }
                    
                document.getElementById('message').innerHTML="";                 
            }
            function setDefault()
            {
                for(var i=1;i<=document.getElementById('noOfRowsTraversed').value;i++)
                    document.getElementById(i).bgColor="";                
            }
                function displayMapList(id)
            {
                var searchDivision = document.getElementById('searchDivision').value;
                if(id=='viewPdf')
                action="task=generateMapReport&searchDivision="+searchDivision;           
               else 
                action="task=generateXlsMapReport&searchDivision="+searchDivision;
                var url="divisionTypeCont?"+action;
                popup = popupWindow(url,"Division_View_Report");
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
       <td class="header_table" align="center" width="870">Division Table</td>
            <td><%@include file="/layout/org_menu.jsp" %></td>
   </tr>
   <tr>
       <td colspan="2" align="center"><br style="line-height: 6px;">
            <form action="divisionTypeCont" method="post">
                <span class="heading1"> Enter Division name:</span>
                    <input type="text" class="new_input" name="searchDivision" size="30" id="searchDivision" value="${searchDivision}"/>
                    <input class="button" type="submit" name="searchDivisionModel" value="Search"/>
                    <input class="button" type="submit" name="task" value="Search All Records"/>
                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                    <input class="button" type="button" id="viewXls" name="viewExcell" value="Excel" onclick="displayMapList(id);"/>
            </form>
                    
            <form action="divisionTypeCont" method="post">
                <input type="hidden" name="searchDivision" id="searchDivision" value="${searchDivision}"/>
             <table id="table1" border="1" align="center"  class="reference">
                <tr>
                    <th class="heading">S.No.</th>
                    <th class="heading" id="changeDivisionFont">Division Name</th>
                    <th class="heading" id="changeRemarkFont">Division Description</th>
                    <th class="heading" id="changeStateFont">State & UT Name</th>
                    <th class="heading">Zonal Council</th>
                </tr>
                <c:forEach var="divisionBean" items="${requestScope['divisionList']}" varStatus="loopcounter">
                    <tr id="${loopcounter.count}"onclick="findfill(id)">
                        <td id="${loopcounter.count}1" style="display: none">${divisionBean.divisionId}</td>
                        <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                        <td id="${loopcounter.count}3" class="new_input division_name">${divisionBean.divisionName}</td>
                        <td id="${loopcounter.count}4" class="new_input remark">${divisionBean.divisionDescription}</td>
                        <td id="${loopcounter.count}5" class="new_input state_name">${divisionBean.stateName}</td>
                        <td id="${loopcounter.count}6" class="">${divisionBean.zoneName}</td>
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
             <div align="center" style="text-align: center;">
                 <div id="autoCreatedTableDiv" STYLE="overflow: auto; visibility: hidden;height: 0px" align="center">
                <form method="post" action="divisionTypeCont">
                    <table id="insertTable" border="1" class="content" border="1"  align="center">
                        <tr>
                            <th width="100" class="heading1">S. No</th>
                            <th class="heading1" width="100">Zone Name</th>
                            <th class="heading1" width="100">State Name</th>
                            <th class="heading1" width="100">UT Name</th>
                            <th class="heading1" width="100">Division Name</th>
                            <th class="heading1" width="100">Division Description</th>
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
        </div>
        <div align="center">
            <form action="divisionTypeCont" method="post" name="form2" onsubmit="return varification()">
                <table class="content" border="1" align="center">                    
                        <c:if test="${not empty message}">
                            <tr id="message">
                            <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                            </tr>
                        </c:if>
                              <tr>
                        <td colspan="2"><input style="display: none;" type="text" id="divisionId" name="divisionId" value="" readonly/><td>
                    </tr>
                     <tr>
                        <td class="heading1">Zonal Council</td><td><input type="text" name="zoneName" id="zoneName" value="" size="40" disabled></td>
                    </tr>
                     <tr>
                        <td class="heading1">State Name</td><td><input type="text" class="new_input" name="stateName" id="stateName" value="" size="40" disabled></td>
                    </tr>
                    <tr>
                        <td class="heading1">UT Name</td><td><input type="text" class="new_input" name="utName" id="utName" value="" size="40" disabled></td>
                    </tr>
                    <tr>
                        <td class="heading1" width="150">Division Name</td><td><input type="text" class="new_input" id="divisionName" name="divisionName" size="40" disabled/></td>
                    </tr>
                    <tr>
                        <td class="heading1">Division Description</td><td><input type="text" class="new_input" name="divisionDescription" id="divisionDescription" value="" size="40" disabled></td>
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