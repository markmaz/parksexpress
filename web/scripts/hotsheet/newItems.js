function getNewItemData() {
	new Ajax.Updater('reportData', 'hotSheet?action=newItemData', {evalScripts: true});
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
	var num = li.id.split(":");
	$('number').value = num[1];
	$('type').value = num[0];
}

function submitData(){
	$('reportData').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	new Ajax.Updater('reportData', 'hotSheet?action=newItemData&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value + "&number=" + $('number').value + "&type=" + $('type').value, {evalScripts: true});
}

function printReport(){
	window.open('hotSheet?action=printNewItems&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value + "&number=" + $('number').value + "&type=" + $('type').value, "New_Items_Report");
}

function exportReport(){
	window.open('hotSheet?action=exportNewItems&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value + "&number=" + $('number').value + "&type=" + $('type').value, "New_Items_Report");
}