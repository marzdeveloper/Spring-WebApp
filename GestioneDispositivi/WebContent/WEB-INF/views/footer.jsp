<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="text-align:center"></div>
<script src="<c:url value="/js/menu.js" />"></script>
<script>
	$(document).ready( function () {
	    var alert = ${alertMsg} + "";
	    if (alert) {
		    var $alertMsg1 = $('<div id="alert_error" class="alert alert-success alert-flotante temp_alert">' + alert + '</div>');
	    	$('.msg_alert').append($alertMsg1);
	    	setTimeout(function() {
	    		$alertMsg1.fadeOut( "slow", function() {
					$alertMsg1.remove();
	    		  });
	    	}, 3000);
		}
	} );

	function showAlertMsg(response) {
		if(response.success) {
			window.location.replace(window.location.origin + window.location.pathname + '?msg="' + response.msg + '"');
		} else {
			var $alertMsg = $('<div id="alert_error" class="alert alert-danger alert-flotante temp_alert">' + response.msg + '</div>');
			$('.msg_alert').append($alertMsg);
			setTimeout(function() {
				$alertMsg.fadeOut( "slow", function() {
					$alertMsg.remove();
				});
			}, 3000);
		}
	}

	$('#newModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-new").empty();
	})
	
	$('#editModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-edit").empty();
	})
	
	$('#linkModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-link").empty();
	})
</script>