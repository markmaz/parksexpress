	//From Controller	
	var currentBook;
	var currentItemNumber;
	var currentType;
	var currentText;
	var popuptext;
	var bookDescription;
	var shadowBox;
	
	Shadowbox.init({
		skipSetup: true,
		players:["html"]
	});
	
	
	function showSuspended(familyCode){
		var element = $('suspendedItems');
		
		if(element.style.display == 'none'){
			$('discontinued').innerHTML="(-)";
			new Effect.BlindDown(element, {duration: .5});
			element.innerHTML = "<br><center><align='center'><img src='images/ajax-loader.gif'></center><br>";
			
			var params = "action=suspendedItems&familyCode=" + familyCode + "&book=" + currentBook;
			var ajax = new Ajax.Updater(element, 'srp', {parameters: params, evalScripts: true}); 
			
		}else{
			$('discontinued').innerHTML = "(+)";
			new Effect.BlindUp(element, arguments[1] || {});
		}
	}
	
	function getOffsetLeft (el) {
	  var ol = el.offsetLeft;
	  while ((el = el.offsetParent) != null)
	    ol += el.offsetLeft;
	  return ol;
	}
	
	function getOffsetTop (el) {
	  var ot = el.offsetTop;
	  while((el = el.offsetParent) != null)
	   ot += el.offsetTop;
	  return ot;
	}
	
	function updateOrderGuide(itemNumber, val){
		var element = $(val);
		var top = getOffsetTop(element);
		var left = getOffsetLeft(element);
		
		if(element.value == 'NOG'){
			element.value = 'OG';
			var params = "action=orderGuideAdd&itemNumber=" + itemNumber;
			new Ajax.Updater('result', 'srp', {parameters: params, onSuccess: function(){
				//showTooltip("", "Item added to order guide", element);
				showHistory("", "");
			}});	
		}else{
			element.value= 'NOG';
			var params = "action=orderGuideDelete&itemNumber=" + itemNumber;
			new Ajax.Updater('result', 'srp', {parameters: params, onSuccess: function(){
				//showTooltip("", "Item removed from order guide", element);
				showHistory("", "");
			}});		
		}
	}
	

	function mouseOut(el){
		hideTooltip();
	}
	
	function getInfo(itemNumber, el){	
		var x = tempX;
		var y = tempY;
		
		//showTip.delay(1, x, y, itemNumber, el);

		
	}
	
	function showTip(x, y, itemNumber, el){
		if(Math.abs(x - tempX) <= 10 && Math.abs(y - tempY) <= 10){
			var element = $(el);
			var params = "action=itemInfoJSON&srpBook=" + currentBook + "&itemNumber=" + itemNumber;
			new Ajax.Request('srp', {parameters: params, onSuccess: function(transport){
				var itemInfo = transport.responseText.evalJSON();
				var html = '<div>Item Number: ' + itemInfo.number + '<br>' +
					'Description: ' + itemInfo.description + '<br>' + 
					'Pack: ' + itemInfo.pack + '<br>' +
					'Size: ' + itemInfo.size + '<br>' +
					'Retail UPC: ' + itemInfo.retailUPCNumber + '<br>' + 
					'Carton UPC: ' + itemInfo.cartonUPCNumber;
				showTooltip(itemInfo.number, html, element);
			}});
		}
	}
	
	function openModal(item, description){
		var html = "<div style='width: 550px; height: 700px;'><center><img style='padding-top: 150px' src='images/ajax-loader.gif'></center></div>";
		popuptext = "Item Pricing - " + item + " - " + description;

		Shadowbox.open({
	        player:     "html",
	        title:      "Item Pricing - " + bookDescription,
	        height:     550,
	        width:      850,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });
		var params = "action=itemInfo&srpBook=" + currentBook + "&itemNumber=" + item;
		new Ajax.Request('srp', {parameters: params, onSuccess: openShadow, evalScripts: true});
	}
	
	function openFamilyForm(family, description){
		var html = "<div style='width: 850px; height: 550px;'><center><img style='padding-top: 150px' src='images/ajax-loader.gif'></center></div>";
		popuptext = "Family Pricing - " + family + " - " + description;
		
		Shadowbox.open({
	        player:     "html",
	        title:      "Family Pricing - " + bookDescription,
	        height:     550,
	        width:      850,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });
		var params = "action=familyForm&srpBook=" + currentBook + "&family=" + family;
		new Ajax.Request('srp', {parameters: params, onSuccess: openFamilyFormShadow, evalScripts: true});	
	}
	
	function openFamilyFormShadow(transport){
		var html = transport.responseText;
	    Shadowbox.open({
	        player:     "html",
	        title:      "Family Pricing - " + bookDescription,
	        height:     550,
	        width:      850,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });		
	}
	
	function changeFamilyException(zone, family){
		var srp = $(zone + '.srp.exception').value;
		var effDate = $(zone + '.calendar.exception').value;
		var isFixed = $(zone + '.familyIsFixed.exception').value;
		
		var params = "action=updateFamilyPricing&srpBook=" + zone + "&family=" + family + "&srp=" + srp + "&isFixed=" + isFixed+ "&effectiveDate=" + effDate;
		new Ajax.Request('srp', {parameters: params, onSuccess: openShadow, evalScripts: true});		
	}
	
	function removeFamilyException(zone, family){
		var params = "action=removeFamilyPricing&srpBook=" + zone + "&family=" + family;
		new Ajax.Request('srp', {parameters: params, onSuccess: openShadow, evalScripts: true});			
	}
	
	function addFamilyPricing(f){
		var params = "action=updateFamilyPricing&srpBook=" + currentBook + "&family=" + f + "&srp=" + $('familyPricing').value + "&isFixed=" + $('familyIsFixed').value + "&effectiveDate=" + $('familyEffectiveDate').value;
		new Ajax.Request('srp', {parameters: params, onSuccess: closeShadow, evalScripts: true});
	}
	
	function removeFamilyPricing(f){
		var params = "action=removeFamilyPricing&srpBook=" + currentBook + "&family=" + f;
		new Ajax.Request('srp', {parameters: params, onSuccess: closeShadow, evalScripts: true});	
	}
	
	function removeItemPricing(i){
		var params = "action=removeItemPricing&book=" + currentBook + "&item=" + i;
		new Ajax.Request('srp', {parameters: params, onSuccess: closeShadow});
	}
	
	//MTM - CLOSE
	function closeShadow(){
		Shadowbox.close();

		$('itemsSelected').innerHTML = " ";
		$('itemQueue').style.display = "none";
		
		var elements = $('familyItemList').getElementsByClassName('itemNumber');
		
		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				elements[i].checked = false;
			}
		}

		cmdGoClicked(true);
		showHistory("", "");
	}
	
	function closeShadowFromPost(){
		Shadowbox.close();

		$('itemsSelected').innerHTML = " ";
		$('itemQueue').style.display = "none";
		
		var elements = $('familyItemList').getElementsByClassName('itemNumber');
		
		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				elements[i].checked = false;
			}
		}

		var family = "family";
		var id = $("criteria").value;
		$('itemQueue').style.display = "none";
		$('SRP').innerHTML = "<center><div style='margin:25px; padding: 25px'><img src='images/ajax-loader.gif'></div></center>";			
		new Ajax.Updater('SRP', 'srp?action=getData&type=' + family + '&id=' + id + '&book=' + currentBook, {evalScripts: true});
		showHistory("", "");
	}
	
	
	function openShadow(transport){		
		var html = transport.responseText;
	    Shadowbox.open({
	        player:     "html",
	        title:      "Item Pricing - " + bookDescription,
	        height:     550,
	        width:      850,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });	
	    	    
	}
	
	function showStores(image, zone){
		var element = $(zone);
		if(element.style.display == 'none'){
			$(image).innerHTML="(-)";
			new Effect.BlindDown(element, {duration: .5});
			$(zone).innerHTML = "<align='center'><img src='images/ajax-loader.gif'></center>";
			
			var url = "srp";
			var params = "action=getStoresForZone&zone=" + zone;
			var ajax = new Ajax.Updater(zone, url, {parameters: params}); 
			
		}else{
			$(image).innerHTML = "(+)";
			new Effect.BlindUp(element, arguments[1] || {});
		}
	}
	
	function openException(zone, image){
		var element = $(zone);
		if(element.style.display == 'none'){
			$(image).innerHTML="(-)";
			new Effect.BlindDown(element, {duration: .5});
		}else{
			$(image).innerHTML = "(+)";
			new Effect.BlindUp(element, arguments[1] || {});
		}		
	}
	
	function changeBook(book, desc){
		currentBook = book;
		bookDescription = "SRP - [" + desc +"]";
		
		$('srp_title').innerHTML = bookDescription;
		if(currentItemNumber){
			goClick(currentItemNumber, currentType);
		}
	}
	
	window.onload = function(){
		Calendar.setup({
			parentElement : 'calendar',
			dateField : 'date',
			selectHandler : showHistory
		});
	}
	
	function showHistory(d, date){
		$('historyDetails').innerHTML = "<center><img src='images/ajax-loader.gif'></center>";
		var params = "action=getTransactionHistory&date=" + date;
		new Ajax.Updater('historyDetails', 'srp', {parameters: params});		
	}
	
	var checks;
	
	function checkSum(number, divName){
		var total = 0;
		var totalChecked = 0;
		var div = $(divName);
		var elements = div.getElementsByClassName('itemNumber');
		
		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				total++;		
				
				if(elements[i].checked){
					totalChecked++;
				}
			}
		}
		
		var percent = totalChecked / total * 100;
		
		if(totalChecked > 1){
			$('itemsSelected').innerHTML = totalChecked + " items selected.";
		}else {
			$('itemsSelected').innerHTML = totalChecked + " item selected.";
		}
		
		if(totalChecked == 0){
			$('itemQueue').style.display = "none";
		}else{
			$('itemQueue').style.display = "";
		}
		
		if(percent > 30){
			alert("You have selected to change more than 30% of a family's SRPs at the item level. It might make more sense to do this at the family level.");
		}
	}
	
	function editListItems(){
		var html = "<div style='width: 450px; height: 350px;'><center><img style='padding-top: 150px' src='images/ajax-loader.gif'></center></div>";
		Shadowbox.open({
	        player:     "html",
	        title:      "Item Pricing",
	        height:     550,
	        width:      850,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });
	    	
		var elements = $('familyItemList').getElementsByClassName('itemNumber');
		var params = "action=updateItems&book=" + currentBook + "&items=";
		
		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				if(elements[i].checked){
					params += elements[i].value + ",";
				}
			}
		}	
		
		currentItemNumber = $('criteria').value
		new Ajax.Request('srp', {parameters: params, onSuccess: openShadow, evalScripts: true});
	}
	
	function postForm(f, item){	
		var isFixed = $('itemIsFixed');
			
		var params = "action=updateItemPricing&itemNumber=" + item + "&effDate=" + $('effectiveDate').value + "&itemPricing=" + $('itemPricing').value + "&priceBook=" + currentBook + "&isFixed=" + isFixed.value;
		new Ajax.Updater('result', 'srp', {parameters: params, onSuccess: function(){
			closeShadow();			
		}, onFailure: function(){closeShadow();}});	
		
		closeShadow();
	}
		
	function postItems(){
		var isFixed = $('itemIsFixed');
		var elements = $('familyItemList').getElementsByClassName('itemNumber');
		var params = "action=updateItemPricing&effDate=" + $('effectiveDate').value + "&itemPricing=" + $('itemPricing').value + "&priceBook=" + currentBook + "&isFixed=" + isFixed.value;

		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				if(elements[i].checked){
					new Ajax.Request('srp', {parameters: params + "&itemNumber=" + elements[i].value});
				}
			}
		}
		
		closeShadowFromPost();
	}
	
	function removeItems(){
		var elements = $('familyItemList').getElementsByClassName('itemNumber');
		var params = "action=removeItemPricing&priceBook=" + currentBook;

		for(i = 0; i < elements.length; i++){
			if(elements[i].type=="checkbox"){
				if(elements[i].checked){
					new Ajax.Request('srp', {parameters: params + "&itemNumber=" + elements[i].value});
				}
			}
		}
		
		closeShadow();
	}
		
  		function createAuto(){
			var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'srp', {minChars: 2, parameters: 'action=searchAhead', afterUpdateElement:getSRPData});
		}
  		
  		function openFamilyPanel(){
  			//alert($('priceBookFamilyTree').value);
  			$("criteria").value = $('priceBookFamilyTree').value;
  			goClick($("criteria").value, "family");
  		}
		
		function cmdGoClicked(isClosed){
			if($("criteria").value == "" && currentItemNumber == ""){
				return;
			}
			
			if(isClosed){
				goClick(currentItemNumber, currentType);
				$("criteria").value = currentItemNumber;
				return;
			}
			
			if($("criteria").value == currentItemNumber){
				goClick(currentItemNumber, currentType);
				$("criteria").value = currentItemNumber;
			}else{
				currentText = $('criteria').value;
				currentItemNumber = "";
				currentType = "";
				var params = "action=findItem&criteria=" + $('criteria').value;
				new Ajax.Request('search', {parameters: params, onSuccess: openSearchShadow});
			}
			
		}
		
		function openSearchShadow(transport){		
			var html = transport.responseText;
		    Shadowbox.open({
		        player:     "html",
		        title:      "Search Results",
		        height:     550,
		        width:      850,
		        content:    html,
		        options: {enableKeys: false, modal: true}
		    });	
		    	    
		}
		
		function makeSelection(itemNumber){
			currentItemNumber = itemNumber;
			$("criteria").value = currentItemNumber;
			goClick(itemNumber, 'item');
			Shadowbox.close();
		}
		
		function clearText(){
			$('criteria').value = "";
		}
		
		function goClick(id, type){
			$('itemQueue').style.display = "none";
			$('SRP').innerHTML = "<center><div style='margin:25px; padding: 25px'><img src='images/ajax-loader.gif'></div></center>";			
			new Ajax.Updater('SRP', 'srp?action=getData&type=' + type + '&id=' + id + '&book=' + currentBook, {evalScripts: true});			
		}
		
		function getSRPData(text, li){
			var typeID = li.id.split(":");
			
			currentItemNumber = typeID[1];
			currentText = text;
			currentType = typeID[0];
			goClick(currentItemNumber, currentType);
		}

		function createZones(){
			$('zones').innerHTML = "<align='center'><img src='images/ajax-loader.gif'></center>";
			new Ajax.Updater('zones', 'srp?action=getZones');
		}	
		
		function removePricing(item, book, table){
			var tbl = $(table);
			var row = $(item + "_row");
			
			tbl.deleteRow(row.rowIndex);
			
			var params = "action=removeItemPricing&book=" + book + "&item=" + item;
			new Ajax.Request('srp', {parameters: params});
			cmdGoClicked();
		}
		
		function changeItemPricing(item, fixed, srp, effDate, book, isException){
			//Change to make the word 'Change' to updated.
			var params = "action=updateItemPricing&itemNumber=" + item + "&effDate=" + $(effDate).value + "&itemPricing=" + $(srp).value + "&priceBook=" + book + "&isFixed=" + $(fixed).value;
			new Ajax.Request('srp', {parameters: params});
			$("change_" + item + "_" + book).innerHTML = "<span style='font-size: x-small'>Updated</span>";
			
			if(!isException){
				cmdGoClicked();
			}
		}

		
		// Detect if the browser is IE or not.
		// If it is not IE, we assume that the browser is NS.
		var IE = document.all?true:false

		// If NS -- that is, !IE -- then set up for mouse capture
		if (!IE) document.captureEvents(Event.MOUSEMOVE)

		// Set-up to use getMouseXY function onMouseMove
		document.onmousemove = getMouseXY;

		// Temporary variables to hold mouse x-y pos.s
		var tempX = 0
		var tempY = 0

		// Main function to retrieve mouse x-y pos.s

		function getMouseXY(e) {
		  if (IE) { // grab the x-y pos.s if browser is IE
		    tempX = event.clientX + document.body.scrollLeft
		    tempY = event.clientY + document.body.scrollTop
		  } else {  // grab the x-y pos.s if browser is NS
		    tempX = e.pageX
		    tempY = e.pageY
		  }  
		  // catch possible negative values in NS4
		  if (tempX < 0){tempX = 0}
		  if (tempY < 0){tempY = 0}  
		}