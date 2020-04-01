<%@ taglib uri="/core" prefix="c" %>		
<%@ taglib uri="/fmt" prefix="fmt" %>

<table width="670px" id="familyItemList">
	<tr>
		<th>&nbsp;</th>
		<th width="30%">Description</th>
		<th>Pack</th>
		<th>Size</th>
		<th>Retail UPC</th>
		<th>Carton UPC</th>
		<th>Cost</th>
		<th>Retail</th>
		<th>GP%</th>
		<th>F/P</th>
		<th>Order Guide</th>
	</tr>
	<c:forEach var="famItem" items="${family.items}" varStatus="stat">
		<c:choose>
			<c:when test="${stat.count % 2 == 0}">
				<tr class="highlight">
			</c:when>
			<c:otherwise>
				<tr class="normal">
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${isParksBook == true }">
				<td>&nbsp;</td>
				<td><span style="font-size: x-small">${famItem.checkDigitItemNumber} - ${famItem.description}</span></td>
			</c:when>
			<c:otherwise>
				<td><input type="checkbox" id="chk_${famItem.number}" onclick="checkSum('chk_${famItem.number}', 'familyItemList')" value="${famItem.number}" class="itemNumber"></td>
				<td><span style="font-size: x-small"><a style="font-size: x-small" id="A_${famItem.number}" href="#" onclick="openModal('${famItem.number}')">${famItem.checkDigitItemNumber} - ${famItem.description}</a></span></td>
			</c:otherwise>
		</c:choose>
						
		<td><span style="width: 25px; font-size: x-small">${famItem.pack}</span></td>
		<td><span style="width: 25px; font-size: x-small">${famItem.size}</span></td>
		<td><span style="width: 25px; font-size: x-small">${famItem.cartonUPCNumber}</span></td>
		<td><span style="width: 25px; font-size: x-small">${famItem.retailUPCNumber}</span></td>
		<td><span style="font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" maxIntegerDigits="4" value="${famItem.marketCost}"/></span></td>
		<td><span style="width: 25px; font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" maxIntegerDigits="4" value="${famItem.pricing.price}"/></span></td>
		<td><span style="width: 25px; font-size: x-small"><fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${famItem.grossProfit}"/>%</span></td>
		<td>
			<c:choose>
				<c:when test="${famItem.pricing.fixed}">
					<input disabled="disabled" type="radio" name="C_${famItem.description}" value="F" checked>
					<input disabled="disabled" type="radio" name="C_${famItem.description}" value="P">
				</c:when>
				<c:otherwise>
					<input disabled="disabled" type="radio" name="C_${famItem.description}" value="F">
					<input disabled="disabled" type="radio" name="C_${famItem.description}" value="P" checked>							
				</c:otherwise>
			</c:choose>
		</td>
		<td align="center">
			<c:choose>
				<c:when test="${famItem.inOrderGuide}">
					<input type="checkbox" id="og_${famItem.number}" value="OG" checked onclick="updateOrderGuide('${famItem.number}', 'og_${famItem.number}')">
				</c:when>
				<c:otherwise>
					<input type="checkbox" id="og_${famItem.number}" value="NOG" onclick="updateOrderGuide('${famItem.number}', 'og_${famItem.number}')">
				</c:otherwise>
			</c:choose>
		</td>							
		</tr>
	</c:forEach>
</table>				
