<%--
 * list.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>
<spring:message code="quolet.publicationMoment" var="publicationMoment" />
<spring:message code="quolet.body" var="body" />
<spring:message code="quolet.picture" var="picture" />
<spring:message code="quolet.finalMode" var="finalMode" />
<spring:message code="quolet.finalMode.true" var="finalModeTrue" />
<spring:message code="quolet.finalMode.false" var="finalModeFalse" />
<spring:message code="quolet.ticker" var="ticker" />
<spring:message code="formatDate" var="formatDate" />

<spring:message code="quolet.create" var="create" />
<spring:message code="quolet.edit" var="edit" />
<spring:message code="quolet.save" var="save" />
<spring:message code="quolet.cancel" var="cancel" />
<spring:message code="quolet.display" var="display" />
<spring:message code="quolet.delete" var="msgDelete" />
<spring:message code="quolet.confirm.delete" var="msgConfirm" />


<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="quolets" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	<display:column title="${publicationMoment}" sortable="true">
		<fmt:formatDate value="${row.publicationMoment}" pattern="${formatDate}"/>
	</display:column>
	
	<display:column title="${finalMode}" sortable="true">
		<jstl:if test="${row.finalMode eq true}">
		<jstl:out value="${finalModeTrue}"/>
		</jstl:if>
		<jstl:if test="${row.finalMode eq false}">
		<jstl:out value="${finalModeFalse}"/>
		</jstl:if>
	</display:column>
	
	<%-- Display --%>
		<spring:url var="displayUrl" value="quolet/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<%-- Edit --%>	
		<spring:url var="editUrl" value="quolet/company/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		
		<display:column title="${edit}">
		<jstl:if test="${row.finalMode eq false}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</jstl:if>
		</display:column>		

	<!-- Delete -->
	
	<spring:url var="deleteURL" value="quolet/company/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	
	<display:column title="${msgDelete}">
	<jstl:if test="${row.finalMode eq false}">
		<a href="${deleteURL}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDelete}" /></a>
	</jstl:if>
	</display:column>
	
</display:table>