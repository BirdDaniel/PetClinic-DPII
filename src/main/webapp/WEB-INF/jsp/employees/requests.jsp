<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>Owners</h2>

    <table id="requestsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Fecha</th>
            <th style="width: 200px;">Propietario</th>
            <th>City</th>
            <th style="width: 120px">Aceptar</th>
            <th>Pets</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request">
            <tr>
                <td>
                    <c:out value="${request.date_req}"/>
                </td>
                <td>
                    <c:out value="${request.owner.name}"/>
                </td>
                <td>
                    <a>Aceptar</a>
                    <a>Rechazar</a>
                </td> 
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>