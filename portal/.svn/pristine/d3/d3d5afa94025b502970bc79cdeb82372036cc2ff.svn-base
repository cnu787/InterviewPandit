/**
 * This function is used to bootstrap validation input fields
 * 
 */
$(document).ready(function() {
	$("#myedu").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {			
			graduateyears : {
				validators : {
					callback : {
						message : "Please select listed Graduated year.",
						callback : function(a, b) {
							var d = b.getFieldElements("graduateyears").val();
							return -1 != d
						}
					}
				}
			},
			graduatemonth : {
				validators : {
					callback : {
						message : "Please select listed Graduated month.",
						callback : function(a, b) {
							var d = b.getFieldElements("graduatemonth").val();
							return -1 != d
						}
					}
				}
			},
			degreename : {				
				validators : {
					notEmpty : {
						message : "Degree name is required."
					}					
				}
			},
			study : {
				validators : {
					notEmpty : {
						message : "Field of study is required."
					}
				}
			},
			universityname : {
				validators : {
					notEmpty : {
						message : "university name is required."
					}					
				}
			}
		}
	}), $("#myedu").on("success.form.bv", function(a) {
		a.preventDefault(), myajaxform()
	})
});