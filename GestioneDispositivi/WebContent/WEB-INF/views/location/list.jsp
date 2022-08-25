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
                <th>Name</th>
                <th>Description</th>
                <c:if test="${isAdmin}"><th></th></c:if>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${locations}" var="l">
				<tr>
					<td>${l.name}</td>
					<td>${l.description}</td>
					<c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="showEdit(${l.id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#editModal"><i class="fa fa-edit"></i></button></td></c:if>
			        <c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="deleteData(${l.id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td></c:if>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
           	<tr>
                <th>Name</th>
                <th>Description</th>
                <c:if test="${isAdmin}"><th></th></c:if>
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

<!-- Modal Edit -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-edit"></div>
  	</div>
</div>

<script type="text/javascript">
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false<c:if test="${isAdmin}">, "targets": [2, 3]</c:if> }
	    	  ]
	    });
	} );
	
    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/location/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function showEdit(id) {
    	$(".modal-content-edit").empty();
       	$.get("<c:url value="/location/showEdit/"/>"+id, function(data, status){
   	    	$(".modal-content-edit").append(data);
   	    });
    }

    function saveData(id) {
        var name = $('#location-name').val();
        var des = $('#location-des').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/location/saveData"/>",
            data: { name: name, description: des, id: id },
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
            url: "<c:url value="/location/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>