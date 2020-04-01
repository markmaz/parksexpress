<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
	<table class="sortable" width="100%">
		<thead>
			<tr>
				<th>Item Number</th>
				<th>Pack</th>
				<th>Size</th>
				<th>Description</th>
				<th>Carton UPC</th>
				<th>Retail UPC</th>
				<th>Regular Price</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${suspendedItems}" varStatus="stat">
			<c:choose>
				<c:when test="${stat.count % 2 == 0}">
					<tr class="highlight">
				</c:when>
				<c:otherwise>
					<tr class="normal">
				</c:otherwise>
			</c:choose>
			<td style="font-size: x-small">${item.checkDigitItemNumber}</td>
			<td style="font-size: x-small">${item.size}</td>
			<td style="font-size: x-small">${item.pack}</td>
			<td style="font-size: x-small">${item.description}</td>
			<td style="font-size: x-small">${item.cartonUPCNumber}</td>
			<td style="font-size: x-small">${item.retailUPCNumber}</td>
			<td style="font-size: x-small" align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.marketCost}"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script>sorttable.init();</script>