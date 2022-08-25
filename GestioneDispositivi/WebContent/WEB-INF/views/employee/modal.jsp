<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
  <h5 class="modal-title" id="exampleModalLabel">${employee.name} ${employee.surname}</h5>
  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
<div class="modal-body">
	<h5>Workstations:</h5>
	<c:choose>
		<c:when test="${fn:length(work) > 0}">
			<c:forEach items="${work}" var="w">
				<ul>
					<li>Start: ${w.start} | End:
					<c:if test="${not empty w.end}">${w.end}</c:if>
					<c:if test="${empty w.end}">In progress</c:if></li>
					</ul>
				<ul>
				<p>Location: <c:if test="${not empty w.location}">${w.location.name}</c:if>
				<c:if test="${empty w.location}">No location assigned</c:if></p></ul>
			</c:forEach>
		</c:when>    
		<c:otherwise>
			<p>No workstation assigned</p>
		</c:otherwise>
	</c:choose>
	<hr/>
	<h5>Teams:</h5>
  	<c:choose>
    	<c:when test="${fn:length(teams) > 0}">
    		<ul>
	    		<c:forEach items="${teams}" var="t">
    				<li>${t.name}</li>    		
		  		</c:forEach>
    		</ul>
    	</c:when>    
    	<c:otherwise>
        	<p>No team assigned</p>
    	</c:otherwise>
 	</c:choose>
 	<hr/>
  	<h5>Jobs:</h5>
	<c:choose>
		<c:when test="${fn:length(jobs) > 0}">
			<c:forEach items="${jobs}" var="j">
				<ul>
					<li>Start: ${j.start} | End:
					<c:if test="${not empty j.end}">${j.end}</c:if>
					<c:if test="${empty j.end}">In progress</c:if></li>
					</ul>
				<ul><p>${j.description}</p>
				<p>Device: <c:if test="${not empty j.device}">${j.device.brand} ${j.device.model} - ${j.device.serialNumber}</c:if>
				<c:if test="${empty j.device}">No device assigned</c:if></p></ul>
			</c:forEach>
		</c:when>    
		<c:otherwise>
			<p>No job assigned</p>
		</c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
