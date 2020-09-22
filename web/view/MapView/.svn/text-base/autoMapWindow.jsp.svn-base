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
        <META http-equiv="REFRESH" content="60">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <script type="text/javascript" language="javascript">
             jQuery(function() {
                $(document).ready(function () {
                    var x = $.trim(document.getElementById("latti").value);
                    var y = $.trim(document.getElementById("longi").value);
                    //openMap(y, x);
                   // openMap(79.9333, 23.1667);
                 
                });
             });

            function openMap(longitude, lattitude)
            {
               
                //var myCenter=new google.maps.LatLng(lattitude,longitude);
                var myCenter=new google.maps.LatLng(23.164302900000000,79.893905500000000);
                var mapProp = {
                    center:myCenter,
                    zoom:15,
                    mapTypeId:google.maps.MapTypeId.ROADMAP
                };
                var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

                var marker=new google.maps.Marker({
                    position:myCenter
                });
                marker.setMap(map);
            }

            var markers = [];
var waypoints = [];
var map;
var start;
var end;

var directionsService = new google.maps.DirectionsService();

function initialize() {
  var mapOptions = {
    zoom: 6,
    center: new google.maps.LatLng(23.15,79.90),
    travelMode: google.maps.TravelMode.DRIVING
  };

  map = new google.maps.Map(document.getElementById('googleMap'),
      mapOptions);

  var locations1 = [ new google.maps.LatLng(37.7833,-122.4167),
                    new google.maps.LatLng(37.3382,-121.8863),
                    new google.maps.LatLng(37.4223662,-122.0839445),
                    new google.maps.LatLng(37.8694772,-122.2577238),
                    new google.maps.LatLng(37.7919615,-122.2287941),
                    new google.maps.LatLng(37.6256441,-122.0413544) ];
   var coordinateCount = $("#count").val();
   var array = new Array();
   for(var i = 1; i <= coordinateCount; i++){
       //alert($("#longi"+i).val() +" "+ $("#lati"+i).val());
       array[i - 1] = new google.maps.LatLng($("#lati"+i).val(),$("#longi"+i).val());
   }
   var locations = array;
   //locations = [new google.maps.LatLng($("#longi").val(), $("#latti").val())];//(23.153982100000000,79.907076500000000)];//,
//                    new google.maps.LatLng(23.164302900000000,79.893905500000000),
//                    new google.maps.LatLng(23.155434900000000,79.915218900000000)];

    for (var i=0; i < locations.length; i++) {
        markers[i] = new google.maps.Marker({
           position: locations[i],
           map: map,
           url: 'http://www.google.com/',
           title: locations[i].lat()+", "+locations[i].lng()+" "+"<a href='www.google.com'>Google Link</a>"
        });
        //marker.setMap(map);
        //markers.push(marker);
        google.maps.event.addListener(markers[i], 'click', function(marker) {
            console.log(marker);
            //waypoints.push(  );
            //console.log(i+": "+markers[i].getTitle());
            calcRoute(marker.latLng);
        });

        google.maps.event.addListener(markers[i], 'click', function() {
      window.open(this.url);  //changed from markers[i] to this
    });

    }

    directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);
}



for ( i = 0; i < markers.length; i++ ) {
    google.maps.event.addListener(markers[i], 'click', function() {
      window.location.href = this.url;  //changed from markers[i] to this
    });
}


function calcRoute(inLatLng) {
    waypoints.push({location: inLatLng});
    if ( waypoints.length >=2 ) {
      end = inLatLng;
      var waypts = waypoints.slice(1, waypoints.length -1 );

        console.log(start);
        console.log(end);
        console.log(waypts);
      var request = {
          origin: start,
          destination: end,
          waypoints: waypts,
          optimizeWaypoints: false,
          travelMode: google.maps.TravelMode.DRIVING
      };

      directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
          directionsDisplay.setDirections(response);

        }
      });

    } else {
        start = inLatLng;
    }
}

google.maps.event.addDomListener(window, 'load', initialize);

function drawRoute(originAddress, destinationAddress, _waypoints) {

    //Define a request variable for route .

    var _request = '';



    //This is for more then two locatins

    if (_waypoints.length > 0) {

        _request = {

            origin: originAddress,

            destination: destinationAddress,

            waypoints: _waypoints, //an array of waypoints

            optimizeWaypoints: true, //set to true if you want google to determine the shortest route or false to use the order specified.

            travelMode: google.maps.DirectionsTravelMode.DRIVING

        };

    } else {

        //This is for one or two locations. Here noway point is used.

        _request = {

            origin: originAddress,

            destination: destinationAddress,

            travelMode: google.maps.DirectionsTravelMode.DRIVING

        };

    }



    //This will take the request and draw the route and return response and status as output

    directionsService.route(_request, function (_response, _status) {

        if (_status == google.maps.DirectionsStatus.OK) {

            _directionsRenderer.setDirections(_response);

        }

    });

}
        </script>
    </head>
    <body>
        <div id="googleMap" style="width:1450px;height:650px;text-align: center"></div><!--width:1500px;height:650px;-->
        <input type="hidden" id="longi" value="${longi}" >
        <input type="hidden" id="latti" value="${latti}" >        
        <c:forEach var="Coordinates" items="${requestScope['CoordinatesList']}" varStatus="loopCounter">
            <c:set var="cordinateLength"  value="${loopCounter.count}"></c:set>
            <input type="hidden" id="lati${loopCounter.count}" value="${Coordinates.latitude}">
            <input type="hidden" id="longi${loopCounter.count}" value="${Coordinates.longitude}">
        </c:forEach>
            <input type="hidden" id="count" value="${cordinateLength}">
    </body>
</html>
