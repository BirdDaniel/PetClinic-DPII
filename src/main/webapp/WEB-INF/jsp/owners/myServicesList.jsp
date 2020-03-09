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
                        <dd><c:out value="With ${request.employee.specialty.name}: ${request.employee.firstName} ${request.employee.lastName}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Services</th>
                        </tr>
                        </thead>
                        <tr>
                        	<td>Price: </td>
                        	<td>Address: </td>
                        	<td>Price: </td>
                        <tr>
                        
                        <c:forEach var="service" items="${request.services}">
                            <tr>
                            	<td><c:out value="${service.price}"/></td>
                            	<td><c:out value="${service.address}: ${service.room}"/></td>
                            	<td><c:out value="${service.price}"/></td>
                            </tr>
                        </c:forEach>
                     </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
