<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Café Luft - Daily Register</title>

<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #F7F5F0;
	color: #1c1917;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.top-navbar {
	background: linear-gradient(135deg, rgba(27, 67, 50, 0.98) 0%,
		rgba(8, 28, 21, 1) 100%);
	padding: 14px 32px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 3px solid #d4af37;
	box-shadow: 0 4px 18px rgba(27, 67, 50, 0.15);
}

.brand-container {
	display: flex;
	align-items: center;
	gap: 12px;
	text-decoration: none;
}

.brand-logo {
	width: 42px;
	height: 42px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid #d4af37;
}

.brand-name {
	font-family: 'Playfair Display', serif;
	font-size: 22px;
	font-weight: 700;
	color: #ffffff;
	letter-spacing: 0.5px;
}

.user-meta {
	display: flex;
	align-items: center;
	gap: 18px;
	color: #f7f5f0;
	font-size: 13px;
}

.user-tag {
	background: rgba(212, 175, 55, 0.15);
	border: 1px solid rgba(212, 175, 55, 0.4);
	color: #d4af37;
	padding: 5px 12px;
	border-radius: 20px;
	font-weight: 600;
	font-size: 12px;
}

.btn-logout {
	color: #fca5a5;
	text-decoration: none;
	font-weight: 600;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	transition: color 0.2s ease;
}

.btn-logout:hover {
	color: #f87171;
}

.page-container {
	width: 100%;
	max-width: 1100px;
	margin: 32px auto;
	padding: 0 20px;
}

.status-card {
	background: #ffffff;
	border: 1px solid rgba(212, 175, 55, 0.25);
	border-radius: 16px;
	box-shadow: 0 10px 28px rgba(27, 67, 50, 0.08);
	padding: 28px;
	margin-bottom: 30px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 20px;
	flex-wrap: wrap;
}

.status-info h2 {
	font-family: 'Playfair Display', serif;
	font-size: 24px;
	font-weight: 700;
	color: #1b4332;
	margin-bottom: 6px;
}

.status-date {
	font-size: 13px;
	color: #78716c;
	margin-bottom: 12px;
	display: flex;
	align-items: center;
	gap: 6px;
}

.shift-state {
	display: inline-flex;
	align-items: center;
	gap: 8px;
	font-size: 14px;
	font-weight: 700;
	padding: 6px 14px;
	border-radius: 8px;
	letter-spacing: 0.02em;
}

.shift-state.open {
	background-color: #e8f5e9;
	color: #2e7d32;
	border: 1px solid #c8e6c9;
}

.shift-state.closed {
	background-color: #fef3c7;
	color: #b45309;
	border: 1px solid #fde68a;
}

.status-actions {
	display: flex;
	align-items: center;
	gap: 12px;
}

.btn-gold {
	background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%);
	color: #111111;
	padding: 12px 22px;
	border: none;
	border-radius: 10px;
	font-family: 'Plus Jakarta Sans', sans-serif;
	font-weight: 700;
	font-size: 13px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
	cursor: pointer;
	box-shadow: 0 4px 14px rgba(212, 175, 55, 0.35);
	display: inline-flex;
	align-items: center;
	gap: 8px;
	text-decoration: none;
	transition: all 0.2s ease;
}

.btn-gold:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 18px rgba(212, 175, 55, 0.45);
	color: #000000;
}

.btn-emerald {
	background: linear-gradient(135deg, #1b4332 0%, #081c15 100%);
	color: #ffffff;
	padding: 12px 22px;
	border: none;
	border-radius: 10px;
	font-family: 'Plus Jakarta Sans', sans-serif;
	font-weight: 700;
	font-size: 13px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
	cursor: pointer;
	box-shadow: 0 4px 14px rgba(27, 67, 50, 0.3);
	display: inline-flex;
	align-items: center;
	gap: 8px;
	text-decoration: none;
	transition: all 0.2s ease;
}

.btn-emerald:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 18px rgba(27, 67, 50, 0.45);
	color: #d4af37;
}

.btn-danger-outline {
	background: #ffffff;
	color: #9f1239;
	border: 1.5px solid #fecdd3;
	padding: 11px 20px;
	border-radius: 10px;
	font-family: 'Plus Jakarta Sans', sans-serif;
	font-weight: 700;
	font-size: 13px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
	cursor: pointer;
	display: inline-flex;
	align-items: center;
	gap: 8px;
	transition: all 0.2s ease;
}

.btn-danger-outline:hover {
	background: #ffe4e6;
	border-color: #fda4af;
	transform: translateY(-1px);
}

.table-card {
	background: #ffffff;
	border: 1px solid rgba(212, 175, 55, 0.25);
	border-radius: 16px;
	box-shadow: 0 10px 28px rgba(27, 67, 50, 0.08);
	overflow: hidden;
}

.table-header {
	padding: 20px 28px;
	background: #faf8f3;
	border-bottom: 1px solid #e7e5e0;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.table-title {
	font-family: 'Playfair Display', serif;
	font-size: 19px;
	font-weight: 700;
	color: #1b4332;
}

.table-responsive {
	width: 100%;
	overflow-x: auto;
}

table {
	width: 100%;
	border-collapse: collapse;
	text-align: left;
	font-size: 13px;
}

thead {
	background: #faf8f3;
	color: #1b4332;
	text-transform: uppercase;
	font-size: 11px;
	letter-spacing: 0.06em;
	border-bottom: 2px solid #e7e5e0;
}

th {
	padding: 14px 24px;
	font-weight: 700;
}

tbody tr {
	border-bottom: 1px solid #f3f0ea;
	transition: background 0.15s ease;
}

tbody tr:hover {
	background-color: #fdfbf7;
}

td {
	padding: 16px 24px;
	color: #292524;
	vertical-align: middle;
}

.badge-status {
	padding: 4px 10px;
	border-radius: 6px;
	font-size: 11px;
	font-weight: 700;
	letter-spacing: 0.04em;
	text-transform: uppercase;
	display: inline-block;
}

.badge-opened {
	background: #e8f5e9;
	color: #2e7d32;
	border: 1px solid #c8e6c9;
}

.badge-closed {
	background: #f5f5f4;
	color: #78716c;
	border: 1px solid #e7e5e0;
}

.time-badge {
	background-color: #faf8f3;
	border: 1px solid #e7e5e0;
	padding: 4px 8px;
	border-radius: 6px;
	font-family: monospace;
	font-size: 12px;
	color: #44403c;
}

.empty-state {
	text-align: center;
	padding: 48px 20px;
	color: #78716c;
}

.empty-state i {
	font-size: 38px;
	color: #d4af37;
	margin-bottom: 12px;
	display: block;
}
</style>
</head>
<body>

	<nav class="top-navbar">
		<a href="#" class="brand-container"> <img
			src="https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80"
			alt="Cafe Luft Logo" class="brand-logo"> <span
			class="brand-name">Cafe Luft</span>
		</a>

		<div class="user-meta">
			<span class="user-tag"><i class="bi bi-geo-alt-fill"></i>
				${sessionScope.loginUser.branchName}</span> <span><i
				class="bi bi-person-fill"></i> ${sessionScope.loginUser.name}</span> <a
				href="${pageContext.request.contextPath}/auth?action=logout"
				class="btn-logout"> <i class="bi bi-box-arrow-right"></i> Sign
				Out
			</a>
		</div>
	</nav>

	<div class="page-container">

		<div class="status-card">
			<div class="status-info">
				<h2>Daily Shift Register</h2>
				<div class="status-date">
					<i class="bi bi-calendar3"></i> Date: <strong>${today}</strong>
				</div>

				<c:choose>
					<c:when test="${not empty activeRegister}">
						<div class="shift-state open">
							<i class="bi bi-check-circle-fill"></i> Shift Status: OPENED
						</div>
						<div style="font-size: 12px; color: #57534e; margin-top: 10px;">
							Opened by <strong>${activeRegister.employee_name}</strong> at <span
								class="time-badge">${activeRegister.opened_at}</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="shift-state closed">
							<i class="bi bi-exclamation-triangle-fill"></i> Shift Status:
							CLOSED
						</div>
						<div style="font-size: 12px; color: #78716c; margin-top: 10px;">
							Please open register before starting POS billing transactions.</div>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="status-actions">
				<c:choose>
					<c:when test="${not empty activeRegister}">
						<a href="${pageContext.request.contextPath}/pos" class="btn-gold">
							<i class="bi bi-calculator-fill"></i> Enter POS Counter
						</a>
						<form action="${pageContext.request.contextPath}/daily-register"
							method="POST" style="margin: 0;">
							<input type="hidden" name="action" value="close"> <input
								type="hidden" name="registerId"
								value="${activeRegister.register_id}">
							<button type="submit" class="btn-danger-outline"
								onclick="return confirm('ဆိုင်းသိမ်းရန် သေချာပါသလား?')">
								<i class="bi bi-door-closed"></i> Close Shift
							</button>
						</form>
					</c:when>
					<c:otherwise>
						<form action="${pageContext.request.contextPath}/daily-register"
							method="POST" style="margin: 0;">
							<input type="hidden" name="action" value="open">
							<button type="submit" class="btn-emerald">
								<i class="bi bi-door-open-fill"></i> Open Shift Now
							</button>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="table-card">
			<div class="table-header">
				<h3 class="table-title">Shift History & Audit Logs</h3>
				<span style="font-size: 12px; color: #78716c; font-weight: 600;">
					Branch: ${sessionScope.loginUser.branchName} </span>
			</div>

			<div class="table-responsive">
				<table>
					<thead>
						<tr>
							<th>Date</th>
							<th>Staff Member</th>
							<th>Opened Time</th>
							<th>Closed Time</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty registers}">
								<c:forEach var="item" items="${registers}">
									<tr>
										<td style="font-weight: 600;">${item.date}</td>
										<td><i class="bi bi-person-circle"
											style="color: #d4af37; margin-right: 4px;"></i>
											${item.employee_name}</td>
										<td><span class="time-badge">${item.opened_at}</span></td>
										<td><c:choose>
												<c:when test="${not empty item.closed_at}">
													<span class="time-badge">${item.closed_at}</span>
												</c:when>
												<c:otherwise>
													<span style="color: #a8a29e; font-style: italic;">In
														Progress...</span>
												</c:otherwise>
											</c:choose></td>
										<td><c:choose>
												<c:when test="${item.status_name eq 'OPENED'}">
													<span class="badge-status badge-opened"> <i
														class="bi bi-dot"></i> OPENED
													</span>
												</c:when>
												<c:otherwise>
													<span class="badge-status badge-closed"> <i
														class="bi bi-check2"></i> CLOSED
													</span>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5">
										<div class="empty-state">
											<i class="bi bi-clock-history"></i>
											<p style="font-weight: 600; margin-bottom: 4px;">No
												Register Records Found</p>
											<p style="font-size: 12px;">This branch has no previous
												shift records logged yet.</p>
										</div>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>

	</div>

</body>
</html>