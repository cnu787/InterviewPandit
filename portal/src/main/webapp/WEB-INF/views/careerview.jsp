<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/intlTelInput.css">
<link rel="stylesheet" href="resources/css/careerView.css">
<script src="resources/js/intlTelInput.js"></script>
<body>
	<!-- Modal -->
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog mymodal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Delete confirmation</h4>
				</div>
				<div class="modal-body">Are you sure you wish to delete this
					record?</div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" class="form-control mybackbtn"
							data-dismiss="modal">No</button>
					</div>
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" id="deleteItem"
							class="form-control mycontinuebtn">Yes</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 18px;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h2 class="profile-header">Share your career map</h2>
			<h5 class="profile-text">If you have worked in multiple
				organizations. You can add them now, or you can edit or delete any
				info you entered</h5>
			<hr>
			<form id="myprofile" action="" method="post">
				<c:if test="${not empty careerList}">
					<table>
						<thead>
							<tr>
								<th id="width1" Style="color: #FFF">Company Name</th>
								<th id="width2"
									Style="color: #FFF; word-break: break-all !important;">Position/<br />
									Designation
								</th>
								<th id="width3" Style="color: #FFF">Location</th>
								<th id="width4" Style="color: #FFF">Time From</th>
								<th id="width5" Style="color: #FFF">Time To</th>
								<th id="width6" Style="color: #FFF"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:set var="i" value="0" />
							<c:forEach var="careerLst" items="${careerList}">
								<tr>
									<td id="width1">${careerLst.companyname}</td>
									<td id="width2">${careerLst.designation}</td>
									<td id="width3">${careerLst.location}</td>
									<td id="width4">${careerLst.months}&nbsp;${careerLst.graduateyear}</td>
									<td id="width5">
									<c:if test="${careerLst.tomonth.equals('0')}">
									Till date
									</c:if>
									<c:if test="${careerLst.tomonth!='0'}">
									${careerLst.tomonth}&nbsp;${careerLst.toyear}</c:if>
									</td>
									<td id="test"><img src="resources/images/icon_edit.png"
										onclick="editcareer(${careerLst.careerid})"><img
										class="imgpadding"
										}"										src="resources/images/icon_delete_SPhone.png"
										data-toggle="modal" data-target="#myModal"
										data-careerid${i}="${careerLst.careerid}"
										onclick="deleteCareer('${i}')"></td>
								</tr>
								<c:set var="i" value="${i+1}" />
								<c:if test="${careerLst.careerid>maxid}">
									<c:set var="maxid" value="${careerLst.careerid}" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty careerList}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Please
						add your career details to help us evaluate you better.
					</div>
				</c:if>
				<br>
				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-4 col-md-4">
									<img src="resources/images/addworkexp.png"
										onclick="addCareer()">
								</div>


							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<c:if test="${empty param.edit}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" onclick="editcareer(${maxid})"
									class="form-control mybackbtn" id="back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<input type="button" id="continue" onclick="careermap()"
									class="form-control mycontinuebtn" value="Continue" />
							</div>
						</div>
					</c:if>
					<c:if test="${param.edit==true}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group"></div>
						</div>
						<div
							class="col-xs-6 col-xs-push-2 col-sm-3 col-sm-push-7 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button"
									onclick="window.location.href='myCompleteProfile.do';"
									class="form-control mybackbtn" id="back">View Summary</button>
							</div>
						</div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">	
	jQuery(document).ready(function() {
		/* $.ajaxSetup ({cache: false}); */
		$('#myModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget); 			 
			  $('#deleteItem').click(function(){
				  var careerid=button.data('careerid'+recordnumber);
				  if(typeof careerid !== 'undefined'){
					  $('#myModal').modal('hide');
				  if('${param.edit}'=='true'){			
						window.location.href = "careerdelete.do?careerId="+careerid+"&edit=true";
					}else{
						$("#mainProfileContainer").load('careerdelete',{'careerId':careerid});
					}			
				  }
				  });
			
			});	
	});
function editcareer(careerId){	
	if('${param.edit}'=='true'){			
		window.location.href = "careerpage.do?careerId="+careerId+"&edit=true";
	}else{
		$("#mainProfileContainer").load('careerpage',{'careerId':careerId});	
	}			
}
function addCareer(){
	if('${param.edit}'=='true'){			
		window.location.href = "careerpage.do?edit=true";
	}else{
		$("#mainProfileContainer").load('careerpage');
	}	
}
</script>
	<script src="resources/js/careerView.js"></script>
</body>