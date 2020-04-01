<%@ taglib uri="/core" prefix="c" %>
<table id="storeInfo" name="storeInfo" cellpadding="0" cellspacing="0" width="75%">
	<tbody>
		<tr>
			<th width="10%">
				Store Number
			</th>
			<th width="25%">
				Store Name
			</th>
			<th width="35%">
				Address
			</th>
			<th width="15%">
				City
			</th>
			<th width="5%">
				State
			</th>
			<th width="10%">
				&nbsp;
			</th>
		</tr>
		<c:forEach var="store" items="${stores}" varStatus="stat">
			<c:choose>
				<c:when test="${stat.count % 2 == 0}">
					<tr class="highlight">
				</c:when>
				<c:otherwise>
					<tr class="normal">
				</c:otherwise>
			</c:choose>
			<td width="10%">
				${store.number}
			</td>
			<td width="25%">
				${store.name}
			</td>
			<td width="35%">
				${store.address}
			</td>
			<td width="15%">
				${store.city}
			</td>
			<td width="5%">
				${store.state}
			</td>
			<td width="10%">
				<a href="#" onclick="removeStore('${store.number}')">Remove</a>
			</td>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="newUserID" value="${userID}">