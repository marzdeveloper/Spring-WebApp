<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">Employees - ${team.name}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
		<div class="form-check">
			<c:if test="${fn:length(employees) > 0}">
	      		<c:forEach items="${employees}" var="e">
	      			<c:choose>
				    	<c:when test="${e[2] == '1'}">
				    		<input class="form-check-input" type="checkbox" value="${e[0]}" id="check-${e[0]}" name="check-link" checked>
				    	</c:when>    
				    	<c:otherwise>
				    		<input class="form-check-input" type="checkbox" value="${e[0]}" id="check-${e[0]}" name="check-link">
				    	</c:otherwise>
				  	</c:choose>
					<label class="form-check-label" for="check-${e[0]}">${e[1]}</label>
					<br />
			  	</c:forEach>
	      	</c:if>
	  	</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="linkData(${team.id})">Save</button>
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
