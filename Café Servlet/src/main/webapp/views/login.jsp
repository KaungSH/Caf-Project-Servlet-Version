<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Café Luft - Employee Login</title>

<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
* {
	box-sizing: border-box;
}

body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #F7F5F0;
	color: #1c1917;
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0;
	padding: 20px;
}

.login-card {
	width: 100%;
	max-width: 420px;
	background-color: #ffffff;
	border: 1px solid rgba(212, 175, 55, 0.25);
	border-radius: 16px;
	box-shadow: 0 12px 32px rgba(27, 67, 50, 0.12);
	overflow: hidden;
}

.card-header-banner {
	background: linear-gradient(135deg, rgba(27, 67, 50, 0.95) 0%,
		rgba(8, 28, 21, 0.98) 100%),
		url('https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=1200&q=80');
	background-size: cover;
	background-position: center;
	padding: 28px 20px 24px;
	text-align: center;
	color: #ffffff;
	border-bottom: 3px solid #d4af37;
}

.header-logo {
	width: 58px;
	height: 58px;
	border-radius: 50%;
	object-fit: cover;
	border: 2px solid #d4af37;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
	margin-bottom: 10px;
}

.brand-title {
	font-family: 'Playfair Display', serif;
	font-size: 26px;
	font-weight: 700;
	margin: 0;
	color: #ffffff;
	letter-spacing: 0.5px;
}

.subtitle {
	color: #d4af37;
	font-size: 11px;
	margin-top: 4px;
	text-transform: uppercase;
	letter-spacing: 0.12em;
	font-weight: 700;
}

.card-body {
	padding: 28px 28px 24px;
}

.form-group {
	margin-bottom: 18px;
}

.form-label {
	display: block;
	font-size: 12px;
	font-weight: 700;
	color: #1b4332;
	text-transform: uppercase;
	letter-spacing: 0.05em;
	margin-bottom: 6px;
}

.form-control {
	width: 100%;
	padding: 11px 14px;
	font-size: 14px;
	font-family: 'Plus Jakarta Sans', sans-serif;
	color: #1c1917;
	background-color: #faf8f3;
	border: 1px solid #e7e5e0;
	border-radius: 10px;
	outline: none;
	transition: all 0.2s ease;
}

.form-control:focus {
	background-color: #ffffff;
	border-color: #d4af37;
	box-shadow: 0 0 0 3px rgba(212, 175, 55, 0.2);
}

.btn-submit {
	width: 100%;
	background: linear-gradient(135deg, #d4af37 0%, #aa820a 100%);
	color: #111111;
	padding: 12px;
	border: none;
	border-radius: 10px;
	font-family: 'Plus Jakarta Sans', sans-serif;
	font-weight: 700;
	font-size: 13px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
	cursor: pointer;
	box-shadow: 0 4px 14px rgba(212, 175, 55, 0.35);
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 8px;
	transition: all 0.2s ease;
	margin-top: 24px;
}

.btn-submit:hover {
	transform: translateY(-1px);
	box-shadow: 0 6px 18px rgba(212, 175, 55, 0.45);
}

.btn-submit:active {
	transform: translateY(0);
}

.alert-error {
	background-color: #ffe4e6;
	color: #9f1239;
	border: 1px solid #fecdd3;
	border-radius: 8px;
	padding: 10px 14px;
	font-size: 12px;
	font-weight: 600;
	margin-bottom: 18px;
	display: flex;
	align-items: center;
	gap: 8px;
}

.card-footer {
	text-align: center;
	padding: 0 28px 20px;
	font-size: 11px;
	color: #78716c;
}
</style>
</head>
<body>

	<div class="login-card">

		<div class="card-header-banner">
			<img
				src="https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=100&q=80"
				alt="Cafe Luft Logo" class="header-logo">
			<h1 class="brand-title">Cafe Luft</h1>
			<div class="subtitle">Employee Portal Login</div>
		</div>

		<div class="card-body">

			<c:if test="${not empty errorMessage}">
				<div class="alert-error">
					<i class="bi bi-exclamation-circle-fill"></i> <span>${errorMessage}</span>
				</div>
			</c:if>


			<form action="${pageContext.request.contextPath}/auth?action=login"
				method="POST">
				<div class="form-group">
					<label for="email" class="form-label">Email Address</label> <input
						type="email" class="form-control" id="email" name="email"
						placeholder="admin@mail.com" required autofocus>
				</div>

				<div class="form-group">
					<label for="password" class="form-label">Password</label> <input
						type="password" class="form-control" id="password" name="password"
						placeholder="••••••••" required>
				</div>

				<button type="submit" class="btn-submit">
					<i class="bi bi-box-arrow-in-right"></i> Sign In
				</button>
			</form>
		</div>


	</div>

</body>
</html>