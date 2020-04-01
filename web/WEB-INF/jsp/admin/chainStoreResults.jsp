<%@ taglib uri="/core" prefix="c"%>
<ul style="list-style-type: none; margin: 0; padding: 0;">
	<c:forEach var="result" items="${results}">
		<li id="${result.number}:${result.type}"
			style="list-style-type: none; display: block; margin: 0; padding: 2px; height: 32px; cursor: pointer;">${result.number}&nbsp;${result.name}&nbsp;(${result.type})</li>
	</c:forEach>
</ul>