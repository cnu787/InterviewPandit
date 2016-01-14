/**
 * This Function is used to load skill map 
 * 
 */
function Skillmap() {
	$("#mainProfileContainer").load("yourpreference")
}
/**
 * This Function is used to display completeprofile view
 */
function Skillmap1() {
	window.location.href = "myCompleteProfile.do"
}
/**
 * This Function is used to delete skill
 * @param a
 */
function deleteSkill(a) {	
	recordnumber = a
}
$("#profilelink").parent().addClass("active");
var recordnumber = 0;