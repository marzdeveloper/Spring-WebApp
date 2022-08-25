<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin" />

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<h2>
	${title}
	<c:if test="${isAdmin}"><button onclick="showNew()" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#newModal"><i class="fa fa-plus"></i></button></c:if>
</h2>
<div>
   	<table id="example" class="table table-striped table-bordered" style="width:100%">
   		<thead>
            <tr>
                <th>Serial</th>
                <th>Brand</th>
                <th>Model</th>
                <th>Document</th>
                <th>CheckIn</th>
                <th>CheckOut</th>
                <th></th>
                <c:if test="${isAdmin}"><th></th></c:if>
                <c:if test="${isAdmin}"><th></th></c:if>
            </tr>
        </thead>
        <tbody>
	        <c:forEach items="${devices}" var="d">
	        	<tr>
	                <td>${d.serialNumber}</td>
	                <td>${d.brand}</td>
	                <td>${d.model}</td>
	                <td>${d.document}</td>
	                <td>${d.checkIn}</td>
	                <td>${d.checkOut}</td>
	                <td style="text-align:center;"><button onclick="getDetails(${d.id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#exampleModal"><i class="fa fa-eye"></i></button></td>
	                <c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="showEdit(${d.id})" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#editModal"><i class="fa fa-edit"></i></button></td></c:if>
	                <c:if test="${isAdmin}"><td style="text-align:center;"><button onclick="deleteData(${d.id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td></c:if>
	            </tr>
			</c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th>Serial</th>
                <th>Brand</th>
                <th>Model</th>
                <th>Document</th>
                <th>CheckIn</th>
                <th>CheckOut</th>
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
    	<div class="modal-content modal-content-device"></div>
	</div>
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
	var quale_link = 1;
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [6, <c:if test="${isAdmin}">7, 8</c:if>] }
	    	  ]
	    });
	} );

    function getDetails(id) {
    	$(".modal-content-device").empty();
       	$.get("<c:url value="/dispositivi/getDetails/"/>"+id, function(data, status){
   	    	$(".modal-content-device").append(data);
   	    });
    }

    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/dispositivi/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function showEdit(id) {
    	$(".modal-content-edit").empty();
       	$.get("<c:url value="/dispositivi/showEdit/"/>"+id, function(data, status){
   	    	$(".modal-content-edit").append(data);
   	    });
    }

    function saveData(id) {
        var ser = $('#dev-serial').val();
        var brand = $('#dev-brand').val();
        var model = $('#dev-model').val();
        var reason = $('#dev-reason').val();
        var doc = $('#dev-doc').val();
        var checkIn = $('#dev-checkIn').val();
        var checkOut = $('#dev-checkOut').val();
        var sender = $('#dev-sender').val();
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/dispositivi/saveData"/>",
            data: { serialNumber: ser, brand: brand, model: model, reason: reason, document: doc,
            	checkIn: checkIn, checkOut: checkOut, sender: sender, id: id },
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
            url: "<c:url value="/dispositivi/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>
