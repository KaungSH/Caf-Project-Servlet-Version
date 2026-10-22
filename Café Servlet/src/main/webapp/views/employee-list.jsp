<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Employee Management - Cafe Luft</title>
<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #F7F5F0;
	color: #1c1917;
	margin: 0;
	padding: 20px;
}

.container {
	width: 100%;
	max-width: 1350px;
	margin: 0 auto;
}

.header {
	background: linear-gradient(135deg, rgba(27, 67, 50, 0.95) 0%,
		rgba(8, 28, 21, 0.98) 100%),
		url('https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=1200&q=80');
	background-size: cover;
	background-position: center;
	padding: 20px 30px;
	border-radius: 16px;
	box-shadow: 0 8px 24px rgba(27, 67, 50, 0.2);
	margin-bottom: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	color: #ffffff;
	border-bottom: 3px solid #d4af37;
}

.brand-wrapper {
	display: flex;
	align-items: center;
	gap: 14px;
}

.header-logo {
	width: 44px;
	height: 44px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid #d4af37;
}

.header-content h1 {
	font-family: 'Playfair Display', serif;
	font-size: 26px;
	font-weight: 700;
	margin: 0;
	color: #ffffff;
}

.subtitle {
	color: #d4af37;
	font-size: 11px;
	margin-top: 2px;
	text-transform: uppercase;
	letter-spacing: 0.1em;
	font-weight: 700;
}

.btn-add {
	background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%);
	color: #111111;
	padding: 10px 18px;
	text-decoration: none;
	border-radius: 12px;
	font-weight: 700;
	font-size: 12px;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	box-shadow: 0 4px 12px rgba(212, 175, 55, 0.3);
	transition: all 0.2s;
}

.btn-add:hover {
	transform: translateY(-1px);
}

.table-card {
	background-color: #ffffff;
	border: 1px solid rgba(212, 175, 55, 0.2);
	border-radius: 16px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.03);
	overflow-x: auto;
}

table {
	width: 100%;
	border-collapse: collapse;
	text-align: left;
	white-space: nowrap;
}

th {
	background-color: #fcfbf9;
	color: #1b4332;
	font-size: 11px;
	text-transform: uppercase;
	letter-spacing: 0.06em;
	font-weight: 700;
	padding: 12px 16px;
	border-bottom: 2px solid #e7e5e0;
}

td {
	padding: 10px 16px;
	border-bottom: 1px solid #f4f2ec;
	font-size: 13px;
	color: #334155;
	vertical-align: middle;
}

tr:hover td {
	background-color: #faf8f3;
}

/* Profile image styling */
.emp-profile {
	display: flex;
	align-items: center;
	gap: 12px;
}

.emp-avatar {
	width: 36px;
	height: 36px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid #E2E8F0;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
	flex-shrink: 0;
	transition: transform 0.2s ease;
}

tr:hover .emp-avatar {
	transform: scale(1.08);
	border-color: #d4af37;
}

.font-mono-id {
	font-family: monospace;
	font-size: 11px;
	color: #64748b;
	font-weight: 600;
}

.font-name {
	font-weight: 700;
	color: #0f172a;
}

.salary-text {
	font-weight: 700;
	color: #1b4332;
}

.badge-manager {
	background-color: rgba(212, 175, 55, 0.12);
	color: #997404;
	border: 1px solid rgba(212, 175, 55, 0.3);
	padding: 3px 8px;
	border-radius: 6px;
	font-size: 11px;
	font-weight: 700;
}

.badge-staff {
	background-color: #f1f5f9;
	color: #475569;
	border: 1px solid #cbd5e1;
	padding: 3px 8px;
	border-radius: 6px;
	font-size: 11px;
	font-weight: 700;
}

.status-badge {
	display: inline-flex;
	align-items: center;
	gap: 5px;
	padding: 3px 10px;
	border-radius: 9999px;
	font-size: 11px;
	font-weight: 600;
}

.dot {
	width: 5px;
	height: 5px;
	border-radius: 50%;
}

.status-normal {
	background-color: #dcfce7;
	color: #166534;
	border: 1px solid #bbf7d0;
}

.status-normal .dot {
	background-color: #22c55e;
}

.status-leave {
	background-color: #e0f2fe;
	color: #0369a1;
	border: 1px solid #bae6fd;
}

.status-leave .dot {
	background-color: #0ea5e9;
}

.status-fired {
	background-color: #ffe4e6;
	color: #9f1239;
	border: 1px solid #fecdd3;
}

.status-fired .dot {
	background-color: #f43f5e;
}

.action-btns {
	display: flex;
	gap: 6px;
	align-items: center;
	justify-content: flex-end;
}

.btn-edit {
	background-color: #f1f5f9;
	color: #1b4332;
	padding: 4px 10px;
	border-radius: 6px;
	font-size: 11px;
	font-weight: 700;
	text-decoration: none;
	border: 1px solid #cbd5e1;
	transition: all 0.2s;
}

.btn-edit:hover {
	background-color: #1b4332;
	color: #ffffff;
}

.btn-delete {
	background-color: #fff1f2;
	color: #e11d48;
	padding: 4px 10px;
	border-radius: 6px;
	font-size: 11px;
	font-weight: 700;
	text-decoration: none;
	border: 1px solid #fecdd3;
	transition: all 0.2s;
}

.btn-delete:hover {
	background-color: #e11d48;
	color: #ffffff;
}

.text-right {
	text-align: right;
}
</style>
</head>
<body>

	<div class="container">
		<div class="header">
			<div class="brand-wrapper">
				<img
					src="https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80"
					alt="Cafe Luft Logo" class="header-logo">
				<div class="header-content">
					<h1>Cafe Luft</h1>
					<div class="subtitle">Employee Directory & Management</div>
				</div>
			</div>

			<a href="${pageContext.request.contextPath}/employee?action=new"
				class="btn-add"> <svg xmlns="http://www.w3.org/2000/svg"
					width="14" height="14" fill="none" viewBox="0 0 24 24"
					stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
						stroke-width="2" d="M12 4v16m8-8H4" />
                </svg> Add New Employee
			</a>
		</div>

		<div class="table-card">
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Salary</th>
						<th>Role</th>
						<th>Branch</th>
						<th>Status</th>
						<th class="text-right">Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="emp" items="${employees}">
						<tr>
							<td class="font-mono-id">${emp.employeeId}</td>
							<td>
								<div class="emp-profile">
									<img
										src="${not empty emp.photopath ? emp.photopath : 'https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80'}"
										alt="${emp.name}" class="emp-avatar"
										onerror="this.src='https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80';">
									<span class="font-name">${emp.name}</span>
								</div>
							</td>
							<td>${emp.email}</td>
							<td>${emp.phone}</td>
							<td class="salary-text">${emp.salary}</td>
							<td><c:choose>
									<c:when test='${emp.roleName == "MANAGER"}'>
										<span class="badge-manager">${emp.roleName}</span>
									</c:when>
									<c:otherwise>
										<span class="badge-staff">${emp.roleName}</span>
									</c:otherwise>
								</c:choose></td>
							<td>${emp.branchName}</td>
							<td><c:choose>
									<c:when test='${emp.statusName == "NORMAL"}'>
										<span class="status-badge status-normal"><span
											class="dot"></span>${emp.statusName}</span>
									</c:when>
									<c:when test='${emp.statusName == "ONLEAVE"}'>
										<span class="status-badge status-leave"><span
											class="dot"></span>${emp.statusName}</span>
									</c:when>
									<c:otherwise>
										<span class="status-badge status-fired"><span
											class="dot"></span>${emp.statusName}</span>
									</c:otherwise>
								</c:choose></td>
							<td class="text-right">
								<div class="action-btns">

									<a
										href="${pageContext.request.contextPath}/employee?action=edit&id=${emp.employeeId}"
										class="btn-edit">Edit</a> <a
										href="${pageContext.request.contextPath}/employee?action=delete&id=${emp.employeeId}"
										onclick="return confirm('Are you sure you want to delete this employee?');"
										class="btn-delete">Delete</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>