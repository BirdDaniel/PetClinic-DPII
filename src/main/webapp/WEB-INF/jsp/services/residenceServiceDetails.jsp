<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<petclinic:layout pageName="residences">

    <h2>Residence Details</h2>


    <table class="table table-striped">
        <tr>
            <th>Description</th>
           <td><c:out value="${residence.description}"/></td>
        </tr>
    <tr>
    <th>Days:</th>
            <td><c:out value="${residence.days}"/></td>
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
