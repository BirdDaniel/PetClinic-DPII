<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clinics">
	
	<br/>
	<br/>
	<br/>
	
	<h2>Clinics: </h2>
	<table id="clinicTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Type</th>
        	<th>Name</th>
            <th>Address</th>
            <th>Price</th>
            <th>Rating</th>
            <th>Max Pets</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clinics}" var="clinics">
            <tr>
             	<td>
                    <c:out value="${clinics.type}"/>
                </td>
                <td> 
                     <spring:url value="/clinic/{clinicId}" var="clinicUrl">
                         <spring:param name="clinicId" value="${clinics.id}"/>
                     </spring:url>
                     <a href="${fn:escapeXml(clinicUrl)}"><c:out value=" ${clinics.name}"/></a>
                </td>
                <td>
                    <c:out value="${clinics.address}"/>
                </td>
                <td>
                    <c:out value="${clinics.price}"/>
                </td>
                <td>
                    <c:out value="${clinics.rating}"/>
                </td>
                 <td>
                 <c:out value="${clinics.max}"/>
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