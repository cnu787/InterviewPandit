<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/studyView.css" />
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
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;Profile
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h2 class="profile-header">Where did you Study?</h2>
			<h5 class="profile-text">Complete or Edit Various Levels of Your
				Education Here</h5>
			<hr>
			<form id="myprofile" action="" method="post" autocomplete="off">
				<c:if test="${not empty educationList}">
					<table>
						<thead>
							<tr>
								<th id="#width1" Style="color: #FFF">School/College/University</th>
								<th id="#width2" Style="color: #FFF">Graduated Year</th>
								<th id="#width3" Style="color: #FFF">Degree</th>
								<th id="#width4" Style="color: #FFF">Field of Study</th>
								<th id="#width5" Style="color: #FFF"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:set var="i" value="0" />
							<c:forEach var="eduLst" items="${educationList}">
								<tr>
									<td>${eduLst.universityname}</td>
									<td>${eduLst.graduateyear}</td>
									<td>${eduLst.degreename}</td>
									<td>${eduLst.fieldofstudy}</td>
									<td id="test"><img src="resources/images/icon_edit.png"
										onclick="editEdu(${eduLst.educationid})"><img
										class="imgpadding"
										src="resources/images/icon_delete_SPhone.png"
										data-toggle="modal" data-target="#myModal"
										data-studyid${i}="${eduLst.educationid}"
										onclick="deleteedu('${i}')"></td>
								</tr>
								<c:set var="i" value="${i+1}" />
								<c:if test="${eduLst.educationid>maxid}">
									<c:set var="maxid" value="${eduLst.educationid}" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty educationList}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Please
						add your study details to help us evaluate you better.
					</div>
				</c:if>
				<br>
				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-4 col-md-4">
									<img src="resources/images/addeducation.png"
										onclick="addSkills()">
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
								<button type="button" onclick="editEdu(${maxid})"
									class="form-control mybackbtn" id="back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button type="button" id="continue" onclick="careermap()"
									class="form-control mycontinuebtn">Continue</button>
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
				  $('#myModal').modal('hide');				
				 var studyid=button.data('studyid'+number);
				  if(typeof studyid !== 'undefined'){	
				   if('${param.edit}'=='true'){			
						window.location.href = "applicantStudydelete.do?studyId="+studyid+"&edit=true";
					}else{
						$("#mainProfileContainer").load('applicantStudydelete',{'studyId':studyid });
					} 				 
				  }
				  });
			
			});	
	});
function editEdu(studyId){	
		if('${param.edit}'=='true'){			
			window.location.href = "applicantStudyView.do?studyId="+studyId+"&edit=true";
		}else{
			$("#mainProfileContainer").load('applicantStudyView',{'studyId':studyId});	
		}
		
}


function addSkills(){	
	if('${param.edit}'=='true'){			
		window.location.href = "applicantStudyView.do?edit=true";
	}else{
		$("#mainProfileContainer").load('applicantStudyView');
	}	
}
function careermap(){	
	if("${mySesProfile[0].usertypeid}"==1){
		$("#mainProfileContainer").load('career');
	}	
	if("${mySesProfile[0].usertypeid}"==2){
		$("#mainProfileContainer").load('careerpage');
	}
}
</script>
	<script src="resources/js/study.js"></script>
</body>