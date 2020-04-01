<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt" %>
				<div>
					<table>
						<thead>
							<tr>
								<th width="15%">Date</th>
								<th width="15%">Invoice #</th>
								<th width="15%">Type</th>
								<th width="12%">Amt Due</th>
								<th width="12%">Inv Amt</th>
								<th width="12%">Pym Amt</th>
								<th width="12%">Adj Amt</th>
								<th width="12%">Balance</th>
							</tr>				
						</thead>
						
						<c:forEach var="invoice" items="${invoices}" varStatus="stat">
							<c:choose>
								<c:when test="${stat.index % 2 == 0}">
									<tr class="highlight">
								</c:when>
								<c:otherwise>
									<tr class="normal">
								</c:otherwise>
							</c:choose>
								<td>${invoice.date}</td>
								<td>${invoice.number }</td>
								<td>${invoice.type }</td>
								<td align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="0"/></td>
								<td align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${invoice.invoiceAmount }"/></td>
								<td align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${invoice.paymentAmount }"/></td>
								<td align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${invoice.adjustmentAmount }"/></td>
								<td align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${invoice.balance}"/></td>
							</tr>
						</c:forEach>						
					</table><br/>
					<table>
							<tr>
								<td width="15%">&nbsp;</td>
								<td width="15%">&nbsp;</td>
								<td width="15%"><strong>Total</strong></td>
								<td width="12%">&nbsp;</td>
								<td width="12%" align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${totalInvoiceAmount}"/></td>
								<td width="12%" align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${totalPaymentAmount}"/></td>
								<td width="12%" align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${totalAdjustmentAmount}"/></td>
								<td width="12%" align="right"><fmt:formatNumber type="currency" currencySymbol="$"  maxFractionDigits="2" value="${totalBalanceAmount}"/></td>
							</tr>					
					</table>
			</div>