<%@ taglib uri="/core" prefix="c" %>
<div style="margin: 10px">
<c:choose>
	<c:when test="${not empty items}">
		<table width="100%">
			<tr>
				<th>Item Number</th>
				<th width="40%">Description</th>
				<th>Pack</th>
				<th>Size</th>
				<th>Carton UPC</th>
				<th>Retail UPC</th>
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
						<td><a href="#" onclick="makeSelection('${item.number}', '${item.description}')">${item.checkDigitItemNumber}</a></td>
						<td>${item.description}</td>
						<td>${item.pack}</td>
						<td>${item.size}</td>
						<td>${item.cartonUPCNumber}</td>
						<td>${item.retailUPCNumber}</td>
					</tr>
			</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<b>No Results Found</b>
		</c:otherwise>
</c:choose>
</div>