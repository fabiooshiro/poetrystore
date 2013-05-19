<%@ page import="poetrystore.Poet" %>



<div class="fieldcontain ${hasErrors(bean: poetInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="poet.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${poetInstance?.name}"/>
</div>

