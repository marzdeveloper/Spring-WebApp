<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="true"%>

<sec:authorize access="hasRole('ADMIN')" var="isAdmin" />

<!-- Sidebar  -->
<nav id="sidebar">
    <div class="sidebar-header">
        <h3>Gestione Dispositivi</h3>
    </div>
    <ul class="list-unstyled components">
    	<c:if test="${isAdmin}">
	    	<li>
	            <a href="<c:url value="/user" />">Users</a>
	        </li>
	    </c:if>
	    <li>
            <a href="<c:url value="/dispositivi" />">Devices</a>
        </li>
	    <li>
	        <a href="<c:url value="/sender" />">Senders</a>
	    </li>
	    <li>
            <a href="<c:url value="/employee" />">Employees</a>
        </li>
        <li>
            <a href="<c:url value="/team" />">Teams</a>
        </li>
        <li>
			<a href="<c:url value="/job" />">Jobs</a>
		</li>
		<li>
            <a href="<c:url value="/location" />">Locations</a>
        </li>
	    <li>
	        <a href="<c:url value="/positioning" />">Positionings</a>
	    </li>
		<li>
            <a href="<c:url value="/workstation" />">Workstations</a>
        </li>
	</ul>
</nav>