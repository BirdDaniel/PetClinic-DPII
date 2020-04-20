<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<petclinic:layout pageName="itemsEmployee">


  <spring:url value="itemsList/new" var="addUrl">
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Item</a>
    
    
    <table id="itemsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 50px;">Price</th>
            <th style="width: 50px;">Sale</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 50px;">Stock</th>
            <th style="width: 75px;">Edit Item</th>
            <th style="width: 75px;">Remove Item</th>
        </tr>
        </thead>
        <tbody>
     <c:forEach items="${items}" var="item"> 
            <tr> 
           		 <td>
                   <c:out value="${item.name}"></c:out>
                </td>
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
                 <td>
                <spring:url value="itemsList/{itemId}/edit" var="editUrl">
                 <spring:param name="itemId" value="${item.id}"/>
   				 </spring:url>
   				 <spring:url value="itemsList/{itemId}/delete" var="deleteUrl">
                 <spring:param name="itemId" value="${item.id}"/>
   				 </spring:url>
   				 <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Item</a>
   				 <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Remove Item</a>
                 </td>
            </tr>
         </c:forEach> 
        </tbody>
    </table>
</petclinic:layout>