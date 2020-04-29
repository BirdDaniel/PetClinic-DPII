<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<petclinic:layout pageName="appointmentsOwner">
    <table id="requestsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Request Date</th>
            <th style="width: 150px;">Service Date</th>
            <th style="width: 200px;">Pet</th>
            <th style="width: 200px;">Owner</th>
            <th style="width: 250px">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request">
            <tr>
              <td><javatime:format value="${request.requestDate}" pattern="yyyy/MM/dd HH:mm"/></td>

              <td><javatime:format value="${request.serviceDate}" pattern="yyyy/MM/dd HH:mm"/></td>

                 
                <td>
                    <c:out value="${request.pet.name}"/>
                </td>
                <td>
                   <c:out value="${request.owner.firstName} ${request.owner.lastName}"/>
                </td>    
                <td> 
                     <c:out value="Accepted" />
                </td>             
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>