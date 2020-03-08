<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="requests">

    <br/>
    <br/>
    <br/>
    <h2>Request List</h2>

    <table class="table table-striped">
        <c:forEach var="request" items="${owner.requests}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Date</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Services</th>
                        </tr>
                        </thead>
    
                        <c:forEach var="service" items="${request.services}">
                            <tr>
                            	<td>Price: </td>
                                <td><c:out value="${service.price}"/></td>
                            </tr>
                            <tr>
                            	<td>Address: </td>
                                <td><c:out value="${service.address}"/></td>
                                <td><c:out value="${service.room}"/></td>
                            </tr>
                            <tr>
                            	<td>Price: </td>
                                <td><c:out value="${service.price}"/></td>
                            </tr>
                            <tr>
                            	<td>Doctor: </td>
                                <td><c:out value="${service.employee.name} ${service.employee.lastName}"/></td>
                            </tr>
                        </c:forEach>
                     </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
