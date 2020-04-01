<%@ taglib uri="/core" prefix="c"%>
<ul style="list-style-type: none; margin: 0; padding: 0;">
	<c:forEach var="result" items="${results}">
			<c:choose>
				<c:when test="${result.type == 'family' }">
					<li id="family:${result.number}" type="${result.type}" pack="${result.pack}" itemSize="${result.size}" itemName="${result.name}" style="list-style-type: none; display: block; margin: 0; padding: 2px; height: 32px; cursor: pointer; font-size: x-small; font-weight: bold;">(${result.type})&nbsp;${result.number}&nbsp;${result.name} ${result.pack} ${result.size}</li>
				</c:when>
				<c:otherwise>
					<li id="item:${result.number}" type="${result.type}" pack="${result.pack}" itemSize="${result.size}" itemName="${result.name}" style="list-style-type: none; display: block; margin: 0; padding: 2px; height: 32px; cursor: pointer; font-size: x-small;">(${result.type}) ${result.number} ${result.name} ${result.pack} ${result.size}</li>
				</c:otherwise>
			</c:choose>
	</c:forEach>
</ul>