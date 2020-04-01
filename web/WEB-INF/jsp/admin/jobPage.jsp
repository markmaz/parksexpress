<%@ taglib uri="/core" prefix="c" %>
<script type="text/javascript" src="scripts/jquery-1.5.1.min.js">
<script type="text/javascript">
	var interval = 5000;
	window.setInterval("reloadPage()", interval);
	
	function reloadPage(){
		document.location.href = "jobController?action=getSummary";
	}
</script>
<div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>Jobs</strong></span>
	</div>
	<table id="jobTable" width="100%" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th width="35%"><strong>Job Name<strong></th>
				<th align="center" width="15%"><strong>Time<strong></th>
				<th align="center" width="10%"><strong>Message Type<strong></th>
				<th align="center" width="10%"><strong>Message<strong></th>
				<th align="center" width="10%"><strong>Duration (seconds)<strong></th>
				<th align="center" width="10%"><strong>Run Job Manually<strong></th>
			</tr>
			<c:forEach var="job" items="${jobs}" varStatus="stat">
			<c:choose>
				<c:when test="${stat.count % 2 == 0}">
					<tr class="highlight">
				</c:when>
				<c:otherwise>
					<tr class="normal">
				</c:otherwise>
			</c:choose>
				<td><a href="jobController?action=showHistory&jobName=${job.name}">${job.name}</a></td>
				<td align="center">${job.moment}</td>
				<td align="center">${job.messageType}</td>
				<td align="center">
					<c:choose>
						<c:when test="${job.message == 'success'}">
							<font style="color: green"><strong>${job.message}</strong></font>
						</c:when>
						<c:otherwise>
							<font style="color: red">${job.message}</font>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td align="center">${job.duration}</td>
				<td align="center"><input type="submit" value="Run Job" onclick="javascript: jQuery.post('jobController?action=runJob&jobName=${job.name}')"></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="submit" value="Run All Jobs" onclick="javascript: jQuery.post('jobController?action=runJob&jobName=LoadAll')">
</div>