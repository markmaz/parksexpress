<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript">
	var currentStore = "";
	
	function highlight(el){
		document.getElementById(el).style.background = '#C0C0C0';
	}
	
	function unhighlight(el){
		document.getElementById(el).style.background = '#FFF';
	}	
	
	function addStore(){
		var element = document.getElementById("storeSelect");
	
		var selectedIndex = element.selectedIndex;
		var option = element.options[selectedIndex];
		var values = element.options[selectedIndex].value.split(":");
		$('storeValue').value = values[1];
		$('store').value= values[0];
	}
	
	function createAuto() {
		var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'srp', {
			minChars : 2,
			parameters : 'action=searchAhead',
			afterUpdateElement: setValues
		});
	}	
	
	
	function setValues(text, li){
		$('number').value = li.id;
		$('type').value = li.type;
	}
		
	
	function selectTab(tabName){
	var list = $('zones').childElements();
	
	for(i = 0; i < list.length; i++){
		if($(list[i].id).hasClassName('selectedTab')){
			$(list[i].id).setAttribute('class', 'normalTab');
		}
	}
	
	$(tabName).setAttribute('class', 'selectedTab');
	submitData(tabName);
}

function getSelectedTab(){
	var list = $('zones').childElements();
	var tabName = "";
	
	for(i = 0; i < list.length; i++){
		if($(list[i].id).hasClassName('selectedTab')){
			tabName = list[i].id;
		}
	}
	
	return tabName;
}

function rollOverTab(tab){
	if($(tab).hasClassName('normalTab')){
		$(tab).setAttribute('class', 'rollOver');
	}
	
	if($(tab).hasClassName('rollOver')){
		$(tab).setAttribute('class', 'normalTab');
	}	
}

function findInvoice(){
	var element = document.getElementById("storeSelect");
	
	var selectedIndex = element.selectedIndex;
	var option = element.options[selectedIndex];
	
	$('data').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	currentStore = option.value;
	new Ajax.Updater('data', 'invoice?action=find&code=' + option.value, {evalScripts: true});	
	
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
</script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div style="font-size: large; color:#a04617; padding: 10px; float: left">AP Detail</div>
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
						<select size="15" style="width: 180px; margin-left: 8px;" id="storeSelect" onchange="findInvoice()">
							<c:forEach var="store" items="${stores}">							
								<option value= "${store.number}" id="${store.number}">${store.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
			</div>
		</div>	
	</div>
	<div class="contentPanelMiddle" style="float: left; width: 683px;  margin: 8px;  background-color: #ffffff;">
		<div>
			<div id="data">
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
					</table>
			</div>
			</div> 
		</div>
	</div>