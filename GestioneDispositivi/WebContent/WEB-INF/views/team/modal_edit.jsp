<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">${team.name}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
   		<div class="form-group">
	    	<label for="team-name">Name</label>
	    	<input type="text" class="form-control" value="${team.name}" id="team-name" placeholder="">
	  	</div>
   		<div class="form-group">
	    	<label for="team-type">Type</label>
	    	<input type="text" class="form-control" value="${team.type}" id="team-type" placeholder="">
	  	</div>
	  	<div class="form-group">
	    	<label for="team-des">Description</label>
	    	<input type="text" class="form-control" value="${team.description}" id="team-des" placeholder="">
	  	</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveData(${team.id})">Save</button>
  	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
