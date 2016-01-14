/**
 *  This function is used to check drop down value selected or not 
 */
function myDropdownCheckType() {
	$("#myedu").bootstrapValidator("enableFieldValidators", "skilltype", !1),
			$("#myedu").bootstrapValidator("enableFieldValidators",
					"skilltype", !0), $("#myedu").bootstrapValidator(
					"validateField", "skilltype");
	var a = "skillname", b = document.getElementById(a);
	removeOptions(b), myDropdownCheck()
}
/**
 *  This function is used to add skills
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "addSkills",
		type : "GET",
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
	$("#myedu").bootstrapValidator("enableFieldValidators", "skillname", !1),
			$("#myedu").bootstrapValidator("enableFieldValidators",
					"skillname", !0), $("#myedu").bootstrapValidator(
					"validateField", "skillname")
};
/**
 * validating empty fields
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
}), $("#closeId").click(
		function() {
			document.getElementById("skilltype").value = "-1",
					myDropdownCheckType(), $("#myModal").modal("hide")
		}), $("#myModal").click(function() {
	document.getElementById("skilltype").value = "-1", myDropdownCheckType()
}), $("#othercloseId").click(
		function() {
			document.getElementById("skilltype").value = "-1",
					myDropdownCheckType(), $("#myModal").modal("hide")
		});
/**
 *  This function is used to get other type skills
 */
var myotherajaxform = function() {
	var a = $("input[name=otherTypeName]").val(), b = $("input[name=otherName]")
			.val();
	$.ajax({
		url : "getSkillotherTypes.do/" + a + "/" + b,
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
 * validating empty fields
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
 *  This function is used to get other skill names
 */
var myothernameajaxform = function() {
	var a = $("input[name=otherNameOnly]").val();
	$.ajax({
		url : "getSkillotherName.do/" + a,
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