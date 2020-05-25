<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="clinics">
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

        <td valign="top">
        
            <h2><c:out value="${clinic.type.name}"/></h2>
            
            <table class="table table-striped">
                <tr>
                   <th> <strong>Address-Room:</strong> </th>
                    <td><c:out value="${clinic.address}"/></td>
                    
                </tr>
             
          <!--       <c:forEach var="employee" items="${clinic.employees}">
                    <tr>
                        <th><strong>Employee:</strong> </th>
                        <td><c:out value="${employee.firstName} ${employee.lastName}"/></td>
                    </tr>
                </c:forEach>
                --> 
            <tr>
                <tr>
                    <th><strong>Description:</strong></th>
                    
                    <td><c:out value="${clinic.description}"/></td>
                </tr>
                <tr>
                    <th><strong>Max pets:</strong></th>
                    <td><c:out value="${clinic.max}"/></td>
                </tr>
                <tr>
                    <th><strong>Open:</strong></th>
                    <td><c:out value="${clinic.open}" /></td>
                </tr>
                <tr>
                    <th><strong>Close:</strong></th>
                    <td><c:out value="${clinic.close}"/></td>
                </tr>
                
           
                <tr>
                    <th><strong>Rating:</strong></th>
                    <td><c:out value="${clinic.rating} by the clients"/></td>
                </tr>
                <tr>
                    <th><strong>Total price:</strong></th>
                    <td><c:out value="${clinic.price}"/></td>
                </tr>
                
                <c:forEach var="payment" items="${clinic.payments}">
                    <tr>
                        <th><strong>Pay: </strong></th>
                        
                        <td><c:out value="${payment.pay}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <spring:url value="/createRequest/clinic/{clinicId}" var="reqUrl">
                    <spring:param name="clinicId" value="${clinic.id}"/>
                </spring:url>
              <a href="${fn:escapeXml(reqUrl)}" class="btn btn-default">Make a request</a>
              
             <spring:url value="/pay/{clinicId}" var="payUrl">
                    <spring:param name="clinicId" value="${clinic.id}"/>
                </spring:url>
              <a href="${fn:escapeXml(payUrl)}" class="btn btn-default">Pay with PayPal</a>
        
              <h2>Where are we?</h2>
              <div data-clinicaddress="${clinic.address}" id="map"></div>

              <script>
                var map;
                var service;
                var infowindow;
                const clinicAddress = document.getElementById("map").dataset.clinicaddress;
        
                function initMap() {
                var city = new google.maps.LatLng(40.421241,-3.7542552);
        
                infowindow = new google.maps.InfoWindow();
        
                map = new google.maps.Map(
                        document.getElementById('map'), {center: city, zoom: 15}
                    );
        
                var request = {
                    query: clinicAddress,
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