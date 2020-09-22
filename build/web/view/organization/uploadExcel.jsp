
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <script type="text/javascript" language="javascript">
            jQuery(function(){
             
            });

            function viewDriverImage1(){
                var vehicle_no=$("#vehicle_no").val();
                var image_type=$("#image_type1").val();
                var queryString = "task=viewImage&image_type="+image_type+"&vehicle_no="+vehicle_no;
                var url = "UploadImageCont.do?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }

    function openCurrentMap() {
       
                         //   var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                          //  var y = longitude;//$.trim(doc.getElementById(logitude).value);
                            var url="UploadExcelCont.do?task1=showMapWindow";
                            window.open(url);
                            //popupwin = openPopUp(url, "",  580, 620);
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
                                </table>
                            </td></tr>
      
                        <form  action="UploadExcelCont.do" method="post" enctype="multipart/form-data">
                            <table  align="center"  class="content" cellpadding="2%" border="1">
                                <tr>
                                    <td colspan="4" align="center" class="header_table" width="100%"><b>Upload Image</b></td>
                                </tr>
                                <tr id="message">
                                    <c:if test="${not empty message}">
                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                    </c:if>
                                </tr>

                                <tr>
                                    <th class="heading1">Upload Excel</th>
       
                                    <td colspan="2">
                                        <input type="file" name="file" size="50" />
                                    </td>
                                <tr>
                                    <td colspan="4" align="center">
                                        <input type="reset" name="" value="Reset"  >
                                        <input type="submit" value="Upload File" name="task"/>
                                        <input id="map" class="" type="button" onclick="openCurrentMap();" value="Map">
                                    </td>
                                </tr>


                            </table>
                        </form>
                    </table>
                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
