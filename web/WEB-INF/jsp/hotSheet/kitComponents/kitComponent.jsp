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
	<div style="font-size: large; color:#a04617; padding: 10px; float: left">Kit Components</div>
	<script type="text/javascript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/hotsheet/kitComponents.js"></script>
	<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">Print</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">Export</a></div>
</div>

<div id="searchHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div>
		<div id="searchCriteria" style="padding: 10px; ">
			<table>
				<tr>
					<td style="border: 0px;">Kit Number:</td>
					<td style="border: 0px;" colspan="3">
						<input id="criteria" type="text" style="width: 400px" name="criteria"/>
						<div id="autocomplete" class="autocomplete" name="autocomplete"></div>
					</td>
					
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
	<table>
		<thead>
			<tr>
				<th width="10%">Kit Number</th>
				<th width="20%">Kit Description</th>
				<th width="10%">Component Number</th>
				<th width="15%">Pack</th>
				<th width="15%">Size</th>
				<th width="10%">Quantity</th>
				<th width="20%">Description</th>
				<th width="10%">Price</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="7" align="center">No data</td>
			</tr>
		</tbody>
	</table>
</div>
<script>
	createAuto();	
</script>