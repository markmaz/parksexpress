<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
      };


	//Hack
	Event.observe(window, 'load', function()
	{
	    try
	    {
	        Position.clone($(textInputId), $(suggestionsHolderId),
	            { setHeight: false, offsetTop: $(textInputId).offsetHeight});
	    }
	    catch(e){}
	});

    </script>
    <script type="text/javascript" src="scripts/reverseLookup/reverseLookup.js"></script>
<div id="reportHeader"
	style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9;">
	<div
		style="font-size: large; color: #a04617; padding: 10px; float: left">
		Reverse Lookup
	</div>
	<div style="text-align: right; padding: 10px;">
		<a href="#" onclick="printSummary()" id="allStores" style="display:none">Print All Stores</a>&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="printReport()" id="summary">Print</a>&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="exportDetail()" id="exportDetails" style="display:none">Export By Store</a>&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="exportReport()" id="export">Export</a>
	</div>
</div>
<div class="contentPanelLeft"
	style="float: left; width: 200px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9; height: 328px">
	<div class="contentPanelHeader">
		<span><strong>Select a Store</strong>
		</span>
	</div>
	<div id="StoreColumn">
		<div id="stores">
			<div>
				<span style="color: #a04617; font-size: small"> <select id="storeSelect" 
						size="15" style="width: 180px; margin-left: 8px;" onChange="addStore()">
						<option value="${chain.name}:${chain.number}" id="${chain.number}">${chain.name}</option>
						<c:forEach var="store" items="${stores}">
							<option
								value="${store.name}:${store.number}"
								id="${store.number}">
								${store.name}
							</option>
						</c:forEach>
					</select> </span>
			</div>
		</div>
	</div>
</div>
<div class="contentPanelMiddle"
	style="float: left; width: 680px; margin: 8px; background-color: #ffffff;">
	<div>
		<div id="searchCriteria"
			style="padding: 10px; background-color: #f9f9f9; border: 1px #e5e5e5 solid;">
			<fieldset>
				<legend style="font-size: small">
					Store (Required)
				</legend>
				<table>
					<tr>
						<td style="border: 0px;" colspan="4">
							<span id="store" style="font-weight: bold">Please select a
								store</span>

						</td>
					</tr>
				</table>
			</fieldset>
			<div>
				<br />
				<form>
					<fieldset>
						<legend style="font-size: small">
							Timeframe (Required)
						</legend>
						<table>
							<tr>
								<td style="border: 0px;">
									Start Date:
								</td>
								<td style="border: 0px;">
									<input type="text" style="width: 150px; color: #888;"
										id="startDate" value="${startDate}" />
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td style="border: 0px;">
									End Date:
								</td>
								<td style="border: 0px;" align="right">
									<input type="text" style="width: 150px; color: #888;"
										id="endDate" value="${endDate}" />
								</td>
							</tr>
						</table>
					</fieldset>
			</div>
			</form>
			<div>
				<fieldset>
					<legend style="font-size: small" id="optionalItems">
						Optional
					</legend>
					<div>
						<span id="itemSign"
							style="font-size: small; color: #a04617; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; cursor: pointer"
							onclick="showSearchCriteria('itemSign', 'itemInfo')">(+)</span><span
							style="font-size: small; color: #a04617; padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">Item
							Categories</span><span
							style="font-size: small; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;">(click
							to expand)</span>
						<br>
						<div id="itemInfo" style="padding: 10px; display: none">
							<table>
								<tr>
									<td style="border: 0px;">
										<select class="drop" id="priceBookHeader"
											onchange="turnOffOthers('priceBookHeader')">
											<option value="-1">
												Select a Price Book Header
											</option>
											<c:forEach var="pbHeader" items="${headers}">
												<option value="${pbHeader.headerCode}">
													${pbHeader.headerCode} - ${pbHeader.description}
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="border: 0px;">
										<select class="drop" id="priceBookClass"
											onchange="turnOffOthers('priceBookClass')">
											<option value="-1">
												Select a Price Book Class
											</option>
											<c:forEach var="clazz" items="${classes}">
												<option value="${clazz.classCode}">
													${clazz.classCode} - ${clazz.description}
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="border: 0px;">
										<select class="drop" id="priceBookFamily"
											onchange="turnOffOthers('priceBookFamily')">
											<option value="-1">
												Select a Price Book Family
											</option>
											<c:forEach var="family" items="${families}">
												<option value="${family.familyCode}">
													${family.familyCode} - ${family.description}
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
							<br />
							<table>
								<tr>
									<td style="border: 0px;">
										Item [number/upc]:
									</td>
									<td>
										<input class="drop" id="criteria" type="text"
											style="width: 300px" name="criteria"
											onchange="turnOffOthers('criteria')" />
										<div id="autocomplete" class="autocomplete"
											name="autocomplete"></div>
										<input type="button" value="Add" onclick="validateAndAdd()" id="cmdAdd"/>	
										<input type="hidden" id="number" value="" />
										<input type="hidden" id="type" value="" />
										<input type="hidden" id="desc" value="" />
									</td>
								</tr>
							</table>
							<div id="items">
								
							</div>
						</div>
					</div>
					<br/>
					<div>
						<span id="vendorSign"
							style="font-size: small; color: #a04617; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; cursor: pointer"
							onclick="showSearchCriteria('vendorSign', 'vendorInfo')">(+)</span><span
							style="font-size: small; color: #a04617; padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">Vendor
							Information</span><span
							style="font-size: small; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;">(click
							to expand)</span>
						<br>
						<div id="vendorInfo" style="padding: 10px; display: none">
							<table>
								<tr>
									<td>
										<input class="drop" id="vendor" type="text"
											style="width: 300px" name="vendor"
											onchange="turnOffOthers('vendor')" />
										<div id="autocomplete2" class="autocomplete2"
											name="autocomplete2"></div>
									</td>
								</tr>
							</table>
						</div>
					</div>
<br/>
					<div>
						<span id="brandSign"
							style="font-size: small; color: #a04617; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; cursor: pointer"
							onclick="showSearchCriteria('brandSign', 'brandInfo')">(+)</span><span
							style="font-size: small; color: #a04617; padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">Brand
							Information</span><span
							style="font-size: small; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;">(click
							to expand)</span>
						<br>
						<div id="brandInfo" style="padding: 10px; display: none">
							<table>
								<tr>
									<td>
										<select class="drop" id="brand"
											onchange="turnOffOthers('brand')">
											<option value="-1">
												Select a Brand
											</option>
											<c:forEach var="brand" items="${brands}">
												<option value="${brand}">
													${brand}
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
			<br />
			<div>
				<table>
					<tr align="right">
						<input type="hidden" id="number" />
						<input type="hidden" id="type" />
						<input type="hidden" id="storeValue" />
						<td style="border: 0px;" align="right">
							<input type="button" value="Search" onclick="submitData()" />
							<input type="button" value="Clear" onclick="resetData()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<br />
</div>
<div style="float: clear"></div>
<div id="data"
	style="border: 1px #e5e5e5 solid; margin: 8px; width: 900px; margin: 8px; background-color: #f9f9f9; float: left">

</div>



<script>
	createAuto();	
</script>