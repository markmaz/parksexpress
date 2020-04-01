<%@ taglib uri="/form" prefix="form" %>
<%@ taglib uri="/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Parks Express - Login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="css/main.css" type="text/css">
		<link rel="stylesheet" href="css/picker.css" type="text/css">
		<!--[if IE 6]><link rel="stylesheet" href="css/ie6.css" type="text/css"><![endif]-->
		<script type="text/javascript" src="scripts/browserDetect.js"></script>
	</head>
	<script>
		if(BrowserDetect.browser == 'Firefox'){
		}else{
			document.location.href = "unsupported";
		}
	</script>
	
	<body bgcolor="#ffffff" style="background-image: url(images/logo/ParksFadeBG.png); background-repeat: no-repeat; background-attachment: fixed">
		<div style="top:0px; height: 200px"></div>
			<form:form commandName="user" action="logon.do">
				<table width=100% height=98% align=center>
					<tr width=100% align=center>
						<td valign=middle align=center width=100%>
						<table cellspacing=0 cellpadding=0 border=0>
							<tr>
								<td width=7>
									<img src='images/parts/white_01.gif' width=7 height=7 alt=''>
								<br></td>
								<td background='images/parts/white_02.gif'>
									<img src='images/parts/white_02.gif' width=7 height=7 alt=''>
								<br></td>
								<td width=7>
									<img src='images/parts/white_03.gif' width=7 height=7 alt=''>
								<br></td>
							</tr>
							<tr>
								<td background='images/parts/white_04.gif'>
									<img src='images/parts/white_04.gif' width=7 height=7 alt=''>
								<br></td>
								<td bgcolor='white'>
									<table border=0 cellspacing=4 cellpadding=2>
										<tr>
											<td colspan=2>
												<img src='images/logo/parks200.png' width=200 height=58 alt="Parks Express" title="Parks Express">
											<br></td>
										</tr>
										<tr>
											<td colspan=2>
												<span class='mainSubTitle'>Enter Login details:</span>
											<br></td>
										</tr>
										<tr>
											<td>
												User Name:
											<br></td>
											<td>
												<form:input path="username"/>
											<br></td>
										</tr>
										<tr>
											<td>
												Password:
											<br></td>
											<td>
												<form:password path="password"/>
											<br></td>
										</tr>
										<tr>
											<td colspan=2 align=right>
												<input id="loginForm:submit" name="loginForm:submit" type="submit" value="Login"
													onclick="if(typeof window.clearFormHiddenParams_loginForm!='undefined'){clearFormHiddenParams_loginForm('loginForm');}" />
											<br></td>
										</tr>
										<tr>
											<td colspan=2><br></td>
										</tr>
									</table>
								<br></td>
								<td background='images/parts/white_06.gif'>
									<img src='images/parts/white_06.gif' width=7 height=7 alt=''>
								<br></td>
							</tr>
							<tr>
								<td width=7>
									<img src='images/parts/white_07.gif' width=7 height=7 alt=''>
								<br></td>
								<td background='images/parts/white_08.gif'>
									<img src='images/parts/white_08.gif' width=7 height=7 alt=''>
								<br></td>
								<td width=7>
									<img src='images/parts/white_09.gif' width=7 height=7 alt=''>
								<br></td>
							</tr>
						</table>

						<c:if test="${not empty errMsg}">
						<div >
							<table cellpadding="0" cellspacing="0" border="0" style="padding-top: 16px;">
								<tr>
									<td>
										<table cellspacing='0' cellpadding='0' style='border-width: 0px; width: 100%'>
											<tr>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_01.gif' width='7' height='7' alt='' />
												<br></td>
												<td	style='background-image: url(images/parts/yellowInner_02.gif)'>
													<img src='images/parts/yellowInner_02.gif' width='7' height='7' alt='' />
												<br></td>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_03.gif' width='7'
														height='7' alt='' />
												<br></td>
											</tr>
											<tr>
												<td
													style='background-image: url(images/parts/yellowInner_04.gif)'>
													<img src='images/parts/yellowInner_04.gif' width='7'
														height='7' alt='' />
												<br></td>
												<td style='background-color: #ffffcc;'>
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td valign=top style="padding-top: 2px" width=20>
																<img src="images/icons/info_icon.gif" height="16" width="16" />
															<br></td>
															<td class="mainSubText">
																<c:out value="${errMsg}"></c:out>
															<br></td>
														</tr>
													</table>
												<br></td>
												<td	style='background-image: url(images/parts/yellowInner_06.gif)'>
													<img src='images/parts/yellowInner_06.gif' width='7'height='7' alt='' />
												<br></td>
											</tr>
											<tr>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_07.gif' width='7'height='7' alt='' />
												<br></td>
												<td style='background-image: url(images/parts/yellowInner_08.gif)'>
													<img src='images/parts/yellowInner_08.gif' width='7'height='7' alt='' />
												<br></td>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_09.gif' width='7'height='7' alt='' />
												<br></td>
											</tr>
										</table>
									<br></td>
								</tr>
							</table>
						</div>
						</c:if>
						<div id="no-cookies" style="display: none">
							<table cellpadding="0" cellspacing="0" border="0" style="padding-top: 16px;">
								<tr>
									<td>
										<table cellspacing='0' cellpadding='0' style='border-width: 0px; width: 100%'>
											<tr>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_01.gif' width='7' height='7' alt='' />
												</td>
												<td	style='background-image: url(images/parts/yellowInner_02.gif)'>
													<img src='images/parts/yellowInner_02.gif' width='7' height='7' alt='' />
												</td>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_03.gif' width='7'
														height='7' alt='' />
												</td>
											</tr>
											<tr>
												<td
													style='background-image: url(images/parts/yellowInner_04.gif)'>
													<img src='images/parts/yellowInner_04.gif' width='7'
														height='7' alt='' />
												</td>
												<td style='background-color: #ffffcc;'>
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td valign=top style="padding-top: 2px" width=20>
																<img src="images/icons/info_icon.gif" height="16" width="16" />
															</td>
															<td class="mainSubText">
																Cookies must be enabled in your browser for Parks Express to function correctly.
															</td>
														</tr>
													</table>
												</td>
												<td	style='background-image: url(images/parts/yellowInner_06.gif)'>
													<img src='images/parts/yellowInner_06.gif' width='7'height='7' alt='' />
												</td>
											</tr>
											<tr>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_07.gif' width='7'height='7' alt='' />
												</td>
												<td style='background-image: url(images/parts/yellowInner_08.gif)'>
													<img src='images/parts/yellowInner_08.gif' width='7'height='7' alt='' />
												</td>
												<td style='width: 7px;'>
													<img src='images/parts/yellowInner_09.gif' width='7'height='7' alt='' />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<script>
							document.cookie="_parksTest=_parksTest"
							var cookieEnabled = (document.cookie.indexOf("_parksTest") != -1);
							
							if (cookieEnabled == false){
								document.getElementById("no-cookies").style.display = 'inline';
							}
						</script>
					</td>
				</tr>
			</table>
			<script type="text/javascript"><!--
				function clear_loginForm(){
					clearFormHiddenParams_loginForm('loginForm');
				}
				
				function clearFormHiddenParams_loginForm(currFormName){
					var f = document.forms['loginForm'];
					f.elements['loginForm:_idcl'].value='';
					f.elements['loginForm:_link_hidden_'].value='';
					f.target='';
				}
				
				clearFormHiddenParams_loginForm();
			//--></script>
		</form:form>
		<script>
			if (document.getElementById("loginForm:user-name").value.length == 0){
				document.getElementById("loginForm:user-name").focus();
			}else{
				document.getElementById("loginForm:user-password").focus();
			}
			</script>
	</body>
</html>

</body>
