<%@ taglib uri="/core" prefix="c" %>
<c:choose>
	<c:when test="${not empty items}">
		<table width="100%" style="left: 0px; font-size: xx-small">
			<tr>
				<th width="10%">Date</th>
				<th width="90%">Details</th>
			</tr>
			
			<c:forEach var="item" items="${items}" varStatus="stat">
					<c:choose>
						<c:when test="${stat.count % 2 == 0}">
							<tr class="highlight">
						</c:when>
						<c:otherwise>
							<tr class="normal">
						</c:otherwise>
					</c:choose>
					<td><span style="left: 0px; font-size: xx-small">${item.transactionDate}</span></td>		
					<td><span style="left: 0px; font-size: xx-small">${item.details}</span></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p>No Transactions</p>
	</c:otherwise>
</c:choose>