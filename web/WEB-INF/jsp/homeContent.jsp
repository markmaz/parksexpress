<%@ taglib uri="/core" prefix="c"%>
<script>
	function getReports() {
		var url = 'priceChange?action=getPriceChangeReport&startDate=${startDate}&endDate=${endDate}&code=&codeType=&store=${zone}';
		new Ajax.Updater('priceChange', url, {
			evalScripts : true
		});
		new Ajax.Updater('history', 'history?action=getSevenDayHistory', {
			evalScripts : true
		});
	}
</script>
<div
	style="float: left; width: 900px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9;">
	<div style="font-size: small">
		<span
			style="color: #a04617; font-weight: bold; margin: 3px; padding: 5px">News</span>
		<br />
		<div style="background-color: #ffffff; padding: 10px">
			<p>
				Welcome to
				<span style="color: red">Parks Express</span>, the Charles C. Parks
				Company's (CCP) interactive application. This website, available
				24/7, conveniently provides you with valuable information to help
				you better manage your business.
			</p>

			<ul style="list-style: circle; font-size: small">
				<li
					style="display: list-item; font-size: small; padding-bottom: 10px">
					<span><strong>Manage Your Price Book:</strong>
					</span> Add items from the CCP master order book, fine-tune your retail
					prices to attain the gross profit percentages you want, or delete
					from your price book those items that no longer fit your
					merchandising needs.
				</li>
				<li
					style="display: list-item; font-size: small; padding-bottom: 10px">
					<span><strong>Movement Reports:</strong>
					</span> Create a report for an entire store, a category, or even a single
					item. Need a movement report by vendor? It's right at your
					fingertips. And for the frame you specify: a single day, a month,
					or even a year.
				</li>
				<li
					style="display: list-item; font-size: small; padding-bottom: 10px">
					<span><strong>Authorized Distributions:</strong>
					</span> Preview promotional displays scheduled for delivery. You can also
					see what specific items are in a particular display to help better
					manage your inventory.
				</li>
				<li
					style="display: list-item; font-size: small; padding-bottom: 10px">
					<span><strong>Price Change Report:</strong>
					</span> Allows you to rapidly adjust your retails so you keep pace with
					changes in the market and maintain your desired profit margin.
				</li>
				<li
					style="display: list-item; font-size: small; padding-bottom: 10px">
					<span><strong>Account Information:</strong>
					</span> Quickly see your CCP balance or review and print an invoice, if
					necessary.
				</li>
			</ul>
			<p>
				Please know this application uses the Internet as its communication
				vehicle. As a result, performance and response time may vary
				depending upon traffic through either the Internet or your service
				provider. If you consistently experience this type of problem, you
				may want to contact your service provider to determine if it is a
				service provider issue.
			</p>
			<p>
				Additionally, this interactive application has a significant number
				of quality controls built into it. As a result, when you are
				changing retails, running movement reports, or pushing the apply
				button, the response time may seem a bit slower than other websites
				you use. This is due to numerous "behind the scenes" computations
				and jobs occurring on our website.
			</p>
			<p>
				We offer assistance in using this application. Contact your CCP
				sales representative or call our corporate office 1-800-873-2406, and
				use any of the following extensions:
			<ul style="list-style: circle; font-size: small">
				<li style="display: list-item; font-size: small">
					6112 Gary Smith
				</li>
				<li style="display: list-item; font-size: small">
					6129 David Lowery
				</li>
				<li style="display: list-item; font-size: small">
					6118 Tom Cripps
				</li>
				<li style="display: list-item; font-size: small">
					6106 Gary Pickett
				</li>
			</ul>
			</p>
		</div>
	</div>
</div>
<br />
<br />
<c:if test="${authenticatedUser.admin == 'N'}">
	<div
		style="float: left; width: 900px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9;">
		<div style="">
			<span
				style="color: #a04617; font-weight: bold; margin: 3px; padding: 5px">Recent
				Transactions</span>
			<br />
			<div style="background-color: #ffffff; padding: 10px" id="history">
				<center>
					<img src='images/ajax-loader.gif' />
				</center>
			</div>
		</div>
	</div>
	<br />
	<br />
	<div
		style="float: left; width: 900px; border: 1px #e5e5e5 solid; margin: 8px; background-color: #f9f9f9;">
		<div style="">
			<span
				style="color: #a04617; font-weight: bold; margin: 3px; padding: 5px">Price
				Changes</span>
			<br />
			<div style="background-color: #ffffff; padding: 10px"
				id="priceChange">
				<center>
					<img src='images/ajax-loader.gif' />
				</center>
			</div>
		</div>
	</div>
</c:if>

<c:if test="${authenticatedUser.admin == 'N'}">
	<script>
	getReports();
</script>
</c:if>