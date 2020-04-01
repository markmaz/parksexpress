<%@ taglib uri="/core" prefix="c"%>
<ul style="list-style-type: none; margin: 0; padding: 0;">
	<c:forEach var="vendor" items="${vendors}">
		<li style="list-style-type: none; display: block; margin: 0; padding: 2px; height: 32px; cursor: pointer; font-size: x-small; font-weight: bold;">${vendor.number} - ${vendor.name}</li>
	</c:forEach>
</ul>