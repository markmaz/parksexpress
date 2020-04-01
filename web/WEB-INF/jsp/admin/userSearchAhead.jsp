<%@ taglib uri="/core" prefix="c" %>



		<ul style="list-style-type:none; margin:0; padding:0;">
		<c:forEach var="user" items="${users}">
			<li id="${user.id}" style="list-style-type:none; display:block; margin:0; padding:2px; height:32px; cursor:pointer;">${user.username}&nbsp;&nbsp;-&nbsp;${user.password}&nbsp;-&nbsp;${user.firstName}&nbsp;${user.lastName}&nbsp;${user.emailAddress}</li>
		</c:forEach>
		</ul>
