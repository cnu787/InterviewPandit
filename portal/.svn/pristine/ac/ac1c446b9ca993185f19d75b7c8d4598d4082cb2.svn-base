<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<body>
	<style>
#datepicker1 {
	z-index: 1060;
}
</style>
	<script type="text/javascript">
		var bookedslot = null;
		var timeSlotArray = [];
		var timeSlotMap = {};
		var slotIdMap = {};
		<c:forEach items="${timeSlotList}" var="timeslot" varStatus="loop">
		timeSlotArray.push("${timeslot.startTime}");
		timeSlotMap["${timeslot.startTime}"] = "${timeslot.endTime}";
		slotIdMap["${timeslot.startTime}"] = "${timeslot.slottime}";
		</c:forEach>
		var timeSlotOptionTxt = "";
		var timeSlotOptionArray = [];
		function tmiInterviewSlotChange(element) {
			var endTimeId = 'endTime'
					+ jQuery.trim($('#' + element).attr("id")).substring(12,
							$('#' + element).attr("id").length);
			$('#' + endTimeId).val(
					timeSlotMap[$('#' + element).find(":selected").text()]);
		}
		$(document)
				.ready(
						function() {
							$("#processImg").hide();
							$('#selectDate ').datepicker({
								format : 'dd-mm-yyyy',
								startDate : '+2d',
								endDate : '+32d'
							})

							.on('changeDate', $('#selectDate').datepicker({
								format : 'dd-mm-yyyy',
								startDate : '+2d',
								endDate : '+32d'

							}),

							function(ev) {
								$(".datepicker").hide();
							});

							$(".tmiInterviewSlot")
									.change(
											function() {

												var endTimeId = 'endTime'
														+ jQuery
																.trim(
																		$(this)
																				.attr(
																						"id"))
																.substring(
																		12,
																		$(this)
																				.attr(
																						"id").length);
												$('#' + endTimeId)
														.val(
																timeSlotMap[$(
																		this)
																		.find(
																				":selected")
																		.text()]);
											});

							$('#myinterview').click(function() {
								$('#search_companyname').hide();
							});
						});
		var myPaginationFun = function() {

			$("#processImg").show();
			$('.modal-bodyreschedule').load("addInterviewSlot", {
				'selectDate' : $('#selectDate').val(),
				'pageLimit' : $("#pageLimitIds").val(),
				'addStartHour' : $('#addStartHour0').val(),
			});
			window.scrollTo(0, 0);

		}
	</script>


	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">

								<label>Select Date</label>
								<div class="input-group date " id="datepicker1">
									<input type="text" name="selectDate" readonly
										class="form-control" id="selectDate" value="${gmtdate}"
										onchange="myPaginationFun()"> <span
										class="input-group-addon" id="startdateicon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="col-sm-2 col-md-2">
							<label>From</label>
							<div class="form-group">
								<select id="addStartHour0" name="addStartHour"
									class="form-control tmiInterviewSlot"
									onchange="myPaginationFun()">
									<c:forEach var="timeslot" items="${timeSlotList}">
										<option value='${timeslot.slottime}'
											<c:if test="${timeslot.slottime==slotId}">
													selected="selected"
													</c:if>>${timeslot.startTime}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-2 col-md-2 ">
							<label>To</label>
							<div class="form-group">
								<input type="text" class="form-control" id="endTime0"
									name="endTime" size="7px" value="${slotTo}" readonly>
							</div>
						</div>



		

					</div>
					<hr>
					<c:if test="${not empty applicantAvailabeSlots}">
						<div class="row">
							<div class="col-xs-10 col-sm-6 col-md-4 form-group"
								style="text-align: left">Total No.of
								Records:${aplSlotLstCount}</div>
							<div class="col-xs-10 col-sm-6 col-md-4 form-group">
								Show <select id="pageLimitIds" onchange="myPaginationFun('#');">
									<c:forEach var="pagLst" items="${paginationList}">
										<option
											<c:if test="${pagLst.paginationlimit==pageLimits}">selected="selected"</c:if>
											value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
									</c:forEach>
								</select>
							</div>
							<div id="" class="col-xs-10 col-sm-6 col-md-4">
								<nav>
									<ul class="pagination" id="pagination-bottom"
										style="margin-top: -2px"> ${pageNav}</ul>
								</nav>
							</div>
						</div>
						<c:set var="i" value="0" />
						<table>
							<tr>
								<th class="table-hd">Evaluator Name</th>
								<!-- <th Style="color: #FFF">Evaluator Date&Time</th> -->
								<th class="table-hd">Mobile Number</th>
								<th class="table-hd">Time Zone</th>
								<th class="table-hd">Select</th>
							</tr>
							<c:forEach var="evalNameList" items="${applicantAvailabeSlots}">
								<tr class="cal-white" id="${evalNameList.userid}">
									<td style="padding-left: 18px; padding-right: 10px;">${evalNameList.ename}</td>
									<td style="padding-left: 18px; padding-right: 10px;">
										${evalNameList.mobileno}</td>
									<td style="padding-left: 18px; padding-right: 10px;">${evalNameList.timezone}</td>
									<td style="padding-left: 18px; padding-right: 10px;"><input
										class="slot-radio" type="radio" name="radioName"
										id="radioName"></td>
								</tr>
								<c:set var="i" value="${i+1}" />
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty applicantAvailabeSlots}">
						<div class="row">
							<div class="col-xs-8 col-sm-8 col-md-12"
								style="text-align: center;">
								<div class="form-group">
									<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
									are no records available.
								</div>
							</div>
						</div>
					</c:if>
					<hr>
					<input type="hidden" name="userId" id="userId" value=""> <input
						type="hidden" name="interviewSlotId" id="interviewSlotId"
						value="${interviewSlotId}">
				</div>

			</div>
		</div>
	</div>

	<br>
	<script type="text/javascript">
		$('.cal-white').click(function() {
			$("tr:even").css("background-color", "#FFFFFF");
			$("tr:odd ").css("background-color", "#f2f2f2");
			$(this).css("background-color", "#F36F21");
			bookedslot = $(this).attr('id');
			$(this).children().children("#radioName").prop("checked", true);
		});
	</script>
</body>