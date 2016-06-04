<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/includes/taglibInclude.jsp" %>

<div>
	<h1>An Error Has Occurred</h1>

		<h2>State Exception</h2>
		<c:out value="${stateException.message}" />
		
		<h2>Flow Exception</h2>
		<c:out value="${exception.message}" />
		
		<h2>Root Cause</h2>
		<c:out value="${currentErrorException.errMsg}" />
</div>