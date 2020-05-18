<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="parks">
    <jsp:attribute name="customScript">
        <!-- API KEY = AIzaSyAZZgwolBSoFe3hRQgnhxNxKCJPQIvgOig -->
        <style>
            /* Always set the map height explicitly to define the size of the div
             * element that contains the map. */
             .cont {
                 display: flex;
                 justify-content: space-between;
             }
             .right {
                 height: 50vh;
                 width: 40%;
                 display: flex;
                 flex-direction: column;
                 justify-content: center;
             }
             h2 {
                 transform: translateY(-30px);
             }
            #map {
              height: 50vh;
              flex: 0 0 55%;
            }
            /* Optional: Makes the sample page fill the window. */
            html, body {
              height: 100%;
              margin: 0;
              padding: 0;
            }
          </style>
        
    </jsp:attribute>
    <jsp:body>
	<br/>
	<br/>
	
	<h2>Park: <c:out value="${park.name}" /> (<c:out value="${park.address}" />) </h2>
    <div class="cont">
    <div data-parkaddr="${park.address}" class="map" id="map"></div>
    <div class="right">
        <h2>
            Isn't this the park you're looking for? Add a park in your area and help other owners discover new places!
        </h2>
        <spring:url value="/parks/new" var="addParkUrl"/>
        <a href="${fn:escapeXml(addParkUrl)}" class="btn btn-default">Add a Park!</a>
    </div>

    </div>

    <script>
        var map;
        var service;
        var infowindow;
        const parkAddress = document.getElementById("map").dataset.parkaddr;

        function initMap() {
        var city = new google.maps.LatLng(40.421241,-3.7542552);

        infowindow = new google.maps.InfoWindow();

        map = new google.maps.Map(
                document.getElementById('map'), {center: city, zoom: 15}
            );

        var request = {
            query: parkAddress,
            fields: ['name', 'geometry'],
        };

        service = new google.maps.places.PlacesService(map);

        service.findPlaceFromQuery(request, function(results, status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    createMarker(results[i]);
                }
                map.setCenter(results[0].geometry.location);
            }
        });
        }

        function createMarker(place) {
        var marker = new google.maps.Marker({
          map: map,
          position: place.geometry.location
        });

        google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(place.name);
          infowindow.open(map, this);
        });
      }
      </script>
      <script src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAZZgwolBSoFe3hRQgnhxNxKCJPQIvgOig&callback=initMap"
      async defer></script>
    </jsp:body>

</petclinic:layout>