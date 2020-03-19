<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="clinics">
<td valign="top">

	<h2><c:out value="${clinic.type.name}"/></h2>
    <table class="table table-striped">
        <tr>
            <th>Address-Room: </th>
            <td><b><c:out value="${clinic.address}"/></b></td>
        </tr>
        
        <c:forEach var="employee" items="${clinic.employees}">
        	<tr>
            	<th>Employee: </th>
            	<td><c:out value="${employee.firstName} ${employee.lastName}"/></td>
        	</tr>
        </c:forEach>
    </table>
    
    <h2>Details</h2>
	
	<table class="table table-striped">
    <tr>
    	<tr>
            <th>Description:</th>
            <td><c:out value="${clinic.description}"/></td>
        </tr>
        <tr>
            <th>Max pets:</th>
            <td><c:out value="${clinic.max}"/></td>
        </tr>
        <tr>
            <th>Open:</th>
            <td><fmt:formatDate value="${clinic.open}" type="date" pattern="HH:mm"/></td>
        </tr>
        <tr>
            <th>Close:</th>
            <td><fmt:formatDate value="${clinic.close}" type="date" pattern="HH:mm"/></td>
        </tr>
        <tr>
            <th>Rating:</th>
            <td><c:out value="${clinic.rating} stars by the clients"/></td>
        </tr>
		
    </table>
    
    <h2>Details</h2>
    
	<table class="table table-striped">
        
        <tr>
            <th>Total price</th>
            <td><c:out value="${clinic.price}"/></td>
        </tr>
        
        <c:forEach var="payment" items="${clinic.payments}">
        	<tr>
            	<th>Pay: </th>
            	<td><c:out value="${payment.pay}"/></td>
            </tr>
        </c:forEach>
    </table>
</td>
</petclinic:layout>