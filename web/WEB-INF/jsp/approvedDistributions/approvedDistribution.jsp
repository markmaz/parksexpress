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
      
	function highlight(el){
		document.getElementById(el).style.background = '#C0C0C0';
	}
	
	function unhighlight(el){
		document.getElementById(el).style.background = '#FFF';
	}	
	
	function addStore(storeName, storeNumber){
		var element = document.getElementById("storeSelect");
	
		var selectedIndex = element.selectedIndex;
		var option = element.options[selectedIndex];
		var values = element.options[selectedIndex].value.split(":");
		
		$('storeValue').value = values[1];
		$('store').innerHTML= values[0];
	}
	
	function createAuto() {
		var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'srp', {
			minChars : 2,
			parameters : 'action=searchAhead',
			afterUpdateElement: setValues
		});
	}	
	
	function createReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var item = $('number').value;
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		$('data').innerHTML = "<center><br><img src='images/ajax-loader.gif'/></center>";
		var url = 'approvedDistributions?action=data&start=' + start + '&end=' + end + '&item=' + item + '&store=' + store;
		new Ajax.Updater('data', url, {evalScripts: true});
		
	}
	
	function setValues(text, li){
		$('number').value = li.id;
		$('type').value = li.type;
	}
	
	function printReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var item = $('number').value;
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		window.open('approvedDistributions?action=print&start=' + start + '&end=' + end + '&item=' + item + '&store=' + store, "Approved_Distributions_Report");
	}
	
	function exportReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var item = $('number').value;
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		window.open('approvedDistributions?action=export&start=' + start + '&end=' + end + '&item=' + item + '&store=' + store, "Approved_Distributions_Report");
	}	
</script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div style="font-size: large; color:#a04617; padding: 10px; float: left">Approved Distributions</div>
		<script type="text/javascript" src="scripts/sorttable.js"></script>
		<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">Print</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">Export</a></div>
	</div>	
	<div class="contentPanelLeft" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader">
			<span><strong>Select a Store</strong></span>
		</div>
		<div id="StoreColumn">
			<div id="stores">
				<div>
					<span style="color:#a04617; font-size: small">
						<select size="15" style="width: 180px; margin-left: 8px;" id="storeSelect" onchange="addStore()">
							<option value="${chain.webSafeName}:${chain.number}" id="${chain.number}">${chain.name}</option>
							<c:forEach var="store" items="${stores}">							
								<option value="${store.webSafeName}:${store.number}" id="${store.number}">${store.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
			</div>
		</div>	
	</div>
	<div class="contentPanelMiddle" style="float: left; width: 680px;  margin: 8px;  background-color: #ffffff;">
		<div>
			<div id="searchCriteria" style="padding: 10px; background-color: #f9f9f9; border: 1px #e5e5e5 solid;">
				<table>
					<tr>
						<td style="border: 0px;">Store:</td>
						<td style="border: 0px;" colspan="3"><span id="store"></span>
							
						</td>						
					</tr>
					<tr>
						<td style="border: 0px;">Item/Family:</td>
						<td style="border: 0px;" colspan="3">
							<input id="criteria" type="text" style="width: 400px" name="criteria"/>
							<div id="autocomplete" class="autocomplete" name="autocomplete"></div>
						</td>
						
					</tr>
					<tr>
						<td style="border: 0px;">Start Date:</td>
						<td style="border: 0px;"><input type="text" style="width: 150px; color: #888;" id="startDate" value="${startDate}"/></td>
						<td style="border: 0px;">End Date:</td>
						<td style="border: 0px;" align="right"><input type="text" style="width: 150px;color: #888;" id="endDate" value="${endDate}"/></td>
					</tr>		
					<tr>
						<input type="hidden" id="number"/>
						<input type="hidden" id="type"/>
						<input type="hidden" id="storeValue"/>
						<td style="border: 0px;" colspan="4" align="right"><input type="button" value="Search" onclick="createReport()"/></td>
					</tr>		
				</table>
			</div>
		</div>
		<br/>
		<div id="data">
				
		</div> 
	</div>
	<script>
		createAuto();
	</script>