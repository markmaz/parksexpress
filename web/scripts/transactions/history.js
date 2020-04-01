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
	new Ajax.Updater('reportData', 'history?action=history&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value, {evalScripts: true});	
}

function getSevenDayHistory(){
	new Ajax.Updater('reportData', 'history?action=getSevenDayHistory', {evalScripts:true});
}

function printReport(){
	window.open('history?action=printHistory&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value, "History_Report");
}
