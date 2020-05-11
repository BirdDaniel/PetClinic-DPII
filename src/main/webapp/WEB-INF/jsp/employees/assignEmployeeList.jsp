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
<petclinic:layout pageName="assign">
	<br/>
	<br/>
	<br/>
	
	<h2>Colleagues: </h2>
	<table id="assignTable" class="table table-striped">
        <thead>
         <tr>
         <th>Id</th>
        	<th>First Name</th>
            <th>Last Name</th>
            <th>DNI</th>
            <th>Telephone</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employees">
        	<tr>
        		<td>
        		<c:out value="${employees.id}"/>
        		</td>
        		<td>
        		<c:out value="${employees.firstName}"/>
        		</td>
        		<td>
        		<c:out value="${employees.lastName}"/>
        		</td>
        		<td>
        		<c:out value="${employees.dni}"/>
        		</td>
        		<td>
        		<c:out value="${employees.telephone}"/>
        		</td>
        		 <td>
                     <spring:url value="/employees/{employeeId}/{action}/{requestId}/assigned" var="assignUrl">
                         <spring:param name="action" value="request"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                       
                        </td>
        </c:forEach>
       
            </table>
    
	
</petclinic:layout>
