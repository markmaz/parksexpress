<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt" %>
				<div>
					<table class="sortable" >
						<thead>
							<tr>
								<th width="15%">Order Number</th>
								<th width="15%">Invoice Number</th>
								<th width="15%">Customer Number</th>
								<th width="12%">Date</th>
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
								<td><a href="#" onclick="createInvoice('${invoice.invoiceNumber }', '${invoice.orderNumber}', '${invoice.storeNumber}')">${invoice.invoiceNumber }</a></td>
								<td>${invoice.orderNumber}</td>
								<td>${invoice.storeNumber }</td>
								<td>${invoice.invoiceDate }</td>
							</tr>
						</c:forEach>						
					</table><br/>
			</div>
			
				<script>sorttable.init();</script>