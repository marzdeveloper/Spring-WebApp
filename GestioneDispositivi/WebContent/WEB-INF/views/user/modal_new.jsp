<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">New user</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	   	<div class="form-group">
	    	<label for="user-username">Username</label>
	    	<input type="text" class="form-control" id="user-username" placeholder="" />
	  	</div>
	  	<div class="form-group">
	    	<label for="user-password">Password</label>
	    	<input type="text" class="form-control" id="user-password" placeholder="" />
	  	</div>
	  	<div class="form-group">
	    	<label for="user-role">Role</label>
		  	<select class="form-control" id="user-role">
		      	<c:if test="${fn:length(roles) > 0}">
		      		<c:forEach items="${roles}" var="r">
				  		<option value="${r.id}">${r.name}</option>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveData(null)">New</button>
  	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
