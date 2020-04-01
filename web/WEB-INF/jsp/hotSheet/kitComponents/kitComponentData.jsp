<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

	<table cellpadding="5">
		<thead>
			<tr>
				<th width="10%">Kit Number</th>
				<th width="20%">Kit Description</th>
				<th width="10%">Component Number</th>
				<th width="10%">Pack</th>
				<th width="10%">Size</th>
				<th width="10%">Quantity</th>
				<th width="25%">Description</th>
				<th width="10%">Price</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td width="10%">${kit.checkDigitItemNumber}</td>
				<td width="20%" colspan="7">${kit.description}</td>
			</tr>
			<c:forEach var="kitItem" items="${kit.components}">
				<tr>
					<td width="10%">&nbsp;</td>
					<td width="20%">&nbsp;</td>
					<td width="10%">${kitItem.checkDigitItemNumber}</td>
					<td width="10%">${kitItem.pack}</td>
					<td width="10%">${kitItem.size}</td>
					<td width="10%" align="right">${kitItem.quantity}</td>			
					<td width="25%">${kitItem.description}</td>
					<td width="10%" align="right">$${kitItem.formattedPrice}</td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>