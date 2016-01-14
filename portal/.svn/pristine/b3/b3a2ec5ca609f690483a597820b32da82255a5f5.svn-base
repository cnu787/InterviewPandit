
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
* {
	margin: 0;
	padding: 0;
}

#page-wrap {
	margin: 50px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

tr:nth-of-type(odd) {
	background: #FFF;
}

tr:nth-of-type(even) {
	background: #f2f2f2;
}

th {
	background: #59574B;
	color: #fff;
	font-weight: 700;
}

td,th {
	padding: 10px 20px;
	border: 0 solid #ccc;
	text-align: left;
}

tr {
	border-bottom: 1px solid #ccc
}

@media only screen and (max-width: 700px) {
	table,thead,tbody,th,td,tr {
		display: block
	}
	td {
		color: #0095da;
		font-size: 14px;
		font-family: "robotoregular"
	}
	tr:nth-of-type(even) {
		background: #f2f2f2
	}
	tr:nth-of-type(odd) {
		background: #f2f2f2
	}
	thead tr {
		position: absolute;
		top: -9999px;
		left: -9999px
	}
	tr {
		border: 1px solid #ccc;
		margin-top: 25px
	}
	td {
		border: none;
		border-bottom: 1px solid #eee;
		position: relative;
		padding-left: 50%
	}
	td:before {
		position: absolute;
		top: 6px;
		left: 6px;
		width: 45%;
		padding-right: 10px;
		white-space: nowrap
	}
	td:nth-of-type(1):before {
		content: "Month Year :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	td:nth-of-type(2):before {
		content: "Amount :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
	td:nth-of-type(3):before {
		content: "Payment Status :";
		font-family: "robotoregular";
		color: #3E454c;
		font-size: 14px
	}
}
</style>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<hr>


				<table>
					<thead>
						<tr>
							<th id="width1" Style="color: #FFF">Interview ID</th>
							<th id="width2" Style="color: #FFF">Applicant Name</th>
							<th id="width2" Style="color: #FFF">Time Slot</th>
							<th id="width3" Style="color: #FFF">Amount</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="paylLst" items="${mypaymentList}">
							<tr>
								<td>${paylLst.interviewtmiid}</td>
								<td>${paylLst.name}</td>
								<td>${paylLst.date}${paylLst.timeslot}</td>
								<td>${paylLst.amount}</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<hr>
			</div>
		</div>
	</div>

</body>