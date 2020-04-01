<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
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
    </script>
<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div style="font-size: large; color:#a04617; padding: 10px; float: left">New  Items</div>
	<script type="text/javascript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/hotsheet/newItems.js"></script>
	<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">Print</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">Export</a></div>
</div>

<div id="searchHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div>
		<span id="sign" style="font-size: medium; color:#a04617; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-weight: bold; cursor: pointer" onclick="showSearchCriteria('sign', 'searchCriteria')">(+)</span><span style="font-size: medium; color:#a04617; padding-left: 5px; padding-top: 10px; padding-bottom: 10px; font-weight: bold">Search</span><span style="font-size: small; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; ">(click to expand)</span><br>
		<div id="searchCriteria" style="padding: 10px; display: none;">
			<table>
				<tr>
					<td style="border: 0px;">Item/Family:</td>
					<td style="border: 0px;" colspan="3">
						<input id="criteria" type="text" style="width: 400px" name="criteria"/>
						<div id="autocomplete" class="autocomplete" name="autocomplete"></div>
					</td>
					
				</tr>
				<tr>
					<td style="border: 0px;">Start Date:</td>
					<td style="border: 0px;"><input type="text" style="width: 150px; color: #888;" id="startDate" value="${startDate}"/></td>
					<td style="border: 0px;">End Date:</td>
					<td style="border: 0px;" align="right"><input type="text" style="width: 150px;color: #888;" id="endDate" value="${endDate}"/></td>
				</tr>		
				<tr>
					<input type="hidden" id="number"/>
					<input type="hidden" id="type"/>
					<td style="border: 0px;" colspan="4" align="right"><input type="button" value="Search" onclick="submitData()"/></td>
				</tr>		
			</table>
		</div>
	</div>
</div>

<div id="reportData" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<br/>
	<center><img src='images/ajax-loader.gif'></center>
	<br/>
</div>
<script>
	getNewItemData();
	createAuto();	
</script>