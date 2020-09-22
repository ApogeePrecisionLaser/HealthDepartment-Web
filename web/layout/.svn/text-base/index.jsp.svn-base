

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>

        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript" src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>

        <script type="text/javascript">
     jQuery(function(){
        $("#location").autocomplete("shiftLoginCont.do", {
            extraParams: {
                action1: function() { return "getLocation"}
            }
        });
    });
    jQuery(function(){
        $("#name").autocomplete("shiftLoginCont.do", {
            extraParams: {
                action1: function() { return "getName"}
            }
        });
    });
 function makeEditable(id) {
   document.getElementById("name").disabled = false;
        document.getElementById("location").disabled = false;
        if(id == 'new') {
           $("#message").html("");

             document.getElementById("login").disabled =false;
             document.getElementById("shift_key_person_detial_id").value=0;




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
 function verify() {
       var result;
        var clickedButton = document.getElementById("clickedButton").value;

        if(clickedButton == 'login') {
                        var location = document.getElementById("location").value;
                                if(myLeftTrim(location).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Location  is required...</b></td>");
                                    document.getElementById("location").focus();
                                    return false;
                                }
                      var name = document.getElementById("name").value;
                                if(myLeftTrim(name).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Name is required...</b></td>");
                                    document.getElementById("name").focus();
                                    return false;
                                }
        return result;

        }
    }


function setStatus(id) {

        if(id == 'login'){

            document.getElementById("clickedButton").value = "login";
        }
        else
            {}
    }
    
     function getRidelist(){
                var fileName =$("#fileName").val();
                var queryString = "task=generateHSReport&filename="+fileName;
                var url = "shiftLoginCont.do?" + queryString;
                popupwin = openPopUp(url, "Ride List", 600, 900);
            }
      function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, active=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
    </script>
    </head>
    <body onload="checkClickedColumnId();">
        <table align="center"  class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">

            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>

            <tr>
                <td nowrap>
                    <DIV id="div_viewQtList" class="maindiv"  style="height: 650px; width: 900px" >
                        <table width="100%"  align="center">
                            <tr>
                                <td align="center">
                                    <h3>  Login</h3>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                               <form  action="shiftLoginCont.do" method="post" onsubmit="return verify()">
                            <table  align="center"  class="content" border="1">
                                         <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                       </tr>
                              <tr><input class="input" type="hidden" id="shift_key_person_detail_id" name="shift_key_person_detail_id" value="" ></tr>

                              <tr>
                              <th class="heading1"> Location </th>
                              <td><input type="text" class="new_input" id="location" size="20" name="location" value="" disabled></td>
                              </tr>
                             <tr>
                                 <th class="heading1"> Name </th>
                                 <td><input type="text" class="new_input" id="name" size="20" name="name" value="" disabled></td>
                              </tr>
                              <tr>
                                  <td colspan="2">
                               <input class="button" type="submit" name="task" id="login" value="Login" onclick="setStatus(id)" disabled >
                               <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                  </td>
                              </tr>
                              </table>
                                   <input type="button" class="pdf_button"  id="viewPdf" name="viewPdf" value="" style="display: ${fileName eq '' || fileName==null?'none':''}" onclick="getRidelist()">
                                   <input type="hidden" id="clickedButton" value="">
                                   <input type="hidden" id="fileName" name="fileName" value="${fileName}">
                               </form>
                                </td>
                        </tr>

                        </table>
                    </DIV>
                </td>
            </tr>




            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>

        </table>



    </body>
</html>
