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
                <th>Date</th>
                <th>Device</th>
                <th>Employee</th>
                <th>Location</th>
                <c:if test="${isAdmin}"><th></th></c:if>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${positions}" var="p">
				<tr>
					<td>${p.datePositioning}</td>
					<td>${p.device.brand} ${p.device.model}</td>
					<td>${p.employee.name} ${p.employee.surname}</td>
					<td>${p.location.name}</td>
					<c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="showLink(${p.id})" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#linkModal"><i class="fa fa-link"></i></button></td></c:if>
			        <c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="deleteData(${p.id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td></c:if>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
           	<tr>
                <th>Date</th>
                <th>Device</th>
                <th>Employee</th>
                <th>Location</th>
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

<!-- Modal Link -->
<div class="modal fade" id="linkModal" tabindex="-1" role="dialog" aria-labelledby="linkModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-link"></div>
  	</div>
</div>

<script type="text/javascript">
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false<c:if test="${isAdmin}">, "targets": [4, 5]</c:if> }
	    	  ]
	    });
	} );

    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/positioning/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function showLink(id) {
    	$(".modal-content-link").empty();
       	$.get("<c:url value="/positioning/showLink/"/>"+id, function(data, status){
   	    	$(".modal-content-link").append(data);
   	    });
    }

    function saveData(id) {
        var dev = $('#pos-dev').val();
        var emp = $('#pos-emp').val();
        var loc = $('#pos-location').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/positioning/saveData"/>",
            data: { device: dev, employee: emp, location: loc },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }

    function linkData(id) {
    	var dev = $('#pos-dev').val();
        var emp = $('#pos-emp').val();
        var loc = $('#pos-location').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/positioning/saveLink"/>",
            data: { device: dev, employee: emp, location: loc, id: id },
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
            url: "<c:url value="/positioning/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>