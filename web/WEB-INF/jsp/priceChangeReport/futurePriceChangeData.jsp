<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<c:forEach var="entry" items="${itemMap}" varStatus="stat">
	<div id="${entry.key.headerCode}">
		<span>${entry.key.description}</span>
		<table width="100%" background="#fff" class="sortable">
			<thead>
				<tr>
					<th>Item</th>
					<th>Pack</th>
					<th>Size</th>
					<th>Description</th>
					<th>Retail UPC</th>
					<th>Carton UPC</th>
					<th>Old $</th>
					<th>New $</th>
					<th>Diff</th>
					<th>Effective Date</th>
				</tr>
			</thead>
			<c:forEach var="item" items="${entry.value}" varStatus="stat2">
				<c:choose>
					<c:when test="${stat2.index % 2 == 0}">
						<tr class="highlight">
					</c:when>
					<c:otherwise>
						<tr class="normal">
					</c:otherwise>
				</c:choose>
					<td align="center">${item.number}</td>
					<td align="center">${item.pack}</td>
					<td align="center">${item.size}</td>
					<td align="left">${item.description}</td>
					<td align="center">${item.retailUPCNumber}</td>
					<td align="center">${item.cartonUPCNumber}</td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.oldPrice}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.newPrice}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.difference}"/></td>
					<td align="center">${item.effectiveDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div><br/>
</c:forEach>

<script>sorttable.init();</script>