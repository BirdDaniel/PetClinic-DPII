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
	<table class="table table-striped">
        <c:forEach var="residence" items="${residences}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Residence Date</dt>
						<dd><fmt:formatDate value="${residence.open}"/></dd>
                    </dl>
                </td>

        </c:forEach>
    </table>	
	
	
</petclinic:layout>