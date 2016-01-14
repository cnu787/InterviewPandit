<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- shyam added on 12-05-2015 - Start -->
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<!-- shyam added on 12-05-2015 - End -->
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<link rel="stylesheet" href="resources/css/internalcalender.css">
<script src="resources/js/intlTelInput.js"></script>
<script type="text/javascript">
<fmt:parseDate value="${currentGmtTime}" var="parsedEmpDate" 
    pattern="yyyy-MM-dd H:m:s" />
<fmt:formatDate var="dateFormat" pattern="MMM dd, yyyy H:m:ss" 
    value="${parsedEmpDate}" /> 
	var currentGmtTOLocal=convertGmtTOLocal('${dateFormat}');  
	var timeSlotArray = [];
	var timeSlotMap = {};
	var slotIdMap={};
	var applicantNamesArray=[];
	var months = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
	var weekday = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" ];
	<c:forEach items="${timeSlotList}" var="timeslot" varStatus="loop">
	timeSlotArray.push("${timeslot.startTime}");
	timeSlotMap["${timeslot.startTime}"] = "${timeslot.endTime}";
	slotIdMap["${timeslot.startTime}"] = "${timeslot.slotid}";
	</c:forEach>
	var timeSlotOptionTxt = "";
	var timeSlotOptionArray = [];
	<c:forEach var="timeslot" items="${timeSlotList}">
	timeSlotOptionTxt += "<option  value='${timeslot.slotid}'>${timeslot.startTime}</option>";
	timeSlotOptionArray['${timeslot.slotid}'] = "<option  value='${timeslot.slotid}'>${timeslot.startTime}</option>";
	</c:forEach>
	var count = 0;
	var monthWeekBtn = 0;
	var dataArray = [];
	$(document)
			.ready(
					function() {
						$("#calenderlink").parent().addClass("active");
						$('#addStartHour0').html(timeSlotOptionTxt);	
						$('#endTime0').val('1 AM');
						calendarValidator();				
						myweekView(count);
						//previous week/month button
						$("#previousday").click(function() {							
							count--;
							if (monthWeekBtn == 0) {
								if (count > -1 && count < 5){
									myweekView(count);
								}
								else{
									var modal = $('#myModal').modal({'show':true});
									  modal.find('.modal-title').text('Alert!');
									  modal.find('.modal-body').text('Sorry no data available');
									  count++;
								}
							} else {
								if (count > -1 && count < 2){
									monthView(count);
								}
								else{	
									
									var modal = $('#myModal').modal({'show':true});
									 modal.find('.modal-title').text('Alert!');
									  modal.find('.modal-body').text('Sorry no data available.');
									  count++;
								}

							}
						});
						//current week/month button 
						$("#current").click(function() {
							count = 0;
							if (monthWeekBtn == 0) {
								myweekView(count);
							} else {
								monthView(count);
							}
						});
						//next week/month  button 
						$("#nextday").click(function() {
							count++;
							if (monthWeekBtn == 0) {
								if (count > -1 && count < 5){
									myweekView(count);
								}
								else{
									count--;								
									var modal = $('#myModal').modal({'show':true});
									  modal.find('.modal-title').text('Alert!');
									  modal.find('.modal-body').text('Sorry no data available.');
								}
							} else {
								if (count > -1 && count < 2){
									monthView(count);}
								else{
									count--;								
									var modal = $('#myModal').modal({'show':true});
									 modal.find('.modal-title').text('Alert!');
									  modal.find('.modal-body').text('Sorry no data available.');
								}

							}
						});

						//display weekView 
						$("#weekView")
								.click(
										function() {
											$("#weekImg")
													.attr("src",
															"resources/images/icon_week_view_active.png");
											$("#monthImg")
													.attr("src",
															"resources/images/icon_month_view_inactive.png");
											count = 0;
											monthWeekBtn = 0;
											myweekView(count);
										});
						//display monthView
						$("#monthView")
								.click(
										function() {
											$("#weekImg")
													.attr("src",
															"resources/images/icon_week_view_inactive.png");
											$("#monthImg")
													.attr("src",
															"resources/images/icon_month_view_active.png");
											count = 0;
											monthWeekBtn = 1;
											monthView(count);
										});

						//change timeslot end time
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
															timeSlotMap[$(this)
																	.find(
																			":selected")
																	.text()]);
										});
					});

	function populateWeekSlots(weekIncr) {
		dataArray = [];
		applicantNamesArray =[];
		$.each(timeSlotArray, function(key, value) {
			dataArray.push(new Array(0, 0, 0, 0, 0, 0, 0));
			applicantNamesArray.push(new Array(0, 0, 0, 0, 0, 0, 0));
		});
		var jsonweektext;
		$.ajax({
			url : "getScheduleForWeek.do/" + weekIncr + "/"
					+ '${interviewerId}',
			async : false,
			success : function(result) {
				jsonweektext = result;
			}
		});
		for ( var key in jsonweektext) {
			if (jsonweektext.hasOwnProperty(key)) {					
				if(jsonweektext[key].status==0){					
					dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 1;
				}else if(jsonweektext[key].status==1){
					dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 5;
				} else if(jsonweektext[key].status==2){
					dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 2;
					applicantNamesArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = jsonweektext[key].name==null? "Bulk" :jsonweektext[key].name;
				}else if(jsonweektext[key].status==3){
					dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 3;
				} else if(jsonweektext[key].status==4){
					dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 4;				
					applicantNamesArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = jsonweektext[key].name==null? "Bulk" :jsonweektext[key].name;					
				} else if(jsonweektext[key].status==5){
						dataArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = 3;				
						applicantNamesArray[jsonweektext[key].slotid][jsonweektext[key].dayindex] = jsonweektext[key].name==null? "Bulk" :jsonweektext[key].name;					
				}	
			}
			
		}
	}
	
	function populateMonthSlots(monthIncr) {
		$.each(timeSlotArray, function(key, value) {
			dataArray.push(new Array(0, 0, 0, 0, 0, 0, 0));
		});
		var jsonweektext;
		$.ajax({
			url : "getScheduleForCurrentMonth.do/" + monthIncr + "/"
					+ '${interviewerId}',
			async : false,
			success : function(result) {
				jsonweektext = result;
				return jsonweektext;
			}
		});
		var darry = [];
		for ( var key in jsonweektext) {
			if (jsonweektext.hasOwnProperty(key)) {
				darry.push(jsonweektext[key].dayindex);
			}
		}
		return darry;
	}
	function monthView(monthIncr) {
		var montharray = populateMonthSlots(monthIncr);
		$("#tmiWeekTable").remove();
		$("#tmiMonthTable").remove();
		var tbl = $('<table></table>').attr({
			class : [ "table", "table-bordered" ].join(' ')
		}).attr({
			id : "tmiMonthTable"
		});
		var thead = $('<thead></thead>').appendTo(tbl);
		var trow = $('<tr></tr>').appendTo(thead);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;SUN")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;MON")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;TUE")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;WED")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;THU")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;FRI")
				.appendTo(trow);
		$('<th></th>').html("<span class=weekdayheader pull-left> &nbsp;SAT")
				.appendTo(trow);

		var dateVar = 1;
		var date = currentGmtTOLocal;		
		var y = date.getFullYear();
		var m = date.getMonth() + monthIncr;
		var monthvar = m + 1;
		var firstDay = new Date(y, m, 1);
		var lastDay = new Date(y, m + 1, 0).getDate();
		var dayOfTheWeek = firstDay.getDay() + 1;
		var cntFlag = false;
		var calendarRows = Math.ceil((dayOfTheWeek + lastDay) / 7);
		for (var j = 0; j < calendarRows; j++) {
			var row = $('<tr></tr>').attr({
				class : j % 2 == 0 ? "timeslotEven" : "timeslotOdd"
			}).appendTo(tbl);
			for (var i = 1; i <= 7; i++) {
				if (cntFlag == false && dayOfTheWeek == i) {
					cntFlag = true;
				}
				if (cntFlag == true && lastDay >= dateVar) {
					var tdbg = $('<td></td>').attr({
						class : [ "clickableMonth-td cal-white" ].join(' '),
						id : y + "/" + monthvar + "/" + dateVar
					})
							.html(
									"<span class=weekdateheader>" + dateVar
											+ "</span>").appendTo(row);
					for ( var key in montharray) {
						if (montharray[key] == dateVar) {
							tdbg.removeClass("cal-white");
							tdbg.addClass("cal-blue");
							tdbg.css({
								'backgroundColor' : '#0094da',
								'color' : '#fff !important'
							});
							tdbg.children().removeClass("weekdateheader");
							tdbg.children().addClass("weekdateheaderWhite");
						}

					}
					dateVar++;
				} else {
					$('<td></td>').appendTo(row);
				}
			}

		}
		tbl.appendTo($(".tmiSchedule"));
		$('tr').each(function() {
			if ($(this).text().trim() == "") {
				$(this).closest("tr").remove();
			}

		});
		$(".clickableMonth-td").click(
				function() {
					if ($(this).hasClass("cal-white")) {
					} else {
						 var one_day=1000*60*60*24;
						// This is to give week incr w.r.t today
						var daydiff = (((new Date($(this).attr("id"))).getTime()-currentGmtTOLocal.getTime())/one_day);
						daydiff = Math.floor((daydiff+1)/7);
						myweekView(daydiff);
					}

				});
		$("#weekInfo")
				.html(months[monthvar - 1] + " " + firstDay.getFullYear());

	}
	function getWeekNum(clickedDate) {
		var onejan = new Date(clickedDate.getFullYear(), 0, 1);
		var dayOfYear = ((clickedDate - onejan + 86400000) / 86400000);
		return Math.ceil(dayOfYear / 7)
	}
	function getMonday(d) {
		d = new Date(d);
		var day = d.getDay(), diff = d.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday 
		return new Date(d.setDate(diff));
	}

	function getWeek(weekIncr) {
		var thisMonday = getMonday(currentGmtTOLocal);
		var reqWeek = new Date(thisMonday.getFullYear(), thisMonday.getMonth(),
				thisMonday.getDate() + 7 * weekIncr);
		return reqWeek;
	}
	//sanga changes in progress
	function getMyWeek(weekIncr) {		
		var tdy = new Date(currentGmtTOLocal);
		var reqWeek = new Date(tdy.getFullYear(), tdy.getMonth(),
				tdy.getDate() + 7 * weekIncr);
		return reqWeek;
	}
	
	function myweekView(weekIncr) {
		$("#weekImg").attr("src","resources/images/icon_week_view_active.png");
		$("#monthImg").attr("src","resources/images/icon_month_view_inactive.png");
			count = weekIncr;
			monthWeekBtn = 0;		
		var week1day = getMyWeek(weekIncr); // Mon Nov 08 2010
		$("#tmiMonthTable").remove();
		$("#tmiWeekTable").remove();
		populateWeekSlots(weekIncr);
		var tbl = $('<table></table>').attr({
			class : [ "table", "table-bordered" ].join(' ')

		}).attr({
			id : "tmiWeekTable"
		});
		var thead = $('<thead></thead>').appendTo(tbl);
		var trow = $('<tr></tr>').appendTo(thead);
		$('<th></th>').text("").appendTo(trow);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week1day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week1day.getDay()]+"</span>")
				.appendTo(trow);
		var week2day = new Date(week1day.getFullYear(), week1day.getMonth(),
				week1day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week2day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week2day.getDay()]+"</span>")
				.appendTo(trow);
		var week3day = new Date(week2day.getFullYear(), week2day.getMonth(),
				week2day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week3day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week3day.getDay()]+"</span>")
				.appendTo(trow);
		var week4day = new Date(week3day.getFullYear(), week3day.getMonth(),
				week3day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week4day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week4day.getDay()]+"</span>")
				.appendTo(trow);
		var week5day = new Date(week4day.getFullYear(), week4day.getMonth(),
				week4day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week5day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week5day.getDay()]+"</span>")
				.appendTo(trow);
		var week6day = new Date(week5day.getFullYear(), week5day.getMonth(),
				week5day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week6day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week6day.getDay()]+"</span>")
				.appendTo(trow);
		var week7day = new Date(week6day.getFullYear(), week6day.getMonth(),
				week6day.getDate() + 1);
		$('<th></th>')
				.html(
						"<span class=weekdateheader>"
								+ week7day.getDate()
								+ "</span><span class=weekdayheader pull-left> &nbsp;"+weekday[week7day.getDay()]+"</span>")
				.appendTo(trow);
		for (var j = 0; j < timeSlotArray.length; j++) {
			var row = $('<tr></tr>').attr({
				class : j % 2 == 0 ? "timeslotEven" : "timeslotOdd"
			}).appendTo(tbl);
			$('<td ></td>').attr({
				class : [ "timeslotclass" ].join(' '),
				id : j + "-" + i
			}).html(
					'<span class="timeSlotTime">'
							+ timeSlotArray[j].substring(0,
									timeSlotArray[j].length - 2)
							+ '</span> <span class="timeSlotAMPM">'
							+ timeSlotArray[j].substring(
									timeSlotArray[j].length - 2,
									timeSlotArray[j].length) + '</span>')
					.appendTo(row);
			for (var i = 0; i < 7; i++) {
				var tdrow = $('<td></td>').attr({
					class : [ "clickable-td cal-white" ].join(' '),
					id : j + "-" + i+"-"+timeSlotArray[j]
				}).appendTo(row);				
				if (dataArray[j][i] == 1){
					tdrow.removeClass("cal-white");
					tdrow.addClass("cal-blue");
					tdrow.css('backgroundColor', '#0094da');
				} else if (dataArray[j][i] == 2){
					tdrow.removeClass("cal-white");
					tdrow.addClass("cal-orange");
					tdrow.css('backgroundColor', '#f36f21');
					tdrow.css('color', '#FFF');
				}else if (dataArray[j][i] == 3){
					tdrow.removeClass("cal-white");
					tdrow.addClass("cal-red");
					tdrow.css('backgroundColor', '#FF0000');
				}else if (dataArray[j][i] == 4){
					tdrow.removeClass("cal-white");
					tdrow.addClass("cal-green");
					tdrow.css('backgroundColor', '#00CC99');
					tdrow.css('color', '#FFF');					
				}else if (dataArray[j][i] == 5){
					tdrow.removeClass("cal-white");
					tdrow.addClass("cal-yellow");
					tdrow.css('backgroundColor', '#ededb2');										
				
				}
			}
		}
		tbl.appendTo($(".tmiSchedule"));
		$(".clickable-td").click(function() {
			var strdate = ($('#tmiWeekTable th').eq($(this).index())).text();			
			strdate = strdate.split(" ");	
			var slotdate =strdate[0];
			var currentId = $(this).attr('id');
			currentId = currentId.split("-");
			var slotid = slotIdMap[currentId[2]];
				
			var date = currentGmtTOLocal;
			var addFactor = parseInt(weekIncr*7)+parseInt(currentId[1]);
			var schDate = new Date(currentGmtTOLocal.getTime()+ 24 * 60 * 60 * 1000 * addFactor);			
			var scheduledate =schDate.getFullYear()+"-"+((schDate.getMonth())+1)+"-"+slotdate;	
		  var today= date.getFullYear()+'-'+(date.getMonth() + 1) +'-'+(date.getDate()+1);
		  var mdate=date.getFullYear()+'-'+(date.getMonth() +2) +'-'+(date.getDate());
		  var maxdate=process(mdate);
		 var currDate =process(today);		 
			var selectedDate = process(scheduledate);	
			if(currDate < selectedDate && maxdate > selectedDate){
				var scheduleStatus = updateSlotSchedule(slotid,scheduledate);
				if(scheduleStatus == true){
			if ($(this).hasClass("cal-white")) {
				$(this).removeClass("cal-white");
				$(this).addClass("cal-blue");
				$(this).css('backgroundColor', '#0094da');				
				dataArray[currentId[0]][currentId[1]] = 0;
			} else if ($(this).hasClass("cal-blue")) {
				$(this).removeClass("cal-blue");
				$(this).addClass("cal-white");				
				$(this).css('backgroundColor', 'transparent');
				dataArray[currentId[0]][currentId[1]] = 1;

			} 
			}else{
				if ($(this).hasClass("cal-blue")) {
				location.reload();
				} 
			}
			} 
			
		});
		if(week1day.getFullYear()==week7day.getFullYear()){
			
			if(week1day.getMonth()==week7day.getMonth()){
				$("#weekInfo").html(months[week1day.getMonth()] + " " + week1day.getFullYear());
			}else{
				$("#weekInfo").html(months[week1day.getMonth()]+" - "+months[week7day.getMonth()]+ " " + week1day.getFullYear());
			}
		}else{
			$("#weekInfo").html(months[week1day.getMonth()] + " " + week1day.getFullYear() +" - "+months[week7day.getMonth()] + " " + week7day.getFullYear());
		}
		
		
	}
	var rowCount = 0;
	function addMoreRows() {
		var tempTimeSlotOptionArray = JSON.parse(JSON
				.stringify(timeSlotOptionArray));
		for (var rowC = 0; rowC <= rowCount; rowC++) {
			if (document.getElementById('addStartHour' + rowC)) {
				var removeId = document.getElementById('addStartHour' + rowC).value;
				delete tempTimeSlotOptionArray[removeId];
			}
		}
		var myOptionTimeSlotOption = tempTimeSlotOptionArray.join("");
		if (myOptionTimeSlotOption.length > 0) {
			rowCount++;
			var recRow = '<div id="rowCount'+rowCount+'" class="row form-inline"><br><div class="col-xs-4 col-sm-4 col-md-4 form-group"> <label style="font-size: 13px"><b>From</b></label><select id="addStartHour'
					+ rowCount
					+ '" name="addStartHour[]" class="form-control tmiInterviewSlot" onChange=tmiInterviewSlotChange("addStartHour'
					+ rowCount
					+ '");>'
					+ myOptionTimeSlotOption
					+ '</select></div><div class="col-xs-4 col-sm-4 col-md-4 form-group"><label style="font-size: 13px"><b>To</b></label></td><td><input type="text" class="form-control" id="endTime'+rowCount+'" name="endTime[]"  size="7px" value="" readonly></div> <div class="col-xs-3 col-sm-3 col-md-4 form-grouping"><span style="font: normal 14px agency, arial; color: blue; text-decoration: underline; cursor: pointer;" onclick="removeRow('
					+ rowCount + ');">Remove</span></div></div>';
			jQuery('#addRows').append(recRow);
			$('#endTime' + rowCount).val(
					timeSlotMap[$('#addStartHour' + rowCount).find(":selected")
							.text()]);
		} else {		
			var modal = $('#myModal').modal({'show':true});
			  modal.find('.modal-title').text('Add Time Slots');
			  modal.find('.modal-body').text('You cannot add more time slots ');
		}
	}

	function removeRow(removeNum) {
		$('#rowCount' + removeNum).find('br').remove();
		jQuery('#rowCount' + removeNum).remove();

	}

	function tmiInterviewSlotChange(element) {
		var endTimeId = 'endTime'
				+ jQuery.trim($('#' + element).attr("id")).substring(12,
						$('#' + element).attr("id").length);
		$('#' + endTimeId).val(
				timeSlotMap[$('#' + element).find(":selected").text()]);
	}
	 function updateSlotSchedule(slotid,scheduledate){
		var resultStatus =false;
		$.ajax({
			url : "updateEvaluatorScheduleSlots.do/"+slotid+"/"+scheduledate+"/"+'${interviewerId}',
			async : false,
			 success : function(result) {
				resultStatus =result;
				return result;			
				} 
		});
		return resultStatus;
	}
	function process(date){
		   var parts = date.split("-");		   
		   return new Date(parts[0], parts[1] - 1, parts[2]);
		}
	
	function calendarValidator(){		
		var nowTemp = currentGmtTOLocal;
		var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
		var checkin = $('#startDate ').datepicker({
			 format: 'dd/mm/yyyy',
			startDate : '+2d',
			endDate : '+30d',
		  onRender: function(date) {
		    return date.valueOf() < now.valueOf() ? 'disabled' : '';
		  }
		}).on('changeDate', function(ev) {
		  if (ev.date.valueOf() < checkout.date.valueOf()) {
		    var newDate = new Date(ev.date);
		    newDate.setDate(newDate.getDate() + 1);
		    checkout.setValue(newDate);
		  }else{
			  $('#endDate').val('');
			  $('#endDate')[0].focus();
			  }
		  $('#calenderForm').bootstrapValidator(
					'revalidateField', 'startDate');
		  checkin.hide();
		  if(($('#startDate').val()>$('#endDate').val())){
		  $('#endDate').val('');
		  $('#endDate')[0].focus();
		  $('#calenderForm').bootstrapValidator(
					'revalidateField', 'endDate');
		  }else if($('#endDate').val()==''){
			  $('#endDate')[0].focus();
		  } 
		}).data('datepicker'); 
		var checkout = $('#endDate').datepicker({
			format: 'dd/mm/yyyy',
			startDate:'+2d',
			endDate : '+30d',
		  onRender: function(date) {
		    return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
		  }
		}).on('changeDate', function(ev) {
		 $('#calenderForm').bootstrapValidator(
						'revalidateField', 'endDate');
		  checkout.hide();
		  if(checkin.date.valueOf() > checkout.date.valueOf()){
			  $('#startDate').val('');
			  $('#startDate')[0].focus();
			  $('#calenderForm').bootstrapValidator(
						'revalidateField', 'startDate');
			  
			  }
		 
		}).data('datepicker');
	}
	
</script>
<style>
.tmilejend {
	height: 16px;
	width: 16px;
	margin-right: 10px;
}

.tmiTxt {
	font-size: 14px;
}

@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}

.table {
	
}

hr {
	border-color: #57574B;
}

form {
	color: #3e454c;
	font-size: 12px;
	font-family: robotoregular;
}

.timeslotEven {
	background-color: #f2f2f2;
}

.timeslotOdd {
	background-color: #fff;
}

.weekdateheader {
	color: #f36f21;
	font-family: robotoregular;
	font-size: 27px;
}

.weekdateheaderWhite {
	color: #fff;
	font-family: robotoregular;
	font-size: 30px;
}

.weekdayheader {
	color: #58574b;
	font-family: robotoregular;
	font-size: 14px;
}

.timeslotclass {
	width: 7%;
}

.timeSlotTime {
	color: #58574b;
	font-family: robotoregular;
	font-size: 18px;
}

.timeSlotAMPM {
	color: #58574b;
	font-family: robotoregular;
	font-size: 12px;
}

.clickable-td {
	width: 16em;
	text-wrap: none;
	white-space: -moz-pre-wrap !important; /* Mozilla, since 1999 */
	white-space: -webkit-pre-wrap; /*Chrome & Safari */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	white-space: pre-wrap; /* css-3 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	word-break: break-all;
	white-space: normal;
}

@media ( min-width : 767px) {
	#pagination-right {
		float: right;
	}
	.clickableMonth-td {
		height: 100px;
	}
	.form-grouping {
		margin-top: 10px;
	}
}

@media ( max-width : 1200px) {
	.timeslotclass {
		width: 9%;
	}
}

@media ( max-width : 768px) {
	.weekdateheader {
		color: #f36f21;
		font-family: robotoregular;
		font-size: 25px;
	}
	.weekdayheader {
		color: #58574b;
		font-family: robotoregular;
		font-size: 13px;
	}
}

@media ( max-width : 767px) {
	.weekdateheader {
		color: #f36f21;
		font-family: robotoregular;
		font-size: 14px;
	}
	.weekdateheaderWhite {
		color: #fff;
		font-family: robotoregular;
		font-size: 14px;
	}
	.weekdayheader {
		color: #58574b;
		font-family: robotoregular;
		font-size: 9px;
	}
	.timeSlotTime {
		color: #58574b;
		font-family: robotoregular;
		font-size: 12px;
	}
	.timeSlotAMPM {
		color: #58574b;
		font-family: robotoregular;
		font-size: 9px;
	}
	.clickableMonth-td {
		height: 40px;
	}
	.clickable-td {
		width: 21em !important;
		font-size: 10px;
	}
	.textdown {
		margin-top: 0px;
	}
	.form-grouping {
		margin-top: 10%;
	}
}
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_calendar_logo.png">&nbsp;&nbsp;Calendar
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<div
				style="font-family: robotoregular; font-size: 32px; color: #f36f21">
				My Availability</div>
			<h5 style="font-family: robotoregular;">Interviews will be
				scheduled based on your availability. Please update your
				availability by selecting the time slots in the Calendar and press
				Save.</h5>
			<hr>
			<form id="calenderForm" method="post">
				<div class="row">
					<div class="col-md-9">
						<br>
						<div class="row">
							<div class=" col-xs-12  col-sm-12  col-md-10">
								<div class="row">
									<div class="col-xs-8 col-sm-4 col-md-4">
										<div class="form-group">

											<div style="font-family: robotoregular; font-size: 15px;">
												<b>Set Time Period</b>
											</div>
										</div>
									</div>

									<div class="col-xs-8 col-sm-4 col-md-4">
										<div class="form-group ">
											<div class="input-group date " id='datepicker'>
												<input type="text" name="startDate" readonly
													class="form-control" id="startDate"> <span
													class="input-group-addon" id="startdateicon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>

									<div class="col-xs-8 col-sm-4 col-md-4">
										<div class="form-group ">
											<div class="input-group date " id='datepicker1'>
												<input type="text" name="endDate" readonly
													class="form-control" id="endDate"><span
													class="input-group-addon" id="enddateicon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 col-md-4">
								<div style="font-family: robotoregular; font-size: 15px;">
									<b>Set Your Work Days</b>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 col-md-10">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-5 col-md-5">
									<div class="row">
										<div class="col-sm-4 col-md-4">
											<div class="checkbox">
												<label> <input type="checkbox" value="Mon" id="mon"
													name="checkedweekdays[]">Monday
												</label>
											</div>
										</div>
										<div class="col-sm-4 col-md-4">
											<div class="checkbox">
												<label> <input type="checkbox" value="Tue" id="tue"
													name="checkedweekdays[]">Tuesday
												</label>
											</div>
										</div>
										<div class="col-sm-4 col-md-4">
											<div class="checkbox">
												<label> <input type="checkbox" value="Wed" id="wed"
													name="checkedweekdays[]">Wednesday
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-7 col-md-7">
									<div class="row">
										<div class="col-sm-3 col-md-3">
											<div class="checkbox textdown">
												<label> <input type="checkbox" value="Thu" id="thu"
													name="checkedweekdays[]">Thursday
												</label>
											</div>
										</div>
										<div class="col-sm-3 col-md-3">
											<div class="checkbox">
												<label> <input type="checkbox" value="Fri" id="fri"
													name="checkedweekdays[]">Friday
												</label>
											</div>
										</div>
										<div class="col-sm-3 col-md-3">
											<div class="checkbox">
												<label> <input type="checkbox" value="Sat" id="sat"
													name="checkedweekdays[]">Saturday
												</label>
											</div>
										</div>
										<div class="col-sm-3 col-md-3">
											<div class="checkbox">
												<label> <input type="checkbox" value="Sun" id="sun"
													name="checkedweekdays[]"> Sunday
												</label>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-11 col-md-9">
						<div class="row">
							<div class="col-sm-4 col-md-4">
								<div style="font-family: robotoregular; font-size: 15px;">
									<b>Define Your Work Hours</b>
								</div>
							</div>
						</div>
						<br>
						<div class="row form-inline">
							<div class="col-xs-4 col-sm-4 col-md-4 form-group">
								<label style="font-size: 13px"><b>From</b></label> <select
									id="addStartHour0" name="addStartHour[]"
									class="form-control tmiInterviewSlot">
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-4 form-group">
								<label style="font-size: 13px"><b>To</b></label> <input
									type="text" class="form-control" id="endTime0" name="endTime[]"
									size="7px" value="1 AM" readonly>
							</div>
							<div class="col-xs-4 col-sm-3 col-md-3 form-grouping"
								style="font: normal 14px agency, arial; color: blue; text-decoration: underline; cursor: pointer;"
								onclick="addMoreRows(this);">Add Row</div>

						</div>
						<div id="addRows" class="selValue"></div>
						<br>

					</div>
				</div>
				<div class="row">
					<div
						class="col-xs-4 col-xs-push-8 col-sm-2 col-sm-push-10 col-md-2 col-md-push-10">
						<div class="form-group">
							<input id="continue" type="submit"
								class="form-control mycontinuebtn" value="Save" />
						</div>
					</div>
				</div>
			</form>
			<hr>
			<div class="row">
				<div class="col-sm-5 col-md-5">
					<div style="font-family: robotoregular; font-size: 15px;">
						<ul class="pagination" id="pagination">
							<li><a id="previousday" href="#"><<</a></li>
							<li><a id="current" href="#">Current</a></li>
							<li><a id="nextday" href="#">>></a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-4 col-md-5">
					<h4 class="calender-bottom">
						<div id="weekInfo"></div>
					</h4>
				</div>
				<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});
</script>
				<div class="col-sm-3 col-md-2">
					<ul class="pagination" id="pagination-right">
						<li><a id="weekView"
							style="border: 0px; background-color: #fff; padding: 0px 4px;"
							href="#" data-toggle="tooltip" data-placement="left"
							title="Week view"><img id="weekImg"
								src="resources/images/icon_week_view_active.png"></a></li>
						<li><a id="monthView"
							style="border: 0px; background-color: #fff; padding: 0px 4px;"
							href="#" data-toggle="tooltip" data-placement="left"
							title="Month view"><img id="monthImg"
								src="resources/images/icon_month_view_inactive.png"></a></li>
					</ul>
				</div>
			</div>
			<div class="tmiSchedule"></div>

			<div class="row">
				<div class="form-group tmiTxt">
					<div class="col-sm-4 col-md-2">
						<img class="tmilejend"
							src="resources/images/icon_evaluators_feedback.png">Slot
						Open
					</div>
					<div class="col-sm-4 col-md-3">
						<img class="tmilejend"
							src="resources/images/icon_applicant_waiting _payment.png">Waiting
						for Payment
					</div>
					<div class="col-sm-4 col-md-2">
						<img class="tmilejend"
							src="resources/images/icon_video_audio_clip.png">Slot
						Booked
					</div>
					<div class="col-sm-4 col-md-2 ">
						<img class="tmilejend"
							src="resources/images/icon_eval_interview_accepted.png">Slot
						Accepted
					</div>
					<div class="col-sm-4 col-md-2">
						<img class="tmilejend"
							src="resources/images/icon_eval_interview_reject.png">Slot
						Rejected
					</div>
				</div>
			</div>
			<hr>
			<div class="modal" id="myModal" tabindex="-1" role="dialog"
				data-backdrop="static" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog mymodal-dialog ">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								onclick="reload()" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel"></h4>
						</div>
						<div class="modal-body"></div>
						<div class="modal-footer">
							<div
								class="col-xs-4 col-xs-push-4 col-sm-4 col-sm-push-4 col-md-4 col-md-push-4">
								<button type="button" class="form-control mycontinuebtn"
									onclick="reload()" data-dismiss="modal">Ok</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">	
		$(document).ready(function() {		
			$('#calenderForm').bootstrapValidator({
				framework : 'bootstrap',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					'checkedweekdays[]' : {
						validators : {
							choice : {
								min : 1,
								max : 7,
								message : 'Please select your work days.'
							}
						}
					},
					startDate : {
						validators : {
							notEmpty : {
								message : 'Please select start date.'
							}
						}
					},
					endDate : {
						validators : {
							notEmpty : {
								message : 'Please select end date.'
							}
						}
					}
				}
			});		
			$('#calenderForm').on('success.form.bv', function(e) {
				e.preventDefault();
				myajaxform() ;
			});
		});
		function myajaxform() {
			$("#processImg").show();
			var formData = new FormData($('form')[0]);
			$.ajax({
				url : 'scheduleTimeSlots.do', //Server script to process data
				type : 'POST',
				xhr : function() { // Custom XMLHttpRequest
					var myXhr = $.ajaxSettings.xhr();
					if (myXhr.upload) { // Check if upload property exists
						myXhr.addEventListener('progress',
								progressHandlingFunction, false); // For handling the progress of the upload
					}
					return myXhr;
				},
				//Ajax events
				//beforeSend: beforeSendHandler,
				success : completeHandler,
				error : errorHandler,
				// Form data
				data : formData,
				//Options to tell jQuery not to process data or worry about content-type.
				cache : false,
				contentType : false,
				processData : false
			});
		};
		var errorHandler = function(data) {

		};
		var progressHandlingFunction = function(evt) {

		};
		var completeHandler = function(data) {		
			location.reload();
			$('#startDate').val('');
			$('#endDate').val('');
			$('#startdateicon').val('');
			$('#enddateicon').val('');			
			$('input[type=checkbox]').attr('checked',false);			
			$("#processImg").hide();
		};	
	</script>
	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(".myTmi")
							.click(function() {
								var a = $(this).attr("id");
								$(".modal-bodyTmi").load("myInterviewSummary", {
									'interviewId' : a.replace("my", "")
								}), $("#myModal").modal({
									show : !0
								})
							})
				});		
		
		function processfunc(){
			$("#processImg").show();
		}
		
	</script>
</body>