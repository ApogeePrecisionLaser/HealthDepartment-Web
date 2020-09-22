<%--
    Document   : getCordinateMapWindow1
    Created on : Oct 25, 2017, 3:20:44 PM
    Author     : Com7_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            #map {
         height: 100%;
        float: left;
        width: 100%;
        height: 100%;

      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #floating-panel {
        position: absolute;
        top: 10px;
        left: 25%;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
        text-align: center;
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }
	   #right-panel {
        margin: 20px;
        border-width: 2px;
        width: 20%;
        height: 400px;
        float: left;
        text-align: left;
        padding-top: 0;
      }
	   #directions-panel {
        margin-top: 10px;
        background-color: #FFEE77;
        padding: 10px;
        overflow: scroll;
        height: 174px;
        float: left;
      }
        </style>
        <script type="text/javascript" language="javascript">
            var data= [];
            window.onload = function()
            {
                
                        map();
            };
            function map()
            {
                //alert("map function");

                var headlat = ${headlatti};
               // alert(headlat);
                if(headlat == "")
                    headlat = "23.1657228";
                var headlng = ${headlongi};
                if(headlng == "")
                    headlng = "79.9505182";

                var taillat = ${taillatti};
                if(taillat == "")
                    taillat = "23.1657228";
                var taillng = ${taillongi};
                if(taillng == "")
                    taillng = "79.9505182";
               // var latlng = new google.maps.LatLng(lat, lng);//(23.1657228,79.9505182);

                initMap(headlat,headlng,taillat,taillng);

            }

            function initMap(headlat,headlng,taillat,taillng) {

           // alert("initMap function");
        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 7,
          center: {lat: 23.654345,lng: 79.876554}
        });
        directionsDisplay.setMap(map);

		calculateAndDisplayRoute(directionsService, directionsDisplay,headlat,headlng,taillat,taillng);
		  initMap1(headlat,headlng,taillat,taillng);

//        var onChangeHandler = function() {
//          calculateAndDisplayRoute(directionsService, directionsDisplay);
//		  initMap1();
//        };
//        document.getElementById('start').addEventListener('change', onChangeHandler);
//        document.getElementById('end').addEventListener('change', onChangeHandler);
//		//initMap1();
      }

      function calculateAndDisplayRoute(directionsService, directionsDisplay,headlat,headlng,taillat,taillng) {
          //alert("calculateAndDisplayRoute");
        directionsService.route({
          origin: {lat: headlat,lng: headlng},
          destination: {lat: taillat,lng: taillng},
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
	   function initMap1(headlat,headlng,taillat,taillng) {

          // alert("initMap1");
        var bounds = new google.maps.LatLngBounds;
        var markersArray = [];

        var origin1 = {lat: headlat,lng: headlng};
        //var origin2 = 'Greenwich, England';
        //var destinationA = 'Stockholm, Sweden';
        var destinationB = {lat: taillat,lng: taillng};

        var destinationIcon = 'https://chart.googleapis.com/chart?' +
            'chst=d_map_pin_letter&chld=D|FF0000|000000';
        var originIcon = 'https://chart.googleapis.com/chart?' +
            'chst=d_map_pin_letter&chld=O|FFFF00|000000';
//        var map = new google.maps.Map(document.getElementById('map'), {
//          center: {lat: 22.654345,lng: 78.876554},
//          zoom: 10
//        });
        var geocoder = new google.maps.Geocoder;

        var service = new google.maps.DistanceMatrixService;
        service.getDistanceMatrix({
          origins: [origin1],
          destinations: [destinationB],
          travelMode: 'DRIVING',
          unitSystem: google.maps.UnitSystem.METRIC,
          avoidHighways: false,
          avoidTolls: false
        }, function(response, status) {
          if (status !== 'OK') {
            alert('Error was: ' + status);
          } else {
            var originList = response.originAddresses;
            var destinationList = response.destinationAddresses;
            var outputDiv = document.getElementById('directions-panel');
            outputDiv.innerHTML = '';
            //dynamicMarkers(markersArray);

            var showGeocodedAddressOnMap = function(asDestination) {
              var icon = asDestination ? destinationIcon : originIcon;
              return function(results, status) {
                if (status === 'OK') {
                  map.fitBounds(bounds.extend(results[0].geometry.location));
                  //markersArray.push(new google.maps.Marker({
                    //map: map,
                    //position: results[0].geometry.location,
                    //icon: icon
                  //}));
                } else {
                  alert('Geocode was not successful due to: ' + status);
                }
              };
            };

            for (var i = 0; i < originList.length; i++) {
              var results = response.rows[i].elements;
              geocoder.geocode({'address': originList[i]},
                  showGeocodedAddressOnMap(false));
              for (var j = 0; j < results.length; j++) {
                geocoder.geocode({'address': destinationList[j]},
                    showGeocodedAddressOnMap(true));
                outputDiv.innerHTML += originList[i] + ' to ' + destinationList[j] +
                    ': ' + results[j].distance.text + ' in ' +
                    results[j].duration.text + '<br>';
              }
            }
          }
        });
		//dynamicMarkers(markersArray);
      }

      function dynamicMarkers(markersArray) {
	  alert("dynamicMarkers");
	  var marker = new google.maps.Marker({
    position: {lat: 23.6543556,lng: 79.876554},
    map: map
    //title: 'Uluru (Ayers Rock)'
  });
  marker.setMap(map);

       // for (var i = 0; i < markersArray.length; i++) {
         // markersArray[i].setMap(null);
        //}
       // markersArray = [];
      }

        </script>
    </head>
    <body>
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0"></script>
<!--        <input type="button" value="Close" onclick="window.close();">-->
        <div id="map"></div>
    
	 <div id="directions-panel"></div>
        <input type="hidden" id="headlongi" value="${headlongi}" >
        <input type="hidden" id="headlatti" value="${headlatti}" >
        <input type="hidden" id="taillongi" value="${taillongi}" >
        <input type="hidden" id="taillatti" value="${taillatti}" >
    </body>
</html>
