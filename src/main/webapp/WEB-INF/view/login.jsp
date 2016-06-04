<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome to SalonBooks</title>
<jsp:include page="../includes/headTag.jsp"/>
	</head> 
	<body>
  <div class="container" id="pageBox" style="width:500px" >
	<div>Welcome to SalonBooks!!</div>
	<br/>
	<% pageContext.setAttribute("now", new org.joda.time.DateTime()); %>
	<div>Today is <joda:format value="${now}" pattern="MM dd, YYYY"/></div>

		<form:form id="login" 
		modelAttribute="loginFlowActions" 
		action="${flowExecutionUrl}" >
	
      <input type="hidden" name="_flowExecutionKey" 
             value="${flowExecutionKey}" />
             
		User: <br/><form:input type="text" path="username" />
		<form:errors cssClass="error" path="username" />
		<br/>
		Passcode: <br/><form:input type="password" path="password"/>
		<form:errors cssClass="error" path="password" /><br/>
		 <input name="_eventId_doLogin" type="submit" value="Login"/> | 
		 <input type="button" name="_eventId_cancel" value="Cancel" />
		</form:form>
		</div>
	</body>
</html>
