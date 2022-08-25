<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="editModalLabel">${location.name}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	   	<form name='editLocation' action="<c:url value="/location/editLocation" />" method='POST'>
	   		<div class="form-group">
		    	<label for="location-name">Name</label>
		    	<input type="text" class="form-control" value="${location.name}" id="location-name" placeholder="">
		  	</div>
		  	<div class="form-group">
		    	<label for="location-des">Description</label>
		    	<input type="text" class="form-control" value="${location.description}" id="location-des" placeholder="">
		  	</div>
	   	</form>	    	
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveData(${location.id})">Save</button>
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
