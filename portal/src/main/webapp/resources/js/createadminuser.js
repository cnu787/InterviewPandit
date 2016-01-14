/**
 * This function is used to bootstrap validation input fields
 * 
 */
$(document).ready(function() {
	$("#myadminuser").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			firstName : {
				trigger : "blur",
				validators : {
					notEmpty : {
						message : "Enter First Name."
					}
				}
			},
			lastName : {
				trigger : "blur",
				validators : {
					notEmpty : {
						message : "Enter  Last Name."
					}
				}
			},
			phoneNumber : {
				trigger : "blur",
				validators : {
					notEmpty : {
						message : "Enter  Mobile Number."
					},
					integer: {
                        message: 'Enter only numbers.'
                    }
				}
			},
			userRole : {
				trigger : "blur",
				validators : {
					notEmpty : {
						message : "Choose any Role."
					}
				}
			},
			emailaddress : {
				trigger : "blur",
				validators : {
					notEmpty : {
						message : "Email address Cannot be Empty."
					},
					regexp : {
						regexp : "^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$",
						message : " Please enter a valid Email Address."
					}
				}
			},
		}
	})
});