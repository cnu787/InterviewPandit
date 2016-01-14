<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script type="text/javascript">
	var bookedslotid = null;
	/* <c:if test="${not empty evaluatorscheduleid}">
	bookedslotid = '${evaluatorscheduleid[0].slotscheduleid}';
	</c:if> */
	var checkin;
	var reqcount = 0;
	$(document).ready(
			function() {
				$('#startDate').val('${applDate}');
				$("#processImg").hide();
				checkin = $('#startDate').datepicker({
					format : 'dd-mm-yyyy',
					startDate : '+2d',
					endDate : '+32d'

				}).on('changeDate', function(e) {
					$(this).datepicker('hide');
					if ($('#startDate').val() != '${applDate}') {
						myPaginationFun();
					}
				});

				$("tr:even").css("background-color", "#FFFFFF");
				$("tr:odd ").css("background-color", "#f2f2f2");
				$('.pageClick').click(function(e) {
					$("#processImg").show();
					var a = this.getAttribute('href');
					$('.applicantSlotDetails').load("applicantSlotbooking", {
						'startDate' : $('#startDate').val(),
						'pageNo' : a.replace("#", ""),
						'pageLimit' : '${pageLimit}'
					});
					e.preventDefault();
					window.scrollTo(0, 0);
				});

				if (bookedslotid != null) {
					$('#' + bookedslotid).css('backgroundColor', '#f36f21');
					$('#' + bookedslotid).children().children("#selectcolor")
							.prop("checked", true);
				}

				$('.cal-white').click(
						function() {
							$("tr:even").css("background-color", "#FFFFFF");
							$("tr:odd ").css("background-color", "#f2f2f2");
							$(this).css("background-color", "#F36F21");
							bookedslotid = $(this).attr('id');
							$(this).children().children("#selectcolor").prop(
									"checked", true);
						});

			});
	var myPaginationFun = function() {
		$("#processImg").show();
		$('.applicantSlotDetails').load("applicantSlotbooking", {
			'startDate' : $('#startDate').val(),
			'pageLimit' : $("#pageLimitId").val()
		});
		window.scrollTo(0, 0);

	}

	function checkAvailabilitySlots() {
		if (bookedslotid != null) {
			$("#processImg").show();
			var status = false;
			$.ajax({
				url : "updateSlotScheduleIdStatus.do/" + bookedslotid,
				async : false,
				success : function(result) {
					status = result;
				}
			});
			if (status == true) {
				nextstep();

				$("#processImg").hide();
				if ((bookedslotid != '${evaluatorscheduleid[0].slotscheduleid}')
						|| myCountDownTime == 0) {
					//$("#countdown").html('');
					var myts = (new Date()).getTime() + 10 * 60 * 1000;
					myCountDownTime = myts;
					myCountDown(myts, '${interviewId}');
				}
				$("#interviewBookingContainer").load('interviewSummary');
			} else {
				$("#processImg").hide();
				var modal = $('#myModal').modal({
					'show' : true
				});
				modal.find('.modal-title').text('Alert!');
				modal.find('.modal-body').text('Please select another slot');

			}
		} else {

			var modal = $('#myModal').modal({
				'show' : true
			});
			modal.find('.modal-title').text('Alert!');
			modal.find('.modal-body').text('Please select a slot');
		}

	}
	function requestSlot() {
		var status =null;
		
		$.ajax({
			url : "requestAdminForSlot.do/${formatDate}?reqcount=",
			async : false,
			success : function(result) {
				status=result;
				document.getElementById("reqslot").style.visibility="hidden";
				document.getElementById("reqslot1").style.visibility="visible";
			}
		});
	
		if(status==1){
			 var modal = $('#myModal').modal({
					'show' : true
				});
				modal.find('.modal-title').text('Alert!');
				modal.find('.modal-body').text('Thanking You ');
				document.getElementById("reqslot").style.visibility="hidden";
				document.getElementById("reqslot1").style.visibility="visible";
		}
		

	}
</script>
<style>
.table-hd {
	color: #FFF;
	background: #59574B;
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
				style="text-align: left">Total No.of
				Records:${aplSlotLstCount}</div>
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
				Show <select id="pageLimitId" onchange="myPaginationFun('#');">
					<c:forEach var="pagLst" items="${paginationList}">
						<option
							<c:if test="${pagLst.paginationlimit==pageLimit}">selected="selected"</c:if>
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

		<table>
			<tr>
				<th class="table-hd">Available Slots</th>
				<th class="table-hd">Select</th>
			</tr>
			<c:forEach var="slotList" items="${applicantAvailabeSlots}">
				<tr class="cal-white" id="${slotList.slotscheduleid}">
					<td style="padding-left: 18px; padding-right: 10px;">${slotList.applDate}(${slotList.appTimezone})<br>
						${slotList.applTime}
					</td>
					<td style="padding-left: 18px; padding-right: 10px;"><input
						class="slot-radio" type="radio" name="selectcolor"
						id="selectcolor" value="option"></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty applicantAvailabeSlots}">
		<div class="row">
			<div class="col-xs-8 col-sm-8 col-md-12" style="text-align: center;">
				<div class="form-group">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
					are no slots available.
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-8 col-sm-8 col-md-12" style="text-align: center;">
				<div class="form-group">
				<div id="reqslot">
					<div id="reqslot">Click <a href="#" onClick="requestSlot();">here </a>to request
					Admin </div> 	
					 <div id="reqslot1" style="visibility:hidden">Click here to request	Admin </div>
				</div></div>
			</div>
		</div>
	</c:if>


</body>
