<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="editModalLabel">${user.username}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	  	<div class="form-group">
	    	<label for="user-password">Password</label>
	    	<input type="password" class="form-control" value="" id="user-password" placeholder="" />
	  	</div>
	  	<div class="form-group">
	    	<label for="user-enabled">Enabled</label>
		  	<select class="form-control" id="user-enabled">
		  		<c:choose>
			    	<c:when test="${user.enabled}">
			    		<option value="true" selected>True</option>
			      		<option value="false">False</option>
			    	</c:when>    
			    	<c:otherwise>
			    		<option value="true">True</option>
			      		<option value="false" selected>False</option>
			    	</c:otherwise>
			  	</c:choose>
		    </select>
	  	</div>
	  	<div class="form-group">
	    	<label for="user-role">Role</label>
		  	<select class="form-control" id="user-role">
		      	<c:if test="${fn:length(roles) > 0}">
		      		<c:forEach items="${roles}" var="r">
		      			<c:choose>
					    	<c:when test="${r[2] == '1'}">
					    		<option value="${r[0]}" selected>${r[1]}</option>
					    	</c:when>    
					    	<c:otherwise>
					    		<option value="${r[0]}">${r[1]}</option>
					    	</c:otherwise>
					  	</c:choose>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>
	</div>
</div>
<div class="modal-footer">
 	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveData('${user.username}')">Save</button>
 	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>