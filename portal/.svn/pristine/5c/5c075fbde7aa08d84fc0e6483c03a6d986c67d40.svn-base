<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" href="resources/css/datepicker.css" />
<style>
/* Generic Styling, for Desktops/Laptops */
.tmimock-rollno {
	width: 1000px;
}

table {
	width: 100%;
	/*border-collapse: collapse;*/
}

th {
	/* 	background: #59574B;
	color: white; */
	font-weight: bold;
}

.table-hd {
	color: #FFF;
	background: #59574B;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: center;
	width: -1em;
	text-wrap: none;
	white-space: -moz-pre-wrap !important; /* Mozilla, since 1999 */
	white-space: -webkit-pre-wrap; /*Chrome & Safari */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	white-space: pre-wrap; /* css-3 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	/*word-break: break-all;*/
	white-space: normal;
}

tr {
	border-bottom: 1px solid #cccccc;
}

label {
	font-weight: bold;
}
</style>
<script type="text/javascript">

	$(document).ready(
			function() {
				$('#mocktestform').bootstrapValidator({
					framework : 'bootstrap',
					feedbackIcons : {
						valid : 'glyphicon glyphicon-ok',
						invalid : 'glyphicon glyphicon-remove',
						validating : 'glyphicon glyphicon-refresh'
					},
					fields : {						
						testStartDate : {
							validators : {
								notEmpty : {
									message : 'Please select start date.'
								}
							}
						},
						testEndDate : {
							validators : {
								notEmpty : {
									message : 'Please select end date.'
								}
							}
						}
					}
				});
				$('#mocktestform').on('success.form.bv', function(e) {
					e.preventDefault();
					myPaginationFun();
				});	
				calendarValidator();				
			});
								

			
	var myPaginationFun = function() {
		$('.mockTestResultInner').load("tmiAdmMockTestResultModelList", {
			'testStartDate' : $('#testStartDate').val(),
			'testEndDate' : $('#testEndDate').val()
		});
		window.scrollTo(0, 0);

	}
	function calendarValidator(){		
		
		var checkin = $('#testStartDate ').datepicker({
			 format: 'yyyy-mm-dd'
		}).on('changeDate', function(ev) {
		  if (ev.date.valueOf() < checkout.date.valueOf()) {			  
		    var newDate = new Date(ev.date);		    
		    newDate.setDate(newDate.getDate() + 1);		    
		    //checkout.setValue(newDate);
		  }else{			  
			  $('#testEndDate').val('');			 
			  $('#testEndDate')[0].focus();
			  }		  
		  $('#mocktestform').bootstrapValidator(
					'revalidateField', 'testStartDate');		  
		  checkin.hide();
		  if(($('#testStartDate').val()>$('#testEndDate').val())){
		  $('#testEndDate').val('');
		  $('#testEndDate')[0].focus();
		  $('#mocktestform').bootstrapValidator(
					'revalidateField', 'testEndDate');
		  }else if($('#testEndDate').val()==''){
			  $('#testEndDate')[0].focus();
		  } 
		}).data('datepicker'); 
		var checkout = $('#testEndDate').datepicker({
			format: 'yyyy-mm-dd'			
		}).on('changeDate', function(ev) {
		 $('#mocktestform').bootstrapValidator(
						'revalidateField', 'testEndDate');
		  checkout.hide();
		  if(checkin.date.valueOf() > checkout.date.valueOf()){
			  $('#testStartDate').val('');
			  $('#testStartDate')[0].focus();
			  $('#mocktestform').bootstrapValidator(
						'revalidateField', 'testStartDate');
			  
			  }
		 
		}).data('datepicker');
	}
	
	
</script>
<body>

	<div class="container">
	<br>
	<div class="row">
	<form method="post" id="mocktestform">
	<div class="col-md-3 form-group">
				<label>Start Date</label>
				<div class="input-group date ">
					<input type="text" name="testStartDate" readonly class="form-control"
						id="testStartDate"> <span class="input-group-addon"
						id="startdateicon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
			<div class="col-md-3 form-group">
			<label>End Date</label>
				<div class="input-group date ">
					<input type="text" name="testEndDate" readonly class="form-control"
						id="testEndDate"> <span class="input-group-addon"
						id="enddateicon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
			<div class="col-md-2 form-group">		
			<br><label></label>
			<div>	
						<input id="continue" type="submit"							
							class="form-control mycontinuebtn" value="Submit" />
							</div>
			
			</div>
	</form>
	</div>
		
			<div class="mockTestResultInner">
			</div>
			
		
	</div>
	
	<script type="text/javascript">
	

		

		
		function printData(data) {
		    var printwindow = window.open('', 'printDiv', 'height=450,width=650');
		    
		   printwindow.document.write('<style type="text/css">table {border-spacing: 0px;border-collapse: collapse;width: 100%;}th {font-weight: bold;}.table-hd {color: #FFF;background: #59574B;}td,th {padding: 10px 18px;border: 0px solid #ccc;text-align: center;width: -1em;text-wrap: none;white-space: -moz-pre-wrap !important; white-space: -webkit-pre-wrap; white-space: -pre-wrap; white-space: -o-pre-wrap;white-space: pre-wrap;word-wrap: break-word; white-space: normal;}tr {border-bottom: 1px solid #cccccc;	}label {font-weight: bold;}</style>');
		   printwindow.document.write(data);
		    printwindow.print();   
		   return true; 
		}
		function PrintElem(elem) {
			printData($(elem).html());
		} 
		
		
	
</script>
</body>