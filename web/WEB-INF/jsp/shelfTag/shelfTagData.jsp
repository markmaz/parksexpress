<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
	<div>
		<table width="100%" background="#fff" class="sortable">
			<thead>
				<tr>
					<th>Store</th>
					<th>Item</th>
					<th>Pack</th>
					<th>Description</th>
					<th>Retail UPC</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<c:forEach var="shelfTag" items="${shelfTagList}" varStatus="stat2">
				<c:choose>
					<c:when test="${stat2.index % 2 == 0}">
						<tr class="highlight">
					</c:when>
					<c:otherwise>
						<tr class="normal">
					</c:otherwise>
				</c:choose>
					<td style="font-size: x-small" align="center">${shelfTag.storeOrChainName}</td>
					<td style="font-size: x-small" align="center">${shelfTag.item.checkDigitItemNumber}</td>
					<td style="font-size: x-small" align="center">${shelfTag.item.pack}</td>
					<td style="font-size: x-small" align="center">${shelfTag.item.description}</td>
					<td style="font-size: x-small" align="center">${shelfTag.item.cartonUPCNumber}</td>
					<td style="font-size: x-small" align="center"><a href="#" onclick="removeRow('${shelfTag.itemNumber}')">Remove</a></td>
				</tr>
			</c:forEach>
		</table>
		<table>
			<tr>
				<td align="right"><input type="button" value="Submit" onclick="submitTags()"/></td>
			</tr>
		</table>
	</div><br/>
<script>sorttable.init();</script>