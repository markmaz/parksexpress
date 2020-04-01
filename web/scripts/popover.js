/* Offset position of tooltip */
var x_offset_tooltip = 5;
var y_offset_tooltip = 0;

/* Don't change anything below here */


var tooltipObj = false;
var tooltipObj_iframe = false;

var tooltip_MSIE = false;
if(navigator.userAgent.indexOf('MSIE')>=0)tooltip_MSIE=true;


function showTooltip(title, msg,inputObj){
		tooltipObj = document.createElement('DIV');
		tooltipObj.style.position = 'absolute';
		tooltipObj.id = 'ajax_tooltipObj';	
		tooltipObj.name = 'ajax_tooltipObj';	
		tooltipObj.style.zIndex="100";	
		document.body.appendChild(tooltipObj);
		
		var contentDiv = document.createElement('DIV'); 
		tooltipObj.appendChild(contentDiv);
		contentDiv.id = 'popover';

		var popTop = document.createElement('DIV');
		contentDiv.appendChild(popTop);
		popTop.id = 'popover_top';
		popTop.innerHTML = "<H1>" + title + "</H1>";
		
		var popMiddle = document.createElement('DIV');
		contentDiv.appendChild(popMiddle);
		popMiddle.id = 'popover_middle';				
		popMiddle.innerHTML = msg;
		
		var popBottom = document.createElement('DIV');
		contentDiv.appendChild(popBottom);
		popBottom.id = 'popover_bottom';

		/* Create iframe object for MSIE in order to make the tooltip cover select boxes */
		if(tooltip_MSIE){	
			tooltipObj_iframe = document.createElement('<IFRAME frameborder="0">');
			tooltipObj_iframe.style.position = 'absolute';
			tooltipObj_iframe.border='0';
			tooltipObj_iframe.frameborder=0;
			tooltipObj_iframe.style.backgroundColor='#FFF';
			tooltipObj_iframe.src = 'about:blank';
			tooltipObj_iframe.style.left = '0px';
			tooltipObj_iframe.style.top = '0px';
			tooltipObj_iframe.style.zIndex = "1";		
			document.body.appendChild(tooltipObj_iframe);
		}

	
		tooltipObj.style.display='block';
	
	// Find position of tooltip
	//ajax_loadContent('ajax_tooltip_content',externalFile);
	if(tooltip_MSIE){
		tooltipObj_iframe.style.width = 260 + 'px';
		tooltipObj_iframe.style.height = 200 + 'px';
	}

	positionTooltip(inputObj);
	//tooltipObj.style.opacity = 0;
	
	/**
	new Effect.Opacity(tooltipObj,
  		{ duration: 2.0, from: 0.0, to: 1.0 } );
	new Effect.Opacity(tooltipObj,
    	{ delay: 2.0, duration: 4.0, from: 1.0, to: 0.0, afterFinish: hideTooltip } );
    	**/
}

function positionTooltip(inputObj){
	var leftPos = (getLeftPos(inputObj) + inputObj.offsetWidth);
	var topPos = getTopPos(inputObj);
	
	var tooltipWidth = '300px'; 
	tooltipObj.style.left = leftPos + 'px';
	tooltipObj.style.top = topPos + 'px';	
	
	if(tooltip_MSIE){
		tooltipObj_iframe.style.left = (leftPos + 40) + 'px';
		tooltipObj_iframe.style.top = (topPos + 5) + 'px';
	}
}


function hideTooltip(){
		tooltipObj.style.display='none';
	
		if(tooltip_MSIE){
			tooltipObj_iframe.style.display='none';
		}
}


function getTopPos(inputObj){		
  var returnValue = inputObj.offsetTop;
  
  while((inputObj = inputObj.offsetParent) != null){
  	if(inputObj.tagName!='HTML'){
		returnValue += inputObj.offsetTop;
	}
		
  }
  
  return returnValue - 92;
}

function getLeftPos(inputObj){
  var returnValue = inputObj.offsetLeft;
  while((inputObj = inputObj.offsetParent) != null){
  	if(inputObj.tagName!='HTML'){
		returnValue += inputObj.offsetLeft;	
	}
  }
  return returnValue;
}