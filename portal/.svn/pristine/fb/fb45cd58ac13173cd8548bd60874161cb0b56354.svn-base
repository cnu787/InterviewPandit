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
							id="closeItem">No</button>
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
	<div class="" style="margin-top: 20px;">
		<div class="container">
			<h1 class="header-file">My Skills</h1>
			<h4 class="header-text">
				<span style="color: #0095DA;">If you want to highlight your
					skills, you can add them now, or you can edit or delete any info
					you entered</span>
			</h4>
			<hr>
			<form id="myskills" action="JavaScript:myajaxform()" method="post"
				autocomplete="off">
				<c:if test="${not empty intterviewlist}">
					<table id="tablerow">
						<thead>
							<tr>
								<th id="width1" Style="color: #FFF;">Skill Type</th>
								<th id="width2" Style="color: #FFF">Skill Name</th>
								<th id="width3" Style="color: #FFF">Rating</th>
								<th id="width4" Style="color: #FFF">Years of Experience</th>
								<th id="width5" Style="color: #FFF"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="pageid" value="0" />
							<c:set var="maxid" value="0" />
							<c:set var="i" value="0" />
							<c:forEach var="intList" items="${intterviewlist}">
								<tr id="skillrow${i}">
									<td><input type="hidden" name="skillid_${i}"
										id="skillid_${i}" value="${intList.skillid}" />${intList.skilltypename}</td>
									<td>${intList.skillname}</td>
									<td>${intList.skillrating}</td>
									<td>${intList.year}&nbsp;Years&nbsp;${intList.month}&nbsp;months</td>
									<td id="test"><img src="resources/images/icon_edit.png"
										style="padding-right: 20px;"
										onclick="editSkill(${intList.skillid},${intList.pagetype})">&nbsp;
										&nbsp; <img src="resources/images/icon_delete_SPhone.png"
										data-toggle="modal" data-target="#myModal"
										data-skillid${i}="${intList.skillid}"
										data-pagetype${i}="${intList.pagetype}"
										onclick="deleterecord('${i}')"> <input type="hidden"
										name="pagetype_${i}" id="pagetype_${i}"
										value="${intList.pagetype}" /><input type="hidden"
										name="updateskills_${i}" id="updateskills_${i}"
										value="${intList.updateskills}" /></td>
								</tr>
								<c:set var="i" value="${i+1}" />
								<c:if test="${intList.pagetype==2}">
									<c:if test="${intList.skillid>maxid}">
										<c:set var="maxid" value="${intList.skillid}" />
										<c:set var="pageid" value="${intList.pagetype}" />
									</c:if>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty intterviewlist}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;Please
						add your skills to help us evaluate you better.
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
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12">
						<div class="form-group">
							<div class="checkbox">
								<label><input type="checkbox" name="skillspage"
									id="skillspage" value="true">Would you like to update
									these skills in your profile </label>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<input type="hidden" name="_dontcare">
				<div class="row">
					<c:if test="${empty intterviewlist}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						</div>
					</c:if>
					<div
						class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
						<div class="form-group">
							<button type="button" class="form-control mybackbtn"
								onclick="backbutton()" id="back">Back</button>
						</div>
					</div>
					<c:if test="${not empty intterviewlist}">
						<div
							class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
							<div class="form-group">
								<button id="continue" type="submit"
									class="form-control mycontinuebtn">Continue</button>

							</div>
						</div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<div class="modal" id="myprofileUpdate" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog mymodal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Profile update
						confirmation</h4>
				</div>
				<div class="modal-body">Are you sure you want to update
					industry,domain,skills in profile?</div>
				<div class="modal-footer">
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" id="noupdate" class="form-control mybackbtn"
							data-dismiss="modal">No</button>
					</div>
					<div
						class="col-xs-4 col-xs-push-2 col-sm-4 col-sm-push-2 col-md-4 col-md-push-2">
						<button type="button" id="updateProfile"
							class="form-control mycontinuebtn">Yes</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="resources/js/interskillonready.js"></script>
	<c:if test="${profilecount ne 1 }">
		<script src="resources/js/interskillprofile.js"></script>
	</c:if>
	;
	<c:if test="${profilecount eq 1 }">
		<script src="resources/js/interviewprofilecount.js"></script>
	</c:if>
	<script type="text/javascript">		
</script>
</body>