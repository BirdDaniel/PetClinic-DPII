<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="parks">
	
	<br/>
	<br/>
	
	<h2>Parks: </h2>
	<table id="parksTable" class="table table-dark table-striped">
        <thead>
        <tr>
       		<th>Name</th>
            <th>Address</th>
            <th>Edit | Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${parks}" var="park">
            <tr>
             	<td> 
                    <spring:url value="/parks/{parkId}" var="parkUrl">
                         <spring:param name="parkId" value="${park.id}"/>
                     </spring:url>
                    <a href="${fn:escapeXml(parkUrl)}"><c:out value=" ${park.name}"/></a>
                </td>
                <td>
                    <c:out value="${park.address}"/>
                </td> 
                <td>
                    <spring:url value="/parks/{parkId}/edit" var="parkEditUrl">
                         <spring:param name="parkId" value="${park.id}"/>
                     </spring:url>
                    <c:if test="${park.owner.id eq loggedUser}">
                <a href="${fn:escapeXml(parkEditUrl)}" class="btn btn-default">
                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                </a>
                <spring:url value="/parks/{parkId}/delete" var="parkDeleteUrl">
                         <spring:param name="parkId" value="${park.id}"/>
                     </spring:url>
                <a href="${fn:escapeXml(parkDeleteUrl)}" class="btn btn-danger">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                </a>
            </c:if>
                </td>  
            </tr>

        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/parks/new" var="addParkUrl"/>
    <a href="${fn:escapeXml(addParkUrl)}" class="btn btn-default">Add Park</a>
	
</petclinic:layout>