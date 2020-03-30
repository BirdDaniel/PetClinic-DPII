<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date"/>

<petclinic:layout pageName="appointmentsEmployee">
    <table id="requestsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Request Date</th>
            <th style="width: 150px;">Service Date</th>
            <th style="width: 200px;">Pet</th>
            <th style="width: 200px;">Owner</th>
            <th style="width: 250px">Accept or Decline</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="request">
            <tr>
                <td><fmt:formatDate value="${request.requestDate}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
            
            <td><fmt:formatDate value="${request.serviceDate}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
                 
                <td>
                    <spring:url value="/pets/{petId}" var="petUrl">
                        <spring:param name="petId" value="${request.pet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(petUrl)}"><c:out value="${request.pet.name}"/></a>
                </td>
                <td>
                    <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${request.owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${request.owner.firstName} ${request.owner.lastName}"/></a>
                </td>
                <td>
                    
                        <spring:url value="/employees/{employeeId}/{action}/{requestId}/accept" var="acceptUrl">
                            <spring:param name="action" value="appointments"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                        <spring:url value="/employees/{employeeId}/{action}/{requestId}/decline" var="declineUrl">
                         	<spring:param name="action" value="appointments"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                       
                        
                        
                        <c:if test="${request.status == true}">
                            <c:out value="Accepted"/>
                        </c:if>
                        |
                        <c:if test="${(request.status != false) && (now lt request.serviceDate)}">
                        <a href="${fn:escapeXml(declineUrl)}" class="btn btn-danger">Cancel</a>
                        </c:if>
                        
                    
                </td>
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>