<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt" %>
<div style="width: 700px" id="popUpContents">
	<div style="border: 1px #e5e5e5 solid; margin: 8px; background-color:#7d86aa;"><span style="margin: 5px; color:#FFF; font-size: small"><strong>${item.checkDigitItemNumber} - ${item.description}</strong></span></div>
	<div style="margin: 8px" id="itemSRP" name="itemSRP">
		<table width="100%" style="font: small">
			<tr>
				<th width="25%" align="center">Current Price</th>
				<th width="25%" align="center">Fixed or %</th>
				<th width="25%" align="center">New SRP</th>
				<th width="25%" align="center">Effective Date</th>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${item.pricing.fixed}">
						<td width="25%" align="center"><fmt:formatNumber currencySymbol="$" maxFractionDigits="2" maxIntegerDigits="4" value="${item.pricing.price}"/></td>
					</c:when>
					<c:otherwise>
						<td width="25%" align="center"><fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${item.pricing.percent}"/>%</td>
					</c:otherwise>
				</c:choose>	
				<td width="25%" align="center">
					<select id="itemIsFixed">
					<c:choose>
						<c:when test="${item.pricing.fixed}">
							<option value="F" selected="selected">Fixed</option>
							<option value="P">% Markup</option>
						</c:when>
						<c:otherwise>
							<option value="F">Fixed</option>
							<option value="P" selected="selected">% Markup</option>
						</c:otherwise>
					</c:choose>					
					</select>
				</td>
				<td width="25%" align="center"><input id="itemPricing" type="text" style="width: 100px"></td>
				<td width="25%" align="center"><input id="effectiveDate" type="text" style="width: 100px" value="${today}"></td>
			</tr>			
		</table>
	</div>
	<div style="border: 1px #e5e5e5 solid; margin: 8px;  padding: 5px; text-align: right"><input type="submit" value="Submit" onclick="postForm('itemSRP', '${item.number}')"><input type="button" value="Remove" onclick="removeItemPricing('${item.number}')"><input type="button" value="Cancel" onclick="closeShadow()"></div>