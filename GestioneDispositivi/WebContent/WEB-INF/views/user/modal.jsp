<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">${user.username}</h5>
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<h5>Role:</h5>
	<c:choose>
		<c:when test="${fn:length(roles) > 1}">
			<p>ADMIN</p>
		</c:when>    
		<c:otherwise>
			<c:forEach items="${roles}" var="r">
					<p>${r.name}</p>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
