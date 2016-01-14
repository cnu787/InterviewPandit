/**
 * This function is used to validating drop down values selected or not  
 */
function myDropdownCheckType() {
	$("#myinterview").bootstrapValidator("enableFieldValidators", "industry",
			!1), $("#myinterview").bootstrapValidator("enableFieldValidators",
			"industry", !0), $("#myinterview").bootstrapValidator(
			"validateField", "industry");
	var a = document.getElementById(subcatId);
	removeOptions(a), myDropdownCheck()
}
/**
 * This function is used to validating drop down values selected or not  
 */
function myDropdownInterviewCheck() {
	$("#myinterview").bootstrapValidator("enableFieldValidators",
			"interviewrole", !1), $("#myinterview").bootstrapValidator(
			"enableFieldValidators", "interviewrole", !0), $("#myinterview")
			.bootstrapValidator("validateField", "interviewrole")
}
/**
 * This function is used to validating drop down values selected or not  
 */
function myDropdownCompanyCheck() {
	
}
/**
 * This function is used to check selected  file changed ot not
 */
$(":file").change(function() {
	{
		var a = this.files[0];
		a.name, a.size, a.type
	}
});
/**
 * This function is used to add evaluator details 
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = new FormData($("form")[0]);
	$.ajax({
		url : "addinterviewerdetails",
		type : "POST",
		xhr : function() {
			var a = $.ajaxSettings.xhr();
			return a.upload
					&& a.addEventListener("progress", progressHandlingFunction,
							!1), a
		},
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
}, errorHandler = function() {
}, progressHandlingFunction = function() {
}, myDropdownCheck = function() {
	/**
	 * This function is used to validating drop down values selected or not  
	 */
			$("#myinterview").bootstrapValidator("enableFieldValidators",
					"domain", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "domain", !0), $("#myinterview")
					.bootstrapValidator("validateField", "domain"), $(
					"#myinterview").bootstrapValidator("enableFieldValidators",
					"interviewrole", !1), $("#myinterview").bootstrapValidator(
					"enableFieldValidators", "interviewrole", !0), $(
					"#myinterview").bootstrapValidator("validateField",
					"interviewrole")
};
$(document).ready(function() {
	/**
	 * This function is used to validating empty fields  
	 */
	$("#myotherIndustry").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			otherIndustry : {
				validators : {
					notEmpty : {
						message : "Others industry is required."
					}
				}
			},
			otherDomain : {
				validators : {
					notEmpty : {
						message : "Others domain is required."
					}
				}
			},
			otherinterviewroles : {
				validators : {
					notEmpty : {
						message : "Others interviewer role is required."
					}
				}
			}
		}
	}), $("#myotherIndustry").on("success.form.bv", function(a) {
		a.preventDefault(), myotherajaxform()
	})
});
var subcatId = "domain";
$("#closeId").click(
		function() {
			document.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1", document
					.getElementById("interviewrole").value = "-1",
					myDropdownCheckType()
		}), $("#myModal").click(
		function() {
			document.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1", document
					.getElementById("interviewrole").value = "-1",
					myDropdownCheckType()
		}), $("#othercloseId").click(
		function() {
			document.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1", document
					.getElementById("interviewrole").value = "-1",
					myDropdownCheckType()
		});
/**
 * This function is used to get other industry type interview
 */
var myotherajaxform = function() {
	var a = $("input[name=otherIndustry]").val(), b = $(
			"input[name=otherDomain]").val(), c = $(
			"input[name=otherinterviewroles]").val();
	$.ajax({
		url : "getIndustryotherTypesInterview.do/" + a + "/" + b + "/" + c,
		async : !1,
		success : function() {
			$("#myotherIndustry").bootstrapValidator("resetForm", !0), document
					.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1", document
					.getElementById("interviewrole").value = "-1",
					$("#myModal").modal("hide"), document
							.getElementById("otherIndustry").value = "",
					document.getElementById("otherDomain").value = "",
					myDropdownCheckType()
		}
	})
};
/**
 * This function is used to validating empty fields
 */
$(document).ready(function() {
	$("#myotherDomain").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			otherDomainName : {
				validators : {
					notEmpty : {
						message : "Others domain is required."
					}
				}
			}
		}
	}), $("#myotherDomain").on("success.form.bv", function(a) {
		a.preventDefault(), myothernameajaxform()
	})
}), $("#closeIdName").click(function() {
	document.getElementById("domain").value = "-1", myDropdownCheck()
}), $("#myModalOthersDomain").click(function() {
	document.getElementById("domain").value = "-1", myDropdownCheck()
}), $("#closeIdName").click(function() {
	document.getElementById("domain").value = "-1", myDropdownCheck()
});
/**
 * This function is used to get other domain type interviews 
 */
var myothernameajaxform = function() {
	var a = $("input[name=otherDomainName]").val();
	$.ajax({
		url : "getotherDomainInterview.do/" + a,
		async : !1,
		success : function() {
			document.getElementById("domain").value = "-1", $("#myotherDomain")
					.bootstrapValidator("resetForm", !0), document
					.getElementById("otherDomain").value = "", $(
					"#myModalOthersDomain").modal("hide"), myDropdownCheck()
		}
	})
};