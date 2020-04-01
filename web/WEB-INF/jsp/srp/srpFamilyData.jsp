<%@ taglib uri="/core" prefix="c" %>		
<%@ taglib uri="/fmt" prefix="fmt" %>
		<div class="contentPanelSRP" id="family" style="width:680px">
			<strong>${family.familyCode} - ${family.description}</strong>
			<div style="background-color: #fff">
				<table width="675px">
					<tr>
						<th width="50%">Description</th>
						<th>Retail</th>
						<th>GP%</th>
						<th>F/P</th>
					</tr>
					<tr>
						<td><span style="font-size: x-small">
						<c:choose>
							<c:when test="${isParksBook == true }">
								${family.familyCode} - ${family.description}
							</c:when>
							<c:otherwise>
									<c:choose>
								    	<c:when test="${not empty futureFamilyPricing}">
								    		<c:choose>
								    			<c:when test="${futureFamilyPricing.fixed}">
													<a title="Effective Date: ${futureFamilyPricing.effectiveDate} Price: <fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" maxIntegerDigits="4" value="${futureFamilyPricing.price}"/>" style="font-size: x-small; color: #a04617" href="#" onclick="openFamilyForm('${family.familyCode}', '${family.description}')">
								    			</c:when>
								    			<c:otherwise>
								    				<a title="Effective Date: ${futureFamilyPricing.effectiveDate} Percent: <fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${family.grossProfit}"/>%"	 style="font-size: x-small; color: #a04617" href="#" onclick="openFamilyForm('${family.familyCode}', '${family.description}')">
								    			</c:otherwise>
								    		</c:choose>
								    	</c:when>
								    	<c:otherwise>
											<a href="#" style="font-size: x-small" onclick="openFamilyForm('${family.familyCode}', '${family.description}')">
								    	</c:otherwise>
								    </c:choose>
									${family.familyCode} - ${family.description}
									</a>
							</c:otherwise>
						</c:choose>
						
						</span></td>
						<td><span style="width: 25px; font-size: x-small"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" maxIntegerDigits="4" value="${family.pricing.price}"/></span></td>
						<td><span style="width: 25px; font-size: x-small"><fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${family.grossProfit}"/>%</span></td>
						<td>
							<c:choose>
								<c:when test="${family.pricing.fixed}">
									<input disabled="disabled" type="radio" name="${family.familyCode}" value="F" checked>
									<input disabled="disabled" type="radio" name="${family.familyCode}" value="P">
								</c:when>
								<c:otherwise>
									<input disabled="disabled" type="radio" name="${family.familyCode}" value="F">
									<input disabled="disabled" type="radio" name="${family.familyCode}" value="P" checked>							
								</c:otherwise>
							</c:choose>
						</td>					
					</tr>
				</table>
				<c:choose>
					<c:when test="${not empty family.items}">
						<table width="675px" id="familyItemList">
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
								
								<c:if test="${family.pricing.price != famItem.pricing.price}">
									<tr class="priceDifference">
								</c:if>
								
									<c:choose>
										<c:when test="${isParksBook == true }">
											<td>&nbsp;</td>
											<td><span style="font-size: x-small">${famItem.checkDigitItemNumber} - ${famItem.description}</span></td>
										</c:when>
										<c:otherwise>
											<td><input type="checkbox" id="chk_${famItem.number}" onclick="checkSum('chk_${famItem.number}', 'familyItemList')" value="${famItem.number}" class="itemNumber"></td>
											<td><span style="font-size: x-small">
										<c:choose>
											<c:when test="${not empty famItem.futurePricing}">
											<c:choose>
												<c:when test="${famItem.futurePricing.fixed}">
													<a title="Effective Date: ${famItem.futurePricing.effectiveDate} Price: <fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" maxIntegerDigits="4" value="${famItem.futurePricing.price}"/>" style="font-size: x-small; color: #a04617" id="A_${famItem.number}" href="#" onclick="openModal('${famItem.number}', '${famItem.description}')">
												</c:when>
												<c:otherwise>
													<a title="Effective Date: ${famItem.futurePricing.effectiveDate} Percent: <fmt:formatNumber maxFractionDigits="2" maxIntegerDigits="4" value="${famItem.futurePricing.percent}"/>%" style="font-size: x-small; color: #a04617" id="A_${famItem.number}" href="#" onclick="openModal('${famItem.number}', '${famItem.description}')">												
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<a style="font-size: x-small" id="A_${famItem.number}" href="#" onclick="openModal('${famItem.number}', '${famItem.description}')">
										</c:otherwise>
									</c:choose>
									
									${famItem.checkDigitItemNumber} - ${famItem.description}
									</a></span></td>
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
					</c:when>
					<c:otherwise>
						<table width="675px" id="familyItemList">
							<tr>
								<td>This family contains no items.</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</div>			
		</div>
		<br>
		<div class="contentPanelSRP" style="width: 680px">
			<div>
				<span id="discontinued" style="cursor: pointer; color: #FFF" onclick="showSuspended('${family.familyCode}')">(+)</span><strong>&nbsp;Discontinued & Suspended Items</strong>
			</div>
			<div id="suspendedItems" style="background-color: #fff; display: none">
				<table width="100%">
					<tr>
						<td>No items found.</td>
					</tr>
				</table>
			</div>
		</div>		
		<br>