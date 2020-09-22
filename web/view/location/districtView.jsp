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
                $("#searchDistrict").autocomplete("districtTypeCont", {
                    extraParams: {
                        action1: function() { return "getDistrict"}
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

                         $("#searchDistrict").click(cssFunction);
                         $("#stateName").click(cssFunction);
                         $("#utName").click(cssFunction);
                         $("#divisionName").click(cssFunction);

                         var stateFontCount = 1;
                         var districtFontCount = 1;
                         var divisionFontCount = 1;
                         
                          $("#changeStateFont").click(function(){
                        if(stateFontCount == 1 ){
                              $(".state_name").removeClass("new_input");
                            stateFontCount = 2;
                        }else{
                              $(".state_name").addClass("new_input");
                            stateFontCount = 1;
                        }
                    });
                    $("#changeDistrictFont").click(function(){
                        if(districtFontCount == 1 ){
                              $(".district_name").removeClass("new_input");
                            districtFontCount = 2;
                        }else{
                              $(".district_name").addClass("new_input");
                            districtFontCount = 1;
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
                $("#stateName").autocomplete("districtTypeCont", {
                    extraParams: {
                        action1: function() { return "getState"}
                    }
                });
            });
            jQuery(function(){
                $("#utName").autocomplete("districtTypeCont", {
                    extraParams: {
                        action1: function() { return "getUt"}
                    }
                });
            });
            jQuery(function(){
                $("#divisionName").autocomplete("districtTypeCont", {
                    extraParams: {
                        stateName: function(){return document.getElementById('stateName').value;},
                        utName: function(){return document.getElementById('utName').value;},
                        action1: function() { return "getDivision"}
                    }
                });
            });
//            jQuery(function(){
//                $("#districtName").autocomplete("districtTypeCont", {
//                    extraParams: {
//                        divisionName: function(){return document.getElementById('divisionName').value;},
//                        action1: function() { return "getDist"}
//                    }
//                });
//            });

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
                    document.getElementById('districtName').disabled=false;
                    document.getElementById('districtDescription').disabled=false;
                    document.getElementById('divisionName').disabled=false;
                    document.getElementById('stateName').disabled=false;
                    document.getElementById('utName').disabled=false;
                    document.getElementById('save').disabled=false;
                    document.getElementById('stateName').focus()
                    document.getElementById('message').innerHTML="";
                
                }
                if(id=="edit")
                {
                    document.getElementById('stateName').disabled=false;
                    document.getElementById('utName').disabled=false;
                    document.getElementById('divisionName').disabled=false;
                    document.getElementById('districtName').disabled=false;
                    document.getElementById('districtDescription').disabled=false;
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
                if($.trim(document.getElementById('stateName').value)=="" && $.trim(document.getElementById('utName').value)=="" )
                {
                    alert("Please enter State & UT name")
                    document.getElementById('stateName').value="";
                    document.getElementById('utName').value="";
                    document.getElementById('stateName').focus()
                    return false;
                }
                if($.trim(document.getElementById('divisionName').value)=="")
                {
                    alert("Please enter Division name")
                    document.getElementById('divisionName').value="";
                    document.getElementById('divisionName').focus()
                    return false;
                }
                if($.trim(document.getElementById('districtName').value)=="")
                {
                    alert("Please enter District name")
                    document.getElementById('districtName').value="";
                    document.getElementById('districtName').focus()
                    return false;
                }
//                if($.trim(document.getElementById('districtDescription').value)=="")
//                {
//                    alert("Please enter District description")
//                    document.getElementById('districtDescription').value="";
//                    document.getElementById('districtDescription').focus()
//                    return false;
//                }
                if(document.getElementById('districtId').value=="")
                {                 
                    addRow();
                    return false;
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
                element1.name="district_id";
                element1.id="district_id"+rowCount;
                element1.type="hidden";
                element1.value=1;                
                element1.size=1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name="districtCheckbox";
                element2.id="districtCheckbox"+rowCount;
                element2.type="checkbox";
                element2.checked=true;
                element2.setAttribute("onclick", 'singleCheck('+rowCount+')');
                cell1.appendChild(element2);
              
                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name="stateName";
                element3.width="70";
                element3.id="stateName"+rowCount;
                element3.value=document.getElementById('stateName').value;
                element3.size=30;
                element3.className = "new_input";
                cell2.appendChild(element3);
                
                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name="utName";
                element4.width="70";
                element4.id="utName"+rowCount;
                element4.value=document.getElementById('utName').value;
                element4.size=30;
                element4.className = "new_input";
                cell3.appendChild(element4);

                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name="divisionName";
                element5.width="70";
                element5.id="divisionName"+rowCount;
                element5.value=document.getElementById('divisionName').value;
                element5.size=30;
                element5.className = "new_input";
                cell4.appendChild(element5);

               
                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name="districtName";
                element6.width="70";
                element6.id="districtName"+rowCount;
                element6.value=document.getElementById('districtName').value;
                element6.size=30;
                element6.className = "new_input";
                cell5.appendChild(element6);

                var cell6 = row.insertCell(5);
                var element7 = document.createElement("input");
                element7.name="districtDescription";
                element7.width="70";
                element7.id="districtDescription"+rowCount;
                element7.value=document.getElementById('districtDescription').value;
                element7.size=30;
                element7.className = "new_input";
                cell6.appendChild(element7);                
                 
                var height = (rowCount * 40)+ 60;
                document.getElementById('autoCreatedTableDiv').style.visibility="visible";
                document.getElementById("autoCreatedTableDiv").style.height = height+'px';
            }
            function singleCheck(id)
            {
                if(document.getElementById('districtCheckbox'+id).checked==true)
                    document.getElementById('district_id'+id).value=1;
                else
                    document.getElementById('district_id'+id).value=0;
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
                var length = document.getElementsByName('districtCheckbox').length;
                if(value=="UncheckAll")
                {
                    for(var checkbox=0;checkbox<length;checkbox++)
                    {
                        document.getElementsByName('districtCheckbox')[checkbox].checked=false;
                        document.getElementsByName('district_id')[checkbox].value=0;
                    }
                    document.getElementById('uncheckAll').value="CheckAll";
                }
                else
                {
                    for(var checkbox=0;checkbox<length;checkbox++)
                    {
                        document.getElementsByName('districtCheckbox')[checkbox].checked=true;
                        document.getElementsByName('district_id')[checkbox].value=1;
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
                document.getElementById('districtId').value = document.getElementById(id+'1').innerHTML;
                document.getElementById('districtName').value = document.getElementById(id+'3').innerHTML;
                document.getElementById('districtDescription').value = document.getElementById(id+'4').innerHTML;
                document.getElementById('divisionName').value=document.getElementById(id+'5').innerHTML;              

                var sep = document.getElementById(id+'6').innerHTML;
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
                    document.getElementById('stateName').value=document.getElementById(id+'6').innerHTML;
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
                var searchDistrict = document.getElementById('searchDistrict').value;
                 var action;
                if(id=='viewPdf')
                 action="task=generateMapReport&searchDistrict="+searchDistrict;
                  else
                 action="task=generateXlsMapReport&searchDistrict="+searchDistrict;
                var url="districtTypeCont?"+action;
                popup = popupWindow(url,"District_View_Report");
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
                <td class="header_table" align="center" width="870">District Table</td>
                <td><%@include file="/layout/org_menu.jsp" %></td>   </tr>
            <tr>
                <td colspan="2" align="center"><br style="line-height: 6px;">
                    <form action="districtTypeCont" method="post">
                        <span class="heading1"> Enter District name:</span>
                        <input type="text" class="new_input" name="searchDistrict" size="30" id="searchDistrict" value="${searchDistrict}"/>
                        <input class="button" type="submit" name="searchDistrictModel" value="Search"/>
                        <input class="button" type="submit" name="task" value="Search All Records"/>
                        <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                        <input class="button" type="button" id="viewXls" name="viewExcell" value="Excel" onclick="displayMapList(id);"/>
                    </form>

                    <form action="districtTypeCont" method="post">
                        <input type="hidden" name="searchDistrict" id="searchDistrict" value="${searchDistrict}"/>
                        <table id="table1" border="1" align="center"  class="reference">
                            <tr>
                                <th class="heading">S.No.</th>
                                <th class="heading" id = "changeDistrictFont">District Name</th>
                                <th class="heading">District Description</th>
                                <th class="heading" id = "changeDivisionFont">Division Name</th>
                                <th class="heading" id ="changeStateFont">State & UT Name</th>
                            </tr>
                            <c:forEach var="districtBean" items="${requestScope['districtList']}" varStatus="loopcounter">
                                <tr id="${loopcounter.count}"onclick="findfill(id)">
                                    <td id="${loopcounter.count}1" style="display: none">${districtBean.districtId}</td>
                                    <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                    <td class="new_input district_name" id="${loopcounter.count}3">${districtBean.districtName}</td>
                                    <td class="new_input" id="${loopcounter.count}4">${districtBean.districtDescription}</td>
                                    <td class="new_input division_name" id="${loopcounter.count}5">${districtBean.divisionName}</td>
                                    <td class="new_input state_name" id="${loopcounter.count}6">${districtBean.stateName}</td>
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
                        <div id="autoCreatedTableDiv"  STYLE="overflow: auto; visibility: hidden;height: 0px" align="center">
                            <form method="post" action="districtTypeCont">
                                <table id="insertTable" border="1" class="content" border="1"  align="center">
                                    <tr>
                                        <th width="40" class="heading1">S. No</th>
                                        <th class="heading1" width="70">State Name</th>
                                        <th class="heading1" width="70">UT Name</th>
                                        <th class="heading1" width="70">Division Name</th>
                                        <th class="heading1" width="70">District Name</th>
                                        <th class="heading1" width="70">District Description</th>
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
                    <form action="districtTypeCont" method="post" name="form2" onsubmit="return varification()">
                        <table class="content" border="1" align="center">
                            <c:if test="${not empty message}">
                                <tr id="message">
                                    <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan="2"><input style="display: none;" type="text" id="districtId" name="districtId" value=""/><td>
                            </tr>
                            <tr>
                                <td class="heading1">State Name</td><td><input type="text" class="new_input" name="stateName" id="stateName" value="" size="40" disabled></td>
                            </tr>
                            <tr>
                                <td class="heading1">UT Name</td><td><input type="text" class="new_input" name="utName" id="utName" value="" size="40" disabled></td>
                            </tr>
                            <tr>
                                <td class="heading1">Division Name</td><td><input type="text" class="new_input" name="divisionName" id="divisionName" value="" size="40" disabled></td>
                            </tr>
                            <tr>
                                <td class="heading1" width="150">District Name</td><td><input type="text" class="new_input" id="districtName" name="districtName" size="40" disabled/></td>
                            </tr>
                            <tr>
                                <td class="heading1">District Description</td><td><input type="text" class="new_input" name="districtDescription" id="districtDescription" value="" size="40" disabled></td>
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
                </td>
            </tr>
            <tr>
                <td colspan="2"> <br style="line-height: 180px;"/><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>