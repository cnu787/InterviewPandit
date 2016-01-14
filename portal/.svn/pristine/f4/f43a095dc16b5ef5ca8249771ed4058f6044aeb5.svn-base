/**
 *  this method is used to load tmiadmininterviewRole in interviewBookingContainer div
 */
function interviewBooking() {
	currentstep("1"), $("#interviewBookingContainer").load("tmiadmininterviewRole")

}
/**
 *  this method is used to load admintmislotBooking in interviewBookingContainer div
 */
function interviewSlotBooking() {
	currentstep("3"), $("#interviewBookingContainer").load("admintmislotBooking")
}
jQuery(document).ready(function() {
	$("#back").click(function() {
		$("#processImg").show();
		backstep(),
		$("#interviewBookingContainer").load("admintmislotBooking")
	})
}), $(document).ready(function() {
	/**
	 *  this method is used to bootstrap Validator for given input fields names like terms
	 * 
	 */
	$("#makePayment").bootstrapValidator({
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		},
		fields : {
			terms : {
				validators : {
					choice : {
						min : 1,
						message : "Please accept the terms & conditions."
					}
				}
			}
		}
	})
});