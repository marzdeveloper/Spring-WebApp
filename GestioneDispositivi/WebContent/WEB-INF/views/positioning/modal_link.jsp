<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="linkModalLabel">${positioning.datePositioning}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	  	<div class="form-group">
	    	<label for="pos-emp">Employee</label>
		  	<select class="form-control" id="pos-emp">
		      	<option value="">No employee assigned</option>
		      	<c:if test="${fn:length(employees) > 0}">
		      		<c:forEach items="${employees}" var="e">
		      			<c:choose>
					    	<c:when test="${e.id == pos_employee}">
					    		<option value="${e.id}" selected>${e.name} ${e.surname}</option>
					    	</c:when>    
					    	<c:otherwise>
					    		<option value="${e.id}">${e.name} ${e.surname}</option>
					    	</c:otherwise>
					  	</c:choose>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>
	  	<div class="form-group">
	    	<label for="pos-location">Location</label>
		  	<select class="form-control" id="pos-location">
		      	<option value="">No location assigned</option>
		      	<c:if test="${fn:length(locations) > 0}">
		      		<c:forEach items="${locations}" var="l">
		      			<c:choose>
					    	<c:when test="${l.id == pos_location}">
					    		<option value="${l.id}" selected>${l.name}</option>
					    	</c:when>    
					    	<c:otherwise>
					    		<option value="${l.id}">${l.name}</option>
					    	</c:otherwise>
					  	</c:choose>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="linkData(${positioning.id})">Save</button>
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
