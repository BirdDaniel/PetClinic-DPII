<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="pets">
    <h2>Pets</h2>
             <spring:url value="myPetList/residence" var="serviceUrl">
             </spring:url>
             <a href="${fn:escapeXml(serviceUrl)}" class="btn btn-default">Pets in Residence</a>
            <br>
            <br>
            <br>
             <form:form modelAttribute="pet" method="get" class="form-horizontal"
               id="search-pet-form">
        <div class="form-group">
            <div class="control-group" id="name">
                <label class="col-sm-2 control-label">Name </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="name" size="30" maxlength="80"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Find Pet</button>
            </div>
        </div>

    </form:form>
    <br>
    <table id="petsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th>Birth_date</th>
            <th>Type</th>
            <th>Owner</th>
            <th>Telephone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
               <!--       <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"></a> -->
                    <c:out value="${pet.name}"/>
                </td>
                <td>
                    <c:out value="${pet.birthDate}"/>
                </td>
                <td>
                    <c:out value="${pet.type}"/>
                </td>
                <td>
                    <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
                </td>
                <td>
                 <c:out value="${pet.owner.telephone}"/>
                 </td>
                <td>
   <!--                 <c:forEach var="pet" items="${owner.pets}">
                        <c:out value="${pet.name} "/>
                    </c:forEach>
                </td>
                
      

                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>

