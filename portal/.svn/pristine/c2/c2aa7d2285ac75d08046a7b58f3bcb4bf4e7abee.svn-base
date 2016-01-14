/**
 * Validating empty fields 
 */
$(document).ready(function() {
	$("#myedu").bootstrapValidator({
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
			},
			otherSkillType : {
				validators : {
					notEmpty : {
						message : 'Field-others in skilltype is required'
					}
				}
			},
			otherSkillName : {
				validators : {
					notEmpty : {
						message : 'Field-others in skillname is required'
					}
				}
			}
		}
	}), $("#myedu").on("success.form.bv", function(a) {
		a.preventDefault(), 0 == myflag && myajaxform()
	})
});