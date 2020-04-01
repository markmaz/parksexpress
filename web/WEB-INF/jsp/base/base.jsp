<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">  
<%@ taglib uri="/tiles" prefix="tiles"%>
<%@ taglib uri="/core" prefix="c"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<title>Parks Express</title>
  <script type="text/javascript" src="scripts/prototype.js"></script>
  <script type="text/javascript" src="scripts/scriptaculous.js"></script>
  <script type="text/javascript" src="scripts/effects.js"></script>
  <script type="text/javascript" src="scripts/shadowbox/shadowbox.js"></script>
  <script type="text/javascript" src="scripts/calendarview.js"></script>
  <script type="text/javascript" src="scripts/sorttable.js"></script>
  <script type="text/javascript" src="scripts/tooltips.js"></script>
  <script type="text/javascript" src="scripts/quickmenu.js"></script>
  
  <!--<script type="text/javascript" src="scripts/jquery-1.5.1.min.js"></script>-->

	<link rel="stylesheet" type="text/css" href="scripts/shadowbox/shadowbox.css">
	<link rel="stylesheet" type="text/css" href="css/popover.css">
	<link rel="stylesheet" type="text/css" href="css/calendarview.css">
	<link rel="stylesheet" type="text/css" href="css/quickmenu.css">
	<link rel="stylesheet" type="text/css" href="css/parksexpress.css">
	<link rel="stylesheet" type="text/css" href="css/tabs/tabs.css">
	<link rel="stylesheet" type="text/css" href="css/tooltips.css">
<script>
	var timer;
	
	function set_Interval(){
		timer = setInterval("autoLogOut()", 1500000);
	}
	
	function resetInterval(){
		clearInterval(timer);
		timer = setInterval("autoLogOut()", 1500000);
	}
	
	function autoLogOut(){
		document.location.href = "logout";
	}
	
	Event.observe(window, 'load', function()
	{
	    try
	    {
	        Position.clone($('criteria'), $('autoComplete'),
	            { setHeight: false, offsetTop: $('criteria').offsetHeight});
	    }
	    catch(e){}
	});
</script>
<body onload="set_Interval()" onmousemove="resetInterval()" onclick="resetInterval()" onkeypress="resetInterval()">
<img src="images/logo/parks200.png">
<div style="margin-left: 800px; padding-bottom: 5px"><a href="logout">Log Out</a></div>
<div id="outer" style="width: 950px; float: left; background-color: #f9f9f9; border: 1px #ccc solid; padding-bottom: 15px; padding-top: 10px; padding-left: 15px; padding-right: 15px;">
	<div id="menu" style="padding-bottom: 10px;">
<ul id="qm0" class="qmmc">
		<li><a href="home">HOME</a></li>
		<li><span class="qmdivider qmdividery" ></span></li>
	<c:set var="admin" value="N"></c:set>
	<c:forEach var="permission" items="${authenticatedUser.permissions}">
		<c:if test="${permission.name == 'SRP'}">
			<li><a href="srp?action=showSRP">SRP</a></li>
			<li><span class="qmdivider qmdividery" ></span></li>
		</c:if>
		
		<c:if test="${permission.name == 'Reports'}">
				<li><a class="qmparent" href="javascript:void(0)">REPORTS</a>
					<ul style="width:150px;">
						<li><a href="movement?action=show">Movement</a></li>
						<li><a href="approvedDistributions?action=show">Approved Distributions</a></li>
						<li><a href="reverseLookup?action=show">Reverse Lookup</a></li>
						<li><span class="qmdivider qmdividerx" ></span></li>
						<li><a href="priceChange?action=showPriceChangeReport">Price Change Report</a></li>
						<li><a href="futurePriceChange?action=show">Future Price Change Report</a></li>
						<li><span class="qmdivider qmdividerx" ></span></li>
						<li><a href="hotSheet?action=itemAllowances">Item Allowances</a></li>
						<li><a href="hotSheet?action=newItems">New Items</a></li>
						<li><a href="hotSheet?action=discontinuedItems">Discontinued Items</a></li>
						<li><a href="hotSheet?action=suspendedItems">Suspended Items</a></li>
						<li><a href="kit?action=show">Kit Components</a></li>
					</ul>
				</li>
				<li><span class="qmdivider qmdividery" ></span></li>
		</c:if>
	<c:if test="${permission.name == 'Accounts Payable'}">
		<li><a class="qmparent" href="javascript:void(0);">ACCOUNTS PAYABLE</a>
			<ul style="width:150px;">
				<li><a href="invoice?action=show">AP Detail</a></li>
				<li><a href="invoiceReprint?action=show">Invoice Reprint</a></li>
			</ul>
		</li>
		<li><span class="qmdivider qmdividery" ></span></li>
	</c:if>
	
	<c:if test="${permission.name == 'Shelf Labels'}">
	<li><a href="shelfTag?action=show">SHELF LABELS</a></li>
	<li><span class="qmdivider qmdividery" ></span></li>
	</c:if>
	
	<c:if test="${permission.name == 'FTP'}">
	<li><a href="ftp://www.parksexpress.com" target="_blank">FTP</a></li>
	<li><span class="qmdivider qmdividery" ></span></li>
	</c:if>
	
	<c:if test="${permission.name == 'Admin'}">
		<li><a class="qmparent" href="javascript:void(0);">ADMIN</a>
		<li><span class="qmdivider qmdividery" ></span></li>

		<ul style="width:150px;">
		<li><a href="userController?action=search">User Management</a></li>
		<li><a href="jobController?action=getSummary">Jobs</a></li>
		</ul></li>
		<c:set var="admin" value="Y"></c:set>
	</c:if>
	</c:forEach>
	<c:choose>
		<c:when test="${admin == 'Y'}">
			<li><a href="history?action=showAdmin">HISTORY</a></li>
			<li><span class="qmdivider qmdividery" ></span></li>
		</c:when>
		<c:otherwise>
			<li><a href="history?action=show">HISTORY</a></li>
			<li><span class="qmdivider qmdividery" ></span></li>
		</c:otherwise>
	</c:choose>
		
	<li><a href="emailAddress?action=show">EMAIL MANAGEMENT</a></li>
	<li><span class="qmdivider qmdividery" ></span></li>
	<li class="qmclear">&nbsp;</li>
	</ul>
		
	</div>
	<div id="content" style="z-index: 0; float:left; width: 100%; border: 1px #ccc solid; background-color: white">
		<tiles:insert attribute="content"/>
	</div>
	<div style="clear: both"></div>
	<div id="footer" style="position:relative; padding-top: 15px; padding-bottom: 15px;">
		<span style="font-size: small; color: #7d86aa;">Copyright 2009 <a href="http://www.charlescparks.com">Charles C Parks Company</a></span>
	</div>
</div>
<script type="text/javascript">qm_create(0,false,0,250,false,false,false,false,false);</script>
</body>
</html>