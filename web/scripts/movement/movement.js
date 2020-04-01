function showSearchCriteria(image, zone) {
	var element = $(zone);
	if (element.style.display == 'none') {
		$(image).innerHTML = "(-)";
		new Effect.BlindDown(element, {
			duration : .5
		});
	} else {
		$(image).innerHTML = "(+)";
		new Effect.BlindUp(element, arguments[1] || {});
	}
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

function submitData(tabName){
	$('data').innerHTML = "<center><br><br><br><br><br><br><br><br><br><img src='images/ajax-loader.gif'/></center>";
	$('data').style.height="";
	
	var store = tabName;
	var startDate = $('startDate').value;
	var endDate = $('endDate').value;
	
	if($('criteria').value == ''){
		$('number').value = "";
		$('type').value = "";
	}
	
	var code = $('number').value;
	var codeType = $('type').value;
	
	var url = 'priceChange?action=getPriceChangeReport&startDate=' + startDate + '&endDate=' 
					+ endDate + '&code=' + code + '&codeType=' + codeType + '&store=' + store;
	new Ajax.Updater('data', url, {evalScripts: true});
}

function submit(){
	var list = $('zones').childElements();
	var tabName = "";
	
	for(i = 0; i < list.length; i++){
		if($(list[i].id).hasClassName('selectedTab')){
			tabName = list[i].id;
		}
	}	
	
	submitData(tabName);
}

function clearFields(){
	$('startDate').value = "";
	$('endDate').value = "";
	$('number').value = "";
	$('type').value = "";
	$('criteria').value = "";
}

function printReport(){
	var tabName = getSelectedTab();
	window.open("priceChange?action=printPriceChangeReport&startDate=" + $('startDate').value + "&endDate=" + 
			$('endDate').value + "&code=" + $('number').value + "&codeType=" + $('type').value + "&store=" + tabName, "Price_Change_Report");
}

function exportReport(){
	var tabName = getSelectedTab();
	window.open("priceChange?action=exportPriceChangeReport&startDate=" + $('startDate').value + "&endDate=" + 
			$('endDate').value + "&code=" + $('number').value + "&codeType=" + $('type').value + "&store=" + tabName, "Price_Change_Report");
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