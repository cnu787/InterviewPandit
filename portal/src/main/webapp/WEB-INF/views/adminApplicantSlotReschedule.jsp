<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script type="text/javascript">
	var bookedslotid = null;
	var previd = null;
	var myschedulestatus='${reschedule}';
	$(document).ready(function() {
		$("#processImg").hide();
		$('#startDate').val('${applDate}');
		$('#startDate').datepicker({
			format : 'dd-mm-yyyy',
			startDate : '+2d',
			endDate : '+32d'

		}).on('changeDate', function(e) {
			$(this).datepicker('hide');
			if ($('#startDate').val() != '${applDate}') {
				myPaginationFun();
			}
		});
		$('.pageClick').click(function(e) {
			$("#processImg").show();
			var a = this.getAttribute('href');
			if(myschedulestatus==0){
			$('.modal-bodyreschedule').load("admApplicantSlotReschedule", {
				'startDate' : $('#startDate').val(),
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimits}',
				'interviewId' : '${interviewId}',
				'userid' : '${userid}',
				'startTimestamp' : '${startTimestamp}',
				'reschedule':'${reschedule}'
			});
			}else if(myschedulestatus==1){
				$('.modal-bodyreschedule').load("admRescheduleApplicantInterview", {
					'startDate' : $('#startDate').val(),
					'pageNo' : a.replace("#", ""),
					'pageLimit' : '${pageLimits}',
					'interviewId' : '${interviewId}',
					'userid' : '${userid}',
					'startTimestamp' : '${startTimestamp}',
					'reschedule':'${reschedule}'
				});	
				
			}
			e.preventDefault();
			window.scrollTo(0, 0);
		});

		$('.cal-white').click(function() {
			$(".table-cls tbody tr").removeClass('on');
			$(this).toggleClass('on');
			bookedslotid = $(this).attr('id');
			$(this).children().children("#evalatorRad").prop("checked", true);
		});

	});
	var myPaginationFun = function() {
		$("#processImg").show();
		if(myschedulestatus==0){
		$('.modal-bodyreschedule').load("admApplicantSlotReschedule", {
			'startDate' : $('#startDate').val(),
			'pageLimit' : $("#pageLimitIds").val(),
			'interviewId' : '${interviewId}',
			'userid' : '${userid}',
			'startTimestamp' : '${startTimestamp}',
			'reschedule':'${reschedule}'
		});
		}else if(myschedulestatus==1){
			$('.modal-bodyreschedule').load("admRescheduleApplicantInterview", {
				'startDate' : $('#startDate').val(),
				'pageLimit' : $("#pageLimitIds").val(),
				'interviewId' : '${interviewId}',
				'userid' : '${userid}',
				'startTimestamp' : '${startTimestamp}',
				'reschedule':'${reschedule}'
			});
		}
		window.scrollTo(0, 0);

	}
</script>
<style>
#datepicker {
	z-index: 1060;
}

.tmilejend {
	height: 16px;
	width: 16px;
	margin-right: 10px;
}

.table-hd {
	color: #FFF;
	background: #59574B;
}

.slot-green {
	background-color: #00CC99 !important;
	color: #ffffff !important;
}

.slot-orange {
	background-color: #F36F21 !important;
	color: #ffffff !important;
}

.slot-blue {
	background-color: #0094da !important;
	color: #ffffff !important;
}

.cal-white.on {
	background-color: #ededb2 !important;
	color: #000000 !important;
}

@media ( min-width : 1200px) {
	#pagination-bottom {
		float: right;
	}
}
</style>
<body>
	<div class="row">
		<div class="col-xs-10 col-sm-6 col-md-3 form-group"
			style="text-align: left">Total No.of Records:${aplSlotLstCount}</div>
		<div class="col-xs-8 col-sm-4 col-md-3 form-group">
			<div class="input-group date " id="datepicker">
				<input type="text" name="startDate" readonly class="form-control"
					id="startDate"> <span class="input-group-addon"
					id="startdateicon"> <span
					class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
		</div>
		<div class="col-xs-10 col-sm-6 col-md-3 form-group">
			Show <select id="pageLimitIds" onchange="myPaginationFun('#');">
				<c:forEach var="pagLst" items="${paginationList}">
					<option
						<c:if test="${pagLst.paginationlimit==pageLimits}">selected="selected"</c:if>
						value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
				</c:forEach>
			</select>
		</div>
		<div id="" class="col-xs-10 col-sm-6 col-md-3">
			<nav>
				<ul class="pagination" id="pagination-bottom"
					style="margin-top: -2px">${pageNav}
				</ul>
			</nav>
		</div>
	</div>
	<c:if test="${not empty applicantAvailabeSlots}">
		<table class="table-cls">
			<tr>
				<th class="table-hd">Applicant<br>TimeSlots
				</th>
				<th class="table-hd">Evaluator<br>Name
				</th>
				<th class="table-hd">Evaluator<br>Mobile No
				</th>
				<th class="table-hd">Evaluator<br> EmailId
				</th>
				<th class="table-hd">Evaluator<br>TimeSlots
				</th>


				<th class="table-hd">Select</th>
			</tr>
			<c:forEach var="slotList" items="${applicantAvailabeSlots}">
				<tr
					class="cal-white  <c:if test='${slotList.statustype==1}'>slot-green</c:if> <c:if test='${slotList.statustype==2}'>slot-orange</c:if><c:if test='${slotList.statustype==3}'>slot-blue</c:if>"
					id="${slotList.slotscheduleid}">
					<td style="padding-left: 5px; padding-right: 2px;">${slotList.applDate}(${slotList.appTimezone})<br>
						${slotList.applTime}
					</td>
					<td>${slotList.evalName}</td>
					<td>${slotList.evalMobile}</td>
					<td>${slotList.evalEmail}</td>
					<td style="padding-left: 5px; padding-right: 2px;">${slotList.evalDate}(${slotList.evalTimezone})<br>
						${slotList.evalTime}
					</td>

					<td><input class="slot-radio" type="radio" name="evalatorRad"
						id="evalatorRad"
						value="${slotList.slotscheduleid}-${slotList.evalId}"></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<div class="row">

			<div class="col-md-3 ">
				<img class="tmilejend"
					src="resources/images/icon_eval_interview_accepted.png">Same
				date&time
			</div>
			<div class="col-md-4">
				<img class="tmilejend"
					src="resources/images/icon_video_audio_clip.png">Same
				date&time but slot not open
			</div>
			<div class="col-md-3">
				<img class="tmilejend"
					src="resources/images/icon_evaluators_feedback.png">Different
				date&time
			</div>
			<div class="col-md-2">
				<img class="tmilejend"
					src="resources/images/icon_applicant_waiting _payment.png">Slot
				selected
			</div>
		</div>
	</c:if>
	<c:if test="${empty applicantAvailabeSlots}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>

</body>
