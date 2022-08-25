<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
  <h5 class="modal-title" id="exampleModalLabel">${job.description}</h5>
  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
<div class="modal-body">
	<h6>[${job.start} | <c:if test="${not empty job.end}">${job.end}]</c:if>
		                     <c:if test="${empty job.end}">In progress]</c:if>
    </h6>
    <h5>Device:</h5>
    <c:choose>
	    <c:when test="${job.device != null}">
	        	<p>${job.device.brand} ${job.device.model} - ${job.device.serialNumber}</p>
	    </c:when>
	    <c:otherwise>
	        <p>No device assigned</p>
	    </c:otherwise>
	</c:choose>
    <h5>Employee:</h5>
	<c:choose>
    	<c:when test="${job.employee != null }">
    		<p>${job.employee.name} ${job.employee.surname}</p>
    	</c:when>
    	<c:otherwise>
        	<p>No employee assigned</p>
	    </c:otherwise>
  	</c:choose>
	<hr/>
	<h5>Team:</h5>
	<c:choose>
	    <c:when test="${job.team != null}">
	        	<p>${job.team.name}</p>
	    </c:when>
	    <c:otherwise>
	        <p>No team assigned</p>
	    </c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
