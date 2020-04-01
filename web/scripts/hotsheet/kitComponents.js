function createAuto() {
	var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'kit', {
		minChars : 2,
		parameters : 'action=search',
		afterUpdateElement: setValues
	});
}

function setValues(text, li){
	$('number').value = li.id;
	$('type').value = li.type;
}

function submitData(){
	$('reportData').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	new Ajax.Updater('reportData', 'kit?action=get&kit='  + $('criteria').value, {evalScripts: true});
}

function printReport(){
	window.open('kit?action=print&kit=' + $('criteria').value, "Kit_Components");
}

function exportReport(){
	window.open('kit?action=export&kit=' + $('criteria').value, "Kit_Components");
}