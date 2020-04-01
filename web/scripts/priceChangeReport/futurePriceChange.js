function printReport(){
	window.open("futurePriceChange?action=print", "Future_Price_Change_Report");
}

function exportReport(){
	window.open("futurePriceChange?action=export", "Future_Price_Change_Report");
}

function getData(){
	$('data').innerHTML = "<center><br><br><br><br><br><br><br><br><br><img src='images/ajax-loader.gif'/></center>";
	new Ajax.Updater('data', 'futurePriceChange?action=report', {evalScripts:true});
}