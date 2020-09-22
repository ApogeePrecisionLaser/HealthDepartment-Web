<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <title>City Entry</title>
        <script type="text/javascript">
            
            jQuery(function(){
                $("#searchCity").autocomplete("cityTypeCont", {
                    extraParams: {
                        action1: function() { return "getCity"}
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

                         $("#searchCity").click(cssFunction);
                         $("#divisionName").click(cssFunction);
                         $("#districtName").click(cssFunction);

                         var cityFontCount = 1;
                         var districtFontCount = 1;
                         var divisionFontCount = 1;

                          $("#changeCityFont").click(function(){
                        if(cityFontCount == 1 ){
                              $(".city_name").removeClass("new_input");
                            cityFontCount = 2;
                        }else{
                              $(".city_name").addClass("new_input");
                            cityFontCount = 1;
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
                $("#districtName").autocomplete("cityTypeCont", {
                    extraParams: {
                        diviName: function(){return document.getElementById("divisionName").value;},
                        action1: function() { return "getDistrict"}
                    }
                });
            });
            jQuery(function(){
                $("#divisionName").autocomplete("cityTypeCont", {
                    extraParams: {
                        action1: function() { return "getDivision"}
                    }
                });
            });
//            jQuery(function(){
//                $("#cityName").autocomplete("cityTypeCont", {
//                    extraParams: {
//                        districtName: function(){return document.getElementById('districtName').value},
//                        action1: function() { return "getCityName"}
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
                    document.getElementById('cityName').disabled=false;
                    document.getElementById('cityDescription').disabled=false;
                    document.getElementById('pin_code').disabled=false;
                    document.getElementById('std_code').disabled=false;
                    document.getElementById('save').disabled=false;
                    document.getElementById('divisionName').focus()
                    document.getElementById('message').innerHTML="";
                
                }
                if(id=="edit")
                {
                    document.getElementById('pin_code').disabled=false;
                    document.getElementById('std_code').disabled=false;
                    document.getElementById('cityName').disabled=false;
                    document.getElementById('cityDescription').disabled=false;
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
                if($.trim(document.getElementById('divisionName').value)=="")
                {
                    alert("Please enter division name")
                    document.getElementById('divisionName').value="";
                    document.getElementById('divisionName').focus()
                    return false;
                }
                if($.trim(document.getElementById('districtName').value)=="")
                {
                    alert("Please enter district name")
                    document.getElementById('districtName').value="";
                    document.getElementById('districtName').focus()
                    return false;
                }
                if($.trim(document.getElementById('cityName').value)=="")
                {
                    alert("Please enter city name")
                    document.getElementById('cityName').value="";
                    document.getElementById('cityName').focus()
                    return false;
                }
//                if($.trim(document.getElementById('cityDescription').value)=="")
//                {
//                    alert("Please enter city description")
//                    document.getElementById('cityDescription').value="";
//                    document.getElementById('cityDescription').focus()
//                    return false;
//                }
                if(document.getElementById('cityId').value=="")
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
                var table = document.getElementById('insetTable');
                var rowCount = table.rows.length;                
                var row = table.insertRow(rowCount);

                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.name="city_id";
                element1.id="city_id"+rowCount;
                element1.type="hidden";
                element1.value=1;                
                element1.size=1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name="cityCheckbox";
                element2.id="cityCheckbox"+rowCount;
                element2.type="checkbox";
                element2.checked=true;
                element2.setAttribute("onclick", 'singleCheck('+rowCount+')');
                cell1.appendChild(element2);
                ////////////////////////////////////
                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name="divisionName";
                element3.id="divisionName"+rowCount;
                element3.value=document.getElementById('divisionName').value;
                element3.size=30;
                element3.className = "new_input";
                cell2.appendChild(element3);

                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name="districtName";
                element4.id="districtName"+rowCount;
                element4.value=document.getElementById('districtName').value;
                element4.size=30;
                element4.className = "new_input";
                cell3.appendChild(element4);

                ///////////////////////////////////////////
                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name="cityName";
                element5.id="cityName"+rowCount;
                element5.value=document.getElementById('cityName').value;
                element5.size=30;
                element5.className = "new_input";
                cell4.appendChild(element5);

                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name="cityDescription";
                element6.id="cityDescription"+rowCount;
                element6.value=document.getElementById('cityDescription').value;
                element6.size=30;
                element6.className = "new_input";
                cell5.appendChild(element6);
                var height = (rowCount * 40)+ 60;
                document.getElementById('autoCreatedTableDiv').style.visibility="visible";
                document.getElementById("autoCreatedTableDiv").style.height = height+'px';
            }
            function singleCheck(id)
            {
                if(document.getElementById('cityCheckbox'+id).checked==true)
                    document.getElementById('city_id'+id).value=1;
                else
                    document.getElementById('city_id'+id).value=0;
            }
            function deleteTable()
            {
                var table = document.getElementById('insetTable');
                var rowCount=table.rows.length;
                for(var i =0;i<rowCount-1;i++)
                    table.deleteRow(1);
                document.getElementById('autoCreatedTableDiv').style.visibility="hidden";
                 
            }
            function check()
            {                
                var value = document.getElementById('uncheckAll').value;
                var length = document.getElementsByName('cityCheckbox').length;
                if(value=="UncheckAll")
                {
                    for(var checkbox=0;checkbox<length;checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked=false;
                        document.getElementsByName('city_id')[checkbox].value=0;
                    }
                    document.getElementById('uncheckAll').value="CheckAll";
                }
                else
                {
                    for(var checkbox=0;checkbox<length;checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked=true;
                        document.getElementsByName('city_id')[checkbox].value=1;
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
                document.getElementById('cityId').value = document.getElementById(id+'1').innerHTML;
                document.getElementById('cityName').value = document.getElementById(id+'3').innerHTML;
                       document.getElementById('pin_code').value=document.getElementById(id+'4').innerHTML;
                document.getElementById('std_code').value=document.getElementById(id+'5').innerHTML;
                document.getElementById('cityDescription').value = document.getElementById(id+'6').innerHTML;
         
                document.getElementById('message').innerHTML="";                 
            }
            function setDefault()
            {
                for(var i=1;i<=document.getElementById('noOfRowsTraversed').value;i++)
                    document.getElementById(i).bgColor="";                
            }
            function displayMapList(id)
            {
                var searchCity = document.getElementById('searchCity').value;
                var action;
                if(id=='viewPdf')                    
                 action="task=generateMapReport&searchCity="+searchCity;
               else                 
                action="task=generateMapXlsReport&searchCity="+searchCity;
                var url="cityTypeCont?"+action;
                popup = popupWindow(url,"City_View_Report");
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
                <td class="header_table" align="center" width="870">City Table</td>
                <td><%@include file="/layout/org_menu.jsp" %></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><br style="line-height: 6px;">
                    <form action="cityTypeCont" method="post">
                        <span class="heading1"> Enter city name:</span>
                        <input type="text" class="new_input" name="searchCity" size="30" id="searchCity" value="${searchCity}"/>
                        <input class="button" type="submit" name="searchCityModel" value="Search"/>
                        <input class="button" type="submit" name="task" value="Search All Records"/>
                        <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                        <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/>
                    </form>
                    <form action="cityTypeCont" method="post">
                        <input type="hidden" name="searchCity" id="searchCity" value="${searchCity}"/>
                        <table id="table1" border="1" align="center"  class="reference">
                            <tr>
                                <th class="heading">S.No.</th>
                                <th class="heading">City Name</th>
                                <td class="heading">Pin Code</td>
                                <td class="heading">Std Code</td>
                                <th class="heading">City Description</th>
                                
                            </tr>
                            <c:forEach var="cityBean" items="${requestScope['cityList']}" varStatus="loopcounter">
                                <tr id="${loopcounter.count}"onclick="findfill(id)">
                                    <td id="${loopcounter.count}1" style="display: none">${cityBean.cityId}</td>
                                    <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                    <td class="new_input city_name" id="${loopcounter.count}3">${cityBean.cityName}</td>
                                     <td  id="${loopcounter.count}4">${cityBean.pin_code}</td>
                                    <td  id="${loopcounter.count}5">${cityBean.std_code}</td>
                                    <td class="new_input" id="${loopcounter.count}6">${cityBean.cityDescription}</td>
                                   
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
                </td></tr><tr><td colspan="2" align="center">
                    <div id="autoCreatedTableDiv" STYLE="overflow: auto; visibility: hidden;height: 0px" align="center">
                        <form method="post" action="cityTypeCont">
                            <table id="insetTable" border="1" class="content" border="1"  align="center">
                                <tr>
                                    <th width="100" class="heading1">S. No</th>
                                    <th class="heading1">Division Name</th>
                                    <th class="heading1">District Name</th>
                                    <th class="heading1">City Name</th>
                                    <th class="heading1">City Description</th>
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
                </td></tr><tr><td colspan="2" align="center">
                    <form action="cityTypeCont" method="post" name="form2" onsubmit="return varification()">
                        <table class="content" border="1" align="center">
                            <c:if test="${not empty message}">
                                <tr id="message">
                                    <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan="2"><input style="display: none;" type="text" id="cityId" name="cityId" value="" readonly/><td>
                            </tr>
                            <tr>
                                <td class="heading1" width="150">City Name</td><td><input type="text" class="new_input" id="cityName" name="cityName" size="40" disabled/></td>
                            </tr>
                             <tr>
                                <td class="heading1" width="150">Pin Code</td><td><input type="text" class="new_input" id="pin_code" name="pin_code" size="40" disabled/></td>
                            </tr>
                              <tr>
                                <td class="heading1" width="150">Std Code</td><td><input type="text" class="new_input" id="std_code" name="std_code" size="40" disabled/></td>
                            </tr>
                            <tr>
                                <td class="heading1">City Description</td><td><input type="text" class="new_input" name="cityDescription" id="cityDescription" value="" size="40" disabled></td>
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