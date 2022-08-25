<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()" var="isAuth" />
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <c:if test="${isAuth}">
        <button type="button" id="sidebarCollapse" class="btn btn-info">
            <i class="fas fa-align-left"></i>
            <span>Menu</span>
        </button>
            </c:if>
        <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-align-justify"></i>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav navbar-nav ml-auto">
                <c:if test="${!isAuth}">
	                <li class="nav-item">
	                    <a class="nav-link" href="<c:url value="/login" />">Login</a>  
	                </li>
                </c:if>
                <c:if test="${isAuth}">                  
	                <li class="nav-item">
    	            	<a class="nav-link" href="<c:url value="/logout" />">Logout</a> 
	             	</li>
               	</c:if>
            </ul>
        </div>
    </div>
</nav>