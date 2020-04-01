<%@ taglib uri="/core" prefix="c" %>	
		<div class="contentPanelSRP" id="header">
			<span style="padding: 5px"><img src="images/arrow_close.gif"></span><span style="margin-top: -5px"><strong>${priceBookHeader.headerCode} - ${priceBookHeader.description}</strong></span>
			<div style="background-color: #FFF">
				<c:forEach var="pbClass" items="${priceBookHeader.priceBookClasses}">
					<div class="contentPanelInnerSRP" id="priceClass">
						<span style="padding: 5px"><img src="images/arrow_close.gif"></span><span style="margin-top: -5px"><strong>${pbClass.classCode} - ${pbClass.description}</strong></span>
						<div style="background-color: white">
						<c:forEach var="pbFamily" items="${pbClass.priceBookFamilies}">
							<div class="contentPanelInnerSRP" id="priceClass">
								<span style="padding: 5px"><img src="images/arrow_close.gif"></span><span style="margin-top: -5px"><strong>${pbFamily.familyCode} - ${pbFamily.description}</strong></span>
							</div>
							<br>
						</c:forEach>
						</div>
					</div>					
				</c:forEach>				
			</div>
		</div>			
		<br>	