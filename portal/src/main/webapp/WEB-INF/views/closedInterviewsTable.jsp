<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function() {

		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');		
			$('.close_interviews').load("tmiAdmclosedInterviewsDetailsBasedonSearch", {
				'dashboard' : '${dashboard}',
				'evaluatorName':'${enterstring}',
				'mydays' : '${daycount}',
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

	});
	var myPaginationFun = function() {	
		$('.close_interviews').load("tmiAdmclosedInterviewsDetailsBasedonSearch", {
			'dashboard' : '${dashboard}',
			'evaluatorName':'${enterstring}',
			'mydays' : '${daycount}',
			'pageLimit' : $("#pageLimitId").val()
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
	<c:if test="${not empty closedInterviewsData}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${closedInterviewsListCount}</div>
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

			<c:forEach var="closeLst" items="${closedInterviewsData}">
				<tr>
					<td style="padding-left: 12px; padding-right: 10px;">
						<button type="button" class="ev-btn" style="background: #FFF;"></button>
					<td style="padding-left: 18px; padding-right: 10px;" class="myTmi"
						id="my${closeLst.interviewid}_${closeLst.interviewtmiid}"><a
						href="#">${closeLst.interviewtmiid}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${closeLst.appuserid}"><a href="#">
							${closeLst.ApplicantName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="evalid"
						id="user${closeLst.evaluserid}"><a href="#">${closeLst.EvaluatorName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${closeLst.interviewerrolename}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${closeLst.date}
						<br> ${closeLst.timeslot}
					</td>
					<td style="padding-left: 18px; padding-right: 10px;">${closeLst.status}</td>

				</tr>
			</c:forEach>
		</table>
		<br>
	</c:if>
	<c:if test="${empty closedInterviewsData}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
			are no Closed Interviews
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
