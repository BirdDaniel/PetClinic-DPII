<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="createRequest">
    <jsp:attribute name="customScript">
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.full.min.js"></script>
        <script>
            $(function () {
                $("#serviceDate").datetimepicker({
                    onShow:function( ct ){
                        this.setOptions({
                            maxDateTime:$('#finishDate').val()?$('#finishDate').val():false
                        })
                        },
                    minDateTime: Date.now(),
                    allowTimes:[
                            '12:00', '13:00','14:00', '15:00', 
                            '16:00', '17:00', '18:00', '19:00', '20:00'
                        ]
              });

            });

            $(function () {
                $("#finishDate").datetimepicker({
                    onShow: function( ct ){
                        var serviceDate = $('#serviceDate').val();
                        console.log(serviceDate);
                        this.setOptions({
                            minDateTime:$('#serviceDate').val()?serviceDate:Date.now()
                        })
                        },
                    allowTimes:[
                            '09:00','10:00','11:00','12:00', '13:00','14:00', '15:00', 
                            '16:00', '17:00', '18:00', '19:00', '20:00'
                        ]
              });
            });
            
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        New Request for <c:out value="${residence.name}" />, <c:out value="${request.owner.firstName}"/>
    </h2>
    <form:form modelAttribute="request" class="form-horizontal" id="add-request-form">
        <div class="form-group has-feedback">

<h2>
        New Request for <c:out value="${request.employee.firstName} ${request.owner.firstName}" />, <c:out value="${request.owner.firstName}"/>
    </h2>

			<!-- <div style="display: none;">
                <petclinic:inputField label="" name="employee"/>
            </div> -->
            <petclinic:inputField label="" name="requestDate"/>
            <c:if test="${service eq 'residence'}">
            <petclinic:inputField label="Date to bring your pet" name="serviceDate"/>
            <petclinic:inputField label="Date to pick up your pet" name="finishDate"/>
            </c:if>
            <c:if test="${service eq 'clinic'}">
            <petclinic:inputField label="Choose a date" name="serviceDate"/>
            </c:if>
            <div class="control-group">
                <petclinic:selectField name="pet" label="Pet" names="${pets}" size="2"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Make Request</button>
        </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
