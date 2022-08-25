<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">${device.brand} ${device.model} - ${device.serialNumber}</h5>
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<h6>[${device.checkIn} | <c:if test="${not empty device.checkOut}">${device.checkOut}]</c:if>
		                     <c:if test="${empty device.checkOut}">Available]</c:if>
    </h6>
	<h5>Sender:</h5>
	<p><c:if test="${not empty device.sender}">${sender.name}</c:if>
					<c:if test="${empty device.sender}">No sender assigned</c:if></p>
	<hr>
	<h5>Reason:</h5>
	<p>${device.reason}</p>
	<hr>
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
				<p>Team: <c:if test="${not empty j.team}">${j.team.name}</c:if>
				<c:if test="${empty j.team}">No team assigned</c:if></p>
				<p>Employee: <c:if test="${not empty j.employee}">${j.employee.name} ${j.employee.surname}</c:if>
				<c:if test="${empty j.employee}">No employee assigned</c:if></p></ul>
			</c:forEach>
		</c:when>    
		<c:otherwise>
			<p>No job assigned</p>
		</c:otherwise>
	</c:choose>
	<hr>
	<h5>Positionings:</h5>
	<c:choose>
		<c:when test="${fn:length(pos) > 0}">
			<c:forEach items="${pos}" var="p">
				<ul><li>Date: ${p.datePositioning}</li></ul>
				<ul><p>
					Location: 
					<c:if test="${not empty p.location.name}">${p.location.name}</c:if>
					<c:if test="${empty p.location.name}">Warehouse</c:if>
				</p></ul>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p>No positioning assigned</p>
		</c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
