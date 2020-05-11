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
<petclinic:layout pageName="colleagues">
	<br/>
	<br/>
	<br/>
	
	<h2>Colleagues: </h2>
	<table id="colleagueTable" class="table table-striped">
        <thead>
         <tr>
         <th>Id</th>
        	<th>First Name</th>
            <th>Last Name</th>
            <th>DNI</th>
            <th>Telephone</th>
           <c:if test="${assign == true}">
            <th>Assign</th>
           </c:if>
          
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${colleagues}" var="colleagues">
        	<tr>
        		<td>
        		<c:out value="${colleagues.id}"/>
        		</td>
        		<td>
        		<c:out value="${colleagues.firstName}"/>
        		</td>
        		<td>
        		<c:out value="${colleagues.lastName}"/>
        		</td>
        		<td>
        		<c:out value="${colleagues.dni}"/>
        		</td>
        		<td>
        		<c:out value="${colleagues.telephone}"/>
        		</td>
        		<c:if test="${assign == true}">
        		 <td>
                     <spring:url value="/employees/{employeeId}/{action}/{requestId}/{colleagueId}/reassign" var="assignUrl">
                         <spring:param name="action" value="request"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="colleagueId" value="${colleagues.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                           <c:if test="${request.status != false}">
                            <a href="${fn:escapeXml(assignUrl)}" class="btn btn-assign">Assign</a>
                        </c:if>
                        </td>
                </c:if>
        </c:forEach>
            </table>
    
	
</petclinic:layout>