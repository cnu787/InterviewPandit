/**
 * This function is used to Validating drop down values selected or not
 */
function myDropdownCheckType() {
			$("#myprofile").bootstrapValidator("enableFieldValidators",
					"industry", !1), $("#myprofile").bootstrapValidator(
					"enableFieldValidators", "industry", !0), $("#myprofile")
					.bootstrapValidator("validateField", "industry");
	var a = document.getElementById(subcatId);
	removeOptions(a), myDropdownCheck()
}
$(document).ready(function() {
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
					.getElementById("domain").value = "-1",
					myDropdownCheckType()
		}), $("#myModal").click(
		function() {
			document.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1",
					myDropdownCheckType()
		}), $("#othercloseId").click(
		function() {
			document.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1",
					myDropdownCheckType()
		});
/**
 * This function is used to get other industry type profile
 */
var myotherajaxform = function() {
	var a = $("input[name=otherIndustry]").val(), b = $(
			"input[name=otherDomain]").val();
	$.ajax({
		url : "getIndustryotherTypeProfile.do/" + a + "/" + b,
		async : !1,
		success : function() {
			$("#myotherIndustry").bootstrapValidator("resetForm", !0), document
					.getElementById("industry").value = "-1", document
					.getElementById("domain").value = "-1", $("#myModal")
					.modal("hide"),
					document.getElementById("otherIndustry").value = "",
					document.getElementById("otherDomain").value = "",
					myDropdownCheckType()
		}
	})
};
/**
 * This function is used to Validating drop down values selected or not
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
 * This function is used to get other type domain profile
 */
var myothernameajaxform = function() {
	var a = $("input[name=otherDomainName]").val();
	$.ajax({
		url : "getotherDomainProfile.do/" + a,
		async : !1,
		success : function() {
			document.getElementById("domain").value = "-1", $("#myotherDomain")
					.bootstrapValidator("resetForm", !0), document
					.getElementById("otherDomain").value = "", $(
					"#myModalOthersDomain").modal("hide"), myDropdownCheck()
		}
	})
};