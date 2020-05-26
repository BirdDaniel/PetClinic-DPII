<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<petclinic:layout pageName="appointmentsOwner">
    <h2>
        <c:out value="${owner.firstName}" /><span> </span> <c:out value="${owner.lastName}"/>'s requests
    </h2>
    <table id="requestsTable" class="table table-striped">
        <thead>
        <tr>

           <th style="width: 100px;">Service Date</th>
            <th style="width: 120px;">Pet</th>
            <th style="width: 180px">Status</th>
            <th style="width: 150px;">Payment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request">
            <tr>
              <td><javatime:format value="${request.serviceDate}" pattern="yyyy/MM/dd HH:mm"/></td>
                <td>
                    <c:out value="${request.pet.name}"/>
                </td>   
                <td> 
                     <c:out value="Accepted" />
                </td> 

                
                 <td>
                </td>
                
                <td>
                    <c:if test="${request.paid == true}">
                        <c:out value="Paid"/>
                    </c:if>
                    <c:if test="${request.paid == false}">
                        <spring:url value="/pay/{requestId}" var="payUrl">
                         <spring:param name="requestId" value="${request.id}"/>
                           </spring:url>
                          <a href="${fn:escapeXml(payUrl)}" class="btn btn-default">Pay with PayPal</a>
                        

                    </c:if>
                 </td>    
                         
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>