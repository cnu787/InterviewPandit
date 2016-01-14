<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function() {

		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');			
			$('.cancel_interviews').load("tmiAdmcancelInterviewsDetailsBasedonSearch", {
				'mydays' : '${daycount}',
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}',
				'dashboard':'${dashboard}',
				'evaluatorName':'${enterstring}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

	});
	var myPaginationFun = function() {		
		$('.cancel_interviews').load("tmiAdmcancelInterviewsDetailsBasedonSearch", {
			'mydays' : '${daycount}',
			'pageLimit' : $("#pageLimitId").val(),
			'dashboard':'${dashboard}',
			'evaluatorName':'${enterstring}'
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
	<c:if test="${not empty cancelInterviewsData}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${cancelInterviewsListCount}</div>
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
				<th></th>
				<th Style="color: #FFF">Interview ID </th>
				<th Style="color: #FFF">Applicant Name </th>
				<th Style="color: #FFF">Evaluator Name </th>
				<th Style="color: #FFF">Interviewers Role </th>
				<th Style="color: #FFF">Time Slot </th>
				<th Style="color: #FFF">Status </th>
			</tr>

			<c:forEach var="cancelLst" items="${cancelInterviewsData}">
				<tr>
					<td style="padding-left: 12px; padding-right: 10px;">
						<button type="button" class="ev-btn" style="background: #FFF;"></button>
					<td style="padding-left: 18px; padding-right: 10px;" class="myTmi"
						id="my${cancelLst.interviewid}_${cancelLst.interviewtmiid}"><a
						href="#">${cancelLst.interviewtmiid}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${cancelLst.appuserid}"><a href="#">
							${cancelLst.ApplicantName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="evalid"
						id="user${cancelLst.evaluserid}"><a href="#">${cancelLst.EvaluatorName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${cancelLst.interviewerrolename}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${cancelLst.date}
						<br> ${cancelLst.timeslot}
					</td>
					<td style="padding-left: 18px; padding-right: 10px;">${cancelLst.status}</td>

				</tr>
			</c:forEach>
		</table>
		<br>
	</c:if>
	<c:if test="${empty cancelInterviewsData}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
			are no Cancelled Interviews
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
								var profile = "Interview Pandit ID : ";
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
			$(".evalid").click(function() {
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
