/**
 * this method is used to call while click on interview booking button
 * @param a
 */
function ShowOld(a) {
	1 == a ? window.location.href = "interviewBooking.do" : $("#myProfile")
			.modal("show")
}
/**
 * This function is used to add class to interviewlandLink id
 * 
 */
jQuery(document).ready(
		function() {
			$("#interviewlandLink").parent().addClass("active"), $(".myTmi")
					.click(function() {
						var a = $(this).attr("id");
						$(".modal-bodyTmi").load("myInterviewSummary", {
							interviewId : a.replace("my", "")
						}), $("#myModal").modal({
							show : !0
						})
					})
		});