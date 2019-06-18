<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="quolet.create" var="create" />
<spring:message code="quolet.edit" var="edit" />
<spring:message code="quolet.delete" var="delete" />
<spring:message code="quolet.confirm.delete" var="confirm" />
<spring:message code="quolet.publicationMoment" var="publicationMoment" />
<spring:message code="quolet.body" var="body" />
<spring:message code="quolet.picture" var="picture" />
<spring:message code="quolet.finalMode" var="finalMode" />
<spring:message code="quolet.save" var="save" />
<spring:message code="quolet.cancel" var="cancel" />


<security:authorize access="hasRole('COMPANY')">

<form:form action="${requestURI}" modelAttribute="quolet">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="audit"/>
	
	<form:label path="body">
			<jstl:out value="${body}"/>
		</form:label>	
		<form:textarea path="body" maxlength="100"/>
		<form:errors path="body" cssClass="error" />
	<br>
		
	<acme:textbox code="quolet.picture" path="picture" />
		
	<form:label path="finalMode">
			<jstl:out value="${finalMode}" />:
		</form:label>
			<form:select path="finalMode" >
				<form:option
					label="NO"
					value="false" />
				<form:option
					label="YES"
					value="true" />
			</form:select>
		<br>
	<%-- Buttons --%>
	<input type="submit" name="save" value="${save}"/>&nbsp; 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('quolet/company/list.do');" />

</form:form>

</security:authorize>