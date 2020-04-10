<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="itemsEmployee">
    <table id="itemsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Price</th>
            <th style="width: 150px;">Sale</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 200px;">Stock</th>
        </tr>
        </thead>
        <tbody>
     <c:forEach items="${items}" var="item"> 
            <tr> 
                <td>
                   <c:out value="${item.price}"></c:out>
                </td>
                 <td>
                   <c:out value="${item.sale}"></c:out>
                </td>
                 <td>
                   <c:out value="${item.description}"></c:out>
                </td>
                 <td>
                   <c:out value="${item.stock}"></c:out>
                </td>
                
            </tr>
         </c:forEach> 
        </tbody>
    </table>
</petclinic:layout>