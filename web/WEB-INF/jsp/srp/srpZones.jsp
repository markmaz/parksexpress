<%@ taglib uri="/core" prefix="c" %>
<span><strong>Zones</strong></span><br>
			<c:forEach var="zone" items="${zones['100']}">
 				<div class="contentPanelTopLevelStore">			 		
					<div>
						<span id="img_${zone.number}" style="cursor: pointer; color: #FFF;" onclick="showStores('img_${zone.number}', '${zone.number}')">(+)</span><input type="radio" id="check_${zone.number}" name="zoneID" checked value="${zone.number}" onclick="changeBook('${zone.number}', '${zone.description}')"><span style="font-size: x-small; color: white">${zone.description}</span><br>
					</div>
					<div id='${zone.number}'  style="display:none; background-color: white; color: black"></div>
				</div>
			</c:forEach>
	<c:forEach var="zone" items="${zones['50']}">
 		<div class="contentPanelStore">			 		
			<div>
				<span id="img_${zone.number}" style="cursor: pointer; color: #FFF" onclick="showStores('img_${zone.number}', '${zone.number}')">(+)</span><input type="radio" id="check_${zone.number}" name="zoneID" value="${zone.number}" onclick="changeBook('${zone.number}', '${zone.description}')"><span style="font-size: x-small; color: white">${zone.description}</span><br>
			</div>
			<div id='${zone.number}'  style="display:none; background-color: white; color: black"></div>
		</div>		
	</c:forEach>