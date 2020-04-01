	Shadowbox.init({
		skipSetup: true,
		players:["html"]
	});
	
	
	function openShadow(transport){		
		var html = transport.responseText;
	    Shadowbox.open({
	        player:     "html",
	        title:      "Email Address",
	        height:     100,
	        width:      350,
	        content:    html,
	        options: {enableKeys: false, modal: true}
	    });	
	    	    
	}
	
	function closeShadow(){
		Shadowbox.close();
		document.location.href="emailAddress?action=show";
	}
	
	function email(){
		var params = "action=newEmail";
		new Ajax.Request('emailAddress', {parameters: params, onSuccess: openShadow, evalScripts: true});
	}
	
	function saveNew(){
		var params = "action=saveNewEmail&email=" + $("emailAddress").value;
		new Ajax.Request("emailAddress", {parameters: params, onSuccess: closeShadow});
	}
	
	function deleteEmail(id){
		var params = "action=deleteEmail&id=" + id;
		new Ajax.Request("emailAddress", {parameters: params, onSuccess: repaint});
	}
	
	function repaint(){
		document.location.href="emailAddress?action=show";
	}