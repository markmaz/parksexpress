<%@ taglib uri="/core" prefix="c" %>		
<%@ taglib uri="/fmt" prefix="fmt" %>

<div style="width: 850px">
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
					<select id="familyIsFixed">
					<c:choose>
						<c:when test="${family.pricing.fixed}">
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
				<td width="25%" align="center"><input id="familyPricing" type="text" style="width: 100px"></td>
				<td width="25%" align="center"><input id="familyEffectiveDate" type="text" style="width: 100px" value="${today}"></td>
			</tr>			
		</table>
	</div><br>
	<div style="border: 1px #e5e5e5 solid; margin: 8px;  padding: 5px; text-align: right"><input type="submit" value="Submit" onclick="addFamilyPricing('${family.familyCode}')"><input type="button" value="Remove" onclick="removeFamilyPricing('${family.familyCode}')"></div>
	<br><br>
	<c:choose>
		<c:when test="${not empty familyExceptions}">
			<div style="border: 1px #e5e5e5 solid; background-color:#7d86aa;"><span style="margin: 5px; color:#FFF; font-size: small"><strong>Zone Exceptions</strong></span></div>
		
			<c:forEach var="familyException" items="${familyExceptions}">
				<div style="border: 1px #e5e5e5 solid; background-color: #f9f9f9"><span style="margin: 5px; color:#a04617; font-size: small; cursor: pointer;" onclick="openException('z_${familyException.priceBook.number}', 'img_${familyException.priceBook.number}')"><span id="img_${familyException.priceBook.number}">(+)</span>&nbsp;<strong>${familyException.priceBook.number} - ${familyException.priceBook.description}</strong></span></div>
				<div id="z_${familyException.priceBook.number}" style="display:none">
					<div style="background-color: #fff"><span style="margin: 5px; color:#FFF; font-size: small">
						<table width="100%" style="font: small">
							<tr>
								<th width="25%" align="center"><span style="font-size: x-small">Current Price</span></th>
								<th width="25%" align="center"><span style="font-size: x-small">Fixed or %</span></th>
								<th width="25%" align="center"><span style="font-size: x-small">New SRP</span></th>
								<th width="25%" align="center"><span style="font-size: x-small">Effective Date</span></th>
							</tr>
							<tr>
								<td width="25%" align="center"><span style="font-size: x-small">${familyException.family.pricing.price}</span></td>
								<td width="25%" align="center">
									<select id="${familyException.priceBook.number}.familyIsFixed.exception">
									<c:choose>
										<c:when test="${familyException.family.pricing.fixed}">
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
								<c:choose>
									<c:when test="${isParksBook == true }">
										<td width="25%" align="center">&nbsp;</td>
										<td width="25%" align="center"><input type="text" style="width: 100px" value="${today}" id="${familyException.priceBook.number}.calendar.exception"></td>
									</c:when>
									<c:otherwise>
										<td width="25%" align="center"><input type="text" style="width: 100px" id="${familyException.priceBook.number}.srp.exception"></td>
										<td width="25%" align="center"><input type="text" style="width: 100px" value="${today}" id="${familyException.priceBook.number}.calendar.exception"></td>
									</c:otherwise>
								</c:choose>
							</tr>			
						</table>		
					</div>
					<c:choose>
						<c:when test="${isParksBook == true }">					

						</c:when>
						<c:otherwise>
								<div style="margin: 8px; border: 1px #e5e5e5 solid; padding: 5px; text-align: right">
									<input type="submit" value="Submit" onclick="changeFamilyException('${familyException.priceBook.number}', '${family.familyCode}')">
									<input type="button" value="Remove" onclick="removeFamilyException('${familyException.priceBook.number}','${family.familyCode}')">
								</div>
						</c:otherwise>
					</c:choose>
					<br><br>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div style="border: 1px #e5e5e5 solid; background-color:#7d86aa;"><span style="margin: 5px; color:#FFF; font-size: small"><strong>No Family Exceptions</strong></span></div>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${not empty itemExceptions}">
			<div style="margin: 8px; border: 1px #e5e5e5 solid; background-color: #f9f9f9"><span style="margin: 5px; color:#a04617; font-size: small"><strong>Item Exceptions</strong></span></div>
				<div style="margin: 8px">
					<table width="100%" style="font: small" id="itemExceptions" class="sortable">
						<tr>
							<th align="center"><span style="font-size: x-small">Zone</span></th>
							<th align="left"><span style="font-size: x-small">Description</span></th>
							<th align="center"><span style="font-size: x-small">Current Price</span></th>
							<th align="center"  class="sorttable_nosort"><span style="font-size: x-small">Fixed or %</span></th>
							<th align="center"  class="sorttable_nosort"><span style="font-size: x-small">New SRP</span></th>
							<th align="center"  class="sorttable_nosort"><span style="font-size: x-small">Effective Date</span></th>
							<th align="center"  class="sorttable_nosort"></th>
						</tr>
						<c:forEach var="itemException" items="${itemExceptions}" varStatus="stat">
							<c:choose>
								<c:when test="${stat.count % 2 == 0}">
									<tr class="highlight" id="${itemException.item.number}_row">
								</c:when>
								<c:otherwise>
									<tr class="normal" id="${itemException.item.number}_row">
								</c:otherwise>
							</c:choose>
								<td align="center"><span style="font-size: x-small">${itemException.book.description}</span></td>
								<td align="left"><span style="font-size: x-small">${itemException.item.checkDigitItemNumber} - ${itemException.item.description}</span></td>
								<c:choose>
									<c:when test="${itemException.item.pricing.fixed}">
										<td align="center"><fmt:formatNumber currencySymbol="$" maxFractionDigits="2" maxIntegerDigits="4" value="${itemException.item.pricing.price}"/></td>
									</c:when>
									<c:otherwise>
										<td align="center">${itemException.item.pricing.percent}</td>
									</c:otherwise>
								</c:choose>	
								<td align="center">
									<select id="${itemException.item.number}_${itemException.book.number}_fixed">
									<c:choose>
										<c:when test="${itemException.item.pricing.fixed}">
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
								
								<c:choose>
									<c:when test="${isParksBook == true }">
										<td align="center">&nbsp;</td>
										<td align="center">&nbsp;</td>
										<td align="center">&nbsp;</td>
									</c:when>
									<c:otherwise>
										<td align="center"><input id="${itemException.item.number}_${itemException.book.number}_srp" type="text" style="width: 100px"></td>
										<td align="center"><input id="${itemException.item.number}_${itemException.book.number}_effDate" type="text" style="width: 100px" value="${today}"></td>
										<td align="center"><span id="change_${itemException.item.number}_${itemException.book.number}"><a href="#" onclick="changeItemPricing('${itemException.item.number}', '${itemException.item.number}_${itemException.book.number}_fixed', '${itemException.item.number}_${itemException.book.number}_srp', '${itemException.item.number}_${itemException.book.number}_effDate', '${itemException.book.number}', true)"><span style="font-size: x-small">Change</span></a></span>&nbsp;<a href="#" onclick="removePricing('${itemException.item.number}', '${itemException.book.number}', 'itemExceptions')"><span style="font-size: x-small">Remove</span></a></td>
									</c:otherwise>
								</c:choose>
							</tr>		
						</c:forEach>		
					</table>
				</div>
			</div>		
		</c:when>
		<c:otherwise>
			<div style="border: 1px #e5e5e5 solid; background-color:#7d86aa;"><span style="margin: 5px; color:#FFF; font-size: small"><strong>No Item Exceptions</strong></span></div>
		</c:otherwise>
	</c:choose>
</div>
<script>sorttable.init();</script>
