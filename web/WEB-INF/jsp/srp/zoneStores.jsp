<%@ taglib uri="/core" prefix="c" %>
	<div style="margin-left: 5px">
		<c:forEach var="store" items="${stores}">
			<span style="font-size: .8em; color: black">${store.number} - ${store.name}</span><br>
		</c:forEach>
	</div>