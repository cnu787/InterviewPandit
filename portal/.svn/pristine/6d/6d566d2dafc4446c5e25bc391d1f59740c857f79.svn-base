<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script type="text/javascript">
	$(document).ready(function() {

		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');
			$('.register_users').load("tmiAdmGetallRegisteredUsers", {
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}',
				'emailId' : '${EmailId}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

	});
	var myPaginationFun = function() {
		$('.register_users').load("tmiAdmGetallRegisteredUsers", {
			'pageLimit' : $("#pageLimitId").val(),
			'emailId' : '${EmailId}'
		});
		window.scrollTo(0, 0);

	}
</script>
<style>
.table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}

.modal-dialog {
	width: 728px;
	margin: 30px auto;
}
</style>
<body>
	<c:if test="${not empty registeredUserList}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${registeredListCount}</div>
			<div class="col-md-4" style="margin-top: -4px">
				Show <select id="pageLimitId" onchange="myPaginationFun('#');">
					<c:forEach var="pagLst" items="${paginationList}">
						<option
							<c:if test="${pagLst.paginationlimit==pageLimit}">selected="selected"</c:if>
							value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
					</c:forEach>
				</select>
			</div>
			<div id="" class="col-md-4" style="text-align: right">
				<nav>
					<ul class="pagination" style="margin-top: -2px">${pageNav}</ul>
				</nav>
			</div>
		</div>
		<table>
			<tr>
				<th Style="color: #FFF">EmailId</th>
				<th Style="color: #FFF">Full Name</th>
				<th Style="color: #FFF">Industry Name</th>
				<th Style="color: #FFF">User Type</th>
				<th Style="color: #FFF">Created Date</th>
			</tr>

			<c:forEach var="usersLst" items="${registeredUserList}">
				<tr>
					<td style="padding-left: 18px; padding-right: 10px;">${usersLst.emailid}</td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${usersLst.userid}"><a href="#">
							${usersLst.fullname}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${usersLst.industryname}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${usersLst.usertype}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${usersLst.createddate}</td>

				</tr>
			</c:forEach>
		</table>
		<br>
	</c:if>
	<c:if test="${empty registeredUserList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;No
			records available.
		</div>
	</c:if>
	<hr>
	<script>
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(
							".myTmi").click(
							function() {

								var a = $(this).attr("id");
								var res = a.split("_");
								a = res[0];
								var profile = "TMI ID : ";
								var profileName = profile.concat(res[1]);
								$(".nameOf").text(profileName);

								$(".modal-bodyTmi").load(
										"tmiAdmGetApplicantDetailsApplicant", {
											'interviewId' : a.replace("my", "")
										}), $("#myModal").modal({
									show : !0
								})

							})
				});
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".appid").click(function() {
				var a = $(this).attr("id");
				$(".modal-bodyprofile").load("usersCompleteProfile", {
					'userId' : a.replace("user", "")
				}), $("#myModalprofile").modal({
					show : !0
				})
			});
		});
	</script>

</body>
