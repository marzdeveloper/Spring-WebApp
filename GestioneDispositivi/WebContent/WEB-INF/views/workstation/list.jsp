<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin" />

<h2>
	${title}
	<c:if test="${isAdmin}"><button onclick="showNew()" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#newModal"><i class="fa fa-plus"></i></button></c:if>
</h2>
<div>
	<table id="example" class="table table-striped table-bordered" style="width:100%">
		<thead>
            <tr>
                <th>Start</th>
                <th>End</th>
                <th>Employee</th>
                <th>Location</th>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${workstations}" var="w">
				<tr>
					<td>${w.start}</td>
					<td>${w.end}</td>
					<td>${w.employee.name} ${w.employee.surname}</td>
					<td>${w.location.name}</td>
			        <c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="deleteData(${w.id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td></c:if>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
            <tr>
                <th>Start</th>
                <th>End</th>
                <th>Employee</th>
                <th>Location</th>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
       	</tfoot>
	</table>
</div>
     	
<!-- Modal New -->
<div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="newModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-new"></div>
  	</div>
</div>

<script type="text/javascript">
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [<c:if test="${isAdmin}">4</c:if>] }
	    	  ]
	    });
	} );

    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/workstation/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function saveData(id) {
        var emp = $('#work-emp').val();
        var loc = $('#work-location').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/workstation/saveData"/>",
            data: { employee: emp, location: loc },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }

    function deleteData(id) {
        $.ajax({
            type: "POST",
            url: "<c:url value="/workstation/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>