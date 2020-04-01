<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript" src="scripts/popover.js"></script>
<script type="text/javascript" src="scripts/srp.js"></script>
	<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9;">
		<div id="srp_title" style="font-size: large; color:#a04617; padding: 10px; float: left">SRP - ${defaultBook}</div>
		<script type="text/javascript" src="scripts/sorttable.js"></script>
		<div style="padding-left: 10px; width: 200px; position: relative">
			<span id="itemQueue" style="display:none"><a style="font-size: small; color: #a04617" href="#" id="itemsSelected" onclick="editListItems()">Items selected.</a></span>
		</div>
	</div>	
	<div class="contentPanelLeft" style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader" id="zones">
		
		</div>
		<div class="contentPanelHeader">
			<span><strong>Family</strong></span>
		</div>
		<div class="contentPanelHeader" id="familyTree">
			<select class="drop" id="priceBookFamilyTree"
				onchange="openFamilyPanel()" style="font-size: xx-small; width: 185px">
				<option value="-1">
					Select a Price Book Family
				</option>
				<c:forEach var="family" items="${families}">
					<option value="${family.familyCode}">
						${family.familyCode} - ${family.description}
					</option>
				</c:forEach>
			</select>
		</div>
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
	<div class="contentPanelMiddle" style="float: left; width: 680px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
		<div class="contentPanelHeader">
			<span><strong>Search</strong></span>
		</div>
		<div class="contentPanelHeader">
			<span style="font-size: smaller; color: black">You must use the arrow keys or the mouse wheel to scroll through the results.</span>
			<input type="text" style="width: 400px;" name="criteria" id="criteria" onfocus="clearText()"><input type="button" value="GO" onclick="javascript:cmdGoClicked();"> 
			<div id="autocomplete" name="autocomplete" class="autocomplete"></div>
		</div>
		<div id="SRP">
		
		</div> <!-- SRP -->
	</div>
  	<script>		
		createAuto();
		createZones();
		showHistory();
		bookDescription = "${defaultBook}";
  	</script> 