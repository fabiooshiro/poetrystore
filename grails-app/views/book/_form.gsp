<%@ page import="poetrystore.Poem" %>



<div class="fieldcontain ${hasErrors(bean: poemInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="poem.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${poemInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: poemInstance, field: 'poet', 'error')} required">
	<label for="poet">
		<g:message code="poem.poet.label" default="Poet" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="poet" name="poet.id" from="${poetrystore.Poet.list()}" optionKey="id" required="" value="${poemInstance?.poet?.id}" class="many-to-one"/>
</div>

