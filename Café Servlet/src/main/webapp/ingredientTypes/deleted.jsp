<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="cafe.project.servlet.models.IngredientTypes"%>
<%@ page import="cafe.project.servlet.repositories.IngredientTypeDAO"%>
<%
List<IngredientTypes> items = (List<IngredientTypes>) request.getAttribute("items");
if (items == null) {
	try {
		IngredientTypeDAO ingredientTypeDAO = new IngredientTypeDAO();
		items = ingredientTypeDAO.findAll(true);
	} catch (Exception e) {
		items = new java.util.ArrayList<>();
	}
}

String message = request.getParameter("message");
String contextPath = request.getContextPath();

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

String alertMessage = "";
String alertType = "success";

if ("restored".equals(message)) {
	alertMessage = "Ingredient type restored successfully.";
} else if ("permanentDeleted".equals(message)) {
	alertMessage = "Ingredient type permanently deleted.";
} else if ("duplicate".equals(message)) {
	alertMessage = "Cannot restore because another active ingredient type has the same name.";
	alertType = "danger";
}

java.util.function.Function<String, String> esc = value -> {
	if (value == null) {
		return "";
	}
	return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'",
	"&#39;");
};
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Deleted Ingredient Types - Cafe Management System</title>

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
<link rel="stylesheet" href="<%=contextPath%>/assets/css/cafe-theme.css">
</head>

<body>
	<!-- Top Navigation Bar -->
	<jsp:include page="/includes/navbar.jsp">
		<jsp:param name="activeNav" value="ingredients" />
	</jsp:include>
	
	<main class="page-wrapper">
		<div class="main-container">

			<!-- Breadcrumb Navigation -->
			<div class="cafe-breadcrumb">
				<a href="<%=contextPath%>/home.jsp"><i
					class="bi bi-house-door me-1"></i>Dashboard</a> <span class="separator"><i
					class="bi bi-chevron-right"></i></span> <a
					href="<%=contextPath%>/ingredientTypes">Ingredients</a> <span
					class="separator"><i class="bi bi-chevron-right"></i></span> <span>Deleted
					Ingredients</span>
			</div>

			<!-- Page Header Box -->
			<div class="page-header-box">
				<div>
					<h1 class="page-title">
						<i class="bi bi-trash3-fill"></i>Deleted Ingredients Archive
					</h1>
					<p class="page-subtitle">Restore soft-deleted ingredient
						classifications or permanently purge them from the database.</p>
				</div>

				<div class="d-flex gap-2 flex-wrap">
					<a href="<%=contextPath%>/ingredientTypes?action=list"
						class="btn btn-primary"> <i class="bi bi-arrow-left"></i>Back
						to Active Ingredients
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
						placeholder="Search by ingredient name, description, or unit..."
						autocomplete="off" aria-label="Search deleted ingredients">
				</div>

				<div class="record-count-badge">
					<span>Archived Records:</span> <strong id="totalCount"><%=items == null ? 0 : items.size()%></strong>
				</div>
			</div>

			<!-- Deleted Table -->
			<div class="table-responsive">
				<table class="table cafe-table align-middle"
					id="deletedIngredientTable">
					<thead>
						<tr>
							<th style="width: 70px;">No.</th>
							<th>Ingredient Name</th>
							<th>Description</th>
							<th style="width: 140px;">Unit</th>
							<th style="width: 210px;">Created At</th>
							<th style="min-width: 220px; width: 240px;">Actions</th>
						</tr>
					</thead>

					<tbody>
						<%
						if (items != null && !items.isEmpty()) {
							int no = 1;
							for (IngredientTypes item : items) {
						%>
						<tr class="ingredient-row">
							<td class="tabular-num"><%=no++%></td>
							<td>
								<div class="d-flex align-items-center gap-2">
									<span class="status-badge-deleted">Archived</span> <strong><%=esc.apply(item.getName())%></strong>
								</div>
							</td>
							<td><span class="text-muted"> <%=item.getDescription() == null || item.getDescription().isEmpty() ? "-" : esc.apply(item.getDescription())%>
							</span></td>
							<td><span class="unit-badge"> <i
									class="bi bi-tag-fill"></i> <%=esc.apply(item.getUnitName())%>
							</span></td>
							<td class="tabular-num"><i
								class="bi bi-calendar3 me-1 text-muted"></i> <%=item.getCreatedAt() == null ? "-" : item.getCreatedAt().format(formatter)%>
							</td>
							<td>
								<div class="action-buttons">
									<!-- Restore -->
									<form method="post" action="<%=contextPath%>/ingredientTypes"
										onsubmit="return confirm('Restore this ingredient type?');">
										<input type="hidden" name="action" value="restore"> <input
											type="hidden" name="id"
											value="<%=esc.apply(item.getIngredientTypeId())%>">
										<button class="btn btn-sm btn-success" type="submit"
											title="Restore ingredient type">
											<i class="bi bi-arrow-counterclockwise"></i>Restore
										</button>
									</form>

									<!-- Destroy -->
									<form method="post" action="<%=contextPath%>/ingredientTypes"
										onsubmit="return confirm('Permanently delete this ingredient type? This action cannot be undone.');">
										<input type="hidden" name="action" value="permanentDelete">
										<input type="hidden" name="id"
											value="<%=esc.apply(item.getIngredientTypeId())%>">
										<button class="btn btn-sm btn-danger" type="submit"
											title="Permanently delete">
											<i class="bi bi-x-octagon"></i>Permanent Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr id="emptyRow">
							<td colspan="6" class="empty-state-box">
								<div class="empty-state-icon">
									<i class="bi bi-check2-circle"></i>
								</div>
								<h3 class="empty-state-title">Recycle Bin is Empty</h3>
								<p class="empty-state-text">No deleted ingredient items
									currently stored in the archive.</p> <a
								href="<%=contextPath%>/ingredientTypes?action=list"
								class="btn btn-primary"> <i class="bi bi-arrow-left"></i>Return
									to Active Ingredients
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
		const table = document.getElementById("deletedIngredientTable");
		const totalCount = document.getElementById("totalCount");
		const tbody = table.querySelector("tbody");

		searchInput.addEventListener("input", function() {
			const keyword = this.value.toLowerCase().trim();
			const rows = tbody.querySelectorAll(".ingredient-row");
			let count = 0;

			rows.forEach(function(row) {
				const text = row.textContent.toLowerCase();
				const matched = text.includes(keyword);
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
				cell.colSpan = 6;
				cell.className = "empty-state-box";
				cell.innerHTML = `
					<div class="empty-state-icon">
						<i class="bi bi-search"></i>
					</div>
					<h3 class="empty-state-title">No Matching Deleted Ingredients</h3>
					<p class="empty-state-text">No archived items match "<strong>` + keyword.replace(/</g, "&lt;").replace(/>/g, "&gt;") + `</strong>".</p>
				`;

				noResultRow.appendChild(cell);
				tbody.appendChild(noResultRow);
			}
		});
	</script>

</body>
</html>
