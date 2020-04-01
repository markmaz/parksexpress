<%@ taglib uri="/tiles" prefix="tiles"%>
<html>
  <head>
    
    <title><tiles:getAsString name="title"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="css/main.css" type="text/css">
		<link rel="stylesheet" href="css/picker.css" type="text/css">
		  <script type="text/javascript" src="scripts/jquery-1.3.2.min.js"></script>
  <script type="text/javascript" src="scripts/prototype.js"></script>
  <script type="text/javascript" src="scripts/scriptaculous.js"></script>
  <script type="text/javascript" src="scripts/effects.js"></script>
<style type="text/css">
body{padding: 20px;background-color: #FFF;
    font: 100.01% "Trebuchet MS",Verdana,Arial,sans-serif}
h1,h2,p{margin: 0 10px}
h1{font-size: 250%;color: #FFF}
h2{font-size: 200%;color: #f0f0f0}
p{padding-bottom:1em}
h2{padding-top: 0.3em}

b.rtop, b.rbottom{display:block;background: #FFF}
b.rtop b, b.rbottom b{display:block;height: 1px;
    overflow: hidden; background: #dfe6ed}
b.r1{margin: 0 5px}
b.r2{margin: 0 3px}
b.r3{margin: 0 2px}
b.rtop b.r4, b.rbottom b.r4{margin: 0 1px;height: 2px}
</style>

  </head>
  
  <body>
	<div id="wrapper">
		<tiles:insert attribute="titleBar"/>
		<tiles:insert attribute="content"/>
	</div>
  </body>
</html>