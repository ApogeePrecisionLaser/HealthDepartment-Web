<%-- 
    Document   : node_detail_map_view1
    Created on : Jan 17, 2018, 3:23:17 PM
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
    <title>Directions service</title>
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
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
      }
    </style>
  </head>
  <body>
    <div id="floating-panel">
    <b>Choose Road: </b>
    <select id="start">
        <option value="">Select</option>
         <c:forEach var="list" items="${requestScope['list']}">
      <option value="${list.node_name}">${list.node_name}</option>
         </c:forEach>
    </select>
<!--    <b>End: </b>
    <select id="end">
      <option value="chicago, il">Chicago</option>
      <option value="st louis, mo">St Louis</option>
      <option value="joplin, mo">Joplin, MO</option>
      <option value="oklahoma city, ok">Oklahoma City</option>
      <option value="amarillo, tx">Amarillo</option>
      <option value="gallup, nm">Gallup, NM</option>
      <option value="flagstaff, az">Flagstaff, AZ</option>
      <option value="winona, az">Winona</option>
      <option value="kingman, az">Kingman</option>
      <option value="barstow, ca">Barstow</option>
      <option value="san bernardino, ca">San Bernardino</option>
      <option value="los angeles, ca">Los Angeles</option>
    </select>-->
    </div>
    <div id="map"></div>
<!--    <div id="right-panel"></div>-->
	 <div id="directions-panel"></div>
    <script>
      function initMap() {
        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 7,
          center: {lat: 23.654345,lng: 79.876554}
        });
        directionsDisplay.setMap(map);

        var onChangeHandler = function() {
       var temp=document.getElementById('start').value;
       
      $.ajax({url: "generalCont.do?task=checkSubPointExits&temp="+temp, success: function(response_data) {
              var b=response_data;
              
              var array = b.split(",");
              var head_lati=parseFloat(array[0]);
              var head_longi=parseFloat(array[1]);
              var tail_lati=parseFloat(array[2]);
              var tail_longi=parseFloat(array[3]);

                   calculateAndDisplayRoute(directionsService, directionsDisplay,head_lati,head_longi,tail_lati,tail_longi);
		  initMap1(head_lati,head_longi,tail_lati,tail_longi);
      }
      });

        };
        document.getElementById('start').addEventListener('change', onChangeHandler);
      }

      function calculateAndDisplayRoute(directionsService, directionsDisplay,head_lati,head_longi,tail_lati,tail_longi) {
       directionsService.route({
          origin: {lat: head_lati,lng: head_longi},
          destination: {lat: tail_lati,lng: tail_longi},
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);
          } else {
            //window.alert('Directions request failed due to ' + status);
          }
        });
      }
	   function initMap1(head_lati,head_longi,tail_lati,tail_longi) {
        var bounds = new google.maps.LatLngBounds;
        var markersArray = [];

        var origin1 = {lat: head_lati,lng: head_longi};
        //var origin2 = 'Greenwich, England';
        //var destinationA = 'Stockholm, Sweden';
        var destinationB = {lat: tail_lati,lng: tail_longi};

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
            //alert('Error was: ' + status);
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
                  //alert('Geocode was not successful due to: ' + status);
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
    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&callback=initMap"></script>
  </body>
</html>