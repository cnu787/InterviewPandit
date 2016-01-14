/**
 * This function is used to display technical interview  skills page 
 */
function interviewTechSkills() {
	$('#interviewmodification').modal('show');
	$('#interviewmodificationok').click(function(){
		$('#interviewmodification').modal('hide');
		currentstep("2"), $("#interviewBookingContainer").load("interviewTechSkills");
	});
	
}
/**
 * This function is used to display interview Roles  page 
 */
function interviewBooking() {
	$('#interviewmodification').modal('show');
	$('#interviewmodificationok').click(function(){
		$('#interviewmodification').modal('hide');
		currentstep("1"), $("#interviewBookingContainer").load("interviewRole");
	});
	
}
/**
 * This function is used to display slot booking page 
 */
function interviewSlotBooking() {
	$('#interviewmodification').modal('show');
	$('#interviewmodificationok').click(function(){
		$('#interviewmodification').modal('hide');
		currentstep("3"), $("#interviewBookingContainer").load("slotBooking");
	});
	
}
/**
 * This function is used to display slot booking page 
 */
jQuery(document).ready(function() {
	$("#back").click(function() {		
		$('#interviewmodification').modal('show');
		$('#interviewmodificationok').click(function(){
			$('#interviewmodification').modal('hide');
			$("#processImg").show();
			backstep(),$("#interviewBookingContainer").load("slotBooking");
		});
	})
}), $(document).ready(function() {
/**
 * This function is used to Validating empty fields 
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