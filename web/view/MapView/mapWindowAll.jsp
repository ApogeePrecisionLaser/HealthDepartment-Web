<%-- 
    Document   : mapWindowAll
    Created on : 18 Apr, 2020, 4:33:24 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <script type="text/javascript" language="javascript">
            jQuery(function() {
                $(document).ready(function () {
                    var x = $.trim(document.getElementById("latti").value);
                    var y = $.trim(document.getElementById("longi").value);
                });
            });



            var markers = [];
            var waypoints = [];
            var map;
            var start;
            var end;

            var directionsService = new google.maps.DirectionsService();

            function initialize() {

                var mapOptions = {
                    zoom: 10,
                    center: new google.maps.LatLng(23.443197840375497, 78.593334362109400),
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


//              var polylineOptions = {path : locations};
//              var polyLine = new google.maps.Polyline(polylineOptions);
//              polyLine.setMap(map);

            

                for (var i=0; i < locations.length; i++) {
                    var condition = $("#active"+i).val();
                    var   mark =   "img/marker-gold.png" ;
                  
                    markers[i] = new google.maps.Marker({
                        position: locations[i],
                        map: map,
                        icon:mark
                      //  url: 'ts_statusShowerCont?task=getLatestMapSignal',
                       // title: "Junction Name: "+$("#junction_name"+i).val()+" \n No of sides: "+$("#no_of_sides"+i).val()+" \n No of Plans: "+$("#no_of_plans"+i).val()+" \n Active: "+$("#active"+i).val()
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

           function getLatestStatus(){

           $.ajax({url: "ts_statusShowerCont", async: true, data: "task=getLatestStatus", dataType:'json', success: function(response_data) {
                        //alert(response_data);
                        if(response_data == ''){
                        }else{
                            $("#junction_id").val(response_data.junction_id);
                            $("#program_version_no").val(response_data.program_version_no);
                            $("#fileNo").val(response_data.fileNo);
                            $("#functionNo").val(response_data.functionNo);
                        }
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
        <input type="hidden" id="count" value="${size}">
    </body>
</html>
