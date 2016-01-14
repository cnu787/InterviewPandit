<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$(document).ready(function() {
		$("#reportslink").parent().addClass("active");
		displaydata();
	});
	function displaydata() {
		$(".ongng_bookings").load("ongoingBookingDetails", {
			'dashboardId' : '${dashboardId}',
			'enterString' : '${enterString}'
		});
	}
</script>
<style>
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
</style>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">Ongoing Bookings</div>
	</div>
	<div class="" style="margin-top: 40px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
<hr>
					<div class="ongng_bookings"></div>
<hr>
				</div>
			</div>
		</div>
	</div>
</body>