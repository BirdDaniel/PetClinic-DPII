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

</petclinic:layout>