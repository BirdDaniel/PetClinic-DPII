<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<petclinic:layout pageName="requestsEmployee">
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
        <c:forEach items="${requests}" var="request">
            <tr>
            <td><fmt:formatDate value="${request.requestDate}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
            
            <td><javatime:format value="${request.serviceDate}" pattern="yyyy/MM/dd HH:mm"/></td>
                 
                <td>
                    <c:out value="${request.pet.name}"/>
                </td>
                <td>
                    <c:out value="${request.owner.firstName} ${request.owner.lastName}"/>
                </td>
                <td>
                    
                        <spring:url value="/employees/{employeeId}/{action}/{requestId}/accept" var="acceptUrl">
                         <spring:param name="action" value="requests"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                        <spring:url value="/employees/{employeeId}/{action}/{requestId}/decline" var="declineUrl">
                         <spring:param name="action" value="requests"/>
                            <spring:param name="requestId" value="${request.id}"/>
                            <spring:param name="employeeId" value="${request.employee.id}"/>
                        </spring:url>
                       
                        
                        <c:if test="${request.status == null}">
                         <c:out value="Pending  "/>       
                        <a href="${fn:escapeXml(acceptUrl)}" class="btn btn-success">Accept</a>

                        </c:if>
                        <c:if test="${request.status == true}">
                            <c:out value="Accepted"/>
                        </c:if>
                        <c:if test="${request.status == false}">
                            <c:out value="Declined"/>
                        </c:if>
                        |
                        <c:if test="${request.status != true}">
                         </c:if>
                          <c:if test="${request.status != false}">
                        <a href="${fn:escapeXml(declineUrl)}" class="btn btn-danger">Decline</a>
                        </c:if>
                        
                    
                </td>
        <%-- 		       <td>
                                <spring:url value="/employees/{employeeId}/requests/{requestId}/edit" var="requestUrl">
                                    <spring:param name="employeeId" value="${request.employee.id}"/>
                                    <spring:param name="requestId" value="${request.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(requestUrl)}">Edit Request</a>
                            </td>
     	 --%>	
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