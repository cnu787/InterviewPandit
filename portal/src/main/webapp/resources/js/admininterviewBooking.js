/**
 * on load calling tmiadmininterviewRole method return information load interviewBookingContainer div
 *
 */
jQuery(document).ready(
		function() {
			$("#interviewlandLink").parent().addClass("active"),
					Window.onload = $("#interviewBookingContainer").load(
							"tmiadmininterviewRole");
		});
var myCountDownTime = 0, myCountDown = 
/**
 * this method is used to show count down while select slot
 * @param a
 * @param b
 */
function(a, b) {
	$("#countdown").countdown(
			{
				interviewId : b,
				timestamp : a,
				callback : function(a, b) {
					var c = "";
					c += a + " minute" + (1 == a ? "" : "s") + " and ", c += b
							+ " second" + (1 == b ? "" : "s") + " <br />";
				}
			});
};
$("#mySlotTimeoutModal").on("show.bs.modal", function() {
});