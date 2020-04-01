<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript">
      window.onload = function() {
        Calendar.setup({
          dateField      : 'startDate',
          triggerElement : 'startDate'
        });
        
        Calendar.setup({
          dateField      : 'endDate',
          triggerElement : 'endDate'
        });
      };
      
	var currentStore = "";
	
	function highlight(el){
		document.getElementById(el).style.background = '#C0C0C0';
	}
	
	function unhighlight(el){
		document.getElementById(el).style.background = '#FFF';
	}	
	
	function addStore(storeName, storeNumber){
		$('storeValue').value = storeNumber;
		$('store').value= storeName;
	}
	

function findInvoice(){
	var element = document.getElementById("storeSelect");
	
	var selectedIndex = element.selectedIndex;
	var option = element.options[selectedIndex];
	var storeNumber = option.value;
		
	if(storeNumber == ""){
		alert("You must select a store");
		return false;
	}
	
	if($('startDate').value == ""){
		alert("The start date can not be blank");
		return false;
	}
	
	if($('endDate').value == ""){
		alert("The end date can not be blank.");
		return false;
	}
	
	$('data').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	currentStore = storeNumber;
	new Ajax.Updater('data', 'invoiceReprint?action=find&code=' + storeNumber + "&start=" + $('startDate').value + "&end=" + $('endDate').value, {evalScripts: true});	
	
}

function printReport(){
	if(currentStore == ""){
		alert("You must first select a store");
		return false;
	}
	
	window.open('invoice?action=print&code=' + currentStore, "Invoice");
}

function exportReport(){
	if(currentStore == ""){
		alert("You must first select a store");
		return false;
	}
	
	window.open('invoice?action=export&code='+ currentStore, "Invoice");
}

function createInvoice(invoice, order, store){
	new Ajax.Request('invoiceReprint?action=create&invoice=' + invoice + '&order=' + order + '&store=' + store, {
		onSuccess: function(transport){
			window.open("http://www.parksexpress.com/invoices/INV0" + invoice + ".PDF", "Invoice");		
		}
	})
}

function createReport(){
	var storeName = "";
	var storeNumber = "";
	
	for(i = 0; i < $('storeSelect').options.length; i++){
		if($('storeSelect').options[i].selected == true){
			storeName = $('storeSelect').options[i].value;
			storeNumber = $('storeSelect').options[i].id;
		}
	}	
	
	findInvoice(storeName, storeNumber);
}
</script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div style="font-size: large; color:#a04617; padding: 10px; float: left">Invoice Reprint</div>
		<script type="text/javascript" src="scripts/sorttable.js"></script>
		<div style="text-align: right; padding: 10px; "><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
	</div>	
	<div class="contentPanelLeft" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader">
			<span><strong>Select a Store</strong></span>
		</div>
		<div id="StoreColumn">
			<div id="stores">
				<div>
					<span style="color:#a04617; font-size: small">
						<select size="15" style="width: 180px; margin-left: 8px;" id="storeSelect" onchange="findInvoice()">
							<c:forEach var="store" items="${stores}">							
								<option value="${store.number}" id="${store.number}">${store.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
			</div>
		</div>	
	</div>
	<div class="contentPanelMiddle" style="float: left; width: 683px;  margin: 8px;  background-color: #ffffff;">
		<div>
			<div id="searchCriteria" style="padding: 10px; background-color: #f9f9f9; border: 1px #e5e5e5 solid;">
				<table>
					<tr>
						<td style="border: 0px;">Start Date:</td>
						<td style="border: 0px;"><input type="text" style="width: 150px; color: #888;" id="startDate" value="${startDate}"/></td>
						<td style="border: 0px;">End Date:</td>
						<td style="border: 0px;" align="right"><input type="text" style="width: 150px;color: #888;" id="endDate" value="${endDate}"/></td>
					</tr>		
					<tr>
						<td style="border: 0px;" colspan="4" align="right"><input type="button" value="Search" onclick="createReport()"/></td>
					</tr>		
				</table>
			</div>
		</div>
		<br/>
		<div id="data">
				
		</div> 
	</div>
