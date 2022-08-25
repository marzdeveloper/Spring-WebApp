<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="editModalLabel">${sender.name}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	   	<div class="form-group">
	    	<label for="sender-name">Name</label>
	    	<input type="text" class="form-control" value="${sender.name}" id="sender-name" placeholder="">
	  	</div> 	
	</div>
</div>
<div class="modal-footer">
 	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveData(${sender.id})">Save</button>
 	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>