<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="linkModalLabel">Team - Employee - ${job.description}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
	  	<li class="nav-item">
	    	<a class="nav-link active" id="pills-team-tab" data-toggle="pill" href="#pills-team" role="tab" aria-controls="pills-team" aria-selected="true" onclick="cambioLink(1)">Team</a>
	  	</li>
	  	<li class="nav-item">
	    	<a class="nav-link" id="pills-employee-tab" data-toggle="pill" href="#pills-employee" role="tab" aria-controls="pills-employee" aria-selected="false" onclick="cambioLink(2)">Employee</a>
	  	</li>
	</ul>
	<div class="tab-content" id="nav-tabContent">
	  	<div class="tab-pane fade show active" id="pills-team" role="tabpanel" aria-labelledby="pills-home-tab">
	  		<br/>
	  		<div class="form-group">
			  	<select class="form-control" id="job-team">
			      	<option value="">No team assigned</option>
			      	<c:if test="${fn:length(teams) > 0}">
			      		<c:forEach items="${teams}" var="t">
					    	<option value="${t.id}">${t.name}</option>
					  	</c:forEach>
			      	</c:if>
			    </select>
		  	</div>
		</div>
	  	<div class="tab-pane fade" id="pills-employee" role="tabpanel" aria-labelledby="pills-profile-tab">
	  		<br/>
			<div class="form-group">
			  	<select class="form-control" id="job-employee">
			      	<option value="">No employee assigned</option>
			      	<c:if test="${fn:length(employees) > 0}">
			      		<c:forEach items="${employees}" var="e">
					    	<option value="${e.id}">${e.name} ${e.surname}</option>
					  	</c:forEach>
			      	</c:if>
			    </select>
		  	</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="linkData(${job.id})">Save</button>
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
