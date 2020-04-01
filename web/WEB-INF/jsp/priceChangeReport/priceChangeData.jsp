<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<c:forEach var="entry" items="${itemMap}" varStatus="stat">
	<div id="${entry.key.headerCode}">
		<span>${entry.key.headerCode} - ${entry.key.description}</span>
		<table width="100%" background="#fff" class="sortable">
			<thead>
				<tr>
					<th><span onclick="updateSort('0')">Family</span></th>
					<th><span onclick="updateSort('1')">Item</span></th>
					<th><span onclick="updateSort('2')">Description</span></th>
					<th><span onclick="updateSort('3')">Old $</span></th>
					<th><span onclick="updateSort('4')">New $</span></th>
					<th><span onclick="updateSort('5')">Diff</span></th>
					<th><span onclick="updateSort('6')">Old SRP</span></th>
					<th><span onclick="updateSort('7')">New SRP</span></th>
					<th><span onclick="updateSort('8')">Effective Date</span></th>
					<th align="center">F/%</th>
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
					<td align="center" style="font-size: small">${item.priceBookFamilyCode}</td>
					<td align="center">${item.number}</td>
					<td align="left">${item.description}</td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.oldPrice}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.newPrice}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.difference}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.oldSrp}"/></td>
					<td align="right"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${item.newSrp}"/></td>
					<td align="center">${item.effectiveDate}</td>
					<td align="center">${item.fixedOrPercent}</td>
				</tr>
			</c:forEach>
		</table>
	</div><br/>
</c:forEach>

<script>sorttable.init();</script>