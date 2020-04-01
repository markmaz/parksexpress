<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript">
     window.onload = function() {
       Calendar.setup({
         dateField      : 'startDate',
         triggerElement : 'startDate'
       });
       
       Calendar.setup({
         dateField      : 'endDate',
         triggerElement : 'endDate'
       });
     }
      	
	
	function addStore(){
		var element = document.getElementById("storeSelect");
	
		var selectedIndex = element.selectedIndex;
		var option = element.options[selectedIndex];
		var values = element.options[selectedIndex].value.split(":");
	
		$('storeValue').value = values[1];
		$('store').innerHTML= values[0];
	}
	
	
	function createReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var code = $('priceBookHeader').value;
		var time = $RF('time');
		var timestamp = Number(new Date());
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		$('data').innerHTML = "<center><br><img src='images/ajax-loader.gif'/></center>";
		var url = 'movement?action=data&start=' + start + '&end=' + end + '&code=' + code + '&store=' + store + '&time=' + time + "&timestamp=" + timestamp;
		new Ajax.Updater('data', url, {evalScripts: true});
		
	}
	
	function setValues(text, li){
		$('number').value = li.id;
		$('type').value = li.type;
	}
	
	function printReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var code = $('priceBookHeader').value;
		var time = $RF('time');
		var timestamp = Number(new Date());
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		window.open('movement?action=print&start=' + start + '&end=' + end + '&code=' + code + '&store=' + store + '&time=' + time  + "&timestamp=" + timestamp, "Movement_Report");
	}
	
	function exportReport(){
		var store = $('storeValue').value;
		var start = $('startDate').value;
		var end = $('endDate').value;
		var code = $('priceBookHeader').value;
		var time = $RF('time');
		var timestamp = Number(new Date());
		
		if(store == ''){
			alert("Please select a store.");
			return false;
		}
		
		window.open('movement?action=export&start=' + start + '&end=' + end + '&code=' + code + '&store=' + store + '&time=' + time  + "&timestamp=" + timestamp, "Movement_Report");
	}	
	
	function enableDates(){
		$('startDate').disabled="";
		$('endDate').disabled="";
		$('endDateLabel').style.color="#000";
		$('startDateLabel').style.color="#000";
	}
	
	function disableDates(){
		$('startDate').disabled="true";
		$('endDate').disabled="true";	
		$('startDate').value="";
		$('endDate').value="";
		$('endDateLabel').style.color="#C0C0C0";
		$('startDateLabel').style.color="#C0C0C0";		
	}
	
	function $RF(el, radioGroup) {
	    if($(el).type && $(el).type.toLowerCase() == 'radio') {
	        var radioGroup = $(el).name;
	        var el = $(el).form;
	    } else if ($(el).tagName.toLowerCase() != 'form') {
	        return false;
	    }
	 
	    var checked = $(el).getInputs('radio', radioGroup).find(
	    	        function(re) {return re.checked;}
	    );

	    return (checked) ? $F(checked) : null;
	}
</script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div style="font-size: large; color:#a04617; padding: 10px; float: left">Movement</div>
		<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">Print</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">Export</a></div>
	</div>	
	<div class="contentPanelLeft" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9; height: 328px">
		<div class="contentPanelHeader">
			<span><strong>Select a Store</strong></span>
		</div>
		<div id="StoreColumn">
			<div id="stores">
				<div>
					<span style="color:#a04617; font-size: small">
						<select size="15" style="width: 180px; margin-left: 8px;" onchange="addStore()" id="storeSelect">
							<c:forEach var="store" items="${stores}">							
								<option value="${store.webSafeName}:${store.number}" id="${store.number}">${store.name}</option>
							</c:forEach>
						</select>
					</span>
				</div>
			</div>
		</div>	
	</div>
	<div class="contentPanelMiddle" style="float: left; width: 680px;  margin: 8px;  background-color: #ffffff;">
		<div>
			<div id="searchCriteria" style="padding: 10px; background-color: #f9f9f9; border: 1px #e5e5e5 solid;">
				<fieldset>
					<legend style="font-size: small">Store</legend>
					<table>
						<tr>
							<td style="border: 0px;" colspan="4"><span id="store" style="font-weight:bold">Please select a store</span>
								
							</td>						
						</tr>
					</table>
				</fieldset>
				<div><form>
					<fieldset>
						<legend style="font-size: small">Time</legend>
						<table>
							<tr>
								<td style="border: 0px;" colspan="5">
									<input type="radio" name="time" id="time" checked="checked" onclick="disableDates()" value="MTD"/><span>Month to Date ()</span>
								</td>
							</tr>
							<tr>
								<td style="border: 0px;" colspan="5">
									<input type="radio" name="time" id="time" onclick="disableDates()" value="YTD"/><span>Year to Date ()</span>
								</td>
							</tr>					
							<tr>
								<td style="border: 0px;" colspan="5"><input name="time" type="radio" id="time" onclick="enableDates()"/>User defined:</td>
							</tr>	
							<tr>
								<td style="border: 0px;" width="10%">&nbsp;</td>
								<td style="border: 0px;"><span id="startDateLabel" style="color:#c0c0c0">Start Date:</span></td>
								<td style="border: 0px;"><input type="text" style="width: 150px; color: #888;" id="startDate" value="${startDate}" disabled="disabled"/></td>
								<td style="border: 0px;"><span id="endDateLabel" style="color:#c0c0c0">End Date:</span></td>
								<td style="border: 0px;" align="right"><input type="text" style="width: 150px;color: #888;" id="endDate" value="${endDate}" disabled="disabled"/></td>
							</tr>	
						</table>
					</fieldset>
				</div></form>
				<div>
					<fieldset>
						<legend style="font-size: small">Optional</legend>
						<table>
							<tr>
								<td style="border: 0px;">Price Book:</td>
								<td style="border: 0px;" colspan="3">
									<select class="drop" id="priceBookHeader">
										<option value="-1">Select a Price Book Header</option>
										<c:forEach var="pbHeader" items="${headers}">
											<option value="${pbHeader.headerCode}">${pbHeader.headerCode} - ${pbHeader.description}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</fieldset>
				</div><br/>
				<div>
					<table>
						<tr align="right">
							<input type="hidden" id="number"/>
							<input type="hidden" id="type"/>
							<input type="hidden" id="storeValue"/>
							<td style="border: 0px;" align="right"><input type="button" value="Search" onclick="createReport()"/></td>
						</tr>
					</table>	
				</div>	
			</div>
		</div>
		<br/>
	</div>
	<div style="float: clear"></div>
	<div id="data" style="border: 1px #e5e5e5 solid; margin: 8px; width: 900px;  margin: 8px;  background-color: #f9f9f9; float: left">

	</div>
	