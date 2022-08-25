<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="newModalLabel">New device</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	   	<div class="form-group">
	    	<label for="dev-brand">Brand</label>
	    	<input type="text" class="form-control" id="dev-brand" placeholder="">
	  	</div>
   		<div class="form-group">
	    	<label for="dev-ent">Document</label>
	    	<input type="text" class="form-control" id="dev-doc" placeholder="">
	  	</div>
     	<div class="form-group">
	    	<label for="dev-model">Model</label>
	    	<input type="text" class="form-control" id="dev-model" placeholder="">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-reason">Reason</label>
	    	<textarea class="form-control" id="dev-reason" rows="3"></textarea>
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-checkIn">CheckIn</label>
	  		<input class="form-control" data-date-format="yyyy/mm/dd" id="dev-checkIn">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-serial">Serial Number</label>
	    	<input type="text" class="form-control" id="dev-serial" placeholder="">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-sender">Sender</label>
		  	<select class="form-control" id="dev-sender">
		      	<option value="">No sender assigned</option>
		      	<c:if test="${fn:length(sender) > 0}">
		      		<c:forEach items="${sender}" var="s">
				  		<option value="${s.id}">${s.name}</option>
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

<script type="text/javascript">
	$( function() {
		$("#dev-checkIn").datepicker({
	    	closeText: 'Close',
	    	prevText: 'Prev',
	    	nextText: 'Next',
	    	currentText: 'Today',
	    	monthNames: ['January','February','March','April','May','June','July','August','September','October','November','December'],
	    	monthNamesShort: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	    	dayNames: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
	    	dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
	    	dayNamesMin: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
	    	dateFormat: 'yy-mm-dd',
	    	firstDay: 1
    	});
	
		$("#dev-checkOut").datepicker({
	    	closeText: 'Close',
	    	prevText: 'Prev',
	    	nextText: 'Next',
	    	currentText: 'Today',
	    	monthNames: ['January','February','March','April','May','June','July','August','September','October','November','December'],
	    	monthNamesShort: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	    	dayNames: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
	    	dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
	    	dayNamesMin: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
	    	dateFormat: 'yy-mm-dd',
	    	firstDay: 1
    	});
		$.datepicker.setDefaults($.datepicker.regional['it']);
	} ); 
</script>
