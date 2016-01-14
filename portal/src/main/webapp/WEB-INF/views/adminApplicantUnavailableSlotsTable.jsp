<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$(document).ready(function() {
		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');
			$('.aplcnt_unavailSlot').load("getApplicantUnavailableSlots", {
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

		$(".appTmi").click(function() {
			var a = $(this).attr("id");
			$(".modal-bodytmi").load("admUnAvailableslotInterviewSummary", {
				'interviewId' : a.replace("my", "")
			}), $("#myModaltmi").modal({
				show : !0
			})
		});
	});
	var myPaginationFun = function() {
		$('.aplcnt_unavailSlot').load("getApplicantUnavailableSlots", {
			'pageLimit' : $("#pageLimitId").val()
		});
		window.scrollTo(0, 0);

	}
</script>
<style>
.interview-tmiid {
	width: 728px;
}
</style>
<body>
	<c:if test="${not empty applicantUnavailSlotList}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${applcntSlotCount}</div>
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
				<th Style="color: #FFF">Name</th>
				<th Style="color: #FFF">Email</th>
				<th Style="color: #FFF">Phone</th>
				<th Style="color: #FFF">InterviewId</th>
				<th Style="color: #FFF">Skills</th>
				<th Style="color: #FFF">ScheduleDate</th>


			</tr>

			<c:forEach var="appList" items="${applicantUnavailSlotList}">
				<tr>
					<td>${appList.name}</td>
					<td>${appList.emailid}</td>
					<td>${appList.mobileno}</td>
					<td class="appTmi" id="my${appList.interviewid}"><a href="#">${appList.interviewtmiid}</a>
					<td>${appList.skillname}</td>
					<td>${appList.scheduledDate}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${ empty applicantUnavailSlotList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>

	<div class="modal" id="myModaltmi" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">

		<div class="modal-dialog interview-tmiid">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Interview Summary</h4>
				</div>
				<div class="modal-bodytmi"></div>
				<div class="modal-footer">
					<div class="col-md-4 col-md-push-8">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>