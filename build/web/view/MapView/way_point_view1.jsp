<%--
    Document   : way_point_view
    Created on : Jan 18, 2018, 12:52:23 PM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>


<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Waypoints in directions</title>
    <style>
      #right-panel {
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }

      #right-panel select, #right-panel input {
        font-size: 15px;
      }

      #right-panel select {
        width: 100%;
      }

      #right-panel i {
        font-size: 12px;
      }
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
        float: left;
        width: 100%;
        height: 100%;
      }
/*      #right-panel {
        margin: 20px;
        border-width: 2px;
        width: 20%;
        height: 400px;
        float: left;
        text-align: left;
        padding-top: 0;
      }*/
      #directions-panel {
        margin-top: 10px;
        background-color: #FFEE77;
        padding: 10px;
        overflow: scroll;
        height: 174px;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
<!--    <div id="right-panel">-->
<!--    <div>
    <b>Start:</b>
    <select id="start">
      <option value="Halifax, NS">Halifax, NS</option>
      <option value="Boston, MA">Boston, MA</option>
      <option value="New York, NY">New York, NY</option>
      <option value="Miami, FL">Miami, FL</option>
    </select>
    <br>
    <b>Waypoints:</b> <br>
    <i>(Ctrl+Click or Cmd+Click for multiple selection)</i> <br>
    <select multiple id="waypoints">
      <option value="montreal, quebec">Montreal, QBC</option>
      <option value="toronto, ont">Toronto, ONT</option>
      <option value="chicago, il">Chicago</option>
      <option value="winnipeg, mb">Winnipeg</option>
      <option value="fargo, nd">Fargo</option>
      <option value="calgary, ab">Calgary</option>
      <option value="spokane, wa">Spokane</option>
    </select>
    <br>
    <b>End:</b>
    <select id="end">
      <option value="Vancouver, BC">Vancouver, BC</option>
      <option value="Seattle, WA">Seattle, WA</option>
      <option value="San Francisco, CA">San Francisco, CA</option>
      <option value="Los Angeles, CA">Los Angeles, CA</option>
    </select>
    <br>
      <input type="submit" id="submit">
    </div>-->
    <div id="directions-panel"></div>

    <script>
      function initMap() {
        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 7,
          center: {lat: 23.85, lng: 80.65}
        });
        directionsDisplay.setMap(map);

       // document.getElementById('submit').addEventListener('click', function() {
          calculateAndDisplayRoute(directionsService, directionsDisplay);
        //});
      }

      function calculateAndDisplayRoute(directionsService, directionsDisplay) {


        var vkp_id=${vehicle_key_person_id};
       // alert(vkp_id);
      var waypts = [];
      var checkboxArray=[];
      var b;
      var str="";
      var heatlati;
      var heatlongi;
      var taillati;
      var taillongi;
     // var output= "[";
          $.ajax({
              dataType: "json",
              async : false,
              url: "VehicleKeyPersonPoint?task=getAllCordinates",
              data:{data: vkp_id},
              success: function(response_data) {
              b=response_data.map;
              //alert(b);

             for(var i=1;i<b.length-1;i++){
                  // alert(b[i]["latitude"]);
                   //alert(b[i]["longitude"]);
                   heatlati=b[0]["latitude"];
                   //alert(b[0]["latitude"]);
                   heatlongi=b[0]["longitude"];
                   //alert(b[0]["longitude"]);
                   taillati=b[b.length-1]["latitude"];
                   //alert(b[b.length-1]["latitude"]);
                   taillongi=b[b.length-1]["longitude"];
                   //alert(b[b.length-1]["longitude"]);
//                    str = new google.maps.LatLng(b[i]["latitude"], b[i]["longitude"]);
//                    while(i<b.length-2){
//                    str=str+",";
//                    }
//                  alert(str);
             }
               //     var output= "[";

        for(var i = 1; i < b.length-1; i++)
        {
            var d=i;
            checkboxArray[d-1] = new google.maps.LatLng(b[i]["latitude"],b[i]["longitude"]);

//            if(i<b.length-1){
//                output=output+',';
//            }
        }
        output += "]";
         // alert(output);

                   //calculateAndDisplayRoute(directionsService, directionsDisplay);
		  //initMap1();
      }
        });
        //checkboxArray=str.split(",");
        //checkboxArray=output;
     //   alert("checkboxArray="+checkboxArray);


//        var output= "[";
//        for(var i = 0; i < b.length; i++)
//        {
//            output += new google.maps.LatLng(23.11118, 79.1111157)+',';
//        }
//        output += "]";

        //var waypts = [];
//        var checkboxArray = document.getElementById('waypoints');
//           var checkboxArray = [new google.maps.LatLng(23.11118, 79.1111157),
//                                new google.maps.LatLng(23.222222, 79.22222),
//                                new google.maps.LatLng(23.333333, 79.333333),
//                                new google.maps.LatLng(23.444444, 79.444444),
//                               new google.maps.LatLng(23.555555, 79.55555)];
        for (var i = 0; i < checkboxArray.length; i++) {
          //if (checkboxArray.options[i].selected) {
          //alert(checkboxArray.length);
         // alert( checkboxArray[i]);
            waypts.push({
              location: checkboxArray[i],
              stopover: true
            });
          //}
        }

        directionsService.route({
//          origin: document.getElementById('start').value,
//          destination: document.getElementById('end').value,
            origin:new google.maps.LatLng(heatlati,heatlongi) ,
          destination: new google.maps.LatLng(taillati,taillongi),
          waypoints: waypts,
          optimizeWaypoints: true,
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
            var route = response.routes[0];
            var summaryPanel = document.getElementById('directions-panel');
            summaryPanel.innerHTML = '';
            // For each route, display summary information.
            for (var i = 0; i < route.legs.length; i++) {
              var routeSegment = i + 1;
              summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment +
                  '</b><br>';
              summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
              summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
              summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
            }
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
    </script>
    <script async defer
    src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0">
    </script>
  </body>
</html>