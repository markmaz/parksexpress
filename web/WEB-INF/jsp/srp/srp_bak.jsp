<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript" src="scripts/popover.js"></script>
<script type="text/javascript" src="scripts/srp.js"></script>
	<div id="result" style="opacity: 0.0; background-color: #C0C0C0; position:absolute"></div>

	<div class="contentPanelLeft" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader">
			<span><strong>History</strong></span>
		</div>
		<div class="contentPanelHeaderHistory" name="HistoryElements">
			<div name="calendar" id="calendar"></div>
			<div name="date" id="date" style="display:none"></div>
			<div name="history" id="history">
				<div style="padding-top: 15px; "><span style="color:#a04617; font-size: small">Transactions:</span></div>
			</div>
			<div id="historyDetails" style="text-align: left; padding-top: 10px;"></div>
		</div>	
	</div>
	<div class="contentPanelMiddle" style="float: left; width: 460px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader">
			<span><strong>Search</strong></span>
		</div>
		<div class="contentPanelHeader">
			<input type="text" style="width: 300px;" name="criteria" id="criteria"><input type="button" value="GO" onclick="javascript:cmdGoClicked();"> 
			<div id="autocomplete" name="autocomplete" class="autocomplete"></div>
		</div>
		<div id="SRP">
		
		</div> <!-- SRP -->
	</div>
	<div class="contentPanelRight" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader" id="zones">
		
		</div>
	</div>
  	<script>		
		createAuto();
		createZones();
		showHistory();
  	</script> 	