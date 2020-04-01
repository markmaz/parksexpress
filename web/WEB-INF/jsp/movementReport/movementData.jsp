<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<table width="100%" background="#fff" class="sortable">
	<thead>
		<tr>
			<th>Item #</th>
			<th>Pack</th>
			<th>Size</th>
			<th>Description</th>
			<th>Retail UPC</th>
			<th>Carton UPC</th>
			<th>Units</th>
			<th>Cost Amt</th>
			<th>SRP Amt</th>
			<th>PFT %</th>
			<th>PFT $</th>
			<th>Vendor #</th>
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
				<td>${item.number}</td>
				<td>${item.pack}</td>
				<td>${item.size}</td>
				<td>${item.description}</td>
				<td>${item.retailUPCNumber}</td>
				<td>${item.cartonUPCNumber}</td>
				<td>${item.fullCase}</td>
				<td><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.extendedCostAmount}"/></td>
				<td><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.srp}"/></td>
				<td>${item.profitPercent}</td>
				<td><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.profitDollars}"/></td>
				<td>${item.vendorNumber}</td>
			</tr>
	</c:forEach>
</table>
	<script>sorttable.init();</script>