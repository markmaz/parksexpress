<%@ taglib uri="/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Parks Express</title>
		<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="css/main.css" type="text/css">
		<link rel="stylesheet" href="css/picker.css" type="text/css">
		<!--[if IE 6]>
<link rel="stylesheet" href="css/ie6.css" type="text/css">
<![endif]-->
		<script type="text/javascript" src="/alfresco/scripts/menu.js"></script>
		<script type="text/javascript" src="/alfresco/scripts/webdav.js"></script>
		<script type="text/javascript" src="/alfresco/scripts/onload.js"></script>
		<script type="text/javascript"
			src="/alfresco/scripts/ajax/yahoo/yahoo/yahoo-min.js"></script>
		<script type="text/javascript"
			src="/alfresco/scripts/ajax/yahoo/connection/connection-min.js"></script>
		<script type="text/javascript"
			src="/alfresco/scripts/ajax/yahoo/event/event-min.js"></script>

		<script type="text/javascript"
			src="/alfresco/scripts/ajax/mootools.v1.11.js"></script>
		<script type="text/javascript" src="/alfresco/scripts/ajax/common.js"></script>
		<script type="text/javascript"
			src="/alfresco/scripts/ajax/summary-info.js"></script>
		<script type="text/javascript" src="/alfresco/scripts/ajax/picker.js"></script>
		<script type="text/javascript" src="/alfresco/scripts/ajax/tagger.js"></script>
	</head>
	<body>


		<script type="text/javascript">
function applySizeSpaces(e)
{
return applySize(e, 'spaces-apply');
}

function applySizeContent(e)
{
return applySize(e, 'content-apply');
}

function applySize(e, field)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
if (keycode == 13)
{
document.forms['browse']['browse:act'].value='browse:' + field;
document.forms['browse'].submit();
return false;
}
return true;
}
</script>
		<form id="browse" name="browse" method="post"
			action="/alfresco/faces/jsp/browse/browse.jsp" accept-charset="UTF-8"
			enctype="application/x-www-form-urlencoded">


			<table cellspacing=0 cellpadding=2>


				<tr>
					<td colspan=2>


						<table cellspacing="0" cellpadding="2" width="100%">
							<tr>

								<td style="width: 100%;">
									<table cellspacing="0" cellpadding="0" width="100%">
										<tr>
											<td style="padding-right: 4px;">
												<a href='#'
													onclick="document.forms['browse']['browse:act'].value='browse:about_alfresco';document.forms['browse'].submit();return false;"
													id='about_alfresco' title='Alfresco'><img
														src='images/logo/ccplogo32.png' alt='Parks Express'
														title='Parks Express'
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
											</td>
											<td>
												<img src="images/parts/titlebar_begin.gif" width="10"
													height="31" alt="" />
											</td>
											<td
												style="width: 100%; background-image: url(images/parts/titlebar_bg.gif); background-repeat: repeat-x">






												<table cellspacing='1' cellpadding='0'>
													<tr>
														<td>
															<table cellpadding='0' style='width: 100%;'
																cellspacing="3" class="topToolbar">
																<tr>
																	<td>
																		<a href='#'
																			onclick="document.forms['browse']['browse:modelist'].value='browse:_idJsp0:userhome';document.forms['browse'].submit();return false;"
																			class="topToolbarLink">My Home</a>
																	</td>
																</tr>
															</table>
														</td>
														<td>
															<table cellpadding='0' style='width: 100%;'
																cellspacing="3" class="topToolbar">
																<tr>
																	<td>
																		<a href='#'
																			onclick="document.forms['browse']['browse:modelist'].value='browse:_idJsp0:myalfresco';document.forms['browse'].submit();return false;"
																			class="topToolbarLink">Parks Express</a>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>

											</td>
											<td>
												<img src="images/parts/titlebar_end.gif" width="8"
													height="31" alt="" />
											</td>
										</tr>
									</table>
								</td>


								<td>
									<table cellspacing="2" cellpadding="0" width="100%">
										<tr>
											<td>
											</td>
											<td style="width: 12px;">
												&nbsp;
											</td>
											<td>
												<a href='userProfile.do'
													id='alf_user_console'><img
														src='images/icons/user_console.gif' alt='<c:out value="${user.firstName}"/>\'s Profile'
														title="<c:out value="${user.firstName}"/>'s Profile"
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
											</td>
											<td style="width: 8px;">
												&nbsp;
											</td>
											<td>
												<a href='#'
													onclick="document.forms['browse']['browse:act'].value='browse:alf_toggle_shelf';document.forms['browse'].submit();return false;"
													id='alf_toggle_shelf'><img src='images/icons/shelf.gif'
														alt='Hide or Show the Sidebar'
														title='Hide or Show the Sidebar'
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
											</td>
											<td style="width: 8px;">
												&nbsp;
											</td>
											<td>
												<a href="#"
													target='help' id='alf_help'><img
														src='images/icons/Help_icon.gif' alt='Help' title='Help'
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
											</td>
											<td style="width: 8px;">
												&nbsp;
											</td>

											<td style="width: 8px;">
												&nbsp;
											</td>
											<td style="white-space: nowrap;">
												<a href="logout.do">
												<img src='images/icons/login.gif' alt='Logout'
													title='Logout'
													style='border-width: 0px; vertical-align: -4px;' /></a>
												<a href='logout.do' id='logout' style='padding-left: 2px; vertical-align: 0%'><c:out value="${user.firstName}"/> (log out)</a>
											</td>
										</tr>
									</table>
								</td>
								<td>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr valign=top>
					<td>
						<div id='sidebar' class='sidebar'>
							<table cellspacing='0' cellpadding='0'
								style='background-color: #ffffff;' width='100%'>
								<tr>
									<td
										style='width: 5px; background-image: url(images/parts/sidebar_top_grey_begin.gif)'
										valign='top'>
										<img src="images/parts/sidebar_grey_01.gif" width='5'
											height='5' alt='' />
									</td>
									<td
										style='height: 24px; background-image: url(images/parts/sidebar_top_grey_bg.gif)'>
										<table border='0' cellpadding='6' cellspacing='0'
											style='width: 100%;'>
											<tr>
												<td>
													<table cellspacing='0' cellpadding='0'
														style='white-space: nowrap'>
														<tr>
															<td>
																<a href='#'
																	onclick="javascript:_toggleMenu(event, 'browse:sidebarPluginList_0');return false;"><span>Navigator</span>&nbsp;<img
																		src='images/icons/menu.gif' alt=''
																		style='border-width: 0px; vertical-align: -4px;' />
																</a>
															</td>
														</tr>
													</table>
													<div id='browse:sidebarPluginList_0'
														style='position: absolute; display: none; padding-left: 2px;'>
														<table cellspacing='1' cellpadding='0'
															class="moreActionsMenu">
															<tr>
																<td>
																	<table cellpadding='0' style='width: 100%;'
																		cellspacing="4" class="statusListHighlight">
																		<tr>
																			<td style='width: 2px'></td>
																			<td>
																				<a href='#'
																					onclick="document.forms['browse']['browse:modelist'].value='browse:sidebarPluginList:navigator';document.forms['browse'].submit();return false;"
																					title="Allows tree based navigation around the repository">Navigator</a>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td>
																	<table cellpadding='0' style='width: 100%;'
																		cellspacing="4">
																		<tr>
																			<td style='width: 2px'></td>
																			<td>
																				<a href='#'
																					onclick="document.forms['browse']['browse:modelist'].value='browse:sidebarPluginList:shelf';document.forms['browse'].submit();return false;"
																					title="Area that includes the clipboard, recent space and shortcuts">Shelf</a>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td>
																	<table cellpadding='0' style='width: 100%;'
																		cellspacing="4">
																		<tr>
																			<td style='width: 2px'></td>
																			<td>
																				<a href='#'
																					onclick="document.forms['browse']['browse:modelist'].value='browse:sidebarPluginList:opensearch';document.forms['browse'].submit();return false;"
																					title="Provides ability to search across multiple OpenSearch supported search engines.">OpenSearch</a>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td>
																	<table cellpadding='0' style='width: 100%;'
																		cellspacing="4">
																		<tr>
																			<td style='width: 2px'></td>
																			<td>
																				<a href='#'
																					onclick="document.forms['browse']['browse:modelist'].value='browse:sidebarPluginList:category-browser';document.forms['browse'].submit();return false;"
																					title="Category Browsing">Categories</a>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</div>
												</td>
												<td align='right'>
													<span id="browse:id_3"><a href='#'
														onclick="document.forms['browse']['browse:act'].value='browse:reset_navigatorid_4';document.forms['browse'].submit();return false;"
														id='reset_navigatorid_4'><img
																src='images/icons/reset.gif' alt='Refresh'
																title='Refresh'
																style='border-width: 0px; vertical-align: -4px;' />
													</a>
													</span>
												</td>
											</tr>
										</table>
									</td>
									<td
										style='width: 5px; background-image: url(images/parts/sidebar_top_grey_end.gif)'
										align='right' valign='top'>
										<img src='images/parts/sidebar_grey_03.gif' width='5'
											height='5' alt='' />
									</td>
								</tr>
								<tr>
									<td colspan='3'>
										<div id="pluginBox">
											<div id="navigator" class="navigator">
												<div class="sidebarButtonSelected"
													style="background-image: url(images/parts/navigator_blue_gradient_bg.gif)">
													<a class='sidebarButtonSelectedLink'
														onclick="document.forms['browse']['browse:sidebar-body:navigator'].value='panel:userhome';document.forms['browse'].submit();return false;"
														href="#">SRP</a>
												</div>
												<script type="text/javascript">function treeNodeSelected(nodeRef) {document.forms['browse']['browse:sidebar-body:navigator'].value=nodeRef;document.forms['browse'].submit();}</script>
												<div class="navigatorPanelBody"></div>
												<div class="sidebarButton"
													style="background-image: url(images/parts/navigator_grey_gradient_bg.gif)">
													<a class='sidebarButtonLink'
														onclick="document.forms['browse']['browse:sidebar-body:navigator'].value='panel:myalfresco';document.forms['browse'].submit();return false;"
														href="#">AR</a>
												</div>
												<div class="sidebarButton"
													style="background-image: url(images/parts/navigator_grey_gradient_bg.gif)">
													<a class='sidebarButtonLink'
														onclick="document.forms['browse']['browse:sidebar-body:navigator'].value='panel:myalfresco';document.forms['browse'].submit();return false;"
														href="#">AR</a>
												</div>
																																				<div class="sidebarButton"
													style="background-image: url(images/parts/navigator_grey_gradient_bg.gif)">
													<a class='sidebarButtonLink'
														onclick="document.forms['browse']['browse:sidebar-body:navigator'].value='panel:myalfresco';document.forms['browse'].submit();return false;"
														href="#">AR</a>
												</div>
												<div class="sidebarButton"
													style="background-image: url(images/parts/navigator_grey_gradient_bg.gif)">
													<a class='sidebarButtonLink'
														onclick="document.forms['browse']['browse:sidebar-body:navigator'].value='panel:myalfresco';document.forms['browse'].submit();return false;"
														href="#">AR</a>
												</div>												
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td style='height: 12px; width: 5px;'>
										<img src='images/parts/sidebar_bottom_grey_begin.gif'
											width='5' height='12' alt='' />
									</td>
									<td
										style='_width: 100%; background-image: url(images/parts/sidebar_bottom_grey_bg.gif)'>
										<img src='images/parts/sidebar_bottom_grey_bg.gif' width='48'
											height='12' alt='' />
									</td>
									<td align='right' style='width: 5px;'>
										<img src='images/parts/sidebar_bottom_grey_end.gif' width='5'
											height='12' alt='' />
									</td>
								</tr>
							</table>
						</div>

					</td>


					<td width=100%>
						<table cellspacing=0 cellpadding=0 width=100%>




							<tr>
								<td>
									<img src="images/parts/headbar_1.gif" width="4" height="7"
										alt="" />
								</td>
								<td
									style="width: 100%; background-image: url(images/parts/headbar_2.gif)"></td>
								<td>
									<img src="images/parts/headbar_3.gif" width="4" height="7"
										alt="" />
								</td>
							</tr>

							<tr>
								<td style="background-image: url(images/parts/headbar_4.gif)"></td>
								<td style="background-color: #dfe6ed;">

									<div style="padding-left: 8px" class="headbarTitle">

										<a href='#'
											onclick="document.forms['browse']['browse:_idJsp7'].value='0';document.forms['browse'].submit();return false;"
											class="headbarLink">${user.firstName}'s Home</a>
									</div>
								</td>
								<td style="background-image: url(images/parts/headbar_6.gif)"></td>
							</tr>

							<tr>
								<td>
									<img src="images/parts/headbar_7.gif" width="4" height="10"
										alt="" />
								</td>
								<td
									style="width: 100%; background-image: url(images/parts/headbar_8.gif)"></td>
								<td>
									<img src="images/parts/headbar_9.gif" width="4" height="10"
										alt="" />
								</td>
							</tr>



							<tr>
								<td
									style="background-image: url(images/parts/statuspanel_4.gif)"
									width=4></td>

								<td bgcolor="#dfe6ed">



									<table cellspacing=4 cellpadding=0 width=100%>
										<tr>



											<td width=32>
												<img id="browse:space-logo"
													src="images/icons/space-icon-default.gif" height="32"
													width="32" />
											</td>
											<td>

												<div class="mainTitle">
													<span id="browse:msg2">${user.firstName}'s Home</span>&nbsp;

													<a href="file:///\\ERRATAA\Alfresco\Guest Home"
														target='new' id='cifs'><img
															src='images/icons/opennetwork.gif'
															alt='Open Network Folder \\ERRATAA\Alfresco\Guest Home'
															title='Open Network Folder \\ERRATAA\Alfresco\Guest Home'
															style='border-width: 0px; vertical-align: -4px;' />
													</a>&nbsp;

												</div>
												<div class="mainSubText">
													<span id="browse:msg3">This view allows you to
														browse the items in this space.</span>
												</div>
												<div class="mainSubText">
													<span id="browse:msg4">The guest root space</span>
												</div>
											</td>
											<td style="padding-right: 4px" align=right>
												<nobr>

													<img id="browse:img-rule" src="images/icons/rule.gif"
														height="16" width="16"
														title="Number of rules applied to this Space" />
													<span id="browse:rulemsg1" style="vertical-align: 20%">(0)</span>
												</nobr>
											</td>
											<td class="separator" width=1>
												<img src="images/parts/dotted_separator.gif" border=0
													height=29 width=1>
											</td>

											<td style="padding-left: 4px; white-space: nowrap"
												align="right">

												<span id="browse:id_5"></span>
											</td>
											<td style="padding-left: 4px" width=52>

												<a href='#'
													onclick="javascript:_toggleMenu(event, 'browse:createMenu_1');return false;"
													style="white-space: nowrap">Create<img
														src='images/icons/menu.gif' alt=''
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
												<br>
												<div id='browse:createMenu_1'
													style="position: absolute; display: none; padding-left: 2px; * width: 0px">
													<table border='0' cellpadding='0' cellspacing="4"
														class="moreActionsMenu">
														<span id="browse:id_8"></span>
													</table>
												</div>
											</td>
											<td style="padding-left: 4px" width=80>

												<a href='#'
													onclick="javascript:_toggleMenu(event, 'browse:actionsMenu_2');return false;"
													style="white-space: nowrap">More Actions<img
														src='images/icons/menu.gif' alt=''
														style='border-width: 0px; vertical-align: -4px;' />
												</a>
												<br>
												<div id='browse:actionsMenu_2'
													style="position: absolute; display: none; padding-left: 2px; * width: 0px">
													<table border='0' cellpadding='0' cellspacing="4"
														class="moreActionsMenu">
														<span id="browse:id_21">
														<tr>
															<td>
																<img src='images/icons/View_details.gif'
																	alt='View Details' title='View Details'
																	style='border-width: 0px;' />
															</td>
															<td>
																<a href='#'
																	onclick="document.forms['browse']['browse:act'].value='browse:details_spaceid_22';document.forms['browse']['id'].value='5ea3acee-e68a-4774-87da-a8680f01f40b';document.forms['browse'].submit();return false;">View
																	Details</a>
															</td>
														</tr>
														<tr>
															<td>
																<img src='images/icons/copy.gif' alt='Copy' title='Copy'
																	style='border-width: 0px;' />
															</td>
															<td>
																<a href='#'
																	onclick="document.forms['browse']['browse:act'].value='browse:copy_nodeid_32';document.forms['browse']['ref'].value='workspace://SpacesStore/5ea3acee-e68a-4774-87da-a8680f01f40b';document.forms['browse'].submit();return false;">Copy</a>
															</td>
														</tr>
														</span>

													</table>
												</div>
											</td>





											<td class="separator" width=1>
												<img src="images/parts/dotted_separator.gif" border=0
													height=29 width=1>
											</td>
											<td width=118 valign=middle>






												<table cellspacing='0' cellpadding='0'
													style='white-space: nowrap'>
													<tr>
														<td style='padding-right: 4px'>
															<img src='images/icons/Details.gif' alt=''
																style='border-width: 0px; vertical-align: middle;' />
														</td>
														<td>
															<a href='#'
																onclick="javascript:_toggleMenu(event, 'browse:viewMode_3');return false;"><span>Icon
																	View</span>&nbsp;<img src='images/icons/menu.gif' alt=''
																	style='border-width: 0px; vertical-align: -4px;' />
															</a>
														</td>
													</tr>
												</table>
												<div id='browse:viewMode_3'
													style='position: absolute; display: none; padding-left: 2px;'>
													<table cellspacing='1' cellpadding='0'
														class="moreActionsMenu">
														<tr>
															<td>
																<table cellpadding='0' style='width: 100%;'
																	cellspacing="4">
																	<tr>
																		<td style='width: 20px'></td>
																		<td>
																			<a href='#'
																				onclick="document.forms['browse']['browse:modelist'].value='browse:viewMode:details';document.forms['browse'].submit();return false;">Details
																				View</a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table cellpadding='0' style='width: 100%;'
																	cellspacing="4" class="statusListHighlight">
																	<tr>
																		<td style='width: 20px'>
																			<img src='images/icons/Details.gif' alt=''
																				style='border-width: 0px;' />
																		</td>
																		<td>
																			<a href='#'
																				onclick="document.forms['browse']['browse:modelist'].value='browse:viewMode:icons';document.forms['browse'].submit();return false;">Icon
																				View</a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table cellpadding='0' style='width: 100%;'
																	cellspacing="4">
																	<tr>
																		<td style='width: 20px'></td>
																		<td>
																			<a href='#'
																				onclick="document.forms['browse']['browse:modelist'].value='browse:viewMode:list';document.forms['browse'].submit();return false;">Browse
																				View</a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table cellpadding='0' style='width: 100%;'
																	cellspacing="4">
																	<tr>
																		<td style='width: 20px'></td>
																		<td>
																			<span class="statusListDisabled">Custom View</span>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</div>

											</td>
										</tr>
									</table>

								</td>
								<td
									style="background-image: url(images/parts/statuspanel_6.gif)"
									width=4></td>
							</tr>


							<tr>
								<td>
									<img src="images/parts/statuspanel_7.gif" width=4 height=9>
								</td>
								<td
									style="background-image: url(images/parts/statuspanel_8.gif)"></td>
								<td>
									<img src="images/parts/statuspanel_9.gif" width=4 height=9>
								</td>
							</tr>




							<tr valign=top>
								<td style="background-image: url(images/parts/whitepanel_4.gif)"
									width=4></td>
								<td style="padding: 4px">


									<span id="browse:spaces-panel-facets"></span>
									<table cellspacing='0' cellpadding='0'
										style='border-width: 0px; width: 100%'>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_01.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-image: url(images/parts/lbgrey_02.gif)'>
												<img src='images/parts/lbgrey_02.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_03.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='background-image: url(images/parts/lbgrey_04.gif)'>
												<img src='images/parts/lbgrey_04.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-color: white;'>
												<table border='0' cellspacing='0' cellpadding='0'
													width='100%'>
													<tr>
														<td>
															<a href='#'
																onclick="document.forms['browse']['browse:panel'].value='browse:spaces-panel:false';document.forms['browse'].submit();return false;"><img
																	src='images/icons/expanded.gif' width='11' height='11'
																	alt='Browse Spaces' title='Browse Spaces'
																	style='border-width: 0px;' />
															</a>&nbsp;&nbsp;
															<span class="mainSubTitle">Browse Spaces</span>
														</td>
														<td align='right'>
															<span id="browse:items-txt1">Items Per Page</span>
															<input id="browse:spaces-pages"
																name="browse:spaces-pages" type="text" value="9"
																maxlength="3" onkeyup="return applySizeSpaces(event);"
																style="width: 24px; margin-left: 4px" />
															<a href='#'
																onclick="document.forms['browse']['browse:act'].value='browse:spaces-apply';document.forms['browse'].submit();return false;"
																id='spaces-apply'></a>
														</td>
													</tr>
												</table>
											</td>
											<td style='background-image: url(images/parts/lbgrey_06.gif)'>
												<img src='images/parts/lbgrey_06.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_dotted_07.gif' width='7'
													height='7' alt='' />
											</td>
											<td
												style='background-image: url(images/parts/lbgrey_dotted_08.gif)'>
												<img src='images/parts/lbgrey_dotted_08.gif' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_dotted_09.gif' width='7'
													height='7' alt='' />
											</td>
										</tr>
										<tr>
											<td style='background-image: url(images/parts/white_04.gif)'>
												<img src='images/parts/white_04.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-color: white; padding-top: 6px;'>
												<table cellspacing=0 cellpadding=0 class="recordSet"
													width="100%">
													<thead></thead>
													<tbody>
														<span id="browse:no-space-items">No items to
															display. Click the 'Create Space' action to create a
															space.</span>
														</tr>
														<tr>
															<td colspan=10>
																<div style='padding: 3px'></div>
															</td>
														</tr>
														<tr>
															<td colspan=99 align=center>
																<span class=pager>Page 1 of 1&nbsp;&nbsp;<img
																		src='images/icons/FirstPage_unavailable.gif'
																		width='16' height='16' alt=''
																		style='border-width: 0px;' />&nbsp;<img
																		src='images/icons/PreviousPage_unavailable.gif'
																		width='16' height='16' alt=''
																		style='border-width: 0px;' />&nbsp;<b>1</b>&nbsp;<img
																		src='images/icons/NextPage_unavailable.gif' width='16'
																		height='16' alt='' style='border-width: 0px;' />&nbsp;<img
																		src='images/icons/LastPage_unavailable.gif' width='16'
																		height='16' alt='' style='border-width: 0px;' />
																</span>
															</td>
														</tr>
													</tbody>
												</table>

											</td>
											<td style='background-image: url(images/parts/white_06.gif)'>
												<img src='images/parts/white_06.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/white_07.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-image: url(images/parts/white_08.gif)'>
												<img src='images/parts/white_08.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/white_09.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
									</table>

								</td>

								<td style="background-image: url(images/parts/whitepanel_6.gif)"
									width=4></td>
							</tr>


							<tr valign=top>
								<td style="background-image: url(images/parts/whitepanel_4.gif)"
									width=4></td>
								<td style="padding: 4px">

									<span id="browse:content-panel-facets"></span>
									<table cellspacing='0' cellpadding='0'
										style='border-width: 0px; width: 100%'>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_01.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-image: url(images/parts/lbgrey_02.gif)'>
												<img src='images/parts/lbgrey_02.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_03.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='background-image: url(images/parts/lbgrey_04.gif)'>
												<img src='images/parts/lbgrey_04.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-color: white;'>
												<table border='0' cellspacing='0' cellpadding='0'
													width='100%'>
													<tr>
														<td>
															<a href='#'
																onclick="document.forms['browse']['browse:panel'].value='browse:content-panel:false';document.forms['browse'].submit();return false;"><img
																	src='images/icons/expanded.gif' width='11' height='11'
																	alt='Content Items' title='Content Items'
																	style='border-width: 0px;' />
															</a>&nbsp;&nbsp;
															<span class="mainSubTitle">Content Items</span>
														</td>
														<td align='right'>
															<span id="browse:items-txt2">Items Per Page</span>
															<input id="browse:content-pages"
																name="browse:content-pages" type="text" value="9"
																maxlength="3" onkeyup="return applySizeContent(event);"
																style="width: 24px; margin-left: 4px" />
															<a href='#'
																onclick="document.forms['browse']['browse:act'].value='browse:content-apply';document.forms['browse'].submit();return false;"
																id='content-apply'></a>
														</td>
													</tr>
												</table>
											</td>
											<td style='background-image: url(images/parts/lbgrey_06.gif)'>
												<img src='images/parts/lbgrey_06.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_dotted_07.gif' width='7'
													height='7' alt='' />
											</td>
											<td
												style='background-image: url(images/parts/lbgrey_dotted_08.gif)'>
												<img src='images/parts/lbgrey_dotted_08.gif' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/lbgrey_dotted_09.gif' width='7'
													height='7' alt='' />
											</td>
										</tr>
										<tr>
											<td style='background-image: url(images/parts/white_04.gif)'>
												<img src='images/parts/white_04.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-color: white; padding-top: 6px;'>














































































































												<table cellspacing=0 cellpadding=0 class="recordSet"
													width="100%">
													<thead></thead>
													<tbody>
														<tr class="recordSetRow">
															<td width=33%>
																<table cellspacing=0 cellpadding=2 border=0>
																	<tr>
																		<td rowspan=10
																			style="padding: 2px; text-align: left; vertical-align: top;">
																			<a
																				href="/alfresco/d/d/workspace/SpacesStore/558c91cb-abbc-40c7-8da4-3bef0989e06c/Alfresco-Tutorial.pdf"
																				target='new' id='col11-act1' class='inlineAction'><img
																					src='images/filetypes32/pdf.gif'
																					alt='Alfresco-Tutorial.pdf'
																					title='Alfresco-Tutorial.pdf'
																					style='border-width: 0px; vertical-align: -4px;' />
																			</a>
																		</td>
																		<td
																			style="padding: 2px; text-align: left; vertical-align: top;">
																			<a
																				href="/alfresco/d/d/workspace/SpacesStore/558c91cb-abbc-40c7-8da4-3bef0989e06c/Alfresco-Tutorial.pdf"
																				target='new' id='col11-act2' class='header'>Alfresco-Tutorial.pdf</a>
																			<script>var AlfNodeInfoMgr = new Alfresco.PanelManager("NodeInfoBean.sendNodeInfo", "noderef");</script>
																			<span
																				onclick="AlfNodeInfoMgr.toggle('workspace://SpacesStore/558c91cb-abbc-40c7-8da4-3bef0989e06c',this);"><img
																					id="browse:col11-img" src="images/icons/popup.gif"
																					height="16" width="16" class="popupImage" />
																			</span>
																		</td>
																	</tr>
																	<tr>
																		<td style="text-align: left">
																			<span id="browse:col13-txt">Getting started
																				guide</span>
																		</td>
																	</tr>
																	<tr>
																		<td style="text-align: left">
																			<span id="browse:col15-txt">4.15 MB</span>
																		</td>
																	</tr>
																	<tr>
																		<td style="text-align: left">
																			<span id="browse:col17-txt">6 October 2008
																				10:40</span>
																		</td>
																	</tr>
																	<tr>
																		<td style="text-align: left">
																			<span id="browse:id_48"><a
																				href="/alfresco/d/a/workspace/SpacesStore/558c91cb-abbc-40c7-8da4-3bef0989e06c/Alfresco-Tutorial.pdf"
																				id='download_docid_77' class='inlineAction'><img
																						src='images/icons/download_doc.gif' alt='Download'
																						title='Download'
																						style='border-width: 0px; vertical-align: -4px;' />
																			</a><a href='#'
																				onclick="document.forms['browse']['browse:act'].value='browse:details_docid_78';document.forms['browse']['id'].value='558c91cb-abbc-40c7-8da4-3bef0989e06c';document.forms['browse'].submit();return false;"
																				id='details_docid_78' class='inlineAction'><img
																						src='images/icons/View_details.gif'
																						alt='View Details' title='View Details'
																						style='border-width: 0px; vertical-align: -4px;' />
																			</a>
																			</span><a href='#'
																				onclick="javascript:_toggleMenu(event, 'browse:content-more-menu_4');return false;"
																				title="More Actions"><img
																					src='images/icons/more.gif' alt=''
																					style='border-width: 0px; vertical-align: -4px;' />
																			</a>
																			<br>
																			<div id='browse:content-more-menu_4'
																				style="position: absolute; display: none; padding-left: 2px; * width: 0px">
																				<table border='0' cellpadding='0' cellspacing="4"
																					class="moreActionsMenu">
																					<span id="browse:id_88">
																					<tr>
																						<td>
																							<img src='images/icons/preview.gif'
																								alt='Preview in Template'
																								title='Preview in Template'
																								style='border-width: 0px;' />
																						</td>
																						<td>
																							<a href='#'
																								onclick="document.forms['browse']['browse:act'].value='browse:preview_docid_89';document.forms['browse']['id'].value='558c91cb-abbc-40c7-8da4-3bef0989e06c';document.forms['browse'].submit();return false;">Preview
																								in Template</a>
																						</td>
																					</tr>
																					<tr>
																						<td>
																							<img src='images/icons/copy.gif' alt='Copy'
																								title='Copy' style='border-width: 0px;' />
																						</td>
																						<td>
																							<a href='#'
																								onclick="document.forms['browse']['browse:act'].value='browse:copy_nodeid_111';document.forms['browse']['ref'].value='workspace://SpacesStore/558c91cb-abbc-40c7-8da4-3bef0989e06c';document.forms['browse'].submit();return false;">Copy</a>
																						</td>
																					</tr>
																					</span>
																				</table>
																			</div>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td colspan=10>
																<div style='padding: 3px'></div>
															</td>
														</tr>
														<tr>
															<td colspan=99 align=center>
																<span class=pager>Page 1 of 1&nbsp;&nbsp;<img
																		src='images/icons/FirstPage_unavailable.gif'
																		width='16' height='16' alt=''
																		style='border-width: 0px;' />&nbsp;<img
																		src='images/icons/PreviousPage_unavailable.gif'
																		width='16' height='16' alt=''
																		style='border-width: 0px;' />&nbsp;<b>1</b>&nbsp;<img
																		src='images/icons/NextPage_unavailable.gif' width='16'
																		height='16' alt='' style='border-width: 0px;' />&nbsp;<img
																		src='images/icons/LastPage_unavailable.gif' width='16'
																		height='16' alt='' style='border-width: 0px;' />
																</span>
															</td>
														</tr>
													</tbody>
												</table>

											</td>
											<td style='background-image: url(images/parts/white_06.gif)'>
												<img src='images/parts/white_06.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
										<tr>
											<td style='width: 7px;'>
												<img src='images/parts/white_07.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='background-image: url(images/parts/white_08.gif)'>
												<img src='images/parts/white_08.gif' width='7' height='7'
													alt='' />
											</td>
											<td style='width: 7px;'>
												<img src='images/parts/white_09.gif' width='7' height='7'
													alt='' />
											</td>
										</tr>
									</table>

								</td>
								<td style="background-image: url(images/parts/whitepanel_6.gif)"
									width=4></td>
							</tr>


							<tr valign=top>
								<td style="background-image: url(images/parts/whitepanel_4.gif)"
									width=4></td>
								<td style="padding: 4px">


								</td>
								<td style="background-image: url(images/parts/whitepanel_6.gif)"
									width=4></td>
							</tr>

							<tr>
								<td>
									<img src="images/parts/whitepanel_7.gif" width=4 height=4>
								</td>
								<td width=100% align=center
									style="background-image: url(images/parts/whitepanel_8.gif)"></td>
								<td>
									<img src="images/parts/whitepanel_9.gif" width=4 height=4>
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>

			<input type="hidden" name="browse_SUBMIT" value="1" />
			<input type="hidden" name="browse:_idJsp7" />
			<input type="hidden" name="browse:act" />
			<input type="hidden" name="ref" />
			<input type="hidden" name="browse:sidebar-body:navigator" />
			<input type="hidden" name="browse:panel" />
			<input type="hidden" name="id" />
			<input type="hidden" name="browse:modelist" />
			<script type="text/javascript"><!--

	function clear_browse()
	{
		clearFormHiddenParams_browse('browse');
	}
	
	function clearFormHiddenParams_browse(currFormName)
	{
		var f = document.forms['browse'];
		f.elements['browse:_idJsp7'].value='';
		f.elements['browse:act'].value='';
		f.elements['ref'].value='';
		f.elements['browse:sidebar-body:navigator'].value='';
		f.elements['browse:panel'].value='';
		f.elements['id'].value='';
		f.elements['browse:modelist'].value='';
		f.target='';
	}
	
	clearFormHiddenParams_browse();
//--></script>
			<input type="hidden" name="javax.faces.ViewState"
				id="javax.faces.ViewState"
				value="rO0ABXVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAN0AAEycHQAFi9qc3AvYnJvd3NlL2Jyb3dzZS5qc3A=" />
		</form>
		<center>
			<table style='margin: 0px auto;'>
				<tr>
					<td>
						<a href='http://www.charlescparks.com'><img
								style='vertical-align: middle; border-width: 0px;' alt=''
								title='Charles C. Parks'
								src='images/logo/parks200.png'>
						</a>
					</td>
					<td align='center'>&nbsp;
					</td>
					<td>&nbsp;
					</td>
				</tr>
			</table>
		</center>

	</body>
</html>
