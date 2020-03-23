<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="residences">
	
	<br/>
	<br/>
	<br/>
	
	<h2>Residences: </h2>
	<table id="residenceTable" class="table table-striped">
        <thead>
        <tr>
            <th>Address</th>
            <th>Price</th>
            <th>Rating</th>
            <th>Open</th>
            <th>Close</th>
            <th>Max</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${residences}" var="residence">
            <tr> <td>
                     
                     <spring:url value="/residence/{residenceId}" var="residenceUrl">
                         <spring:param name="residenceId" value="${residence.id}"/>
                     </spring:url>
                
               <!--       <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"></a> -->
                     <a href="${fn:escapeXml(residenceUrl)}"><c:out value=" ${residence.address}"/></a>
                   <!-- <c:out value="${residence.address}"/> --> 
                </td>
                <td>
                    <c:out value="${residence.price}"/>
                </td>
                <td>
                    <c:out value="${residence.rating}"/>
                </td>
                <td>
                    <c:out value="${residence.open}"/>
                </td>
                <td>
                 <c:out value="${residence.close}"/>
                 </td>
                 <td>
                 <c:out value="${residence.max}"/>
                 </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
</petclinic:layout>