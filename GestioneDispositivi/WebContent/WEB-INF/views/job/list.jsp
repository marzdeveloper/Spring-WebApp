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
                <th>Description</th>
                <th>Start</th>
                <th>End</th>
                <th></th>
                <c:if test="${isAdmin}"><th></th></c:if>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${jobs}" var="j">
				<tr>
					<td>${j.description}</td>
					<td>${j.start}</td>
					<td>${j.end}</td>
					<td style="text-align:center;"><button onclick="getDetails(${j.id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#exampleModal"><i class="fa fa-eye"></i></button></td>
			        <c:if test="${isAdmin}">
				        <td style="text-align:center;"><button onclick="showLink(${j.id})" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#linkModal"><i class="fa fa-link"></i></button></td>
					</c:if>
                	<c:if test="${isAdmin}">
				        <td style="text-align:center;"><button onclick="deleteData(${j.id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
            <tr>
                <th>Description</th>
                <th>Start</th>
                <th>End</th>
                <th></th>
               	<c:if test="${isAdmin}"><th></th></c:if>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
       	</tfoot>
	</table>
</div>
	
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-job"></div>
	</div>
</div>
     	
<!-- Modal New -->
<div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="newModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-new"></div>
  	</div>
</div>

<!-- Modal Link -->
<div class="modal fade" id="linkModal" tabindex="-1" role="dialog" aria-labelledby="linkModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-link"></div>
  	</div>
</div>

<script type="text/javascript">
	var quale_link = 1;
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [3, <c:if test="${isAdmin}">4, 5</c:if>] }
	    	  ]
	    });
	} );

    function getDetails(id) {
    	$(".modal-content-job").empty();
       	$.get("<c:url value="/job/getDetails/"/>"+id, function(data, status){
   	    	$(".modal-content-job").append(data);
   	    });
    }

    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/job/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function showEdit(id) {
    	$(".modal-content-edit").empty();
       	$.get("<c:url value="/job/showEdit/"/>"+id, function(data, status){
   	    	$(".modal-content-edit").append(data);
   	    });
    }

    function showLink(id) {
    	$(".modal-content-link").empty();
       	$.get("<c:url value="/job/showLink/"/>"+id, function(data, status){
   	    	$(".modal-content-link").append(data);
   	    });
    }

    function saveData(id) {
        var des = $('#job-des').val();
        var dev = $('#job-dev').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/job/saveData"/>",
            data: { description: des, device: dev, id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }

    function linkData(id) {
    	var team = $('#job-team').val();
    	var emp = $('#job-employee').val();
    	if (team == null && emp == null) {
        	return false;
        }
    	$.ajax({
            type: "POST",
            url: "<c:url value="/job/saveLink"/>",
            data: { team: team, employee: emp, link: quale_link, id: id },
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
            url: "<c:url value="/job/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }

    function cambioLink(id) {
        quale_link = id;
    }
</script>