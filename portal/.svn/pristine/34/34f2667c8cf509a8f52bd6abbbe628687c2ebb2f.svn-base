<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Your List of Tests</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">			
			<div class="row">
				<div class="col-md-12 ">
				<c:if test="${not empty examresultsList}">
					<table>
						<tr>							
							<th class="table-hd">Test RollNo</th>
							<th class="table-hd">Date & Time</th>
						</tr>
						<c:forEach var="resultList" items="${examresultsList}">					
						<tr style="background: #f2f2f2;">
							
							<td class="appmockTmi" id="my${resultList.testRollNo}"><a
												href="#">${resultList.testRollNo}</a></td>								
							<td>
							
								${resultList.datetime}</td>			
						</tr>
						</c:forEach>
					</table>
					</c:if>
					<c:if test="${empty examresultsList}">
					<div style="text-align: center;">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
						No records available.
					</div>
					</c:if>
					</div>
			</div>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$(".appmockTmi").click(function() {
			var a = $(this).attr("id");
			$(".modal-bodymocktmi").load("mockTestResultById", {
				'testRollNo' : a.replace("my", "")
			}), $("#tmimock-modal").modal({
				show : !0
			})
		});
		});
	</script>
	<div class="modal" id="tmimock-modal" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="mockModalLabel"
		aria-hidden="true">

		<div class="modal-dialog tmimock-rollno ">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="mockModalLabel">Your Test Result</h4>
				</div>
				<div class="modal-bodymocktmi"></div>
				<div class="modal-footer">
					<div class="col-md-3 col-md-push-9">
						<button type="button" class="form-control mycontinuebtn"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
</body>