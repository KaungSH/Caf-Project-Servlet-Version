<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="cafe.project.servlet.models.Suppliers"%>
<%@ page import="cafe.project.servlet.repositories.SupplierDAO"%>
<%!public String esc(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
	}%>

<%
List<Suppliers> suppliers = (List<Suppliers>) request.getAttribute("suppliers");
if (suppliers == null) {
	try {
		SupplierDAO supplierDAO = new SupplierDAO();
		suppliers = supplierDAO.getAllSuppliers();
	} catch (Exception e) {
		suppliers = new java.util.ArrayList<>();
	}
}

String ctx = request.getContextPath();
String message = request.getParameter("message");

String alertMessage = "";
String alertType = "success";

if ("added".equals(message)) {
	alertMessage = "Supplier added successfully.";
} else if ("updated".equals(message)) {
	alertMessage = "Supplier updated successfully.";
} else if ("deleted".equals(message)) {
	alertMessage = "Supplier moved to deleted list.";
} else if ("restored".equals(message)) {
	alertMessage = "Supplier restored successfully.";
}

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Suppliers Directory - Cafe Management System</title>

<!-- Google Fonts & Bootstrap Icons -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">

<!-- Centralized Cafe Theme -->
<link rel="stylesheet" href="<%=ctx%>/assets/css/cafe-theme.css">
</head>

<body>

	<!-- Top Navigation Bar -->
	<jsp:include page="/includes/navbar.jsp">
		<jsp:param name="activeNav" value="suppliers" />
	</jsp:include>

	<main class="page-wrapper">
		<div class="main-container">

			<!-- Breadcrumb Navigation -->
			<div class="cafe-breadcrumb">
				<a href="<%=ctx%>/home.jsp"><i class="bi bi-house-door me-1"></i>Dashboard</a>
				<span class="separator"><i class="bi bi-chevron-right"></i></span> <span>Suppliers</span>
			</div>

			<!-- Page Header Box -->
			<div class="page-header-box">
				<div>
					<h1 class="page-title">
						<i class="bi bi-truck"></i>Suppliers Directory
					</h1>
					<p class="page-subtitle">Manage roastery partners, dairy farms,
						flavor syrup vendors, and order contacts.</p>
				</div>

				<div class="d-flex gap-2 flex-wrap">
					<a href="<%=ctx%>/suppliers?action=deleted"
						class="btn btn-outline-secondary"> <i class="bi bi-trash3"></i>Deleted
						List
					</a> <a href="<%=ctx%>/suppliers?action=add" class="btn btn-primary">
						<i class="bi bi-plus-lg"></i>Add Supplier
					</a>
				</div>
			</div>

			<!-- Flash Alert Message -->
			<%
			if (!alertMessage.isEmpty()) {
			%>
			<div class="alert alert-<%=alertType%> alert-dismissible fade show"
				role="alert">
				<div class="d-flex align-items-center gap-2">
					<i
						class="bi <%="success".equals(alertType) ? "bi-check-circle-fill text-success" : "bi-exclamation-triangle-fill text-danger"%>"></i>
					<span><%=alertMessage%></span>
				</div>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
			}
			%>

			<!-- Search & Count Toolbar -->
			<div class="search-toolbar">
				<div class="search-input-wrap">
					<i class="bi bi-search search-icon"></i> <input type="text"
						id="searchInput" class="form-control"
						placeholder="Search by supplier name or contact info..."
						autocomplete="off" aria-label="Search suppliers">
				</div>

				<div class="record-count-badge">
					<span>Total Active Records:</span> <strong id="totalCount"><%=suppliers == null ? 0 : suppliers.size()%></strong>
				</div>
			</div>

			<!-- Suppliers Table -->
			<div class="table-responsive">
				<table class="table cafe-table align-middle" id="supplierTable">
					<thead>
						<tr>
							<th style="width: 70px;">No.</th>
							<th>Supplier Name</th>
							<th>Contact Information</th>
							<th style="width: 210px;">Created At</th>
							<th style="min-width: 160px; width: 170px;">Actions</th>
						</tr>
					</thead>

					<tbody>
						<%
						if (suppliers != null && !suppliers.isEmpty()) {
							int no = 1;
							for (Suppliers s : suppliers) {
						%>
						<tr class="supplier-row">
							<td class="tabular-num"><%=no++%></td>
							<td>
								<div class="d-flex align-items-center gap-2">
									<i class="bi bi-building text-muted"></i> <strong><%=esc(s.getName())%></strong>
								</div>
							</td>
							<td><span class="text-muted"><%=esc(s.getContact_info())%></span>
							</td>
							<td class="tabular-num"><i
								class="bi bi-calendar3 me-1 text-muted"></i> <%=s.getCreated_at() == null ? "-" : s.getCreated_at().format(formatter)%>
							</td>
							<td>
								<div class="action-buttons">
									<!-- Edit -->
									<a
										href="<%=ctx%>/suppliers?action=edit&amp;id=<%=URLEncoder.encode(s.getSupplier_id(), "UTF-8")%>"
										class="btn btn-sm btn-warning" title="Edit supplier"> <i
										class="bi bi-pencil-square"></i>Edit
									</a>

									<!-- Delete -->
									<form method="post" action="<%=ctx%>/suppliers"
										onsubmit="return confirm('Are you sure you want to move this supplier to the deleted list?');">
										<input type="hidden" name="action" value="delete"> <input
											type="hidden" name="supplier_id"
											value="<%=esc(s.getSupplier_id())%>">
										<button class="btn btn-sm btn-danger" type="submit"
											title="Delete supplier">
											<i class="bi bi-trash3"></i>Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr id="initialEmptyRow">
							<td colspan="5" class="empty-state-box">
								<div class="empty-state-icon">
									<i class="bi bi-truck"></i>
								</div>
								<h3 class="empty-state-title">No Suppliers Registered</h3>
								<p class="empty-state-text">Start building your cafe
									supplier catalog by adding your roasters and ingredient
									partners.</p> <a href="<%=ctx%>/suppliers?action=add"
								class="btn btn-primary"> <i class="bi bi-plus-lg"></i>Add
									Supplier
							</a>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>

		</div>
	</main>

	<!-- Footer -->
	<jsp:include page="/includes/footer.jsp" />


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		const searchInput = document.getElementById("searchInput");
		const table = document.getElementById("supplierTable");
		const totalCount = document.getElementById("totalCount");
		const tbody = table.querySelector("tbody");

		searchInput.addEventListener("input", function() {
			const keyword = this.value.toLowerCase().trim();
			const rows = tbody.querySelectorAll(".supplier-row");
			let count = 0;

			rows.forEach(function(row) {
				const rowText = row.textContent.toLowerCase();
				const matched = rowText.includes(keyword);
				row.style.display = matched ? "" : "none";
				if (matched) count++;
			});

			totalCount.textContent = count;

			const oldMessage = document.getElementById("noResultRow");
			if (oldMessage) {
				oldMessage.remove();
			}

			if (keyword !== "" && count === 0) {
				const noResultRow = document.createElement("tr");
				noResultRow.id = "noResultRow";

				const cell = document.createElement("td");
				cell.colSpan = 5;
				cell.className = "empty-state-box";
				cell.innerHTML = `
					<div class="empty-state-icon">
						<i class="bi bi-search"></i>
					</div>
					<h3 class="empty-state-title">No Matching Suppliers</h3>
					<p class="empty-state-text">No records match "<strong>` + keyword.replace(/</g, "&lt;").replace(/>/g, "&gt;") + `</strong>". Try a different name or contact query.</p>
				`;

				noResultRow.appendChild(cell);
				tbody.appendChild(noResultRow);
			}
		});
	</script>

</body>
</html>
