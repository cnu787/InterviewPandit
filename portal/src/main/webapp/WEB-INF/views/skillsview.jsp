<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/skillView.css" />
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
			<h2 class="profile-header">My Skills</h2>
			<hr>
			<form id="myprofile" action="" method="post" autocomplete="off">
				<c:if test="${not empty skillsList}">
					<table>
						<thead>
							<tr>
								<th id="width1" Style="color: #FFF">Skill Type</th>
								<th id="width2" Style="color: #FFF">Skill Name</th>
								<th id="width3" Style="color: #FFF">Rating</th>
								<th id="width4" Style="color: #FFF">Years of Experience</th>
								<th id="width5" Style="color: #FFF"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:set var="i" value="0" />
							<c:forEach var="skilLst" items="${skillsList}">
								<tr>
									<td>${skilLst.skilltypename}</td>
									<td>${skilLst.skillname}</td>
									<td>${skilLst.skillrating}</td>
									<td>${skilLst.year}&nbsp;Years&nbsp;${skilLst.month}&nbsp;months</td>
									<td id="test"><img src="resources/images/icon_edit.png"
										onclick="editSkill(${skilLst.skillid})"><img
										class="imgpadding"
										src="resources/images/icon_delete_SPhone.png"
										data-toggle="modal" data-target="#myModal"
										data-skillid${i}="${skilLst.skillid}"
										onclick="deleteSkill('${i}')"></td>
								</tr>
								<c:set var="i" value="${i+1}" />
								<c:if test="${skilLst.skillid>maxid}">
									<c:set var="maxid" value="${skilLst.skillid}" />
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty skillsList}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Please
						add your skills.
					</div>
				</c:if>
				<br>
				<div class="row">
					<div class="col-sm-12 col-md-12">
						<div class="form-group">
							<div class="row">
								<div class="col-sm-4 col-md-4">
									<img src="resources/images/addskill.png" onclick="addSkills()">
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
								<button type="button" onclick="editSkill(${maxid})"
									class="form-control mybackbtn" style=""back">Back</button>
							</div>
						</div>
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">

								<c:if test="${externalProfile[0].usertypeid==2}">
									<button type="button" id="continue" onclick="Skillmap()"
										class="form-control mycontinuebtn">Continue</button>
								</c:if>
								<c:if test="${externalProfile[0].usertypeid==1}">
									<button type="button" onclick="Skillmap1()" id="continue"
										class="form-control mycontinuebtn">Done</button>
								</c:if>
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
		$('#myModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget); 			 
			  $('#deleteItem').click(function(){
				  var skillid=button.data('skillid'+recordnumber);
				  if(typeof skillid !== 'undefined'){	
					  $('#myModal').modal('hide');
				  if('${param.edit}'=='true'){			
						window.location.href = "skilldelete.do?skillId="+skillid+"&edit=true";
					}else{
						$("#mainProfileContainer").load('skilldelete',{'skillId':skillid});
					}
				  }				 
				  });
			  
			});	
	});
function editSkill(skillId){	
	if('${param.edit}'=='true'){
		window.location.href = "skillspage.do?skillId="+skillId+"&edit=true";
	}else{
		$("#mainProfileContainer").load('skillspage',{'skillId':skillId});	
	}	
}
function addSkills(){
	if('${param.edit}'=='true'){			
		window.location.href = "skillspage.do?edit=true";
	}else{
		$("#mainProfileContainer").load('skillspage');
	}	
}
</script>
	<script src="resources/js/skillView.js"></script>
</body>