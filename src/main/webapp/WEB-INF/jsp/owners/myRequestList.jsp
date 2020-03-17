<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="requests">
	
	<br/>
	<br/>
	<br/>
	
	<h2>Request List: </h2>
	<table class="table table-striped">
        <c:forEach var="request" items="${owner.requests}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Date</dt>
						<dd><petclinic:localDate date="${request.date}" pattern="yyyy-MM-dd"/></dd>
						<dt>Employee</dt>
						<dd><c:out value="With: ${request.employee.firstName} ${request.employee.lastName}"/></dd>
						<dt>Status</dt>
						<dd>
							<c:choose>
								<c:when test="${status==null}">
									<c:out value="pendding"></c:out>
								</c:when>
								<c:when test="${status==true }">
									<c:out value="accepted"></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="accepted"></c:out>
								</c:otherwise>
							</c:choose>
						</dd>
                    </dl>
                </td>
                <td valign="top">
                    <spring:url value="/owners/myRequestList/{ownerId}/details/{requestId}" var="serviceUrl">
                    	<spring:param name="ownerId" value="${owner.id}"/>
                    	<spring:param name="requestId" value="${request.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(serviceUrl)}" class="btn btn-default">Show Service</a>
                </td>
            </tr>

        </c:forEach>
    </table>	
	
	
</petclinic:layout>