
<%@ page import="poetrystore.Sale" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'sale.label', default: 'Sale')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-sale" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-sale" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="year" title="${message(code: 'sale.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="month" title="${message(code: 'sale.month.label', default: 'Month')}" />
					
						<g:sortableColumn property="week" title="${message(code: 'sale.week.label', default: 'Week')}" />
					
						<g:sortableColumn property="amount" title="${message(code: 'sale.amount.label', default: 'Amount')}" />
					
						<th><g:message code="sale.book.label" default="Book" /></th>
					
						<g:sortableColumn property="date" title="${message(code: 'sale.date.label', default: 'Date')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${saleInstanceList}" status="i" var="saleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${saleInstance.id}">${fieldValue(bean: saleInstance, field: "year")}</g:link></td>
					
						<td>${fieldValue(bean: saleInstance, field: "month")}</td>
					
						<td>${fieldValue(bean: saleInstance, field: "week")}</td>
					
						<td>${fieldValue(bean: saleInstance, field: "amount")}</td>
					
						<td>${fieldValue(bean: saleInstance, field: "book")}</td>
					
						<td><g:formatDate date="${saleInstance.date}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${saleInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
