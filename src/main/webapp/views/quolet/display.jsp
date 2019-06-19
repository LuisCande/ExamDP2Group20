<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="quolet.ticker" var="ticker" />
<spring:message code="quolet.publicationMoment" var="publicationMoment" />
<spring:message code="quolet.body" var="body" />
<spring:message code="quolet.picture" var="picture" />
<spring:message code="quolet.finalMode" var="finalMode" />
<spring:message code="quolet.finalMode.false" var="msgFalse" />
<spring:message code="quolet.finalMode.true" var="msgTrue" />
<spring:message code="quolet.return" var="returnMsg" />
<spring:message code="formatDate" var="formatDate" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- For the selected floatAcme, display the following information: --%>


	<jstl:choose>
		<jstl:when
			test="${((now.time - quolet.publicationMoment.time)< 2592000000)}">
			<jstl:set var="colorValue" value="indigo" />
		</jstl:when>
		<jstl:when
			test="${(now.time - quolet.publicationMoment.time)> (2*2592000000)}">
			<jstl:set var="colorValue" value="darkSlateGrey" />
		</jstl:when>

		<jstl:otherwise>
			<jstl:set var="colorValue" value="PapayaWhip" />
		</jstl:otherwise>
	</jstl:choose>
	
	<p style="color:${colorValue}; font-weight: bold">
	<jstl:out value="${publicationMoment}" />:
	<fmt:formatDate value="${quolet.publicationMoment}" pattern="${formatDate}"/>
	</p>
	
	<jstl:out value="${ticker}" />
	<jstl:out value="${quolet.ticker}"/>
	<br />
	
	
	<jstl:out value="${body}" />:
	<jstl:out value="${quolet.body}"/>
	<br />
	
	<jstl:if test="${not empty quolet.picture}">
	<jstl:out value="${picture}" />:
	<jstl:out value="${quolet.picture}"/>
	<br />
	</jstl:if>
	
	<jstl:out value="${finalMode}" />:
	<jstl:if test="${quolet.finalMode eq false}">
		<jstl:out value="${msgFalse}" />
	</jstl:if>
	<jstl:if test="${quolet.finalMode eq true}">
		<jstl:out value="${msgTrue}" />
	</jstl:if>
	<br />
	<br>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>
