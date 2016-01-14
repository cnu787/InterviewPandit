<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
/* Generic Styling, for Desktops/Laptops */
table {
	width: 100%;
	/*border-collapse: collapse;*/
}
/* Zebra striping */
tr:nth-of-type(odd) {
	background: #FFFFFF;
}

tr:nth-of-type(even) {
	background: #f2f2f2;
}

th {
	background: #59574B;
	color: white;
	font-weight: bold;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
	width: -1em;
	text-wrap: none;
}

td,th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: left;
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

.btn-lg {
	padding: 10px 39px;
}
</style>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#bulkuploadlink").parent().addClass("active");
	});
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Bulk Upload Groups</div>
	</div>
	<div class="container">
		<br>
		<hr>
		<c:if test="${not empty bulkGroupList}">
			<table>
				<tr>

					<th Style="color: #FFF">Bulk Id</th>
					<th Style="color: #FFF">Bulk Group Name</th>

				</tr>

				<c:forEach var="bulkGroupLst" items="${bulkGroupList}">
					<tr>
						<td style="padding-left: 18px; padding-right: 10px;" class="appid"
							id="${bulkGroupLst.bulkgroupid}"><a
							href="tmiAdmInterviewBulkGroup.do?bulkGroupId=${bulkGroupLst.bulkgroupid}">${bulkGroupLst.bulkgroupid}</a></td>
						<td style="padding-left: 18px; padding-right: 10px;">${bulkGroupLst.bulkgroupname}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty bulkGroupList}">

			<div style="text-align: center;">
				<br> <img style="width: 30px;"
					src="resources/images/warning.png" />&nbsp;No Groups uploaded.
			</div>
		</c:if>
		<hr>
	</div>
</body>