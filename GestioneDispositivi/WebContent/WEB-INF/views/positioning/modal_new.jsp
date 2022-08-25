<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">New positioning</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	  	<div class="form-group">
	    	<label for="pos-dev">Device</label>
		  	<select class="form-control" id="pos-dev">
		      	<c:if test="${fn:length(devices) > 0}">
		      		<c:forEach items="${devices}" var="d">
				  		<option value="${d.id}">${d.brand} ${d.model} - ${d.serialNumber}</option>
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
