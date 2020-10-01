<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Find a route using Geolocation and Google Maps API</title>
     <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0"></script>
       
      <script src="https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyCrbBOSEAUcnJfoLVochZlp92HZROIzXSQ"></script>
          <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script>
        
        
          jQuery(function() {
                $(document).ready(function () {
                    var x = $.trim(document.getElementById("latti").value);
                    var y = $.trim(document.getElementById("longi").value);
                });
            

   var geocoder;
var map;
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var locations=[];
   var coordinateCount = $("#count").val();
                var array = new Array();
                for(var i = 1; i <= coordinateCount; i++){
                    array=[];
                    //alert($("#longi"+i).val() +" "+ $("#lati"+i).val());
                   // array[i] = new google.maps.LatLng($("#lati"+i).val(),$("#longi"+i).val());
                  var lat= $("#lati"+i).val() ;
                  var lon= $("#longi"+i).val() ;
                  
                  var loc;
                  var name1="n";
                  var name2="n2";
                  loc="['Name',"+lat+","+lon+",'Name2']";
                  //alert("location ---"+loc);
                  
                   //array[i-1]=lat+","+lon;
                  //  array[i-1] = new google.maps.LatLng(lat+","+lon);
                     //locations.push(loc);
                    // alert("loca array --"+locations);
                   // locations.push("['Name',"+lat+","+lon+",'Name2']");
                    locations.push(lat+","+lon);
                }
                //alert("loc array all ----"+locations);
               // alert("length  -"+locations);
//                alert("length 1 -"+locations[0][1]);
//                alert("length 2 -"+locations[0][2]);
//                alert("length 3 -"+locations[0][3]);
//                alert("length 4 -"+locations[0][4]);
                //alert("split val -"+locations[1].split(","));
//                 for (i = 0; i < locations.length; i++) {
//                      var names = locations[i];
//                      var nameArr = names.split(',');
//                      alert("lat"+nameArr[0]);
//                      alert("lon"+nameArr[1]);
//                 }
               
              // alert(locations);
             //  alert(locations[0][1]);
function initialize() {
  directionsDisplay = new google.maps.DirectionsRenderer();


  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 14,
    center: new google.maps.LatLng(23.92, 24.89),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });
  directionsDisplay.setMap(map);
  var infowindow = new google.maps.InfoWindow();

  var marker, i;
  var request = {
    travelMode: google.maps.TravelMode.DRIVING
  };
   for (i = 0; i < locations.length; i++) {
        var names = locations[i];
                      var nameArr = names.split(',');
                    //  alert("lat"+nameArr[0]);
                   //   alert("lon"+nameArr[1]);
    marker = new google.maps.Marker({
      position: new google.maps.LatLng(nameArr[0], nameArr[1]),
    });

    google.maps.event.addListener(marker, 'click', (function(marker, i) {
      return function() {
        infowindow.setContent(locations[i]);
        infowindow.open(map, marker);
      }
    })(marker, i));

    if (i == 0) request.origin = marker.getPosition();
    else if (i == locations.length - 1) request.destination = marker.getPosition();
    else {
      if (!request.waypoints) request.waypoints = [];
      request.waypoints.push({
        location: marker.getPosition(),
        stopover: true
      });
    }

  }
  directionsService.route(request, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(result);
    }
  });
}
google.maps.event.addDomListener(window, "load", initialize);
});
    </script>
    <style type="text/css">
      #map {
        width: 600px;
        height: 600px;
        margin-top: 0px;
      }
    </style>
  </head>
  <body>
    
    <form id="calculate-route" name="calculate-route" action="#" method="get">
      
          <c:forEach var="Coordinates" items="${requestScope['CoordinatesList']}" varStatus="loopCounter">
            <c:set var="cordinateLength"  value="${loopCounter.count}"></c:set>
            <input type="hidden" id="lati${loopCounter.count}" value="${Coordinates.latitude}">
            <input type="hidden" id="longi${loopCounter.count}" value="${Coordinates.longitude}">
 
        </c:forEach>
        <input type="hidden" id="count" value="${size}">
 
    </form>
    <div id="map"></div>
    <p id="error"></p>
  </body>
</html>