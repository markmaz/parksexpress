<%@ taglib uri="/core" prefix="c" %>
<%@ taglib uri="/fmt" prefix="fmt"  %>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<div id="reportHeader" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<div style="font-size: large; color:#a04617; padding: 10px; float: left">Email Management</div>
	<script type="text/javascript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/email/email.js"></script>
	<div style="text-align: right; padding: 10px; "><a href="#" onclick="printReport()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportReport()">&nbsp;&nbsp;</a></div>
</div>

<div id="reportData" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<c:choose>
		<c:when test="${not empty emailAddresses}">
			<table width="500px">
				<tr>
					<th>ID</th>
					<th>Email Address</th>
					<th></th>
				</tr>
				
				<c:forEach items="${emailAddresses}" var="email" varStatus="stat" >
					<c:choose>
						<c:when test="${stat.count % 2 == 0}">
							<tr class="highlight">
						</c:when>
						<c:otherwise>
							<tr class="normal">
						</c:otherwise>
					</c:choose>
					
					<td>${email.id}</td>
					<td>${email.emailAddress}</td>
					<td><a href="#" onclick="deleteEmail('${email.id}')">Delete</a>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td>No data found</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</div>

<div id="reportData" style="width: 900px; border: 1px #e5e5e5 solid; margin: 8px;  background-color: #f9f9f9;">
	<table>
		<tr>
			<td align="left"><input type="button" value="Add New" onclick="email()"></td>
		</tr>
	</table>
</div>
