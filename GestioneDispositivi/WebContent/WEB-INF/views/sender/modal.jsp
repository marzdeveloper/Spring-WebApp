<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">${sender.name}</h5>
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<h5>Devices:</h5>
	<c:choose>
		<c:when test="${fn:length(devices) > 0}">
			<ul>
				<c:forEach items="${devices}" var="d">
					<li>${d.brand} ${d.model} - ${d.serialNumber}</li>
				</c:forEach>
			</ul>
		</c:when>    
		<c:otherwise>
			<p>No device assigned</p>
		</c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
