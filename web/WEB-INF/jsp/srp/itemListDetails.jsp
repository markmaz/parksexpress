<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt" %>

<div style="width: 700px" id="popUpContents">
	<div style="border: 1px #e5e5e5 solid; margin: 8px; background-color:#7d86aa;"><span style="margin: 5px; color:#FFF; font-size: small"><strong>${family.familyCode} - ${family.description}</strong></span></div>
	<div style="margin: 8px">
		<table width="100%" style="font: small">
			<tr>
				<th width="25%" align="center">Current Price</th>
				<th width="25%" align="center">Fixed or %</th>
				<th width="25%" align="center">New SRP</th>
				<th width="25%" align="center">Effective Date</th>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${family.pricing.fixed}">
						<td width="25%" align="center"><fmt:formatNumber currencySymbol="$" maxFractionDigits="2" maxIntegerDigits="4" value="${family.pricing.price}"/></td>
					</c:when>
					<c:otherwise>
						<td width="25%" align="center"><fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${family.pricing.percent}"/>%</td>
					</c:otherwise>
				</c:choose>	
				<td width="25%" align="center">
					<select id="itemIsFixed">
					<c:choose>
						<c:when test="${family.pricing.fixed}">
							<option value="F" selected="selected">Fixed</option>
							<option value="P">% Markup</option>
						</c:when>
						<c:otherwise>
							<option value="P" selected="selected">% Markup</option>
							<option value="F">Fixed</option>
						</c:otherwise>
					</c:choose>					
					</select>
				</td>
				<td width="25%" align="center"><input id="itemPricing" type="text" style="width: 100px"></td>
				<td width="25%" align="center"><input id="effectiveDate" type="text" style="width: 100px" value="${today}"></td>
			</tr>			
		</table>
	</div><br>
			<div style="margin: 8px; border: 1px #e5e5e5 solid; background-color: #f9f9f9"><span style="margin: 5px; color:#a04617; font-size: small"><strong>Items</strong></span></div>
				<div style="margin: 8px">
					<table width="100%" style="font: small">
						<tr>
							<th align="left"><span style="font-size: x-small">Description</span></th>
							<th align="center"><span style="font-size: x-small">Current Price</span></th>
							<th align="center"><span style="font-size: x-small">Fixed or %</span></th>
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
								<td align="left"><span style="font-size: x-small">${item.description}</span></td>
								<c:choose>
									<c:when test="${item.pricing.fixed}">
										<td align="center"><fmt:formatNumber currencySymbol="$" maxFractionDigits="2" maxIntegerDigits="4" value="${item.pricing.price}"/></td>
									</c:when>
									<c:otherwise>
										<td align="center"><fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${item.pricing.percent}"/>%</td>
									</c:otherwise>
								</c:choose>	
								<td align="center">
									<select id="isFixed" disabled="disabled">
									<c:choose>
										<c:when test="${item.pricing.fixed}">
											<option value="F" selected="selected">Fixed</option>
											<option value="P">% Markup</option>
										</c:when>
										<c:otherwise>
											<option value="P" selected="selected">% Markup</option>
											<option value="F">Fixed</option>
										</c:otherwise>
									</c:choose>					
									</select>								
								</td>
							</tr>		
						</c:forEach>		
					</table>
				</div>
			</div>	
<br>
<div style="border: 1px #e5e5e5 solid; margin: 8px;  padding: 5px; text-align: right"><input type="submit" value="Submit" onclick="postItems()"><input type="button" value="Remove" onclick="removeItems()"><input type="button" value="Cancel" onclick="closeShadow(true)"></div>