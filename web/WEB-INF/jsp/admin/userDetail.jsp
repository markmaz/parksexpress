<%@ taglib uri="/core" prefix="c"%>

<script type="text/javascript">
		var itemID;
		var itemType;
		
		function save(){
			$('userInfo').action="userController?action=save";
			$('userInfo').submit();
		}
		
		function deleteUser(){
			$('userInfo').action="userController?action=delete";
			$('userInfo').submit();		
		}
		
		function addItem(){
			var url = "userController?action=addStore&type=" + itemType + "&storeID=" + itemID + 
					  "&username=" + $('username').value + "&password=" + $('password').value +
					  "&firstName=" + $('firstName').value + "&lastName=" + $('lastName').value +
					  "&emailAddress=" + $('emailAddress').value + "&id=" + $('id').value;
					  
			if($('id').value == ""){
				document.location.href = url;
			}else{
				new Ajax.Updater("stores", url);
				
				$('criteria').value = "";
				$('criteria').focus();
			}
		}
		
		function removeStore(storeNumber){
			var url = "userController?action=removeStore&storeID=" + storeNumber + "&userID=${user.id}";
			new Ajax.Updater("stores", url);
		}
		
		function removeAll(){
			var yesNo = confirm("Are you sure you want to remove all the stores from this user?");
			
			if(yesNo){
				var url = "userController?action=removeAllStores&id=${user.id}";
				new Ajax.Updater("stores", url);
			}
		}
		
		function updatePermission(permission){
			if($('id').value == ''){
				alert("You must save the user first before you can update the permissions.");
				return false;
			}
			
			var url = "userController?";
			
			if($(permission).checked){
				url = url + "action=addPermission&name=" + escape(permission) + "&userID=" + $('id').value;
			}else{
				url = url + "action=removePermission&name=" + escape(permission) + "&userID=" + $('id').value;
			}
			
			new Ajax.Request(url);
		}
	</script>
<div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>User Detail</strong>
		</span>
	</div>
	<div class="contentPanelHeader">
		<form name="userInfo" id="userInfo" method="post">
			<table>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" id="username"
						value="${user.username}"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" id="password"
						value="${user.password}">
					</td>
				</tr>
				<tr>
					<td>
					First Name:</td>
					<td>
					<input type="text" name="firstName" id="firstName"
						value="${user.firstName}">
					</td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><input type="text" name="lastName" id="lastName"
					value="${user.lastName}"></td>
				</tr>
				<tr>
					<td>Email Address:</td>
					<td><input type="text" name="emailAddress" id="emailAddress"
					value="${user.emailAddress}"></td>
				</tr>
				<input type="hidden" name="id" id="id" value="${user.id}">
			</table>
		</form>
		<input type="button" value="Save" onclick="save()">
		<input type="button" value="Delete" onclick="deleteUser()">
	</div>
</div>

<div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>Permissions</strong></span>
	</div>
	<div class="contentPanelHeader">
		<table>
			<tr>
							<c:choose>
								<c:when test="${user.srpPermissible}">
									<td><input type="checkbox" checked="checked" id="SRP" onchange="updatePermission('SRP')"/>SRP</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="SRP" onchange="updatePermission('SRP')"/>SRP</td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${user.reportsPermissible}">
									<td><input type="checkbox" checked="checked" id="Reports" onchange="updatePermission('Reports')"/>Reports</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="Reports" onchange="updatePermission('Reports')"/>Reports</td>
								</c:otherwise>
							</c:choose>			
							<c:choose>
								<c:when test="${user.accountsPayablePermissible}">
									<td><input type="checkbox" checked="checked" id="Accounts Payable" onchange="updatePermission('Accounts Payable')"/>A/R</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="Accounts Payable" onchange="updatePermission('Accounts Payable')"/>A/R</td>
								</c:otherwise>
							</c:choose>	
							<c:choose>
								<c:when test="${user.shelfLabelPermissible}">
									<td><input type="checkbox" checked="checked" id="Shelf Labels" onchange="updatePermission('Shelf Labels')"/>Shelf Labels</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="Shelf Labels" onchange="updatePermission('Shelf Labels')"/>Shelf Labels</td>
								</c:otherwise>
							</c:choose>	
							<c:choose>
								<c:when test="${user.ftpPermissible}">
									<td><input type="checkbox" checked="checked" id="FTP" onchange="updatePermission('FTP')"/>FTP</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="FTP" onchange="updatePermission('FTP')"/>FTP</td>
								</c:otherwise>
							</c:choose>	
							<c:choose>
								<c:when test="${user.admin}">
									<td><input type="checkbox" checked="checked" id="Admin" onchange="updatePermission('Admin')"/>Admin</td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" id="Admin" onchange="updatePermission('Admin')"/>Admin</td>
								</c:otherwise>
							</c:choose>	
			</tr>
		</table>
	</div>	
</div>

<div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>Store Info</strong>
		</span>
	</div>
	<div class="contentPanelHeader">
		Select stores to associate with this user.
		<br>
		<input id="criteria" name="criteria" type="text" style="width: 450px">
		<div id="autocomplete" class="autocomplete"></div>
		&nbsp;
		<input type="submit" onclick="addItem()" value="Add">
		<input type="button" value="Remove All Stores" onclick="removeAll()">
	</div>
</div>

<div class="contentPanel">
	<div class="contentPanelHeader" id="stores">
		<table id="storeInfo" cellspacing="0" cellpadding="0" width="95%">
			<tbody>
				<tr style="background-color: #D1D1D1">
					<th width="10%">
						Store Number
					</th>
					<th width="25%">
						Store Name
					</th>
					<th width="35%">
						Address
					</th>
					<th width="15%">
						City
					</th>
					<th width="5%">
						State
					</th>
					<th width="10%">
						&nbsp;
					</th>
				</tr>
				<c:forEach var="store" items="${stores}" varStatus="stat">
					<c:choose>
						<c:when test="${stat.count % 2 == 0}">
							<tr class="highlight">
						</c:when>
						<c:otherwise>
							<tr class="normal">
						</c:otherwise>
					</c:choose>
					<td width="10%">
						${store.number}
					</td>
					<td width="25%">
						${store.name}
					</td>
					<td width="35%">
						${store.address}
					</td>
					<td width="15%">
						${store.city}
					</td>
					<td width="5%">
						${store.state}
					</td>
					<td width="10%">
						<a href="#" onclick="removeStore('${store.number}')">Remove</a>
					</td>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script>
  	
  		function createAuto(){
			var auto = new Ajax.Autocompleter('criteria', 'autocomplete', 'search', {minChars: 2, parameters: 'action=search',  afterUpdateElement:getSelectedItem});
		}
		
		function getSelectedItem(text, li){
			var chainStore = li.id.split(":");
			itemID = chainStore[0];
			itemType = chainStore[1];
		}

		createAuto();
  	</script>
