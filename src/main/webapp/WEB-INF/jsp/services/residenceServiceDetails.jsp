<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<petclinic:layout pageName="residences">

	<h2><b><c:out value="${residence.name}"/></b></h2>
	<table class="table table-striped">
	<tr>
             <tr>
            <th><strong>Address:</strong></th>
            <td><c:out value="${residence.address}"/></td>
        </tr>
         <tr>
            <th><strong>Price:</strong></th>
            <td><c:out value="${residence.price}"/></td>
        </tr>
        <tr>
            <th><strong>Open:</strong></th>
            <td><c:out value="${residence.open}"/></td>
        </tr>
        <tr>
            <th><strong>Close:</strong></th>
            <td><c:out value="${residence.close}"/></td>
        </tr>
         <tr>
            <th><strong>Rating:</strong></th>
            <td><c:out value="${residence.rating}"/></td>
        </tr>
         <tr>
            <th><strong>Max allowed pets:</strong></th>
            <td><c:out value="${residence.max}"/></td>
        </tr>
        <tr>
            <th><strong>Description:</strong></th>
           <td><c:out value="${residence.description}"/></td>
        </tr>
      </table>
    <!--    <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${requestD==true}">
                            <button class="btn btn-default" type="submit">Return to Request</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Return to Residences</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            --> 
</petclinic:layout>
