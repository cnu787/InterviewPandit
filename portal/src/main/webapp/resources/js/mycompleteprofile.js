/**
 * This function is used to form submition
 */
function submitMe() {
			window
					.open(
							"",
							"newwindow",
							"menubar=no,location=no,resizable=no,scrollbars=yes,status=yes,top=500, left=500, width=1000, height=600"),
			document.forms.testForm.submit()
}
/**
 * This function is used to display profile about me page
 */
jQuery(document).ready(function() {
	$("#profilelink").parent().addClass("active"), $("#back").click(function() {
		$("#mainProfileContainer").load("profileAboutMe")
	})
});