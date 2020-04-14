<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<petclinic:layout pageName="requestsOwner">
	
	<br/>
	<br/>
	<br/>
	<spring:url value="/owners/{ownerId}/appointments" var="appointmentsUrl">
                    	<spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
	
	<a href="${fn:escapeXml(appointmentsUrl)}" class="btn-default btn">Appointments</a>
	<br>
	<br>
	<h2>Request List: </h2>
	<table class="table table-striped">
        <c:forEach var="request" items="${owner.requests}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Request Date</dt>
						<dd><fmt:formatDate value="${request.requestDate}" type="date" pattern="yyyy/MM/dd HH:mm"/></dd>
						<dt>Service Date</dt>
						<dd><javatime:format value="${request.serviceDate}" pattern="yyyy/MM/dd HH:mm"/></dd>
						<dt>Employee</dt>
						<dd><c:out value="With: ${request.employee.firstName} ${request.employee.lastName}"/></dd>
						<dt>Status</dt>
						<dd>
							<c:choose>
								<c:when test="${request.status==null}">
									<c:out value="pendding"></c:out>
								</c:when>
								<c:when test="${request.status==true }">
									<c:out value="accepted"></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="rejected"></c:out>
								</c:otherwise>
							</c:choose>
						</dd>
                    </dl>
                </td>
                <td valign="top">
                    <spring:url value="/owners/{ownerId}/myRequestList/{requestId}/details" var="serviceUrl">
                    	<spring:param name="ownerId" value="${request.owner.id}"/>
                    	<spring:param name="requestId" value="${request.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(serviceUrl)}" class="btn btn-default">Show Service</a>
                </td>
            </tr>

        </c:forEach>
    </table>	
	
	
</petclinic:layout>