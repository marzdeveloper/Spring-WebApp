<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()" var="isAuth" />

<c:if test="${not empty errorMessage}">
	<div class="alert alert-danger alert-flotante" role="alert">
	  "${errorMessage}"
	</div>
</c:if>
<div class="wrapper fadeInDown">
	<div id="formContent">
		<h2>Gestione Dispositivi</h2>
    	<!-- Login Form -->
    	<form name='login' action="<c:url value="/login" />" method='POST'>
    		<input type="text" id="login" class="fadeIn second" name="username" placeholder="Username">
      		<input type="password" id="password" class="fadeIn third" name="password" placeholder="Password">
      		<input type="submit" name="submit" class="fadeIn fourth" value="submit">
    	</form>
	</div>
</div>

<script>
if(${isAuth})
window.location.replace("/GestioneDispositivi/dispositivi");
</script>