<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Employee Deleted - Cafe Luft</title>
<link
	href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Plus Jakarta Sans', sans-serif;
	background-color: #F4F5F0;
	color: #1C1917;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background-color: #FFFFFF;
	padding: 40px;
	border-radius: 20px;
	border: 1px solid #E2E8F0;
	box-shadow: 0 10px 30px -5px rgba(0, 0, 0, 0.05);
	text-align: center;
	max-width: 450px;
	width: 100%;
}

h2 {
	color: #E11D48;
	font-size: 22px;
	font-weight: 700;
	margin-top: 0;
	margin-bottom: 12px;
}

p {
	color: #475569;
	font-size: 14px;
	margin-bottom: 24px;
}

a {
	text-decoration: none;
	background-color: #1B4332;
	color: #FFFFFF;
	padding: 12px 24px;
	border-radius: 12px;
	font-size: 14px;
	font-weight: 600;
	display: inline-block;
	transition: background-color 0.2s;
	box-shadow: 0 4px 12px rgba(27, 67, 50, 0.2);
}

a:hover {
	background-color: #133024;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Employee Deleted Successfully!</h2>
		<p>Successfull Deleted</p>
		<a href="${pageContext.request.contextPath}/employee?action=list">Back
			to Employee List</a>
	</div>
</body>
</html>