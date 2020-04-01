<%@ taglib uri="/core" prefix="c" %>

  
  <script>
  	function getResults(){
  		document.location.href='userController?action=search&type=' + $('searchType').value + '&searchCriteria=' + $('searchCriteria').value;
  	}
  	
  </script>
  <div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>Users</strong></span>
	</div>
	<div class="contentPanelHeader">
  				<select id="searchType">
  					<option value="0">Username</option>
  					<option value="1">First Name</option>
  					<option value="2">Last Name</option>
  					<option value="3">Email Address</option>
  				</select>

  				<input id="searchCriteria" name="searchCriteria" type="text" style="width: 350px">&nbsp;<input type="submit" onclick="getResults()">
			  	<div id="autocomplete" name="autocomplete" class="autocomplete"></div>

  	</div>
  	<c:choose>
  		<c:when test="${empty users}">
  			<table>
  				<tr>
  					<td>No Matches</td>
  				</tr>
  			</table>
  		</c:when>
  		<c:otherwise>
			<table id="userTable" width="100%" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th><strong>Username</strong></th>
						<th><strong>Password<strong></th>
						<th><strong>Name<strong></th>
						<th><strong>Email<strong></th>
					</tr>
					<c:forEach var="user" items="${users}" varStatus="stat">
					<c:choose>
						<c:when test="${stat.count % 2 == 0}">
							<tr class="highlight">
						</c:when>
						<c:otherwise>
							<tr class="normal">
						</c:otherwise>
					</c:choose>
						<td align="left"><a href="userController?action=userDetail&id=${user.id}">${user.username}</a></td>
						<td align="left">${user.password}</td>
						<td align="left">${user.firstName}&nbsp;${user.lastName}</td>
						<td align="left">${user.emailAddress}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
  		</c:otherwise>
  	</c:choose>
	
	<div class="contentPanelHeader">
		<input type="button" value="Create User" onclick="javascript: document.location.href='userController?action=create'">
	</div>
  	<script>
  		function createAuto(){
			var auto = new Ajax.Autocompleter('searchCriteria', 'autocomplete', 'userController', {minChars: 2, parameters: 'action=searchAhead&type=0', afterUpdateElement:getSelectedUser});
		}
		
		function getSelectedUser(text, li){
			document.location.href = "userController?action=userDetail&id=" + li.id;
		}

		createAuto();
  	</script>
</div>
