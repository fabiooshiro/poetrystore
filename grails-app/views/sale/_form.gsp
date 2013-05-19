<%@ page import="poetrystore.PoemSale" %>

<div class="fieldcontain ${hasErrors(bean: saleInstance, field: 'poem', 'error')} required">
	<label for="poem">
		<g:message code="sale.poem.label" default="Poem" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="poem" name="poem.id" from="${poetrystore.Poem.list()}" optionKey="id" required="" value="${saleInstance?.poem?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: saleInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="sale.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${saleInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: saleInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="sale.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="price" value="${fieldValue(bean: saleInstance, field: 'price')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: saleInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="sale.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quantity" type="number" value="${saleInstance.quantity}" required=""/>
</div>
