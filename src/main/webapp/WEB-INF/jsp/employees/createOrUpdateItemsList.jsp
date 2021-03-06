<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="items">
    <h2>
        <c:if test="${item['new']}">New </c:if> Item
    </h2>
    <form:form modelAttribute="item" class="form-horizontal" id="add-item-form">
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${pet.id}"/>
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Price" name="price"/>
            <petclinic:inputField label="Sale" name="sale"/>
            <petclinic:inputField label="Description" name="description"/>
            <petclinic:inputField label="Stock" name="stock"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${item['new']}">
                        <button class="btn btn-default" type="submit">Add Item</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Item</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>