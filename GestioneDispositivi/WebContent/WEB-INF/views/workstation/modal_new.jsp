<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">New workstation</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	  	<div class="form-group">
	    	<label for="work-emp">Employee</label>
		  	<select class="form-control" id="work-emp">
		      	<c:if test="${fn:length(employees) > 0}">
		      		<c:forEach items="${employees}" var="e">
				  		<option value="${e.id}">${e.name} ${e.surname}</option>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>
	  	<div class="form-group">
	    	<label for="work-location">Location</label>
		  	<select class="form-control" id="work-location">
		      	<c:if test="${fn:length(locations) > 0}">
		      		<c:forEach items="${locations}" var="l">
				  		<option value="${l.id}">${l.name}</option>
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
