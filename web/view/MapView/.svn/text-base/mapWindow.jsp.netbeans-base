<%-- 
    Document   : mapWindow
    Created on : Sep 29, 2014, 11:36:35 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <script type="text/javascript" language="javascript">
             jQuery(function() {
                $(document).ready(function () {
                    var x = $.trim(document.getElementById("latti").value);
                    var y = $.trim(document.getElementById("longi").value);
                    openMap(y, x);
                   // openMap(79.9333, 23.1667);
                 
                });
             });

            function openMap(longitude, lattitude)
            {
               
                var myCenter=new google.maps.LatLng(lattitude,longitude);
                var mapProp = {
                    center:myCenter,
                    zoom:10,
                    mapTypeId:google.maps.MapTypeId.ROADMAP
                };
                var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

                var marker=new google.maps.Marker({
                    position:myCenter
                });
                marker.setMap(map);
            }
        </script>
    </head>
    <body>
        <div id="googleMap" style="width:600px;height:550px;"></div> 
        <input type="hidden" id="longi" value="${longi}" >
        <input type="hidden" id="latti" value="${latti}" >
    </body>
</html>
