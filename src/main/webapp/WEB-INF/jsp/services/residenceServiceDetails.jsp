<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<petclinic:layout pageName="residences">
    <jsp:attribute name="customScript">
        <!-- API KEY = AIzaSyAZZgwolBSoFe3hRQgnhxNxKCJPQIvgOig -->
        <style>
            /* Always set the map height explicitly to define the size of the div
             * element that contains the map. */
             
            #map {
              height: 50vh;
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

        <h2><b><c:out value="${residence.name}"/></b></h2>
        <table class="table table-striped">
        <tr>
                 <tr>
                <th><strong>Address:</strong></th>
                <td><c:out value="${residence.address}"/></td>
            </tr>
             <tr>
                <th><strong>Price:</strong></th>
                <td><c:out value="${residence.price}"/></td>
            </tr>
            <tr>
                <th><strong>Open:</strong></th>
                <td><c:out value="${residence.open}"/></td>
            </tr>
            <tr>
                <th><strong>Close:</strong></th>
                <td><c:out value="${residence.close}"/></td>
            </tr>
             <tr>
                <th><strong>Rating:</strong></th>
                <td><c:out value="${residence.rating}"/></td>
            </tr>
             <tr>
                <th><strong>Max allowed pets:</strong></th>
                <td><c:out value="${residence.max}"/></td>
            </tr>
            <tr>
                <th><strong>Description:</strong></th>
               <td><c:out value="${residence.description}"/></td>
            </tr>
          </table>
            <br>
            <br>
             <spring:url value="/createRequest/residence/{residenceId}" var="reqUrl">
                    <spring:param name="residenceId" value="${residence.id}"/>
                </spring:url>
              <a href="${fn:escapeXml(reqUrl)}" class="btn btn-default">Make a request</a>
          <h2>Where are we?</h2>
          <div data-residenceaddress="${residence.address}" id="map"></div>

          <script>
            var map;
            var service;
            var infowindow;
            const residenceAddress = document.getElementById("map").dataset.residenceaddress;
    
            function initMap() {
            var city = new google.maps.LatLng(40.421241,-3.7542552);
    
            infowindow = new google.maps.InfoWindow();
    
            map = new google.maps.Map(
                    document.getElementById('map'), {center: city, zoom: 15}
                );
    
            var request = {
                query: residenceAddress,
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
