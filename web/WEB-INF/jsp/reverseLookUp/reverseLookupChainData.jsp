<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>

<table width="100%" background="#fff" class="sortable">
	<thead>
		<tr>
			<th><span onclick="updateSort(0)">Item #</span></th>
			<th><span onclick="updateSort(1)">Vendor</span></th>
			<th><span onclick="updateSort(2)">Pack</span></th>
			<th><span onclick="updateSort(3)">Size</span></th>
			<th><span onclick="updateSort(4)">Description</span></th>
			<th><span onclick="updateSort(5)">Carton UPC</span></th>
			<th><span onclick="updateSort(6)">Retail UPC</span></th>
			<th><span onclick="updateSort(7)">Quantity</span></th>
			<th><span onclick="updateSort(8)">Cost</span></th>
			<th><span onclick="updateSort(9)">Retail</span></th>
			<th><span onclick="updateSort(10)">PFT %</span></th>
		</tr>
	</thead>
	<c:forEach var="item" items="${data}" varStatus="stat2">
		<c:choose>
			<c:when test="${stat2.index % 2 == 0}">
				<tr class="highlight">
			</c:when>
			<c:otherwise>
				<tr class="normal">
			</c:otherwise>
		</c:choose>
			<td align="center" style="font-size: x-small">${item.number}</td>
			<td align="center" style="font-size: x-small">${item.vendorName}</td>
			<td align="left" style="font-size: x-small">${item.pack}</td>
			<td align="left" style="font-size: x-small">${item.size}</td>
			<td align="left" style="font-size: x-small">${item.description}</td>
			<td align="left" style="font-size: x-small">${item.cartonUPCNumber}</td>
			<td align="left" style="font-size: x-small">${item.retailUPCNumber}</td>
			<td align="right" style="font-size: x-small">${item.quantity}</td>
			<td align="right" style="font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.costAmount}"/></td>
			<td align="right" style="font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.srpAmount}"/></td>
			<td align="right" style="font-size: x-small"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${item.profitPercent}"/></td>
		</tr>
	</c:forEach>
				<tr><td colspan="11">&nbsp;</td></tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right" style="font-size: x-small">${totalUnits}</td>
					<td align="right" style="font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${totalCost}"/></td>
					<td align="right" style="font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${totalRetail}"/></td>
					<td>&nbsp;</td>
				</tr>
</table>
	</div><br/>
<script>sorttable.init();</script>