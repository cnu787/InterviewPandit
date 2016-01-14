/**
 * This function is used to validating empty fields
 */
$(document)
		.ready(
				function() {
							$("#registerForm")
									.bootstrapValidator(
											{
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
																message : "Enter your First Name."
															}
														}
													},
													lastName : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Enter your Last Name."
															}
														}
													},
													chooseyourUsername : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Choose your Screen Name."
															}
														}
													},
													phoneNumber : {
														validators : {
															notEmpty : {
																message : "Mobile number is required."
															},
															integer : {
																message : "Enter only numbers."
															}
														}
													},
													chooseyourRole : {
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
													password : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Password is not valid ."
															},
															notEmpty : {
																message : "Password Cannot be Empty."
															},
															securePassword : {
																message : "Password is not valid."
															}
														}
													},
													confirmPassword : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Password is not valid."
															},
															notEmpty : {
																message : "Confirm Password Cannot Be Empty."
															},
															securePassword : {
																message : "Password is not valid."
															},
															identical : {
																field : "password",
																message : "Fields do not match."
															}
														}
													}
												}
											}),
							$("#loginForm")
									.bootstrapValidator(
											{
												framework : "bootstrap",
												feedbackIcons : {
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													j_username : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Enter your email address."
															},
															regexp : {
																regexp : "^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$",
																message : "Please enter a valid Email Address."
															}
														}
													},
													j_password : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Please enter a valid password."
															},
															notEmpty : {
																message : "Enter your Password."
															}
														}
													}
												}
											}),
							$("#forgotForm")
									.bootstrapValidator(
											{
												feedbackIcons : {
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													emailid : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Please enter an email address."
															},
															regexp : {
																regexp : "^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$",
																message : " Please enter a valid Email Address."
															}
														}
													}
												}
											}),
							$("#referrerForm")
									.bootstrapValidator(
											{
												feedbackIcons : {
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													emailid : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Please enter an email address."
															},
															regexp : {
																regexp : "^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$",
																message : " Please enter a valid Email Address."
															}
														}
													},
													chooseyourRole : {
														trigger : "blur",
														validators : {
															notEmpty : {
																message : "Choose any Role."
															}
														}
													}
												}
											}),
							$("#changepasswordform")
									.bootstrapValidator(
											{
												feedbackIcons : {
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													currentPassword : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Please enter a valid password."
															},
															notEmpty : {
																message : "Enter your Password."
															}
														}
													},
													newPassword : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Please enter a valid password."
															},
															notEmpty : {
																message : "Enter your Password."
															}
														}
													},
													confirmPassword : {
														trigger : "blur",
														validators : {
															regexp : {
																regexp : /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/,
																message : "Password is not valid."
															},
															notEmpty : {
																message : "Confirm Password Cannot Be Empty."
															},
															securePassword : {
																message : "Password is not valid."
															},
															identical : {
																field : "newPassword",
																message : "Fields do not match."
															}
														}
													}
												}
											});
							/**
							 * This function is used to converting lower case
							 */
					$('#loginForm').on(
							'success.form.bv',
							function(e) {									
								var strMD5 = CryptoJS.MD5($('#j_username').val().toLowerCase()
										+ $('#j_password').val());
								$('#j_password').val(strMD5);
							});
					$('#registerForm').on(
							'success.form.bv',
							function(e) {
								var strMD5 = CryptoJS.MD5($('#emailaddress').val().toLowerCase()
										+ $('#password').val());
								$('#password').val(strMD5);
								strMD5 = CryptoJS.MD5($('#emailaddress').val().toLowerCase()
										+ $('#confirmPassword').val());
								$('#confirmPassword').val(strMD5);
							});

				});