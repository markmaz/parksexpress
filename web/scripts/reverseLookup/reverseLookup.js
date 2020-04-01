var sortIndex = 0;

Shadowbox.init({
	skipSetup: true,
	players:["html"]
});

function turnOffOthers(name){
	var elements = document.getElementsByClassName('drop');
	for(i = 0; i < elements.length; i++){
		if(elements[i].id != name){
			if(elements[i].options){
				elements[i].options[0].selected = true;
			}else{
				elements[i].value="";
			}
		}
	}
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
		parameters : 'action=searchAheadItemsOnly',
		afterUpdateElement: setValues
	});
	
	var auto2 = new Ajax.Autocompleter('vendor', 'autocomplete2', 'vendor', {
		minChars : 2,
		parameters: 'action=search'
	});
}

function setValues(text, li){
	$('number').value = li.id;
	$('type').value = li.type;
}

function submitData(){
	if($('startDate').value == "" || $('endDate').value == ""){
		alert("You must enter a start and end date.");
		return false;
	}
	
	if($('storeValue').value == ""){
		alert("You must choose a store.");
		return false;
	}
	
	itemList = getItemNumberList();
	
	if(!isNumeric($('storeValue').value) && $('vendor').value == "-1" && itemList.length == 0
		&& $('priceBookHeader').value == "-1" && $('priceBookClass').value == "-1" && $('priceBookFamily').value == "-1"
		&& $('brand').value == "-1"){
		
		alert("When selecting the chain, you must select something from the required section.");
		return false;
	}
	
	$('data').innerHTML = "<br/><center><img src='images/ajax-loader.gif'></center><br/>";
	new Ajax.Updater('data', 'reverseLookup?action=lookUp&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value 
			+ "&item=" + itemList + "&header=" + $('priceBookHeader').value + "&class=" + $('priceBookClass').value
			+ "&vendor=" + $('vendor').value + "&family=" + $('priceBookFamily').value + "&brand=" + $('brand').value + "&store=" + $('storeValue').value + "&sort=" + sortIndex, {evalScripts: true});
}

function getItemNumberList(){
	var elements = $("items").childElements();
	var itemList = "";
	
	for(i = 0; i < elements.length; i++){
		if(elements[i].id){
			var id = elements[i].id;
			var ids = id.split("_");
			
			if(itemList == ""){
				itemList = ids[1];
			}else{
				itemList = itemList + "," + ids[1];
			}
		}
	}
	
	return itemList;
}

function isNumeric(sText){
	var validChars = "0123456789 ";
	var isNumber=true;
	var character;
	
	for (i = 0; i < sText.length && isNumber == true; i++){ 
		character = sText.charAt(i); 
		if (validChars.indexOf(character) == -1) {
			isNumber = false;
		}
	}
	
	return isNumber;   
}

function printReport(){
	if($('startDate').value == "" || $('endDate').value == ""){
		alert("You must enter a start and end date.");
		return false;
	}
	
	if($('storeValue').value == ""){
		alert("You must choose a store.");
		return false;
	}
	
	itemList = getItemNumberList();
	
	if(!isNumeric($('storeValue').value) && $('vendor').value == "-1" && itemList.length == 0 
		&& $('priceBookHeader').value == "-1" && $('priceBookClass').value == "-1" && $('priceBookFamily').value == "-1"
		&& $('brand').value == "-1"){
		
		alert("You must select something from the required section.");
		return false;
	}
	
	window.open('reverseLookup?action=print&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value 
			+ "&item=" + itemList + "&header=" + $('priceBookHeader').value + "&class=" + $('priceBookClass').value
			+ "&vendor=" + $('vendor').value + "&family=" + $('priceBookFamily').value + "&brand=" + $('brand').value + "&store=" + $('storeValue').value + "&sort=" + sortIndex, "Reverse_Lookup_Report");
}

function printSummary(){
	if($('startDate').value == "" || $('endDate').value == ""){
		alert("You must enter a start and end date.");
		return false;
	}
	
	if($('storeValue').value == ""){
		alert("You must choose a store.");
		return false;
	}
	
	itemList = getItemNumberList();
	
	if(!isNumeric($('storeValue').value) && $('vendor').value == "-1" && itemList.length == 0 
		&& $('priceBookHeader').value == "-1" && $('priceBookClass').value == "-1" && $('priceBookFamily').value == "-1"
		&& $('brand').value == "-1"){
		
		alert("You must select something from the required section.");
		return false;
	}
	
	window.open('reverseLookup?action=printSummary&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value 
			+ "&item=" + itemList + "&header=" + $('priceBookHeader').value + "&class=" + $('priceBookClass').value
			+ "&vendor=" + $('vendor').value + "&family=" + $('priceBookFamily').value + "&brand=" + $('brand').value + "&store=" + $('storeValue').value + "&sort=" + sortIndex, "Reverse_Lookup_Report");	
}

function exportReport(){
	if($('startDate').value == "" || $('endDate').value == ""){
		alert("You must enter a start and end date.");
		return false;
	}
	
	if($('storeValue').value == ""){
		alert("You must choose a store.");
		return false;
	}
	
	itemList = getItemNumberList();
	
	if(!isNumeric($('storeValue').value) && $('vendor').value == "-1" && itemList.length == 0 
		&& $('priceBookHeader').value == "-1" && $('priceBookClass').value == "-1" && $('priceBookFamily').value == "-1"
		&& $('brand').value == "-1"){
		
		alert("You must select something from the optional section.");
		return false;
	}
	
	window.open('reverseLookup?action=export&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value 
			+ "&item=" + itemList + "&header=" + $('priceBookHeader').value + "&class=" + $('priceBookClass').value
			+ "&vendor=" + $('vendor').value + "&family=" + $('priceBookFamily').value + "&brand=" + $('brand').value + "&store=" + $('storeValue').value + "&sort=" + sortIndex, "Reverse_Lookup_Report");
}

function exportDetail(){
	if($('startDate').value == "" || $('endDate').value == ""){
		alert("You must enter a start and end date.");
		return false;
	}
	
	if($('storeValue').value == ""){
		alert("You must choose a store.");
		return false;
	}
	
	itemList = getItemNumberList();
	
	if(!isNumeric($('storeValue').value) && $('vendor').value == "-1" && itemList.length == 0 
		&& $('priceBookHeader').value == "-1" && $('priceBookClass').value == "-1" && $('priceBookFamily').value == "-1"
		&& $('brand').value == "-1"){
		
		alert("You must select something from the optional section.");
		return false;
	}
	
	window.open('reverseLookup?action=exportDetail&startDate=' + $('startDate').value + "&endDate=" + $('endDate').value 
			+ "&item=" + itemList + "&header=" + $('priceBookHeader').value + "&class=" + $('priceBookClass').value
			+ "&vendor=" + $('vendor').value + "&family=" + $('priceBookFamily').value + "&brand=" + $('brand').value + "&store=" + $('storeValue').value + "&sort=" + sortIndex, "Reverse_Lookup_Report");
}

function addStore(){
	var element = document.getElementById("storeSelect");
	
	var selectedIndex = element.selectedIndex;
	var option = element.options[selectedIndex];
	var values = element.options[selectedIndex].value.split(":");

	if(isNumeric(values[1])){
		$('allStores').style.display="none";
		$('exportDetails').style.display="none";
		$('summary').innerHTML="Print";
		$('optionalItems').innerHTML = "Optional";
		$('export').innerHTML = "Export";
	}else{
		$('allStores').style.display="";
		$('exportDetails').style.display="";
		$('summary').innerHTML="Print Summary";
		$('optionalItems').innerHTML = "Required";	
		$('export').innerHTML = "Export Summary";
	}
	
	$('storeValue').value = values[1];
	$('store').innerHTML= values[0];
	
	if($('startDate').value != '' || $('endDate').value != ''){
		submitData();
	}
}

function resetData(){
	$('storeValue').value = "-1"; 
	$('vendor').value = "-1";
	$('priceBookHeader').value = "-1"; 
	$('priceBookClass').value = "-1";
	$('priceBookFamily').value = "-1";
	$('brand').value = "-1";
	$('store').innerHTML = "";
	$('startDate').value = "";
	$('endDate').value = "";
	$('storeSelect').value = "";
	var elements = $("items").childElements();
	var itemList = "";
	
	for(i = 0; i < elements.length; i++){
		elements[i].remove();
	}
	//location.reload(true);
}

function updateSort(sort){
	sortIndex = sort;
}

function validateAndAdd(){
	if($("criteria").value == ""){
		alert("You must enter a description, item number or an UPC code.");
		return;
	}
	
	if($('number').value == ''){
		var params = "action=findItem&criteria=" + $('criteria').value;
		new Ajax.Request('search', {parameters: params, onSuccess: openSearchShadow});
	}else{
		var num = $('number').value.split(":");
		addItem(num[1]);
	}
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

function makeSelection(itemNumber, description){
	addItem(itemNumber, description);
	Shadowbox.close();
}

function addItem(itemNumber, description){
	if(!description){
		var str = $('criteria').value.unescapeHTML();
		var its = str.split(" ");

		var items = its[2];
		for(i = 3; i < its.length; i++){
			items = items + " " + its[i];
		}

		description = items;
	}
	
	var vals = $("items").innerHTML + "<span class='listItem' id='item_" + itemNumber + "'>" + itemNumber + " - " + description + " " + "<a href='#' onclick='removeItem(" + itemNumber + ")'>x</a></span><br>";
	$("items").innerHTML = vals;
	$("criteria").value = "";
	$("number").value = "";
	$("desc").value = "";
}

function removeItem(itemNumber){
	$('item_' + itemNumber).remove();
}