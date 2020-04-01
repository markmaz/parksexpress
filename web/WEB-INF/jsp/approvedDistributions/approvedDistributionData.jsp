<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
	<div>
		<table width="100%" background="#fff" class="sortable">
			<thead>
				<tr>
					<th>Item</th>
					<th>Pack</th>
					<th>Size</th>
					<th>Description</th>
					<th>Carton UPC</th>
					<th>Retail UPC</th>
					<th>Quantity</th>
					<th>Price</th>
					<th>Retail</th>
					<th>Ship Date</th>
					<th>Inventory</th>
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
					<td style="font-size: x-small" align="center">${item.number}</td>
					<td style="font-size: x-small" align="center">${item.pack}</td>
					<td style="font-size: x-small" align="center">${item.size}</td>
					<td style="font-size: x-small" align="center">${item.description}</td>
					<td style="font-size: x-small" align="center">${item.cartonUPCNumber}</td>
					<td style="font-size: x-small" align="center">${item.retailUPCNumber}</td>
					<td style="font-size: x-small" align="center">${item.quantity}</td>
					<td style="font-size: x-small" align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.price}"/></td>
					<td style="font-size: x-small" align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.retail}"/></td>
					<td style="font-size: x-small" align="center">${item.shipDate}</td>
					<td style="font-size: x-small" align="center">${item.inventoryLevel}</td>
				</tr>
			</c:forEach>
		</table>
	</div><br/>
<script>sorttable.init();</script>