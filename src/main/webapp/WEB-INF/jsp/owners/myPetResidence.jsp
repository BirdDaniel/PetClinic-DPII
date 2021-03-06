<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<petclinic:layout pageName="residences">
	
	<br/>
	<br/>
	<br/>
	
	<h2>Pets in Residence: </h2>
	<table class="table table-striped">
        <c:if test="${empty requests}">
            <p>
                You don't have any pet in a Residence!
            </p>
        </c:if>
        <c:forEach var="request" items="${requests}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Pet Name</dt>
						<dd><c:out value = "${request.pet.name}"></c:out></dd>
						<dt>Service Date</dt>
						<dd><javatime:format value="${request.serviceDate}" pattern="yyyy/MM/dd HH:mm"/></dd>
						<dt>Finish Date</dt>
                        <dd><javatime:format value="${request.finishDate}" pattern="yyyy/MM/dd HH:mm"/></dd>
                        <dt>Employee</dt>
						<dd><c:out value="With: ${request.employee.firstName} ${request.employee.lastName}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <spring:url value="/owners/{ownerId}/myRequestList/{requestId}/details" var="serviceUrl">
                    	<spring:param name="ownerId" value="${request.owner.id}"/>
                    	<spring:param name="requestId" value="${request.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(serviceUrl)}" class="btn btn-default">Residence Details</a>
                </td>
            </tr>

        </c:forEach>
    </table>	
	
	
</petclinic:layout>