<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin" />

<h2>
	${title}
	<button onclick="showNew()" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#newModal"><i class="fa fa-plus"></i></button>
</h2>
<div>
	<table id="example" class="table table-striped table-bordered" style="width:100%">
		<thead>
            <tr>
                <th>Username</th>
                <th>Enabled</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${users}" var="u">
				<tr>
					<td>${u.username}</td>
					<td>${u.enabled}</td>
					<td style="text-align:center;"><button onclick="getDetails('${u.username}')" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#exampleModal"><i class="fa fa-eye"></i></button></td>
			        <td style="text-align:center;"><button onclick="showEdit('${u.username}')" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#editModal"><i class="fa fa-edit"></i></button></td>
                	<td style="text-align:center;"><button onclick="deleteData('${u.username}')" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
            <tr>
                <th>Username</th>
                <th>Enabled</th>
                <th></th>
               	<th></th>
                <th></th>
            </tr>
       	</tfoot>
	</table>
</div>
	
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
    	<div class="modal-content modal-content-user"></div>
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
	$(document).ready( function () {
	    $('#example').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [2, 3, 4] }
	    	  ]
	    });
	} );

    function getDetails(id) {
    	$(".modal-content-user").empty();
       	$.get("<c:url value="/user/getDetails/"/>"+id, function(data, status){
   	    	$(".modal-content-user").append(data);
   	    });
    }

    function showNew() {
    	$(".modal-content-new").empty();
       	$.get("<c:url value="/user/showNew/"/>", function(data, status){
   	    	$(".modal-content-new").append(data);
   	    });
    }

    function showEdit(id) {
    	$(".modal-content-edit").empty();
       	$.get("<c:url value="/user/showEdit/"/>"+id, function(data, status){
   	    	$(".modal-content-edit").append(data);
   	    });
    }

    function saveData(id) {
        var username = $('#user-username').val();
        var password = $('#user-password').val();
        var enabled = $('#user-enabled').val();
        var role = $('#user-role').val();

        if (id != null) {
            username = id;
        }
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/user/saveData"/>",
            data: { username: username, password: password, enabled: enabled, role: role },
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
            url: "<c:url value="/user/deleteData"/>",
            data: { username: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>