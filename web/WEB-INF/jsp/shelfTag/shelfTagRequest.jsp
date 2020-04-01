<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript">
	Shadowbox.init({
		skipSetup: true,
		players:["html"]
	});
      
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
	
	function createAuto() {
		var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'srp', {
			minChars : 2,
			parameters : 'action=searchAheadItemsOnly',
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
		var url = 'shelfTag?action=shelfTagRequest&item=' + item + '&store=' + store;
		new Ajax.Updater('data', url, {evalScripts: true});
		
	}
	
	function setValues(text, li){
		var nums = li.id.split(":");
		$('number').value = nums[1];
		$('type').value = nums[0];
		$('criteria').value = li.innerHTML.unescapeHTML();

		$('cmdAdd').focus();
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
		
		//window.open('approvedDistributions?action=print&start=' + start + '&end=' + end + '&item=' + item + '&store=' + store, "Approved Distributions Report");
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
		
		//window.open('approvedDistributions?action=export&start=' + start + '&end=' + end + '&item=' + item + '&store=' + store, "Approved Distributions Report");
	}	
	
	function validateAndAdd(){
		if($('store').value == ''){
			alert("Please select a store");
			return false;
		}
		
		if($('criteria').value == ''){
			alert("Please select an item.");
			return false;
		}
		
		if($('number').value == ''){
			var params = "action=findItem&criteria=" + $('criteria').value;
			new Ajax.Request('search', {parameters: params, onSuccess: openSearchShadow});
		}else{
			addShelfTag();
		}
	}
	
	function addShelfTag(){
		$('data').innerHTML = "<center><br><img src='images/ajax-loader.gif'/></center>";
		var url = "shelfTag?action=requestShelfTag&item="+ $('number').value + "&type=" + $('type').value + "&store=" + $('store').value;
		new Ajax.Updater('data', url, {evalScripts: true});	
		$('number').value = "";
	}
	
	
	function openSearchShadow(transport){	
		var html = transport.responseText;
		
	    Shadowbox.open({
	        player:     "html",
	        title:      "Search Results",
	        height:     550,
	        width:      700,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });	
	}
	
	function makeSelection(itemNumber){
		$('number').value = itemNumber;
		$('criteria').value = itemNumber;
		addShelfTag();
		Shadowbox.close();
	}
		
	function removeRow(itemNumber){
		$('data').innerHTML = "<center><br><img src='images/ajax-loader.gif'/></center>";
		var url = "shelfTag?action=removeShelfTag&item="+ itemNumber;
		new Ajax.Updater('data', url, {evalScripts: true});
	}
	
	function submitTags(){
		$('data').innerHTML = "<center><br><img src='images/ajax-loader.gif'/></center>";
		var url = "shelfTag?action=submit";
		new Ajax.Updater('data', url, {evalScripts: true});		
	}
	
	function clearText(){
		$('criteria').value = "";
	}
</script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div style="font-size: large; color:#a04617; padding: 10px; float: left">Shelf Label Request</div>
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
						<select size="15" style="width: 180px; margin-left: 8px;" id="store">
							<option id="${chain.number}" value="${chain.number}">${chain.name}</option>
							<c:forEach var="store" items="${stores}">							
								<option id="${store.number}" value="${store.number}">${store.name}</option>
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
						<td style="border: 0px;">Item/Family:</td>
						<td style="border: 0px;" colspan="3">
							<input id="criteria" type="text" style="width: 400px" name="criteria"/>
							<div id="autocomplete" name="autocomplete" class="autocomplete"></div>
						</td>
						<td><input type="button" value="Add" onclick="validateAndAdd()" id="cmdAdd"/></td>
					</tr>		
					<input type="hidden" id="number" value=""/>
					<input type="hidden" id="type" value=""/>				
				</table>
			</div>
		</div>
		<br/>
		<div id="data">
				<table id="labelRequests" width="100%" class="sortable">
						<tr>
							<td>No items added.</td>
						</tr>
				</table>
		</div> 
	</div>
	<script>
		createAuto();

	</script>