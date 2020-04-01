<%@ taglib uri="/core" prefix="c" %>
<div class="contentPanel">
	<div class="contentPanelHeader">
		<span><strong>${jobName}</strong></span>
	</div>
  	<table>
  		<tr>
  			<td align="left">
  			<c:if test="${pages > 0}">
  				<a href="jobController?action=showHistory&jobName=${jobName}&rowStart=${0}&offSet=20">1</a>
				<c:forEach begin="1" end="${pages}" varStatus="row">
					<a href="jobController?action=showHistory&jobName=${jobName}&rowStart=${row.count * 20}&offSet=20">${row.count + 1}</a>
				</c:forEach>
  			</c:if>
  			</td>
  		</tr>
  	</table>
	<table id="jobTable" width="100%" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th align="center" width="15%"><strong>System PID<strong></th>
				<th align="center" width="15%"><strong>Root PID<strong></th>
				<th align="center" width="15%"><strong>Father PID<strong></th>
				<th align="center" width="15%"><strong>Time<strong></th>
				<th align="center" width="10%"><strong>Message Type<strong></th>
				<th align="center" width="10%"><strong>Message<strong></th>
				<th align="center" width="10%"><strong>Duration (seconds)<strong></th>
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
				<td align="center">${job.systemPID}</td>
				<td align="center">${job.rootPID}</td>
				<td align="center">${job.fatherPID}</td>
				<td align="center">${job.moment}</td>
				<td align="center">${job.messageType}</td>
				<td align="center">
					<c:choose>
						<c:when test="${job.message == 'success'}">
							<span class="success"><strong>${job.message}</strong></span>
						</c:when>
						<c:otherwise>
							<span class="failure">${job.message}</span>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td align="center">${job.duration}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div style="padding: 10px;">
		<input type="button" value="Back to Summary" onclick="javascript: window.location.href='jobController?action=getSummary'">
	</div>
</div>