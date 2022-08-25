<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
  <h5 class="modal-title" id="exampleModalLabel">${team.name}</h5>
  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
<div class="modal-body">
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
  	<hr>
  	<h5>Employees:</h5>
	<c:choose>
  		<c:when test="${fn:length(employees) > 0}">
    		<c:forEach items="${employees}" var="e">
	    		<ul>
	       			<li>${e.name} ${e.surname}</li>
				</ul>
			</c:forEach>
    	</c:when>    
    	<c:otherwise>
        	<p>No employee assigned</p>
    	</c:otherwise>
    </c:choose>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
