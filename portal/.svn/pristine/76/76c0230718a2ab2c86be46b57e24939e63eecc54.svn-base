/**
 * This function is used to bootstrap validation input fields
 * 
 */
var myflag=0;
$(document).ready(function() {
	$("#myprofile").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			 companyname : {
				validators : {
					notEmpty : {
						message : "Company name is required."
					}
				}
			}, 			
			position : {
				validators : {
					notEmpty : {
						message : "Position is required."
					}
				}
			},
			location : {
				validators : {
					notEmpty : {
						message : "Location is required."
					}
				}
			},
			month : {
				validators : {
					callback : {
						message : "From month is required.",
						callback : function(a, b) {
							var d = b.getFieldElements("month").val();
							return -1 != d
						}
					}
				}
			},
			year : {
				validators : {
					callback : {
						message : "From year is required.",
						callback : function(a, b) {
							var d = b.getFieldElements("year").val();
							return -1 != d
						}
					}
				}
			},
			expmonth : {
				validators : {
					callback : {
						message : "To month is required.",
						callback : function(a, b) {
							var d = b.getFieldElements("expmonth").val();
							return -1 != d
						}
					}
				}
			},
			expryear : {
				validators : {
					callback : {
						message : "To year is required.",
						callback : function(a, b) {
							var d = b.getFieldElements("expryear").val();
							return -1 != d
						}
					}
				}
			}
		}
	}), $("#myprofile").on("success.form.bv", function(a) {
		a.preventDefault(),0==myflag && myajaxform()
	})
});