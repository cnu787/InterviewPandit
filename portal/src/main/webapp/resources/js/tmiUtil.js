
/**
 * This Function is used to remove Options from drop down
 * @param obj
 */
function removeOptions(obj) {
	if (obj == null)
		return;
	if (obj.options == null)
		return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
	obj.options[obj.options.length] = new Option("Select", "-1");
}
/**
 * This Function is used to enable Other Fields
 * @param subCat
 * @param othSubcat
 */
function enableOthersField(subCat, othSubcat) {
	var subcatId = document.getElementById(subCat).options[document
			.getElementById(subCat).selectedIndex].value;
	if (subcatId == "0") {
		$('#myModalOthersName').modal({keyboard: false,show:true})
		} else {
		
	}
}
/**
 *  This Function is used to show skill list
 * @param skillnames
 * @param skilltype
 * @param skillNameValue
 * @param industryId
 */
function showSkillList(skillnames, skilltype, skillNameValue, industryId) {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var select = document.getElementById(skillnames);
	var skilltypeId = document.getElementById(skilltype).options[document
			.getElementById(skilltype).selectedIndex].value;
	if (skilltypeId == 0) {
		$('#myModal').modal({keyboard: false,show:true})
		var requestURL = "#";
		xmlhttp.open("GET", requestURL, true);
		xmlhttp.send();
	} else {
		removeOptions(select);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
					if (xmlhttp.status == 200) {
					var jsonObject = xmlhttp.responseText;
					var subCatList = JSON.parse(jsonObject);
					removeOptions(select);
					for ( var subCat in subCatList) {
						select.options[select.options.length] = new Option(
								subCatList[subCat].skillname,
								subCatList[subCat].skillnameid);
						if (skillNameValue == subCatList[subCat].skillnameid) {
							select.options[select.options.length - 1].selected = true;
						}
					}
					myDropdownCheck();
				}
			}
		};
		myDropdownCheck();
		var requestURL = "getSkillsList?skilltypeId=" + skilltypeId
				+ "&industryId=" + industryId;
		xmlhttp.open("GET", requestURL, true);
		xmlhttp.send();
	}
}
/**
 *  This Function is used to show interview roles
 * @param industryId
 * @param interviewRoleId
 * @param InterviewerRoleValue
 */
function showInterviewerRole(industryId, interviewRoleId, InterviewerRoleValue) {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	var select = document.getElementById(interviewRoleId);
	var industryTypeId = document.getElementById(industryId).options[document
			.getElementById(industryId).selectedIndex].value;
	removeOptions(select);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var jsonObject = xmlhttp.responseText;
				var subCatList = JSON.parse(jsonObject);
				removeOptions(select);
				for ( var subCat in subCatList) {
					select.options[select.options.length] = new Option(
							subCatList[subCat].interviewerrolename,
							subCatList[subCat].interviewerroleid);
					if (InterviewerRoleValue == subCatList[subCat].interviewerroleid) {
						select.options[select.options.length - 1].selected = true;
					}
				}
				myDropdownCheck();
			}
		}
	};
	myDropdownCheck();
	var requestURL = "getInterviewerRole?industryId=" + industryTypeId;
	xmlhttp.open("GET", requestURL, true);
	xmlhttp.send();
}
/**
 *  This Function is used to show SubCategory List Interview
 * @param subcatId
 * @param categId
 * @param domainValue
 */
function showSubCategoryListInterview(subcatId, categId, domainValue) {	
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var select = document.getElementById(subcatId);
	var catId = document.getElementById(categId).options[document
			.getElementById(categId).selectedIndex].value;
	if (catId == 0) {
		$('#myModal').modal({ keyboard: false,show:true })
		var requestURL = "#";
		xmlhttp.open("GET", requestURL, true);
		xmlhttp.send();
	} else {
		removeOptions(select);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					var jsonObject = xmlhttp.responseText;
					var subCatList = JSON.parse(jsonObject);
					removeOptions(select);
					for ( var subCat in subCatList) {
						select.options[select.options.length] = new Option(
								subCatList[subCat].domainname,
								subCatList[subCat].domainid);
						if (domainValue != null) {
							var array = domainValue.split(',');
							for (var j = 0; j <= array.length; j++) {
								if (array[j] == subCatList[subCat].domainid) {
									select.options[select.options.length - 1].selected = true;
								}
							}
						}
					}
					myDropdownCheck();
				}
			}
		};
		myDropdownCheck();
		var requestURL = "getDomainList?industryId=" + catId;
		xmlhttp.open("GET", requestURL, true);
		xmlhttp.send();
	}
}
/**
 * This Function is used to enable other interviews
 * @param subCat
 * @param othSubcat
 */
function enableOthersInterview(subCat, othSubcat) {
	var otherscatId = false;
	var subcatId = document.getElementById(subCat);
	appendString = '';
	for (var i = 0; i < subcatId.length; i++) {
		if (subcatId[i].selected == true) {
			appendString += +subcatId[i].value + ",";
			if (subcatId[i].value == 0) {
				otherscatId = true;
				break;
			}
		}
	}
	if (otherscatId == true) {
		$('#myModalOthersDomain').modal({ keyboard: false,show: true})
	}
}
/**
 * This Function is used to  enable Other Interview Roles
 * @param interviewrole
 * @param othSubcat
 */
function enableOthersInterviewRoles(interviewrole,othSubcat) {
	var subcatId = document.getElementById(interviewrole).options[document.getElementById(interviewrole).selectedIndex].value;	
	if (subcatId == "0") {		
		$('#myotherinterviewrole').modal({ keyboard: false,show: true})
	}
	
	}
/**
 * This Function is used to show SubCategory List Interview not Others category  
 * @param subcatId
 * @param categId
 * @param domainValue
 */
function showSubCategoryListInterviewnotOthers(subcatId, categId, domainValue) {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var select = document.getElementById(subcatId);
	var catId = document.getElementById(categId).options[document
			.getElementById(categId).selectedIndex].value;	
		removeOptions(select);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					var jsonObject = xmlhttp.responseText;
					var subCatList = JSON.parse(jsonObject);
					removeOptions(select);
					for ( var subCat in subCatList) {
						select.options[select.options.length] = new Option(
								subCatList[subCat].domainname,
								subCatList[subCat].domainid);
						if (domainValue != null) {
							var array = domainValue.split(',');
							for (var j = 0; j <= array.length; j++) {
								if (array[j] == subCatList[subCat].domainid) {
									select.options[select.options.length - 1].selected = true;
								}
							}
						}
					}
					myDropdownCheck();
				}
			}
		};
		myDropdownCheck();
		var requestURL = "getDomainListNotOthers?industryId=" + catId;
		xmlhttp.open("GET", requestURL, true);
		xmlhttp.send();
	
}
/**
 * This Function is used to show Interviewer Roles not Other roles
 * @param industryId
 * @param interviewRoleId
 * @param InterviewerRoleValue
 */
function showInterviewerRolenotOthers(industryId, interviewRoleId,InterviewerRoleValue) {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	var select = document.getElementById(interviewRoleId);
	var industryTypeId = document.getElementById(industryId).options[document
			.getElementById(industryId).selectedIndex].value;
	removeOptions(select);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var jsonObject = xmlhttp.responseText;
				var subCatList = JSON.parse(jsonObject);
				removeOptions(select);					
				for ( var subCat in subCatList) {
					select.options[select.options.length] = new Option(
							subCatList[subCat].interviewerrolename,
							subCatList[subCat].interviewerroleid);
					if (InterviewerRoleValue == subCatList[subCat].interviewerroleid) {
						select.options[select.options.length - 1].selected = true;
					};
				}
				myDropdownCheck();
			};
		};
	};
	myDropdownCheck();
	var requestURL = "getInterviewerRoleNotOthers?industryId=" + industryTypeId;
	xmlhttp.open("GET", requestURL, true);
	xmlhttp.send();
}

/**
 * This Function is used to show Interviewer Roles not Other roles
 * @param industryId
 * @param interviewRoleId
 * @param InterviewerRoleValue
 */
function showInterviewerRoleWithRateCard(industryId, interviewRoleId, InterviewerRoleValue,interviewId) {
	//alert(industryId+"--"+interviewRoleId+"--"+InterviewerRoleValue+"--"+interviewId);
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	var select = document.getElementById(interviewRoleId);
	var industryTypeId = document.getElementById(industryId).options[document
			.getElementById(industryId).selectedIndex].value;
	removeOptions(select);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var jsonObject = xmlhttp.responseText;
				var subCatList = JSON.parse(jsonObject);
				removeOptions(select);
					
				var tooltipText="";
				for ( var subCat in subCatList) {
					tooltipText+="<div class='tool-pull-left'>"+subCatList[subCat].interviewerrolename+"</div><div class='tool-pull-right'> - INR"+subCatList[subCat].amount+" </div><div class='tool-clear'></div>";
					select.options[select.options.length] = new Option(
							subCatList[subCat].interviewerrolename,
							subCatList[subCat].interviewerroleid);
					if (InterviewerRoleValue == subCatList[subCat].interviewerroleid) {
						select.options[select.options.length - 1].selected = true;
					};
				}
				$('#tooltip_id').attr('title', tooltipText).tooltip('fixTitle');
				myDropdownCheck();
			};
		};
	};
	myDropdownCheck();
	var requestURL = "getInterviewerRoleWithRateCard?industryId=" + industryTypeId+"&interviewId="+interviewId;
	xmlhttp.open("GET", requestURL, true);
	xmlhttp.send();
}

/**
 * This Function is used to enable other company name
 * @param companyName
 */
function enableOthersCompanyName(companyName) {
	var subcatId = document.getElementById(companyName).options[document.getElementById(companyName).selectedIndex].value;
	if (subcatId == "0") {			
		$('#myothercompanyName').modal({ keyboard: false,show: true})
	}
	
	}

/**
 * This Function is used to show sub category list
 * @param subcatId
 * @param categId
 * @param othcat
 * @param othSubcat
 * @param domainValue
 */
function showSubCategoryList(subcatId,categId,othcat,othSubcat,domainValue) {	
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var select = document.getElementById(subcatId);
	var catId = document.getElementById(categId).options[document
			.getElementById(categId).selectedIndex].value;	
	removeOptions(select);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {				
				var jsonObject = xmlhttp.responseText;
				var subCatList = JSON.parse(jsonObject);
				removeOptions(select);				
				if (catId == "0") {				
					//put Others option selected into subcateogry list 
					document.getElementById(subcatId).options[0] = new Option("Others", 0);
					document.getElementById(subcatId).options[0].selected = true;
					//enable others text area
					document.getElementById(othcat).style.display = "block";
					document.getElementById(othSubcat).style.display = "block";					
					myDropdownCheck();					
				} else {					
					document.getElementById(othcat).style.display = "none";
					document.getElementById(othSubcat).style.display = "none";					
				for ( var subCat in subCatList) {
					select.options[select.options.length] = new Option(
							subCatList[subCat].domainname,
							subCatList[subCat].domainid);
					/*if(domainValue==subCatList[subCat].domainid){
						select.options[select.options.length-1].selected=true;
						if(domainValue==0){							
							document.getElementById(othSubcat).style.display = "block";
						}
					}*/					
					if (domainValue != null) {
						var array = domainValue.split(',');
						for (var j = 0; j <= array.length; j++) {							
							if (array[j] == subCatList[subCat].domainid) {
								select.options[select.options.length - 1].selected = true;
								if(array[j] ==0 ){
									document.getElementById(othSubcat).style.display = "block";
								}
							}
						}
					}
					
					//myDropdownCheck();
				}
				myDropdownCheck();				
			}
		}
	}	

	};
	if(catId == -1){
	document.getElementById(othcat).style.display = "none";
	document.getElementById(othSubcat).style.display = "none";
	
	myObject =	myDropdownCheck();
	}
	var requestURL = "getDomainList?industryId=" + catId;	
	xmlhttp.open("GET", requestURL, true);
	xmlhttp.send();
}
/**
 * This Function is used to enable other field profile
 * @param subCat
 * @param othSubcat
 */
function enableOthersFieldProfile(subCat,othSubcat) {
	var otherscatId = false;
	var subcatId = document.getElementById(subCat);
	appendString = '';
	for (var i = 0; i < subcatId.length; i++) {
		if (subcatId[i].selected == true) {
			appendString += +subcatId[i].value + ",";
			if (subcatId[i].value == 0) {
				otherscatId = true;
				break;
			}
		}
	}
	if (otherscatId == true) {
		document.getElementById(othSubcat).style.display = "block";
	}else {
		document.getElementById(othSubcat).style.display = "none";
	}	
}


/**
 * This Function is used to  show skill list in profile
 * @param skillnames
 * @param skilltype
 * @param othcat
 * @param othSubcat
 * @param industryId
 * @param skillNameValue
 */
function showSkillListProfile(skillnames, skilltype, othcat, othSubcat,industryId,skillNameValue) {	
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var select = document.getElementById(skillnames);
	var skilltypeId = document.getElementById(skilltype).options[document
			.getElementById(skilltype).selectedIndex].value;	
	removeOptions(select);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var jsonObject = xmlhttp.responseText;
			//	alert(jsonObject);
				var subCatList = JSON.parse(jsonObject);
				removeOptions(select);				
				if (skilltypeId == "0") {
					// put Others option selected into subcateogry list
					document.getElementById(skillnames).options[0] = new Option(
							"Others", 0);
					document.getElementById(skillnames).options[0].selected = true;
					// enable others text area
					document.getElementById(othcat).style.display = "block";
					document.getElementById(othSubcat).style.display = "block";
					myDropdownCheck();
				} else {
					document.getElementById(othcat).style.display = "none";
					document.getElementById(othSubcat).style.display = "none";
					for ( var subCat in subCatList) {
						select.options[select.options.length] = new Option(
								subCatList[subCat].skillname,
								subCatList[subCat].skillnameid);
						//alert("skillNameValue=="+skillNameValue);
						//alert("skillNameValue=="+subCatList[subCat].skillnameid);
						if (skillNameValue == subCatList[subCat].skillnameid) {
							select.options[select.options.length - 1].selected = true;
							if (skillNameValue == 0) {
								document.getElementById(othSubcat).style.display = "block";
							}
						}
					}
					myDropdownCheck();
				}
			}
		}
	};
	if (skilltypeId == "0") {
	document.getElementById(othcat).style.display = "block";
	document.getElementById(othSubcat).style.display = "block";
	}else {
		document.getElementById(othcat).style.display = "none";
		document.getElementById(othSubcat).style.display = "none";
	}	
	myDropdownCheck();
	var requestURL = "getSkillsListWithOthers?skilltypeId=" + skilltypeId
	+ "&industryId=" + industryId;
	xmlhttp.open("GET", requestURL, true);
	xmlhttp.send();
}
/**
 * This Function is used to  enable other field skills 
 * @param subCat
 * @param othSubcat
 */
function enableOthersFieldSkills(subCat, othSubcat) {
	var subcatId = document.getElementById(subCat).options[document
			.getElementById(subCat).selectedIndex].value;
	if (subcatId == "0") {
		document.getElementById(othSubcat).style.display = "block";
	}else {
		document.getElementById(othSubcat).style.display = "none";
	}	
}

	

	function getAutoSuggestNames(name,id,controllerName) {
		var xmlHttp;
		if (document.getElementById(name).value == "") {
			document.getElementById(id).value = "-1";
		} else{
			document.getElementById(id).value = "0";
			
		}
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlHttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var str = escape((document.getElementById(name).value).trim());	
		if (str.length == 0) {				
			$("#search_"+name).hide();				
		} else{
			
			xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {				
				var divID = document.getElementById('search_'+name);				
				divID.style.display = 'block';
				divID.innerHTML = '';
				var str = JSON.parse(xmlHttp.responseText);			
				if (str == "") {
					document.getElementById(id).value = "0";
					divID.style.display = 'none';
				}				
				if (str.length <= 5) {
					divID.style.height = "auto";
				} else if (str.length >= 5) {
					divID.style.height = "80px";
					divID.style.overflowY = "scroll";
				}
				for (var i = 0; i < str.length; i++) {				
					var suggest = '<div onmouseover="javascript:suggestOvers(this);" ';
					suggest += 'onmouseout="javascript:suggestOuts(this);" ';
					suggest += 'onclick="javascript:setSearchName(\''
							+ str[i][name] + '\',\''+ str[i][id] + '\',\''+name + '\',\''+id + '\');" ';
					suggest += 'class="suggest_link">' + str[i][name]
							 
							 + '</div>';
					divID.innerHTML += suggest;
				}
			}
		};
		xmlHttp.open("POST",  controllerName);
		xmlHttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlHttp.send("search=" + str , true);
		}
	}
	function suggestOvers(div_value) {
		div_value.className = 'suggest_link_over';
		div_value.style.background = '#0094DA';
	}
	//Mouse out function
	function suggestOuts(div_value) {
		div_value.className = 'suggest_link';
		div_value.style.background = 'white';
	}
	/**
	 * This Function is used to set search result name and id
	 * @param sname
	 * @param sid
	 * @param name
	 * @param id
	 */
	function setSearchName(sname,sid,name,id) {		
		document.getElementById(name).value = sname;
		document.getElementById(id).value = sid;
		document.getElementById('search_'+name).innerHTML = '';
		document.getElementById('search_'+name).style.display = 'none'; 		
		
	}
	
