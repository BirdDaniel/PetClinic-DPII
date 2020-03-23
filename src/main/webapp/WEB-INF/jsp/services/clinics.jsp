<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="clinics">
	
	<br/>
	<br/>
	<br/>
	
	<h2>Clinics: </h2>
	<table id="clinicTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Type</th>
            <th>Address</th>
            <th>Price</th>
            <th>Rating</th>
            <th>Open</th>
            <th>Close</th>
            <th>Max</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clinics}" var="clinic">
            <tr>
             	<td>
                    <c:out value="${clinic.type}"/>
                </td>
                <td>
                    <c:out value="${clinic.address}"/>
                </td>
                <td>
                    <c:out value="${clinic.price}"/>
                </td>
                <td>
                    <c:out value="${clinic.rating}"/>
                </td>
                <td>
                    <c:out value="${clinic.open}"/>
                </td>
                <td>
                 <c:out value="${clinic.close}"/>
                 </td>
                 <td>
                 <c:out value="${clinic.max}"/>
                 </td>     
                  <!--       <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"></a> -->
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
</petclinic:layout>