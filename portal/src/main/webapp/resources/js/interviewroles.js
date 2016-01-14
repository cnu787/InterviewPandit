/**
 * This function is used to validating empty fields
 */
$(document).ready(function() {
	$("#addotherinterviewrole").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			otherinterviewrole : {
				validators : {
					notEmpty : {
						message : "Others interviewer role is required."
					}
				}
			}
		}
	}), $("#addotherinterviewrole").on("success.form.bv", function(a) {
		a.preventDefault(), myotherinterviewajaxform()
	})
}), $("#myotherinterviewrole").click(
		function() {
			document.getElementById("interviewrole").value = "-1",
					myDropdownInterviewCheck()
		}), $("#closeinterviewrole").click(
		function() {
			document.getElementById("interviewrole").value = "-1",
					myDropdownInterviewCheck(), $("#myotherinterviewrole")
							.modal("hide")
		}), $("#closeinterviewId").click(
		function() {
			document.getElementById("interviewrole").value = "-1",
					myDropdownInterviewCheck(), $("#myotherinterviewrole")
							.modal("hide")
		});
/**
 * This function is used to get other role type interviews
 */
var myotherinterviewajaxform = function() {
	var a = $("input[name=otherinterviewrole]").val();
	$.ajax({
		url : "getotherInterview.do/" + a,
		async : !1,
		success : function() {
			document.getElementById("interviewrole").value = "-1", $(
					"#addotherinterviewrole").bootstrapValidator("resetForm",
					!0),
					document.getElementById("otherinterviewrole").value = "",
					$("#myotherinterviewrole").modal("hide"),
					myDropdownInterviewCheck()
		}
	})
};
