<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div style="font-size: large; color:#a04617; padding: 10px; float: left">Suspended Items</div>
	<script type="text/javascript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/hotsheet/suspendedItems.js"></script>
	<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">Print</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">Export</a></div>
</div>

<div id="reportData" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<br/>
	<center><img src='images/ajax-loader.gif'></center>
	<br/>
</div>
<script>
	getSuspendedItemData();
	createAuto();	
</script>