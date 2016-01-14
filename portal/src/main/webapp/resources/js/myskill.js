/**
 * This function is used to displays interview tech skills view
 */
function backSkills() {
	$("#interviewBookingContainer").load("interviewTechSkills")
}
function myDropdownCheckType() {
	$("#myInterSkillForm").bootstrapValidator("enableFieldValidators",
			"skilltype", !1), $("#myInterSkillForm").bootstrapValidator(
			"enableFieldValidators", "skilltype", !0), $("#myInterSkillForm")
			.bootstrapValidator("validateField", "skilltype");
	var a = "skillname", b = document.getElementById(a);
	removeOptions(b), myDropdownCheck()
}
/**
 * This function is used to Validating empty fields 
 */
jQuery(document).ready(function() {
	$("#interviewlandLink").parent().addClass("active")
}), $(document).ready(function() {
	$("#myInterSkillForm").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			skillname : {
				validators : {
					callback : {
						message : "Please select listed skill name.",
						callback : function(a, b) {
							var d = b.getFieldElements("skillname").val();
							return -1 != d
						}
					}
				}
			},
			skilltype : {
				validators : {
					callback : {
						message : "Please select listed skill type.",
						callback : function(a, b) {
							var d = b.getFieldElements("skilltype").val();
							return -1 != d
						}
					}
				}
			},
			skillrating : {
				validators : {
					callback : {
						message : "Please select listed skill rating.",
						callback : function(a, b) {
							var d = b.getFieldElements("skillrating").val();
							return -1 != d
						}
					}
				}
			},
			exprienceyear : {
				validators : {
					callback : {
						message : "Please select listed experience year.",
						callback : function(a, b) {
							var d = b.getFieldElements("exprienceyear").val();
							return -1 != d
						}
					}
				}
			},
			expriencemonth : {
				validators : {
					callback : {
						message : "Please select listed experience month.",
						callback : function(a, b) {
							var d = b.getFieldElements("expriencemonth").val();
							return -1 != d
						}
					}
				}
			}
		}
	}), $("#myInterSkillForm").on("success.form.bv", function(a) {
		a.preventDefault(), 0 == myflag && myajaxform()
	})
});
/**
 * This function is used to  add skills to interview 
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "addSkillsToInterView",
		type : "GET",
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
}, completeHandler = function() {
	$("#processImg").hide();
	$("#interviewBookingContainer").load("interviewTechSkills")
}, errorHandler = function() {
}, progressHandlingFunction = function() {
}, myDropdownCheck = function() {
	$("#myInterSkillForm").bootstrapValidator("enableFieldValidators",
			"skillname", !1), $("#myInterSkillForm").bootstrapValidator(
			"enableFieldValidators", "skillname", !0), $("#myInterSkillForm")
			.bootstrapValidator("validateField", "skillname")
};
/**
 * This function is used to Validating empty fields
 */
$(document).ready(function() {
	$("#myotherSkills").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			otherTypeName : {
				validators : {
					notEmpty : {
						message : "Others  skilltype is required."
					}
				}
			},
			otherName : {
				validators : {
					notEmpty : {
						message : "Others  skillname is required."
					}
				}
			}
		}
	}), $("#myotherSkills").on("success.form.bv", function(a) {
		a.preventDefault(), myotherajaxform()
	})
}), $("#closeId").click(function() {
	document.getElementById("skilltype").value = "-1", myDropdownCheckType()
}), $("#myModal").click(function() {
	document.getElementById("skilltype").value = "-1", myDropdownCheckType()
}), $("#othercloseId").click(function() {
	document.getElementById("skilltype").value = "-1", myDropdownCheckType()
});
/**
 * This function is used to get other type skills interview
 */
var myotherajaxform = function() {
	var a = $("input[name=otherTypeName]").val(), b = $("input[name=otherName]")
			.val();
	$.ajax({
		url : "getSkillotherTypesInterview.do/" + a + "/" + b,
		async : !1,
		success : function() {
			$("#myotherSkills").bootstrapValidator("resetForm", !0), document
					.getElementById("skilltype").value = "-1", $("#myModal")
					.modal("hide"),
					document.getElementById("otherTypeName").value = "",
					document.getElementById("otherName").value = "",
					myDropdownCheckType()
		}
	})
};
/**
 * This function is used to Validating empty fields
 */
$(document).ready(function() {
	$("#myotherSkillsName").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			otherNameOnly : {
				validators : {
					notEmpty : {
						message : "Others  skillname is required."
					}
				}
			}
		}
	}), $("#myotherSkillsName").on("success.form.bv", function(a) {
		a.preventDefault(), myothernameajaxform()
	})
}), $("#closeIdName").click(function() {
	document.getElementById("skillname").value = "-1", myDropdownCheck()
}), $("#myModalOthersName").click(function() {
	document.getElementById("skillname").value = "-1", myDropdownCheck()
}), $("#closeIdName").click(function() {
	document.getElementById("skillname").value = "-1", myDropdownCheck()
});
/**
 * This function is used to get other name skills interviews
 */
var myothernameajaxform = function() {
	var a = $("input[name=otherNameOnly]").val();
	$.ajax({
		url : "getSkillotherNameInterview.do/" + a,
		async : !1,
		success : function() {
			document.getElementById("skillname").value = "-1", $(
					"#myotherSkillsName").bootstrapValidator("resetForm", !0),
					document.getElementById("otherNameOnly").value = "", $(
							"#myModalOthersName").modal("hide"),
					myDropdownCheck()
		}
	})
};