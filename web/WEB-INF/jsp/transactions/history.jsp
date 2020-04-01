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
	<div style="font-size: large; color:#a04617; padding: 10px; float: left">Transaction History</div>
	<script type="text/javascript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/transactions/history.js"></script>
	<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">&nbsp;&nbsp;</a></div>
</div>

<div id="searchHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div>
		<div id="searchCriteria" style="padding: 10px; ">
			<table>
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
	getSevenDayHistory();
</script>