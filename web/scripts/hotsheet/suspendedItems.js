function getSuspendedItemData() {
	new Ajax.Updater('reportData', 'hotSheet?action=suspendedItemData', {evalScripts: true});
}

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

function submitData(){
	$('reportData').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	new Ajax.Updater('reportData', 'hotSheet?action=suspendedItemData', {evalScripts: true});
}

function printReport(){
	window.open('hotSheet?action=printSuspendedItems', "Suspended_Items_Report");
}

function exportReport(){
	window.open('hotSheet?action=exportSuspendedItems', "Suspended_Items_Report");
}