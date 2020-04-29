<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="newParkForm">
    <jsp:body>
        <h2>
            <c:if test="${park['new']}">New </c:if> Park
        </h2>
        <form:form modelAttribute="park"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${park.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Owner</label>
                    <div class="col-sm-10">
                        <c:out value="${park.owner.firstName} ${park.owner.lastName}"/>
                    </div>
                </div>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Address" name="address"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${park['new']}">
                            <button class="btn btn-default" type="submit">Add Park</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Park</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
