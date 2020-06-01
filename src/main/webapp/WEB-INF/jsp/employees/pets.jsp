<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="pets">


	<br/>
	<br/>
	<br/>
	
	<h2>Pets: </h2>
	<table id="petTable" class="table table-striped">
        <thead>
         <tr>
         
        	<th>Name</th>
            <th>Owner</th>
            <th>Type</th>
   
    
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pets">
        	<tr>
        		<td>
        		   <spring:url value="/employee/{employeeId}/pets" var="petsUrl">
                         <spring:param name="petId" value="${pets.id}"/>
                     </spring:url>
                      <c:out value=" ${pets.name}"/>
        		</td>
        		<td>
        		  <c:out value="${pets.owner.firstName} ${pets.owner.lastName}"/>
        		</td>
        		<td>
        		<c:out value="${pets.type}"/>
        		</td>
        	
        
            </tr>
        </c:forEach>
            </table>
    
	
</petclinic:layout>

