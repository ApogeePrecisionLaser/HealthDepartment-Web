<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <title>District Entry</title>
        <style type="text/css">
                .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    /*background-color: white;*/
                }
            </style>
        <script type="text/javascript">
               jQuery(function(){
                $("#searchStateut").autocomplete("stateutTypeCont", {
                    extraParams: {
                                    action1: function() { return "getStateut"}
                                }
                             });
                     });              
                jQuery(function(){
                $("#zoneName").autocomplete("stateutTypeCont", {
                    extraParams: {
                        action2: function() { return "getZoneName"}
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
                    var stateFontCount = 1;

                    $("#searchStateut").click(cssFunction);
                    $("#zoneName").click(cssFunction);
                    $("#changeStateFont").click(function(){
                        if(stateFontCount == 1 ){
//                            $(".state_name").css({
//                                'font-family' :'Sans-Serif',
//                                'font-size': '12px'
//                            });
                              $(".state_name").removeClass("new_input");

                            stateFontCount = 2;
                        }else{
                            //$(".state_name").attr('style', style.replace(/font-family: 'kruti Dev 010';/g, ''));
//                            $('.state_name').css("font-family", "kruti Dev 010");
                              $(".state_name").addClass("new_input");
                            stateFontCount = 1;
                        }
                    });                    
                     });
//               jQuery(function(){
//                $("#stateName").autocomplete("stateutTypeCont", {
//                    extraParams: {
//                                    zoneName: function(){return document.getElementById('zoneName').value},
//                                    action3: function() { return "getStateName"}
//                                }
//                             });
//                     });
//                 jQuery(function(){
//                $("#utName").autocomplete("stateutTypeCont", {
//                    extraParams: {
//                                    zoneName: function(){return document.getElementById('zoneName').value},
//                                    action4: function() { return "getUtName"}
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
                document.getElementById('countryName').disabled=false;
                document.getElementById('zoneName').disabled=false;
                document.getElementById('stateName').disabled=false;
                document.getElementById('utName').disabled=false;
                document.getElementById('stateutDescription').disabled=false;
                document.getElementById('save').disabled=false;
                document.getElementById('countryName').focus()
                document.getElementById('message').innerHTML="";
                
                }
                if(id=="edit")
                {
                    document.getElementById('countryName').disabled=false;
                    document.getElementById('zoneName').disabled=false;
                    document.getElementById('stateName').disabled=false;
                    document.getElementById('utName').disabled=false;
                    document.getElementById('stateutDescription').disabled=false;
                    document.getElementById('save').disabled=false;
                    document.getElementById('saveAsNew').disabled=false;
                    document.getElementById('delete').disabled=false;
                }
               
            }
            function varification()
            {
                var click = document.getElementById('buttonClick').value;
                var zoneName = $.trim(document.getElementById('zoneName').value);
                var stateName = $.trim(document.getElementById('stateName').value);
                var utName = $.trim(document.getElementById('utName').value);
                var stateutDescription = $.trim(document.getElementById('stateutDescription').value);
                

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
               
                if(zoneName=="")
                {                    
                    alert("Please enter Zone name")
                    document.getElementById('zoneName').value="";
                    document.getElementById('zoneName').focus()
                    return false;
                }                       
                if((stateName == "") && (utName ==""))
                 {
                    alert("Please enter State or UT Name")
                    document.getElementById('stateName').value="";
                    document.getElementById('utName').value="";
                    document.getElementById('stateName').focus()
                    return false;
                 }
                /*if(stateutDescription=="")
                {
                    alert("Please enter Stateut description")
                    document.getElementById('stateutDescription').value="";
                    document.getElementById('stateutDescription').focus()
                    return false;
                }*/
                if(document.getElementById('stateutId').value=="")
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
                var language=document.getElementById("language");
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                
                var row = table.insertRow(rowCount);

                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.name="stateut_id";
                element1.id="stateut_id"+rowCount;
                element1.type="hidden";
                element1.value=1;                
                element1.size=1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name="stateutCheckbox";
                element2.id="stateutCheckbox"+rowCount;
                element2.type="checkbox";
                element2.checked=true;
                element2.setAttribute("onclick", 'singleCheck('+rowCount+')');
                cell1.appendChild(element2);
              
                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name="countryName";
                element3.id="countryName"+rowCount;
                element3.value=document.getElementById('countryName').value;
                element3.size=30;
                cell2.appendChild(element3);

                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name="zoneName";
                element4.id="zoneName"+rowCount;
                element4.value=document.getElementById('zoneName').value;
                element4.size=30;
                cell3.appendChild(element4);

               
                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name="stateName";
                element5.id="stateName"+rowCount;
                element5.value=document.getElementById('stateName').value;
                element5.size=30;
                element5.className = "new_input";
                cell4.appendChild(element5);

                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name="utName";
                element6.id="utName"+rowCount;
                element6.value=document.getElementById('utName').value;
                element6.size=30;
                element6.className = "new_input";
                cell5.appendChild(element6);


                var cell6 = row.insertCell(5);
                var element7 = document.createElement("input");
                element7.name="stateutDescription";
                element7.id="stateutDescription"+rowCount;
                element7.value=document.getElementById('stateutDescription').value;
                element7.size=30;
                element7.className = "new_input";
                cell6.appendChild(element7);
                
                   var height = (rowCount * 40)+ 60;
                 document.getElementById('autoCreatedTableDiv').style.visibility="visible";
                  document.getElementById("autoCreatedTableDiv").style.height = height+'px';
            }
            function singleCheck(id)
            {
                if(document.getElementById('stateutCheckbox'+id).checked==true)
                    document.getElementById('stateut_id'+id).value=1;
                else
                    document.getElementById('stateut_id'+id).value=0;
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
               var length = document.getElementsByName('stateutCheckbox').length;
               if(value=="UncheckAll")
               {
               for(var checkbox=0;checkbox<length;checkbox++)
               {
                  document.getElementsByName('stateutCheckbox')[checkbox].checked=false;
                  document.getElementsByName('stateut_id')[checkbox].value=0;
               }
               document.getElementById('uncheckAll').value="CheckAll";
               }
               else
               {
                 for(var checkbox=0;checkbox<length;checkbox++)
                {
                  document.getElementsByName('stateutCheckbox')[checkbox].checked=true;
                  document.getElementsByName('stateut_id')[checkbox].value=1;
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
                document.getElementById('stateutId').value = document.getElementById(id+'1').innerHTML;
                document.getElementById('stateName').value = document.getElementById(id+'3').innerHTML;
                document.getElementById('utName').value = document.getElementById(id+'4').innerHTML;
                document.getElementById('stateutDescription').value = document.getElementById(id+'5').innerHTML;
                document.getElementById('countryName').value=document.getElementById(id+'7').innerHTML;
                document.getElementById('zoneName').value=document.getElementById(id+'6').innerHTML;
                document.getElementById('message').innerHTML="";                 
            }
            function setDefault()
            {
                for(var i=1;i<=document.getElementById('noOfRowsTraversed').value;i++)
                    document.getElementById(i).bgColor="";                
            }
             function displayMapList(id)
            {
                var action;
                var searchStateut = document.getElementById('searchStateut').value;
                if(id == 'viewPdf')
                    action="task=geerateMapReport&searchStateut="+searchStateut;
                else
                    action="task=generateXlsapReport&searchStateut="+searchStateut;
                var url="stateutTypeCont?"+action;
                popup = popupWindow(url,"State & UT Report");
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
            <td class="header_table" align="center" width="870">State & UT Table</td>
            <td><%@include file="/layout/org_menu.jsp" %></td>
        </tr>
       <tr>
       <td colspan="2" align="center"><br style="line-height: 6px;">
            <form action="stateutTypeCont" method="post">
                <span class="heading1"> Enter State & UT Name:</span>
                <input type="text" class="new_input" name="searchStateut" size="30" id="searchStateut" value="${searchStateut}"/>
                    <input class="button" type="submit" name="searchStateutModel" value="Search"/>
                    <input class="button" type="submit" name="task" value="Search All Records"/>
                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                     <input class="button" type="button" id="viewXls" name="viewPdf" value="Excel" onclick="displayMapList(id);"/>
            </form>
                    
            <form action="stateutTypeCont" method="post">
                <input type="hidden" name="searchStateut" id="searchStateut" value="${searchStateut}"/>
             <table id="table1" border="1" align="center"  class="reference">
                <tr>
                    <th class="heading">S.No.</th>
                    <th class="heading" id="changeStateFont">State Name</th>
                    <th class="heading">UT Name</th>
                    <th class="heading">State & UT Description</th>
                    <th class="heading">Zone Name</th>
                    <th class="heading">Country Name</th>
                </tr>
                <c:forEach var="stateutBean" items="${requestScope['stateutList']}" varStatus="loopcounter">
                    <tr id="${loopcounter.count}"onclick="findfill(id)">
                        <td id="${loopcounter.count}1" style="display: none">${stateutBean.stateutId}</td>
                        <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                        <td id="${loopcounter.count}3" class="new_input toggle_state state_name">${stateutBean.stateName}</td>
                        <td id="${loopcounter.count}4" class="new_input">${stateutBean.utName}</td>
                        <td id="${loopcounter.count}5" class="new_input">${stateutBean.stateutDescription}</td>
                        <td id="${loopcounter.count}6">${stateutBean.zoneName}</td>
                        <td id="${loopcounter.count}7">${stateutBean.countryName}</td>
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
                <form method="post" action="stateutTypeCont">
                    <table id="insertTable" border="1" class="content" border="1"  align="center">
                        <tr>
                            <th width="100" class="heading1">S. No</th>
                            <th class="heading1" width="100">Country Name</th>
                            <th class="heading1" width="100">Zone Name</th>
                            <th class="heading1" width="100">State Name</th>
                            <th class="heading1" width="100">UT Name</th>
                            <th class="heading1" width="100">State/UT Description</th>
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
            <form action="stateutTypeCont" method="post" name="form2" onsubmit="return varification()">
                <table>
                    <c:if test="${not empty message}">
                            <tr id="message">
                            <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                            </tr>
                        </c:if>
                </table>                
                <table class="content" border="1" align="center">
                     <tr>
                        <td colspan="2"><input style="display: none;" type="text" id="stateutId" name="stateutId" value="" readonly/><td>
                    </tr>
                     <tr>
                         <td class="heading1">Country Name</td><td><input type="text" name="countryName" id="countryName" value="India" size="40" readonly></td>
                    </tr>
                     <tr>
                        <td class="heading1">Zone Name</td><td><input type="text" name="zoneName" id="zoneName" value="" size="40" disabled></td>
                    </tr>
                    <tr>
                        <td class="heading1" width="150">State Name</td><td><input type="text" class="new_input" id="stateName" name="stateName" size="40" disabled/></td>
                    </tr>
                    <tr>
                        <td class="heading1" width="150">UT Name</td><td><input type="text" class="new_input" id="utName" name="utName" size="40" disabled/></td>
                    </tr>

                    <tr>
                        <td class="heading1">State or UT Description</td><td><input type="text" class="new_input" name="stateutDescription" id="stateutDescription" value="" size="40" disabled></td>
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
         <td colspan="2"> <br style="line-height: 160px;"/><%@include file="/layout/footer.jsp" %></td>
     </tr>
    </table>
    </body>
</html>