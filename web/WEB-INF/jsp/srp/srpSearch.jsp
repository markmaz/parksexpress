<%@ taglib uri="/core" prefix="c" %>
  	<table>
  		<tr>
  			<td>
  				<input id="criteria" name="criteria" type="text" style="width: 400px">&nbsp;<input type="submit" onclick="getResults()" value="GO">
			  	<div id="resultMenu" style="position:absolute; width:400px; background-color:white; border:1px solid #888; margin:0; padding:0;"></div>
  			</td>
  		</tr>
  	</table>

  	<script>
  		function createAuto(){
			var auto = new Ajax.Autocompleter('criteria', 'resultMenu', 'srp', {minChars: 2, parameters: 'action=searchAhead', afterUpdateElement:getSelectedPriceGuide});
		}
		
		function getSelectedPriceGuide(text, li){
			document.location.href = "srp?action=showSRP&id=" + li.id + "&type=" + li.type;
		}

		createAuto();
  	</script>

