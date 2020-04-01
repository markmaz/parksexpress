<%@ taglib uri="/core" prefix="c" %>
<c:choose>
	<c:when test="${not empty items}">
		<table width="100%" style="left: 0px; font-size: small">
			<tr>
				<th width="20%">Customer</th>
				<th width="10%">Date</th>
				<th width="70%">Details</th>
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
					<td><span style="left: 0px; font-size: small">${item.user.firstName}&nbsp;${item.user.lastName}</span></td>
					<td><span style="left: 0px; font-size: small">${item.transactionDate}</span></td>		
					<td><span style="left: 0px; font-size: small">${item.details}</span></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<span style="left: 0px; font-size: small">No transactions in the last 7 days.</span>
	</c:otherwise>
</c:choose>